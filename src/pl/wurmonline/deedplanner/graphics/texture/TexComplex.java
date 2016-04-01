package pl.wurmonline.deedplanner.graphics.texture;

import javax.media.opengl.GL2;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import pl.wurmonline.deedplanner.Globals;

public final class TexComplex {

    private final Tex[] tex2d = new Tex[4];
    private final Tex[] tex3d = new Tex[4];
    
    public TexComplex(Node node) {
        NodeList list = node.getChildNodes();
        for (int i=0; i<list.getLength(); i++) {
            Node n = list.item(i);
            String name = n.getNodeName();
            if (!name.equals("universal") && !name.equals("2d") && !name.equals("3d")) {
                continue;
            }
            NamedNodeMap map = n.getAttributes();
            Tex tex = Tex.getTexture(map.getNamedItem("location").getNodeValue());
            int season = Season.seasonFromString(map.getNamedItem("season").getNodeValue());
            switch (name) {
                case "universal":
                    setTexture(tex2d, tex, season);
                    setTexture(tex3d, tex, season);
                    break;
                case "2d":
                    setTexture(tex2d, tex, season);
                    break;
                case "3d":
                    setTexture(tex3d, tex, season);
                    break;
            }
        }
    }
    
    public void bind(GL2 g) {
        bind(g, 0);
    }
    
    public void bind(GL2 g, int target) {
        if (Globals.upCamera) {
            tex2d[Globals.season].bind(g, target);
        }
        else {
            tex3d[Globals.season].bind(g, target);
        }
    }
    
    public Tex getDefault() {
        return tex2d[Season.SUMMER];
    }
    
    private void setTexture(Tex[] array, Tex tex, int season) {
        if (season==Season.ALL) {
            for (int i=0; i<array.length; i++) {
                array[i] = tex;
            }
        }
        else {
            array[season] = tex;
        }
    }
    
}
