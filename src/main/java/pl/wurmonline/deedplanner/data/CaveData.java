package pl.wurmonline.deedplanner.data;

import com.jogamp.opengl.GL2;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pl.wurmonline.deedplanner.Constants;
import pl.wurmonline.deedplanner.Globals;
import pl.wurmonline.deedplanner.data.storage.Data;
import pl.wurmonline.deedplanner.graphics.CameraType;
import pl.wurmonline.deedplanner.graphics.texture.SimpleTex;

public class CaveData implements TileEntity {

    private static final SimpleTex CEILING_TEXTURE = SimpleTex.getTexture("Data/Caves/cave_512.png");
    
    public final SimpleTex texture;
    public final String name;
    public final String shortName;
    public final boolean wall;
    public final boolean show;
    
    public static CaveData get(Element cave) {
        String shortName = cave.getAttribute("id");
        return Data.caves.get(shortName);
    }
    
    public CaveData(SimpleTex texture, String name, String shortName, boolean wall, boolean show) {
        this.texture = texture;
        this.name = name;
        this.shortName = shortName;
        this.wall = wall;
        this.show = show;
    }
    
    public Materials getMaterials() {
        return null;
    }

    public void render(GL2 g, Tile tile) {
        float h00 = (tile.getCaveHeight()) / Constants.HEIGHT_MOD;
        float h10 = (tile.getMap().getTile(tile, 1, 0).getCaveHeight()) / Constants.HEIGHT_MOD;
        float h11 = (tile.getMap().getTile(tile, 1, 1).getCaveHeight()) / Constants.HEIGHT_MOD;
        float h01 = (tile.getMap().getTile(tile, 0, 1).getCaveHeight()) / Constants.HEIGHT_MOD;
        
        texture.bind(g);
        if ((wall && show && Globals.camera.getCameraType() == CameraType.TOP_VIEW) || !wall) {
            if (wall) {
                g.glColor3f(0.8f, 0.8f, 0.8f);
            }
            g.glBegin(GL2.GL_QUADS);
                g.glTexCoord2f(0, 0);
                g.glVertex3f(0, 0, h00);
                g.glTexCoord2f(1, 0);
                g.glVertex3f(4, 0, h10);
                g.glTexCoord2f(1, 1);
                g.glVertex3f(4, 4, h11);
                g.glTexCoord2f(0, 1);
                g.glVertex3f(0, 4, h01);
            g.glEnd();
            if (wall) {
                g.glColor3f(1f, 1f, 1f);
            }
        }
        if (Globals.camera.getCameraType() != CameraType.TOP_VIEW && !wall) {
            float ht00 = h00 + (tile.getCaveSize()) / Constants.HEIGHT_MOD;
            float ht10 = h10 + (tile.getMap().getTile(tile, 1, 0).getCaveSize()) / Constants.HEIGHT_MOD;
            float ht11 = h11 + (tile.getMap().getTile(tile, 1, 1).getCaveSize()) / Constants.HEIGHT_MOD;
            float ht01 = h01 + (tile.getMap().getTile(tile, 0, 1).getCaveSize()) / Constants.HEIGHT_MOD;
            CEILING_TEXTURE.bind(g);
            g.glBegin(GL2.GL_QUADS);
                g.glTexCoord2f(0, 0);
                g.glVertex3f(0, 0, ht00);
                g.glTexCoord2f(1, 0);
                g.glVertex3f(4, 0, ht10);
                g.glTexCoord2f(1, 1);
                g.glVertex3f(4, 4, ht11);
                g.glTexCoord2f(0, 1);
                g.glVertex3f(0, 4, ht01);
            g.glEnd();
            if (tile.getMap().getTile(tile, 0, 1).getCaveEntity().wall) {
                tile.getMap().getTile(tile, 0, 1).getCaveEntity().texture.bind(g);
                g.glBegin(GL2.GL_QUADS);
                    g.glTexCoord2f(1, 0);
                    g.glVertex3d(0, 4, h01);
                    g.glTexCoord2f(1, 1);
                    g.glVertex3d(0, 4, ht01);
                    g.glTexCoord2f(0, 1);
                    g.glVertex3d(4, 4, ht11);
                    g.glTexCoord2f(0, 0);
                    g.glVertex3d(4, 4, h11);
                g.glEnd();
            }
            if (tile.getMap().getTile(tile, 0, -1)!=null && tile.getMap().getTile(tile, 0, -1).getCaveEntity().wall) {
                tile.getMap().getTile(tile, 0, -1).getCaveEntity().texture.bind(g);
                g.glBegin(GL2.GL_QUADS);
                    g.glTexCoord2f(0, 0);
                    g.glVertex3d(0, 0, h00);
                    g.glTexCoord2f(0, 1);
                    g.glVertex3d(0, 0, ht00);
                    g.glTexCoord2f(1, 1);
                    g.glVertex3d(4, 0, ht10);
                    g.glTexCoord2f(1, 0);
                    g.glVertex3d(4, 0, h10);
                g.glEnd();
            }
            if (tile.getMap().getTile(tile, 1, 0).getCaveEntity().wall) {
                tile.getMap().getTile(tile, 1, 0).getCaveEntity().texture.bind(g);
                g.glBegin(GL2.GL_QUADS);
                    g.glTexCoord2f(1, 0);
                    g.glVertex3d(4, 0, h10);
                    g.glTexCoord2f(1, 1);
                    g.glVertex3d(4, 0, ht10);
                    g.glTexCoord2f(0, 1);
                    g.glVertex3d(4, 4, ht11);
                    g.glTexCoord2f(0, 0);
                    g.glVertex3d(4, 4, h11);
                g.glEnd();
            }
            if (tile.getMap().getTile(tile, -1, 0)!=null && tile.getMap().getTile(tile, -1, 0).getCaveEntity().wall) {
                tile.getMap().getTile(tile, -1, 0).getCaveEntity().texture.bind(g);
                g.glBegin(GL2.GL_QUADS);
                    g.glTexCoord2f(1, 0);
                    g.glVertex3d(0, 0, h00);
                    g.glTexCoord2f(1, 1);
                    g.glVertex3d(0, 0, ht00);
                    g.glTexCoord2f(0, 1);
                    g.glVertex3d(0, 4, ht01);
                    g.glTexCoord2f(0, 0);
                    g.glVertex3d(0, 4, h01);
                g.glEnd();
            }
        }
        
    }

    public TileEntity deepCopy() {
        return this;
    }

    public void serialize(Document doc, Element root) {
        Element ground = doc.createElement("cave");
        ground.setAttribute("id", shortName);
        root.appendChild(ground);
    }
    
    public String toString() {
        return name;
    }
    
}
