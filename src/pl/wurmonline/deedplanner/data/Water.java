package pl.wurmonline.deedplanner.data;

import javax.media.opengl.GL2;

public class Water {

    private static int listID = 0;
    
    public static int prepareWater(GL2 g, Map map) {
        if (listID==0) {
            listID = g.glGenLists(1);
        }
        
        g.glNewList(listID, GL2.GL_COMPILE);
            g.glColor4f(1, 1, 1, 0.7f);
            g.glBegin(GL2.GL_QUADS);
                for (int i=0; i<map.getWidth(); i++) {
                    for (int i2=0; i2<map.getHeight(); i2++) {
                        g.glTexCoord2f(0, 0);
                        g.glVertex3f(i*4, i2*4, -0.01f);
                        g.glTexCoord2f(1, 0);
                        g.glVertex3f(i*4+4, i2*4, -0.01f);
                        g.glTexCoord2f(1, 1);
                        g.glVertex3f(i*4+4, i2*4+4, -0.01f);
                        g.glTexCoord2f(0, 1);
                        g.glVertex3f(i*4, i2*4+4, -0.01f);
                    }
                }
            g.glEnd();
        g.glEndList();
        
        return listID;
    }
    
}
