
package dk.sdu.group5.common.data;

import java.util.logging.Level;
import java.util.logging.Logger;


//TODO 04/04/16 The creation of entities, and it corresponding addition and removal
//in the world should not exist in common.
//TODO 04/04/16 Bullet should probably just be a data class with 'toBeRemoved', 'activeTime', 'startTime' and 'currentTime'
//This could then be mapped to a bullet entity in the bullet process.
public class Bullet {
    
    private Entity bullet;
    
    private boolean toBeRemoved = false;
    
    private int activeTime = 10000;
    private int startTime = (int) System.currentTimeMillis();
    private int currentTime;
   
    private int speed = 60;  // Should be removed later
    
    
    public Bullet(World world) {
        bullet = new Entity();
        bullet.setType(EntityType.BULLET);
        bullet.setLives(1);
        bullet.setTexture("bulletTexture.png");
        bullet.setX(50);
        bullet.setY(50);
        try {
            bullet.addProperty("collidable");
            //TODO: 04/04/16 Should not be "damageable"
            bullet.addProperty("damageable");
            world.AddEntity(bullet);
        } catch (Exception e) {
            Logger.getLogger(BulletController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    // TODO: 04/04/16 Should be moved outside common
    public void update(float delta) {
        bullet.setX(bullet.getX() + speed * delta);
        bullet.setY(50);
        
        currentTime = (int) System.currentTimeMillis();
        if(currentTime - startTime >= activeTime) {
            toBeRemoved = true;
        }
    }
    
    public boolean toBeRemoved() {
        return toBeRemoved;
    }
    
    public void removeBullet(World world) {
        world.RemoveEntity(bullet);
    }
}
