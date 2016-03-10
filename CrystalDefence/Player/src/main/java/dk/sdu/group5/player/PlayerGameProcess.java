package dk.sdu.group5.player;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.lookup.ServiceProvider;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
@ServiceProvider(service = IGameProcess.class)
public class PlayerGameProcess implements IGameProcess
{
    private Entity player;

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
        player.setTexture("gridPattern.png");
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
