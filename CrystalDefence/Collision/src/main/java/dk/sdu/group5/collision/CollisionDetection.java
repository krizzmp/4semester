package dk.sdu.group5.collision;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.services.ICollisionService;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = ICollisionService.class)
public class CollisionDetection implements ICollisionService{
    
    public CollisionDetection() {
        
    }

    @Override
    public boolean Collides(Entity e1, Entity e2) {
        return false;
    }
    
}
