
package dk.sdu.group5.barrier;

import java.util.List;
import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.GameKeys;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.data.collision.AABB;
import dk.sdu.group5.common.data.collision.CollisionDetector;
import dk.sdu.group5.common.data.collision.SquareCollider;
import dk.sdu.group5.common.services.IGameProcess;

import java.util.LinkedList;
import java.util.Optional;
import org.openide.util.lookup.ServiceProvider;


@ServiceProvider(service = IGameProcess.class)
public class BarrierGameProcess implements IGameProcess {
    
    private int maxBarriers = 10; //TODO: Set a proper number of max barriers
    
    private float offsetX = 8f;
    private float offsetY = 8f;
    private float posX;
    private float posY;
    private final int BARRIER_HEIGHT = 32;
    private final int BARRIER_WIDTH = 32;
    
    private Entity barrier;
    
    private boolean placeable = false;
    
    
    private List<Entity> listBarriers = new LinkedList<>();

    @Override
    public void install() {

    }

    @Override
    public void start(World world) {
        
    }

    @Override
    public void update(World world, float delta) {
        GameKeys gameKeys = GameKeys.getInstance();
        if(gameKeys.player_place_barrier.getKeyState()) {
            //TODO: 19/03/16 Check player direction and add barrier in front of player. - Martin F.
            //TODO: 31/03/16 Implement getWidth and getHeight from entities. - Martin F.
            //TODO: 31/03/16 Replace key detection with look direction. - Martin F.
            //TODO: 31/03/16 Implement via tilesystem (Map component). - Martin F.
            Entity player = getPlayer(world.getEntities()).orElseThrow(RuntimeException::new);
            
            // default is the right direction
            posX = player.getX() + offsetX + 32; // 32 is player width.
            posY = player.getY();

            if(gameKeys.player_movement_up.getKeyState()) {
                // Place up
                posX = player.getX();
                posY = player.getY() + offsetY + 32; // 32 is player height.
            }
            else if(gameKeys.player_movement_down.getKeyState()) {
                // Place down
                posX = player.getX();
                posY = player.getY() - offsetY - BARRIER_HEIGHT; // 32 is barrier height.
            }
            else if(gameKeys.player_movement_left.getKeyState()) {
                // Place left
                posX = player.getX() - offsetX - BARRIER_WIDTH; // 32 is barrier width.
                posY = player.getY();
            }

            if(listBarriers.size() < maxBarriers) {
                barrier = new Entity();
                barrier.setType(EntityType.BARRIER);
                barrier.setHealth(50);
                barrier.setTexture("gridPattern.png");
                barrier.setSpeed(0);
                barrier.setCollider(new SquareCollider(false, new AABB(-16, -16, 16, 16)));
                barrier.setX(posX);
                barrier.setY(posY);
                barrier.addProperty("collidable");
                barrier.addProperty("static");
                barrier.addProperty("tangible");

                if(checkCollision(world)) {
                    world.addEntity(barrier);
                    listBarriers.add(barrier);
                }
            }

        }
    }

    private boolean checkCollision(World world) {
        // Collision stuff
        CollisionDetector cd = world.getCollisionDetector();
        return cd.collides(barrier, world.getEntities()).isEmpty();
    }
    
    private Optional<Entity> getPlayer(List<Entity> xs) {
        return xs.stream().filter(e -> e.getType() == EntityType.PLAYER).findFirst();
    }
    
    @Override
    public void stop(World world) {
        
    }

    @Override
    public void uninstall() {

    }

}