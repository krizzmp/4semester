package dk.sdu.group5.common.data;

import java.util.List;
import java.util.Optional;


public class Bullet { // TODO: 17/03/16 move this to a seperate module

    private Entity bullet;

    private boolean toBeRemoved = false;

    private int activeTime = 10000;
    private long startTime = System.currentTimeMillis();
    
    public Bullet(World world) {
        bullet = new Entity();
        bullet.setType(EntityType.BULLET);
        bullet.setLives(1);
        bullet.setTexture("bulletTexture.png");
        bullet.setSpeed(120);
        Entity player = getPlayer(world.getEntities()).orElseThrow(RuntimeException::new);
        bullet.setX(player.getX()); // TODO: 17/03/16 change this to spawn in front of player instead of right on top of it
        bullet.setY(player.getY());
        bullet.addProperty("collidable");
        bullet.addProperty("damageable");
        world.AddEntity(bullet);
    }

    private Optional<Entity> getPlayer(List<Entity> xs) {
        return xs.stream().filter(e -> e.getType() == EntityType.PLAYER).findFirst();
    }

    public void update(float delta) {
        bullet.setX(bullet.getX() + bullet.getSpeed() * delta); // only shooting right
//        bullet.setY(50);

        long currentTime = System.currentTimeMillis();
        if (currentTime - startTime >= activeTime) {
            toBeRemoved = true;
        }
    }

    public boolean toBeRemoved() {
        return toBeRemoved;
    }

    public void removeBullet(World world) {
        world.RemoveEntity(bullet);
    }
}
