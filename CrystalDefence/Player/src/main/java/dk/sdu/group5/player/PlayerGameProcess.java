package dk.sdu.group5.player;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.GameKeys;
import dk.sdu.group5.common.data.KeyState;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.data.collision.AABB;
import dk.sdu.group5.common.data.collision.SquareCollider;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IGameProcess.class)
public class PlayerGameProcess implements IGameProcess {
    private Entity player;

    @Override
    public void start(World world) {
        player = new Entity();
        player.setType(EntityType.PLAYER);
        player.setSpeed(60);
        player.setTexturePath("playerTexture02b.png");
        player.setHealth(100);
        player.setCollider(new SquareCollider(false, new AABB(-16, -24, 32, 48)));
        player.setX(world.getDisplayResolutionWidth() / 2f - 100f);
        player.setY(world.getDisplayResolutionHeight() / 2f);
        player.addProperty("collidable");
        player.addProperty("tangible");
        player.addProperty("damageable");
        world.addEntity(player);
    }

    @Override
    public void update(World world, float delta) {
        //Player Movement
        GameKeys gameKeys = world.getGameKeys();
        float playerSpeed = player.getSpeed();
        if (gameKeys.getPlayerMovementUp().getState() == KeyState.PRESSED
                || gameKeys.getPlayerMovementUp().getState() == KeyState.HELD) {
            player.setY(player.getY() + playerSpeed * delta);
        }
        if (gameKeys.getPlayerMovementDown().getState() == KeyState.PRESSED
                || gameKeys.getPlayerMovementDown().getState() == KeyState.HELD) {
            player.setY(player.getY() - playerSpeed * delta);
        }
        if (gameKeys.getPlayerMovementLeft().getState() == KeyState.PRESSED
                || gameKeys.getPlayerMovementLeft().getState() == KeyState.HELD) {
            player.setX(player.getX() - playerSpeed * delta);
        }
        if (gameKeys.getPlayerMovementRight().getState() == KeyState.PRESSED
                || gameKeys.getPlayerMovementRight().getState() == KeyState.HELD) {
            player.setX(player.getX() + playerSpeed * delta);
        }

        if (player.getHealth() <= 0) {
            world.setGameover(true);
        }
    }

    @Override
    public void stop(World world) {
        world.removeEntity(player);
    }

}
