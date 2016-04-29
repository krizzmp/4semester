package dk.sdu.group5.weapon;

import dk.sdu.group5.common.data.GameKeys;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.lookup.ServiceProvider;

//TODO 04/04/16 Bullet entities should be created in here, and the BulletController update should be in its own component.
//Or the update should be in here.
@ServiceProvider(service = IGameProcess.class)
public class WeaponGameProcessor implements IGameProcess {

    private BulletController bc;
    private GameKeys gameKeys;

    @Override
    public void start(World world) {
        bc = new BulletController();
        gameKeys = GameKeys.getInstance();
    }

    @Override
    public void update(World world, float delta) {
        bc.update(world, delta);

        // Shoot in 8 directions
        // Note: A whole lot of if statements. I can make it differently, but let's talk about it first - Martin F
        if (gameKeys.player_shoot_up.getKeyState()) {
            if (gameKeys.player_shoot_left.getKeyState()) {
                // if up and left
                bc.shootBullet(world, "upLeft");
            } else if (gameKeys.player_shoot_right.getKeyState()) {
                // if up and right
                bc.shootBullet(world, "upRight");
            } else {
                // if up
                bc.shootBullet(world, "up");
            }
        } else if (gameKeys.player_shoot_down.getKeyState()) {
            if (gameKeys.player_shoot_left.getKeyState()) {
                // if down and left
                bc.shootBullet(world, "downLeft");
            } else if (gameKeys.player_shoot_right.getKeyState()) {
                // if down and right
                bc.shootBullet(world, "downRight");
            } else {
                // if down
                bc.shootBullet(world, "down");
            }
        } else {
            if (gameKeys.player_shoot_left.getKeyState()) {
                // if left
                bc.shootBullet(world, "left");
            }
            if (gameKeys.player_shoot_right.getKeyState()) {
                // if right
                bc.shootBullet(world, "right");
            }
        }
    }

    @Override
    public void stop(World world) {
    }

}
