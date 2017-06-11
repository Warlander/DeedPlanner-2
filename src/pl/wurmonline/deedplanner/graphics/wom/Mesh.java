package pl.wurmonline.deedplanner.graphics.wom;

import pl.wurmonline.deedplanner.graphics.texture.SimpleTex;
import javax.media.opengl.GL2;
import org.joml.Vector3f;
import pl.wurmonline.deedplanner.util.jogl.Renderable;

public final class Mesh implements Renderable {

    private final Model parent;
    
    private SimpleTex tex;
    private final int listID;
    private final String name;
    
    Mesh(Model parent, SimpleTex tex, int listID, String name) {
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
        
        if (scale.x != 1 || scale.y != 1 || scale.z != 1) {
            g.glScalef(scale.x, scale.y, scale.z);
        }
        g.glCallList(listID);
    }
    
    public void setTexture(SimpleTex tex) {
        this.tex = tex;
    }
    
    public SimpleTex getTexture() {
        return tex;
    }
    
    public String getName() {
        return name;
    }
    
    public String toString() {
        return name;
    }
    
}
