package dk.sdu.group5.enemy;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.SpawnController;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.services.IGameProcess;
import java.util.ArrayList;
import java.util.List;
import org.openide.util.lookup.ServiceProvider;


@ServiceProvider(service = IGameProcess.class)
public class EnemyGameProcess implements IGameProcess {

    private EnemySpawner enemySpawner;
    private List<Entity> listOfDeadEnemies;

    @Override
    public void start(World world) {
        enemySpawner = new EnemySpawner();
        SpawnController.getInstance().register(enemySpawner);
        listOfDeadEnemies = new ArrayList<>();
    }

    @Override
    public void update(World world, float delta) {
        world.getEntities().stream().filter(e -> e.getType() == EntityType.ENEMY)
                .forEach(e -> world.getCollisions(e).stream()
                        .filter(collidedEntity -> collidedEntity.getType() == EntityType.PLAYER || collidedEntity.getType() == EntityType.TOWER)
                        .forEach(collidedEntity -> collidedEntity.setHealth(collidedEntity.getHealth() - 1)));

        // TODO: 12/04/16 Missing Enemy-Tower collision check

        // Check if enemy has no more hp
        world.getEntities().stream().filter(e -> e.getType() == EntityType.ENEMY)
                .filter(e -> e.getHealth() <= 0).forEach(e -> listOfDeadEnemies.add(e));

        listOfDeadEnemies.stream().forEach(e -> world.removeEntity(e));
        listOfDeadEnemies.clear();
    }

    @Override
    public void stop(World world) {
        SpawnController.getInstance().unRegister(enemySpawner);
    }

}
