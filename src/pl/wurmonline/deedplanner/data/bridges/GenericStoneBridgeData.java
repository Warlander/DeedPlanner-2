package pl.wurmonline.deedplanner.data.bridges;

import java.util.HashMap;
import pl.wurmonline.deedplanner.data.Materials;
import pl.wurmonline.deedplanner.util.DeedPlannerRuntimeException;
import pl.wurmonline.deedplanner.graphics.wom.Model;
import pl.wurmonline.deedplanner.util.jogl.Renderable;

public abstract class GenericStoneBridgeData extends BridgeData {

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
    
    public GenericStoneBridgeData(String bridgeTexture, String tilingTexture) {
        super(3);
        
        this.narrowFloating = new Model("Data/Bridges/Stone/StoneBridgeFloating.wom");
        narrowFloating.addTextureOverride("BridgeFloatingWMesh", bridgeTexture);
        narrowFloating.addTextureOverride("BridgeFloatingMesh", tilingTexture);
        this.narrowAbutment = new Model("Data/Bridges/Stone/StoneBridgeAbutment.wom");
        narrowAbutment.addTextureOverride("Abutment10_Support_sep36_BridgeMesh", bridgeTexture);
        narrowAbutment.addTextureOverride("Abutment10_Support_sep36_Bridge_TilingMesh", tilingTexture);
        this.narrowDoubleAbutment = new Model("Data/Bridges/Stone/StoneBridgeEnd.wom");
        narrowDoubleAbutment.addTextureOverride("EndWMesh", bridgeTexture);
        narrowDoubleAbutment.addTextureOverride("DoubleAbutmentMesh", tilingTexture);
        this.narrowBracing = new Model("Data/Bridges/Stone/StoneBridgeBracing.wom");
        narrowBracing.addTextureOverride("Bracing10_Support_sep35_BridgeMesh", bridgeTexture);
        narrowBracing.addTextureOverride("Bracing10_Support_sep35_Bridge_TilingMesh", tilingTexture);
        this.narrowDoubleBracing = new Model("Data/Bridges/Stone/StoneBridgeDoubleBracing.wom");
        narrowDoubleBracing.addTextureOverride("DoubleBracingWMesh", bridgeTexture);
        narrowDoubleBracing.addTextureOverride("DoubleBracingMesh", tilingTexture);
        this.narrowCrown = new Model("Data/Bridges/Stone/StoneBridgeCrown.wom");
        narrowCrown.addTextureOverride("Crown10_Support_sep23_BridgeMesh", bridgeTexture);
        narrowCrown.addTextureOverride("Crown10_Support_sep23_Bridge_TilingMesh", tilingTexture);
        this.narrowSupport = new Model("Data/Bridges/Stone/StoneBridgeSupport.wom");
        narrowSupport.addTextureOverride("Support10_Support_sep34_BridgeMesh", bridgeTexture);
        narrowSupport.addTextureOverride("Support10_Support_sep34_Bridge_TilingMesh", tilingTexture);
        this.narrowExtension = new Model("Data/Bridges/Stone/StoneBridgeExtension.wom");
        narrowExtension.addTextureOverride("ExtensionMesh", tilingTexture);

        this.centralFloating = new Model("Data/Bridges/Stone/StoneBridge_FloatingCenter.wom");
        centralFloating.addTextureOverride("FloatingDoubleCenter2Mesh", bridgeTexture);
        centralFloating.addTextureOverride("FloatingDoubleCenterMesh", tilingTexture);
        this.centralAbutment = new Model("Data/Bridges/Stone/StoneBridge_AbutmentCenter.wom");
        centralAbutment.addTextureOverride("AbutmentDoubleCenter2Mesh", bridgeTexture);
        centralAbutment.addTextureOverride("StoneBridgeAbutmentCenterMesh", tilingTexture);
        this.centralDoubleAbutment = new Model("Data/Bridges/Stone/StoneBridge_DoubleAbutmentCenter.wom");
        centralDoubleAbutment.addTextureOverride("DoubleAbutmentCenter2Mesh", bridgeTexture);
        centralDoubleAbutment.addTextureOverride("DoubleAbutmentCenterMesh", tilingTexture);
        this.centralBracing = new Model("Data/Bridges/Stone/StoneBridge_BracingCenter.wom");
        centralBracing.addTextureOverride("BracingDoubleCenter2Mesh", bridgeTexture);
        centralBracing.addTextureOverride("BracingDoubleCenterMesh", tilingTexture);
        this.centralDoubleBracing = new Model("Data/Bridges/Stone/StoneBridge_DoubleBracingCenter.wom");
        centralDoubleBracing.addTextureOverride("DoubleBracingCenter2Mesh", bridgeTexture);
        centralDoubleBracing.addTextureOverride("DoubleBracingCenterMesh", tilingTexture);
        this.centralCrown = new Model("Data/Bridges/Stone/StoneBridge_CrownCenter.wom");
        centralCrown.addTextureOverride("StoneBridgeCrownCenter2Mesh", bridgeTexture);
        centralCrown.addTextureOverride("StoneBridgeCrownCenterMesh", tilingTexture);
        this.centralSupport = new Model("Data/Bridges/Stone/StoneBridge_SupportCenter.wom");
        centralSupport.addTextureOverride("SupportDoubleCenter2Mesh", bridgeTexture);
        centralSupport.addTextureOverride("SupportDoubleCenterMesh", tilingTexture);
        this.centralExtension = new Model("Data/Bridges/Stone/StoneBridge_ExtensionCenter.wom");
        centralExtension.addTextureOverride("ExtensionCenterMesh", tilingTexture);

        this.sideFloating = new Model("Data/Bridges/Stone/StoneBridge_FloatingSide.wom");
        sideFloating.addTextureOverride("FloatingSideWMesh", bridgeTexture);
        sideFloating.addTextureOverride("BridgeFloatingSideMesh", tilingTexture);
        this.sideAbutment = new Model("Data/Bridges/Stone/StoneBridge_AbutmentSide.wom");
        sideAbutment.addTextureOverride("StoneBridgeAbutmentSideWMesh", bridgeTexture);
        sideAbutment.addTextureOverride("StoneBridgeAbutmentSideMesh", tilingTexture);
        this.sideDoubleAbutment = new Model("Data/Bridges/Stone/StoneBridge_AbutmentDoubleRight.wom");
        sideDoubleAbutment.addTextureOverride("AbutmentDoubleTMesh", bridgeTexture);
        sideDoubleAbutment.addTextureOverride("AbutmentDoubleMesh", tilingTexture);
        this.sideBracing = new Model("Data/Bridges/Stone/StoneBridge_BracingDoubleSide.wom");
        sideBracing.addTextureOverride("DoubleBracingSideWMesh", bridgeTexture);
        sideBracing.addTextureOverride("DoubleBracingSideMesh", tilingTexture);
        this.sideDoubleBracing = new Model("Data/Bridges/Stone/StoneBridge_BracingDoubleRight.wom");
        sideDoubleBracing.addTextureOverride("BracingDoubleRightWMesh", bridgeTexture);
        sideDoubleBracing.addTextureOverride("BracingDoubleRightTMesh", tilingTexture);
        this.sideCrown = new Model("Data/Bridges/Stone/StoneBridge_CrownDouble.wom");
        sideCrown.addTextureOverride("CrownDoubleWMesh", bridgeTexture);
        sideCrown.addTextureOverride("CrownDoubleTMesh", tilingTexture);
        this.sideSupport = new Model("Data/Bridges/Stone/StoneBridge_SupportDoubleSide.wom");
        sideCrown.addTextureOverride("SupportDoubleRightWMesh", bridgeTexture);
        sideCrown.addTextureOverride("SupportDoubleRightTMesh", tilingTexture);
        this.sideExtension = new Model("Data/Bridges/Stone/StoneBridge_ExtensionSide.wom");
        sideExtension.addTextureOverride("ExtensionDoubleTMesh", tilingTexture);
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
    
}
