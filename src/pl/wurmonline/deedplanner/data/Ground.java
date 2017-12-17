package pl.wurmonline.deedplanner.data;

import javax.media.opengl.GL2;
import org.w3c.dom.*;
import pl.wurmonline.deedplanner.*;
import pl.wurmonline.deedplanner.data.storage.Data;
import pl.wurmonline.deedplanner.graphics.CameraType;
import pl.wurmonline.deedplanner.graphics.shaders.Program;
import pl.wurmonline.deedplanner.graphics.shaders.Shaders;

public class Ground implements TileEntity {
    
    private final GroundData data;
    private final RoadDirection dir;
    
    private boolean dirty = true;
    private int listId;
    
    public Ground(Element ground) {
        String shortname = ground.getAttribute("id");
        this.data = Data.grounds.get(shortname);
        
        switch (ground.getAttribute("dir")) {
            case "NW":
                this.dir = RoadDirection.NW;
                break;
            case "NE":
                this.dir = RoadDirection.NE;
                break;
            case "SW":
                this.dir = RoadDirection.SW;
                break;
            case "SE":
                this.dir = RoadDirection.SE;
                break;
            default:
                this.dir = RoadDirection.CENTER;
                break;
        }
    }
    
    public Ground(GroundData data) {
        this(data, RoadDirection.CENTER);
    }
    
    public Ground(GroundData data, RoadDirection dir) {
        this.data = data;
        this.dir = dir;
    }
    
    public Ground(Ground ground) {
        data = ground.data;
        dir = ground.dir;
    }
    
    public void render(GL2 g, Tile tile, int listID) {
        GroundData.TextureType textureType = getValidGroundTextureType();
        
        data.getTexture(textureType).bind(g);
        if (dir!=RoadDirection.CENTER) {
            if (dir==RoadDirection.NE || dir==RoadDirection.NW) {
                Tile nTile = tile.getMap().getTile(tile, 0, -1);
                if(nTile != null)
                    nTile.getGround().data.getTexture(textureType).bind(g, 1);
            }
            else {
                Tile nTile = tile.getMap().getTile(tile, 0, 1);
                if(nTile != null)
                    nTile.getGround().data.getTexture(textureType).bind(g, 1);
            }
            
            if (dir==RoadDirection.NW || dir==RoadDirection.SW) {
                Tile nTile = tile.getMap().getTile(tile, 1, 0);
                if(nTile != null)
                    nTile.getGround().data.getTexture(textureType).bind(g, 2);
            }
            else {
                Tile nTile = tile.getMap().getTile(tile, -1, 0);
                if(nTile != null)
                    nTile.getGround().data.getTexture(textureType).bind(g, 2);
            }
        }
        g.glCallList(listID);
        g.glColor3f(1, 1, 1);
    }
    
