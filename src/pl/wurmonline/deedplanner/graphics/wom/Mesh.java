package pl.wurmonline.deedplanner.graphics.wom;

import javax.media.opengl.GL2;
import pl.wurmonline.deedplanner.graphics.texture.SimpleTex;
import pl.wurmonline.deedplanner.util.jogl.Renderable;

public final class Mesh implements Renderable {
    
    private SimpleTex tex;
    private final int listID;
    private final String name;
    
    Mesh(SimpleTex tex, int listID, String name) {
        this.tex = tex;
        this.listID = listID;
        this.name = name;
    }
    
    public void render(GL2 g) {
        if (tex!=null) {
            tex.bind(g);
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
