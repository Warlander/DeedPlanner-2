package pl.wurmonline.deedplanner.forms;

import java.awt.Font;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import pl.wurmonline.deedplanner.Globals;
import pl.wurmonline.deedplanner.data.*;
import pl.wurmonline.deedplanner.logic.TileSelection;
import pl.wurmonline.deedplanner.util.FontWrapper;
import pl.wurmonline.deedplanner.util.jogl.Color;

public class LabelEditor extends javax.swing.JPanel {

    private static final String MATERIALS_BUILDING = "Materials (building)";
    private static final String MATERIALS_SELECTION = "Materials (selection)";
    
    private Label currentLabel;
    
    public LabelEditor() {
        initComponents();
        
        int selectedFont = 0;
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (FontWrapper wrapper : FontWrapper.getWrappers()) {
            model.addElement(wrapper);
            if (wrapper.getFont().getName().equals("Arial")) {
                selectedFont = model.getSize()-1;
            }
        }
        fontBox.setModel(model);
        fontBox.setSelectedIndex(selectedFont);
        
        labelTextField.getDocument().addDocumentListener(new DocumentListener() {
            
            public void insertUpdate(DocumentEvent e) {
                updateLabel();
            }

            public void removeUpdate(DocumentEvent e) {
                updateLabel();
            }

            public void changedUpdate(DocumentEvent e) {
                updateLabel();
            }
            
        });
        
        TileSelection.addTileListener((MapFragment frag) -> {
            updatePanel(frag);
        });
        updatePanel(null);
    }
    
