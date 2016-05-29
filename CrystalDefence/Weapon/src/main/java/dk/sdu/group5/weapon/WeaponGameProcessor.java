package dk.sdu.group5.weapon;

import dk.sdu.group5.common.data.GameKeys;
import dk.sdu.group5.common.data.KeyState;
import dk.sdu.group5.common.data.WeaponType;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IGameProcess.class)
public class WeaponGameProcessor implements IGameProcess {

    private BulletController bulletController;

    @Override
    public void start(World world) {
        world.setWeaponType(WeaponType.PISTOL);
        bulletController = new BulletController();
    }

    @Override
    public void update(World world, float delta) {
        bulletController.update(world, delta);

        GameKeys gameKeys = world.getGameKeys();
        // Shoot in 8 directions
        if (gameKeys.getPlayerShootUp().getState() == KeyState.HELD) {
            if (gameKeys.getPlayerShootLeft().getState() == KeyState.HELD) {
                // if up and left
                bulletController.shootBullet(world, "upLeft");
            } else if (gameKeys.getPlayerShootRight().getState() == KeyState.HELD) {
                // if up and right
                bulletController.shootBullet(world, "upRight");
            } else {
                // if up
                bulletController.shootBullet(world, "up");
            }
        } else if (gameKeys.getPlayerShootDown().getState() == KeyState.HELD) {
            if (gameKeys.getPlayerShootLeft().getState() == KeyState.HELD) {
                // if down and left
                bulletController.shootBullet(world, "downLeft");
            } else if (gameKeys.getPlayerShootRight().getState() == KeyState.HELD) {
                // if down and right
                bulletController.shootBullet(world, "downRight");
            } else {
                // if down
                bulletController.shootBullet(world, "down");
            }
        } else {
            if (gameKeys.getPlayerShootLeft().getState() == KeyState.HELD) {
                // if left
                bulletController.shootBullet(world, "left");
            }
            if (gameKeys.getPlayerShootRight().getState() == KeyState.HELD) {
                // if right
                bulletController.shootBullet(world, "right");
            }
        }
    }

    @Override
    public void stop(World world) {
        bulletController.clearBullets(world);
    }

}
