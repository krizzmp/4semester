package dk.sdu.group5.common.data;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class World {
    private final Map<String, Entity> entities;
    private final Map<Entity, List<Entity>> collisions;
    private final Collection<SpawnData> spawnData;
    private Difficulty difficulty;
    private WeaponType weaponType;
    private boolean gameover = false;
    private String backgroundTexturePath;
    private int displayResolutionWidth;
    private int displayResolutionHeight;
    private GameKeys gameKeys;
    private GameKeys oldGameKeys;

    public World(Difficulty difficulty) {
        this();
        this.difficulty = difficulty;
    }

    private World() {
        entities = new ConcurrentHashMap<>();
        collisions = new HashMap<>();
        spawnData = new ConcurrentLinkedQueue<>();
    }

    public Collection<SpawnData> getSpawnData() {
        return spawnData;
    }

    public GameKeys getGameKeys() {
        return gameKeys;
    }

    public void setGameKeys(GameKeys gameKeys) {
        this.gameKeys = gameKeys;
    }

    public GameKeys getOldGameKeys() {
        return oldGameKeys;
    }

    public void setOldGameKeys(GameKeys oldGameKeys) {
        this.oldGameKeys = oldGameKeys;
    }

    public int getDisplayResolutionWidth() {
        return displayResolutionWidth;
    }

    public void setDisplayResolutionWidth(int displayResolutionWidth) {
        this.displayResolutionWidth = displayResolutionWidth;
    }

    public int getDisplayResolutionHeight() {
        return displayResolutionHeight;
    }

    public void setDisplayResolutionHeight(int displayResolutionHeight) {
        this.displayResolutionHeight = displayResolutionHeight;
    }

    public boolean isGameover() {
        return gameover;
    }

    public void setGameover(boolean gameover) {
        this.gameover = gameover;
    }


    public void addEntity(Entity entity) {
        entities.put(entity.getID(), entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity.getID());
    }

    public Collection<Entity> getEntities() {
        return entities.values();
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

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(WeaponType type) {
        weaponType = type;
    }

    public String getBackgroundTexturePath() {
        return backgroundTexturePath;
    }

    public void setBackgroundTexturePath(String backgroundTexturePath) {
        this.backgroundTexturePath = backgroundTexturePath;
    }
}
