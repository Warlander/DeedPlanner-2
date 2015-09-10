package pl.wurmonline.deedplanner.data;

import java.util.HashMap;
import pl.wurmonline.deedplanner.util.jogl.Mesh;
import pl.wurmonline.deedplanner.util.jogl.Tex;

public class MarbleBridgeData extends BridgeData {

    private final Tex marbleTex;
    
    private final Mesh narrowAbutment;
    private final Mesh narrowBracing;
    private final Mesh narrowDoubleBracing;
    private final Mesh narrowCrown;
    private final Mesh narrowSupport;
    
//    private final Mesh centralAbutment;
//    private final Mesh centralBracing;
//    private final Mesh centralDoubleBracing;
//    private final Mesh centralCrown;
//    private final Mesh centralSupport;
//    
//    private final Mesh leftAbutment;
//    private final Mesh leftBracing;
//    private final Mesh leftDoubleBracing;
//    private final Mesh leftCrown;
//    private final Mesh leftSupport;
//    
//    private final Mesh rightAbutment;
//    private final Mesh rightBracing;
//    private final Mesh rightDoubleBracing;
//    private final Mesh rightCrown;
//    private final Mesh rightSupport;
    
    public MarbleBridgeData() {
        super(3);
        
        this.marbleTex = Tex.getTexture("Data/Bridges/Marble/bridgeTilingMarble.png");
        
        this.narrowAbutment = new Mesh("Data/Bridges/Marble/AbutmentMarble.dae", "marble", marbleTex, 1);
        this.narrowBracing = new Mesh("Data/Bridges/Marble/BracingMarble.dae", "marble", marbleTex, 1);
        this.narrowDoubleBracing = new Mesh("Data/Bridges/Marble/DoubleBracingMarble.dae", "marble", marbleTex, 1);
        this.narrowCrown = new Mesh("Data/Bridges/Marble/CrownMarble.dae", "marble", marbleTex, 1);
        this.narrowSupport = new Mesh("Data/Bridges/Marble/SupportMarble.dae", "marble", marbleTex, 1);
    }
    
    protected void prepareMaterialsMap(HashMap<BridgePartType, Materials> materials) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isCompatibleType(BridgeType type) {
        return type == BridgeType.FLAT || type == BridgeType.ARCHED;
    }

    public void constructBridge(Map map, Bridge bridge, int startX, int startY, int endX, int endY, BridgeType type, BridgePartType[] segments, int additionalData) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Mesh getMeshForPart(BridgePartSide orientation, BridgePartType type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getSupportHeight() {
        return 80;
    }
    
}
