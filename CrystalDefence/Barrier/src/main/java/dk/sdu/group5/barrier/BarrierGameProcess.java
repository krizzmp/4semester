package dk.sdu.group5.barrier;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.GameKeys;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.data.collision.AABB;
import dk.sdu.group5.common.data.collision.SquareCollider;
import dk.sdu.group5.common.services.ICollisionDetectorService;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.Lookup;
import org.openide.util.LookupListener;
import org.openide.util.lookup.ServiceProvider;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@ServiceProvider(service = IGameProcess.class)
public class BarrierGameProcess implements IGameProcess {

    private int maxBarriers = 10; //TODO: Set a proper number of max barriers
    private float offsetX = 8f;
    private float offsetY = 8f;
    private float posX;
    private float posY;
    private final int BARRIER_HEIGHT = 48;
    private final int BARRIER_WIDTH = 48;

    private Entity barrier;

    private boolean placeable = false;

    private List<Entity> listBarriers = new LinkedList<>();

    private Lookup.Result<ICollisionDetectorService> collisionDetectorResult;
    private ICollisionDetectorService collisionDetectorService;

    private ReadWriteLock collisionDetectorLock = new ReentrantReadWriteLock();

    @Override
    public void start(World world) {
        collisionDetectorResult = Lookup.getDefault().lookupResult(ICollisionDetectorService.class);
        collisionDetectorResult.addLookupListener(lookupListenerCollisionDetector);

        updateDetectorService();
    }

    private final LookupListener lookupListenerCollisionDetector = le -> updateDetectorService();

    private void updateDetectorService() {
        collisionDetectorLock.writeLock().lock();
        collisionDetectorService = findDetectorService();
        collisionDetectorLock.writeLock().unlock();
    }

    private ICollisionDetectorService findDetectorService() {
        Optional<? extends ICollisionDetectorService> optionalDetector;
        optionalDetector = collisionDetectorResult.allInstances().stream().findFirst();
        if (optionalDetector.isPresent()) {
            return optionalDetector.get();
        }

        return null;
    }

    @Override
    public void update(World world, float delta) {
        GameKeys gameKeys = GameKeys.getInstance();
        if (gameKeys.player_place_barrier.getKeyState()) {
            //TODO: 19/03/16 Check player direction and add barrier in front of player. - Martin F.
            //TODO: 31/03/16 Implement getWidth and getHeight from entities. - Martin F.
            //TODO: 31/03/16 Replace key detection with look direction. - Martin F.
            //TODO: 31/03/16 Implement via tilesystem (Map component). - Martin F.
            Entity player = getPlayer(world.getEntities()).orElseThrow(RuntimeException::new);

            // default is the right direction
            // TODO: 11/04/16 Player width can be found through the entity's collider
            posX = player.getX() + offsetX + 48; // 32 is player width.
            posY = player.getY();

            if (gameKeys.player_movement_up.getKeyState()) {
                // Place up
                posX = player.getX();
                // TODO: 11/04/16 Player height can be found through the entity's collider
                posY = player.getY() + offsetY + 48; // 32 is player height.
            } else if (gameKeys.player_movement_down.getKeyState()) {
                // Place down
                posX = player.getX();
                posY = player.getY() - offsetY - BARRIER_HEIGHT; // 32 is barrier height.
            } else if (gameKeys.player_movement_left.getKeyState()) {
                // Place left
                posX = player.getX() - offsetX - BARRIER_WIDTH; // 32 is barrier width.
                posY = player.getY();
            }
            if (posX % 48 != 0) {
                if (posX % 48 < 24) {
                    posX = posX - (posX % 48);
                } else {
                    posX = posX + (48 - (posX % 48));
                }
            }

            if (posY % 48 != 0) {
                if (posY % 48 < 24) {
                    posY = posY - (posY % 48);
                } else {
                    posY = posY + (48 - (posY % 48));
                }
            }

            if (listBarriers.size() < maxBarriers) {
                Entity barrier = new Entity();
                barrier.setType(EntityType.BARRIER);
                barrier.setHealth(50);
                barrier.setTexturePath("barrierTexture02.png");
                barrier.setSpeed(0);
                barrier.setCollider(new SquareCollider(false, new AABB(-16, -16, 32, 32)));
                barrier.setX(posX);
                barrier.setY(posY);
                barrier.addProperty("collidable");
                barrier.addProperty("static");
                barrier.addProperty("tangible");

                if (checkCollision(barrier, world.getEntities())) {
                    world.addEntity(barrier);
                    listBarriers.add(barrier);
                }
            }

        }

    }

    private boolean checkCollision(Entity barrier, List<Entity> entities) {
        // Collision stuff
        collisionDetectorLock.readLock().lock();
        if (collisionDetectorService != null) {
            for (Entity ent : entities) {
                if (collisionDetectorService.collides(barrier, ent)) {
                    return false;
                }
            }
        }
        collisionDetectorLock.readLock().unlock();

        return true;
    }

    private Optional<Entity> getPlayer(List<Entity> entities) {
        return entities.stream().filter(e -> e.getType() == EntityType.PLAYER).findFirst();
    }

    @Override
    public void stop(World world) {
        listBarriers.stream().forEach(e -> world.removeEntity(e));
        listBarriers.clear();
    }

}