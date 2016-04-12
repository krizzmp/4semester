package dk.sdu.group5.core;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.openide.modules.ModuleInstall;

public class Start implements Runnable {

    @Override
    public void run() {
        LwjglApplicationConfiguration cfg
                = new LwjglApplicationConfiguration();
        cfg.title = "Crystal Defence";
        cfg.width = 800;
        cfg.height = 400;
        cfg.useGL30 = false;
        cfg.resizable = false;

        new LwjglApplication(Game.getInstance(), cfg);
    }
}
