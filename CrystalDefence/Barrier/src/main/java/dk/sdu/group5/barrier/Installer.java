
package dk.sdu.group5.barrier;

import org.openide.modules.ModuleInstall;


public class Installer extends ModuleInstall{

    @Override
    public void restored() {
        // TODO: Dunno what else to add - Martin F.
        System.out.println("ModuleInstall - Barrier");
    }
}
