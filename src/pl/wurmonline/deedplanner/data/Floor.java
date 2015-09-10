package pl.wurmonline.deedplanner.data;

import javax.media.opengl.GL2;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Floor implements TileEntity {

    private final FloorData data;
    private final EntityOrientation orientation;
    
    public Floor(Element floor) {
        this.data = FloorData.get(floor);
        if (floor.getAttribute("orientation").equals("")) {
            this.orientation = EntityOrientation.UP;
        }
        else {
            this.orientation = EntityOrientation.valueOf(floor.getAttribute("orientation"));
        }
    }
    
    public Floor(FloorData data) {
        this(data, EntityOrientation.UP);
    }
    
    public Floor(FloorData data, EntityOrientation orientation) {
        this.data = data;
        this.orientation = orientation;
    }
    
    public Materials getMaterials() {
        return data.getMaterials();
    }
    
    public void render(GL2 g, Tile tile) {
        if (orientation==EntityOrientation.UP) {
            data.model.render(g);
        }
        else {
            g.glPushMatrix();
                g.glTranslatef(-2f, 2f, 0);
                if (orientation==EntityOrientation.LEFT) {
                    g.glRotatef(90, 0, 0, 1);
                }
                else if(orientation==EntityOrientation.DOWN) {
                    g.glRotatef(180, 0, 0, 1);
                }
                else if(orientation==EntityOrientation.RIGHT) {
                    g.glRotatef(270, 0, 0, 1);
                }
                
                g.glTranslatef(2f, -2f, 0);
                data.model.render(g);
            g.glPopMatrix();
        }
    }

    public Floor deepCopy() {
        return new Floor(data, orientation);
    }

    public void serialize(Document doc, Element root) {
        Element floor = doc.createElement("Floor");
        floor.setAttribute("id", data.shortName);
        floor.setAttribute("orientation", orientation.toString());
        root.appendChild(floor);
    }
    
    public String toString() {
        return data.toString();
    }
    
}
