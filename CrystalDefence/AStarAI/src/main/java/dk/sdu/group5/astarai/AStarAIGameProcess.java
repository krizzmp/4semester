package dk.sdu.group5.astarai;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.lookup.ServiceProvider;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@ServiceProvider(service = IGameProcess.class)
public class AStarAIGameProcess implements IGameProcess {

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
        Optional<Entity> player = getFirstPlayer(entities);
        player.ifPresent(p -> tower.ifPresent(t -> forEachEnemy(entities, e -> {
            // TODO: 12/04/16 Perhaps change barriers to avoidables
            List<Entity> barriers = entities.stream().filter(x -> x != e && isBarrier(x)).collect(Collectors.toList());
            Vec enemyPos = getEntityPosition(e);
            Vec direction = PathFinder.getDirection(e, barriers, p);
            Vec entityVel = direction.unit().times(e.getSpeed() * delta); // (t - e)/(|t-e|) * speed * delta
            Vec newPoint = enemyPos.plus(entityVel);
            e.setX((float) newPoint.getX());
            e.setY((float) newPoint.getY());
        })));
    }

    // TODO: 12/04/16 Perhaps change name to shouldAvoid?
    // TODO: 12/04/16 Should bullets be considered a barrier?
    private Boolean isBarrier(Entity entity) {
        return entity.getType() != EntityType.PLAYER;
    }

    private Vec getEntityPosition(Entity entity) {
        return new Vec(entity.getX(), entity.getY());
    }


    private Optional<Entity> getFirstTower(List<Entity> entities) {
        return entities.stream().filter(e -> e.getType() == EntityType.TOWER).findFirst();
    }

    private Optional<Entity> getFirstPlayer(List<Entity> entities) {
        return entities.stream().filter(e -> e.getType() == EntityType.PLAYER).findFirst();
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
