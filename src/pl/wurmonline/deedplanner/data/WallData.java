package pl.wurmonline.deedplanner.data;

import pl.wurmonline.deedplanner.util.jogl.Color;
import pl.wurmonline.deedplanner.util.jogl.Model;

public class WallData {

    public final Model model;
    public final String name;
    public final String shortName;
    public final Color color;
    public final float scale;
    public final boolean houseWall;
    
    public WallData(Model model, String name, String shortName, Color color, float scale, boolean houseWall) {
        this.model = model;
        this.name = name;
        this.shortName = shortName;
        this.color = color;
        this.scale = scale;
        this.houseWall = houseWall;
    }
    
    public String toString() {
        return name;
    }
    
}
