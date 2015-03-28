package pl.wurmonline.deedplanner.data;

import java.util.function.Consumer;
import javax.media.opengl.GL2;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pl.wurmonline.deedplanner.data.storage.Data;
import pl.wurmonline.deedplanner.util.jogl.Color;

public class BorderData implements TileEntity {
    
    private final String name;
    private final String shortName;
    
    private final Consumer<GL2> drawFunction;
    private final Color color;
    
    public static BorderData get(Element border) {
        String shortname = border.getAttribute("id");
        return Data.borders.get(shortname);
    }
    
    public BorderData(String name, String shortName, Consumer<GL2> drawFunction, Color color) {
        this.name = name;
        this.shortName = shortName;
        this.drawFunction = drawFunction;
        this.color = color;
    }

    public Materials getMaterials() {
        return null;
    }

    public void render(GL2 g, Tile tile) {
        color.use(g);
        drawFunction.accept(g);
    }

    public BorderData deepCopy() {
        return this;
    }

    public void serialize(Document doc, Element root) {
        root.setAttribute("id", shortName);
    }
    
    public String toString() {
        return name;
    }
    
}
