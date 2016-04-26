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
import java.util.Random;

@ServiceProvider(service = IGameProcess.class)
public class MapGameProcess implements IGameProcess {
    private List<Entity> environment;
    private int MAX_AMOUNT = 10;

    @Override
    public void install() {
    }

    @Override
    public void start(World world) {
        environment = new ArrayList<>();

        Random rng = new Random();

        int amount = rng.nextInt(MAX_AMOUNT);
        for (int i = 0; i < amount; i++) {
            Entity entity = new Entity();
            entity.setType(EntityType.ENVIRONMENT);
            entity.setCollider(new SquareCollider(false, new AABB(-16, -16, 32, 32)));
            entity.setTexture("towerTexture.png");
            entity.addProperty("tangible");
            entity.addProperty("collidable");
            entity.addProperty("static");
            // TODO: 26/04/16 Get actual width and height.
            entity.setX(rng.nextInt(800));
            entity.setY(rng.nextInt(400));
            world.addEntity(entity);
        }
    }

    @Override
    public void update(World world, float delta) {
    }

    @Override
    public void stop(World world) {
    }

    @Override
    public void uninstall() {

    }

}
