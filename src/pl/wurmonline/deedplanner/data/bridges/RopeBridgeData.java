package pl.wurmonline.deedplanner.data.bridges;

import java.util.HashMap;
import pl.wurmonline.deedplanner.data.Materials;
import pl.wurmonline.deedplanner.graphics.wom.Model;
import pl.wurmonline.deedplanner.util.DeedPlannerRuntimeException;
import pl.wurmonline.deedplanner.util.jogl.Renderable;

public class RopeBridgeData extends BridgeData {
    
    private final Model narrowCrown;
    private final Model narrowAbutment;
    private final Model narrowDoubleAbutment;
    
    public RopeBridgeData() {
        super(1);
        
        this.narrowCrown = new Model("Data/Bridges/Rope/RopeBridgeCrown.wom");
        this.narrowAbutment = new Model("Data/Bridges/Rope/RopeBridgeEnd.wom");
        this.narrowDoubleAbutment = new Model("Data/Bridges/Rope/RopeBridgeDA.wom");
    }
    
    protected void prepareMaterialsMap(HashMap<BridgePartType, Materials> materials, HashMap<BridgePartSide, Materials> additionalMaterials) {
        Materials crownMaterials = new Materials();
        crownMaterials.put("Planks", 10);
        crownMaterials.put("Mooring ropes", 6);
        crownMaterials.put("Cordage ropes", 4);
        materials.put(BridgePartType.CROWN, crownMaterials);
        
        Materials abutmentMaterials = new Materials();
        abutmentMaterials.put("Planks", 10);
        abutmentMaterials.put("Mooring ropes", 6);
        abutmentMaterials.put("Cordage ropes", 2);
        abutmentMaterials.put("Thick ropes", 2);
        abutmentMaterials.put("Logs", 4);
        materials.put(BridgePartType.ABUTMENT, abutmentMaterials);
        
        Materials doubleAbutmentMaterials = new Materials();
        doubleAbutmentMaterials.put("Planks", 10);
        doubleAbutmentMaterials.put("Mooring ropes", 6);
        doubleAbutmentMaterials.put("Cordage ropes", 2);
        doubleAbutmentMaterials.put("Thick ropes", 4);
        doubleAbutmentMaterials.put("Logs", 8);
        materials.put(BridgePartType.DOUBLE_ABUTMENT, doubleAbutmentMaterials);
        
        Materials twoWallsMaterials = new Materials();
        additionalMaterials.put(BridgePartSide.NARROW, twoWallsMaterials);
    }

    public boolean isCompatibleType(BridgeType type) {
        return type == BridgeType.ROPE;
    }

    public Renderable getRenderableForPart(BridgePartSide side, BridgePartType type) {
        if (side == BridgePartSide.NARROW) {
            switch (type) {
                case CROWN:
                    return narrowCrown;
                case ABUTMENT:
                    return narrowAbutment;
                case DOUBLE_ABUTMENT:
                    return narrowDoubleAbutment;
            }
        }
        
        throw new DeedPlannerRuntimeException("Invalid bridge part");
    }

    public int getSupportHeight() {
        return 0; //no supports
    }

    public String getName() {
        return "rope";
    }
    
}
