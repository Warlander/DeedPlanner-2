package pl.wurmonline.deedplanner.data;

import com.jogamp.opengl.util.awt.TextRenderer;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.util.Locale;
import javax.media.opengl.GL2;
import org.w3c.dom.*;
import pl.wurmonline.deedplanner.Globals;
import pl.wurmonline.deedplanner.Properties;
import pl.wurmonline.deedplanner.util.jogl.Color;

public class Label {

    private final Font font;
    private final String text;
    private final Color color;
    
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
        TextRenderer renderer = new TextRenderer(font);
        Rectangle2D bounds = renderer.getBounds(text);
        final float scale = Properties.scale*4f/(float)Globals.glWindowHeight;
        g.glPushMatrix();
            g.glTranslated(2-(bounds.getWidth()/2)*scale, 2-(bounds.getHeight()/2f)*scale, 0);
            renderer.setColor(color.toAWTColor());
            renderer.begin3DRendering();
                renderer.draw3D(text, 0, 0, 0, scale);
            renderer.end3DRendering();
        g.glPopMatrix();
        renderer.dispose();
    }
    
    public void serialize(Document doc, Element root) {
        Element labelElement = doc.createElement("label");
        labelElement.setAttribute("text", text);
        labelElement.setAttribute("font", font.getFamily(Locale.UK));
        labelElement.setAttribute("style", Integer.toString(font.getStyle()));
        labelElement.setAttribute("size", Integer.toString(font.getSize()));
        
        color.serialize(doc, labelElement);
        root.appendChild(labelElement);
    }

}
