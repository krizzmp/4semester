
package dk.sdu.group5.weapon;

import dk.sdu.group5.common.data.WeaponType;
import dk.sdu.group5.common.data.World;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BulletController {
    
    private static BulletController instance;
    private float bulletRemoveTime = 5.0f;
    private float shootInterval = 1.0f;
    private boolean isLocked = false; 
    private long startLockTime;
    private int weaponMagazineSize = 0;
    private final List<Bullet> weaponMagazine = new LinkedList<>();
    
    public static BulletController getInstance() {
        if (instance == null)
            instance = new BulletController();
        return instance;
    }
    
    public void update(World world, float delta) {
        long currentLockTime = System.currentTimeMillis();
        setShootInterval(world.getWeaponType());
        if(currentLockTime - startLockTime >= shootInterval * 1000) {
            isLocked = false;
        }
        
        updateBullet(world, delta);
    }
    
    public void shootBullet(World world, String direction) {
        boolean magazineNotFull = false;
        
        if(weaponMagazineSize == 0) {
            magazineNotFull = true;
        } 
        else if(weaponMagazine.size() < weaponMagazineSize) {
            magazineNotFull = true;
        }
        
        if(!isLocked && magazineNotFull) {
            Bullet bullet = new Bullet(world, direction);
            weaponMagazine.add(bullet);
            startLockTime = System.currentTimeMillis();
            isLocked = true;
        }
    }
    
    private void setShootInterval(WeaponType type) {
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
                it.remove();
            }
        }
    }
}