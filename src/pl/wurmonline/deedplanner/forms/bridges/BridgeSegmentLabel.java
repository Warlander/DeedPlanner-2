package pl.wurmonline.deedplanner.forms.bridges;

import java.awt.Dimension;
import javax.swing.JLabel;

final class BridgeSegmentLabel extends JLabel {
    
    private BridgeSegment segment;
    
    public BridgeSegmentLabel(BridgeSegment segment) {
        setSize(new Dimension(32, 32));
        
        setSegment(segment);
    }
    
    public BridgeSegmentLabel(BridgeSegment segment, int distToPrevious, int distToNext) {
        setSize(new Dimension(32, 32));
        
        setSegment(segment, distToPrevious, distToNext);
    }
    
    BridgeSegment getSegment() {
        return segment;
    }
    
    void setSegment(BridgeSegment segment) {
        this.segment = segment;
        setIcon(segment.getDefaultIcon());
    }
    
    void setSegment(BridgeSegment segment, int distToPrevious, int distToNext) {
        this.segment = segment;
        setIcon(segment.getIconFor(distToPrevious, distToNext));
    }
    
}
