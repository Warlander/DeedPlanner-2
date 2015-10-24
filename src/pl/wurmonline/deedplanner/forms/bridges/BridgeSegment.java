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
    ROPE_BRIDGE_CROWN(BridgeData.ROPE_BRIDGE, BridgePartType.CROWN, 1, 999, "RopeCrown.png"),
    ROPE_BRIDGE_DOUBLE_ABUTMENT(BridgeData.ROPE_BRIDGE, BridgePartType.DOUBLE_ABUTMENT, 0, 0, "RopeDA.png"),
    ROPE_BRIDGE_ABUTMENT(BridgeData.ROPE_BRIDGE, BridgePartType.ABUTMENT, 0, 0, "RopeEndL.png", "RopeEndR.png");

    public static BridgeSegment[] getValidSegmentsFor(BridgeData data) {
        return Arrays.stream(values())
                .filter((segment) -> segment.data == data)
                .toArray(BridgeSegment[]::new);
    }
    
    private final BridgeData data;
    private final BridgePartType type;
    private final int minRange;
    private final int maxRange;
    private final ImageIcon leftIcon;
    private final ImageIcon rightIcon;
    
    private BridgeSegment(BridgeData data, BridgePartType type, int minRange, int maxRange, String imagePath) {
        this.data = data;
        this.type = type;
        this.minRange = minRange;
        this.maxRange = maxRange;
        ImageIcon icon = createIcon(imagePath);
        this.leftIcon = icon;
        this.rightIcon = icon;
    }
    
    private BridgeSegment(BridgeData data, BridgePartType type, int minRange, int maxRange, String leftImagePath, String rightImagePath) {
        this.data = data;
        this.type = type;
        this.minRange = minRange;
        this.maxRange = maxRange;
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
    
    boolean isAcceptableRange(int range) {
        return range >= minRange && range <= maxRange;
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
