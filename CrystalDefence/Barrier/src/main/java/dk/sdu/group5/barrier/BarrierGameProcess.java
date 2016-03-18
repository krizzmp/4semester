
package dk.sdu.group5.barrier;

import dk.sdu.group5.common.data.World;
import dk.sdu.group5.common.services.IGameProcess;
import org.openide.util.lookup.ServiceProvider;


@ServiceProvider(service = IGameProcess.class)
public class BarrierGameProcess implements IGameProcess {
    
    @Override
    public void install() {

    }

    @Override
    public void start(World world) {
        
    }

    @Override
    public void update(World world, float delta) {
        
    }

    @Override
    public void stop(World world) {
        
    }

    @Override
    public void uninstall() {

    }

}