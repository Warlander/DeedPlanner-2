package pl.wurmonline.deedplanner.data;

import pl.wurmonline.deedplanner.util.jogl.Model;

public class GameObjectData {

    public final Model model;
    public final String name;
    public final String shortName;
    public final boolean centerOnly;
    
    public GameObjectData(Model model, String name, String shortName, boolean centerOnly) {
        this.model = model;
        this.name = name;
        this.shortName = shortName;
        this.centerOnly = centerOnly;
    }
    
    public String toString() {
        return name;
    }
    
}