    public void render(GL2 g, Tile tile) {
        GroundData.TextureType textureType = getValidGroundTextureType();
        
        data.getTexture(textureType).bind(g);
        if (dir!=RoadDirection.CENTER) {
            if (dir==RoadDirection.NE || dir==RoadDirection.NW) {
                Tile nTile = tile.getMap().getTile(tile, 0, -1);
                if(nTile != null)
                    nTile.getGround().data.getTexture(textureType).bind(g, 1);
            }
            else {
                Tile nTile = tile.getMap().getTile(tile, 0, 1);
                if(nTile != null)
                    nTile.getGround().data.getTexture(textureType).bind(g, 1);
            }
            
            if (dir==RoadDirection.NW || dir==RoadDirection.SW) {
                Tile nTile = tile.getMap().getTile(tile, 1, 0);
                if(nTile != null)
                    nTile.getGround().data.getTexture(textureType).bind(g, 2);
            }
            else {
                Tile nTile = tile.getMap().getTile(tile, -1, 0);
                if(nTile != null)
                    nTile.getGround().data.getTexture(textureType).bind(g, 2);
            }
        }
        
        if (dir!=RoadDirection.CENTER) {
            Program program = Shaders.getShaders().diagonal;
            program.setInt(g, "dir", RoadDirection.toInt(dir));
            program.setInt(g, "tex0", 0);
            program.setInt(g, "tex1", 1);
            program.setInt(g, "tex2", 2);
            program.bind(g);
        }
        
        if (dirty || listId == 0) {
            if (listId == 0) {
                listId = g.glGenLists(1);
            }
            
            float h00 = (tile.getHeight()) / Constants.HEIGHT_MOD;
            float h10 = (tile.getMap().getTile(tile, 1, 0).getHeight()) / Constants.HEIGHT_MOD;
            float h11 = (tile.getMap().getTile(tile, 1, 1).getHeight()) / Constants.HEIGHT_MOD;
            float h01 = (tile.getMap().getTile(tile, 0, 1).getHeight()) / Constants.HEIGHT_MOD;
            float hC = (tile.getHeight(0.5f, 0.5f)) / Constants.HEIGHT_MOD;
            g.glNewList(listId, GL2.GL_COMPILE);
                g.glBegin(GL2.GL_TRIANGLES);
                    g.glTexCoord2f(0, 0);
                    g.glVertex3f(0, 0, h00);
                    g.glTexCoord2f(1, 0);
                    g.glVertex3f(4, 0, h10);
                    g.glTexCoord2f(0.5f, 0.5f);
                    g.glVertex3f(2, 2, hC);

                    g.glTexCoord2f(1, 0);
                    g.glVertex3f(4, 0, h10);
                    g.glTexCoord2f(1, 1);
                    g.glVertex3f(4, 4, h11);
                    g.glTexCoord2f(0.5f, 0.5f);
                    g.glVertex3f(2, 2, hC);

                    g.glTexCoord2f(1, 1);
                    g.glVertex3f(4, 4, h11);
                    g.glTexCoord2f(0, 1);
                    g.glVertex3f(0, 4, h01);
                    g.glTexCoord2f(0.5f, 0.5f);
                    g.glVertex3f(2, 2, hC);

                    g.glTexCoord2f(0, 1);
                    g.glVertex3f(0, 4, h01);
                    g.glTexCoord2f(0, 0);
                    g.glVertex3f(0, 0, h00);
                    g.glTexCoord2f(0.5f, 0.5f);
                    g.glVertex3f(2, 2, hC);
                g.glEnd();
            g.glEndList();
            dirty = false;
        }
        
        g.glCallList(listId);
        
        if (dir!=RoadDirection.CENTER) {
            Program diagonal = Shaders.getShaders().diagonal;
            diagonal.unbind(g);
            Program simple = Shaders.getShaders().simple;
            simple.bind(g);
        }
    }
    
    private GroundData.TextureType getValidGroundTextureType() {
        if (Globals.camera.isEditing()) {
            return GroundData.TextureType.TEXTURE_2D_VIEW;
        }
        else {
            return GroundData.TextureType.TEXTURE_3D_VIEW;
        }
    }

    public void serialize(Document doc, Element root) {
        Element ground = doc.createElement("ground");
        ground.setAttribute("id", data.shortName);
        if (dir!=RoadDirection.CENTER) {
            ground.setAttribute("dir", dir.toString());
        }
        root.appendChild(ground);
    }
    
    public GroundData getData() {
        return data;
    }
    
    public boolean equals(Object obj) {
        if (!(obj instanceof Ground)) {
            return false;
        }
        else {
            Ground ground = (Ground) obj;
            if (ground.data!=data) {
                return false;
            }
            else if (ground.dir!=dir) {
                return false;
            }
            else {
                return true;
            }
        }
    }
    
    public void markDirty() {
        dirty = true;
    }
    
    public void destroy(GL2 g) {
        if (listId == 0) {
            return;
        }
        
        g.glDeleteLists(listId, 1);
        listId = 0;
    }
    
    public String toString() {
        return data.toString();
    }

    public Materials getMaterials() {
        return null;
    }

    public Ground deepCopy() {
        return new Ground(this);
    }
    
}
