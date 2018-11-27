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
    STONE_BRIDGE_SUPPORT(BridgeData.STONE_BRIDGE, BridgePartType.SUPPORT, 1, 1, true, "support.png"),
    
    POTTERY_BRIDGE_CROWN(BridgeData.POTTERY_BRIDGE, BridgePartType.CROWN, 2, 2, true, "brick_crown.png"),
    POTTERY_BRIDGE_DOUBLE_ABUTMENT(BridgeData.POTTERY_BRIDGE, BridgePartType.DOUBLE_ABUTMENT, 0, 0, true, "brick_doubleAbutment.png"),
    POTTERY_BRIDGE_DOUBLE_BRACING(BridgeData.POTTERY_BRIDGE, BridgePartType.DOUBLE_BRACING, 1, 1, true, "brick_doubleBracing.png"),
    POTTERY_BRIDGE_ABUTMENT(BridgeData.POTTERY_BRIDGE, BridgePartType.ABUTMENT, 0, 0, false, "brick_abutmentL.png", "brick_abutmentR.png"),
    POTTERY_BRIDGE_BRACING(BridgeData.POTTERY_BRIDGE, BridgePartType.BRACING, 1, 1, false, "brick_bracingL.png", "brick_bracingR.png"),
    POTTERY_BRIDGE_FLOATING(BridgeData.POTTERY_BRIDGE, BridgePartType.FLOATING, 2, 999, true, "brick_floating.png"),
    POTTERY_BRIDGE_SUPPORT(BridgeData.POTTERY_BRIDGE, BridgePartType.SUPPORT, 1, 1, true, "brick_support.png"),
    
    RENDERED_BRIDGE_CROWN(BridgeData.RENDERED_BRIDGE, BridgePartType.CROWN, 2, 2, true, "render_crown.png"),
    RENDERED_BRIDGE_DOUBLE_ABUTMENT(BridgeData.RENDERED_BRIDGE, BridgePartType.DOUBLE_ABUTMENT, 0, 0, true, "render_doubleAbutment.png"),
    RENDERED_BRIDGE_DOUBLE_BRACING(BridgeData.RENDERED_BRIDGE, BridgePartType.DOUBLE_BRACING, 1, 1, true, "render_doubleBracing.png"),
    RENDERED_BRIDGE_ABUTMENT(BridgeData.RENDERED_BRIDGE, BridgePartType.ABUTMENT, 0, 0, false, "render_abutmentL.png", "render_abutmentR.png"),
    RENDERED_BRIDGE_BRACING(BridgeData.RENDERED_BRIDGE, BridgePartType.BRACING, 1, 1, false, "render_bracingL.png", "render_bracingR.png"),
    RENDERED_BRIDGE_FLOATING(BridgeData.RENDERED_BRIDGE, BridgePartType.FLOATING, 2, 999, true, "render_floating.png"),
    RENDERED_BRIDGE_SUPPORT(BridgeData.RENDERED_BRIDGE, BridgePartType.SUPPORT, 1, 1, true, "render_support.png"),
    
    ROUNDED_BRIDGE_CROWN(BridgeData.ROUNDED_BRIDGE, BridgePartType.CROWN, 2, 2, true, "rounded_crown.png"),
    ROUNDED_BRIDGE_DOUBLE_ABUTMENT(BridgeData.ROUNDED_BRIDGE, BridgePartType.DOUBLE_ABUTMENT, 0, 0, true, "rounded_doubleAbutment.png"),
    ROUNDED_BRIDGE_DOUBLE_BRACING(BridgeData.ROUNDED_BRIDGE, BridgePartType.DOUBLE_BRACING, 1, 1, true, "rounded_doubleBracing.png"),
    ROUNDED_BRIDGE_ABUTMENT(BridgeData.ROUNDED_BRIDGE, BridgePartType.ABUTMENT, 0, 0, false, "rounded_abutmentL.png", "rounded_abutmentR.png"),
    ROUNDED_BRIDGE_BRACING(BridgeData.ROUNDED_BRIDGE, BridgePartType.BRACING, 1, 1, false, "rounded_bracingL.png", "rounded_bracingR.png"),
    ROUNDED_BRIDGE_FLOATING(BridgeData.ROUNDED_BRIDGE, BridgePartType.FLOATING, 2, 999, true, "rounded_floating.png"),
    ROUNDED_BRIDGE_SUPPORT(BridgeData.ROUNDED_BRIDGE, BridgePartType.SUPPORT, 1, 1, true, "rounded_support.png"),
    
    SANDSTONE_BRIDGE_CROWN(BridgeData.SANDSTONE_BRIDGE, BridgePartType.CROWN, 2, 2, true, "sand_crown.png"),
    SANDSTONE_BRIDGE_DOUBLE_ABUTMENT(BridgeData.SANDSTONE_BRIDGE, BridgePartType.DOUBLE_ABUTMENT, 0, 0, true, "sand_doubleAbutment.png"),
    SANDSTONE_BRIDGE_DOUBLE_BRACING(BridgeData.SANDSTONE_BRIDGE, BridgePartType.DOUBLE_BRACING, 1, 1, true, "sand_doubleBracing.png"),
    SANDSTONE_BRIDGE_ABUTMENT(BridgeData.SANDSTONE_BRIDGE, BridgePartType.ABUTMENT, 0, 0, false, "sand_abutmentL.png", "sand_abutmentR.png"),
    SANDSTONE_BRIDGE_BRACING(BridgeData.SANDSTONE_BRIDGE, BridgePartType.BRACING, 1, 1, false, "sand_bracingL.png", "sand_bracingR.png"),
    SANDSTONE_BRIDGE_FLOATING(BridgeData.SANDSTONE_BRIDGE, BridgePartType.FLOATING, 2, 999, true, "sand_floating.png"),
    SANDSTONE_BRIDGE_SUPPORT(BridgeData.SANDSTONE_BRIDGE, BridgePartType.SUPPORT, 1, 1, true, "sand_support.png"),
    
    SLATE_BRIDGE_CROWN(BridgeData.SLATE_BRIDGE, BridgePartType.CROWN, 2, 2, true, "slate_crown.png"),
    SLATE_BRIDGE_DOUBLE_ABUTMENT(BridgeData.SLATE_BRIDGE, BridgePartType.DOUBLE_ABUTMENT, 0, 0, true, "slate_doubleAbutment.png"),
    SLATE_BRIDGE_DOUBLE_BRACING(BridgeData.SLATE_BRIDGE, BridgePartType.DOUBLE_BRACING, 1, 1, true, "slate_doubleBracing.png"),
    SLATE_BRIDGE_ABUTMENT(BridgeData.SLATE_BRIDGE, BridgePartType.ABUTMENT, 0, 0, false, "slate_abutmentL.png", "slate_abutmentR.png"),
    SLATE_BRIDGE_BRACING(BridgeData.SLATE_BRIDGE, BridgePartType.BRACING, 1, 1, false, "slate_bracingL.png", "slate_bracingR.png"),
    SLATE_BRIDGE_FLOATING(BridgeData.SLATE_BRIDGE, BridgePartType.FLOATING, 2, 999, true, "slate_floating.png"),
    SLATE_BRIDGE_SUPPORT(BridgeData.SLATE_BRIDGE, BridgePartType.SUPPORT, 1, 1, true, "slate_support.png");

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
