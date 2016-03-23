package dk.sdu.group5.common.data;

import java.util.List;
import java.util.stream.Collectors;

public class CollisionController {
    public void update(World world, float delta) {
        world.clearCollisions();

        List<Entity> collidableEnts = world.getEntities().stream()
                .filter(e -> e.getCollider() != null)
                .collect(Collectors.toList());

        List<Entity> dynamicEnts = collidableEnts.stream()
                .filter(e -> !e.getProperties().contains("static")
                        && e.getVelocity().getX() != 0f || e.getVelocity().getY() != 0f)
                .collect(Collectors.toList());

//        for (Entity dynamicEnt : dynamicEnts) {
//            dynamicEnt.setX(dynamicEnt.getX() + dynamicEnt.getVelocity().getX() * delta);
//            dynamicEnt.setY(dynamicEnt.getY() + dynamicEnt.getVelocity().getY() * delta);
//        }

        for (Entity dynamicEnt : dynamicEnts) {
            for (Entity collidableEnt : collidableEnts) {
                if (dynamicEnt != collidableEnt
                        && world.getCollisionDetector().collides(dynamicEnt, collidableEnt)) {
                    resolveCollision(dynamicEnt, collidableEnt);
                    world.addCollision(dynamicEnt, collidableEnt);
                }
            }
        }
    }

    private void resolveCollision(Entity e1, Entity e2) {
        if (!e1.getCollider().isTrigger() && !e2.getCollider().isTrigger()) {
            float xDepth = 0f, yDepth = 0f;

            if (e1.getVelocity().getX() > 0f) {
                xDepth = (e2.getX() + e2.getCollider().getAABB().getMinX()) - (e1.getX() + e1.getCollider().getAABB().getMaxX());
            } else if (e1.getVelocity().getX() < 0f) {
                xDepth = (e2.getX() + e2.getCollider().getAABB().getMaxX()) - (e1.getX() + e1.getCollider().getAABB().getMinX());
            }

            if (e1.getVelocity().getY() > 0f) {
                yDepth = (e2.getY() + e2.getCollider().getAABB().getMinY()) - (e1.getY() + e1.getCollider().getAABB().getMaxY());
            } else if (e1.getVelocity().getY() < 0f) {
                yDepth = (e2.getY() + e2.getCollider().getAABB().getMaxY()) - (e1.getY() + e1.getCollider().getAABB().getMinY());
            }

            System.out.println(e1.getVelocity());
            System.out.println(xDepth + " " + yDepth);

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
