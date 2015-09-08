package pl.wurmonline.deedplanner.data;

import com.jogamp.opengl.util.glsl.ShaderProgram;
import javax.media.opengl.GL2;
import org.w3c.dom.*;
import pl.wurmonline.deedplanner.*;
import pl.wurmonline.deedplanner.data.storage.Data;
import pl.wurmonline.deedplanner.graphics.Shaders;

public class Ground implements TileEntity {
    
    private final GroundData data;
    private final RoadDirection dir;
    
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
        data.tex.bind(g);
        if (dir!=RoadDirection.CENTER) {
            if (dir==RoadDirection.NE || dir==RoadDirection.NW) {
                Tile nTile = tile.getMap().getTile(tile, 0, -1);
                if(nTile != null)
                    nTile.getGround().data.tex.bind(g, 1);
            }
            else {
                Tile nTile = tile.getMap().getTile(tile, 0, 1);
                if(nTile != null)
                    nTile.getGround().data.tex.bind(g, 1);
            }
            
            if (dir==RoadDirection.NW || dir==RoadDirection.SW) {
                Tile nTile = tile.getMap().getTile(tile, 1, 0);
                if(nTile != null)
                    nTile.getGround().data.tex.bind(g, 2);
            }
            else {
                Tile nTile = tile.getMap().getTile(tile, -1, 0);
                if(nTile != null)
                    nTile.getGround().data.tex.bind(g, 2);
            }
        }
        g.glCallList(listID);
        g.glColor3f(1, 1, 1);
    }
    
    public void render(GL2 g, Tile tile) {
        data.tex.bind(g);
        if (dir!=RoadDirection.CENTER) {
            if (dir==RoadDirection.NE || dir==RoadDirection.NW) {
                Tile nTile = tile.getMap().getTile(tile, 0, -1);
                if(nTile != null)
                    nTile.getGround().data.tex.bind(g, 1);
            }
            else {
                Tile nTile = tile.getMap().getTile(tile, 0, 1);
                if(nTile != null)
                    nTile.getGround().data.tex.bind(g, 1);
            }
            
            if (dir==RoadDirection.NW || dir==RoadDirection.SW) {
                Tile nTile = tile.getMap().getTile(tile, 1, 0);
                if(nTile != null)
                    nTile.getGround().data.tex.bind(g, 2);
            }
            else {
                Tile nTile = tile.getMap().getTile(tile, -1, 0);
                if(nTile != null)
                    nTile.getGround().data.tex.bind(g, 2);
            }
        }
        
        if (dir!=RoadDirection.CENTER) {
            ShaderProgram program = Shaders.getShaders(g).diagonal;
            program.useProgram(g, true);
            int direction = g.glGetUniformLocation(program.program(), "dir");
            g.glUniform1i(direction, RoadDirection.toInt(dir));
            int tex0 = g.glGetUniformLocation(program.program(), "tex0");
            g.glUniform1i(tex0, 0);
            int tex1 = g.glGetUniformLocation(program.program(), "tex1");
            g.glUniform1i(tex1, 1);
            int tex2 = g.glGetUniformLocation(program.program(), "tex2");
            g.glUniform1i(tex2, 2);
        }
        
        float h00 = (tile.getHeight()) / Constants.HEIGHT_MOD;
        float h10 = (tile.getMap().getTile(tile, 1, 0).getHeight()) / Constants.HEIGHT_MOD;
        float h11 = (tile.getMap().getTile(tile, 1, 1).getHeight()) / Constants.HEIGHT_MOD;
        float h01 = (tile.getMap().getTile(tile, 0, 1).getHeight()) / Constants.HEIGHT_MOD;
        float hC = (tile.getHeight(0.5f, 0.5f)) / Constants.HEIGHT_MOD;
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
        
        if (dir!=RoadDirection.CENTER) {
            ShaderProgram program = Shaders.getShaders(g).diagonal;
            program.useProgram(g, false);
            program = Shaders.getShaders(g).simple;
            program.useProgram(g, true);
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
