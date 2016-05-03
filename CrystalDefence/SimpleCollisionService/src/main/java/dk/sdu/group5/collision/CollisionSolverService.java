package dk.sdu.group5.collision;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.data.collision.ICollider;
import dk.sdu.group5.common.services.ICollisionDetectorService;
import dk.sdu.group5.common.services.ICollisionSolverService;
import org.openide.util.Lookup;
import org.openide.util.LookupListener;
import org.openide.util.lookup.ServiceProvider;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

@ServiceProvider(service = ICollisionSolverService.class)
public class CollisionSolverService implements ICollisionSolverService {

    private Lookup.Result<ICollisionDetectorService> collisionDetectorResult;
    private ICollisionDetectorService collisionDetectorService;

    private ReadWriteLock collisionDetectorLock = new ReentrantReadWriteLock();

    public CollisionSolverService() {
        collisionDetectorResult = Lookup.getDefault().lookupResult(ICollisionDetectorService.class);
        collisionDetectorResult.addLookupListener(lookupListenerCollisionDetector);

        updateDetectorService();
    }

    private final LookupListener lookupListenerCollisionDetector = le -> updateDetectorService();

    private void updateDetectorService() {
        collisionDetectorLock.writeLock().lock();
        collisionDetectorService = findDetectorService();
        collisionDetectorLock.writeLock().unlock();
    }

    private ICollisionDetectorService findDetectorService() {
        Optional<? extends ICollisionDetectorService> optionalDetector;
        optionalDetector = collisionDetectorResult.allInstances().stream().findFirst();
        if (optionalDetector.isPresent()) {
            return optionalDetector.get();
        }

        return null;
    }

    public void update(World world) {
        world.clearCollisions();

        collisionDetectorLock.readLock().lock();
        if (collisionDetectorService == null) {
            collisionDetectorLock.readLock().unlock();
            return;
        }
        collisionDetectorLock.readLock().unlock();

        List<Entity> collidableEnts = world.getEntities().stream()
                .filter(e -> e.getCollider() != null)
                .collect(Collectors.toList());

        List<Entity> dynamicEnts = collidableEnts.stream()
                .filter(e -> !e.getProperties().contains("static"))
                .collect(Collectors.toList());

        collisionDetectorLock.readLock().lock();
        if (collisionDetectorService != null) {
            dynamicEnts.stream().forEach(de -> collidableEnts.stream().filter(ce -> de != ce
                    && collisionDetectorService.collides(de, ce)).forEach(ce -> {
                applyImpulse(de, ce);
                world.addCollision(de, ce);
                if (ce.is("static")) {
                    world.addCollision(ce, de);
                }
            }));
        }
        collisionDetectorLock.readLock().unlock();
    }

    private void applyImpulse(Entity e1, Entity e2) {
        ICollider e1Collider = e1.getCollider();
        ICollider e2Collider = e2.getCollider();
        if (notNull(e1Collider, e2Collider) && notTrigger(e1Collider, e2Collider) && isCollidable(e1, e2)) {
            float xDepth, yDepth;

            xDepth = getXDepth(e1, e2);
            yDepth = getYDepth(e1, e2);

            if (e2.is("static")) {
                move(e1, xDepth, yDepth);
            } else if (e1.is("static")) {
                move(e2, xDepth, yDepth);
            } else {
                moveBoth(e1, e2, xDepth, yDepth);
            }
        }
    }

    private void moveBoth(Entity e1, Entity e2, float xDepth, float yDepth) {
        if (Math.abs(xDepth) < Math.abs(yDepth)) {
            e1.setX(e1.getX() + xDepth / 2f);
            e2.setX(e2.getX() - xDepth / 2f);
        } else {
            e1.setY(e1.getY() + yDepth / 2f);
            e2.setY(e2.getY() - yDepth / 2f);
        }
    }

    private void move(Entity e, float xDepth, float yDepth) {
        if (Math.abs(xDepth) < Math.abs(yDepth)) {
            e.setX(e.getX() + xDepth);
        } else {
            e.setY(e.getY() + yDepth);
        }
    }

    private float getYDepth(Entity e1, Entity e2) {
        float yDepth = 0f;
        if (e1.getY() < e2.getY()) {
            yDepth = yDepthMax(e1, e2);
        } else if (e1.getY() > e2.getY()) {
            yDepth = yDepthMin(e1, e2);
        }
        return yDepth;
    }

    private float getXDepth(Entity e1, Entity e2) {
        float xDepth = 0f;
        if (e1.getX() < e2.getX()) {
            xDepth = xDepthMax(e1, e2);
        } else if (e1.getX() > e2.getX()) {
            xDepth = xDepthMin(e1, e2);
        }
        return xDepth;
    }

    private boolean isCollidable(Entity e1, Entity e2) {
        return e1.is("collidable") && e2.is("collidable");
    }

    private boolean notTrigger(ICollider e1Collider, ICollider e2Collider) {
        return e1Collider.notTrigger() && e2Collider.notTrigger();
    }

    private boolean notNull(ICollider e1collider, ICollider e2Collider) {
        return e1collider != null && e2Collider != null;
    }

    private float yDepthMin(Entity e1, Entity e2) {
        return yMax(e2) - yMin(e1);
    }

    private float yDepthMax(Entity e1, Entity e2) {
        return yMin(e2) - yMax(e1);
    }

    private float xDepthMin(Entity e1, Entity e2) {
        return xMax(e2) - xMin(e1);
    }

    private float xDepthMax(Entity e1, Entity e2) {
        return xMin(e2) - xMax(e1);
    }

    private float yMin(Entity e) {
        return e.getY() + e.getBounds().getOriginY();
    }

    private float yMax(Entity e) {
        return e.getY() + e.getBounds().getOriginY() + e.getBounds().getHeight();
    }

    private float xMin(Entity e) {
        return e.getX() + e.getBounds().getOriginX();
    }

    private float xMax(Entity e) {
        return e.getX() + e.getBounds().getOriginX() + e.getBounds().getWidth();
    }
}
