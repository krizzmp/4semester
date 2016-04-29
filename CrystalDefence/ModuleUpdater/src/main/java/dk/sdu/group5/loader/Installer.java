package dk.sdu.group5.loader;

import org.openide.modules.ModuleInstall;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Based on the project:
 * https://github.com/cses-sdu-dk/SB4-KOM-F16/tree/master/AsteroidsNetbeansModules/SilentUpdate
 */
class Installer extends ModuleInstall {
    private final ScheduledExecutorService exector = Executors.newScheduledThreadPool(1);

    private static final Runnable doCheck = () -> {
        if (SilentModuleUpdater.getInstance().timeToCheck()) {
            SilentModuleUpdater.getInstance().checkAndHandleUpdates();
        }
    };

    @Override
    public void restored() {
        super.restored();

        // http://bits.netbeans.org/dev/javadoc/org-netbeans-modules-autoupdate-services/overview-summary.html
        // https://blogs.oracle.com/rechtacek/entry/how_to_update_netbeans_platform

        exector.scheduleAtFixedRate(doCheck, 5000, 5000, TimeUnit.MILLISECONDS);
    }

}
