package pl.wurmonline.deedplanner.data.bridges;

import java.util.HashMap;
import pl.wurmonline.deedplanner.data.Materials;
import pl.wurmonline.deedplanner.util.DeedPlannerRuntimeException;
import pl.wurmonline.deedplanner.util.jogl.Mesh;
import pl.wurmonline.deedplanner.util.jogl.Model;
import pl.wurmonline.deedplanner.util.jogl.Renderable;
import pl.wurmonline.deedplanner.util.jogl.Tex;

public class StoneBridgeData extends BridgeData {

    private final Tex stoneTex;
    
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
        
        this.stoneTex = Tex.getTexture("Data/Bridges/Stone/bridgeTiling.png");
        
        this.narrowFloating = new Model(new Mesh("Data/Bridges/Stone/StoneBridgeFloating.dae", "BridgeFloatingWMesh", stoneTex, 1),
            new Mesh("Data/Bridges/Stone/StoneBridgeFloating.dae", "BridgeFloatingMesh", stoneTex, 1));
        this.narrowAbutment = new Model(new Mesh("Data/Bridges/Stone/StoneBridgeAbutment.dae", "Abutment10_Support_sep36_BridgeMesh", stoneTex, 1),
            new Mesh("Data/Bridges/Stone/StoneBridgeAbutment.dae", "Abutment10_Support_sep36_Bridge_TilingMesh", stoneTex, 1));
        this.narrowDoubleAbutment = new Model(new Mesh("Data/Bridges/Stone/StoneBridgeEnd.dae", "EndWMesh", stoneTex, 1),
            new Mesh("Data/Bridges/Stone/StoneBridgeEnd.dae", "DoubleAbutmentMesh", stoneTex, 1));
        this.narrowBracing = new Model(new Mesh("Data/Bridges/Stone/StoneBridgeBracing.dae", "Bracing10_Support_sep35_BridgeMesh", stoneTex, 1),
            new Mesh("Data/Bridges/Stone/StoneBridgeBracing.dae", "Bracing10_Support_sep35_Bridge_TilingMesh", stoneTex, 1));
        this.narrowDoubleBracing = new Model(new Mesh("Data/Bridges/Stone/StoneBridgeDoubleBracing.dae", "DoubleBracingWMesh", stoneTex, 1),
            new Mesh("Data/Bridges/Stone/StoneBridgeDoubleBracing.dae", "DoubleBracingMesh", stoneTex, 1));
        this.narrowCrown = new Model(new Mesh("Data/Bridges/Stone/StoneBridgeCrown.dae", "Crown10_Support_sep23_BridgeMesh", stoneTex, 1),
            new Mesh("Data/Bridges/Stone/StoneBridgeCrown.dae", "Crown10_Support_sep23_Bridge_TilingMesh", stoneTex, 1));
        this.narrowSupport = new Model(new Mesh("Data/Bridges/Stone/StoneBridgeSupport.dae", "Support10_Support_sep34_BridgeMesh", stoneTex, 1),
            new Mesh("Data/Bridges/Stone/StoneBridgeSupport.dae", "Support10_Support_sep34_Bridge_TilingMesh", stoneTex, 1));
        this.narrowExtension = new Model(new Mesh("Data/Bridges/Stone/StoneBridgeExtension.dae", "ExtensionMesh", stoneTex, 1));

