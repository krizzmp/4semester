package dk.sdu.group5.common.data;

import java.util.List;
import java.util.stream.Collectors;

public class CollisionController
{
    public void update(World world)
    {
        List<Entity> colEnts = world.getEntities().stream()
                .filter(e -> e.getCollider() != null).collect(Collectors.toList());

        for (int i = 0; i < colEnts.size(); i++)
        {
            for (int j = i + 1; j < colEnts.size(); j++)
            {
                if (!world.getCollisionDetector().collides(colEnts.get(i), colEnts.get(j)))
                {
                    continue;
                }

                resolveCollision(colEnts.get(i), colEnts.get(j));
            }
        }
    }

    private void resolveCollision(Entity e1, Entity e2)
    {
        if (!e1.getCollider().isTrigger() && !e2.getCollider().isTrigger())
        {
            Posf2d e1Center = getColliderCenter(e1);
            Posf2d e2Center = getColliderCenter(e2);

            Posf2d direction = new Posf2d(e2Center.getX() - e1Center.getX(), e2Center.getY() - e1Center.getY());

            float xDepth = 0f, yDepth = 0f;
            if (direction.getX() > 0f)
            {
                xDepth = (e2.getX() + e2.getCollider().getAABB().getMinX()) - (e1.getX() + e1.getCollider().getAABB().getMaxX());
            }
            else if (direction.getX() < 0f)
            {
                xDepth = (e2.getX() + e2.getCollider().getAABB().getMaxX()) - (e1.getX() + e1.getCollider().getAABB().getMinX());
            }

            if (direction.getY() > 0f)
            {
                yDepth = (e2.getY() + e2.getCollider().getAABB().getMinY()) - (e1.getY() + e1.getCollider().getAABB().getMaxY());
            }
            else if (direction.getY() < 0f)
            {
                yDepth = (e2.getY() + e2.getCollider().getAABB().getMaxY()) - (e1.getY() + e1.getCollider().getAABB().getMinY());
            }

            System.out.println(direction.getY());
            System.out.println(xDepth);
            System.out.println(yDepth);
            System.out.println(e1.getType());

            if (e1.getProperties().contains("static"))
            {
                if (xDepth != 0)
                {
                    e2.setX(e2.getX() - xDepth);
                }
                else
                {
                    e2.setY(e2.getY() - yDepth);
                }
            }
            else
            {
                if (xDepth != 0)
                {
                    e1.setX(e1.getX() - xDepth);
                }
                else
                {
                    e1.setY(e1.getY() - yDepth);
                }
            }
        }
    }

    private Posf2d getColliderCenter(Entity entity)
    {
        return new Posf2d(entity.getX() + (entity.getCollider().getAABB().getMaxX() - entity.getCollider().getAABB().getMinX()),
                entity.getY() + (entity.getCollider().getAABB().getMaxX() - entity.getCollider().getAABB().getMinX()));
    }
}
