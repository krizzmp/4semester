package dk.sdu.group5.common.data;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SpawnData
{
    private Entity prototypeEntity;
    private int difficulty;
    private final Collection<Entity> spawnedEntities = new ConcurrentLinkedQueue<>();

    public Entity getPrototypeEntity()
    {
        return prototypeEntity;
    }

    public void setPrototypeEntity(Entity prototypeEntity)
    {
        this.prototypeEntity = prototypeEntity;
    }

    public int getDifficulty()
    {
        return difficulty;
    }

    public void setDifficulty(int difficulty)
    {
        this.difficulty = difficulty;
    }

    public Collection<Entity> getSpawnedEntities() {
        return spawnedEntities;
    }
}
