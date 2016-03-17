
package dk.sdu.group5.common.data;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;


public class BulletController {
    
    private static BulletController instance;
    private int speed = 2; // Should be removed and use Entity speed instead
    
    private float bulletRemoveTime = 5.0f;
    private float shootInterval = 1.0f;
    private boolean isLocked = false; 
    private int startLockTime;
    private int currentLockTime;
    
    private int weaponMagazineSize = 5;
    private List<Bullet> weaponMagazine = new LinkedList<>();

    
    public static BulletController getInstance() {
        if (instance == null)
            instance = new BulletController();
        return instance;
    }
    
    public void update(World world, float delta) {
        currentLockTime = (int) System.currentTimeMillis();
        if(currentLockTime - startLockTime >= shootInterval * 1000) {
            isLocked = false;
        }
        
        updateBullet(world, delta);
    }
    
    public void shootBullet(World world) {
        boolean magazineNotFull = false;
        
        if(weaponMagazineSize == 0) {
            magazineNotFull = true;
        } 
        else if((weaponMagazine.size() < weaponMagazineSize)) {
            magazineNotFull = true;
        }
        
        if(!isLocked && magazineNotFull) {
            System.out.println("created bullet");
            
            Bullet bullet = new Bullet(world);

            weaponMagazine.add(bullet);
            startLockTime = (int) System.currentTimeMillis();
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
    
    private void updateBullet(World world, float delta) {
        Iterator<Bullet> it = weaponMagazine.iterator();

        while(it.hasNext()) {
            Bullet itBullet = it.next();
            itBullet.update(delta);
            if(itBullet.toBeRemoved()) {
                itBullet.removeBullet(world);
                itBullet = null;
                it.remove();
            }
        }
    }
}
