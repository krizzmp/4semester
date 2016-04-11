package dk.sdu.group5.core;

import org.openide.modules.ModuleInstall;

class Installer extends ModuleInstall {

    @Override
    public void restored() {
        new Start().run();
        System.out.println("app created");
    }
}
