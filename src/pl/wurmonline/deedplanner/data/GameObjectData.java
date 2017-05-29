package pl.wurmonline.deedplanner.data;

import pl.wurmonline.deedplanner.graphics.wom.Model;

public class GameObjectData {

    public final Model model;
    public final String name;
    public final String shortName;
    public final String type;
    public final boolean centerOnly;
    public final boolean floating;
    
    private final Materials materials;
    
    public GameObjectData(Model model, String name, String shortName, String type, boolean centerOnly, boolean floating, Materials materials) {
        this.model = model;
        this.name = name;
        this.shortName = shortName;
        this.type = type == null ? "" : type;
        this.centerOnly = centerOnly;
        this.floating = floating;
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
