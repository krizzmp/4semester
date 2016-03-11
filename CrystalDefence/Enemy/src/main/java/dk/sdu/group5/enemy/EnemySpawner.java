package dk.sdu.group5.enemy;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.Spawner;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EnemySpawner extends Spawner {
    @Override
    public int getDifficulty() {
        return 100;
    }

    @Override
    public Entity spawn() {
        Entity entity = new Entity();
        entity.setType(EntityType.ENEMY);
        entity.setLives(3);
        entity.setTexture("gridPattern.png");
        try {
            entity.addProperty("collidable");
            entity.addProperty("tangible");
            entity.addProperty("damageable");
        } catch (Exception e) {
            Logger.getLogger(EnemySpawner.class.getName()).log(Level.SEVERE, null, e);

        }

        return entity;
    }
}
