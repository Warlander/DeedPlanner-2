package pl.wurmonline.deedplanner.forms.bridges;

import java.awt.Dimension;
import javax.swing.JLabel;

class BridgeSegmentLabel extends JLabel {
    
    private BridgeSegment segment;
    
    public BridgeSegmentLabel(BridgeSegment segment) {
        super(segment.getDefaultIcon());
        setSize(new Dimension(32, 32));
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
