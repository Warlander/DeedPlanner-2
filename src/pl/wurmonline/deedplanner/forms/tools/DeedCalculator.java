/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pl.wurmonline.deedplanner.forms.tools;

import pl.wurmonline.deedplanner.util.SwingUtils;

public class DeedCalculator extends javax.swing.JFrame {

    private boolean saved = false;
    private int savedWidth;
    private int savedHeight;
    private int savedPaidPerimWidth;
    private int savedPaidPerimHeight;
    private int savedCreationCost;
    private int savedUnkeepCost;
    
    public DeedCalculator() {
        initComponents();
        SwingUtils.centerFrame(this);
        calculate(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        freedomRadio = new javax.swing.JRadioButton();
        epicRadio = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        westSize = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        northSize = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        eastSize = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        southSize = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        perimeterSize = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        resultArea = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        guardsAmount = new javax.swing.JSpinner();
        saveButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Deed Calculator");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setText("Server");

        buttonGroup1.add(freedomRadio);
        freedomRadio.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        freedomRadio.setSelected(true);
        freedomRadio.setText("Freedom");
        freedomRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deedUpdateActionListener(evt);
            }
        });

        buttonGroup1.add(epicRadio);
        epicRadio.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        epicRadio.setText("Epic");
        epicRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deedUpdateActionListener(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Size");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("West:");

        westSize.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        westSize.setModel(new javax.swing.SpinnerNumberModel(5, 5, 999, 1));
        westSize.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                deedUpdateStateListener(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText("North:");

        northSize.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        northSize.setModel(new javax.swing.SpinnerNumberModel(5, 5, 999, 1));
        northSize.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                deedUpdateStateListener(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText("East:");

        eastSize.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        eastSize.setModel(new javax.swing.SpinnerNumberModel(5, 5, 999, 1));
        eastSize.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                deedUpdateStateListener(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel6.setText("South:");

        southSize.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        southSize.setModel(new javax.swing.SpinnerNumberModel(5, 5, 999, 1));
        southSize.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                deedUpdateStateListener(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setText("Perimeter:");

        perimeterSize.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        perimeterSize.setModel(new javax.swing.SpinnerNumberModel(5, 5, 999, 1));
        perimeterSize.setToolTipText("First 5 tiles are free");
        perimeterSize.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                deedUpdateStateListener(evt);
            }
        });

        resultArea.setEditable(false);
        resultArea.setColumns(20);
        resultArea.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        resultArea.setRows(5);
        jScrollPane1.setViewportView(resultArea);

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setText("Guards:");

        guardsAmount.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        guardsAmount.setModel(new javax.swing.SpinnerNumberModel(0, 0, 999, 1));
        guardsAmount.setToolTipText("");
        guardsAmount.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                deedUpdateStateListener(evt);
            }
        });

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deedUpdateActionListener(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(westSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(northSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(7, 7, 7)
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(eastSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(southSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(perimeterSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(guardsAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(freedomRadio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(epicRadio)
                        .addGap(24, 48, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)
                    .addComponent(saveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(freedomRadio)
                    .addComponent(epicRadio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(eastSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(westSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(northSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(southSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(perimeterSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(guardsAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void deedUpdateActionListener(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deedUpdateActionListener
        if (evt.getSource()==saveButton) {
            calculate(true);
        }
        else {
            calculate(false);
        }
    }//GEN-LAST:event_deedUpdateActionListener

    private void deedUpdateStateListener(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_deedUpdateStateListener
        calculate(false);
    }//GEN-LAST:event_deedUpdateStateListener

    private void calculate(boolean save) {
        final String ENTER = System.getProperty("line.separator");
        StringBuilder build = new StringBuilder();
        
        final boolean freedom = freedomRadio.isSelected();
        
        final int perimeter = (int) perimeterSize.getModel().getValue();
        final int guards = (int) guardsAmount.getModel().getValue();
        
        final int height = getDeedHeight();
        final int width = getDeedWidth();
        
        if (height>4*width || width>height*4) {
            build.append("TOO BIG DIFFERENCE BETWEEN").append(ENTER).append("WIDTH AND HEIGHT!").append(ENTER).append(ENTER);
        }
        
        final int deedRadius = height * width;
        
        final int freePerimHeight = height + 10;
        final int freePerimWidth = width + 10;
        final int freePerimRadius = freePerimHeight * freePerimWidth - deedRadius;
        
        final int paidPerimHeight = height + perimeter * 2;
        final int paidPerimWidth = width + perimeter * 2;
        final int paidPerimRadius = paidPerimHeight * paidPerimWidth - deedRadius - freePerimRadius;
        
        final int guardsCreationCost;
        if (freedom) {
            guardsCreationCost = 20000 * guards;
        }
        else {
            guardsCreationCost = 30000 * guards;
        }
        
        final int guardsUnkeepCost;
        if (freedom) {
            guardsUnkeepCost = 10000 * guards;
        }
        else {
            guardsUnkeepCost = 30000 * guards;
        }
        
        final int creationCost = deedRadius * 100 + paidPerimRadius * 50 + guardsCreationCost;
        final int unkeepCost = deedRadius * 20 + paidPerimRadius * 5 + guardsUnkeepCost;
        
        if (save) {
            saved = true;
            savedWidth = width;
            savedHeight = height;
            savedPaidPerimWidth = paidPerimWidth;
            savedPaidPerimHeight = paidPerimHeight;
            savedCreationCost = creationCost;
            savedUnkeepCost = unkeepCost;
        }
        
        build.append("Total deed size: ").append(width).append("X").append(height).append(ENTER);
        build.append("Including perimeter: ").append(paidPerimWidth).append("X").append(paidPerimHeight).append(ENTER);
        build.append(ENTER);
        build.append("Total creation cost: ").append(priceToString(creationCost)).append(ENTER);
        build.append("Total unkeep cost: ").append(priceToString(unkeepCost));
        if (saved) {
            build.append(ENTER).append(ENTER);
            build.append("Saved deed size: ").append(savedWidth).append("X").append(savedHeight).append(ENTER);
            build.append("Including perimeter: ").append(savedPaidPerimWidth).append("X").append(savedPaidPerimHeight).append(ENTER);
            build.append("Saved creation cost: ").append(priceToString(savedCreationCost)).append(ENTER);
            build.append("Saved unkeep cost: ").append(priceToString(savedUnkeepCost));
            build.append(ENTER);
            build.append("Difference in creation cost: ").append(priceToString(creationCost - savedCreationCost)).append(ENTER);
            build.append("Difference in unkeep cost: ").append(priceToString(unkeepCost - savedUnkeepCost));
        }
        
        resultArea.setText(build.toString());
    }
    
    private int getDeedWidth() {
        final int west = (int) westSize.getModel().getValue();
        final int east = (int) eastSize.getModel().getValue();
        return west + east + 1;
    }
    
    private int getDeedHeight() {
        final int north = (int) northSize.getModel().getValue();
        final int south = (int) southSize.getModel().getValue();
        return north + south + 1;
    }
    
    private String priceToString(int price) {
        StringBuilder build = new StringBuilder();
        
        if (price==0) {
            return build.append("none").toString();
        }
        if (price<0) {
            build.append("-");
        }
        
        price = Math.abs(price);
        final int iron = price%100;
        price/=100;
        final int copper = price%100;
        price/=100;
        final int silver = price%100;
        price/=100;
        final int gold = price;
        
        if (gold>0) {
            build.append(gold).append("g");
        }
        if (silver>0) {
            if (gold>0) build.append(" ");
            build.append(silver).append("s");
        }
        if (copper>0) {
            if (gold>0 || silver>0) build.append(" ");
            build.append(copper).append("c");
        }
        if (iron>0) {
            if (gold>0 || silver>0 || copper>0) build.append(" ");
            build.append(iron).append("i");
        }
        return build.toString();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DeedCalculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DeedCalculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DeedCalculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DeedCalculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DeedCalculator().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JSpinner eastSize;
    private javax.swing.JRadioButton epicRadio;
    private javax.swing.JRadioButton freedomRadio;
    private javax.swing.JSpinner guardsAmount;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner northSize;
    private javax.swing.JSpinner perimeterSize;
    private javax.swing.JTextArea resultArea;
    private javax.swing.JButton saveButton;
    private javax.swing.JSpinner southSize;
    private javax.swing.JSpinner westSize;
    // End of variables declaration//GEN-END:variables
}
