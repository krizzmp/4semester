package dk.sdu.group5.common.data.collision;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.Posf2d;

import java.util.List;
import java.util.stream.Collectors;

public class CollisionDetector {
    public List<Entity> collides(Entity srcEntity, List<Entity> entities) {
        List<Entity> collidableEntities = entities.stream().filter(e -> e.getCollider() != null)
                .collect(Collectors.toList());

        return collidableEntities.stream().filter(collidableEntity -> collidableEntity != srcEntity
                && collides(srcEntity, collidableEntity)).collect(Collectors.toList());
    }

    public boolean collides(Entity e1, Entity e2) {
        return intersects(e1.getCollider(),e2.getCollider(),e1,e2);
    }

    private boolean intersects(ICollider col1, ICollider col2, Posf2d col1Pos, Posf2d col2Pos) {
        return !(col1.getBounds().getMinX() + col1Pos.getX() > col2.getBounds().getMaxX() + col2Pos.getX()
                || col1.getBounds().getMaxX() + col1Pos.getX() < col2.getBounds().getMinX() + col2Pos.getX()
                || col1.getBounds().getMinY() + col1Pos.getY() > col2.getBounds().getMaxY() + col2Pos.getY()
                || col1.getBounds().getMaxY() + col1Pos.getY() < col2.getBounds().getMinY() + col2Pos.getY());
    }

    private boolean intersects(ICollider col1, ICollider col2, Entity e1, Entity e2) {
        return intersects(col1, col2, new Posf2d(e1.getX(), e1.getY()), new Posf2d(e2.getX(), e2.getY()));
    }
}