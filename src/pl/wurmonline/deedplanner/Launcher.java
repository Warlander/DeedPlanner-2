package pl.wurmonline.deedplanner;

import java.io.*;
import java.util.Locale;
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
