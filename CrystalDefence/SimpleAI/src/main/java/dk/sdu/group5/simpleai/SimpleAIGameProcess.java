package dk.sdu.group5.simpleai;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.lookup.ServiceProvider;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@ServiceProvider(service = IGameProcess.class)
public class SimpleAIGameProcess implements IGameProcess {

    @Override
    public void install() {

    }

    @Override
    public void start(World world) {

    }

    @Override
    public void update(World world, float delta) {
        List<Entity> entities = world.getEntities();
        Optional<Entity> tower = getFirstTower(entities);
        tower.ifPresent(t -> forEachEnemy(entities, e -> {
            Vector2d e1 = vectorOf(e);
            Vector2d t1 = vectorOf(t);
            Vector2d a = t1.minus(e1).unit().times(e.getSpeed() * delta); // (t - e)/(|t-e|) * speed * delta
            Vector2d newPoint = e1.plus(a);
            e.setX(newPoint.x);
            e.setY(newPoint.y);
        }));
    }

    private Optional<Entity> getFirstTower(List<Entity> entities) {
        return entities.stream().filter(e -> e.getType() == EntityType.TOWER).findFirst();
    }

    private void forEachEnemy(List<Entity> entities, Consumer<Entity> entityConsumer) {
        entities.stream().filter(e -> e.getType() == EntityType.ENEMY).forEach(entityConsumer);
    }

    private Vector2d vectorOf(Entity enemy) {
        return new Vector2d(enemy.getX(), enemy.getY());
    }

    @Override
    public void stop(World world) {

    }

    @Override
    public void uninstall() {

    }

}
