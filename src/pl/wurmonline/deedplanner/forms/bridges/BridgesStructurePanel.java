package pl.wurmonline.deedplanner.forms.bridges;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import pl.wurmonline.deedplanner.data.Tile;

public class BridgesStructurePanel extends BridgesPanel {

    private final MouseListener selectedSegmentListener;
    private final ItemListener selectedValueListener;
    
    private ButtonGroup buttonGroup;
    private int selectedValue;
    
    private BridgeSegmentLabel[] validSegments;
    private BridgeSegmentLabel activatedSegment;
    
    public BridgesStructurePanel(BridgesConstructor constructor) {
        super(constructor);
        initComponents();
        
        selectedSegmentListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (activatedSegment != null) {
                    activatedSegment.setBorder(null);
                }
                activatedSegment = (BridgeSegmentLabel) e.getSource();
                activatedSegment.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            }
        };
        
        selectedValueListener = (ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                return;
            }
            selectedValue = Integer.parseInt(((JRadioButton) e.getSource()).getText());
        };
        
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
        int bridgeLength = Math.max(Math.abs(endX - startX), Math.abs(endY - startY)) - 2;
        
        bridgeInfoLabel.setText("Planned bridge length: "+bridgeLength+", width: "+bridgeWidth);
        
        activatedSegment = null;
        buttonGroup = new ButtonGroup();
        
        bridgePartsPanel.removeAll();
        
        validSegments = Arrays.stream(BridgeSegment.getValidSegmentsFor(getConstructor().getBridgeSpecs().data))
                .map(BridgeSegmentLabel::new)
                .peek(bridgePartsPanel::add)
                .peek(label -> label.addMouseListener(selectedSegmentListener))
                .toArray(BridgeSegmentLabel[]::new);
        
        bridgePartsPanel.validate();
        
        additionalDataPanel.removeAll();
        
        Arrays.stream(getConstructor().getBridgeSpecs().type.getAdditionalParameters())
                .forEach(i -> addRadio(i));
        
        additionalDataPanel.validate();
        JRadioButton firstRadio = (JRadioButton) additionalDataPanel.getComponent(0);
        firstRadio.setSelected(true);
    }
    
    private void addRadio(int value) {
        JRadioButton radio = new JRadioButton();
        radio.setText(Integer.toString(value));
        radio.addItemListener(selectedValueListener);
        buttonGroup.add(radio);
        additionalDataPanel.add(radio);
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
            .addGap(0, 90, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bridgePlanPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bridgeInfoLabel)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(0, 23, Short.MAX_VALUE))
                    .addComponent(additionalDataPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bridgePartsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bridgePartsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(additionalDataPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel additionalDataPanel;
    private javax.swing.JLabel bridgeInfoLabel;
    private javax.swing.JPanel bridgePartsPanel;
    private javax.swing.JPanel bridgePlanPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables

}
