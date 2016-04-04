
package dk.sdu.group5.weapon;

import dk.sdu.group5.common.data.GameKeys;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IGameProcess.class)
public class WeaponGameProcessor implements IGameProcess {

    
    @Override
    public void install() {
        
    }

    @Override
    public void start(World world) {
    }

    @Override
    public void update(World world, float delta) {
        BulletController.getInstance().update(world, delta);
        GameKeys gameKeys = GameKeys.getInstance();

        // TODO 04/04/16 Does not always register the diagonal input directions.
        // Using vectors would only require four if statements.
        // Shoot in 8 directions
        // Note: A whole lot of if statements. I can make it differently, but let's talk about it first - Martin F
        if (gameKeys.player_shoot_up.getKeyState()) {
            if (gameKeys.player_shoot_left.getKeyState()) {
                // if up and left
                BulletController.getInstance().shootBullet(world, "upLeft");
            }
            else if (gameKeys.player_shoot_right.getKeyState()) {
                // if up and right
                BulletController.getInstance().shootBullet(world,"upRight");
            }
            else {
                // if up
                BulletController.getInstance().shootBullet(world,"up");
            }
        }
        else if (gameKeys.player_shoot_down.getKeyState()) {
            if (gameKeys.player_shoot_left.getKeyState()) {
                // if down and left
                BulletController.getInstance().shootBullet(world,"downLeft");
            }
            else if (gameKeys.player_shoot_right.getKeyState()) {
                // if down and right
                BulletController.getInstance().shootBullet(world,"downRight");
            }
            else {
                // if down
                BulletController.getInstance().shootBullet(world,"down");
            }
        }
        else {
            if (gameKeys.player_shoot_left.getKeyState()) {
                // if left
                BulletController.getInstance().shootBullet(world,"left");
            }
            if (gameKeys.player_shoot_right.getKeyState()) {
                // if right
                BulletController.getInstance().shootBullet(world,"right");
            }
        }
    }
    
    @Override
    public void stop(World world) {

    }

    @Override
    public void uninstall() {

    }    
}
