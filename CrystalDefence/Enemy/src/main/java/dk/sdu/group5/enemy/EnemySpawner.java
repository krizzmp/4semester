package dk.sdu.group5.enemy;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.Spawner;

public class EnemySpawner extends Spawner {
    @Override
    public int getDifficulty() {
        return 100;
    }

    @Override
    public Entity spawn() {
        Entity entity = new Entity();
        entity.setType(EntityType.ENEMY);
        entity.setHealth(3);
        entity.setSpeed(40);
        entity.setTexture("gridPattern.png");
        entity.addProperty("collidable");
        entity.addProperty("tangible");
        entity.addProperty("damageable");

        return entity;
    }
}
