package dk.sdu.group5.common.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CollisionDetector {

    public List<Entity> collides(Entity entity, List<Entity> entities){
        List<Entity> collidableEntities = entities.stream().filter(e -> e.getCollider() != null)
                .collect(Collectors.toList());

        List<Entity> collidedEntities = new ArrayList<>();

        for (Entity collidableEntity : collidableEntities) {
            if (collidableEntity == entity
                    || !collides(entity, collidableEntity)) {
                continue;
            }

            collidedEntities.add(collidableEntity);
        }

        return collidedEntities;
    }

    public boolean collides(Entity e1, Entity e2){
        return intersects(e1.getCollider(),e2.getCollider(),e1,e2);
    }

    private boolean intersects(BoxCollider c1, BoxCollider c2, Entity e1, Entity e2){
        return !(c1.getAABB().getMinX() + e1.getX() > c2.getAABB().getMaxX() + e2.getX()
                || c1.getAABB().getMaxX() + e1.getX() < c2.getAABB().getMinX() + e2.getX()
                || c1.getAABB().getMinY() + e1.getY() > c2.getAABB().getMaxY() + e2.getY()
                || c1.getAABB().getMaxY() + e1.getY() < c2.getAABB().getMinY() + e2.getY());
    }
}