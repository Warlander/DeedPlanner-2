package pl.wurmonline.deedplanner.graphics;

import javax.media.opengl.GL2;
import pl.wurmonline.deedplanner.MapPanel;
import pl.wurmonline.deedplanner.util.jogl.Tex;

public class Skybox {

    private static int skyboxID = -1;
    private static Tex tex;
    
    public static void render(MapPanel panel, GL2 g) {
        if (skyboxID==-1) {
            skyboxID = prerenderSkybox(g);
        }
        FPPCamera camera = panel.getFPPCamera();
        g.glPushMatrix();
            g.glTranslated(camera.posx, -camera.posz, camera.posy);
            tex.bind(g);
            g.glCallList(skyboxID);
        g.glPopMatrix();
    }
    
    private static int prerenderSkybox(GL2 g) {
        tex = Tex.getTexture("Data/Special/skybox.png");
        
        int listID = g.glGenLists(1);
        
        g.glNewList(listID, GL2.GL_COMPILE);
            g.glBegin(GL2.GL_QUADS);
                //up
                g.glTexCoord2f(0.25f, 1);
                g.glVertex3d(-250,-250, 249);
                g.glTexCoord2f(0.25f, 2f/3f);
                
                g.glVertex3d( 250,-250, 249);
                g.glTexCoord2f(0.5f, 2f/3f);
                
                g.glVertex3d( 250, 250, 249);
                
                g.glTexCoord2f(0.5f, 1);
                g.glVertex3d(-250, 250, 249);
            
                //down
                g.glTexCoord2f(0.25f, 0);
                g.glVertex3d(-250,-250,-249);
                g.glTexCoord2f(0.25f, 1f/3f);
                g.glVertex3d( 250,-250,-249);
                g.glTexCoord2f(0.5f, 1f/3f);
                g.glVertex3d( 250, 250,-249);
                g.glTexCoord2f(0.5f, 0);
                g.glVertex3d(-250, 250,-249);

                //front
                g.glTexCoord2f(0.25f, 1f/3f);
                g.glVertex3d( 249,-250,-250);
                g.glTexCoord2f(0.25f, 2f/3f);
                g.glVertex3d( 249,-250, 250);
                g.glTexCoord2f(0.5f, 2f/3f);
                g.glVertex3d( 249, 250, 250);
                g.glTexCoord2f(0.5f, 1f/3f);
                g.glVertex3d( 249, 250,-250);

                //back
                g.glTexCoord2f(1, 1f/3f);
                g.glVertex3d(-249,-250,-250);
                g.glTexCoord2f(1, 2f/3f);
                g.glVertex3d(-249,-250, 250);
                g.glTexCoord2f(0.75f, 2f/3f);
                g.glVertex3d(-249, 250, 250);
                g.glTexCoord2f(0.75f, 1f/3f);
                g.glVertex3d(-249, 250,-250);

                //left
                g.glTexCoord2f(0f, 1f/3f);
                g.glVertex3d(-250,-249,-250);
                g.glTexCoord2f(0, 2f/3f);
                g.glVertex3d(-250,-249, 250);
                g.glTexCoord2f(0.25f, 2f/3f);
                g.glVertex3d( 250,-249, 250);
                g.glTexCoord2f(0.25f, 1f/3f);
                g.glVertex3d( 250,-249,-250);

                //right
                g.glTexCoord2f(0.75f, 1f/3f);
                g.glVertex3d(-250, 249,-250);
                g.glTexCoord2f(0.75f, 2f/3f);
                g.glVertex3d(-250, 249, 250);
                g.glTexCoord2f(0.5f, 2f/3f);
                g.glVertex3d( 250, 249, 250);
                g.glTexCoord2f(0.5f, 1f/3f);
                g.glVertex3d( 250, 249,-250);
            g.glEnd();
        g.glEndList();
        
        return listID;
    }
    
}
