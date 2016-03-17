package dk.sdu.group5.common.data;

import java.util.LinkedList;
import java.util.List;

public class World {
    private CollisionDetector collisionDetector;
    private List<Entity> entities;
    private Difficulty difficulty;

    public World()
    {
        entities = new LinkedList<>();
    }

    public World(Difficulty difficulty) {
        this();
        this.difficulty = difficulty;

        collisionDetector = new CollisionDetector();
    }

    public void AddEntity(Entity entity) {
        entities.add(entity);
    }

    public void RemoveEntity(Entity entity)
    {
        entities.remove(entity);
    }

    public List<Entity> getEntities()
    {
        return entities;
    }

    public CollisionDetector getCollisionDetector()
    {
        return collisionDetector;
    }

    public Difficulty getDifficulty()
    {
        return difficulty;
    }
}
