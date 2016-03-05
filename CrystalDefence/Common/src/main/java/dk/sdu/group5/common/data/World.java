package dk.sdu.group5.common.data;

import java.util.LinkedHashSet;
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

    public void AddEntity(Entity entity) throws Exception
    {
        entities.add(entity));

    public World()
    {
        entities = new LinkedHashSet<>();
    }

    public void AddEntity(Entity entity) throws Exception
    {
        entities.add(entity));
    }

    public void RemoveEntity(Entity entity) throws Exception
    {
       entities.remove(entity));
    }
}
