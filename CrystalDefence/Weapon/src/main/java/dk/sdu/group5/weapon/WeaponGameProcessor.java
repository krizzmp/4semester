
package dk.sdu.group5.weapon;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.GameKeys;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IGameProcess.class)
public class WeaponGameProcessor implements IGameProcess {

    private Entity bullet;
    
    
    @Override
    public void install() {

    }

    @Override
    public void start(World world) {
        bullet.setType(EntityType.BULLET);
        bullet.setLives(1);
        bullet.setTexture("bulletTexture.png");
        
    }

    @Override
    public void update(World world, float delta) {
        if(GameKeys.getInstance().player_shoot.getKeyState()) {
        }
    }

    @Override
    public void stop(World world) {

    }

    @Override
    public void uninstall() {

    }
    
}
