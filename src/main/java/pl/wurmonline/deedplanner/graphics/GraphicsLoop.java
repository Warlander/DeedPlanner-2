package pl.wurmonline.deedplanner.graphics;

import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.*;
import pl.wurmonline.deedplanner.*;
import pl.wurmonline.deedplanner.data.Map;
import pl.wurmonline.deedplanner.graphics.texture.SimpleTex;
import pl.wurmonline.deedplanner.util.Log;
import pl.wurmonline.deedplanner.util.jogl.*;

public class GraphicsLoop implements GLEventListener {

    private final MapPanel panel;
    private final FPSAnimator animator;
    
    private boolean runFlag = true;
    private boolean stopped = false;
    
    private int fps;
    private long lastFPS;
    private long timeSpent;
    
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
        GLInit.sendGpuCapabilities(glautodrawable.getGLProfile(), glautodrawable.getGL().getGL2());
    }

    public void dispose(GLAutoDrawable glautodrawable) {}

    public void display(GLAutoDrawable glautodrawable) {
        if (runFlag) {
            long startTime = System.currentTimeMillis();
            stopped = false;
            GL2 g = glautodrawable.getGL().getGL2();
            
            Map destroyingMap = panel.getAndClearDestroyingMap();
            if (destroyingMap != null) {
                destroyingMap.destroy(g);
            }
            
            g.glClearColor(0, 0, 0, 1);
            g.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

            SimpleTex.resetInfo();
            
            panel.getCamera().set(g, panel);
            panel.getMap().render(g, panel.getCamera());
            if (panel.getCamera().isSkyboxRendered()) {
                Skybox.render(panel, g);
            }
            
            stopped = true;
            
            if (lastFPS<System.currentTimeMillis()-1000) {
                float renderPercent = timeSpent/10f;
                Log.out(GraphicsLoop.class, "Graphics FPS: "+fps+", time spent on rendering: "+renderPercent+"%");
                fps = 0;
                timeSpent = 0;
                lastFPS = System.currentTimeMillis();
            }
            else {
                fps++;
                timeSpent += System.currentTimeMillis() - startTime;
            }
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