        this.centralFloating = new Model(new Mesh("Data/Bridges/Stone/StoneBridge_FloatingCenter.dae", "FloatingDoubleCenter2Mesh", stoneTex, 1),
            new Mesh("Data/Bridges/Stone/StoneBridge_FloatingCenter.dae", "FloatingDoubleCenterMesh", stoneTex, 1));
        this.centralAbutment = new Model(new Mesh("Data/Bridges/Stone/StoneBridge_AbutmentCenter.dae", "AbutmentDoubleCenter2Mesh", stoneTex, 1),
            new Mesh("Data/Bridges/Stone/StoneBridge_AbutmentCenter.dae", "StoneBridgeAbutmentCenterMesh", stoneTex, 1));
        this.centralDoubleAbutment = new Model(new Mesh("Data/Bridges/Stone/StoneBridge_DoubleAbutmentCenter.dae", "DoubleAbutmentCenter2Mesh", stoneTex, 1),
            new Mesh("Data/Bridges/Stone/StoneBridge_DoubleAbutmentCenter.dae", "DoubleAbutmentCenterMesh", stoneTex, 1));
        this.centralBracing = new Model(new Mesh("Data/Bridges/Stone/StoneBridge_BracingCenter.dae", "BracingDoubleCenter2Mesh", stoneTex, 1),
            new Mesh("Data/Bridges/Stone/StoneBridge_BracingCenter.dae", "BracingDoubleCenterMesh", stoneTex, 1));
        this.centralDoubleBracing = new Model(new Mesh("Data/Bridges/Stone/StoneBridge_DoubleBracingCenter.dae", "DoubleBracingCenter2Mesh", stoneTex, 1),
            new Mesh("Data/Bridges/Stone/StoneBridge_DoubleBracingCenter.dae", "DoubleBracingCenterMesh", stoneTex, 1));
        this.centralCrown = new Model(new Mesh("Data/Bridges/Stone/StoneBridge_CrownCenter.dae", "StoneBridgeCrownCenter2Mesh", stoneTex, 1),
            new Mesh("Data/Bridges/Stone/StoneBridge_CrownCenter.dae", "StoneBridgeCrownCenterMesh", stoneTex, 1));
        this.centralSupport = new Model(new Mesh("Data/Bridges/Stone/StoneBridge_SupportCenter.dae", "SupportDoubleCenter2Mesh", stoneTex, 1),
            new Mesh("Data/Bridges/Stone/StoneBridge_SupportCenter.dae", "SupportDoubleCenterMesh", stoneTex, 1));
        this.centralExtension = new Model(new Mesh("Data/Bridges/Stone/StoneBridge_ExtensionCenter.dae", "ExtensionCenterMesh", stoneTex, 1));

        this.sideFloating = new Model(new Mesh("Data/Bridges/Stone/StoneBridge_FloatingSide.dae", "FloatingSideWMesh", stoneTex, 1),
            new Mesh("Data/Bridges/Stone/StoneBridge_FloatingSide.dae", "BridgeFloatingSideMesh", stoneTex, 1));
        this.sideAbutment = new Model(new Mesh("Data/Bridges/Stone/StoneBridge_AbutmentSide.dae", "StoneBridgeAbutmentSideWMesh", stoneTex, 1),
            new Mesh("Data/Bridges/Stone/StoneBridge_AbutmentSide.dae", "StoneBridgeAbutmentSideMesh", stoneTex, 1));
        this.sideDoubleAbutment = new Model(new Mesh("Data/Bridges/Stone/StoneBridge_AbutmentDoubleRight.dae", "AbutmentDoubleTMesh", stoneTex, 1),
            new Mesh("Data/Bridges/Stone/StoneBridge_AbutmentDoubleRight.dae", "AbutmentDoubleMesh", stoneTex, 1));
        this.sideBracing = new Model(new Mesh("Data/Bridges/Stone/StoneBridge_BracingDoubleSide.dae", "DoubleBracingSideWMesh", stoneTex, 1),
            new Mesh("Data/Bridges/Stone/StoneBridge_BracingDoubleSide.dae", "DoubleBracingSideMesh", stoneTex, 1));
        this.sideDoubleBracing = new Model(new Mesh("Data/Bridges/Stone/StoneBridge_BracingDoubleRight.dae", "BracingDoubleRightWMesh", stoneTex, 1),
            new Mesh("Data/Bridges/Stone/StoneBridge_BracingDoubleRight.dae", "BracingDoubleRightTMesh", stoneTex, 1));
        this.sideCrown = new Model(new Mesh("Data/Bridges/Stone/StoneBridge_CrownDouble.dae", "CrownDoubleWMesh", stoneTex, 1),
            new Mesh("Data/Bridges/Stone/StoneBridge_CrownDouble.dae", "CrownDoubleTMesh", stoneTex, 1));
        this.sideSupport = new Model(new Mesh("Data/Bridges/Stone/StoneBridge_SupportDoubleSide.dae", "SupportDoubleRightWMesh", stoneTex, 1),
            new Mesh("Data/Bridges/Stone/StoneBridge_SupportDoubleSide.dae", "SupportDoubleRightTMesh", stoneTex, 1));
        
        this.sideExtension = new Model(new Mesh("Data/Bridges/Stone/StoneBridge_ExtensionSide.dae", "ExtensionDoubleTMesh", stoneTex, 1));
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
