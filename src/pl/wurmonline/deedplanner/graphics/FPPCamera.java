package pl.wurmonline.deedplanner.graphics;

import com.jogamp.common.nio.Buffers;
import java.nio.FloatBuffer;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import pl.wurmonline.deedplanner.*;
import pl.wurmonline.deedplanner.data.Map;
import pl.wurmonline.deedplanner.input.*;

public final class FPPCamera {
    
    public boolean fixedHeight = false;
    
    public double posx=0;
    public double posy=2;
    public double posz=0;
    
    private float dirx=1;
    private float diry=0;
    private float dirz=0;
    
    private float anglex=-(float)Math.PI/4;
    private float angley=(float)Math.PI/2;
    
    private double stickedHeight = 1.4;
    
    private final MapPanel panel;
    
    private static final FloatBuffer matrix;
    
    static {
        matrix = Buffers.newDirectFloatBuffer(16);
        matrix.put(new float[] {1, 0, 0, 0,
                                0, 0, -1, 0,
                                0, 1, 0, 0,
                                0, 0, 0, 1});
        matrix.rewind();
    }
    
    public FPPCamera(MapPanel panel) {
        this.panel = panel;
    }
    
    public void update(Mouse mouse, Keybindings keybindings) {
        final Map map = panel.getMap();
        
        double fraction = Properties.mouseFractionFpp;
        if (keybindings.hold(Keybindings.FPP_SPEED_MOD1)) {
            fraction*=Properties.mod1Fpp;
        }
        else if (keybindings.hold(Keybindings.FPP_SPEED_MOD2)) {
            fraction*=Properties.mod2Fpp;
        }
        
        double xDivine = posx/4d;
        double zDivine = posz/4d;
        
        int currX = (int)xDivine;
        int currZ = (int)zDivine;
        Globals.visibleDownX = currX-65;
        Globals.visibleUpX = currX+65;
        Globals.visibleDownY = -currZ-65;
        Globals.visibleUpY = -currZ+65;
        
        if (mouse.pressed.left) {
            mouse.setGrabbed(true);
        }
        else if (mouse.hold.left) {
            anglex += mouse.diffX*Properties.cameraRotationFpp;
            angley -= mouse.diffY*Properties.cameraRotationFpp;
        }
        else if (mouse.released.left) {
            mouse.setGrabbed(false);
        }
        
        if (keybindings.hold(Keybindings.FPP_MOVE_RIGHT)) {
            posx += (float)(Math.cos(anglex+Math.PI/2))*fraction*Properties.delta;
            posz += (float)(Math.sin(anglex+Math.PI/2))*fraction*Properties.delta;
        }
        if (keybindings.hold(Keybindings.FPP_MOVE_LEFT)) {
            posx += (float)(Math.cos(anglex-Math.PI/2))*fraction*Properties.delta;
            posz += (float)(Math.sin(anglex-Math.PI/2))*fraction*Properties.delta;
        }
        if (keybindings.hold(Keybindings.FPP_MOVE_UP)) {
            posx += dirx*fraction*Properties.delta;
            posy += diry*fraction*Properties.delta;
            posz += dirz*fraction*Properties.delta;
        }
        if (keybindings.hold(Keybindings.FPP_MOVE_DOWN)) {
            posx -= dirx*fraction*Properties.delta;
            posy -= diry*fraction*Properties.delta;
            posz -= dirz*fraction*Properties.delta;
        }
        
        if (keybindings.hold(Keybindings.FPP_CAMERA_LEFT) || keybindings.hold(Keybindings.FPP_CAMERA_LEFT_ALT)) {
            anglex -= 5*Properties.cameraRotationFpp;
        }
        if (keybindings.hold(Keybindings.FPP_CAMERA_RIGHT) || keybindings.hold(Keybindings.FPP_CAMERA_RIGHT_ALT)) {
            anglex += 5*Properties.cameraRotationFpp;
        }
        if (keybindings.hold(Keybindings.FPP_CAMERA_UP) || keybindings.hold(Keybindings.FPP_CAMERA_UP_ALT)) {
            angley += 5*Properties.cameraRotationFpp;
        }
        if (keybindings.hold(Keybindings.FPP_CAMERA_DOWN) || keybindings.hold(Keybindings.FPP_CAMERA_DOWN_ALT)) {
            angley -= 5*Properties.cameraRotationFpp;
        }
        
        if (!fixedHeight) {
            if (keybindings.hold(Keybindings.FPP_ELEVATION_UP)) {
                posy += fraction;
            }
            else if (keybindings.hold(Keybindings.FPP_ELEVATION_DOWN)) {
                posy -= fraction;
            }
        }
        
        if (Globals.fixedHeight) {
            if (keybindings.hold(Keybindings.FPP_SPEED_MOD1)) {
                if (mouse.scrollUp) {
                    stickedHeight+=3;
                }
                else if (mouse.scrollDown && stickedHeight-3>1) {
                    stickedHeight-=3;
                }
            }
            else if (keybindings.hold(Keybindings.FPP_SPEED_MOD2)) {
                if (mouse.scrollUp) {
                    stickedHeight+=0.3;
                }
                else if (mouse.scrollDown && stickedHeight-0.3f>1) {
                    stickedHeight-=0.3;
                }
            }
            
            if (posx<=4.1) {
                posx=4.1;
            }
            else if (posx>=map.getWidth()*4-4.1) {
                posx=map.getWidth()*4-4.1;
            }
            
            if (posz>=-4.1) {
                posz=-4.1;
            }
            else if (posz<=-map.getHeight()*4+4.1) {
                posz=-map.getHeight()*4+4.1;
            }
            
            double xPart = (posx%4d)/4d;
            double yPart = -(posz%4d)/4d;
            
            xDivine = posx/4d;
            zDivine = -posz/4d;
        
            currX = (int)xDivine;
            currZ = (int)zDivine;
            
            final double xPartRev = 1f - xPart;
            final double yPartRev = 1f - yPart;

            final double h00 = map.getTile(currX, currZ).getHeight()/Constants.HEIGHT_MOD;
            final double h10 = map.getTile(currX+1, currZ).getHeight()/Constants.HEIGHT_MOD;
            final double h01 = map.getTile(currX, currZ+1).getHeight()/Constants.HEIGHT_MOD;
            final double h11 = map.getTile(currX+1, currZ+1).getHeight()/Constants.HEIGHT_MOD;

            final double x0 = (h00*xPartRev + h10*xPart);
            final double x1 = (h01*xPartRev + h11*xPart);
            
            double height = (x0*yPartRev + x1*yPart);
            height+=stickedHeight;
            if (height<0.3) {
                height = 0.3;
            }
            posy = height;
        }
        
        if (anglex>Math.PI*2) {
            anglex-=Math.PI*2;
        }
        else if (anglex<0) {
            anglex+=Math.PI*2;
        }

        if (angley>Math.PI-0.01f) {
            angley=(float)Math.PI-0.01f;
        }
        else if (angley<0.01f) {
            angley=0.01f;
        }
        
        dirx = (float)(Math.cos(anglex)*Math.sin(angley));
        dirz = (float)(Math.sin(anglex)*Math.sin(angley));
        diry = (float)-Math.cos(angley);
    }
    
    public void set(GL2 g) {
        int width = panel.getWidth();
        int height = panel.getHeight();
        
        GLU glu = GLU.createGLU(g);
        
        g.glViewport(0, 0, width, height);
        g.glMatrixMode(GL2.GL_PROJECTION);
        g.glLoadIdentity();
        float fwidth = width;
        float fheight = height;
        glu.gluPerspective(70, fwidth/fheight, 0.1f, 600.0f);
        g.glMatrixMode(GL2.GL_MODELVIEW);
        g.glLoadIdentity();
        glu.gluLookAt((float)posx, (float)posy, (float)posz, (float)posx+dirx, (float)posy+diry, (float)posz+dirz, 0, 1, 0);
        g.glMultMatrixf(matrix);
    }
    
    public void reset() {
        posx=0;
        posy=2;
        posz=0;
    }
    
}
