package dk.sdu.group5.barrier;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.GameKeys;
import dk.sdu.group5.common.data.KeyState;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.data.collision.AABB;
import dk.sdu.group5.common.data.collision.SquareCollider;
import dk.sdu.group5.common.services.ICollisionDetectorService;
import dk.sdu.group5.common.services.IGameProcess;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.openide.util.Lookup;
import org.openide.util.LookupListener;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IGameProcess.class)
public class BarrierGameProcess implements IGameProcess {

    private int maxBarriers = 5;
    private float offsetX = 8f;
    private float offsetY = 8f;
    private float posX;
    private float posY;

    private List<Entity> listBarriers = new LinkedList<>();

    private Lookup.Result<ICollisionDetectorService> collisionDetectorResult;
    private ICollisionDetectorService collisionDetectorService;

    private final Object collisionDetectorLock = new Object();

    @Override
    public void start(World world) {
        collisionDetectorResult = Lookup.getDefault().lookupResult(ICollisionDetectorService.class);
        collisionDetectorResult.addLookupListener(lookupListenerCollisionDetector);

        updateDetectorService();
    }

    private final LookupListener lookupListenerCollisionDetector = le -> updateDetectorService();

    private void updateDetectorService() {
        synchronized (collisionDetectorLock) {
            collisionDetectorService = findDetectorService();
        }
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
        GameKeys gameKeys = world.getGameKeys();
        if (gameKeys.getPlayerPlaceBarrier().getState() == KeyState.PRESSED) {
            //TODO: 31/03/16 Implement getWidth and getHeight from entities. - Martin F.
            //TODO: 31/03/16 Replace key detection with look direction. - Martin F.
            Optional<Entity> playerOptional = getPlayer(world.getEntities());
            if(!playerOptional.isPresent()){
                return;
            }
            
            Entity player = playerOptional.get();

            // default is the right direction
            posX = player.getX() + offsetX + player.getBounds().getWidth();
            posY = player.getY();

            if (gameKeys.getPlayerMovementUp().getState() == KeyState.HELD) {
                // Place up
                posX = player.getX();
                posY = player.getY() + offsetY + player.getBounds().getHeight();
            } else if (gameKeys.getPlayerMovementDown().getState() == KeyState.HELD) {
                // Place down
                posX = player.getX();
                posY = player.getY() - offsetY - player.getBounds().getHeight();
            } else if (gameKeys.getPlayerMovementLeft().getState() == KeyState.HELD) {
                // Place left
                posX = player.getX() - offsetX - player.getBounds().getWidth();
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

    private boolean checkCollision(Entity barrier, Collection<Entity> entities) {
        synchronized (collisionDetectorLock) {
            if (collisionDetectorService == null) {
                return true;
            }

            for (Entity ent : entities) {
                if (collisionDetectorService.collides(barrier, ent)) {
                    return false;
                }
            }
        }

        return true;
    }

    private Optional<Entity> getPlayer(Collection<Entity> entities) {
        return entities.stream().filter(e -> e.getType() == EntityType.PLAYER).findFirst();
    }

    @Override
    public void stop(World world) {
        listBarriers.stream().forEach(e -> world.removeEntity(e));
        listBarriers.clear();
    }

}