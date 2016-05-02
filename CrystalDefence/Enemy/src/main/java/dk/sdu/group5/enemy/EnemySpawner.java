package dk.sdu.group5.enemy;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.Spawner;
import dk.sdu.group5.common.data.collision.AABB;
import dk.sdu.group5.common.data.collision.SquareCollider;

class EnemySpawner extends Spawner {
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
        entity.setTexturePath("enemyTexture.png");
        entity.setCollider(new SquareCollider(false, new AABB(-16, -16, 32, 32)));
        entity.addProperty("collidable");
        entity.addProperty("tangible");
        entity.addProperty("damageable");

        return entity;
    }
}
