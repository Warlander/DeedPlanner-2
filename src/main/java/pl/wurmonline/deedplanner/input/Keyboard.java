package pl.wurmonline.deedplanner.input;

import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class Keyboard implements KeyListener {
    
    private final int CACHE_SIZE = 1024;
    
    final boolean[] pressed = new boolean[CACHE_SIZE];
    final boolean[] hold = new boolean[CACHE_SIZE];
    final boolean[] released = new boolean[CACHE_SIZE];
    
    final boolean[] keysDown = new boolean[CACHE_SIZE];
    
    public Keyboard(Container panel) {
        panel.addKeyListener(this);
    }
    
    public void update() {
        for (int i=0; i<CACHE_SIZE; i++) {
            if (keysDown[i]) {
                if (!pressed[i] && !hold[i]) {
                    pressed[i] = true;
                    hold[i] = true;
                    released[i] = false;
                }
                else if (pressed[i] && hold[i]) {
                    pressed[i] = false;
                    hold[i] = true;
                    released[i] = false;
                }
            }
            else {
                if (hold[i]) {
                    pressed[i] = false;
                    hold[i] = false;
                    released[i] = true;
                }
                else {
                    pressed[i] = false;
                    hold[i] = false;
                    released[i] = false;
                }
            }
        }
    }
    
    public boolean isPressed(int key) {
        return pressed[key];
    }
    
    public boolean isHold(int key) {
        return hold[key];
    }
    
    public boolean isReleased(int key) {
        return released[key];
    }
    
    public void keyPressed(KeyEvent e) {
        keysDown[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
        keysDown[e.getKeyCode()] = false;
    }

    public void keyTyped(KeyEvent e) {}
    
}
