package pl.wurmonline.deedplanner.data;

import com.jogamp.opengl.util.awt.TextRenderer;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Locale;
import com.jogamp.opengl.GL2;
import org.w3c.dom.*;
import pl.wurmonline.deedplanner.Globals;
import pl.wurmonline.deedplanner.Properties;
import pl.wurmonline.deedplanner.util.jogl.Color;

public class Label implements TileEntity {
    
    private static final HashMap<Font, TextRenderer> renderersMap = new HashMap<>();
    
    public Font font;
    public String text;
    public Color color;
    
    public Label(Element element) {
        this.text = element.getAttribute("text");
        String fontName = element.getAttribute("font");
        int style = Integer.parseInt(element.getAttribute("style"));
        int size = Integer.parseInt(element.getAttribute("size"));
        this.color = new Color((Element) element.getElementsByTagName("color").item(0));
        
        this.font = new Font(fontName, style, size);
    }
    
    public Label() {
        this(Font.decode("Arial-18"), "New Label", new Color(0, 0, 0));
    }
    
    public Label(Font font, String text, Color color) {
        this.font = font;
        this.text = text;
        this.color = color;
    }
    
    public void render(GL2 g, Tile tile) {
        TextRenderer renderer = renderersMap.get(font);
        if (renderer == null) {
            renderer = new TextRenderer(font);
            renderersMap.put(font, renderer);
        }
        Rectangle2D bounds = renderer.getBounds(text);
        final double scale = Properties.scale * 4.0 / Globals.glWindowHeight;
        
        g.glPushMatrix();
            g.glTranslated(2.0 - (bounds.getWidth()/2.0) * scale, 2.0 - (bounds.getHeight()/2.0) * scale, 0);
            renderer.setColor(color.toAWTColor());
            renderer.begin3DRendering();
                renderer.draw3D(text, 0, 0, 0, (float) scale);
            renderer.end3DRendering();
            g.glColor3f(1, 1, 1);
        g.glPopMatrix();
    }
    
    public void serialize(Document doc, Element root) {
        serialize(doc, root, false);
    }
    
    public void serialize(Document doc, Element root, boolean cave) {
        Element labelElement;
        if (!cave) {
            labelElement = doc.createElement("label");
        }
        else {
            labelElement = doc.createElement("caveLabel");
        }
        labelElement.setAttribute("text", text);
        labelElement.setAttribute("font", font.getFamily(Locale.UK));
        labelElement.setAttribute("style", Integer.toString(font.getStyle()));
        labelElement.setAttribute("size", Integer.toString(font.getSize()));
        
        color.serialize(doc, labelElement);
        root.appendChild(labelElement);
    }

    public Materials getMaterials() {
        return null;
    }

    public TileEntity deepCopy() {
        return new Label(font, text, color);
    }

}
