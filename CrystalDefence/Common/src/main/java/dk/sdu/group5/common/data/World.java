package dk.sdu.group5.common.data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class World {
    private final List<Entity> entities;
    private final Map<Entity, List<Entity>> collisions;
    private Difficulty difficulty;
    private WeaponType weaponType;
    private boolean gameover = false;

    public boolean isGameover() {
        return gameover;
    }

    public void setGameover(boolean gameover) {
        this.gameover = gameover;
    }



    private World()
    {
        entities = new LinkedList<>();
        collisions = new HashMap<>();
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

    public void addCollision(Entity srcEntity, Entity targetEntity) {
        if (!collisions.containsKey(srcEntity)) {
            collisions.put(srcEntity, new LinkedList<>());
        }

        collisions.get(srcEntity).add(targetEntity);
    }

    public void clearCollisions() {
        collisions.clear();
    }

    public List<Entity> getCollisions(Entity entity) {
        return collisions.getOrDefault(entity, new LinkedList<>());
    }

    public Difficulty getDifficulty()
    {
        return difficulty;
    }

    public void setWeaponType(WeaponType type) {
        weaponType = type;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }
}
