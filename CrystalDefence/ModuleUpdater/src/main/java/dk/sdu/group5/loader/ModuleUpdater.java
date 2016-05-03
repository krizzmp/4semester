package dk.sdu.group5.loader;

import org.netbeans.api.autoupdate.*;
import org.netbeans.api.autoupdate.InstallSupport.Installer;
import org.netbeans.api.autoupdate.InstallSupport.Validator;
import org.netbeans.api.autoupdate.OperationContainer.OperationInfo;
import org.netbeans.api.autoupdate.OperationSupport.Restarter;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Based on the project:
 * https://github.com/cses-sdu-dk/SB4-KOM-F16/tree/master/AsteroidsNetbeansModules/SilentUpdate
 */

/**
 * Documentation on update centers: http://wiki.netbeans.org/DevFaqCustomUpdateCenter
 */
public final class ModuleUpdater {

    private final String EXTERNAL_UPDATE_CENTER_NAME = "dk_sdu_group5_ModuleUpdater_update_center"; // NOI18N
    private final Logger LOGGER = Logger.getLogger(ModuleUpdater.class.getPackage().getName());
    private UpdateUnitProvider localProvider;
    private Collection<UpdateElement> locallyInstalled = new ArrayList<>();

    private static final ModuleUpdater INSTANCE = new ModuleUpdater();

    public static ModuleUpdater getInstance() {
        return INSTANCE;
    }

    private ModuleUpdater() {
        try {
            File file = new File("../netbeans_site/updates.xml");
            if (file.exists()) {
                localProvider = UpdateUnitProviderFactory.getDefault().create("dk_sdu_group5_local_update_center",
                        "Local ModuleUpdater Update Center", file.toURI().toURL());
                LOGGER.info("Update units: " + localProvider.getUpdateUnits().size());
                for (UpdateUnit uu : localProvider.getUpdateUnits()) {
                    LOGGER.info(uu.getCodeName());
                }
            }
        } catch (MalformedURLException ex) {
            LOGGER.info("Could not find local update center!");
        }

        for (UpdateUnit updateUnit : UpdateManager.getDefault().getUpdateUnits()) {
            if (updateUnit.getType() == UpdateManager.TYPE.KIT_MODULE) {
                locallyInstalled.add(updateUnit.getInstalled());
            }
        }
    }

    public boolean timeToCheck() {
        // every startup
        return true;
    }

    public void checkAndHandleUpdates() {
        // refresh update center first
        refreshUpdateProvider();

        Collection<UpdateElement> updates = findUpdates();
        Collection<UpdateElement> available = findNewModules();
        Collection<UpdateElement> uninstalls = findUninstalls();

        if (updates.isEmpty() && available.isEmpty() && uninstalls.isEmpty()) {
            // none for install
//            LOGGER.info("None for update/install/uninstall");
            return;
        }

        // create a container for install
        OperationContainer<InstallSupport> containerForInstall = feedContainer(available, false);
        if (containerForInstall != null) {
            try {
                handleInstall(containerForInstall);
                LOGGER.info("Install new modules done.");
            } catch (UpdateHandlerException ex) {
                LOGGER.log(Level.INFO, ex.getLocalizedMessage(), ex.getCause());
                return;
            }
        }

        // create a container for update
        OperationContainer<InstallSupport> containerForUpdate = feedContainer(updates, true);
        if (containerForUpdate != null) {
            try {
                handleInstall(containerForUpdate);
                LOGGER.info("Update done.");
            } catch (UpdateHandlerException ex) {
                LOGGER.log(Level.INFO, ex.getLocalizedMessage(), ex.getCause());
                return;
            }
        }

        // create a container for uninstall
        OperationContainer<OperationSupport> containerForUninstall = feedUninstallContainer(uninstalls);
        if (containerForUninstall != null) {
            try {
                handleUninstall(containerForUninstall);
                LOGGER.info("Uninstall modules done.");
            } catch (UpdateHandlerException ex) {
                LOGGER.log(Level.INFO, ex.getLocalizedMessage(), ex.getCause());
                return;
            }
        }

        locallyInstalled = findLocalInstalled();
    }

