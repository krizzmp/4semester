package dk.sdu.group5.weapon;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.WeaponType;
import dk.sdu.group5.common.data.World;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

class BulletController {
    private final List<Bullet> weaponMagazine;
    private float shootInterval = 1.0f;
    private boolean isLocked = false;
    private long startLockTime;
    private int weaponMagazineSize = 0;

    BulletController() {
        weaponMagazine = new LinkedList<>();
    }

    void update(World world, float delta) {
        long currentLockTime = System.currentTimeMillis();
        setShootInterval(world.getWeaponType());
        if (currentLockTime - startLockTime >= shootInterval * 1000) {
            isLocked = false;
        }

        updateBullets(world, delta);
    }

    private void setShootInterval(WeaponType type) {
        if (type == WeaponType.PISTOL) {
            shootInterval = 1.0f;
            weaponMagazineSize = 0;
        }
        if (type == WeaponType.RIFLE) {
            shootInterval = 0.5f;
            weaponMagazineSize = 30;
        }
        if (type == WeaponType.MSG) {
            shootInterval = 0.25f;
            weaponMagazineSize = 30;
        }
    }

    private void updateBullets(World world, float delta) {
        Iterator<Bullet> it = weaponMagazine.iterator();
        while (it.hasNext()) {
            Bullet itBullet = it.next();
            itBullet.update(world, delta);
            if (itBullet.toBeRemoved()) {
                itBullet.removeBullet(world);
                it.remove();
            }
        }
    }

    void shootBullet(World world, String direction) {
        
        Optional<Entity> player = getPlayer(world.getEntities());

        if (player.isPresent() && !isLocked 
                && (weaponMagazine.size() < weaponMagazineSize 
                || weaponMagazineSize == 0)) {
            Bullet bullet = new Bullet(world, player.get(), direction);
            weaponMagazine.add(bullet);
            startLockTime = System.currentTimeMillis();
            isLocked = true;
        }
    }
    
    private Optional<Entity> getPlayer(Collection<Entity> xs) {
        return xs.stream().filter(e -> e.getType() == EntityType.PLAYER).findFirst();
    }

    void clearBullets(World world) {
        weaponMagazine.forEach(b -> b.removeBullet(world));
        weaponMagazine.clear();
    }
}
