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
    
    private final Materials materials;
    
    public WallData(Model model, String name, String shortName, Color color, float scale, boolean houseWall, boolean arch, boolean archBuildable, Materials materials) {
        this.model = model;
        this.name = name;
        this.shortName = shortName;
        this.color = color;
        this.scale = scale;
        this.houseWall = houseWall;
        this.arch = arch;
        this.archBuildable = archBuildable;
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
