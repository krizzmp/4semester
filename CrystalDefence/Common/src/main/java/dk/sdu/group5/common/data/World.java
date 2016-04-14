package dk.sdu.group5.common.data;

import java.util.LinkedList;
import java.util.List;

public class World {
    private final List<Entity> entities;
    private Difficulty difficulty;
    private WeaponType weaponType;

    private World()
    {
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
