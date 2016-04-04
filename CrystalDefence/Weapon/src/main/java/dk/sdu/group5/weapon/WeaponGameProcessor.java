
package dk.sdu.group5.weapon;

import dk.sdu.group5.common.data.BulletController;
import dk.sdu.group5.common.data.GameKeys;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.lookup.ServiceProvider;

//TODO 04/04/16 Bullet entities should be created in here, and the BulletController update should be in its own component.
//Or the update should be in here.
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
        
        if(GameKeys.getInstance().player_shoot.getKeyState()) {
            BulletController.getInstance().shootBullet(world);
        }
    }

    @Override
    public void stop(World world) {

    }

    @Override
    public void uninstall() {

    }    
}
