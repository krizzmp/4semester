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
            List<Entity> barriers = entities.stream().filter(x->isBarrier(e,x)).collect(Collectors.toList());
            Vec e1 = vectorOf(e);
            Vec vec = PathFinder.getDirection(e,barriers,p);
            Vec a = vec.unit().times(e.getSpeed() * delta); // (t - e)/(|t-e|) * speed * delta
            Vec newPoint = e1.plus(a);
            e.setX((float) newPoint.x);
            e.setY((float) newPoint.y);
        })));
    }

    private Boolean isBarrier(Entity self, Entity entity) {
        if(entity.getType() == EntityType.TOWER){
            return true;
        }
        if(entity.getType() == EntityType.PLAYER){
            return false;
        }
        return entity != self;
    }

    private Vec vectorOf(Entity enemy) {
        return new Vec(enemy.getX(), enemy.getY());
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
