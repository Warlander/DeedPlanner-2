package pl.wurmonline.deedplanner.data;

import javax.media.opengl.GL2;
import org.w3c.dom.*;
import pl.wurmonline.deedplanner.Globals;
import pl.wurmonline.deedplanner.data.storage.Data;
import pl.wurmonline.deedplanner.graphics.CameraType;
import pl.wurmonline.deedplanner.graphics.wom.Model;

public class Wall implements TileEntity {

    public final WallData data;
    
    private final boolean reversed;
    
    public Wall(Element wall) {
        String shortname = wall.getAttribute("id");
        this.data = Data.walls.get(shortname);
        this.reversed = wall.getAttribute("reversed").equals("true");
    }
    
    public Wall(WallData data, boolean reversed) {
        this.data = data;
        this.reversed = reversed;
    }
    
    public void render(GL2 g, Tile tile) {
        if (Globals.camera.getCameraType() == CameraType.TOP_VIEW) {
            g.glScalef(1, data.scale, 1);
        }
        
        if (reversed) {
            g.glScalef(-1, -1, 1);
        }
        else {
            g.glTranslatef(4, 0, 0);
        }
        
        Model model;
        if (data.houseWall) {
            int floor = tile.getEntityFloor(this);
            if (floor == 0) {
                model = data.bottomModel;
            }
            else {
                model = data.normalModel;
            }
        }
        else {
            model = data.normalModel;
        }
        
        model.render(g);
    }
    
    public Wall deepCopy() {
        return new Wall(data, reversed);
    }
    
    public void serialize(Document doc, Element root) {
        root.setAttribute("id", data.shortName);
        if (data.houseWall) {
            root.setAttribute("reversed", Boolean.toString(reversed));
        }
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
