package dk.sdu.group5.common.data.collision;

import dk.sdu.group5.common.data.Entity;

public class CollisionController {

    public static void resolveCollision(Entity e1, Entity e2) {
        if (!e1.getCollider().isTrigger() && !e2.getCollider().isTrigger()) {
            float xDepth = 0f, yDepth = 0f;

            if (e1.getVelocity().getX() > 0f) {
                xDepth = (e2.getX() + e2.getCollider().getBounds().getMinX()) - (e1.getX() + e1.getCollider().getBounds().getMaxX());
            } else if (e1.getVelocity().getX() < 0f) {
                xDepth = (e2.getX() + e2.getCollider().getBounds().getMaxX()) - (e1.getX() + e1.getCollider().getBounds().getMinX());
            }

            if (e1.getVelocity().getY() > 0f) {
                yDepth = (e2.getY() + e2.getCollider().getBounds().getMinY()) - (e1.getY() + e1.getCollider().getBounds().getMaxY());
            } else if (e1.getVelocity().getY() < 0f) {
                yDepth = (e2.getY() + e2.getCollider().getBounds().getMaxY()) - (e1.getY() + e1.getCollider().getBounds().getMinY());
            }

            if (e2.getProperties().contains("static")) {
                e1.setX(e1.getX() + xDepth);
                e1.setY(e1.getY() + yDepth);
            } else {
                e1.setX(e1.getX() + xDepth / 2f);
                e1.setY(e1.getY() + yDepth / 2f);
                e2.setX(e2.getX() - xDepth / 2f);
                e2.setY(e2.getY() - yDepth / 2f);
            }
        }
    }
}
