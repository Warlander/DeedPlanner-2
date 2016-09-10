package pl.wurmonline.deedplanner.input;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.media.opengl.awt.GLJPanel;
import pl.wurmonline.deedplanner.util.Log;

public class Mouse implements MouseListener, MouseWheelListener {
    
    private static final Cursor blankCursor;
    private static final Robot robot;
    
    public final Pressed pressed = new Pressed();
    public final Hold hold = new Hold();
    public final Released released = new Released();
    
    public boolean scrollUp=false;
    public boolean scrollDown=false;
    
    public float diffX = 0;
    public float diffY = 0;
    public float x = 0;
    public float y = 0;
    private float realX = 0;
    private float realY = 0;
    
    private final GLJPanel panel;
    
    private final boolean[] buttonsDown = new boolean[3];
    private int wheelRotation = 0;
    private boolean grabbed = false;
    
    static {
        BufferedImage blankCursorImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(blankCursorImage, new Point(0, 0), "blank cursor");
        robot = getRobotOrNull();
    }
    
    private static Robot getRobotOrNull() {
        try {
            return new Robot();
        } catch (AWTException ex) {
            Log.err(ex);
            return null;
        }
    }
    
    public Mouse(GLJPanel panel) {
        this.panel = panel;
        panel.addMouseListener(this);
        panel.addMouseWheelListener(this);
    }
    
    public void update() {
        if (MouseInfo.getPointerInfo()==null) {
            return;
        }
        
        Point mouseLocation;
        try {
            mouseLocation = MouseInfo.getPointerInfo().getLocation();
        } catch (NullPointerException ex){
            // ignore
            return;
        }
        Point panelLocation = panel.getLocationOnScreen();
        
        realX = mouseLocation.x - panelLocation.x;
        realY = mouseLocation.y - panelLocation.y;
        
        left();
        right();
        middle();
        wheel();
        
        if (grabbed) {
            diffX = realX - x;
            diffY = realY - y;
            realX = x;
            realY = y;
            
            robot.mouseMove((int) x+panelLocation.x, (int) y+panelLocation.y);
        }
        else if (!grabbed) {
            diffX = 0;
            diffY = 0;
            x = realX;
            y = realY;
        }
    }
    
    public void setGrabbed(boolean grabbed) {
        this.grabbed = grabbed;
        
        if (grabbed) {
            panel.setCursor(blankCursor);
        }
        else {
            panel.setCursor(Cursor.getDefaultCursor());
            Point p = panel.getLocationOnScreen();
            robot.mouseMove((int) x+p.x, (int) y+p.y);
        }
    }
    
    public boolean isMouseGrabbed() {
        return grabbed;
    }
    
    private void left() {
        if (buttonsDown[0]) {
            if (!pressed.left && !hold.left) {
                pressed.left = true;
                hold.left = true;
                released.left = false;
            }
            else if (pressed.left && hold.left) {
                pressed.left = false;
                hold.left = true;
                released.left = false;
            }
        }
        else {
            if (hold.left) {
                pressed.left = false;
                hold.left = false;
                released.left = true;
            }
            else {
                pressed.left = false;
                hold.left = false;
                released.left = false;
            }
        }
    }
    
    private void right() {
        if (buttonsDown[2]) {
            if (!pressed.right && !hold.right) {
                pressed.right = true;
                hold.right = true;
                released.right = false;
            }
            else if (pressed.right && hold.right) {
                pressed.right = false;
                hold.right = true;
                released.right = false;
            }
        }
        else {
            if (hold.right) {
                pressed.right = false;
                hold.right = false;
                released.right = true;
            }
            else {
                pressed.right = false;
                hold.right = false;
                released.right = false;
            }
        }
    }
    
    private void middle() {
        if (buttonsDown[1]) {
            if (!pressed.middle && !hold.middle) {
                pressed.middle = true;
                hold.middle = true;
                released.middle = false;
            }
            else if (pressed.middle && hold.middle) {
                pressed.middle = false;
                hold.middle = true;
                released.middle = false;
            }
        }
        else {
            if (hold.middle) {
                pressed.middle = false;
                hold.middle = false;
                released.middle = true;
            }
            else {
                pressed.middle = false;
                hold.middle = false;
                released.middle = false;
            }
        }
    }
    
    private void wheel() {
        if (wheelRotation>0) {
            scrollUp=true;
            scrollDown=false;
        }
        else if (wheelRotation<0) {
            scrollUp=false;
            scrollDown=true;
        }
        else {
            scrollUp=false;
            scrollDown=false;
        }
        
        wheelRotation = 0;
    }

    public void mouseClicked(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {
        int button = e.getButton();
        if (button==0 || button>3) {
            return;
        }
        buttonsDown[button-1] = true;
    }

    public void mouseReleased(MouseEvent e) {
        int button = e.getButton();
        if (button==0 || button>3) {
            return;
        }
        buttonsDown[button-1] = false;
    }

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseWheelMoved(MouseWheelEvent e) {
        wheelRotation += e.getWheelRotation();
    }
    
    public final class Pressed {
        public boolean left=false;
        public boolean middle=false;
        public boolean right=false;
    }
    
    public final class Hold {
        public boolean left=false;
        public boolean middle=false;
        public boolean right=false;
    }
    
    public final class Released {
        public boolean left=false;
        public boolean middle=false;
        public boolean right=false;
    }
    
}
