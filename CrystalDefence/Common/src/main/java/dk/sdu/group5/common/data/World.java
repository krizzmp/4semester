package dk.sdu.group5.common.data;

import dk.sdu.group5.common.data.collision.CollisionDetector;
import dk.sdu.group5.common.data.collision.CollisionHandler;

import java.util.LinkedList;
import java.util.List;

public class World {
    private final CollisionHandler collisionHandler;
    private final CollisionDetector detector;
    private final List<Entity> entities;
    private Difficulty difficulty;
    WeaponType weaponType;

    public World()
    {
        collisionHandler = new CollisionHandler();
        detector = new CollisionDetector();
        entities = new LinkedList<>();
        weaponType = WeaponType.PISTOL;
    }

    public World(Difficulty difficulty) {
        this();
        this.difficulty = difficulty;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public CollisionHandler getCollisionHandler()
    {
        return collisionHandler;
    }

    public CollisionDetector getCollisionDetector() {
        return detector;
    }

    public Difficulty getDifficulty()
    {
        return difficulty;
    }

    public void setWeaponType(WeaponType type) {
        weaponType = type;
        BulletController.getInstance().setShootInterval(type);
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }
}
