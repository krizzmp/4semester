package dk.sdu.group5.astarai;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.lookup.ServiceProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

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
            for (Entity x : entities) {
                if (x != e && isAvoidable(target, x)) {
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
        if (targetEntity.getType() == EntityType.PLAYER) {
            return sourceEntity.getType() != EntityType.PLAYER
                    && sourceEntity.getType() != EntityType.BULLET
                    && sourceEntity.getType() != EntityType.ENEMY;
        } else if (targetEntity.getType() == EntityType.TOWER) {
            return sourceEntity.getType() != EntityType.TOWER
                    && sourceEntity.getType() != EntityType.PLAYER
                    && sourceEntity.getType() != EntityType.BULLET
                    && sourceEntity.getType() != EntityType.ENEMY;
        }

        return false;
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
