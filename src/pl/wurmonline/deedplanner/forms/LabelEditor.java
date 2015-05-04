package pl.wurmonline.deedplanner.forms;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import javax.swing.*;
import pl.wurmonline.deedplanner.data.*;
import pl.wurmonline.deedplanner.logic.LogicLoop;
import pl.wurmonline.deedplanner.logic.TileSelection;
import pl.wurmonline.deedplanner.util.FontWrapper;
import pl.wurmonline.deedplanner.util.jogl.Color;

public class LabelEditor extends javax.swing.JPanel {

    public LabelEditor() {
        initComponents();
        
        int selectedFont = 0;
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        for (java.awt.Font f : GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()) {
            model.addElement(new FontWrapper(f));
            if (f.getName().equals("Arial")) {
                selectedFont = model.getSize()-1;
            }
        }
        fontBox.setModel(model);
        fontBox.setSelectedIndex(selectedFont);
        
        TileSelection.addTileListener((MapFragment frag) -> {
            updatePanel(frag);
        });
        updatePanel(null);
    }
    
    private void updatePanel(MapFragment frag) {
        if (frag==null) {
            newLabelButton.setEnabled(false);
            calculateTileButton.setEnabled(false);
            calculateMapButton.setEnabled(false);
            innerPanel.setVisible(false);
            return;
        }
        calculateTileButton.setEnabled(true);
        calculateMapButton.setEnabled(true);
        Tile singleTile = frag.getSingleTile();
        if (singleTile != null) {
            newLabelButton.setEnabled(true);
            Label label = singleTile.getLabel();
            if (label==null) {
                newLabelButton.setText("Create label on tile");
                innerPanel.setVisible(false);
            }
            else {
                newLabelButton.setText("Edit label on tile");
                innerPanel.setVisible(true);
            }
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
        newLabelButton = new javax.swing.JButton();
        calculateMapButton = new javax.swing.JButton();
        calculateTileButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

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
        sizeSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(18), Integer.valueOf(8), null, Integer.valueOf(1)));

        fontBox.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        fontBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Arial" }));

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
                        .addComponent(labelColorButton, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
                    .addGroup(innerPanelLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sizeSpinner))
                    .addComponent(fontBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(innerPanelLayout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(deleteLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        innerPanelLayout.setVerticalGroup(
            innerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(innerPanelLayout.createSequentialGroup()
                .addContainerGap()
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 149, Short.MAX_VALUE)
                .addComponent(deleteLabel)
                .addContainerGap())
        );

        newLabelButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        newLabelButton.setText(bundle.getString("LabelEditor.newLabelButton.text")); // NOI18N
        newLabelButton.setToolTipText(bundle.getString("LabelEditor.newLabelButton.toolTipText")); // NOI18N
        newLabelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newLabelButtonActionPerformed(evt);
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

        jLabel1.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(bundle.getString("LabelEditor.jLabel1.text")); // NOI18N
        jLabel1.setToolTipText(bundle.getString("LabelEditor.jLabel1.toolTipText")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(innerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(newLabelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(calculateMapButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(calculateTileButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(newLabelButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(innerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void newLabelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newLabelButtonActionPerformed
        Tile t = TileSelection.getSelectedTile();
        if (t!=null) {
            t.setLabel(createLabel());
        }
        updatePanel(TileSelection.getMapFragment());
    }//GEN-LAST:event_newLabelButtonActionPerformed

    private void deleteLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteLabelActionPerformed
        Tile t = TileSelection.getSelectedTile();
        if (t!=null) {
            t.setLabel(null);
        }
        updatePanel(TileSelection.getMapFragment());
    }//GEN-LAST:event_deleteLabelActionPerformed

    private void labelColorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labelColorButtonActionPerformed
        labelColorShow.setBackground(JColorChooser.showDialog(this, "Select label color", labelColorShow.getBackground()));
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

    private Label createLabel() {
        FontWrapper wrapper = (FontWrapper) fontBox.getSelectedItem();
        Font font = wrapper.font;
        int fontSize = (int) sizeSpinner.getValue();
        font = font.deriveFont((float)fontSize);
        return new Label(font, labelTextField.getText(), new Color(labelColorShow.getBackground()));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton calculateMapButton;
    private javax.swing.JButton calculateTileButton;
    private javax.swing.JButton deleteLabel;
    private javax.swing.JComboBox fontBox;
    private javax.swing.JPanel innerPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JButton labelColorButton;
    private javax.swing.JPanel labelColorShow;
    private javax.swing.JTextField labelTextField;
    private javax.swing.JButton newLabelButton;
    private javax.swing.JSpinner sizeSpinner;
    // End of variables declaration//GEN-END:variables
}
