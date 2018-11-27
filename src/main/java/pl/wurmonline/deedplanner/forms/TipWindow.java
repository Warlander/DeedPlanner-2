package pl.wurmonline.deedplanner.forms;

import java.util.Random;
import pl.wurmonline.deedplanner.util.SwingUtils;

public class TipWindow extends javax.swing.JFrame {

    private static final String[] allTips;
    
    static {
        allTips = new String[] {
            "There is a possibility to view elevations in all edit modes. It is available in View > Elevation.",
            "Program forum thread is an excellent source of informations about any existing features, bugs and new releases. Make sure to visit it often!",
            "There is possibility to export your in-game deed to DeedPlanner. To do that, you must be a mayor and use proper option on settlement token.",
            "Deeds imported from Wurm Online/Unlimited contain only height differences. You can configure actual height of your deed using File > Resize > Global add height.",
            "You can report any problems with the program in official forum thread.",
            "Make sure to support developer if you like this program! Donation button is available on program exit confirmation box.",
            "You can expand or shrink your deed plan in any direction. When shrinking, watch out to not lose any data as this cannot be reverted!",
            "Program is open sourced and available on Github - everyone can view and edit its code.",
            "In ground edit view, small widget allowing you to select road direction is shown - it works for all kinds of terrain that can be placed diagonally in Wurm Online.",
            "In height edit view, smooth height mode allows you to create smooth line between point A (first one clicked) and B (second one clicked).",
            "Program is in active development - visit program forum thread from time to time, there is a big chance that next version is already out!"
        };
    }
    
    private int currentTip = 0;
    
    public TipWindow() {
        initComponents();
        
        Random random = new Random();
        int tip = random.nextInt(allTips.length);
        showHint(tip);
        
        SwingUtils.centerFrame(this);
    }
    
    private void showHint(int number) {
        if (number < 0 || number >= allTips.length) {
            return;
        }
        
        currentTip = number;
        
        tipCountLabel.setText("Tip " + (number + 1) + "/" + allTips.length);
        tipTextLabel.setText(String.format("<html><div style=\"width:%dpx;\">%s</div><html>", 380, allTips[number]));
        
        previousButton.setEnabled(number > 0);
        nextButton.setEnabled(number < allTips.length - 1);
        
        pack();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        previousButton = new javax.swing.JButton();
        nextButton = new javax.swing.JButton();
        tipCountLabel = new javax.swing.JLabel();
        tipTextLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Tip of the day");
        setResizable(false);

        previousButton.setText("Show previous");
        previousButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousButtonActionPerformed(evt);
            }
        });

        nextButton.setText("Show next");
        nextButton.setToolTipText("");
        nextButton.setActionCommand("Show next");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        tipCountLabel.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        tipCountLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tipCountLabel.setText("Tip 0/0");

        tipTextLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tipTextLabel.setText("Tip text");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("You can disable this window in program settings.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tipCountLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(previousButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(nextButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 111, Short.MAX_VALUE))
                    .addComponent(tipTextLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tipCountLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tipTextLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(previousButton)
                    .addComponent(nextButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void previousButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousButtonActionPerformed
        showHint(currentTip - 1);
    }//GEN-LAST:event_previousButtonActionPerformed

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        showHint(currentTip + 1);
    }//GEN-LAST:event_nextButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton nextButton;
    private javax.swing.JButton previousButton;
    private javax.swing.JLabel tipCountLabel;
    private javax.swing.JLabel tipTextLabel;
    // End of variables declaration//GEN-END:variables
}
