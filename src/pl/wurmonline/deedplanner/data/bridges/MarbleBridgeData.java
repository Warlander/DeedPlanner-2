package pl.wurmonline.deedplanner.data.bridges;

import java.util.HashMap;
import pl.wurmonline.deedplanner.data.Materials;
import pl.wurmonline.deedplanner.util.DeedPlannerRuntimeException;
import pl.wurmonline.deedplanner.util.jogl.Mesh;
import pl.wurmonline.deedplanner.util.jogl.Renderable;
import pl.wurmonline.deedplanner.util.jogl.Tex;

public class MarbleBridgeData extends BridgeData {

    private final Tex marbleTex;
    
    private final Mesh narrowFloating;
    private final Mesh narrowAbutment;
    private final Mesh narrowDoubleAbutment;
    private final Mesh narrowBracing;
    private final Mesh narrowDoubleBracing;
    private final Mesh narrowCrown;
    private final Mesh narrowSupport;
    private final Mesh narrowExtension;
    
    private final Mesh centralFloating;
    private final Mesh centralAbutment;
    private final Mesh centralDoubleAbutment;
    private final Mesh centralBracing;
    private final Mesh centralDoubleBracing;
    private final Mesh centralCrown;
    private final Mesh centralSupport;
    private final Mesh centralExtension;
    
    private final Mesh sideFloating;
    private final Mesh sideAbutment;
    private final Mesh sideDoubleAbutment;
    private final Mesh sideBracing;
    private final Mesh sideDoubleBracing;
    private final Mesh sideCrown;
    private final Mesh sideSupport;
    private final Mesh sideExtension;
    
    public MarbleBridgeData() {
        super(3);
        
        this.marbleTex = Tex.getTexture("Data/Bridges/Marble/bridgeTilingMarble.png");
        
        this.narrowFloating = new Mesh("Data/Bridges/Marble/FloatingMarble.dae", "marble", marbleTex, 1);
        this.narrowAbutment = new Mesh("Data/Bridges/Marble/AbutmentMarble.dae", "marble", marbleTex, 1);
        this.narrowDoubleAbutment = new Mesh("Data/Bridges/Marble/DoubleAbutmentMarble.dae", "marble", marbleTex, 1);
        this.narrowBracing = new Mesh("Data/Bridges/Marble/BracingMarble.dae", "marble", marbleTex, 1);
        this.narrowDoubleBracing = new Mesh("Data/Bridges/Marble/DoubleBracingMarble.dae", "marble", marbleTex, 1);
        this.narrowCrown = new Mesh("Data/Bridges/Marble/CrownMarble.dae", "marble", marbleTex, 1);
        this.narrowSupport = new Mesh("Data/Bridges/Marble/SupportMarble.dae", "marble", marbleTex, 1);
        this.narrowExtension = new Mesh("Data/Bridges/Marble/ExtensionMarble.dae", "marble", marbleTex, 1);
        
        this.centralFloating = new Mesh("Data/Bridges/Marble/FloatingMarbleCenter.dae", "marble", marbleTex, 1);
        this.centralAbutment = new Mesh("Data/Bridges/Marble/AbutmentMarbleCenter.dae", "marble", marbleTex, 1);
        this.centralDoubleAbutment = new Mesh("Data/Bridges/Marble/AbutmentDoubleCenter.dae", "double", marbleTex, 1);
        this.centralBracing = new Mesh("Data/Bridges/Marble/BracingMarbleCenter.dae", "marble", marbleTex, 1);
        this.centralDoubleBracing = new Mesh("Data/Bridges/Marble/BracingDoubleCenter.dae", "double", marbleTex, 1);
        this.centralCrown = new Mesh("Data/Bridges/Marble/CrownMarbleCenter.dae", "marble", marbleTex, 1);
        this.centralSupport = new Mesh("Data/Bridges/Marble/SupportMarbleCenter.dae", "marble", marbleTex, 1);
        this.centralExtension = new Mesh("Data/Bridges/Marble/ExtensionMarbleCenter.dae", "marble", marbleTex, 1);
        
        this.sideFloating = new Mesh("Data/Bridges/Marble/FloatingMarbleDouble.dae", "marble", marbleTex, 1);
        this.sideAbutment = new Mesh("Data/Bridges/Marble/AbutmentMarbleRight.dae", "marble", marbleTex, 1);
        this.sideDoubleAbutment = new Mesh("Data/Bridges/Marble/DoubleAbutmentDouble.dae", "double", marbleTex, 1);
        this.sideBracing = new Mesh("Data/Bridges/Marble/BracingMarbleRight.dae", "marble", marbleTex, 1);
        this.sideDoubleBracing = new Mesh("Data/Bridges/Marble/DoubleBracingDouble.dae", "double", marbleTex, 1);
        this.sideCrown = new Mesh("Data/Bridges/Marble/CrownMarbleDouble.dae", "marble", marbleTex, 1);
        this.sideSupport = new Mesh("Data/Bridges/Marble/SupportMarbleRight.dae", "marble", marbleTex, 1);
        this.sideExtension = new Mesh("Data/Bridges/Marble/ExtensionMarbleDouble.dae", "marble", marbleTex, 1);
    }
    
    protected void prepareMaterialsMap(HashMap<BridgePartType, Materials> materials) {
        Materials crownMaterials = new Materials();
        crownMaterials.put("Marble bricks", 22);
        crownMaterials.put("Mortar", 10);
        crownMaterials.put("Rock shards", 4);
        crownMaterials.put("Marble keystones", 1);
        materials.put(BridgePartType.CROWN, crownMaterials);
        
        Materials abutmentMaterials = new Materials();
        abutmentMaterials.put("Marble bricks", 52);
        abutmentMaterials.put("Mortar", 40);
        abutmentMaterials.put("Rock shards", 16);
        materials.put(BridgePartType.ABUTMENT, abutmentMaterials);
        
        Materials doubleAbutmentMaterials = new Materials();
        doubleAbutmentMaterials.put("Marble bricks", 72);
        doubleAbutmentMaterials.put("Mortar", 60);
        doubleAbutmentMaterials.put("Rock shards", 24);
        materials.put(BridgePartType.DOUBLE_ABUTMENT, doubleAbutmentMaterials);
        
        Materials bracingMaterials = new Materials();
        bracingMaterials.put("Marble bricks", 37);
        bracingMaterials.put("Mortar", 25);
        bracingMaterials.put("Rock shards", 8);
        materials.put(BridgePartType.BRACING, bracingMaterials);
        
        Materials doubleBracingMaterials = new Materials();
        doubleBracingMaterials.put("Marble bricks", 42);
        doubleBracingMaterials.put("Mortar", 30);
        doubleBracingMaterials.put("Rock shards", 12);
        materials.put(BridgePartType.DOUBLE_BRACING, doubleBracingMaterials);
        
        Materials supportMaterials = new Materials();
        supportMaterials.put("Marble bricks", 102);
        supportMaterials.put("Mortar", 90);
        supportMaterials.put("Rock shards", 36);
        materials.put(BridgePartType.SUPPORT, supportMaterials);
        
        Materials extensionMaterials = new Materials();
        extensionMaterials.put("Marble bricks", 30);
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
        return "marble";
    }
    
}
