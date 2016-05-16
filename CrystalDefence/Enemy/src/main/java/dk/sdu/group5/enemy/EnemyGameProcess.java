package dk.sdu.group5.enemy;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.SpawnData;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.data.collision.AABB;
import dk.sdu.group5.common.data.collision.SquareCollider;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.lookup.ServiceProvider;


@ServiceProvider(service = IGameProcess.class)
public class EnemyGameProcess implements IGameProcess {

    private SpawnData spawnData;

    @Override
    public void start(World world) {
        spawnData = new SpawnData();
        spawnData.setPrototypeEntity(createPrototypeEntity());
        spawnData.setDifficulty(100);

        world.getSpawnData().add(spawnData);
    }

    @Override
    public void update(World world, float delta) {
        world.getEntities().stream().filter(e -> e.getType() == EntityType.ENEMY)
                .forEach(e -> world.getCollisions(e).stream()
                        .filter(collidedEntity -> collidedEntity.getType() == EntityType.PLAYER || collidedEntity.getType() == EntityType.TOWER)
                        .forEach(collidedEntity -> collidedEntity.setHealth(collidedEntity.getHealth() - 1)));

        // Remove enemy if enemy has no more hp
        world.getEntities().stream().filter(e -> e.getType() == EntityType.ENEMY)
                .filter(e -> e.getHealth() <= 0).forEach(e -> world.removeEntity(e));
    }

    @Override
    public void stop(World world) {
        world.getSpawnData().remove(spawnData);
    }

    private Entity createPrototypeEntity() {
        Entity entity = new Entity();
        entity.setType(EntityType.ENEMY);
        entity.setHealth(3);
        entity.setSpeed(40);
        entity.setTexturePath("enemyTextureb.png");
        entity.setCollider(new SquareCollider(false, new AABB(-13, -15, 26, 30)));
        entity.addProperty("collidable");
        entity.addProperty("tangible");
        entity.addProperty("damageable");
        return entity;
    }

}
