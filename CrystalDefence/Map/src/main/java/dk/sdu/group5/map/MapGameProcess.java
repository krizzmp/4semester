package dk.sdu.group5.map;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.data.collision.AABB;
import dk.sdu.group5.common.data.collision.SquareCollider;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.lookup.ServiceProvider;

import java.util.ArrayList;
import java.util.List;

@ServiceProvider(service = IGameProcess.class)
public class MapGameProcess implements IGameProcess {
    private List<Entity> environment;

    @Override
    public void install() {
    }

    @Override
    public void start(World world) {
        environment = new ArrayList<>();

        world.setBackgroundTexturePath("mapTexture.png");

        environment.add(createEnvironmentEntity(448, 197));
        environment.add(createEnvironmentEntity(216, 19));
        environment.add(createEnvironmentEntity(288, 222));
        environment.add(createEnvironmentEntity(337, 336));
        // TODO: 26/04/16 Get actual width and height.
        environment.add(createTowerEntity(350, 280));

        for (Entity environment1 : environment) {
            world.addEntity(environment1);
        }
    }

    private Entity createEnvironmentEntity(float x, float y) {
        Entity entity = new Entity();
        entity.setType(EntityType.ENVIRONMENT);
        entity.setCollider(new SquareCollider(false, new AABB(-16, -16, 32, 32)));
        entity.setTexturePath("gridPattern.png");
        entity.addProperty("tangible");
        entity.addProperty("collidable");
        entity.addProperty("static");
        entity.setX(x);
        entity.setY(y);
        return entity;
    }

    private Entity createTowerEntity(float x, float y) {
        Entity entity = new Entity();
        entity.setType(EntityType.TOWER);
        entity.setHealth(3);
        entity.setTexturePath("towerTexture.png");
        entity.setCollider(new SquareCollider(false, new AABB(-16, -16, 32, 32)));
        entity.addProperty("tangible");
        entity.addProperty("collidable");
        entity.addProperty("static");
        entity.addProperty("damageable");
        entity.setX(x);
        entity.setY(y);
        return entity;
    }

    @Override
    public void update(World world, float delta) {
    }

    @Override
    public void stop(World world) {
        environment.stream().forEach((entity) -> {
            world.removeEntity(entity);
        });
    }

    @Override
    public void uninstall() {

    }

}
