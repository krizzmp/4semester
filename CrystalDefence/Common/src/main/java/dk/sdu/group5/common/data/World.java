package dk.sdu.group5.common.data;

import java.util.LinkedList;
import java.util.List;

public class World
{
    // TODO: Add CollisionDetector

    CollisionDetector collisionDetector;
    List<Entity> entities;
    Difficulty difficulty;
    // TODO 04/04/16 Should probably be in Player.
    WeaponType weaponType;
    
    
    public World()
    {
        entities = new LinkedList<>();
        weaponType = WeaponType.PISTOL;
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
    
    public void setWeaponType(WeaponType type) {
        weaponType = type;
        BulletController.getInstance().setShootInterval(type);
    }
    
    public WeaponType getWeaponType() {
        return weaponType;
    }
    
}
