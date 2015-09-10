package pl.wurmonline.deedplanner.data;

public class Bridge {
    
    public static Bridge createBridge(Map map, Tile firstTile, Tile secondTile, BridgeData data, BridgeType type, BridgePartType[] segments, int additionalData) {
        if (!data.isCompatibleType(type)) {
            return null;
        }
        
        int startX = Math.min(firstTile.getX(), secondTile.getX());
        int endX = Math.max(firstTile.getX(), secondTile.getX());
        int startY = Math.min(firstTile.getY(), secondTile.getY());
        int endY = Math.max(firstTile.getY(), secondTile.getY());
        
        int maxWidth = data.getMaxWidth() - 1;
        if (maxWidth < endX - startX && maxWidth < endY - startY) {
            return null;
        }
        
        int width = endX - startX + 1;
        int height = endY - startY + 1;
        
        Tile[] bridgeTiles = new Tile[width * height];
        int i = 0;
        
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                bridgeTiles[i] = map.getTile(x, y);
                i++;
            }
        }
        
        Bridge bridge = new Bridge(data, type, bridgeTiles, additionalData);
        data.constructBridge(map, bridge, startX, startY, endX, endY, type, segments, additionalData);
        
        return bridge;
    }
    
    private final BridgeData data;
    private final BridgeType type;
    private final Tile[] bridgeTiles;
    private final int additionalData;
    
    public Bridge(BridgeData data, BridgeType type, Tile[] bridgeTiles, int additionalData) {
        this.data = data;
        this.type = type;
        this.bridgeTiles = bridgeTiles;
        this.additionalData = additionalData;
    }
    
    public BridgeData getData() {
        return data;
    }
    
}
