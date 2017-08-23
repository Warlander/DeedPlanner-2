package pl.wurmonline.deedplanner.forms.bridges;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import pl.wurmonline.deedplanner.data.Tile;
import pl.wurmonline.deedplanner.data.bridges.BridgePartType;
import pl.wurmonline.deedplanner.data.bridges.BridgeType;

public class BridgesStructurePanel extends BridgesPanel {

    private final MouseAdapter currentSegmentListener;
    private final MouseAdapter selectedSegmentListener;
    private final ItemListener selectedValueListener;
    
    private ButtonGroup buttonGroup;
    private int selectedValue;
    
    private BridgeSegmentLabel[] currentSegments;
    
    private BridgeSegmentLabel activatedSegment;
    
    public BridgesStructurePanel(BridgesConstructor constructor) {
        super(constructor);
        initComponents();
        
        currentSegmentListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (activatedSegment == null || getConstructor().getBridgeSpecs().type != BridgeType.FLAT) {
                    return;
                }
                
                BridgeSegment segment = activatedSegment.getSegment();
                BridgeSegmentLabel label = (BridgeSegmentLabel) e.getSource();
                label.setSegment(segment);
                
                BridgePartType[] segments = Arrays.stream(currentSegments)
                        .map(seg -> seg.getSegment().getType())
                        .toArray(BridgePartType[]::new);
                
                int[] distances = getDistances(segments);
                
                for (int i = 0; i < segments.length; i++) {
                    int previousDist = i == 0 ? 0 : distances[i - 1];
                    int nextDist = i == segments.length - 1 ? 0 : distances[i + 1];

                    currentSegments[i].setSegment(currentSegments[i].getSegment(), previousDist, nextDist);
                }
                
                updateErrors();
                mouseMoved(e);
            }
            
            public void mouseMoved(MouseEvent e) {
                BridgeSegmentLabel label = (BridgeSegmentLabel) e.getSource();
                String text = label.getSegment().toString().toLowerCase().replace('_', ' ');
                segmentInfoLabel.setText("Hovered: "+text);
            }
            
