package pl.wurmonline.deedplanner.data;

import javax.media.opengl.GL2;
import org.w3c.dom.*;
import pl.wurmonline.deedplanner.Globals;
import pl.wurmonline.deedplanner.data.storage.Data;

public class Wall implements DataEntity {

    public final WallData data;
    
    public Wall(Element wall) {
        String shortname = wall.getAttribute("id");
        
        WallData tempData = null;
        for (WallData data : Data.walls) {
            if (data.shortName.equals(shortname)) {
                tempData = data;
                break;
            }
        }
        this.data = tempData;
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

}
