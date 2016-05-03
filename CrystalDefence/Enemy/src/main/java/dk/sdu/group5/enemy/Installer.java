package dk.sdu.group5.enemy;

import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        // TODO tilføjes mere?
        System.out.println("Module installed");
    }

}
