
package dk.sdu.group5.common.data;

import java.util.logging.Level;
import java.util.logging.Logger;


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
            bullet.addProperty("damageable");
            world.AddEntity(bullet);
        } catch (Exception e) {
            Logger.getLogger(BulletController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
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
