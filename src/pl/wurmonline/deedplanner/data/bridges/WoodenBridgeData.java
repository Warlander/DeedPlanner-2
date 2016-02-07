package pl.wurmonline.deedplanner.data.bridges;

import java.util.HashMap;
import pl.wurmonline.deedplanner.data.Materials;
import pl.wurmonline.deedplanner.util.DeedPlannerRuntimeException;
import pl.wurmonline.deedplanner.util.jogl.Model;
import pl.wurmonline.deedplanner.util.jogl.Renderable;
import pl.wurmonline.deedplanner.util.jogl.Tex;

public class WoodenBridgeData extends BridgeData {

    private final Model narrowCrown;
    private final Model narrowAbutment;
    private final Model narrowSupport;
    
    private final Model sideCrown;
    private final Model sideAbutment;
    private final Model sideSupport;
    
    private final Model extension;
    
    public WoodenBridgeData() {
        super(2);
        
        this.narrowCrown = new Model("Data/Bridges/Wooden/WoodBridge.wom");
        this.narrowAbutment = new Model("Data/Bridges/Wooden/WoodBridgeAbutmentNarrow.wom");
        this.narrowSupport = new Model("Data/Bridges/Wooden/WoodBridgeSupport.wom");
        
        this.sideCrown = new Model("Data/Bridges/Wooden/WoodBridgeRight.wom");
        this.sideAbutment = new Model("Data/Bridges/Wooden/WoodBridgeAbutmentRight.wom");
        this.sideSupport = new Model("Data/Bridges/Wooden/WoodBridgeSupportRight.wom");
        
        this.extension = new Model("Data/Bridges/Wooden/WoodBridgeExtension.wom");
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

    public Renderable getRenderableForPart(BridgePartSide side, BridgePartType type) {
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
        else if (side == BridgePartSide.LEFT || side == BridgePartSide.RIGHT) {
            switch (type) {
                case CROWN:
                    return sideCrown;
                case ABUTMENT:
                    return sideAbutment;
                case SUPPORT:
                    return sideSupport;
            }
        }
        
        if (type == BridgePartType.EXTENSION) {
            return extension;
        }
        
        throw new DeedPlannerRuntimeException("Invalid bridge part: side "+side+", type: "+type);
    }

    public int getSupportHeight() {
        return 60;
    }

    public String getName() {
        return "wood";
    }
    
}
