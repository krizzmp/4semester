package dk.sdu.group5.common.data;

public class SpawnData 
{
    private Entity prototypeEntity;
    private int difficulty;

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
}
