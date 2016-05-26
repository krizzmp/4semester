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
    public void start(World world) {
        tower = createTowerEntity(world.getDisplayResolutionWidth() / 2f,
                world.getDisplayResolutionHeight() / 2f);
        world.addEntity(tower);
    }

    @Override
    public void update(World world, float delta) {
        if (tower.getHealth() <= 0) {
            world.setGameover(true);
        }
    }

    private Entity createTowerEntity(float x, float y) {
        Entity entity = new Entity();
        entity.setType(EntityType.TOWER);
        entity.setHealth(50);
        entity.setTexturePath("towerTextureb.png");
        entity.setCollider(new SquareCollider(false, new AABB(-32, -35.5f, 64, 71)));
        entity.addProperty("tangible");
        entity.addProperty("collidable");
        entity.addProperty("static");
        entity.addProperty("damageable");
        entity.setX(x);
        entity.setY(y);
        return entity;
    }

    @Override
    public void stop(World world) {
        world.removeEntity(tower);
    }

}
