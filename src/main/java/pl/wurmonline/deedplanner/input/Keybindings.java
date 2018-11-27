package pl.wurmonline.deedplanner.input;

import java.awt.event.KeyEvent;
import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.wurmonline.deedplanner.Properties;
import pl.wurmonline.deedplanner.util.Log;

public final class Keybindings {

    public static final int FPP_MOVE_UP = 1,
                            FPP_MOVE_LEFT = 2,
                            FPP_MOVE_DOWN = 3,
                            FPP_MOVE_RIGHT = 4,
                            FPP_ELEVATION_UP = 5,
                            FPP_ELEVATION_DOWN = 6,
                            FPP_CAMERA_UP = 7,
                            FPP_CAMERA_UP_ALT = 8,
                            FPP_CAMERA_DOWN = 9,
                            FPP_CAMERA_DOWN_ALT = 10,
                            FPP_CAMERA_LEFT = 11,
                            FPP_CAMERA_LEFT_ALT = 12,
                            FPP_CAMERA_RIGHT = 13,
                            FPP_CAMERA_RIGHT_ALT = 14,
                            FPP_SPEED_MOD1 = 15,
                            FPP_SPEED_MOD2 = 16,
                            UP_MOVE_UP = 17,
                            UP_MOVE_LEFT = 18,
                            UP_MOVE_DOWN = 19,
                            UP_MOVE_RIGHT = 20,
                            UP_ELEVATION_UP = 21,
                            UP_ELEVATION_DOWN = 22,
                            UP_SCALE_MORE = 23,
                            UP_SCALE_LESS = 24,
                            UP_SPEED_MOD1 = 25,
                            UP_SPEED_MOD2 = 26,
                            OTHER_SCREENSHOT = 27;
    
    private final Map<String, Integer> keyToInt = new HashMap<>();
    private final Map<Integer, String> intToKey = new HashMap<>();
    private final Map<String, Integer> bindToInt = new HashMap<>();
    private final Map<Integer, String> intToBind = new HashMap<>();
    private final Map<Integer, Integer> defaults = new HashMap<>();
    private final Map<Integer, Integer> bindToKey = new HashMap<>();
    
    private final Keyboard input;
    
    public Keybindings(Keyboard input) {
        this.input = input;
        prepareVKMap();
        prepareBindMap();
        prepareDefaults();
        prepareKeybindings();
        saveKeybindings();
    }
    
    public boolean pressed(int key) {
        return input.pressed[bindToKey.get(key)];
    }
    
    public boolean hold(int key) {
        return input.hold[bindToKey.get(key)];
    }
    
    private void prepareVKMap() {
        Field[] fields = KeyEvent.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            if (name.startsWith("VK_")) {
                try {
                    keyToInt.put(name.substring("VK_".length()).toUpperCase(), field.getInt(null));
                    intToKey.put(field.getInt(null), name.substring("VK_".length()).toUpperCase());
                } catch (IllegalAccessException | IllegalArgumentException ex) {
                    Log.err(ex);
                }
            }
        }
    }
    
    private void prepareBindMap() {
        Field[] fields = Keybindings.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            if (field.getType()==int.class) {
                try {
                    bindToInt.put(name, field.getInt(null));
                    intToBind.put(field.getInt(null), name);
                } catch (IllegalAccessException | IllegalArgumentException ex) {
                    Log.err(ex);
                }
            }
        }
    }
    
    private void prepareDefaults() {
        defaults.put(FPP_MOVE_UP, KeyEvent.VK_W);
        defaults.put(FPP_MOVE_LEFT, KeyEvent.VK_A);
        defaults.put(FPP_MOVE_DOWN, KeyEvent.VK_S);
        defaults.put(FPP_MOVE_RIGHT, KeyEvent.VK_D);
        defaults.put(FPP_ELEVATION_UP, KeyEvent.VK_R);
        defaults.put(FPP_ELEVATION_DOWN, KeyEvent.VK_F);
        defaults.put(FPP_CAMERA_UP, KeyEvent.VK_I);
        defaults.put(FPP_CAMERA_UP_ALT, KeyEvent.VK_T);
        defaults.put(FPP_CAMERA_DOWN, KeyEvent.VK_K);
        defaults.put(FPP_CAMERA_DOWN_ALT, KeyEvent.VK_G);
        defaults.put(FPP_CAMERA_LEFT, KeyEvent.VK_J);
        defaults.put(FPP_CAMERA_LEFT_ALT, KeyEvent.VK_Q);
        defaults.put(FPP_CAMERA_RIGHT, KeyEvent.VK_L);
        defaults.put(FPP_CAMERA_RIGHT_ALT, KeyEvent.VK_E);
        defaults.put(FPP_SPEED_MOD1, KeyEvent.VK_SHIFT);
        defaults.put(FPP_SPEED_MOD2, KeyEvent.VK_CONTROL);
        defaults.put(UP_MOVE_UP, KeyEvent.VK_W);
        defaults.put(UP_MOVE_LEFT, KeyEvent.VK_A);
        defaults.put(UP_MOVE_DOWN, KeyEvent.VK_S);
        defaults.put(UP_MOVE_RIGHT, KeyEvent.VK_D);
        defaults.put(UP_ELEVATION_UP, KeyEvent.VK_E);
        defaults.put(UP_ELEVATION_DOWN, KeyEvent.VK_Q);
        defaults.put(UP_SCALE_MORE, KeyEvent.VK_R);
        defaults.put(UP_SCALE_LESS, KeyEvent.VK_F);
        defaults.put(UP_SPEED_MOD1, KeyEvent.VK_SHIFT);
        defaults.put(UP_SPEED_MOD2, KeyEvent.VK_CONTROL);
        defaults.put(OTHER_SCREENSHOT, KeyEvent.VK_F11);
    }
    
    private void prepareKeybindings() {
        Scanner scan = null;
        
        try {
            File file = new File(Properties.SAVE_DIR+"Properties/Keybindings.prop");
            File path = new File(Properties.SAVE_DIR+"Properties");
            if (!file.exists()) {
                try {
                    path.mkdirs();
                    file.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(Keybindings.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            scan = new Scanner(new FileInputStream(file));
            
            while (scan.hasNext()) {
                int bind = bindToInt.get(scan.next());
                scan.next();
                int key = keyToInt.get(scan.next());
                bindToKey.put(bind, key);
            }
        } catch (FileNotFoundException ex) {
            Log.err(ex);
        } finally {
            scan.close();
        }
        
        for (Map.Entry<Integer, Integer> entry : defaults.entrySet()) {
            if (!bindToKey.containsKey(entry.getKey())) {
                bindToKey.put(entry.getKey(), entry.getValue());
            }
        }
    }
    
    private void saveKeybindings() {
        File file = new File(Properties.SAVE_DIR+"Properties");
        file.mkdirs();
        file = new File(Properties.SAVE_DIR+"Properties/Keybindings.prop");
        try (PrintStream out = new PrintStream(file)) {
            for (Map.Entry<Integer, Integer> entry : bindToKey.entrySet()) {
                String key = intToBind.get(entry.getKey());
                String value = intToKey.get(entry.getValue());
                out.println(key+" = "+value);
            }
        } catch (FileNotFoundException ex) {
            Log.err(ex);
        }
    }
    
}
