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
    private int startFloor = -1;
    
    private int endX = 0;
    private int endY = 0;
    private int endFloor = -1;
    
    public BridgesEditor() {
        this(null);
    }
    
    public BridgesEditor(Planner planner) {
        initComponents();
        
        this.planner = planner;
        
        updateState();
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

        jLabel1.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel1.setText("<html>Bridges creation instruction:<br>\n1. Select starting tile of bridge.<br>\n(not the one you are standing on,<br>\nbut first tile of planned bridge,<br>\ncurrent floor is important)<br><br>\n\n2. Click \"Select start tile\" button.<br><br>\n\n3. Repeat for end tile.<br><br>\n\n4. If everything is correct,<br>\nclick \"Create new bridge!\" button.<br><br>\n\n5. Follow instructions in new<br>\nwindow.<br>");

        startTileLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        startTileLabel.setText("Start tile: none");

        endTileLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        endTileLabel.setText("End tile: none");

        createBridgeButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        createBridgeButton.setText("Create new bridge!");
        createBridgeButton.setEnabled(false);
        createBridgeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createBridgeButtonActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Warnings");

        warningsLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        warningsLabel.setText("No warnings");
        warningsLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        startTileButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        startTileButton.setText("Select start tile");
        startTileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startTileButtonActionPerformed(evt);
            }
        });

        endTileButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        endTileButton.setText("Select end tile");
        endTileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endTileButtonActionPerformed(evt);
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
                        .addGap(0, 2, Short.MAX_VALUE))
                    .addComponent(endTileButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(warningsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                .addGap(46, 46, 46)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void startTileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startTileButtonActionPerformed
        Tile tile = TileSelection.getSelectedTile();
        if (tile == null) {
            startFloor = -1;
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
            endFloor = -1;
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
        
        new BridgesConstructor(map, startTile, endTile);
    }//GEN-LAST:event_createBridgeButtonActionPerformed

    private void updateState() {
        updateTileLabels();
        updateWarnings();
    }
    
    private void updateTileLabels() {
        if (startFloor == -1) {
            startTileLabel.setText("Start tile: none");
        }
        else {
            startTileLabel.setText("Start tile X:"+startX + ", Y:" + startY + ", floor "+ startFloor);
        }
        
        if (endFloor == -1) {
            endTileLabel.setText("End tile: none");
        }
        else {
            endTileLabel.setText("End tile X:"+endX + ", Y:" + endY + ", floor "+ endFloor);
        }
    }
    
    private void updateWarnings() {
        StringBuilder warningsString = new StringBuilder();
        if (startFloor == -1) {
            warningsString.append("Start tile is not set<br>");
        }
        if (endFloor == -1) {
            warningsString.append("End tile is not set<br>");
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

        if (distMin > 2) {
            warningsString.append("Bridge cannot be more than 3 tiles wide.<br>");
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
