package pl.wurmonline.deedplanner.util;

import java.lang.Thread.UncaughtExceptionHandler;

public class ExceptionHandler implements UncaughtExceptionHandler {

    private static final ExceptionHandler handler = new ExceptionHandler();
    
    public static ExceptionHandler getExceptionHandler() {
        return handler;
    }
    
    public void uncaughtException(Thread t, Throwable e) {
        Log.err(e);
    }

}
