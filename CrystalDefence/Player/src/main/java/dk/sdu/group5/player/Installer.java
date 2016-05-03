package dk.sdu.group5.player;

import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        // TODO tiføjes mere?
        System.out.println("Module installed");
    }

}
