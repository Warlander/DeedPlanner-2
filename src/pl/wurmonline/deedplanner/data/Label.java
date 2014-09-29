package pl.wurmonline.deedplanner.data;

import com.jogamp.opengl.util.awt.TextRenderer;
import java.awt.Color;
import java.awt.Font;
import javax.media.opengl.GL2;
import org.w3c.dom.*;
import pl.wurmonline.deedplanner.Globals;

public class Label implements TileEntity {

    private final TextRenderer renderer;
    private final String text;
    private final Color color;
    
    public Label(Font font, String text, Color color) {
        this.renderer = new TextRenderer(font);
        this.text = text;
        this.color = color;
    }
    
    public void render(GL2 g, Tile tile) {
        renderer.beginRendering(Globals.glWindowWidth, Globals.glWindowHeight, true);
        renderer.draw(text, 0, 0);
        renderer.endRendering();
    }
    
    public void serialize(Document doc, Element root) {
        
    }

}
