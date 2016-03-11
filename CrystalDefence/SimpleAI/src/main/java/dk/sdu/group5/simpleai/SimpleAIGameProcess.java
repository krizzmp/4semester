package dk.sdu.group5.simpleai;

import dk.sdu.group5.common.data.Entity;
import dk.sdu.group5.common.data.EntityType;
import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.lookup.ServiceProvider;

import java.util.logging.Level;
import java.util.logging.Logger;
@ServiceProvider(service = IGameProcess.class)
public class SimpleAIGameProcess implements IGameProcess
{

    @Override
    public void install() {

    }

    @Override
    public void start(World world)
    {

    }

    @Override
    public void update(World world, float delta)
    {

    }

    @Override
    public void stop(World world)
    {

    }

    @Override
    public void uninstall() {

    }

}
