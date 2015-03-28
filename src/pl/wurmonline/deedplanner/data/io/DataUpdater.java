package pl.wurmonline.deedplanner.data.io;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class DataUpdater {

    private static final File appFile = new File(DataUpdater.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("%20", " "));
    private static final String updaterName = "DeedPlanner Updater.jar";
    
    private DataUpdater() {}
    
    /**
     * Checks if program is in update mode (name of program file is equal to {@value #updaterName}).
     * 
     * @return is program in update mode?
     */
    public static boolean isUpdater() {
        return appFile.getName().equals(updaterName);
    }
    
    public static boolean isUpdateAvailable() {
        return false;
    }
    
    public static void prepareUpdate() throws IOException {
        File tempUpdater = new File(updaterName);
        //FileUtils.copyFile(appFile, tempUpdater);
        Desktop.getDesktop().open(tempUpdater);
        System.exit(0);
    }
    
    public static void doUpdate() {
        
    }
    
}
