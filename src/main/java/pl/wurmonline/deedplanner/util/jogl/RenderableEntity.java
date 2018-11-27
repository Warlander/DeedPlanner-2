package pl.wurmonline.deedplanner.util.jogl;

import com.jogamp.opengl.GL2;
import java.util.LinkedList;
import pl.wurmonline.deedplanner.data.Tile;

public abstract class RenderableEntity {

    private static final LinkedList<RenderableEntity> toRemove = new LinkedList<>();
    
    private int listID;
    private boolean redraw = true;
    
    public static void clearEntities(GL2 g) {
        synchronized (toRemove) {
            toRemove.forEach((RenderableEntity e) -> {g.glDeleteLists(e.listID, 1);});
            toRemove.clear();
        }
    }
    
    public final void render(GL2 g, Tile tile) {
        if (redraw) {
            preRender(g, tile);
            redraw = false;
        }
        render(g, tile, listID);
        
    }
    
    public abstract void render(GL2 g, Tile tile, int listID);
    
    public final void preRender(GL2 g, Tile tile) {
        if (listID==0) {
            listID = g.glGenLists(1);
        }
        g.glNewList(listID, GL2.GL_COMPILE);
            prepareRender(g, tile);
        g.glEndList();
    }
    
    public abstract void prepareRender(GL2 g, Tile tile);
    
    public void redraw() {
        redraw = true;
    }
    
    public void destroy() {
        toRemove.add(this);
        redraw = true;
    }
    
}
