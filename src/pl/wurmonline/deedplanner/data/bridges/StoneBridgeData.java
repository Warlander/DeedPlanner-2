package pl.wurmonline.deedplanner.data.bridges;

import java.util.HashMap;
import pl.wurmonline.deedplanner.data.Materials;
import pl.wurmonline.deedplanner.util.DeedPlannerRuntimeException;
import pl.wurmonline.deedplanner.util.jogl.Mesh;
import pl.wurmonline.deedplanner.util.jogl.Model;
import pl.wurmonline.deedplanner.util.jogl.Renderable;
import pl.wurmonline.deedplanner.util.jogl.Tex;

public class StoneBridgeData extends BridgeData {

    private final Model narrowFloating;
    private final Model narrowAbutment;
    private final Model narrowDoubleAbutment;
    private final Model narrowBracing;
    private final Model narrowDoubleBracing;
    private final Model narrowCrown;
    private final Model narrowSupport;
    private final Model narrowExtension;
    
    private final Model centralFloating;
    private final Model centralAbutment;
    private final Model centralDoubleAbutment;
    private final Model centralBracing;
    private final Model centralDoubleBracing;
    private final Model centralCrown;
    private final Model centralSupport;
    private final Model centralExtension;
    
    private final Model sideFloating;
    private final Model sideAbutment;
    private final Model sideDoubleAbutment;
    private final Model sideBracing;
    private final Model sideDoubleBracing;
    private final Model sideCrown;
    private final Model sideSupport;
    private final Model sideExtension;
    
    public StoneBridgeData() {
        super(3);
        
        this.narrowFloating = new Model("Data/Bridges/Stone/StoneBridgeFloating.wom");
        this.narrowAbutment = new Model("Data/Bridges/Stone/StoneBridgeAbutment.wom");
        this.narrowDoubleAbutment = new Model("Data/Bridges/Stone/StoneBridgeEnd.wom");
        this.narrowBracing = new Model("Data/Bridges/Stone/StoneBridgeBracing.wom");
        this.narrowDoubleBracing = new Model("Data/Bridges/Stone/StoneBridgeDoubleBracing.wom");
        this.narrowCrown = new Model("Data/Bridges/Stone/StoneBridgeCrown.wom");
        this.narrowSupport = new Model("Data/Bridges/Stone/StoneBridgeSupport.wom");
        this.narrowExtension = new Model("Data/Bridges/Stone/StoneBridgeExtension.wom");

        this.centralFloating = new Model("Data/Bridges/Stone/StoneBridge_FloatingCenter.wom");
        this.centralAbutment = new Model("Data/Bridges/Stone/StoneBridge_AbutmentCenter.wom");
        this.centralDoubleAbutment = new Model("Data/Bridges/Stone/StoneBridge_DoubleAbutmentCenter.wom");
        this.centralBracing = new Model("Data/Bridges/Stone/StoneBridge_BracingCenter.wom");
        this.centralDoubleBracing = new Model("Data/Bridges/Stone/StoneBridge_DoubleBracingCenter.wom");
        this.centralCrown = new Model("Data/Bridges/Stone/StoneBridge_CrownCenter.wom");
        this.centralSupport = new Model("Data/Bridges/Stone/StoneBridge_SupportCenter.wom");
        this.centralExtension = new Model("Data/Bridges/Stone/StoneBridge_ExtensionCenter.wom");

        this.sideFloating = new Model("Data/Bridges/Stone/StoneBridge_FloatingSide.wom");
        this.sideAbutment = new Model("Data/Bridges/Stone/StoneBridge_AbutmentSide.wom");
        this.sideDoubleAbutment = new Model("Data/Bridges/Stone/StoneBridge_AbutmentDoubleRight.wom");
        this.sideBracing = new Model("Data/Bridges/Stone/StoneBridge_BracingDoubleSide.wom");
        this.sideDoubleBracing = new Model("Data/Bridges/Stone/StoneBridge_BracingDoubleRight.wom");
        this.sideCrown = new Model("Data/Bridges/Stone/StoneBridge_CrownDouble.wom");
        this.sideSupport = new Model("Data/Bridges/Stone/StoneBridge_SupportDoubleSide.wom");
        
        this.sideExtension = new Model("Data/Bridges/Stone/StoneBridge_ExtensionSide.wom");
    }
    