    private boolean isLicenseApproved(String license) {
        // place your code there
        return true;
    }

    // package private methods
    private void handleInstall(OperationContainer<InstallSupport> container) throws UpdateHandlerException {
        // check licenses
        if (!allLicensesApproved(container)) {
            // have a problem => cannot continue
            throw new UpdateHandlerException("Cannot continue because license approval is missing for some updates.");
        }

        // download
        InstallSupport support = container.getSupport();
        Validator v = null;
        try {
            v = doDownload(support);
        } catch (OperationException ex) {
            // caught a exception
            throw new UpdateHandlerException("A problem caught while downloading, cause: ", ex);
        }
        if (v == null) {
            // have a problem => cannot continue
            throw new UpdateHandlerException("Missing Update Validator => cannot continue.");
        }

        // verify
        Installer i = null;
        try {
            i = doVerify(support, v);
        } catch (OperationException ex) {
            // caught a exception
            throw new UpdateHandlerException("A problem caught while verification of updates, cause: ", ex);
        }
        if (i == null) {
            // have a problem => cannot continue
            throw new UpdateHandlerException("Missing Update Installer => cannot continue.");
        }

        // install
        Restarter r = null;
        try {
            r = doInstall(support, i);
        } catch (OperationException ex) {
            // caught a exception
            throw new UpdateHandlerException("A problem caught while installation of updates, cause: ", ex);
        }

        // restart later
        support.doRestartLater(r);
    }

    private void handleUninstall(OperationContainer<OperationSupport> cont) throws UpdateHandlerException {

        if (!cont.listAll().isEmpty()) {
            try {
                Restarter rs = cont.getSupport().doOperation(null);
                if (rs != null) {
                    cont.getSupport().doRestart(rs, null);
                }
            } catch (OperationException ex) {
                throw new UpdateHandlerException("A problem caught while uninstall, cause: ", ex);
            }
        }
    }

    private Collection<UpdateElement> findLocalInstalled() {
        Collection<UpdateElement> locals = new HashSet<>();
        List<UpdateUnit> updateUnits = getUpdateProvider().getUpdateUnits();

        for (UpdateUnit updateUnit : updateUnits) {
            if (updateUnit.getInstalled() != null) {
                locals.add(updateUnit.getInstalled());
            }
        }

        return locals;
    }

    private Collection<UpdateElement> findUninstalls() {

        if (locallyInstalled.isEmpty()) {
            return locallyInstalled;
        }

        Collection<UpdateElement> localInstalled = findLocalInstalled();
        Collection<UpdateElement> uninstalls = new HashSet<>(locallyInstalled);
        uninstalls.removeAll(localInstalled);

        return uninstalls;
    }

    private Collection<UpdateElement> findUpdates() {
        // check updates
        Collection<UpdateElement> elements4update = new HashSet<>();
        List<UpdateUnit> updateUnits = getUpdateProvider().getUpdateUnits();
        for (UpdateUnit unit : updateUnits) {
            if (unit.getInstalled() != null) { // means the plugin already installed
                if (!unit.getAvailableUpdates().isEmpty()) { // has updates
                    elements4update.add(unit.getAvailableUpdates().get(0)); // add plugin with highest version
                }
            }
        }
        return elements4update;
    }

    private Collection<UpdateElement> findNewModules() {
        // check updates
        Collection<UpdateElement> elements4install = new HashSet<>();
        List<UpdateUnit> updateUnits = UpdateManager.getDefault().getUpdateUnits();
        for (UpdateUnit unit : updateUnits) {
            if (unit.getInstalled() == null) { // means the plugin is not installed yet
                if (!unit.getAvailableUpdates().isEmpty()) { // is available
                    elements4install.add(unit.getAvailableUpdates().get(0)); // add plugin with highest version                    
                }
            }
        }
        return elements4install;
    }

