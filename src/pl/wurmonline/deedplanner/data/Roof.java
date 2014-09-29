package pl.wurmonline.deedplanner.data;

import com.jogamp.common.nio.Buffers;
import java.nio.FloatBuffer;
import javax.media.opengl.GL2;
import org.w3c.dom.*;
import pl.wurmonline.deedplanner.data.storage.Data;
import pl.wurmonline.deedplanner.util.jogl.Mesh;

public class Roof implements TileEntity {

    private static final FloatBuffer upMatrix;
    private static final FloatBuffer rightMatrix;
    private static final FloatBuffer downMatrix;
    private static final FloatBuffer leftMatrix;
    
    static {
        upMatrix = Buffers.newDirectFloatBuffer(16);
        rightMatrix = Buffers.newDirectFloatBuffer(16);
        downMatrix = Buffers.newDirectFloatBuffer(16);
        leftMatrix = Buffers.newDirectFloatBuffer(16);
        
        upMatrix.put(new float[] {
            1, 0, 0, 0,
            0,-1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
        }).flip();
        rightMatrix.put(new float[] {
            0, 1, 0, 0,
            1, 0, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
        }).flip();
        downMatrix.put(new float[] {
           -1, 0, 0, 0,
            0, 1, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
        }).flip();
        leftMatrix.put(new float[] {
            0,-1, 0, 0,
           -1, 0, 0, 0,
            0, 0, 1, 0,
            0, 0, 0, 1
        }).flip();
    }
    
    private final RoofData data;
    public int level;
    private Mesh mesh;
    private byte rotation;
    
    public Roof(Element roof) {
        String shortname = roof.getAttribute("id");
        this.data = Data.roofs.get(shortname);
    }
    
    public Roof(RoofData data) {
        this.data = data;
    }
    
    public void render(GL2 g, Tile tile) {
        data.tex.bind(g);
        g.glTranslatef(-2, 2, level*3.5f);
        switch (rotation) {
            case 0:
                g.glMultMatrixf(leftMatrix);
                break;
            case 1:
                g.glMultMatrixf(upMatrix);
                break;
            case 2:
                g.glMultMatrixf(rightMatrix);
                break;
            case 3:
                g.glMultMatrixf(downMatrix);
                break;
        }
        if (mesh!=null) {
            mesh.render(g);
        }
    }

    public void serialize(Document doc, Element root) {
        Element roof = doc.createElement("roof");
        roof.setAttribute("id", data.shortName);
        root.appendChild(roof);
    }

    public void recalculateLevel(Tile currTile, int floor) {
        int currRadius = 1;
        Map map = currTile.getMap();
        
        outer:
        while (true) {
            for (int i=-currRadius; i<=currRadius; i++) {
                for (int i2=-currRadius; i2<=currRadius; i2++) {
                    if (!containsRoof(map.getTile(currTile, i, i2), floor)) break outer;
                }
            }
            currRadius++;
        }
        level = currRadius - 1;
    }
    
    public void recalculateMesh(Tile currTile, int floor) {
        for (RoofType type : RoofType.roofTypes) {
            byte match = type.checkMatch(currTile, floor);
            if (match!=-1) {
                rotation = match;
                mesh = type.mesh;
                return;
            }
        }
        mesh = null;
    }
    
    private boolean containsRoof(Tile t, int floor) {
        if (t!=null) {
            return t.getTileContent(floor) instanceof Roof;
        }
        return false;
    }
    
}
