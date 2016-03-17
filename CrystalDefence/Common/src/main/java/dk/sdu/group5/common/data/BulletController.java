
package dk.sdu.group5.common.data;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;


public class BulletController {
    
    private static BulletController instance;
    private Entity bullet;
    private int speed = 2; // Should be removed and use Entity speed instead
    
    private float shootInterval = 1.0f;
    private boolean isLocked = false; 
    private long startLockTime;
    private long currentLockTime;
    
    private int weaponMagazineSize = 0;
    private List<Entity> weaponMagazine = new LinkedList<>();
    
    public static BulletController getInstance() {
        if (instance == null)
            instance = new BulletController();
        return instance;
    }
    
    public void update(World world, float delta) {
        currentLockTime = System.currentTimeMillis();
        if(currentLockTime - startLockTime >= shootInterval) {
            isLocked = false;
        }
        
        updateBullet(delta);
    }
    
    public void shootBullet() {
        if(!isLocked) {
            bullet = new Entity();
            bullet.setType(EntityType.BULLET);
            bullet.setLives(1);
            bullet.setTexture("bulletTexture.png");
            bullet.setX(700);
            bullet.setY(100);
            try {
                bullet.addProperty("collidable");
                bullet.addProperty("damageable");
            } catch (Exception e) {
                Logger.getLogger(BulletController.class.getName()).log(Level.SEVERE, null, e);
            }

            weaponMagazine.add(bullet);
            startLockTime = System.currentTimeMillis();
            isLocked = true;
        }
    }
    
    public void setShootInterval(WeaponType type) {
        if(type == WeaponType.PISTOL) {
            shootInterval = 1.0f;
            weaponMagazineSize = 0;
        }
        if(type == WeaponType.RIFLE) {
            shootInterval = 0.5f;
            weaponMagazineSize = 30;
        }
        if(type == WeaponType.MSG) {
            shootInterval = 0.25f;
            weaponMagazineSize = 30;
        }
    }
    
    private void updateBullet(float delta) {
        Iterator<Entity> it = weaponMagazine.iterator();

        while(it.hasNext()) {

        }
    }
}
