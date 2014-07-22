package pl.wurmonline.deedplanner.graphics;

import com.jogamp.opengl.util.FPSAnimator;
import javax.media.opengl.*;
import pl.wurmonline.deedplanner.*;
import pl.wurmonline.deedplanner.util.jogl.Tex;
import pl.wurmonline.deedplanner.util.jogl.*;

public class GraphicsLoop implements GLEventListener {

    private final MapPanel panel;
    private final FPSAnimator animator;
    
    private boolean runFlag = true;
    private boolean stopped = false;
    
    public GraphicsLoop(MapPanel panel) {
        this.panel = panel;
        
        animator = new FPSAnimator(panel, Properties.graphicsFps);
        
        panel.addGLEventListener(this);
    }
    
    public void start() {
        animator.start();
    }
    
    public void reshape(GLAutoDrawable glautodrawable, int x, int y, int width, int height) {
                
    }

    public void init(GLAutoDrawable glautodrawable) {
        GLInit.printCapabilities(glautodrawable.getGLProfile());
        GLInit.initializeGL(glautodrawable.getGL().getGL2());
    }

    public void dispose(GLAutoDrawable glautodrawable) {}

    public void display(GLAutoDrawable glautodrawable) {
        if (runFlag) {
            stopped = false;
            GL2 g = glautodrawable.getGL().getGL2();
            
            RenderableEntity.clearEntities(g);

            g.glClearColor(0, 0, 0, 1);
            g.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

            Tex.resetInfo();
            
            if (Globals.upCamera) {
                panel.getUpCamera().set(g);
            }
            else {
                panel.getFPPCamera().set(g);
                Skybox.render(panel, g);
            }

            panel.getMap().render(g);
            stopped = true;
        }
        else {
            stopped = true;
        }
    }
    
    public void setRunFlag(boolean runFlag) {
        this.runFlag = runFlag;
    }
    
    public boolean isStopped() {
        return stopped;
    }
    
}
