package pl.wurmonline.deedplanner.data;

import javax.media.opengl.GL2;
import org.w3c.dom.*;
import pl.wurmonline.deedplanner.Globals;
import pl.wurmonline.deedplanner.data.storage.Data;

public class Wall implements TileEntity {

    public final WallData data;
    
    public Wall(Element wall) {
        String shortname = wall.getAttribute("id");
        this.data = Data.walls.get(shortname);
    }
    
    public Wall(WallData data) {
        this.data = data;
    }
    
    public void render(GL2 g, Tile tile) {
        if (Globals.upCamera) {
            g.glScalef(1, data.scale, 1);
        }
        g.glTranslatef(4, 0, 0);
        data.model.render(g);
    }
    
    public void serialize(Document doc, Element root) {
        root.setAttribute("id", data.shortName);
    }
    
    public Materials getMaterials() {
        return data.getMaterials();
    }
    
    public boolean equals(Object obj) {
        if (!(obj instanceof Wall)) {
            return false;
        }
        else {
            Wall wall = (Wall) obj;
            if (data!=wall.data) {
                return false;
            }
            return true;
        }
    }
    
    public String toString() {
        return data.toString();
    }

}
