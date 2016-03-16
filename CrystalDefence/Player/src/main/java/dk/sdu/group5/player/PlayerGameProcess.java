package dk.sdu.group5.player;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.GameKeys;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.lookup.ServiceProvider;

import java.util.logging.Level;
import java.util.logging.Logger;
@ServiceProvider(service = IGameProcess.class)
public class PlayerGameProcess implements IGameProcess
{
    private Entity player;
    private int speed;
    
    @Override
    public void install() {

    }

    @Override
    public void start(World world)
    {      
        player = new Entity();
        player.setType(EntityType.PLAYER);
        player.setLives(3);
        player.setX(250);
        player.setY(250);
        player.setTexture("playerTexture.png");
        speed = 2;                                // Should probably be implementet into Entity
        try
        {
            player.addProperty("collidable");
            player.addProperty("tangible");
            player.addProperty("damageable");
            world.AddEntity(player);
        }
        catch (Exception ex)
        {
            Logger.getLogger(PlayerGameProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(World world, float delta)
    {
        //Player Movement
        if(GameKeys.getInstance().player_movement_up.getKeyState()) {
            //System.out.println(GameKeys.getInstance().player_movement_up.getKeyState());
            player.setY((int) (player.getY() + speed * delta));
        }
        if(GameKeys.getInstance().player_movement_down.getKeyState()) {
            player.setY((int) (player.getY() - speed * delta));
        }
        if(GameKeys.getInstance().player_movement_left.getKeyState()) {
            player.setX((int) (player.getX() - speed * delta));
        }
        if(GameKeys.getInstance().player_movement_right.getKeyState()) {
            player.setX((int) (player.getX() + speed * delta));
        }
        
        
        // Render stuff
        // Collision stuff
    }

    @Override
    public void stop(World world)
    {
        try
        {
            world.RemoveEntity(player);
        }
        catch (Exception ex)
        {
            Logger.getLogger(PlayerGameProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void uninstall() {

    }

}
