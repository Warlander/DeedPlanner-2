package pl.wurmonline.deedplanner;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.wurmonline.deedplanner.util.Log;

public class Properties {
    
    public static final transient String SLASH = System.getProperty("file.separator");
    public static final transient String BR = System.getProperty("line.separator");
    public static final transient String HOME = System.getProperty("user.home");
    public static final transient String SAVE_DIR;
    
    public static String lookAndFeel = "PGS";
    public static String lastDir = HOME;
    
    public static boolean showGrid = true;
    public static boolean useTranslation = true;
    public static boolean colorblind = false;
    public static boolean showTip = true;
    
    public static int antialiasing = 2;
    
    public static int logicFps = 100;
    public static int graphicsFps = 45;
    public static int scale = 15;
    public static int iconSize = 32;
    
    public static transient double delta = 30d/(double) logicFps;
    
    public static double cameraRotationFpp = 0.015;
    public static double keyboardFractionUp = 1;
    public static double mod1Fpp = 5;
    public static double mod2Fpp = 0.2;
    public static double mouseFractionUp = 0.2;
    public static double mouseFractionFpp = 0.2;
    
    static {
        File file = new File(HOME+SLASH+"DeedPlanner"+SLASH);
        if (file.exists()) {
            SAVE_DIR = HOME+SLASH+"DeedPlanner"+SLASH;
        }
        else {
            SAVE_DIR = HOME+SLASH+".DeedPlanner"+SLASH;
        }
        
        try {
            loadProperties();
            saveProperties();
        } catch (IOException | IllegalArgumentException | IllegalAccessException ex) {
            Log.err(ex);
        }
    }
    
    public static void wake() {}
    
    private static void loadProperties() throws IOException, IllegalArgumentException, IllegalAccessException {
        File propFile = new File(SAVE_DIR+"Properties/Config.prop");
        if (propFile.exists()) {
            try (Scanner scan = new Scanner(new FileReader(propFile))) {
                while (scan.hasNext()) {
                    String type = scan.next();
                    if (!scan.hasNext()) {
                        throw new IOException("corrupted config file");
                    }
                    String param = scan.next();
                    if (!scan.hasNext()) {
                        throw new IOException("corrupted config file");
                    }
                    scan.next();
                    if (!scan.hasNext()) {
                        throw new IOException("corrupted config file");
                    }
                    String value = scan.nextLine().trim();
                    
                    for (Field f : Properties.class.getDeclaredFields()) {
                        f.setAccessible(true);
                        
                        if (f.getName().equals(param)) {
                            switch (type) {
                                case "boolean":
                                    f.setBoolean(null, Boolean.parseBoolean(value));
                                    break;
                                case "byte":
                                    f.setByte(null, Byte.parseByte(value));
                                    break;
                                case "short":
                                    f.setShort(null, Short.parseShort(value));
                                    break;
                                case "int":
                                    f.setInt(null, Integer.parseInt(value));
                                    break;
                                case "long":
                                    f.setLong(null, Long.parseLong(value));
                                    break;
                                case "double":
                                    f.setDouble(null, Double.parseDouble(value));
                                    break;
                                case "float":
                                    f.setFloat(null, Float.parseFloat(value));
                                    break;
                                default:
                                    f.set(null, value);
                                    break;
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
    
    public static void saveProperties() {
        StringBuilder build = new StringBuilder();
        for (Field f : Properties.class.getDeclaredFields()) {
            try {
                f.setAccessible(true);
                if (Modifier.isTransient(f.getModifiers())) continue;
                build.append(f.getType().getCanonicalName()).append(" ").append(f.getName()).append(" = ").append(f.get(null));
                build.append(BR);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(Properties.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        File saveDirectory = new File(SAVE_DIR+"Properties/");
        saveDirectory.mkdirs();
        File saveFile = new File(SAVE_DIR+"Properties/Config.prop");
        try (PrintWriter out = new PrintWriter(new FileWriter(saveFile))) {
            out.printf(build.toString());
        } catch (IOException ex) {
            Log.err(ex);
        }
    }
    
}
