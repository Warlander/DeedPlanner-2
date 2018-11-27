package pl.wurmonline.deedplanner.data;

import java.util.function.Consumer;
import com.jogamp.opengl.GL2;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pl.wurmonline.deedplanner.data.storage.Data;
import pl.wurmonline.deedplanner.util.jogl.Color;

public class BorderData implements TileEntity {
    
    public final String name;
    public final String shortName;
    
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

    public void render(GL2 g, Tile tile) {
        drawFunction.accept(g);
    }
    
    public Materials getMaterials() {
        return null;
    }
    
    public Color getColor() {
        return color;
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
