package pl.wurmonline.deedplanner.util.jogl;

import javax.media.opengl.*;
import pl.wurmonline.deedplanner.Properties;
import pl.wurmonline.deedplanner.util.Log;

public class GLInit {

    public static void printCapabilities(GLProfile profile) {
        Log.out(GLInit.class, "GL2 support: "+profile.isGL2());
        Log.out(GLInit.class, "GL3 support: "+profile.isGL3());
        Log.out(GLInit.class, "GL4 support: "+profile.isGL4());
        Log.out(GLInit.class, "GLSL support: "+profile.hasGLSL());
        Log.out(GLInit.class, "Hardware rendering: "+profile.isHardwareRasterizer());
        Log.out(GLInit.class, "Profile: " + profile.getName());
    }
    
    public static GLCapabilities prepareGLCapabilities() {
        GLProfile profile = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(profile);
        caps.setHardwareAccelerated(true);
        caps.setDoubleBuffered(true);
        if (Properties.antialiasing>0) {
            caps.setSampleBuffers(true);
            caps.setNumSamples(Properties.antialiasing);
        }
        return caps;
    }
    
    public static void initializeGL(GL2 g) {
        g.glEnable(GL2.GL_DEPTH_TEST);
        g.glEnable(GL2.GL_TEXTURE_2D);
        g.glClearColor(0, 0, 0, 0);
        
        g.glEnable(GL2.GL_ALPHA_TEST);
        g.glAlphaFunc(GL2.GL_GREATER, 0.5f);
    }
    
}
