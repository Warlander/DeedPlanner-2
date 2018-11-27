package pl.wurmonline.deedplanner.graphics.texture;

import com.jogamp.opengl.GL2GL3;

public interface Tex {
    
    public abstract int getId(GL2GL3 g);
    public abstract int getType();
    public abstract void update(GL2GL3 g);
    
}
