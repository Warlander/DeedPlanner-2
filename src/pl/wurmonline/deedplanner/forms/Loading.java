package pl.wurmonline.deedplanner.forms;

import java.awt.Image;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import pl.wurmonline.deedplanner.data.io.DataLoader;
import pl.wurmonline.deedplanner.util.DeedPlannerException;
import pl.wurmonline.deedplanner.util.Log;
import pl.wurmonline.deedplanner.util.SwingUtils;

public class Loading extends javax.swing.JFrame {

    public Loading() {
        initComponents();
        try {
            ArrayList<Image> images = new ArrayList();
            images.add(ImageIO.read(Planner.class.getResourceAsStream("logoL.jpg")));
            images.add(ImageIO.read(Planner.class.getResourceAsStream("logoM.png")));
            images.add(ImageIO.read(Planner.class.getResourceAsStream("logoS.png")));
            setIconImages(images);
        } catch (IOException ex) {
            Log.err(ex);
        }
        SwingUtils.centerFrame(this);
        setVisible(true);
        new Thread(() -> {
            try {
                DataLoader.loadData(this, new File("Data/objects.xml"));
                new Planner();
            } catch (DeedPlannerException | ParserConfigurationException | IOException | SAXException ex) {
                Log.err(ex);
                JOptionPane.showMessageDialog(null,
                                          "Critical DeedPlanner error occured.\n" +
                                          "DeedPlanner objects definitions are not found or corrupted.\n" +
                                          "This is possible in one of these scenarios:\n" +
                                          "1. Program download is corrupted.\n" +
                                          "2. Program doesn't have permissions to read files inside \"Data\" folder.\n" +
                                          "3. Program objects definitions got incorrectly modified by user.\n" +
                                          "If this message appears in other situations than mentioned,\n" +
                                          "please copy content of ErrorLog.txt file and report\n" +
                                          "the error in a program thread.",
                                          "DeedPlanner critical error",
                                          JOptionPane.ERROR_MESSAGE);
            }
            
            dispose();
        }).start();
    }

    public void increaseProgress(String message) {
        progressBar.setValue(progressBar.getValue()+1);
        progressBar.setString(message);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLabel3 = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/wurmonline/deedplanner/forms/logo.png"))); // NOI18N
        jLabel3.setMinimumSize(new java.awt.Dimension(0, 0));

        jLayeredPane1.setLayer(jLabel3, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        progressBar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        progressBar.setMaximum(9);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle"); // NOI18N
        progressBar.setString(bundle.getString("Loading.progressBar.string")); // NOI18N
        progressBar.setStringPainted(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane1)
            .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables
}
