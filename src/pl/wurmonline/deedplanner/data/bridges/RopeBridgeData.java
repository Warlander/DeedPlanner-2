package pl.wurmonline.deedplanner.data.bridges;

import java.util.HashMap;
import pl.wurmonline.deedplanner.data.Materials;
import pl.wurmonline.deedplanner.util.DeedPlannerRuntimeException;
import pl.wurmonline.deedplanner.util.jogl.Mesh;
import pl.wurmonline.deedplanner.util.jogl.Renderable;
import pl.wurmonline.deedplanner.util.jogl.Tex;

public class RopeBridgeData extends BridgeData {
    
    private final Tex ropeTex;
    
    private final Mesh narrowCrown;
    private final Mesh narrowAbutment;
    private final Mesh narrowDoubleAbutment;
    
    public RopeBridgeData() {
        super(1);
        
        this.ropeTex = Tex.getTexture("Data/Bridges/Rope/ropebridge.png");
        
        this.narrowCrown = new Mesh("Data/Bridges/Rope/RopeBridgeCrown.dae", "rope", ropeTex, 1);
        this.narrowAbutment = new Mesh("Data/Bridges/Rope/RopeBridgeEnd.dae", "rope", ropeTex, 1);
        this.narrowDoubleAbutment = new Mesh("Data/Bridges/Rope/RopeBridgeDA.dae", "rope", ropeTex, 1);
    }
    
    protected void prepareMaterialsMap(HashMap<BridgePartType, Materials> materials) {
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
    
}
