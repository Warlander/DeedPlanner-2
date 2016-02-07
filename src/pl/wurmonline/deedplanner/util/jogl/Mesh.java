package pl.wurmonline.deedplanner.util.jogl;

import javax.media.opengl.GL2;

public final class Mesh implements Renderable {

    private final Tex tex;
    private final int listID;
    private final String name;
    private final float[] scale;
    
    Mesh(Tex tex, int listID, String name, float scale) {
        this.tex = tex;
        this.listID = listID;
        this.name = name;
        this.scale = new float[3];
        this.scale[0] = this.scale[1] = this.scale[2] = scale;
    }
    
    Mesh(Tex tex, int listID, String name, float[] scale) {
        this.tex = tex;
        this.listID = listID;
        this.name = name;
        this.scale = scale;
    }
    
    public void render(GL2 g) {
        if (tex!=null) {
            tex.bind(g);
        }
        
        g.glScalef(scale[0], scale[1], scale[2]);
        g.glCallList(listID);
    }
    
    public String toString() {
        return name;
    }
    
}