            public void mouseExited(MouseEvent e) {
                segmentInfoLabel.setText("Hovered: none");
            }
        };
        
        selectedSegmentListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (activatedSegment != null) {
                    activatedSegment.setBorder(null);
                }
                activatedSegment = (BridgeSegmentLabel) e.getSource();
                activatedSegment.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                
                String text = activatedSegment.getSegment().toString().toLowerCase().replace('_', ' ');
                activeInfoLabel.setText("Activated: "+text);
            }
            
            public void mouseMoved(MouseEvent e) {
                BridgeSegmentLabel label = (BridgeSegmentLabel) e.getSource();
                String text = label.getSegment().toString().toLowerCase().replace('_', ' ');
                hoverInfoLabel.setText("Hovered: "+text);
            }
            
            public void mouseExited(MouseEvent e) {
                hoverInfoLabel.setText("Hovered: none");
            }
        };
        
        selectedValueListener = (ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                return;
            }
            selectedValue = Integer.parseInt(((JRadioButton) e.getSource()).getText());
            updateErrors();
        };
        
        bridgePlanPanel.setLayout(new FlowLayout(0, 0, 0));
        bridgePartsPanel.setLayout(new FlowLayout(0, 0, 0));
        additionalDataPanel.setLayout(new FlowLayout());
    }
    
    protected void awake() {
        Tile startTile = getConstructor().getStartTile();
        Tile endTile = getConstructor().getEndTile();
        int startX = startTile.getX();
        int startY = startTile.getY();
        int endX = endTile.getX();
        int endY = endTile.getY();
        
        int bridgeWidth = Math.min(Math.abs(endX - startX), Math.abs(endY - startY)) + 1;
        int bridgeLength = Math.max(Math.abs(endX - startX), Math.abs(endY - startY)) - 1;
        
        bridgeInfoLabel.setText("Planned bridge length: "+bridgeLength+", width: "+bridgeWidth);
        activeInfoLabel.setText("Activated: none");
        
        bridgePlanPanel.removeAll();
        
        BridgePartType[] segments = BridgesDefaults.getDefaultBridge(getConstructor().getBridgeSpecs(), bridgeLength);
        int[] distances = getDistances(segments);
        currentSegments = new BridgeSegmentLabel[bridgeLength];
        for (int i = 0; i < bridgeLength; i++) {
            int previousDist = i == 0 ? 0 : distances[i - 1];
            int nextDist = i == bridgeLength - 1 ? 0 : distances[i + 1];
            
            JPanel segmentPanel = new JPanel();
            segmentPanel.setLayout(new BoxLayout(segmentPanel, BoxLayout.Y_AXIS));
            
            JLabel indexLabel = new JLabel(Integer.toString(i + 1));
            
            currentSegments[i] = new BridgeSegmentLabel(BridgeSegment.getValidSegmentFor(getConstructor().getBridgeSpecs().data, segments[i]), previousDist, nextDist);
            currentSegments[i].addMouseListener(currentSegmentListener);
            currentSegments[i].addMouseMotionListener(currentSegmentListener);
            segmentPanel.add(currentSegments[i]);
            segmentPanel.add(indexLabel);
            bridgePlanPanel.add(segmentPanel);
        }
        
        bridgePlanPanel.validate();
        
        activatedSegment = null;
        buttonGroup = new ButtonGroup();
        
        bridgePartsPanel.removeAll();
        
        Arrays.stream(BridgeSegment.getValidSegmentsFor(getConstructor().getBridgeSpecs().data))
                .map(BridgeSegmentLabel::new)
                .peek(bridgePartsPanel::add)
                .peek(label -> label.addMouseListener(selectedSegmentListener))
                .peek(label -> label.addMouseMotionListener(selectedSegmentListener))
                .toArray(BridgeSegmentLabel[]::new);
        
        bridgePartsPanel.validate();
        
        additionalDataPanel.removeAll();
        
        Arrays.stream(getConstructor().getBridgeSpecs().type.getAdditionalParameters())
                .forEach(i -> addRadio(i));
        
        additionalDataPanel.validate();
        JRadioButton firstRadio = (JRadioButton) additionalDataPanel.getComponent(0);
        firstRadio.setSelected(true);
        
        updateErrors();
    }
    
    private void addRadio(int value) {
        JRadioButton radio = new JRadioButton();
        radio.setText(Integer.toString(value));
        radio.addItemListener(selectedValueListener);
        buttonGroup.add(radio);
        additionalDataPanel.add(radio);
    }
    
    private int[] getDistances(BridgePartType[] parts) {
        int[] distances = new int[parts.length];
        
        for (int i = 0; i < parts.length; i++) {
            int dist = 0;
            while (true) {
                int lessDist = i - dist;
                int moreDist = i + dist;
                
                if (lessDist < 0 || parts[lessDist] == BridgePartType.SUPPORT) {
                    break;
                }
                else if (moreDist >= parts.length || parts[moreDist] == BridgePartType.SUPPORT) {
                    break;
                }
                dist++;
            }
            distances[i] = dist;
        }
        
        return distances;
    }
    
    private void updateErrors() {
        StringBuilder build = new StringBuilder();
        
        BridgePartType[] segments = Arrays.stream(currentSegments)
                        .map(seg -> seg.getSegment().getType())
                        .toArray(BridgePartType[]::new);
        int[] distances = getDistances(segments);
        
        for (int i = 0; i < segments.length; i++) {
            int previousDist = i == 0 ? 0 : distances[i - 1];
            int nextDist = i == segments.length - 1 ? 0 : distances[i + 1];
            
            BridgeSegment currentSegment = currentSegments[i].getSegment();
            if (!currentSegment.areAcceptableDistances(previousDist, nextDist)) {
                build.append("Error at: ").append(i + 1).append("<br>");
            }
        }
        
        if (build.length() == 0) {
            build.append("None");
            getConstructor().setCanAdvance(true);
            getConstructor().setSegments(segments);
            getConstructor().setAdditionalData(selectedValue);
        }
        else {
            getConstructor().setCanAdvance(false);
        }
        
        errorsLabel.setText("<html>"+build.toString());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        bridgeInfoLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        bridgePlanPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        bridgePartsPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        additionalDataPanel = new javax.swing.JPanel();
        hoverInfoLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        errorsLabel = new javax.swing.JLabel();
        activeInfoLabel = new javax.swing.JLabel();
        segmentInfoLabel = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Stage 2 - plan your bridge");

        bridgeInfoLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        bridgeInfoLabel.setText("Planned bridge placeholder text");

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setText("Schematic of your bridge plan:");

        javax.swing.GroupLayout bridgePlanPanelLayout = new javax.swing.GroupLayout(bridgePlanPanel);
        bridgePlanPanel.setLayout(bridgePlanPanelLayout);
        bridgePlanPanelLayout.setHorizontalGroup(
            bridgePlanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        bridgePlanPanelLayout.setVerticalGroup(
            bridgePlanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setText("Available bridge parts (can be used only in case of flat bridges):");

        javax.swing.GroupLayout bridgePartsPanelLayout = new javax.swing.GroupLayout(bridgePartsPanel);
        bridgePartsPanel.setLayout(bridgePartsPanelLayout);
        bridgePartsPanelLayout.setHorizontalGroup(
            bridgePartsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        bridgePartsPanelLayout.setVerticalGroup(
            bridgePartsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setText("Please select additional data:\n");

        javax.swing.GroupLayout additionalDataPanelLayout = new javax.swing.GroupLayout(additionalDataPanel);
        additionalDataPanel.setLayout(additionalDataPanelLayout);
        additionalDataPanelLayout.setHorizontalGroup(
            additionalDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        additionalDataPanelLayout.setVerticalGroup(
            additionalDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );

        hoverInfoLabel.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        hoverInfoLabel.setText("Hovered: none");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setText("Errors:");

        errorsLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        errorsLabel.setText("None");

        activeInfoLabel.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        activeInfoLabel.setText("Activated: none");

        segmentInfoLabel.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        segmentInfoLabel.setText("Hovered: none");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                    .addComponent(bridgePlanPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(additionalDataPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bridgePartsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bridgeInfoLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(hoverInfoLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(activeInfoLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(errorsLabel)
                            .addComponent(segmentInfoLabel))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bridgeInfoLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bridgePlanPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(segmentInfoLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bridgePartsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hoverInfoLabel)
                    .addComponent(activeInfoLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(additionalDataPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(errorsLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel activeInfoLabel;
    private javax.swing.JPanel additionalDataPanel;
    private javax.swing.JLabel bridgeInfoLabel;
    private javax.swing.JPanel bridgePartsPanel;
    private javax.swing.JPanel bridgePlanPanel;
    private javax.swing.JLabel errorsLabel;
    private javax.swing.JLabel hoverInfoLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel segmentInfoLabel;
    // End of variables declaration//GEN-END:variables

}
