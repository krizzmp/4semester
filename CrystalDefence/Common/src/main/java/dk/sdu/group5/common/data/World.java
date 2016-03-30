package dk.sdu.group5.common.data;

import java.util.*;

public class World {
    private final CollisionDetector collisionDetector;
    private final Map<Entity, List<Entity>> collisions;
    private final List<Entity> entities;
    private Difficulty difficulty;
    WeaponType weaponType;

    public World() {
        collisionDetector = new CollisionDetector();
        collisions = new HashMap<>();
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

    public void setWeaponType(WeaponType type) {
        weaponType = type;
        BulletController.getInstance().setShootInterval(type);
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }


    public CollisionDetector getCollisionDetector() {
        return collisionDetector;
    }

    public void addCollision(Entity entity1, Entity entity2) {
        if (!collisions.containsKey(entity1)) {
            collisions.put(entity1, new ArrayList<>());
        }
        collisions.get(entity1).add(entity2);

        if (!collisions.containsKey(entity2)) {
            collisions.put(entity2, new ArrayList<>());
        }
        collisions.get(entity2).add(entity1);
    }

    public List<Entity> getCollisions(Entity srcEntity) {
        return collisions.get(srcEntity);
    }

    public void clearCollisions() {
        collisions.values().stream().forEach(List::clear);
    }

    public Difficulty getDifficulty()
    {
        return difficulty;
    }
}
