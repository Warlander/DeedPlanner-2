package pl.wurmonline.deedplanner.graphics;

import javax.media.opengl.GL2;
import pl.wurmonline.deedplanner.MapPanel;
import pl.wurmonline.deedplanner.graphics.texture.SimpleTex;

public class Skybox {

    private static boolean texturesAreLoaded = false;
    private static SimpleTex[] tex;
    
    public static void render(MapPanel panel, GL2 g) {
        if (!texturesAreLoaded) {
            loadSkyboxTextures(g);
        }
        
        FPPCamera camera = panel.getFPPCamera();
        g.glTranslated(camera.posx, -camera.posz, camera.posy);
        
        //top
        tex[0].bind(g);
        g.glBegin(GL2.GL_QUADS);
            g.glTexCoord2f(0, 0);
            g.glVertex3d(-250,-250, 249);
            g.glTexCoord2f(1, 0);
            g.glVertex3d( 250,-250, 249);
            g.glTexCoord2f(1, 1);
            g.glVertex3d( 250, 250, 249);
            g.glTexCoord2f(0, 1);
            g.glVertex3d(-250, 250, 249);
        g.glEnd();

        //bottom
        tex[1].bind(g);
        g.glBegin(GL2.GL_QUADS);
            g.glTexCoord2f(0, 0);
            g.glVertex3d(-250,-250, -249);
            g.glTexCoord2f(1, 0);
            g.glVertex3d( 250,-250, -249);
            g.glTexCoord2f(1, 1);
            g.glVertex3d( 250, 250, -249);
            g.glTexCoord2f(0, 1);
            g.glVertex3d(-250, 250, -249);
        g.glEnd();
        
        //left
        tex[2].bind(g);    
        g.glBegin(GL2.GL_QUADS);
            g.glTexCoord2f(0, 0);
            g.glVertex3d(-249,250,-250);
            g.glTexCoord2f(1, 0);
            g.glVertex3d(-249,-250, -250);
            g.glTexCoord2f(1, 1);
            g.glVertex3d(-249,-250, 250);
            g.glTexCoord2f(0, 1);
            g.glVertex3d(-249, 250, 250);
        g.glEnd();

        //front
        tex[3].bind(g);
        g.glBegin(GL2.GL_QUADS);
            g.glTexCoord2f(0, 0);
            g.glVertex3d( -250,-249,-250);
            g.glTexCoord2f(1, 0);
            g.glVertex3d( 250,-249, -250);
            g.glTexCoord2f(1, 1);
            g.glVertex3d( 250, -249, +250);
            g.glTexCoord2f(0, 1);
            g.glVertex3d( -250, -249,+250);
        g.glEnd();

        //right
        tex[4].bind(g);
        g.glBegin(GL2.GL_QUADS);
            g.glTexCoord2f(0, 0);
            g.glVertex3d(249, -250,-250);
            g.glTexCoord2f(1, 0);
            g.glVertex3d(249, 250, -250);
            g.glTexCoord2f(1, 1);
            g.glVertex3d(249, 250, 250);
            g.glTexCoord2f(0, 1);
            g.glVertex3d(249, -250, 250);
        g.glEnd();
        
        //back
        tex[5].bind(g);
        g.glBegin(GL2.GL_QUADS);
            g.glTexCoord2f(0, 0);
            g.glVertex3d(250, 249, -250);
            g.glTexCoord2f(1, 0);
            g.glVertex3d(-250, 249, -250);
            g.glTexCoord2f(1, 1);
            g.glVertex3d(-250, 249, 250);
            g.glTexCoord2f(0, 1);
            g.glVertex3d(250, 249,250);
        g.glEnd();
    }
    
    private static void loadSkyboxTextures(GL2 g) {
        tex = new SimpleTex[6];
        for(int i = 0; i < tex.length; i++) {
            tex[i] = SimpleTex.getTexture("Data/Special/skybox/sb" + i + ".png");
        }
        texturesAreLoaded = true;
    }
}
