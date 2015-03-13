package pl.wurmonline.deedplanner.data;

import javax.media.opengl.GL2;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pl.wurmonline.deedplanner.data.storage.Data;

public class GameObject implements TileEntity {

    private final GameObjectData data;
    private double rotation = 0;
    
    public GameObject(GameObjectData data) {
        this.data = data;
    }
    
    public GameObject(Element object) {
        String shortname = object.getAttribute("id");
        this.data = Data.objects.get(shortname);
        rotation = Double.parseDouble(object.getAttribute("rotation"));
    }
    
    public void render(GL2 g, Tile tile) {
        g.glRotated(Math.toDegrees(rotation), 0, 0, 1);
        data.model.render(g);
    }
    
    public GameObject deepCopy() {
        return new GameObject(data);
    }
    
    public void serialize(Document doc, Element root) {
        root.setAttribute("id", data.shortName);
        root.setAttribute("rotation", Double.toString(rotation));
    }
    
    public Materials getMaterials() {
        return data.getMaterials();
    }
    
    public GameObjectData getData() {
        return data;
    }
    
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
    
    public String toString() {
        return data.toString();
    }
    
}
