package pl.wurmonline.deedplanner.data;

import com.jogamp.opengl.util.glsl.ShaderProgram;
import javax.media.opengl.GL2;
import org.w3c.dom.*;
import pl.wurmonline.deedplanner.Constants;
import pl.wurmonline.deedplanner.Globals;
import pl.wurmonline.deedplanner.Properties;
import pl.wurmonline.deedplanner.data.storage.Data;
import pl.wurmonline.deedplanner.graphics.Shaders;
import pl.wurmonline.deedplanner.util.DeedPlannerRuntimeException;
import pl.wurmonline.deedplanner.util.jogl.RenderableEntity;

public class Ground extends RenderableEntity implements DataEntity {
    
    private final GroundData data;
    private final RoadDirection dir;
    
    private final byte[][] noise;
    
    public Ground(Element ground) {
        String shortname = ground.getAttribute("id");
        GroundData tempData = null;
        for (GroundData data : Data.grounds) {
            if (data.shortName.equals(shortname)) {
                tempData = data;
                break;
            }
        }
        this.data = tempData;
        
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
        
        noise = new byte[4][4];
        createNoise();
    }
    
    public Ground(GroundData data) {
        this(data, RoadDirection.CENTER);
    }
    
    public Ground(GroundData data, RoadDirection dir) {
        this.data = data;
        this.dir = dir;
        
        noise = new byte[4][4];
        createNoise();
    }
    
    public Ground(Ground ground) {
        data = ground.data;
        noise = ground.noise;
        dir = ground.dir;
    }
    
    private void createNoise() {
        for (int i=0; i<noise.length; i++) {
            for (int i2=0; i2<noise.length; i2++) {
                if (Properties.showNoise && data.noiseStr>0) {
                    noise[i][i2] = (byte) (Constants.random.nextInt(data.noiseStr*2+1)-data.noiseStr);
                }
                else {
                    noise[i][i2] = 0;
                }
            }
        }
    }
    
    public void render(GL2 g, Tile tile, int listID) {
        if (Globals.upCamera && Globals.floor>=0 && Globals.floor<3) {
            switch (Globals.floor) {
                case 0:
                    g.glColor3f(1, 1, 1);
                    break;
                case 1:
                    g.glColor3f(0.6f, 0.6f, 0.6f);
                    break;
                case 2:
                    g.glColor3f(0.25f, 0.25f, 0.25f);
                    break;
            }
        }
        data.tex.bind(g);
        if (dir!=RoadDirection.CENTER) {
            if (dir==RoadDirection.NE || dir==RoadDirection.NW) {
                tile.getMap().getTile(tile, 0, -1).getGround().data.tex.bind(g, 1);
            }
            else {
                tile.getMap().getTile(tile, 0, 1).getGround().data.tex.bind(g, 1);
            }
            
            if (dir==RoadDirection.NW || dir==RoadDirection.SW) {
                tile.getMap().getTile(tile, 1, 0).getGround().data.tex.bind(g, 2);
            }
            else {
                tile.getMap().getTile(tile, -1, 0).getGround().data.tex.bind(g, 2);
            }
        }
        g.glCallList(listID);
        g.glColor3f(1, 1, 1);
    }
    
    public void prepareRender(GL2 g, Tile tile) {
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
        
        for (int i=0; i<4; i++) {
            for (int i2=0; i2<4; i2++) {
                float texX = (float) i / 4f;
                float texY = (float) i2 / 4f;
                float h00 = (tile.getHeight(i, i2) + getNoise(tile, i, i2)/2) / Constants.HEIGHT_MOD;
                float h10 = (tile.getHeight(i+1, i2) + getNoise(tile, i+1, i2)/2) / Constants.HEIGHT_MOD;
                float h11 = (tile.getHeight(i+1, i2+1) + getNoise(tile, i+1, i2+1)/2) / Constants.HEIGHT_MOD;
                float h01 = (tile.getHeight(i, i2+1) + getNoise(tile, i, i2+1)/2) / Constants.HEIGHT_MOD;
                g.glBegin(GL2.GL_QUADS);
                    g.glTexCoord2f(texX, texY);
                    g.glVertex3f(i, i2, h00);
                    g.glTexCoord2f(texX + 0.25f, texY);
                    g.glVertex3f(i+1, i2, h10);
                    g.glTexCoord2f(texX + 0.25f, texY + 0.25f);
                    g.glVertex3f(i+1, i2+1, h11);
                    g.glTexCoord2f(texX, texY + 0.25f);
                    g.glVertex3f(i, i2+1, h01);
                g.glEnd();
            }
        }
        
        if (dir!=RoadDirection.CENTER) {
            ShaderProgram program = Shaders.getShaders(g).diagonal;
            program.useProgram(g, false);
            program = Shaders.getShaders(g).simple;
            program.useProgram(g, true);
        }
    }
    
    private float getNoise(Tile tile, int x, int y) {
        if (x<4 && y<4) {
            return noise[x][y];
        }
        else if (x==4 && y==4) {
            return tile.getMap().getTile(tile, 1, 1).getGround().noise[0][0];
        }
        else if (x==4) {
            return tile.getMap().getTile(tile, 1, 0).getGround().noise[0][y];
        }
        else if (y==4) {
            return tile.getMap().getTile(tile, 0, 1).getGround().noise[x][0];
        }
        else {
            throw new DeedPlannerRuntimeException("Invalid xPart or yPart: must be from 0 to 4");
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
    
}
