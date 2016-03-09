package dk.sdu.group5.core;

import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        System.out.println("hi");
        new dk.sdu.group5.core.Start().run();
        System.out.println("app created");
    }
}
