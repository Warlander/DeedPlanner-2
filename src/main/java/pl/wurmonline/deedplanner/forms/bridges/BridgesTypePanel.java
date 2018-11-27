package pl.wurmonline.deedplanner.forms.bridges;

import javax.swing.DefaultListModel;
import pl.wurmonline.deedplanner.data.Tile;
import pl.wurmonline.deedplanner.data.bridges.BridgeData;
import pl.wurmonline.deedplanner.data.bridges.BridgeType;

public class BridgesTypePanel extends BridgesPanel {

    public BridgesTypePanel(BridgesConstructor constructor) {
        super(constructor);
        initComponents();
        
        typesList.addListSelectionListener((evt) -> {
            getConstructor().setBridgeSpecs(typesList.getSelectedValue());
            getConstructor().setCanAdvance(true);
        });
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
        
        DefaultListModel<BridgeListItem> model = new DefaultListModel<>();
        for (BridgeType type : BridgeType.values()) {
            if (bridgeLength == 1 && type == BridgeType.ARCHED) {
                break;
            }
            
            for (BridgeData data : BridgeData.getAllBridgesData()) {
                if (data.isCompatibleType(type) && data.getMaxWidth() >= bridgeWidth) {
                    model.addElement(new BridgeListItem(type, data));
                }
            }
        }
        typesList.setModel(model);
        
        if (getConstructor().getBridgeSpecs() != null) {
            getConstructor().setCanAdvance(true);
        }
    }
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        typesList = new javax.swing.JList<>();

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Stage 1 - select bridge type");

        typesList.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        typesList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                typesListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(typesList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void typesListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_typesListMouseClicked
        if (evt.getClickCount() == 2) {
            getConstructor().advanceIfAllowed();
        }
    }//GEN-LAST:event_typesListMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<BridgeListItem> typesList;
    // End of variables declaration//GEN-END:variables

}
