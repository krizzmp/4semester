package dk.sdu.group5.player;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.GameKeys;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IGameProcess.class)
public class PlayerGameProcess implements IGameProcess {
    private Entity player;
    
    @Override
    public void install() {

    }

    @Override
    public void start(World world) {
        player = new Entity();
        player.setType(EntityType.PLAYER);
        player.setHealth(100);
        player.setX(250);
        player.setY(250);
        player.setTexture("playerTexture.png");
        player.setSpeed(60);
        player.addProperty("collidable");
        player.addProperty("tangible");
        player.addProperty("damageable");
        world.AddEntity(player);
    }

    @Override
    public void update(World world, float delta) {
        //Player Movement
        GameKeys gameKeys = GameKeys.getInstance();
        float playerSpeed = player.getSpeed();
        if(gameKeys.player_movement_up.getKeyState()) {
            player.setY(player.getY() + playerSpeed * delta);
        }
        if(gameKeys.player_movement_down.getKeyState()) {
            player.setY(player.getY() - playerSpeed * delta);
        }
        if(gameKeys.player_movement_left.getKeyState()) {
            player.setX(player.getX() - playerSpeed * delta);
        }
        if(gameKeys.player_movement_right.getKeyState()) {
            player.setX(player.getX() + playerSpeed * delta);
        }
    }

    // Collision stuff

    @Override
    public void stop(World world) {
        world.RemoveEntity(player);
    }

    @Override
    public void uninstall() {

    }

}
