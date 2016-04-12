package dk.sdu.group5.common.data.collision;

import dk.sdu.group5.common.data.Entity;

public class CollisionController {

    // TODO: Move somewhere else
    public static void applyKnockBack(Entity e1, Entity e2) {
        ICollider e1Collider = e1.getCollider();
        ICollider e2Collider = e2.getCollider();
        if (notNull(e1Collider, e2Collider) && notTrigger(e1Collider, e2Collider) && isCollidable(e1, e2)) {
            float xDepth, yDepth;

            xDepth = getxDepth(e1, e2);
            yDepth = getyDepth(e1, e2);

            if (e2.is("static")) {
                move(e1, xDepth, yDepth);
            } else if (e1.is("static")) {
                move(e2, xDepth, yDepth);
            } else {
                moveBoth(e1, e2, xDepth, yDepth);
            }
        }
    }

    private static void moveBoth(Entity e1, Entity e2, float xDepth, float yDepth) {
        if (Math.abs(xDepth) < Math.abs(yDepth)) {
            e1.setX(e1.getX() + xDepth / 2f);
            e2.setX(e2.getX() - xDepth / 2f);
        } else {
            e1.setY(e1.getY() + yDepth / 2f);
            e2.setY(e2.getY() - yDepth / 2f);
        }
    }

    private static void move(Entity e, float xDepth, float yDepth) {
        if (Math.abs(xDepth) < Math.abs(yDepth)) {
            e.setX(e.getX() + xDepth+4);
        } else {
            e.setY(e.getY() + yDepth+4);
        }
    }

    private static float getyDepth(Entity e1, Entity e2) {
        float yDepth = 0f;
        if (e1.getY() < e2.getY()) {
            yDepth = yDepthMax(e1, e2);
        } else if (e1.getY() > e2.getY()) {
            yDepth = yDepthMin(e1, e2);
        }
        return yDepth;
    }

    private static float getxDepth(Entity e1, Entity e2) {
        float xDepth = 0f;
        if (e1.getX() < e2.getX()) {
            xDepth = xDepthMax(e1, e2);
        } else if (e1.getX() > e2.getX()) {
            xDepth = xDepthMin(e1, e2);
        }
        return xDepth;
    }

    private static boolean isCollidable(Entity e1, Entity e2) {
        return e1.is("collidable") && e2.is("collidable");
    }

    private static boolean notTrigger(ICollider e1Collider, ICollider e2Collider) {
        return e1Collider.notTrigger() && e2Collider.notTrigger();
    }

    private static boolean notNull(ICollider e1collider, ICollider e2Collider) {
        return e1collider != null && e2Collider != null;
    }

    private static float yDepthMin(Entity e1, Entity e2) {
        return yMax(e2) - yMin(e1);
    }

    private static float yDepthMax(Entity e1, Entity e2) {
        return yMin(e2) - yMax(e1);
    }

    private static float xDepthMin(Entity e1, Entity e2) {
        return xMax(e2) - xMin(e1);
    }

    private static float xDepthMax(Entity e1, Entity e2) {
        return xMin(e2) - xMax(e1);
    }

    private static float yMin(Entity e) {
        return e.getY() + e.getBounds().getMinY();
    }

    private static float yMax(Entity e) {
        return e.getY() + e.getBounds().getMaxY();
    }

    private static float xMin(Entity e) {
        return e.getX() + e.getBounds().getMinX();
    }

    private static float xMax(Entity e) {
        return e.getX() + e.getBounds().getMaxX();
    }

}
