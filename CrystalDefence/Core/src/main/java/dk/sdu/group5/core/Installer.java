package dk.sdu.group5.core;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

    @Override
    public void restored() {

       LwjglApplicationConfiguration cfg
               = new LwjglApplicationConfiguration();
       cfg.title = "Crystal Defence";
       cfg.width = 500;
       cfg.height = 400;
       cfg.useGL30 = false;
       cfg.resizable = false;
   }
    }
