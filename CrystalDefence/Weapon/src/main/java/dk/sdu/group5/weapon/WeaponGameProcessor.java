
package dk.sdu.group5.weapon;

import dk.sdu.group5.common.data.BulletController;
import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
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
        
        // Shoot in 8 directions
        // TODO: Implement  Note: A whole lot of if statements, can it be done different? - Martin F
        if (gameKeys.player_shoot_up.getKeyState()) {
            if (gameKeys.player_shoot_left.getKeyState()) {
                // if up and left
                BulletController.getInstance().shootBullet(world);
            }
            else if (gameKeys.player_shoot_right.getKeyState()) {
                // if up and right
                BulletController.getInstance().shootBullet(world);
            }
            else {
                // if up
                BulletController.getInstance().shootBullet(world);
            }
        }
        else if (gameKeys.player_shoot_down.getKeyState()) {
            if (gameKeys.player_shoot_left.getKeyState()) {
                // if down and left
                BulletController.getInstance().shootBullet(world);
            }
            else if (gameKeys.player_shoot_right.getKeyState()) {
                // if down and right
                BulletController.getInstance().shootBullet(world);
            }
            else {
                // if down
                BulletController.getInstance().shootBullet(world);
            }
        }
        else {
            if (gameKeys.player_shoot_left.getKeyState()) {
                // if left
                BulletController.getInstance().shootBullet(world);
            }
            if (gameKeys.player_shoot_right.getKeyState()) {
                // if right
                BulletController.getInstance().shootBullet(world);
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
