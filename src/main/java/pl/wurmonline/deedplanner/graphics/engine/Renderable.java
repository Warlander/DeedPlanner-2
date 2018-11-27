package pl.wurmonline.deedplanner.graphics.engine;

import com.jogamp.opengl.GL2;
import pl.wurmonline.deedplanner.graphics.shaders.Program;
import pl.wurmonline.deedplanner.graphics.texture.Tex;

public abstract class Renderable {
    
    private boolean dirty = true;
    private boolean destroy = false;
    private int listID;
    
    public final int getOrCreateDisplayListID(GL2 g) {
        if (dirty) {
            listID = g.glGenLists(1);
            updateGeometry(g, listID);
            dirty = false;
        }
        
        return listID;
    }
    
    final void destroy(GL2 g) {
        if (listID != 0) {
            g.glDeleteLists(listID, 1);
        }
    }
    
    public final void markDirty() {
        dirty = true;
    }
    
    public final boolean isDirty() {
        return dirty;
    }
    
    public final void markDestroy() {
        destroy = true;
    }
    
    public final boolean isDestroying() {
        return destroy;
    }
    
    public abstract Program getProgram();
    public abstract Tex[] getTextures();
    protected abstract void updateGeometry(GL2 g, int listID);
    
}
