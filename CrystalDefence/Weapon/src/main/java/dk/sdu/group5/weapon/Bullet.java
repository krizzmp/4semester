package dk.sdu.group5.weapon;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.data.collision.AABB;
import dk.sdu.group5.common.data.collision.SquareCollider;

class Bullet {

    private static final float ACTIVE_TIME_LIMIT = 10000;

    private final Entity bullet;
    private final long startTime = System.currentTimeMillis();
    private boolean toBeRemoved = false;
    private int dx = 0; // 1 = right, -1 = left
    private int dy = 0; // 1 = up, -1 = down
    private float speed = 120;

    Bullet(World world, Entity player, String direction) {
        setDirection(direction);

        bullet = new Entity();
        bullet.setType(EntityType.BULLET);
        bullet.setHealth(1);
        bullet.setSpeed(speed);
        bullet.setCollider(new SquareCollider(false, new AABB(-4.5f, -4.5f, 9f, 9f)));
        bullet.setTexturePath("bulletTexture.png");
        bullet.addProperty("collidable");
        bullet.addProperty("damageable");

        bullet.setX(player.getX() + dx * player.getBounds().getWidth() / 2f
                + dx * bullet.getBounds().getWidth() / 2f);
        bullet.setY(player.getY() + dy * player.getBounds().getHeight() / 2f
                + dy * bullet.getBounds().getHeight() / 2f);

        world.addEntity(bullet);
    }

    private void setDirection(String direction) {
        switch (direction) {
            case "up":
                dx = 0;
                dy = 1;
                break;
            case "down":
                dx = 0;
                dy = -1;
                break;
            case "left":
                dx = -1;
                dy = 0;
                break;
            case "right":
                dx = 1;
                dy = 0;
                break;
            case "upLeft":
                dx = -1;
                dy = 1;
                speed = speed / 1.5f; // Should be changed so it more accurately shoots at the same speed as standard.
                break;
            case "upRight":
                dx = 1;
                dy = 1;
                speed = speed / 1.5f;
                break;
            case "downLeft":
                dx = -1;
                dy = -1;
                speed = speed / 1.5f;
                break;
            case "downRight":
                dx = 1;
                dy = -1;
                speed = speed / 1.5f;
                break;
            default:
                break;
        }
    }

    void update(World world, float delta) {
        bullet.setX(bullet.getX() + (bullet.getSpeed() * delta) * dx);
        bullet.setY(bullet.getY() + (bullet.getSpeed() * delta) * dy);

        long currentTime = System.currentTimeMillis();
        if (currentTime - startTime >= ACTIVE_TIME_LIMIT) {
            toBeRemoved = true;
        }

        world.getEntities().stream().filter((entity) -> (entity.getType() == EntityType.BULLET))
                .forEach((b) -> world.getCollisions(b).stream().forEach(collidedEntity -> {
                    if (collidedEntity.getType() == EntityType.ENEMY) {
                        collidedEntity.setHealth(collidedEntity.getHealth() - 1);
                    }
                    
                    if(collidedEntity.getType() != EntityType.PLAYER){
                        toBeRemoved = true;
                    }

                }));
    }

    boolean toBeRemoved() {
        return toBeRemoved;
    }

    void removeBullet(World world) {
        world.removeEntity(bullet);
    }
}
