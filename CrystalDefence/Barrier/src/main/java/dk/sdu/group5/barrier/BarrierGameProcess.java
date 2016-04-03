
package dk.sdu.group5.barrier;

import java.util.List;
import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.GameKeys;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.services.IGameProcess;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;
import org.openide.util.lookup.ServiceProvider;


@ServiceProvider(service = IGameProcess.class)
public class BarrierGameProcess implements IGameProcess {
    
    private int maxBarriers = 3;
    
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
            
            placeable = !checkCollision();
            if(placeable && listBarriers.size() < maxBarriers) {
                barrier = new Entity();
                barrier.setType(EntityType.BARRIER);
                barrier.setHealth(50);
                barrier.setTexture("gridPattern.png");
                barrier.setSpeed(0);
                barrier.setX(posX);
                barrier.setY(posY);
                barrier.addProperty("collidable");
                barrier.addProperty("tangible");
                world.addEntity(barrier);
                listBarriers.add(barrier);
            } 

        }
    }
    
    private boolean checkCollision() {
        // TODO: 31/03/16 Implement collision detection for all entities | Should probably be replaced with proper collision detection

        // check for barriers | should be checked last
        Iterator<Entity> it = listBarriers.iterator();

        while(it.hasNext()) {
            Entity itBarrier = it.next();
            if((posX > (itBarrier.getX() - BARRIER_WIDTH)) && (posX < (itBarrier.getX() + BARRIER_WIDTH)) ) {
                if((posY > (itBarrier.getY() - BARRIER_HEIGHT)) && (posY < (itBarrier.getY() + BARRIER_HEIGHT)) ) {
                    return true;
                }
            }
        }
        return false;
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