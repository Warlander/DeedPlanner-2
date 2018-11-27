package pl.wurmonline.deedplanner;

import com.jogamp.opengl.GL;
import java.io.*;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.json.JSONArray;
import pl.wurmonline.deedplanner.forms.Loading;
import pl.wurmonline.deedplanner.forms.NewVersionWindow;
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
        
        if (!Properties.acceptedAnalytics) {
            int selectedOption = JOptionPane.showConfirmDialog(null,
                    "This application is using Google Analytics in order to track usage data.\n" +
                    "Once agreed, following data will be collected:\n" +
                    "1. Graphics card information (driver vendor, model, OpenGL capabilities)\n" +
                    "2. Program usage data (total time spent using program and on each tab, features usage)\n" +
                    "3. Exception reporting (basic informations about crashes when they happen)\n" +
                    "\n" +
                    "All data is anonymized before being sent.\n" +
                    "Gathered data is very important for application development - using this feature is mandatory for all application users.\n" +
                    "Your data can be removed on demand by sending application developer a message with your tracking ID (in program properties file)\n" +
                    "Collected data will be used for informative purposes only.\n" +
                    "Do you agree to these terms? Once agreed, this message will not appear again unless terms above will change.",
                    "DeedPlanner terms", JOptionPane.YES_NO_OPTION);
            if (selectedOption != JOptionPane.YES_OPTION) {
                return;
            }
            
            Properties.acceptedAnalytics = true;
            Properties.saveProperties();
        }
        
        SwingUtils.setLookAndFeel(Properties.lookAndFeel);
        
        if (!Properties.checkUpdates) {
            new Loading();
            return;
        }
        
        try {
            JSONArray releasesArray = JSONUtils.downloadJSONArray(Constants.GITHUB_API_RELEASES_URL);
            if (Updater.checkUpdate(releasesArray)) {
                String markdownChangelog = Updater.parseMarkdownChangelog(releasesArray);
                Parser parser = Parser.builder().build();
                Node document = parser.parse(markdownChangelog);
                HtmlRenderer renderer = HtmlRenderer.builder().build();
                String htmlChangelog = renderer.render(document);
                
                String currentVersion = Constants.VERSION_STRING;
                String newVersion = Updater.getNewestVersionString(releasesArray);
                
                String releaseUrl = Updater.getLatestReleaseUrl(releasesArray);
                String downloadUrl = Updater.getLatestDownloadUrl(releasesArray);
                
                NewVersionWindow newVersionWindow = new NewVersionWindow();
                newVersionWindow.setSize(800, 600);
                SwingUtils.centerFrame(newVersionWindow);
                newVersionWindow.setChangelog(htmlChangelog);
                newVersionWindow.setCurrentVersion(currentVersion);
                newVersionWindow.setNewVersion(newVersion);
                newVersionWindow.setReleaseUrl(releaseUrl);
                newVersionWindow.setDownloadUrl(downloadUrl);
                newVersionWindow.setVisible(true);
            }
            else {
                new Loading();
            }
        } catch (IOException ex) {
            new Loading();
        }
    }
    
}
