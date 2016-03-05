package dk.sdu.group5.app;

import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

    @Override
    public void restored() {

        new dk.sdu.group5.core.Start().run();
        System.out.println("app created");
    }
}