    public void updatePanel(MapFragment frag) {
        if (frag == null) {
            newTileLabelButton.setEnabled(false);
            newLevelLabelButton.setEnabled(false);
            calculateTileButton.setEnabled(false);
            calculateMapButton.setEnabled(false);
            deleteBuildingButton.setEnabled(false);
            innerPanel.setVisible(false);
            return;
        }
        calculateTileButton.setEnabled(true);
        calculateMapButton.setEnabled(true);
        Tile singleTile = frag.getSingleTile();
        if (singleTile != null) {
            deleteBuildingButton.setEnabled(true);
            newTileLabelButton.setEnabled(true);
            newLevelLabelButton.setEnabled(true);
            calculateTileButton.setText(MATERIALS_BUILDING);
            Label label;
            if (singleTile.getFloorLabel(Globals.floor) != null) {
                label = singleTile.getFloorLabel(Globals.floor);
                labelDescLabel.setText("Current floor label");
            }
            else if (Globals.floor >= 0) {
                label = singleTile.getGlobalSurfaceLabel();
                labelDescLabel.setText("Surface label");
            }
            else {
                label = singleTile.getGlobalCaveLabel();
                labelDescLabel.setText("Cave label");
            }
            
            if (label == null) {
                innerPanel.setVisible(false);
            }
            else {
                setGuiLabel(label);
                innerPanel.setVisible(true);
            }
        }
        else {
            newTileLabelButton.setEnabled(false);
            deleteBuildingButton.setEnabled(false);
            calculateTileButton.setText(MATERIALS_SELECTION);
        }
        repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        innerPanel = new javax.swing.JPanel();
        labelTextField = new javax.swing.JTextField();
        labelColorShow = new javax.swing.JPanel();
        labelColorButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        sizeSpinner = new javax.swing.JSpinner();
        fontBox = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        deleteLabel = new javax.swing.JButton();
        labelDescLabel = new javax.swing.JLabel();
        newTileLabelButton = new javax.swing.JButton();
        calculateMapButton = new javax.swing.JButton();
        calculateTileButton = new javax.swing.JButton();
        deleteBuildingButton = new javax.swing.JButton();
        newLevelLabelButton = new javax.swing.JButton();

        labelTextField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        labelTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle"); // NOI18N
        labelTextField.setText(bundle.getString("LabelEditor.labelTextField.text")); // NOI18N

        labelColorShow.setBackground(new java.awt.Color(255, 255, 255));
        labelColorShow.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout labelColorShowLayout = new javax.swing.GroupLayout(labelColorShow);
        labelColorShow.setLayout(labelColorShowLayout);
        labelColorShowLayout.setHorizontalGroup(
            labelColorShowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        labelColorShowLayout.setVerticalGroup(
            labelColorShowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 21, Short.MAX_VALUE)
        );

        labelColorButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        labelColorButton.setText(bundle.getString("LabelEditor.labelColorButton.text")); // NOI18N
        labelColorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                labelColorButtonActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel6.setText(bundle.getString("LabelEditor.jLabel6.text")); // NOI18N

        sizeSpinner.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        sizeSpinner.setModel(new javax.swing.SpinnerNumberModel(18, 8, null, 1));
        sizeSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sizeSpinnerStateChanged(evt);
            }
        });

        fontBox.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        fontBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Arial" }));
        fontBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fontBoxActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel7.setText(bundle.getString("LabelEditor.jLabel7.text_1")); // NOI18N

        deleteLabel.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        deleteLabel.setForeground(new java.awt.Color(255, 0, 0));
        deleteLabel.setText(bundle.getString("LabelEditor.deleteLabel.text")); // NOI18N
        deleteLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteLabelActionPerformed(evt);
            }
        });

        labelDescLabel.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        labelDescLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelDescLabel.setText(bundle.getString("LabelEditor.labelDescLabel.text_1")); // NOI18N

        javax.swing.GroupLayout innerPanelLayout = new javax.swing.GroupLayout(innerPanel);
        innerPanel.setLayout(innerPanelLayout);
        innerPanelLayout.setHorizontalGroup(
            innerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(innerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(innerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelTextField)
                    .addGroup(innerPanelLayout.createSequentialGroup()
                        .addComponent(labelColorShow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelColorButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(innerPanelLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sizeSpinner))
                    .addComponent(fontBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(innerPanelLayout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(labelDescLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        innerPanelLayout.setVerticalGroup(
            innerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(innerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelDescLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(innerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelColorButton)
                    .addComponent(labelColorShow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(innerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(sizeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fontBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                .addComponent(deleteLabel)
                .addContainerGap())
        );

        newTileLabelButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        newTileLabelButton.setText(bundle.getString("LabelEditor.newTileLabelButton.text")); // NOI18N
        newTileLabelButton.setToolTipText(bundle.getString("LabelEditor.newTileLabelButton.toolTipText")); // NOI18N
        newTileLabelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newTileLabelButtonActionPerformed(evt);
            }
        });

        calculateMapButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        calculateMapButton.setText(bundle.getString("LabelEditor.calculateMapButton.text")); // NOI18N
        calculateMapButton.setToolTipText(bundle.getString("LabelEditor.calculateMapButton.toolTipText")); // NOI18N
        calculateMapButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateMapButtonActionPerformed(evt);
            }
        });

        calculateTileButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        calculateTileButton.setText(bundle.getString("LabelEditor.calculateTileButton.text")); // NOI18N
        calculateTileButton.setToolTipText(bundle.getString("LabelEditor.calculateTileButton.toolTipText")); // NOI18N
        calculateTileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateTileButtonActionPerformed(evt);
            }
        });

        deleteBuildingButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        deleteBuildingButton.setForeground(new java.awt.Color(255, 0, 0));
        deleteBuildingButton.setText(bundle.getString("LabelEditor.deleteBuildingButton.text")); // NOI18N
        deleteBuildingButton.setToolTipText(bundle.getString("LabelEditor.deleteBuildingButton.toolTipText")); // NOI18N
        deleteBuildingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBuildingButtonActionPerformed(evt);
            }
        });

        newLevelLabelButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        newLevelLabelButton.setText(bundle.getString("LabelEditor.newLevelLabelButton.text")); // NOI18N
        newLevelLabelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newLevelLabelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(innerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(newTileLabelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(calculateMapButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(calculateTileButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteBuildingButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(newLevelLabelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(calculateMapButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(calculateTileButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteBuildingButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(newTileLabelButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(newLevelLabelButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(innerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void newTileLabelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newTileLabelButtonActionPerformed
        Tile t = TileSelection.getSelectedTile();
        if (t!=null) {
            if (Globals.floor>=0) {
                t.setGlobalSurfaceLabel(createLabel());
            }
            else {
                t.setGlobalCaveLabel(createLabel());
            }
        }
        updatePanel(TileSelection.getMapFragment());
    }//GEN-LAST:event_newTileLabelButtonActionPerformed

    private void deleteLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteLabelActionPerformed
        Tile t = TileSelection.getSelectedTile();
        
        if (t != null) {
            if (t.getFloorLabel(Globals.floor) != null) {
                t.setFloorLabel(Globals.floor, null);
            }
            else if (Globals.floor>=0) {
                t.setGlobalSurfaceLabel(null);
            }
            else {
                t.setGlobalCaveLabel(null);
            }
        }
        updatePanel(TileSelection.getMapFragment());
    }//GEN-LAST:event_deleteLabelActionPerformed

    private void labelColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labelColorButtonActionPerformed
        labelColorShow.setBackground(JColorChooser.showDialog(this, "Select label color", labelColorShow.getBackground()));
        updateLabel();
    }//GEN-LAST:event_labelColorButtonActionPerformed

    private void calculateMapButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateMapButtonActionPerformed
        Tile tile = TileSelection.getSelectedTile();
        Materials mats = tile.getMap().getTotalMaterials();
        MaterialsReport report = new MaterialsReport(mats);
        report.setVisible(true);
    }//GEN-LAST:event_calculateMapButtonActionPerformed

    private void calculateTileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateTileButtonActionPerformed
        MapFragment frag = TileSelection.getMapFragment();
        Tile singleTile = frag.getSingleTile();
        if (singleTile!=null) {
            HouseResults results = singleTile.getMap().getMaterialsOfBuilding(singleTile);
            if (results!=null) {
                MaterialsReport report = new MaterialsReport(results);
                report.setVisible(true);
            }
            else {
                JOptionPane.showMessageDialog(null, "Invalid building - cannot calculate results.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            MaterialsReport report = new MaterialsReport(frag.getMaterials());
            report.setVisible(true);
        }
    }//GEN-LAST:event_calculateTileButtonActionPerformed

    private void deleteBuildingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBuildingButtonActionPerformed
        MapFragment frag = TileSelection.getMapFragment();
        Tile singleTile = frag.getSingleTile();
        singleTile.getMap().deleteBuildingOnTile(singleTile);
    }//GEN-LAST:event_deleteBuildingButtonActionPerformed

    private void newLevelLabelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newLevelLabelButtonActionPerformed
        Tile t = TileSelection.getSelectedTile();
        
        if (t != null) {
            t.setFloorLabel(Globals.floor, createLabel());
        }
        
        updatePanel(TileSelection.getMapFragment());
    }//GEN-LAST:event_newLevelLabelButtonActionPerformed

    private void sizeSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sizeSpinnerStateChanged
        updateLabel();
    }//GEN-LAST:event_sizeSpinnerStateChanged

    private void fontBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fontBoxActionPerformed
        updateLabel();
    }//GEN-LAST:event_fontBoxActionPerformed

    private Label createLabel() {
        FontWrapper wrapper = (FontWrapper) fontBox.getSelectedItem();
        Font font = wrapper.font;
        int fontSize = (int) sizeSpinner.getValue();
        font = font.deriveFont((float)fontSize);
        return new Label(font, labelTextField.getText(), new Color(labelColorShow.getBackground()));
    }
    
    private void setGuiLabel(Label label) {
        labelTextField.setText(label.text);
        labelColorShow.setBackground(label.color.toAWTColor());
        sizeSpinner.setValue(label.font.getSize());
        fontBox.setSelectedItem(FontWrapper.getWrapper(label.font));
        
        currentLabel = label;
    }
    
    private void updateLabel() {
        if (currentLabel == null) {
            return;
        }
        
        FontWrapper wrapper = (FontWrapper) fontBox.getSelectedItem();
        Font font = wrapper.font;
        int fontSize = (int) sizeSpinner.getValue();
        font = font.deriveFont((float)fontSize);
        
        String text = labelTextField.getText();
        
        Color color = new Color(labelColorShow.getBackground());
        
        currentLabel.font = font;
        currentLabel.text = text;
        currentLabel.color = color;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton calculateMapButton;
    private javax.swing.JButton calculateTileButton;
    private javax.swing.JButton deleteBuildingButton;
    private javax.swing.JButton deleteLabel;
    private javax.swing.JComboBox fontBox;
    private javax.swing.JPanel innerPanel;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JButton labelColorButton;
    private javax.swing.JPanel labelColorShow;
    private javax.swing.JLabel labelDescLabel;
    private javax.swing.JTextField labelTextField;
    private javax.swing.JButton newLevelLabelButton;
    private javax.swing.JButton newTileLabelButton;
    private javax.swing.JSpinner sizeSpinner;
    // End of variables declaration//GEN-END:variables
}
