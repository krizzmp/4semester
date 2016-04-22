package dk.sdu.group5.collision;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.Posf2d;
import dk.sdu.group5.common.data.collision.ICollider;
import dk.sdu.group5.common.services.ICollisionDetectorService;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = ICollisionDetectorService.class)
public class CollisionDetectorService implements ICollisionDetectorService {
    @Override
    public boolean collides(Entity e1, Entity e2) {
        return intersects(e1.getCollider(), e2.getCollider(), e1, e2);
    }

    private boolean intersects(ICollider col1, ICollider col2, Posf2d col1Pos, Posf2d col2Pos) {
        return !(col1.getBounds().getOriginX() + col1Pos.getX() > col2.getBounds().getOriginX() + col2.getBounds().getWidth() + col2Pos.getX()
                || col1.getBounds().getOriginX() + col1.getBounds().getWidth() + col1Pos.getX() < col2.getBounds().getOriginX() + col2Pos.getX()
                || col1.getBounds().getOriginY() + col1Pos.getY() > col2.getBounds().getOriginY() + col2.getBounds().getHeight() + col2Pos.getY()
                || col1.getBounds().getOriginY() + col1.getBounds().getHeight() + col1Pos.getY() < col2.getBounds().getOriginY() + col2Pos.getY());
    }

    private boolean intersects(ICollider col1, ICollider col2, Entity e1, Entity e2) {
        return intersects(col1, col2, new Posf2d(e1.getX(), e1.getY()), new Posf2d(e2.getX(), e2.getY()));
    }

}
