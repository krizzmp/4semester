package dk.sdu.group5.tower;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.data.collision.AABB;
import dk.sdu.group5.common.data.collision.SquareCollider;
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
        tower.setHealth(3);
        tower.setX(350);
        tower.setY(280);
        tower.setTexture("gridPattern.png");
        tower.setCollider(new SquareCollider(false, new AABB(-16, -16, 32, 32)));
        tower.addProperty("tangible");
        tower.addProperty("collidable");
        tower.addProperty("static");
        tower.addProperty("damageable");
        world.addEntity(tower);
    }

    @Override
    public void update(World world, float delta) {
    }

    @Override
    public void stop(World world) {
        world.removeEntity(tower);

    }

    @Override
    public void uninstall() {

    }

}
