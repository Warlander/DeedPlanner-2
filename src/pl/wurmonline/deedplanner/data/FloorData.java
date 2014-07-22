package pl.wurmonline.deedplanner.data;

import javax.media.opengl.GL2;
import org.w3c.dom.*;
import pl.wurmonline.deedplanner.data.storage.Data;
import pl.wurmonline.deedplanner.util.jogl.Model;

public final class FloorData implements TileEntity {

    public final Model model;
    public final String name;
    public final String shortName;
    public final boolean opening;
    
    public static FloorData get(Element floor) {
        String shortname = floor.getAttribute("id");
        GroundData tempData = null;
        for (FloorData data : Data.floors) {
            if (data.shortName.equals(shortname)) {
                return data;
            }
        }
        return null;
    }
    
    public FloorData(Model model, String name, String shortName, boolean opening) {
        this.model = model;
        this.name = name;
        this.shortName = shortName;
        this.opening = opening;
    }
    
    public void render(GL2 g, Tile tile) {
        model.render(g);
    }
    
    public void serialize(Document doc, Element root) {
        Element floor = doc.createElement("Floor");
        floor.setAttribute("id", shortName);
        root.appendChild(floor);
    }
    
    public String toString() {
        return name;
    }

}
