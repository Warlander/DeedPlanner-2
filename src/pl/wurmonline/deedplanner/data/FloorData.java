package pl.wurmonline.deedplanner.data;

import org.w3c.dom.*;
import pl.wurmonline.deedplanner.data.storage.Data;
import pl.wurmonline.deedplanner.graphics.wom.Model;

public final class FloorData {

    public final Model model;
    public final String name;
    public final String shortName;
    public final boolean opening;
    private final Materials materials;
    
    public static FloorData get(Element floor) {
        String shortname = floor.getAttribute("id");
        return Data.floors.get(shortname);
    }
    
    public FloorData(Model model, String name, String shortName, boolean opening, Materials materials) {
        this.model = model;
        this.name = name;
        this.shortName = shortName;
        this.opening = opening;
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
