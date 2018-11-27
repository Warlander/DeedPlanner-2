package pl.wurmonline.deedplanner.forms;

import pl.wurmonline.deedplanner.Globals;
import pl.wurmonline.deedplanner.data.Map;
import pl.wurmonline.deedplanner.data.Tile;
import pl.wurmonline.deedplanner.forms.bridges.BridgesConstructor;
import pl.wurmonline.deedplanner.logic.TileSelection;

public class BridgesEditor extends javax.swing.JPanel {

    private final Planner planner;
    
    private int startX = 0;
    private int startY = 0;
    private int startFloor = Integer.MIN_VALUE;
    
    private int endX = 0;
    private int endY = 0;
    private int endFloor = Integer.MIN_VALUE;
    
    public BridgesEditor() {
        this(null);
    }
    
    public BridgesEditor(Planner planner) {
        initComponents();
        
        this.planner = planner;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        startTileLabel = new javax.swing.JLabel();
        endTileLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        createBridgeButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        warningsLabel = new javax.swing.JLabel();
        startTileButton = new javax.swing.JButton();
        endTileButton = new javax.swing.JButton();
        destroyBridgeButton = new javax.swing.JButton();

        setFocusable(false);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel1.setText("<html>Bridges creation instruction:<br>\n1. Select starting tile of bridge.<br>\n(the one you are standing on,<br>\ncurrent floor is important)<br><br>\n\n2. Click \"Select start tile\" button.<br><br>\n\n3. Repeat for end tile.<br><br>\n\n4. If everything is correct,<br>\nclick \"Create new bridge!\" button.<br><br>\n\n5. Follow instructions in new<br>\nwindow.<br>");
        jLabel1.setFocusable(false);

        startTileLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        startTileLabel.setText("Start tile: none");
        startTileLabel.setFocusable(false);

        endTileLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        endTileLabel.setText("End tile: none");
        endTileLabel.setFocusable(false);

        createBridgeButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        createBridgeButton.setText("Create new bridge!");
        createBridgeButton.setEnabled(false);
        createBridgeButton.setFocusable(false);
        createBridgeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createBridgeButtonActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Warnings");
        jLabel4.setFocusable(false);

        warningsLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        warningsLabel.setText("No warnings");
        warningsLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        warningsLabel.setFocusable(false);

        startTileButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        startTileButton.setText("Select start tile");
        startTileButton.setFocusable(false);
        startTileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startTileButtonActionPerformed(evt);
            }
        });

        endTileButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        endTileButton.setText("Select end tile");
        endTileButton.setFocusable(false);
        endTileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endTileButtonActionPerformed(evt);
            }
        });

        destroyBridgeButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        destroyBridgeButton.setText("Destroy bridge on start tile");
        destroyBridgeButton.setEnabled(false);
        destroyBridgeButton.setFocusable(false);
        destroyBridgeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                destroyBridgeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jSeparator2)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(startTileButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(warningsLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(createBridgeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(startTileLabel)
                            .addComponent(endTileLabel))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(endTileButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(destroyBridgeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(startTileLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startTileButton)
                .addGap(2, 2, 2)
                .addComponent(endTileLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(endTileButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(createBridgeButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(destroyBridgeButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(warningsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void startTileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startTileButtonActionPerformed
        Tile tile = TileSelection.getSelectedTile();
        if (tile == null) {
            startFloor = Integer.MIN_VALUE;
        }
        else {
            startX = tile.getX();
            startY = tile.getY();
            startFloor = Globals.floor;
        }
        updateState();
    }//GEN-LAST:event_startTileButtonActionPerformed

    private void endTileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endTileButtonActionPerformed
        Tile tile = TileSelection.getSelectedTile();
        if (tile == null) {
            endFloor = Integer.MIN_VALUE;
        }
        else {
            endX = tile.getX();
            endY = tile.getY();
            endFloor = Globals.floor;
        }
        updateState();
    }//GEN-LAST:event_endTileButtonActionPerformed

    private void createBridgeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createBridgeButtonActionPerformed
        updateWarnings();
        if (!createBridgeButton.isEnabled()) {
            return;
        }
        
        Map map = planner.getMapPanel().getMap();
            
        Tile startTile = map.getTile(map.getTile(0, 0), startX, startY);
        Tile endTile = map.getTile(map.getTile(0, 0), endX, endY);
        
        new BridgesConstructor(map, startTile, endTile, startFloor, endFloor);
    }//GEN-LAST:event_createBridgeButtonActionPerformed

    private void destroyBridgeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_destroyBridgeButtonActionPerformed
        if (startFloor == Integer.MIN_VALUE) {
            // no starting tile is set, so returning
            return;
        }
        
        Map map = planner.getMapPanel().getMap();
        Tile startTile = map.getTile(map.getTile(0, 0), startX, startY);
        
        if (startFloor >= 0 && startTile.getBridgePart() != null) {
            startTile.getBridgePart().destroy();
        }
        else if (startFloor < 0 && startTile.getCaveBridgePart() != null) {
            startTile.getCaveBridgePart().destroy();
        }
        
        
        updateState();
    }//GEN-LAST:event_destroyBridgeButtonActionPerformed

    public void updateState() {
        updateTileLabels();
        updateBridgeDeletion();
        updateWarnings();
    }
    
    private void updateTileLabels() {
        if (startFloor == Integer.MIN_VALUE) {
            startTileLabel.setText("Start tile: none");
        }
        else {
            startTileLabel.setText("Start tile X:"+startX + ", Y:" + startY + ", floor "+ startFloor);
        }
        
        if (endFloor == Integer.MIN_VALUE) {
            endTileLabel.setText("End tile: none");
        }
        else {
            endTileLabel.setText("End tile X:"+endX + ", Y:" + endY + ", floor "+ endFloor);
        }
    }
    
    private void updateBridgeDeletion() {
        if (startFloor == Integer.MIN_VALUE) {
            destroyBridgeButton.setEnabled(false);
            // no starting tile is set, so returning
            return;
        }
        
        Map map = planner.getMapPanel().getMap();
        Tile startTile = map.getTile(map.getTile(0, 0), startX, startY);
        
        if (startTile.getCurrentLayerBridgePart() != null) {
            destroyBridgeButton.setEnabled(true);
        }
        else {
            destroyBridgeButton.setEnabled(false);
        }
    }
    
    private void updateWarnings() {
        StringBuilder warningsString = new StringBuilder();
        if (startFloor == Integer.MIN_VALUE) {
            warningsString.append("Start tile is not set<br>");
        }
        if (endFloor == Integer.MIN_VALUE) {
            warningsString.append("End tile is not set<br>");
        }
        
        if ((startFloor >= 0 && endFloor < 0) || (startFloor < 0 && endFloor >= 0)) {
            warningsString.append("Bridge cannot go from surface to the cave<br>");
        }
        
        if (planner == null) {
            warningsString.append("Planner instance is null (should never happen in live version)<br>");
        }
        else {
            Map map = planner.getMapPanel().getMap();
            
            Tile startTile = map.getTile(map.getTile(0, 0), startX, startY);
            Tile endTile = map.getTile(map.getTile(0, 0), endX, endY);
            
            if (startTile == null) {
                warningsString.append("Start tile out of map bounds<br>");
            }
            if (endTile == null) {
                warningsString.append("End tile out of map bounds<br>");
            }
        }
        
        int distX = Math.abs(endX - startX);
        int distY = Math.abs(endY - startY);
        int distMin = Math.min(distX, distY);
        int distMax = Math.max(distX, distY);

        if (distMin > 2) {
            warningsString.append("Bridge cannot be more than 3 tiles wide<br>");
        }
        if (distMax < 2) {
            warningsString.append("Bridge cannot be 0 tiles long<br>");
        }
        if (distMax > 40) {
            warningsString.append("Bridge cannot be longer than 38 tiles<br>");
        }
        
        if (warningsString.length() == 0) {
            warningsString.append("No warnings");
            createBridgeButton.setEnabled(true);
        }
        else {
            createBridgeButton.setEnabled(false);
        }
        
        warningsLabel.setText("<html>" + warningsString.toString());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createBridgeButton;
    private javax.swing.JButton destroyBridgeButton;
    private javax.swing.JButton endTileButton;
    private javax.swing.JLabel endTileLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JButton startTileButton;
    private javax.swing.JLabel startTileLabel;
    private javax.swing.JLabel warningsLabel;
    // End of variables declaration//GEN-END:variables
}
