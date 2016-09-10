package pl.wurmonline.deedplanner.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import pl.wurmonline.deedplanner.forms.ErrorWindow;

public class Log {
    
    private static boolean showError = true;
    public static boolean ignoreErrors = false;
    
    public static void out(Object obj, String info) {
        Class clazz = obj.getClass();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        
        String message = new StringWriter().append("[").append(sdf.format(calendar.getTime())).append("] <")
                .append(clazz.getSimpleName()).append("> {").append(obj.toString()).append("} ").append(info).toString();
        
        System.out.println(message);
    }
    
    public static void out(Class clazz, String info) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        
        String message = new StringWriter().append("[").append(sdf.format(calendar.getTime())).append("] <")
                .append(clazz.getSimpleName()).append("> ").append(info).toString();
        
        System.out.println(message);
    }
    
    public static void err(Throwable error) {
        if (ignoreErrors) {
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        
        String message = new StringWriter().append("[").append(sdf.format(calendar.getTime()))
                .append("] Error has occurred!").append(System.getProperty("line.separator"))
                .append("Java vendor: ").append(System.getProperty("java.vendor")).append(System.getProperty("line.separator"))
                .append("Java version: ").append(System.getProperty("java.version")).toString();
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        error.printStackTrace(pw);
        String finalMessage = message+System.getProperty("line.separator")+sw.toString();
        
        System.err.println(finalMessage);
        if (showError) {
            new ErrorWindow(finalMessage).setVisible(true);
            showError = false;
        }
    }
    
    public static void showNextErrors() {
        showError = true;
    }
    
}
