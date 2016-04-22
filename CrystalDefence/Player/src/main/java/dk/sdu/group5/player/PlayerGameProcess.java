package dk.sdu.group5.player;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.GameKeys;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.data.collision.AABB;
import dk.sdu.group5.common.data.collision.CollisionController;
import dk.sdu.group5.common.data.collision.SquareCollider;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.lookup.ServiceProvider;

import java.util.List;

@ServiceProvider(service = IGameProcess.class)
public class PlayerGameProcess implements IGameProcess {
    private Entity player;

    @Override
    public void install() {

    }

    @Override
    public void start(World world) {
        player = new Entity(EntityType.PLAYER, 60, 250, 250, "playerTexture02.png", 100, 48, 48);
        player.addProperty("collidable");
        player.addProperty("tangible");
        player.addProperty("damageable");
        world.addEntity(player);
    }

    @Override
    public void update(World world, float delta) {
        //Player Movement
        GameKeys gameKeys = GameKeys.getInstance();
        float playerSpeed = player.getSpeed();
        if (gameKeys.player_movement_up.getKeyState()) {
            player.setY(player.getY() + playerSpeed * delta);
        }
        if (gameKeys.player_movement_down.getKeyState()) {
            player.setY(player.getY() - playerSpeed * delta);
        }
        if (gameKeys.player_movement_left.getKeyState()) {
            player.setX(player.getX() - playerSpeed * delta);
        }
        if (gameKeys.player_movement_right.getKeyState()) {
            player.setX(player.getX() + playerSpeed * delta);
        }

        // Collision stuff
        List<Entity> collisions = world.getCollisionDetector().collides(player, world.getEntities());
        collisions.stream().forEach(e -> {
            CollisionController.applyKnockBack(player, e);// applies knockback?
            world.getCollisionHandler().addCollision(e.getCollider(), player);
        });
        if(player.getHealth() < 0){
            world.setGameover(true);
        }
    }

    @Override
    public void stop(World world) {
        world.removeEntity(player);
    }

    @Override
    public void uninstall() {

    }

}
