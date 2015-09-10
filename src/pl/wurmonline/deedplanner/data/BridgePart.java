package pl.wurmonline.deedplanner.data;

import javax.media.opengl.GL2;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pl.wurmonline.deedplanner.Constants;
import pl.wurmonline.deedplanner.util.jogl.Mesh;

public class BridgePart implements TileEntity {
    
    private final Bridge bridge;
    private final Tile tile;
    
    private final BridgePartSide side;
    private final BridgePartType type;
    private final EntityOrientation orientation;
    
    private final int height;
    
    public BridgePart(Bridge bridge, Tile tile, BridgePartSide side, BridgePartType type, EntityOrientation orientation, int height) {
        this.bridge = bridge;
        this.tile = tile;
        this.side = side;
        this.type = type;
        this.orientation = orientation;
        this.height = height;
    }

    public Materials getMaterials() {
        Materials totalMaterials = new Materials();
        
        totalMaterials.put(bridge.getData().getMaterialsForPartType(type));
        
        if (type == BridgePartType.SUPPORT) {
            Materials extensionMaterials = bridge.getData().getMaterialsForPartType(BridgePartType.EXTENSION);
            int relativeHeight =  height - tile.getHeight();
            relativeHeight -= bridge.getData().getSupportHeight();
            relativeHeight -= bridge.getData().getSupportHeight();
            while (relativeHeight > 0) {
                totalMaterials.put(extensionMaterials);
                relativeHeight -= 20;
            }
        }
        
        return totalMaterials;
    }

    public void render(GL2 g, Tile tile) {
        Mesh mesh = bridge.getData().getMeshForPart(side, type);
        
        g.glPushMatrix();
            //4 is required to render bridge in DeedPlanner coordinate system
            g.glTranslatef(4, 0, height / Constants.HEIGHT_MOD);
            g.glTranslatef(-2, 2, 0);
            if (orientation==EntityOrientation.LEFT) {
                g.glRotatef(90, 0, 0, 1);
            }
            else if(orientation==EntityOrientation.DOWN) {
                g.glRotatef(180, 0, 0, 1);
            }
            else if(orientation==EntityOrientation.RIGHT) {
                g.glRotatef(270, 0, 0, 1);
            }
            g.glTranslatef(2, -2, 0);
            mesh.render(g);
            if (type == BridgePartType.SUPPORT) {
                Mesh extensionMesh = bridge.getData().getMeshForPart(side, BridgePartType.EXTENSION);
                int relativeHeight =  height - tile.getHeight();
                relativeHeight -= bridge.getData().getSupportHeight();
                g.glTranslatef(0, 0, -bridge.getData().getSupportHeight() / Constants.HEIGHT_MOD);
                while (relativeHeight > 0) {
                    extensionMesh.render(g);
                    g.glTranslatef(0, 0, -20 / Constants.HEIGHT_MOD);
                    relativeHeight -= 20;
                }
            }
        g.glPopMatrix();
    }

    public TileEntity deepCopy() {
        return new BridgePart(bridge, tile, side, type, orientation, height);
    }

    public void serialize(Document doc, Element root) {
        
    }
    
    public BridgePartSide getOrientation() {
        return side;
    }
    
    public BridgePartType getType() {
        return type;
    }
    
}
