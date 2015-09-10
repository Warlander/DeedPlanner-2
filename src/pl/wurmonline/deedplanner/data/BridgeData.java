package pl.wurmonline.deedplanner.data;

import java.util.HashMap;
import pl.wurmonline.deedplanner.util.jogl.Mesh;

public abstract class BridgeData {
    
    private final HashMap<BridgePartType, Materials> materials;
    protected final int maxWidth;
    
    public BridgeData(int maxWidth) {
        this.maxWidth = maxWidth;
        
        this.materials = new HashMap<>();
        prepareMaterialsMap(materials);
    }
    
    public final int getMaxWidth() {
        return maxWidth;
    }
    
    public final Materials getMaterialsForPartType(BridgePartType type) {
        return materials.get(type);
    }
    
    public abstract boolean isCompatibleType(BridgeType type);
    public abstract void constructBridge(Map map, Bridge bridge, int startX, int startY, int endX, int endY, BridgeType type, BridgePartType[] segments, int additionalData);
    public abstract Mesh getMeshForPart(BridgePartSide orientation, BridgePartType type);
    public abstract int getSupportHeight();
    
    protected abstract void prepareMaterialsMap(HashMap<BridgePartType, Materials> materials);
    
    protected BridgePartSide getPartSide(int startX, int startY, int endX, int endY, Tile checkedTile) {
        if (startX == endX || startY == endY) {
            return BridgePartSide.NARROW;
        }
        
        int x = checkedTile.getX();
        int y = checkedTile.getY();
        
        if (startX == x || startY == y) {
            return BridgePartSide.LEFT;
        }
        else if (endX == x || endY == y) {
            return BridgePartSide.RIGHT;
        }
        else {
            return BridgePartSide.CENTER;
        }
    }
    
    protected EntityOrientation getPartOrientation(BridgePartType[] segments, boolean isVertical, int segment) {
        BridgePartType nextSegment = segment + 1 >= segments.length ? null : segments[segment + 1];
        
        if (isVertical) {
            if (isSupport(nextSegment)) {
                return EntityOrientation.DOWN;
            }
            else {
                return EntityOrientation.UP;
            }
        }
        else {
            if (isSupport(nextSegment)) {
                return EntityOrientation.RIGHT;
            }
            else {
                return EntityOrientation.LEFT;
            }
        }
    }
    
    private boolean isSupport(BridgePartType type) {
        return type == BridgePartType.SUPPORT || type == null;
    }
    
}
