package dk.sdu.group5.astarai;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.lookup.ServiceProvider;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@ServiceProvider(service = IGameProcess.class)
public class AStarAIGameProcess implements IGameProcess {

    @Override
    public void install() {

    }

    @Override
    public void start(World world) {

    }


    private Vec vectorOf(Entity enemy) {
        return new Vec(enemy.getX(), enemy.getY());
    }
    @Override
    public void update(World world, float delta) {
        List<Entity> entities = world.getEntities();
        Optional<Entity> tower = getFirstTower(entities);
        tower.ifPresent(t -> forEachEnemy(entities, e -> {
            Vec e1 = vectorOf(e);
            Vec t1 = vectorOf(t);
            Vec direction = t1.minus(e1).unit();
            Vec a = direction.times(e.getSpeed() * delta); // (t - e)/(|t-e|) * speed * delta
            Vec newPoint = e1.plus(a);
            e.setX((float)newPoint.x);
            e.setY((float)newPoint.y);
        }));
    }
    private Optional<Entity> getFirstTower(List<Entity> entities) {
        return entities.stream().filter(e -> e.getType() == EntityType.TOWER).findFirst();
    }

    private void forEachEnemy(List<Entity> entities, Consumer<Entity> entityConsumer) {
        entities.stream().filter(e -> e.getType() == EntityType.ENEMY).forEach(entityConsumer);
    }

    @Override
    public void stop(World world) {

    }

    @Override
    public void uninstall() {

    }

}
