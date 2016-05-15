package dk.sdu.group5.tower;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.lookup.ServiceProvider;

import java.util.Collection;
import java.util.Optional;

@ServiceProvider(service = IGameProcess.class)
public class TowerGameProcess implements IGameProcess {

    @Override
    public void start(World world) {
    }

    @Override
    public void update(World world, float delta) {
        getFirstTower(world.getEntities()).ifPresent(t -> {
            if (t.getHealth() < 0) {
                world.setGameover(true);
            }
        });
    }

    private Optional<Entity> getFirstTower(Collection<Entity> entities) {
        return entities.stream().filter(e -> e.getType() == EntityType.TOWER).findFirst();
    }

    @Override
    public void stop(World world) {
    }

}
