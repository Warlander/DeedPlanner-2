package pl.wurmonline.deedplanner.util.jogl;

import pl.wurmonline.deedplanner.graphics.texture.Tex;
import javax.media.opengl.GL2;
import org.joml.Vector3f;

public final class Mesh implements Renderable {

    private final Model parent;
    
    private final Tex tex;
    private final int listID;
    private final String name;
    
    Mesh(Model parent, Tex tex, int listID, String name) {
        this.parent = parent;
        this.tex = tex;
        this.listID = listID;
        this.name = name;
    }
    
    public void render(GL2 g) {
        if (tex!=null) {
            tex.bind(g);
        }
        
        Vector3f scale = parent.getScale();
        
        g.glScalef(scale.x, scale.y, scale.z);
        g.glCallList(listID);
    }
    
    public String toString() {
        return name;
    }
    
}
