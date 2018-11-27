package pl.wurmonline.deedplanner.data;

import com.jogamp.opengl.GL2;

public class Water {

    private static int listID = 0;
    
    public static int prepareWater(GL2 g, Map map) {
        if (listID==0) {
            listID = g.glGenLists(1);
        }
        
        g.glNewList(listID, GL2.GL_COMPILE);
            g.glColor4f(1, 1, 1, 0.7f);
            g.glBegin(GL2.GL_QUADS);
                g.glTexCoord2f(0, 0);
                g.glVertex3f(0, 0, -0.01f);
                g.glTexCoord2f(map.getWidth(), 0);
                g.glVertex3f(map.getWidth()*4, 0, -0.01f);
                g.glTexCoord2f(map.getWidth(), map.getHeight());
                g.glVertex3f(map.getWidth()*4, map.getHeight()*4, -0.01f);
                g.glTexCoord2f(0, map.getHeight());
                g.glVertex3f(0, map.getHeight()*4, -0.01f);
            g.glEnd();
        g.glEndList();
        
        return listID;
    }
    
}
