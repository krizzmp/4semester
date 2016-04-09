package dk.sdu.group5.enemy;

import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.SpawnController;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.lookup.ServiceProvider;


@ServiceProvider(service = IGameProcess.class)
public class EnemyGameProcess implements IGameProcess {

    private EnemySpawner enemySpawner;

    @Override
    public void install() {

    }

    @Override
    public void start(World world) {
        enemySpawner = new EnemySpawner();
        SpawnController.getInstance().register(enemySpawner);
    }

    @Override
    public void update(World world, float delta) {
        world.getEntities().stream().filter(e -> e.getType() == EntityType.ENEMY)
                .forEach((e) -> world.getCollisionDetector().collides(e, world.getEntities()).stream()
                        .filter(collidedEntity -> collidedEntity.getType() == EntityType.PLAYER)
                        .forEach(collidedEntity -> collidedEntity.setHealth(collidedEntity.getHealth() - 1)));
    }

    @Override
    public void stop(World world) {
        SpawnController.getInstance().unRegister(enemySpawner);
    }

    @Override
    public void uninstall() {

    }
}
