package pl.wurmonline.deedplanner.forms.bridges;

import javax.swing.DefaultListModel;
import pl.wurmonline.deedplanner.data.bridges.BridgeData;
import pl.wurmonline.deedplanner.data.bridges.BridgeType;

public class BridgesTypePanel extends BridgesPanel {

    public BridgesTypePanel(BridgesConstructor constructor) {
        super(constructor);
        initComponents();
        
        DefaultListModel<BridgeListItem> model = new DefaultListModel<>();
        for (BridgeType type : BridgeType.values()) {
            for (BridgeData data : BridgeData.getAllBridgesData()) {
                if (data.isCompatibleType(type)) {
                    model.addElement(new BridgeListItem(type, data));
                }
            }
        }
        typesList.setModel(model);
        typesList.addListSelectionListener((evt) -> {
            getConstructor().setBridgeSpecs(typesList.getSelectedValue());
            getConstructor().setCanAdvance(true);
        });
    }
    
    protected void awake() {
        if (getConstructor().getBridgeSpecs() != null) {
            getConstructor().setCanAdvance(true);
        }
    }
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        typesList = new javax.swing.JList<BridgeListItem>();

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Stage 1 - select bridge type");

        typesList.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        typesList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<BridgeListItem> typesList;
    // End of variables declaration//GEN-END:variables

}
