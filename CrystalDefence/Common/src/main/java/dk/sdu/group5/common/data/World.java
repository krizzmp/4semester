package dk.sdu.group5.common.data;

import java.util.LinkedList;
import java.util.List;

public class World
{
    // TODO: Add CollisionDetector

    CollisionDetector collisionDetector;
    List<Entity> entities;
    Difficulty difficulty;

    public World()
    {
        entities = new LinkedList<>();
    }

    public World(Difficulty difficulty) {
        this();
        this.difficulty = difficulty;
    }

    public void AddEntity(Entity entity)
    {
        entities.add(entity);
    }

    public void RemoveEntity(Entity entity)
    {
       entities.remove(entity);
    }

    public List<Entity> getEntities() {
        return entities;
    }
}
