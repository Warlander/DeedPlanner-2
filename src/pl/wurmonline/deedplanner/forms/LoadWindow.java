package pl.wurmonline.deedplanner.forms;

import java.io.*;
import java.net.*;
import java.util.logging.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import pl.wurmonline.deedplanner.MapPanel;
import pl.wurmonline.deedplanner.Properties;
import pl.wurmonline.deedplanner.data.Map;
import pl.wurmonline.deedplanner.util.DeedPlannerException;
import pl.wurmonline.deedplanner.util.Log;
import pl.wurmonline.deedplanner.util.SwingUtils;

public class LoadWindow extends javax.swing.JFrame {

    private final String endl = System.getProperty("line.separator");
    
    MapPanel panel;
    
    public LoadWindow(MapPanel panel) {
        initComponents();
        this.panel = panel;
        SwingUtils.centerFrame(this);
    }
    
    private void loadManager(byte[] toLoad) {
        try {
            panel.setMap(Map.parseMap(toLoad));
        } catch (DeedPlannerException ex) {
            Log.err(ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        codeField = new javax.swing.JTextField();
        codeButton = new javax.swing.JButton();
        urlButton = new javax.swing.JButton();
        urlField = new javax.swing.JTextField();
        fileButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle"); // NOI18N
        setTitle(bundle.getString("LoadWindow.title")); // NOI18N
        setResizable(false);

        codeField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        codeField.setText(bundle.getString("LoadWindow.codeField.text")); // NOI18N
        codeField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                codeFieldMouseClicked(evt);
            }
        });

        codeButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        codeButton.setText(bundle.getString("LoadWindow.codeButton.text")); // NOI18N
        codeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codeButtonActionPerformed(evt);
            }
        });

        urlButton.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        urlButton.setText(bundle.getString("LoadWindow.urlButton.text")); // NOI18N
        urlButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                urlButtonActionPerformed(evt);
            }
        });

        urlField.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        urlField.setText(bundle.getString("LoadWindow.urlField.text")); // NOI18N
        urlField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                urlFieldMouseClicked(evt);
            }
        });

        fileButton.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        fileButton.setText(bundle.getString("LoadWindow.fileButton.text")); // NOI18N
        fileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fileButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(codeField, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(codeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(urlField, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(urlButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(codeButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(urlField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(urlButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fileButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void codeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codeButtonActionPerformed
        if (!codeField.getText().contains("|")) {
            return;
        }
        loadManager(codeField.getText().getBytes());
        setVisible(false);
        dispose();
    }//GEN-LAST:event_codeButtonActionPerformed

    private void urlButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_urlButtonActionPerformed
        if (!urlField.getText().contains("/")) {
            return;
        }
        String url;
        if (urlField.getText().contains("raw")) {
            url = urlField.getText();
        }
        else {
            String id = urlField.getText().substring(urlField.getText().lastIndexOf("/")+1);
            url = "http://pastebin.com/raw.php?i="+id;
        }
        try {
            URL uri = new URL(url);
            URLConnection conn = uri.openConnection();
            InputStream in = conn.getInputStream();
            byte[] bytes = getBytesFromInputStream(in);
            in.close();
            loadManager(bytes);
        } catch (IOException ex) {
            Logger.getLogger(LoadWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        setVisible(false);
        dispose();
    }//GEN-LAST:event_urlButtonActionPerformed

    private void fileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileButtonActionPerformed
        JFileChooser fc = new JFileChooser();
        FileFilter filter = new ExtensionFileFilter(".MAP file", "MAP");
        fc.setFileFilter(filter);
        fc.setCurrentDirectory(new File(Properties.lastDir));
        
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String ph = file.getPath();
            Properties.lastDir = ph.substring(0, ph.lastIndexOf(Properties.SLASH)+1);
            Properties.saveProperties();
            try {
                FileInputStream fis = new FileInputStream(file);
                byte[] bytes = getBytesFromInputStream(fis);
                fis.close();
                loadManager(bytes);
            } catch (IOException ex) {
                Logger.getLogger(SaveWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        setVisible(false);
        dispose();
    }//GEN-LAST:event_fileButtonActionPerformed

    private byte[] getBytesFromInputStream(InputStream is) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream();) {
            byte[] buffer = new byte[0xFFFF];

            for (int len; (len = is.read(buffer)) != -1;)
                os.write(buffer, 0, len);

            os.flush();

            return os.toByteArray();
        }
        catch (IOException e) {
            return null;
        }
    }
    
    private void urlFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_urlFieldMouseClicked
        urlField.setText("");
    }//GEN-LAST:event_urlFieldMouseClicked

    private void codeFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_codeFieldMouseClicked
        codeField.setText("");
    }//GEN-LAST:event_codeFieldMouseClicked

    private class ExtensionFileFilter extends FileFilter {
        String description;

        String extensions[];

        public ExtensionFileFilter(String description, String extension) {
          this(description, new String[] { extension });
        }

        public ExtensionFileFilter(String description, String extensions[]) {
          if (description == null) {
            this.description = extensions[0];
          } else {
            this.description = description;
          }
          this.extensions = (String[]) extensions.clone();
          toLower(this.extensions);
        }

        private void toLower(String array[]) {
          for (int i = 0, n = array.length; i < n; i++) {
            array[i] = array[i].toLowerCase();
          }
        }

        public String getDescription() {
          return description;
        }

        public boolean accept(File file) {
          if (file.isDirectory()) {
            return true;
          } else {
            String path = file.getAbsolutePath().toLowerCase();
            for (int i = 0, n = extensions.length; i < n; i++) {
              String extension = extensions[i];
              if ((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.')) {
                return true;
              }
            }
          }
          return false;
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton codeButton;
    private javax.swing.JTextField codeField;
    private javax.swing.JButton fileButton;
    private javax.swing.JButton urlButton;
    private javax.swing.JTextField urlField;
    // End of variables declaration//GEN-END:variables
}
