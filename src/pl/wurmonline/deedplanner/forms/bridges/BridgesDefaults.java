package pl.wurmonline.deedplanner.forms.bridges;

import pl.wurmonline.deedplanner.data.bridges.BridgeData;
import pl.wurmonline.deedplanner.data.bridges.BridgePartType;
import static pl.wurmonline.deedplanner.data.bridges.BridgePartType.*;

public final class BridgesDefaults {
    
    private static final BridgePartType[][] woodenArched = new BridgePartType[][] {
        { ABUTMENT, ABUTMENT }, //2
        { ABUTMENT, CROWN, ABUTMENT }, //3
        { ABUTMENT, CROWN, CROWN, ABUTMENT }, //4
        { ABUTMENT, CROWN, CROWN, CROWN, ABUTMENT }, //5
        { ABUTMENT, CROWN, CROWN, CROWN, CROWN, ABUTMENT }, //6
        { ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT }, //7
        { ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT }, //8
        { ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT }, //9
        { ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT }, //10
        { ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT }, //11
        { ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, CROWN, CROWN, CROWN, CROWN, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT }, //12
        { ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT }, //13
        { ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT }, //14
        { ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT }, //15
        { ABUTMENT, CROWN, ABUTMENT, SUPPORT, ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT, SUPPORT, ABUTMENT, CROWN, ABUTMENT }, //16
        { ABUTMENT, CROWN, ABUTMENT, SUPPORT, ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT, SUPPORT, ABUTMENT, CROWN, ABUTMENT }, //17
        { ABUTMENT, CROWN, ABUTMENT, SUPPORT, ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT, SUPPORT, ABUTMENT, CROWN, ABUTMENT }, //18
        { ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT }, //19
        { ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT }, //20
        { ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT }, //21
        { ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT }, //22
        { ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT }, //23
        { ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT }, //24
        { ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT }, //25
        { ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT }, //26
        { ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT }, //27
        { ABUTMENT, CROWN, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, CROWN, ABUTMENT }, //28
        { ABUTMENT, CROWN, ABUTMENT, SUPPORT, ABUTMENT, CROWN, ABUTMENT, SUPPORT, ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT, SUPPORT, ABUTMENT, CROWN, ABUTMENT, SUPPORT, ABUTMENT, CROWN, ABUTMENT }, //29
        { ABUTMENT, CROWN, ABUTMENT, SUPPORT, ABUTMENT, CROWN, ABUTMENT, SUPPORT, ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT, SUPPORT, ABUTMENT, CROWN, ABUTMENT, SUPPORT, ABUTMENT, CROWN, ABUTMENT }, //30
        { ABUTMENT, CROWN, ABUTMENT, SUPPORT, ABUTMENT, CROWN, ABUTMENT, SUPPORT, ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT, SUPPORT, ABUTMENT, CROWN, ABUTMENT, SUPPORT, ABUTMENT, CROWN, ABUTMENT }, //31
        { ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT }, //32
        { ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT }, //33
        { ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT }, //34
        { ABUTMENT, CROWN, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, CROWN, ABUTMENT }, //35
        { ABUTMENT, CROWN, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, CROWN, ABUTMENT }, //36
        { ABUTMENT, CROWN, ABUTMENT, SUPPORT, ABUTMENT, CROWN, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, CROWN, ABUTMENT, SUPPORT, ABUTMENT, CROWN, ABUTMENT }, //37
        { ABUTMENT, CROWN, ABUTMENT, SUPPORT, ABUTMENT, CROWN, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, CROWN, ABUTMENT, SUPPORT, ABUTMENT, ABUTMENT, SUPPORT, ABUTMENT, CROWN, ABUTMENT, SUPPORT, ABUTMENT, CROWN, ABUTMENT } //38
    };
    
    private static final BridgePartType[][] brickArched = new BridgePartType[][] {
        { ABUTMENT, ABUTMENT }, //2
        { ABUTMENT, DOUBLE_BRACING, ABUTMENT }, //3
        { ABUTMENT, BRACING, BRACING, ABUTMENT }, //4
        { ABUTMENT, BRACING, FLOATING, BRACING, ABUTMENT }, //5
        { ABUTMENT, BRACING, FLOATING, FLOATING, BRACING, ABUTMENT }, //6
        { ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT }, //7
        { ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT }, //8
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //9
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //10
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //11
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //12
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //13
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //14
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //15
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //16
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //17
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //18
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //19
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //20
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //21
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //22
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //23
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //24
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //25
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //26
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //27
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //28
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //29
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //30
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //31
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //32
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //33
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //34
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //35
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //36
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //37
        { DOUBLE_ABUTMENT, SUPPORT, ABUTMENT, BRACING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, FLOATING, BRACING, ABUTMENT, SUPPORT, DOUBLE_ABUTMENT }, //38
    };
    
    public static BridgePartType[] getDefaultArchedBridge(BridgeData data, int length) {
        if (length == 1) {
            throw new IllegalArgumentException("Arched bridge cannot be 1 tile long");
        }
        else if (length > 38) {
            throw new IllegalArgumentException("Arched bridge cannot be more than 38 tiles long");
        }
        else if (data == BridgeData.ROPE_BRIDGE) {
            throw new IllegalArgumentException("Rope bridge cannot be arched");
        }
        else if (data == BridgeData.WOODEN_BRIDGE) {
            return woodenArched[length - 1];
        }
        else {
            return brickArched[length - 1];
        }
    }
    
    private BridgesDefaults() {}
    
}
