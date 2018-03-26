package pl.wurmonline.deedplanner.forms;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import pl.wurmonline.deedplanner.Properties;
import pl.wurmonline.deedplanner.util.SwingUtils;

public class SettingsWindow extends javax.swing.JFrame {

    public SettingsWindow() {
        initComponents();
        SwingUtils.centerFrame(this);
        getProperties();
    }
    
    private void getProperties() {
        keyboardFractionUpSelect.getModel().setValue(Properties.keyboardFractionUp);
        mouseFractionUpSelect.getModel().setValue(Properties.mouseFractionUp);
        showGridBox.setSelected(Properties.showGrid);
        translationBox.setSelected(Properties.useTranslation);
        colorblindBox.setSelected(Properties.colorblind);
        tipBox.setSelected(Properties.showTip);
        importBox.setSelected(Properties.showImportWarning);
        scaleUpSelect.getModel().setValue(Properties.scale);
        updatesBox.setSelected(Properties.checkUpdates);
        leftSideInterfaceBox.setSelected(Properties.leftSideInterface);

        mouseFractionFppSelect.getModel().setValue(Properties.mouseFractionFpp);
        cameraRotationFppSelect.getModel().setValue(Properties.cameraRotationFpp);
        mod1FppSelect.getModel().setValue(Properties.mod1Fpp);
        mod2FppSelect.getModel().setValue(Properties.mod2Fpp);

        for (int i=0; i<antialiasingCombo.getItemCount(); i++) {
            int integer = Integer.parseInt((String)antialiasingCombo.getItemAt(i));
            if (integer==Properties.antialiasing) {
                antialiasingCombo.setSelectedIndex(i);
            }
        }
        
        logicFpsSelect.getModel().setValue(Properties.logicFps);
        graphicsFpsSelect.getModel().setValue(Properties.graphicsFps);
        
        for (int i=0; i<iconsCombo.getItemCount(); i++) {
            int integer = Integer.parseInt((String)iconsCombo.getItemAt(i));
            if (integer==Properties.iconSize) {
                iconsCombo.setSelectedIndex(i);
            }
        }
        
        lookCombo.addItem("PGS");
        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            lookCombo.addItem(info.getName());
            if (Properties.lookAndFeel.equals(info.getName())) {
                lookCombo.setSelectedItem(info.getName());
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        showGridBox = new javax.swing.JCheckBox();
        keyboardFractionUpSelect = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        mouseFractionUpSelect = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        mouseFractionFppSelect = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        cameraRotationFppSelect = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        scaleUpSelect = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        mod1FppSelect = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        mod2FppSelect = new javax.swing.JSpinner();
        antialiasingCombo = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        logicFpsSelect = new javax.swing.JSpinner();
        jLabel10 = new javax.swing.JLabel();
        graphicsFpsSelect = new javax.swing.JSpinner();
        jLabel11 = new javax.swing.JLabel();
        iconsCombo = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        lookCombo = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        translationBox = new javax.swing.JCheckBox();
        colorblindBox = new javax.swing.JCheckBox();
        tipBox = new javax.swing.JCheckBox();
        importBox = new javax.swing.JCheckBox();
        updatesBox = new javax.swing.JCheckBox();
        leftSideInterfaceBox = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle"); // NOI18N
        setTitle(bundle.getString("SettingsWindow.title")); // NOI18N
        setResizable(false);

        jButton1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton1.setText(bundle.getString("SettingsWindow.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel1.setText(bundle.getString("SettingsWindow.jLabel1.text_1")); // NOI18N

        showGridBox.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        showGridBox.setSelected(true);
        showGridBox.setText(bundle.getString("SettingsWindow.showGridBox.text")); // NOI18N

        keyboardFractionUpSelect.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        keyboardFractionUpSelect.setModel(new javax.swing.SpinnerNumberModel(1.0d, 0.10000000149011612d, 5.0d, 0.009999999776482582d));

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText(bundle.getString("SettingsWindow.jLabel2.text")); // NOI18N

        mouseFractionUpSelect.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        mouseFractionUpSelect.setModel(new javax.swing.SpinnerNumberModel(0.20000000298023224d, 0.05000000074505806d, 1.0d, 0.009999999776482582d));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText(bundle.getString("SettingsWindow.jLabel3.text")); // NOI18N

        mouseFractionFppSelect.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        mouseFractionFppSelect.setModel(new javax.swing.SpinnerNumberModel(0.20000000298023224d, 0.10000000149011612d, 2.0d, 0.009999999776482582d));

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText(bundle.getString("SettingsWindow.jLabel4.text")); // NOI18N

        cameraRotationFppSelect.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cameraRotationFppSelect.setModel(new javax.swing.SpinnerNumberModel(0.014999999664723873d, 0.0010000000474974513d, 0.10000000149011612d, 0.0010000000474974513d));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText(bundle.getString("SettingsWindow.jLabel5.text")); // NOI18N

        scaleUpSelect.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        scaleUpSelect.setModel(new javax.swing.SpinnerNumberModel(10, 5, 40, 1));

        jLabel6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel6.setText(bundle.getString("SettingsWindow.jLabel6.text")); // NOI18N

        jLabel7.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel7.setText(bundle.getString("SettingsWindow.jLabel7.text")); // NOI18N

        mod1FppSelect.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        mod1FppSelect.setModel(new javax.swing.SpinnerNumberModel(5.0d, 0.10000000149011612d, 10.0d, 0.10000000149011612d));

        jLabel8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel8.setText(bundle.getString("SettingsWindow.jLabel8.text")); // NOI18N

        mod2FppSelect.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        mod2FppSelect.setModel(new javax.swing.SpinnerNumberModel(0.20000000298023224d, 0.10000000149011612d, 10.0d, 0.10000000149011612d));

        antialiasingCombo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        antialiasingCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "16", "8", "4", "2", "0" }));
        antialiasingCombo.setSelectedIndex(3);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel9.setText(bundle.getString("SettingsWindow.jLabel9.text")); // NOI18N

        logicFpsSelect.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        logicFpsSelect.setModel(new javax.swing.SpinnerNumberModel(100, 30, 200, 1));

        jLabel10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel10.setText(bundle.getString("SettingsWindow.jLabel10.text")); // NOI18N

        graphicsFpsSelect.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        graphicsFpsSelect.setModel(new javax.swing.SpinnerNumberModel(45, 30, 60, 1));

        jLabel11.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel11.setText(bundle.getString("SettingsWindow.jLabel11.text")); // NOI18N

        iconsCombo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        iconsCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "64", "48", "32", "24", "16", "0" }));
        iconsCombo.setSelectedIndex(2);

        jLabel12.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel12.setText(bundle.getString("SettingsWindow.jLabel12.text")); // NOI18N

        lookCombo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel13.setText(bundle.getString("SettingsWindow.jLabel13.text")); // NOI18N

        translationBox.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        translationBox.setSelected(true);
        translationBox.setText(bundle.getString("SettingsWindow.translationBox.text")); // NOI18N

        colorblindBox.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        colorblindBox.setText(bundle.getString("SettingsWindow.colorblindBox.text")); // NOI18N

        tipBox.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tipBox.setText(bundle.getString("SettingsWindow.tipBox.text")); // NOI18N

        importBox.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        importBox.setText(bundle.getString("SettingsWindow.importBox.text")); // NOI18N

        updatesBox.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        updatesBox.setSelected(true);
        updatesBox.setText(bundle.getString("SettingsWindow.updatesBox.text")); // NOI18N

        leftSideInterfaceBox.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        leftSideInterfaceBox.setText(bundle.getString("SettingsWindow.leftSideInterfaceBox.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(showGridBox)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(keyboardFractionUpSelect)
                                    .addComponent(scaleUpSelect, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(mouseFractionUpSelect, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(cameraRotationFppSelect, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addComponent(mod2FppSelect, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                    .addComponent(mod1FppSelect)
                                    .addComponent(mouseFractionFppSelect))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel4)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(antialiasingCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(logicFpsSelect)
                                    .addComponent(graphicsFpsSelect, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(iconsCombo, javax.swing.GroupLayout.Alignment.TRAILING, 0, 70, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lookCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel13))
                            .addComponent(translationBox)
                            .addComponent(colorblindBox)
                            .addComponent(tipBox)
                            .addComponent(importBox)
                            .addComponent(updatesBox)
                            .addComponent(leftSideInterfaceBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(filler1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(showGridBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(keyboardFractionUpSelect)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(mouseFractionUpSelect)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(scaleUpSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(mouseFractionFppSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cameraRotationFppSelect)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(mod1FppSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(mod2FppSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(antialiasingCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(logicFpsSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(graphicsFpsSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(iconsCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lookCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(updatesBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(importBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tipBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(colorblindBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(translationBox)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(leftSideInterfaceBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Properties.showGrid = showGridBox.isSelected();
        Properties.useTranslation = translationBox.isSelected();
        Properties.colorblind = colorblindBox.isSelected();
        Properties.showTip = tipBox.isSelected();
        Properties.showImportWarning = importBox.isSelected();
        Properties.keyboardFractionUp = (double) keyboardFractionUpSelect.getModel().getValue();
        Properties.mouseFractionUp = (double) mouseFractionUpSelect.getModel().getValue();
        Properties.scale = (int) scaleUpSelect.getModel().getValue();
        Properties.checkUpdates = updatesBox.isSelected();
        Properties.leftSideInterface = leftSideInterfaceBox.isSelected();
        
        Properties.mouseFractionFpp = (double) mouseFractionFppSelect.getModel().getValue();
        Properties.cameraRotationFpp = (double) cameraRotationFppSelect.getModel().getValue();
        Properties.mod1Fpp = (double) mod1FppSelect.getModel().getValue();
        Properties.mod2Fpp = (double) mod2FppSelect.getModel().getValue();
        
        Properties.antialiasing = Integer.parseInt((String)antialiasingCombo.getSelectedItem());
        
        Properties.logicFps = (int) logicFpsSelect.getModel().getValue();
        Properties.graphicsFps = (int) graphicsFpsSelect.getModel().getValue();
        
        Properties.iconSize = Integer.parseInt((String)iconsCombo.getSelectedItem());
        
        Properties.lookAndFeel = (String) lookCombo.getSelectedItem();
        
        Properties.saveProperties();
        System.exit(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox antialiasingCombo;
    private javax.swing.JSpinner cameraRotationFppSelect;
    private javax.swing.JCheckBox colorblindBox;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JSpinner graphicsFpsSelect;
    private javax.swing.JComboBox iconsCombo;
    private javax.swing.JCheckBox importBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSpinner keyboardFractionUpSelect;
    private javax.swing.JCheckBox leftSideInterfaceBox;
    private javax.swing.JSpinner logicFpsSelect;
    private javax.swing.JComboBox lookCombo;
    private javax.swing.JSpinner mod1FppSelect;
    private javax.swing.JSpinner mod2FppSelect;
    private javax.swing.JSpinner mouseFractionFppSelect;
    private javax.swing.JSpinner mouseFractionUpSelect;
    private javax.swing.JSpinner scaleUpSelect;
    private javax.swing.JCheckBox showGridBox;
    private javax.swing.JCheckBox tipBox;
    private javax.swing.JCheckBox translationBox;
    private javax.swing.JCheckBox updatesBox;
    // End of variables declaration//GEN-END:variables
}
