package pl.wurmonline.deedplanner.data;

import pl.wurmonline.deedplanner.graphics.texture.Tex;

public class RoofData {

    public final String name;
    public final String shortName;
    public final Tex tex;
    
    private final Materials materials;
    
    public RoofData(String name, String shortName, Tex tex, Materials materials) {
        this.name = name;
        this.shortName = shortName;
        this.tex = tex;
        if (materials!=null) {
            this.materials = materials;
        }
        else {
            this.materials = new Materials();
        }
    }
    
    public Materials getMaterials() {
        return materials;
    }
    
    public String toString() {
        return name;
    }
    
}
