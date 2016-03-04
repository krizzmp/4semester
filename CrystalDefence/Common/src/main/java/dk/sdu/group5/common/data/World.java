package dk.sdu.group5.common.data;

import java.util.LinkedHashSet;
import java.util.Set;

public class World
{
    // TODO: Add CollisionDetector

    Set<Entity> entities;

    public World()
    {
        entities = new LinkedHashSet<>();
    }

    public void AddEntity(Entity entity) throws Exception
    {
        // TO-BE-RESOLVED: Silent handling, throw exception or return boolean
        // if duplicate found.
        if (!entities.add(entity))
        {
            throw new Exception("A duplicate entry already exists matching the provided entity!");
        }
    }

    public void RemoveEntity(Entity entity) throws Exception
    {
        // TO-BE-RESOLVED: Silent handling, throw exception or return boolean
        // if none found.
        if (!entities.remove(entity))
        {
            throw new Exception("No entry already exists matching the provided entity!");
        }
    }
}
