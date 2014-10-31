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
    public final boolean arch;
    public final boolean archBuildable;
    
    public WallData(Model model, String name, String shortName, Color color, float scale, boolean houseWall, boolean arch, boolean archBuildable) {
        this.model = model;
        this.name = name;
        this.shortName = shortName;
        this.color = color;
        this.scale = scale;
        this.houseWall = houseWall;
        this.arch = arch;
        this.archBuildable = archBuildable;
    }
    
    public String toString() {
        return name;
    }
    
}
