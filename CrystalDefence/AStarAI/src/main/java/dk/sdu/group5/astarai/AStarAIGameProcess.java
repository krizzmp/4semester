package dk.sdu.group5.astarai;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.services.IGameProcess;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IGameProcess.class)
public class AStarAIGameProcess implements IGameProcess {

    private final float MIN_PLAYER_DISTANCE = 150f;

    @Override
    public void start(World world) {

    }

    @Override
    public void update(World world, float delta) {
        Collection<Entity> entities = world.getEntities();
        Optional<Entity> tower = getFirstTower(entities);
        Optional<Entity> player = getFirstPlayer(entities);

        if (!player.isPresent() && !tower.isPresent()) {
            return;
        }

        forEachEnemy(entities, e -> {
            Entity target = null;
            if (tower.isPresent()) {
                target = tower.get();
            }
            
            Vec enemyPos = getEntityPosition(e);
            if (player.isPresent()
                    && (!tower.isPresent()
                    || enemyPos.minus(getEntityPosition(player.get())).length()
                    <= MIN_PLAYER_DISTANCE)) {
                target = player.get();
            }

            if (target == null) {
                return;
            }

            List<Entity> avoidables = new ArrayList<>();
            for (Entity x : world.getEntities()) {
                if (x != e && isAvoidable(x,target)) {
                    avoidables.add(x);
                }
            }
            
            Vec direction = PathFinder.getDirection(e, avoidables, target);
            Vec entityVel = direction.unit().times(e.getSpeed() * delta); // (t - e)/(|t-e|) * speed * delta
            Vec newPoint = enemyPos.plus(entityVel);
            e.setX((float) newPoint.getX());
            e.setY((float) newPoint.getY());
        });
    }

    private Boolean isAvoidable(Entity sourceEntity, Entity targetEntity) {
        return sourceEntity.getType() != targetEntity.getType();
    }

    private Vec getEntityPosition(Entity entity) {
        return new Vec(entity.getX(), entity.getY());
    }
    

    private Optional<Entity> getFirstTower(Collection<Entity> entities) {
        return entities.stream().filter(e -> e.getType() == EntityType.TOWER).findFirst();
    }

    private Optional<Entity> getFirstPlayer(Collection<Entity> entities) {
        return entities.stream().filter(e -> e.getType() == EntityType.PLAYER).findFirst();
    }

    private void forEachEnemy(Collection<Entity> entities, Consumer<Entity> entityConsumer) {
        entities.stream().filter(e -> e.getType() == EntityType.ENEMY).forEach(entityConsumer);
    }

    @Override
    public void stop(World world) {

    }

}
