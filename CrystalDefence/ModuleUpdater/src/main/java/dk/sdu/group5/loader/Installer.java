package dk.sdu.group5.loader;

import org.openide.modules.ModuleInstall;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
 * Based on the project:
 * https://github.com/cses-sdu-dk/SB4-KOM-F16/tree/master/AsteroidsNetbeansModules/SilentUpdate
 */
class Installer extends ModuleInstall {
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    @Override
    public void restored() {
        super.restored();

        scheduledExecutorService.scheduleAtFixedRate(() -> ModuleUpdater.getInstance().checkAndHandleUpdates(),
                5000, 5000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void uninstalled() {
        super.uninstalled();

        scheduledExecutorService.shutdown();
    }
}
