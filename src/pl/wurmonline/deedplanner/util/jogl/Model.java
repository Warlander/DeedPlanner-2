package pl.wurmonline.deedplanner.util.jogl;

import javax.media.opengl.GL2;
import org.w3c.dom.*;

public class Model {
    
    private final Mesh[] meshes;
    
    public Model(Element node) {
        String modelLoc = node.getAttribute("location");
        String scaleStr = node.getAttribute("scale");
        float scale;
        try {
            scale = Float.parseFloat(scaleStr);
        }
        catch (NumberFormatException ex) {
            scale = 1;
        }
        NodeList list = node.getElementsByTagName("mesh");
        meshes = new Mesh[list.getLength()];
        for (int i=0; i<list.getLength(); i++) {
            Element e = (Element) list.item(i);
            String name = e.getAttribute("name");
            String texStr = e.getAttribute("tex");
            String typeStr = e.getAttribute("type");
            boolean ladder = typeStr.equals("ladder");
            
            Tex tex = null;
            if (!texStr.isEmpty()) {
                tex = Tex.getTexture(texStr);
            }
            meshes[i] = new Mesh(modelLoc, name, tex, ladder, scale);
        }
    }
    
    public void render(GL2 g) {
        for (Mesh mesh : meshes) {
            mesh.render(g);
        }
    }
    
}
