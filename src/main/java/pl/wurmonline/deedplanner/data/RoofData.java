package pl.wurmonline.deedplanner.data;

import pl.wurmonline.deedplanner.graphics.texture.SimpleTex;

public class RoofData {

    public final String name;
    public final String shortName;
    public final SimpleTex tex;
    
    private final Materials materials;
    
    public RoofData(String name, String shortName, SimpleTex tex, Materials materials) {
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
