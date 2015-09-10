package pl.wurmonline.deedplanner.data;

import java.util.HashMap;
import pl.wurmonline.deedplanner.util.DeedPlannerRuntimeException;
import pl.wurmonline.deedplanner.util.jogl.Mesh;
import pl.wurmonline.deedplanner.util.jogl.Tex;

public class WoodenBridgeData extends BridgeData {

    private final Tex woodenTex;
    
    private final Mesh narrowCrown;
    private final Mesh narrowAbutment;
    private final Mesh narrowSupport;
    
    private final Mesh leftCrown;
    private final Mesh leftAbutment;
    private final Mesh leftSupport;
    
    private final Mesh rightCrown;
    private final Mesh rightAbutment;
    private final Mesh rightSupport;
    
    private final Mesh extension;
    
    public WoodenBridgeData() {
        super(2);
        
        this.woodenTex = Tex.getTexture("Data/Bridges/Wooden/woodbridge.png");
        
        this.narrowCrown = new Mesh("Data/Bridges/Wooden/WoodBridge.dae", "wood", woodenTex, 1);
        this.narrowAbutment = new Mesh("Data/Bridges/Wooden/WoodBridgeAbutmentNarrow.dae", "wood", woodenTex, 1);
        this.narrowSupport = new Mesh("Data/Bridges/Wooden/WoodBridgeSupport.dae", "wood", woodenTex, 1);
        
        this.leftCrown = new Mesh("Data/Bridges/Wooden/WoodBridgeRight.dae", "wood", woodenTex, new float[] {-1, 1, 1});
        this.leftAbutment = new Mesh("Data/Bridges/Wooden/WoodBridgeAbutmentNLeft.dae", "wood", woodenTex, 1);
        
        this.extension = new Mesh("Data/Bridges/Wooden/WoodBridgeExtension.dae", "wood", woodenTex, 1);
    }
    
    protected void prepareMaterialsMap(HashMap<BridgePartType, Materials> materials) {
        Materials crownMaterials = new Materials();
        crownMaterials.put("Wooden beams", 4);
        crownMaterials.put("Planks", 22);
        crownMaterials.put("Shafts", 2);
        crownMaterials.put("Iron ribbons", 2);
        crownMaterials.put("Large nails", 3);
        crownMaterials.put("Small nails", 1);
        materials.put(BridgePartType.CROWN, crownMaterials);
        
        Materials abutmentMaterials = new Materials();
        abutmentMaterials.put("Wooden beams", 8);
        abutmentMaterials.put("Planks", 22);
        abutmentMaterials.put("Shafts", 2);
        abutmentMaterials.put("Iron ribbons", 4);
        abutmentMaterials.put("Large nails", 4);
        abutmentMaterials.put("Small nails", 1);
        materials.put(BridgePartType.ABUTMENT, abutmentMaterials);
        
        Materials supportMaterials = new Materials();
        supportMaterials.put("Wooden beams", 12);
        supportMaterials.put("Planks", 22);
        supportMaterials.put("Shafts", 2);
        supportMaterials.put("Iron ribbons", 6);
        supportMaterials.put("Large nails", 5);
        supportMaterials.put("Small nails", 1);
        materials.put(BridgePartType.SUPPORT, supportMaterials);
        
        Materials extensionMaterials = new Materials();
        extensionMaterials.put("Wooden beams", 4);
        extensionMaterials.put("Iron ribbons", 2);
        extensionMaterials.put("Large nails", 1);
        materials.put(BridgePartType.EXTENSION, extensionMaterials);
    }

    public boolean isCompatibleType(BridgeType type) {
        return type == BridgeType.FLAT || type == BridgeType.ARCHED;
    }

    public void constructBridge(Map map, Bridge bridge, int startX, int startY, int endX, int endY, BridgeType type, BridgePartType[] segments, int additionalData) {
        int bridgeWidth = Math.max(endX - startX, endY - startY) + 1;
        boolean verticalOrientation = (endY - startY) > (endX - startX);
        
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                int currentSegment = verticalOrientation ? y - startY : x - startX;
                BridgePartType segment = segments[currentSegment];
                BridgePartSide side = getPartSide(startX, startY, endX, endY, map.getTile(x, y));
                EntityOrientation orientation = getPartOrientation(segments, verticalOrientation, currentSegment);
                
                map.getTile(x, y).setBridgePart(new BridgePart(bridge, map.getTile(x, y), side, segment, orientation, 80));
            }
        }
    }

    public Mesh getMeshForPart(BridgePartSide side, BridgePartType type) {
        if (side == BridgePartSide.NARROW) {
            switch (type) {
                case CROWN:
                    return narrowCrown;
                case ABUTMENT:
                    return narrowAbutment;
                case SUPPORT:
                    return narrowSupport;
            }
        }
        
        if (type == BridgePartType.EXTENSION) {
            return extension;
        }
        
        throw new DeedPlannerRuntimeException("Invalid bridge part");
    }

    public int getSupportHeight() {
        return 60;
    }
    
}
