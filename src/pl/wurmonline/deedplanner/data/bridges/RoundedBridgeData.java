package pl.wurmonline.deedplanner.data.bridges;

import java.util.HashMap;
import pl.wurmonline.deedplanner.data.Materials;

public class RoundedBridgeData extends GenericStoneBridgeData {
    
    public RoundedBridgeData() {
        super("Data/Bridges/Stone/bridgeRounded.dds", "Data/Bridges/Stone/bridgeRoundedTiling.dds");
    }
    
    protected void prepareMaterialsMap(HashMap<BridgePartType, Materials> materials, HashMap<BridgePartSide, Materials> additionalMaterials) {
        Materials crownMaterials = new Materials();
        crownMaterials.put("Rounded stones", 12);
        crownMaterials.put("Mortar", 10);
        crownMaterials.put("Rock shards", 4);
        crownMaterials.put("Stone keystones", 1);
        materials.put(BridgePartType.CROWN, crownMaterials);
        
        Materials abutmentMaterials = new Materials();
        abutmentMaterials.put("Rounded stones", 42);
        abutmentMaterials.put("Mortar", 40);
        abutmentMaterials.put("Rock shards", 16);
        materials.put(BridgePartType.ABUTMENT, abutmentMaterials);
        
        Materials doubleAbutmentMaterials = new Materials();
        doubleAbutmentMaterials.put("Rounded stones", 62);
        doubleAbutmentMaterials.put("Mortar", 60);
        doubleAbutmentMaterials.put("Rock shards", 24);
        materials.put(BridgePartType.DOUBLE_ABUTMENT, doubleAbutmentMaterials);
        
        Materials bracingMaterials = new Materials();
        bracingMaterials.put("Rounded stones", 27);
        bracingMaterials.put("Mortar", 25);
        bracingMaterials.put("Rock shards", 8);
        materials.put(BridgePartType.BRACING, bracingMaterials);
        
        Materials doubleBracingMaterials = new Materials();
        doubleBracingMaterials.put("Rounded stones", 32);
        doubleBracingMaterials.put("Mortar", 30);
        doubleBracingMaterials.put("Rock shards", 12);
        materials.put(BridgePartType.DOUBLE_BRACING, doubleBracingMaterials);
        
        Materials supportMaterials = new Materials();
        supportMaterials.put("Rounded stones", 92);
        supportMaterials.put("Mortar", 90);
        supportMaterials.put("Rock shards", 36);
        materials.put(BridgePartType.SUPPORT, supportMaterials);
        
        Materials extensionMaterials = new Materials();
        extensionMaterials.put("Rounded stones", 30);
        extensionMaterials.put("Mortar", 30);
        extensionMaterials.put("Rock shards", 12);
        materials.put(BridgePartType.EXTENSION, extensionMaterials);
        
        Materials floatingMaterials = new Materials();
        floatingMaterials.put("Rounded stones", 12);
        floatingMaterials.put("Mortar", 10);
        floatingMaterials.put("Rock shards", 4);
        materials.put(BridgePartType.FLOATING, floatingMaterials);
        
        Materials noWallsMaterials = new Materials();
        additionalMaterials.put(BridgePartSide.CENTER, noWallsMaterials);
        
        Materials oneWallMaterials = new Materials();
        oneWallMaterials.put("Rounded stones", 10);
        additionalMaterials.put(BridgePartSide.LEFT, oneWallMaterials);
        additionalMaterials.put(BridgePartSide.RIGHT, oneWallMaterials);
        
        Materials twoWallsMaterials = new Materials();
        twoWallsMaterials.put("Rounded stones", 20);
        additionalMaterials.put(BridgePartSide.NARROW, twoWallsMaterials);
    }

    public String getName() {
        return "rounded stone";
    }
    
}
