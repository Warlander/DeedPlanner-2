package pl.wurmonline.deedplanner.data.bridges;

import com.jogamp.opengl.GL2;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pl.wurmonline.deedplanner.Constants;
import pl.wurmonline.deedplanner.data.EntityOrientation;
import pl.wurmonline.deedplanner.data.Materials;
import pl.wurmonline.deedplanner.data.Tile;
import pl.wurmonline.deedplanner.data.TileEntity;
import pl.wurmonline.deedplanner.util.jogl.Renderable;

public class BridgePart implements TileEntity {
    
    private static final float[] deformMatrix = new float[] {1, 0, 0, 0,
                                                             0, 1, 0, 0,
                                                             0, 0, 1, 0,
                                                             0, 0, 0, 1};
    
    private final Bridge bridge;
    private final Tile tile;
    
    private final BridgePartSide side;
    private final BridgePartType type;
    private final EntityOrientation orientation;
    
    private final float height;
    private final float heightDiff;
    
    public BridgePart(Bridge bridge, Tile tile, BridgePartSide side, BridgePartType type, EntityOrientation orientation, float height, float heightDiff) {
        this.bridge = bridge;
        this.tile = tile;
        this.side = side;
        this.type = type;
        this.orientation = orientation;
        this.height = height;
        this.heightDiff = heightDiff;
    }

    public Materials getMaterials() {
        Materials totalMaterials = new Materials();
        
        totalMaterials.put(bridge.getData().getMaterialsForPart(type, side));
        
        if (type == BridgePartType.SUPPORT) {
            Materials extensionMaterials = bridge.getData().getMaterialsForPart(BridgePartType.EXTENSION, BridgePartSide.CENTER);
            float relativeHeight =  height - tile.getHeight();
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
        Renderable renderable = bridge.getData().getRenderableForPart(side, type);
        
        g.glPushMatrix();
            if (orientation == EntityOrientation.UP || orientation == EntityOrientation.DOWN) {
                deform2(g, heightDiff / 40f);
            }
            else {
                deform1(g, heightDiff / 40f);
            }
            //4 is required to render bridge in DeedPlanner coordinate system
            g.glTranslatef(4, 0, height / Constants.HEIGHT_MOD);
            g.glTranslatef(-2, 2, 0);
            if (orientation==EntityOrientation.LEFT) {
                g.glRotatef(90, 0, 0, 1);
            }
            else if(orientation==EntityOrientation.DOWN) {
                g.glRotatef(180, 0, 0, 1);
                g.glScalef(-1, 1, 1);
            }
            else if(orientation==EntityOrientation.RIGHT) {
                g.glRotatef(270, 0, 0, 1);
                g.glScalef(-1, 1, 1);
            }
            
            if (side == BridgePartSide.RIGHT) {
                g.glScalef(-1, 1, 1);
            }
            g.glTranslatef(2, -2, 0);
            renderable.render(g);
            if (type == BridgePartType.SUPPORT) {
                Renderable extensionRenderable = bridge.getData().getRenderableForPart(side, BridgePartType.EXTENSION);
                float relativeHeight =  height - tile.getHeight();
                relativeHeight -= bridge.getData().getSupportHeight();
                g.glTranslatef(0, 0, -bridge.getData().getSupportHeight() / Constants.HEIGHT_MOD);
                while (relativeHeight > 0) {
                    extensionRenderable.render(g);
                    g.glTranslatef(0, 0, -20 / Constants.HEIGHT_MOD);
                    relativeHeight -= 20;
                }
            }
        g.glPopMatrix();
    }
    
    private void deform1(GL2 g, float scale) {
        deformMatrix[2] = scale;
        deformMatrix[6] = 0;
        g.glMultMatrixf(deformMatrix, 0);
    }
    
    private void deform2(GL2 g, float scale) {
        deformMatrix[2] = 0;
        deformMatrix[6] = scale;
        g.glMultMatrixf(deformMatrix, 0);
    }

    public TileEntity deepCopy() {
        return new BridgePart(bridge, tile, side, type, orientation, height, heightDiff);
    }

    public void serialize(Document doc, Element root) {
        //no serialization of single bridge parts
    }
    
    public BridgePartSide getSide() {
        return side;
    }
    
    public BridgePartType getType() {
        return type;
    }
    
    public EntityOrientation getOrientation() {
        return orientation;
    }
    
    public void destroy() {
        bridge.destroy();
    }
    
}
