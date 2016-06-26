package pl.wurmonline.deedplanner;

import java.io.*;
import java.util.Locale;
import javax.media.opengl.GL;
import javax.swing.JOptionPane;
import pl.wurmonline.deedplanner.forms.Loading;
import pl.wurmonline.deedplanner.util.*;

public class Launcher {

    public static void main(String[] args) {
        try {
            System.setOut(new DoublePrintStream(System.out, new PrintStream("InfoLog.txt")));
            System.setErr(new DoublePrintStream(System.err, new PrintStream("ErrorLog.txt")));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
            System.exit(-1);
        }
        Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler.getExceptionHandler());
        
        //before continuing, check if libraries are accessable and catch exception otherwise
        try {
            Class anyExternalLibraryClass = GL.class;
        }
        catch (NoClassDefFoundError error) {
            error.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null,
                                          "Critical DeedPlanner error occured.\n" +
                                          "Cannot find external libraries bundled with the program.\n" +
                                          "This usually happens in two cases:\n" +
                                          "1. Program is not unzipped from the .zip archive after download.\n" +
                                          "2. Jar file used to run this program is moved somewhere else\n" +
                                          "without moving all other files in program directory.\n\n" +
                                          "If this message appears in other situations than mentioned,\n" +
                                          "please copy content of ErrorLog.txt file and report\n" +
                                          "the error in a program thread.",
                                          "DeedPlanner critical error",
                                          JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        
//        if (DataUpdater.isUpdater()) {
//            try {
//                DataUpdater.doUpdate(null);
//            } catch (IOException | GitAPIException ex) {
//                Log.err(ex);
//            }
//        }
        
        Properties.wake();
        if (!Properties.useTranslation) {
            Locale.setDefault(Locale.ENGLISH);
        }
        
        SwingUtils.setLookAndFeel(Properties.lookAndFeel);
        
        new Loading();
    }
    
}
