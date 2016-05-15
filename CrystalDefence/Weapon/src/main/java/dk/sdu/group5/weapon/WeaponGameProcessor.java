package dk.sdu.group5.weapon;

import dk.sdu.group5.common.data.*;
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
        // Note: A whole lot of if statements. I can make it differently, but let's talk about it first - Martin F
        if (gameKeys.getPlayerShootUp().getState() == KeyState.HELD) {
            if (gameKeys.getPlayerShootLeft().getState() == KeyState.HELD) {
                // if up and left
                bulletController.shootBullet(world, "upLeft", new Posf2d(-1f, 1f));
            } else if (gameKeys.getPlayerShootRight().getState() == KeyState.HELD) {
                // if up and right
                bulletController.shootBullet(world, "upRight", new Posf2d(1f, 1f));
            } else {
                // if up
                bulletController.shootBullet(world, "up", new Posf2d(0f, 1f));
            }
        } else if (gameKeys.getPlayerShootDown().getState() == KeyState.HELD) {
            if (gameKeys.getPlayerShootLeft().getState() == KeyState.HELD) {
                // if down and left
                bulletController.shootBullet(world, "downLeft", new Posf2d(-1f, -1f));
            } else if (gameKeys.getPlayerShootRight().getState() == KeyState.HELD) {
                // if down and right
                bulletController.shootBullet(world, "downRight", new Posf2d(1f, -1f));
            } else {
                // if down
                bulletController.shootBullet(world, "down", new Posf2d(0f, -1f));
            }
        } else {
            if (gameKeys.getPlayerShootLeft().getState() == KeyState.HELD) {
                // if left
                bulletController.shootBullet(world, "left", new Posf2d(-1f, 0f));
            }
            if (gameKeys.getPlayerShootRight().getState() == KeyState.HELD) {
                // if right
                bulletController.shootBullet(world, "right", new Posf2d(1f, 0f));
            }
        }
    }

    @Override
    public void stop(World world) {
        bulletController.clearBullets(world);
    }

}
