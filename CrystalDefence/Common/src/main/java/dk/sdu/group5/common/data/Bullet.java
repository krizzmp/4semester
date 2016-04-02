package dk.sdu.group5.common.data;

import java.util.List;
import java.util.Optional;

// TODO: 17/03/16 move this to a seperate module. 
// Wasn't this supposed to just be Weapon insted? -
// There we can just remove all bullets when unloading. - Martin F
public class Bullet { 

    private Entity bullet;

    private boolean toBeRemoved = false;

    private int activeTime = 10000;
    private long startTime = System.currentTimeMillis();
    
    private int offsetX = 0;
    private int offsetY = 0;
    private int dx = 0; // 1 = right, -1 = left
    private int dy = 0; // 1 = up, -1 = down
    private float speed = 120;

    public Bullet(World world, String direction) {
        setDirection(direction);
        
        bullet = new Entity();
        bullet.setType(EntityType.BULLET);
        bullet.setHealth(1);
        bullet.setTexture("bulletTexture.png");
        bullet.setSpeed(speed);
        Entity player = getPlayer(world.getEntities()).orElseThrow(RuntimeException::new);
        
        bullet.setX(player.getX() + offsetX);
        bullet.setY(player.getY() + offsetY); 
        bullet.addProperty("collidable");
        bullet.addProperty("damageable");
        world.AddEntity(bullet);
    }

    private Optional<Entity> getPlayer(List<Entity> xs) {
        return xs.stream().filter(e -> e.getType() == EntityType.PLAYER).findFirst();
    }

    public void update(float delta) {
        bullet.setX(bullet.getX() + (bullet.getSpeed() * delta) * dx);
        bullet.setY(bullet.getY() + (bullet.getSpeed() * delta) * dy);
        
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
    
    private void setDirection(String direction) {
        // TODO: 18/03/16 Implement entity.getTextureHeight/Width
        switch(direction) {
            case "up":
                offsetX = 16 - 5;
                offsetY = 32;
                dx = 0;
                dy = 1;
                break;
            case "down":
                offsetX = 16 - 5;
                offsetY = -10;
                dx = 0;
                dy = -1;
                break;
            case "left":
                offsetX = -10;
                offsetY = 16 - 5;
                dx = -1;
                dy = 0;
                break;
            case "right":
                offsetX = 32;
                offsetY = 16 - 5;
                dx = 1;
                dy = 0;
                break;
            case "upLeft":
                offsetX = -10;
                offsetY = 32;
                dx = -1;
                dy = 1;
                speed = speed/1.5f; // Should be changed so it more accurately shoots at the same speed as standard.
                break;
            case "upRight":
                offsetX = 32;
                offsetY = 32;
                dx = 1;
                dy = 1;
                speed = speed/1.5f;
                break;
            case "downLeft":
                offsetX = -10;
                offsetY = -10;
                dx = -1;
                dy = -1;
                speed = speed/1.5f;
                break;
            case "downRight":
                offsetX = 32;
                offsetY = -10;
                dx = 1;
                dy = -1; 
                speed = speed/1.5f;
                break;
            default:
                break;
        }
    }
}
