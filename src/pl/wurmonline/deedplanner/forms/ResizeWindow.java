package pl.wurmonline.deedplanner.forms;

import pl.wurmonline.deedplanner.MapPanel;
import pl.wurmonline.deedplanner.data.Map;
import pl.wurmonline.deedplanner.data.Tile;
import pl.wurmonline.deedplanner.util.SwingUtils;

public class ResizeWindow extends javax.swing.JFrame {
    
    private MapPanel panel;
    
    public ResizeWindow(MapPanel panel) {
        initComponents();
        SwingUtils.centerFrame(this);
        this.panel = panel;
        currWidth.setText("Current width: "+panel.getMap().getWidth());
        currHeight.setText("Current height: "+panel.getMap().getHeight());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        upSpin = new javax.swing.JSpinner();
        downSpin = new javax.swing.JSpinner();
        leftSpin = new javax.swing.JSpinner();
        rightSpin = new javax.swing.JSpinner();
        currWidth = new javax.swing.JLabel();
        currHeight = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle"); // NOI18N
        setTitle(bundle.getString("ResizeWindow.title")); // NOI18N
        setResizable(false);

        jButton1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton1.setText(bundle.getString("ResizeWindow.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        upSpin.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        upSpin.setModel(new javax.swing.SpinnerNumberModel(0, -9999, 9999, 1));

        downSpin.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        downSpin.setModel(new javax.swing.SpinnerNumberModel(0, -9999, 9999, 1));

        leftSpin.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        leftSpin.setModel(new javax.swing.SpinnerNumberModel(0, -9999, 9999, 1));

        rightSpin.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        rightSpin.setModel(new javax.swing.SpinnerNumberModel(0, -9999, 9999, 1));

        currWidth.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        currWidth.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        currWidth.setText(bundle.getString("ResizeWindow.currWidth.text")); // NOI18N

        currHeight.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        currHeight.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        currHeight.setText(bundle.getString("ResizeWindow.currHeight.text")); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(bundle.getString("ResizeWindow.jLabel1.text")); // NOI18N

        jLabel2.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText(bundle.getString("ResizeWindow.jLabel2.text")); // NOI18N

        jSpinner1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(0, -999, 999, 1));

        jButton2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton2.setText(bundle.getString("ResizeWindow.jButton2.text")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addComponent(upSpin, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(downSpin, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(currWidth, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(currHeight, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(leftSpin, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 53, Short.MAX_VALUE)
                                .addComponent(rightSpin, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jSpinner1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(upSpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(currWidth)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rightSpin)
                    .addComponent(leftSpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(currHeight)
                .addGap(18, 18, 18)
                .addComponent(downSpin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int up = (int)upSpin.getModel().getValue();
        int down = (int)downSpin.getModel().getValue();
        int left = (int)leftSpin.getModel().getValue();
        int right = (int)rightSpin.getModel().getValue();
        
        int width = panel.getMap().getWidth()+right+left;
        int height = panel.getMap().getHeight()+up+down;
        
        if (width<13 || width>9999) {
            close();
            return;
        }
        else if (height<13 || height>9999) {
            close();
            return;
        }
        
        panel.getLoop().syncAndExecute((() -> {
            Map newMap = new Map(panel.getMap(), left, down, width, height);
            panel.setMap(newMap);
        }));
        
        close();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int add = (int) jSpinner1.getModel().getValue();
        panel.getLoop().syncAndExecute((() -> {
            for (int i=0; i<=panel.getMap().getWidth(); i++) {
                for (int i2=0; i2<=panel.getMap().getHeight(); i2++) {
                    Tile t = panel.getMap().getTile(i, i2);
                    t.setHeight(t.getHeight()+add);
                }
            }
        }));
        close();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void close() {
        setVisible(false);
        dispose();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel currHeight;
    private javax.swing.JLabel currWidth;
    private javax.swing.JSpinner downSpin;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner leftSpin;
    private javax.swing.JSpinner rightSpin;
    private javax.swing.JSpinner upSpin;
    // End of variables declaration//GEN-END:variables
}