    protected void prepareMaterialsMap(HashMap<BridgePartType, Materials> materials) {
        Materials crownMaterials = new Materials();
        crownMaterials.put("Stone bricks", 22);
        crownMaterials.put("Mortar", 10);
        crownMaterials.put("Rock shards", 4);
        crownMaterials.put("Stone keystones", 1);
        materials.put(BridgePartType.CROWN, crownMaterials);
        
        Materials abutmentMaterials = new Materials();
        abutmentMaterials.put("Stone bricks", 52);
        abutmentMaterials.put("Mortar", 40);
        abutmentMaterials.put("Rock shards", 16);
        materials.put(BridgePartType.ABUTMENT, abutmentMaterials);
        
        Materials doubleAbutmentMaterials = new Materials();
        doubleAbutmentMaterials.put("Stone bricks", 72);
        doubleAbutmentMaterials.put("Mortar", 60);
        doubleAbutmentMaterials.put("Rock shards", 24);
        materials.put(BridgePartType.DOUBLE_ABUTMENT, doubleAbutmentMaterials);
        
        Materials bracingMaterials = new Materials();
        bracingMaterials.put("Stone bricks", 37);
        bracingMaterials.put("Mortar", 25);
        bracingMaterials.put("Rock shards", 8);
        materials.put(BridgePartType.BRACING, bracingMaterials);
        
        Materials doubleBracingMaterials = new Materials();
        doubleBracingMaterials.put("Stone bricks", 42);
        doubleBracingMaterials.put("Mortar", 30);
        doubleBracingMaterials.put("Rock shards", 12);
        materials.put(BridgePartType.DOUBLE_BRACING, doubleBracingMaterials);
        
        Materials supportMaterials = new Materials();
        supportMaterials.put("Stone bricks", 102);
        supportMaterials.put("Mortar", 90);
        supportMaterials.put("Rock shards", 36);
        materials.put(BridgePartType.SUPPORT, supportMaterials);
        
        Materials extensionMaterials = new Materials();
        extensionMaterials.put("Stone bricks", 30);
        extensionMaterials.put("Mortar", 30);
        extensionMaterials.put("Rock shards", 12);
        materials.put(BridgePartType.EXTENSION, extensionMaterials);
    }

    public boolean isCompatibleType(BridgeType type) {
        return type == BridgeType.FLAT || type == BridgeType.ARCHED;
    }
    
    public Renderable getRenderableForPart(BridgePartSide side, BridgePartType type) {
        if (side == BridgePartSide.NARROW) {
            switch (type) {
                case FLOATING:
                    return narrowFloating;
                case CROWN:
                    return narrowCrown;
                case ABUTMENT:
                    return narrowAbutment;
                case DOUBLE_ABUTMENT:
                    return narrowDoubleAbutment;
                case BRACING:
                    return narrowBracing;
                case DOUBLE_BRACING:
                    return narrowDoubleBracing;
                case SUPPORT:
                    return narrowSupport;
                case EXTENSION:
                    return narrowExtension;
            }
        }
        else if (side == BridgePartSide.LEFT || side == BridgePartSide.RIGHT) {
            switch (type) {
                case FLOATING:
                    return sideFloating;
                case CROWN:
                    return sideCrown;
                case ABUTMENT:
                    return sideAbutment;
                case DOUBLE_ABUTMENT:
                    return sideDoubleAbutment;
                case BRACING:
                    return sideBracing;
                case DOUBLE_BRACING:
                    return sideDoubleBracing;
                case SUPPORT:
                    return sideSupport;
                case EXTENSION:
                    return sideExtension;
            }
        }
        else if (side == BridgePartSide.CENTER) {
            switch (type) {
                case FLOATING:
                    return centralFloating;
                case CROWN:
                    return centralCrown;
                case ABUTMENT:
                    return centralAbutment;
                case DOUBLE_ABUTMENT:
                    return centralDoubleAbutment;
                case BRACING:
                    return centralBracing;
                case DOUBLE_BRACING:
                    return centralDoubleBracing;
                case SUPPORT:
                    return centralSupport;
                case EXTENSION:
                    return centralExtension;
            }
        }
        
        throw new DeedPlannerRuntimeException("Invalid bridge part");
    }

    public int getSupportHeight() {
        return 80;
    }

    public String getName() {
        return "stone";
    }
    
}
