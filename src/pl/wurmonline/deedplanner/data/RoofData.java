package pl.wurmonline.deedplanner.data;

import pl.wurmonline.deedplanner.util.jogl.Tex;

public class RoofData {

    public final String name;
    public final String shortName;
    public final Tex tex;
    
    public RoofData(String name, String shortName, Tex tex) {
        this.name = name;
        this.shortName = shortName;
        this.tex = tex;
    }
    
    public String toString() {
        return name;
    }
    
}
