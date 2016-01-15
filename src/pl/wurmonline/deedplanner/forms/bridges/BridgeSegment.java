package pl.wurmonline.deedplanner.forms.bridges;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import pl.wurmonline.deedplanner.data.bridges.BridgeData;
import pl.wurmonline.deedplanner.data.bridges.BridgePartType;

enum BridgeSegment {
    ROPE_BRIDGE_CROWN(BridgeData.ROPE_BRIDGE, BridgePartType.CROWN, 1, 999, true, "RopeCrown.png"),
    ROPE_BRIDGE_DOUBLE_ABUTMENT(BridgeData.ROPE_BRIDGE, BridgePartType.DOUBLE_ABUTMENT, 0, 0, true, "RopeDA.png"),
    ROPE_BRIDGE_ABUTMENT(BridgeData.ROPE_BRIDGE, BridgePartType.ABUTMENT, 0, 0, false, "RopeEndL.png", "RopeEndR.png"),
    
    WOODEN_BRIDGE_CROWN(BridgeData.WOODEN_BRIDGE, BridgePartType.CROWN, 0, 999, true, "WoodCrown.png"),
    WOODEN_BRIDGE_ABUTMENT(BridgeData.WOODEN_BRIDGE, BridgePartType.ABUTMENT, 0, 0, false, "WoodCrown.png"),
    WOODEN_BRIDGE_SUPPORT(BridgeData.WOODEN_BRIDGE, BridgePartType.SUPPORT, 1, 1, true, "WoodSupport.png"),
    
    MARBLE_BRIDGE_CROWN(BridgeData.MARBLE_BRIDGE, BridgePartType.CROWN, 2, 2, true, "mCrown.png"),
    MARBLE_BRIDGE_DOUBLE_ABUTMENT(BridgeData.MARBLE_BRIDGE, BridgePartType.DOUBLE_ABUTMENT, 0, 0, true, "mDoubleAbutment.png"),
    MARBLE_BRIDGE_DOUBLE_BRACING(BridgeData.MARBLE_BRIDGE, BridgePartType.DOUBLE_BRACING, 1, 1, true, "mDoubleBracing.png"),
    MARBLE_BRIDGE_ABUTMENT(BridgeData.MARBLE_BRIDGE, BridgePartType.ABUTMENT, 0, 0, false, "mAbutmentL.png", "mAbutmentR.png"),
    MARBLE_BRIDGE_BRACING(BridgeData.MARBLE_BRIDGE, BridgePartType.BRACING, 1, 1, false, "mBracingL.png", "mBracingR.png"),
    MARBLE_BRIDGE_FLOATING(BridgeData.MARBLE_BRIDGE, BridgePartType.FLOATING, 2, 999, true, "mFloating.png"),
    MARBLE_BRIDGE_SUPPORT(BridgeData.MARBLE_BRIDGE, BridgePartType.SUPPORT, 1, 1, true, "mSupport.png"),
    
    STONE_BRIDGE_CROWN(BridgeData.STONE_BRIDGE, BridgePartType.CROWN, 2, 2, true, "crown.png"),
    STONE_BRIDGE_DOUBLE_ABUTMENT(BridgeData.STONE_BRIDGE, BridgePartType.DOUBLE_ABUTMENT, 0, 0, true, "doubleAbutment.png"),
    STONE_BRIDGE_DOUBLE_BRACING(BridgeData.STONE_BRIDGE, BridgePartType.DOUBLE_BRACING, 1, 1, true, "doubleBracing.png"),
    STONE_BRIDGE_ABUTMENT(BridgeData.STONE_BRIDGE, BridgePartType.ABUTMENT, 0, 0, false, "abutmentL.png", "abutmentR.png"),
    STONE_BRIDGE_BRACING(BridgeData.STONE_BRIDGE, BridgePartType.BRACING, 1, 1, false, "bracingL.png", "bracingR.png"),
    STONE_BRIDGE_FLOATING(BridgeData.STONE_BRIDGE, BridgePartType.FLOATING, 2, 999, true, "floating.png"),
    STONE_BRIDGE_SUPPORT(BridgeData.STONE_BRIDGE, BridgePartType.SUPPORT, 1, 1, true, "support.png");

    public static BridgeSegment[] getValidSegmentsFor(BridgeData data) {
        return Arrays.stream(values())
                .filter((segment) -> segment.data == data)
                .filter((segment) -> segment.type != BridgePartType.FLOATING)
                .toArray(BridgeSegment[]::new);
    }
    
    public static BridgeSegment getValidSegmentFor(BridgeData data, BridgePartType type) {
        return Arrays.stream(values())
                .filter((segment) -> segment.data == data)
                .filter((segment) -> segment.type == type)
                .findFirst()
                .orElse(null);
    }
    
    private final BridgeData data;
    private final BridgePartType type;
    private final int minRange;
    private final int maxRange;
    private final boolean crown;
    private final ImageIcon leftIcon;
    private final ImageIcon rightIcon;
    
    private BridgeSegment(BridgeData data, BridgePartType type, int minRange, int maxRange, boolean crown, String imagePath) {
        this.data = data;
        this.type = type;
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.crown = crown;
        ImageIcon icon = createIcon(imagePath);
        this.leftIcon = icon;
        this.rightIcon = icon;
    }
    
    private BridgeSegment(BridgeData data, BridgePartType type, int minRange, int maxRange, boolean crown, String leftImagePath, String rightImagePath) {
        this.data = data;
        this.type = type;
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.crown = crown;
        this.leftIcon = createIcon(leftImagePath);
        this.rightIcon = createIcon(rightImagePath);
    }
    
    private ImageIcon createIcon(String imagePath) {
        try {
            return new ImageIcon(ImageIO.read(BridgeSegment.class.getResourceAsStream(imagePath)));
        } catch (IOException ex) {
            Logger.getLogger(BridgeSegment.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    BridgePartType getType() {
        return type;
    }
    
    boolean areAcceptableDistances(int distToPrevious, int distToNext) {
        if (distToPrevious == distToNext && !crown) {
            return false;
        }
        else {
            int min = Math.min(distToPrevious, distToNext);
            return min >= minRange && min <= maxRange;
        }
    }
    
    ImageIcon getIconFor(int distToPrevious, int distToNext) {
        if (distToPrevious < distToNext) {
            return leftIcon;
        }
        else {
            return rightIcon;
        }
    }
    
    ImageIcon getDefaultIcon() {
        return leftIcon;
    }
    
}
