package dk.sdu.group5.tower;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IGameProcess.class)
public class TowerGameProcess implements IGameProcess {
    private Entity tower;

    @Override
    public void install() {

    }

    @Override
    public void start(World world) {
        tower = new Entity();
        tower.setType(EntityType.TOWER);
        tower.setLives(3);
        tower.setX(350);
        tower.setY(280);
        tower.setTexture("gridPattern.png");
        tower.addProperty("collidable");
        tower.addProperty("tangible");
        tower.addProperty("damageable");
        world.AddEntity(tower);
    }

    @Override
    public void update(World world, float delta) {
        // Render stuff
        // Collision stuff
    }

    @Override
    public void stop(World world) {
        world.RemoveEntity(tower);

    }

    @Override
    public void uninstall() {

    }

}
