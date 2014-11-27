package pl.wurmonline.deedplanner.data;

import pl.wurmonline.deedplanner.util.jogl.Model;

public class GameObjectData {

    public final Model model;
    public final String name;
    public final String shortName;
    public final boolean centerOnly;
    
    private final Materials materials;
    
    public GameObjectData(Model model, String name, String shortName, boolean centerOnly, Materials materials) {
        this.model = model;
        this.name = name;
        this.shortName = shortName;
        this.centerOnly = centerOnly;
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