    private void refreshUpdateProvider() {
        UpdateUnitProvider updateProvider = getUpdateProvider();
        if (updateProvider == null) {
            // have a problem => cannot continue
            LOGGER.info("Missing Silent Update Provider => cannot continue.");
            return;
        }

        try {
            updateProvider.refresh(null, true);
//            for (UpdateUnit updateUnit : updateProvider.getUpdateUnits()){
//                if(updateUnit.)
//            }
        } catch (IOException ex) {
            // caught a exception
            LOGGER.log(Level.INFO, "A problem caught while refreshing Update Centers, cause: ", ex);
        }
    }

    private UpdateUnitProvider getUpdateProvider() {
        if (localProvider != null) {
            return localProvider;
        }

        List<UpdateUnitProvider> updateProviders = UpdateUnitProviderFactory.getDefault().getUpdateUnitProviders(true);
        for (UpdateUnitProvider provider : updateProviders) {
            if (EXTERNAL_UPDATE_CENTER_NAME.equals(provider.getName())) {
                return provider;
            }
        }

        return null;
    }

    private OperationContainer<OperationSupport> feedUninstallContainer(Collection<UpdateElement> uninstalls) {
        if (uninstalls == null || uninstalls.isEmpty()) {
            return null;
        }

        OperationContainer<OperationSupport> cont = OperationContainer.createForDirectUninstall();

        // loop all uninstalls and add to container for update
        for (UpdateElement ue : uninstalls) {
            if (cont.canBeAdded(ue.getUpdateUnit(), ue)) {
                LOGGER.log(Level.INFO, "Uninstall found: {0}", ue);
                OperationInfo<OperationSupport> operationInfo = cont.add(ue);
                if (operationInfo == null) {
                    continue;
                }
                cont.add(operationInfo.getRequiredElements());
                if (!operationInfo.getBrokenDependencies().isEmpty()) {
                    // have a problem => cannot continue
                    LOGGER.log(Level.INFO, "There are broken dependencies => cannot continue, broken deps: {0}", operationInfo.getBrokenDependencies());
                    return null;
                }
            }
        }
        return cont;
    }

    private OperationContainer<InstallSupport> feedContainer(Collection<UpdateElement> updates, boolean update) {
        if (updates == null || updates.isEmpty()) {
            return null;
        }
        // create a container for update
        OperationContainer<InstallSupport> container;
        if (update) {
            container = OperationContainer.createForUpdate();
        } else {
            container = OperationContainer.createForInstall();
        }

        // loop all updates and add to container for update
        for (UpdateElement ue : updates) {
            if (container.canBeAdded(ue.getUpdateUnit(), ue)) {
                LOGGER.log(Level.INFO, "Update found: {0}", ue);
                OperationInfo<InstallSupport> operationInfo = container.add(ue);
                if (operationInfo == null) {
                    continue;
                }
                container.add(operationInfo.getRequiredElements());
                if (!operationInfo.getBrokenDependencies().isEmpty()) {
                    // have a problem => cannot continue
                    LOGGER.log(Level.INFO, "There are broken dependencies => cannot continue, broken deps: {0}", operationInfo.getBrokenDependencies());
                    return null;
                }
            }
        }
        return container;
    }

    private boolean allLicensesApproved(OperationContainer<InstallSupport> container) {
        if (!container.listInvalid().isEmpty()) {
            return false;
        }
        for (OperationInfo<InstallSupport> info : container.listAll()) {
            String license = info.getUpdateElement().getLicence();
            if (!isLicenseApproved(license)) {
                return false;
            }
        }
        return true;
    }

    private Validator doDownload(InstallSupport support) throws OperationException {
        return support.doDownload(null, true);
    }

    private Installer doVerify(InstallSupport support, Validator validator) throws OperationException {

        Installer installer = support.doValidate(validator, null); // validates all plugins are correctly downloaded
        // XXX: use there methods to make sure updates are signed and trusted
        // installSupport.isSigned(installer, <an update element>);
        // installSupport.isTrusted(installer, <an update element>);
        return installer;
    }

    private Restarter doInstall(InstallSupport support, Installer installer) throws OperationException {
        return support.doInstall(installer, null);
    }

    public class UpdateHandlerException extends Exception {
        public UpdateHandlerException(String msg) {
            super(msg);
        }

        public UpdateHandlerException(String msg, Throwable th) {
            super(msg, th);
        }
    }
}
