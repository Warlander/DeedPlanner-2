package pl.wurmonline.deedplanner.forms;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.net.*;
import java.util.logging.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import pl.wurmonline.deedplanner.Properties;
import pl.wurmonline.deedplanner.util.Log;
import pl.wurmonline.deedplanner.util.SwingUtils;

public class SaveWindow extends javax.swing.JFrame {
    
    private final String serializedMap;
    
    public SaveWindow(String serializedMap) {
        initComponents();
        this.serializedMap = serializedMap;
        SwingUtils.centerFrame(this);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        copyMapButton = new javax.swing.JButton();
        saveToFileButton = new javax.swing.JButton();
        pasteExpiration = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle"); // NOI18N
        setTitle(bundle.getString("SaveWindow.title")); // NOI18N
        setMaximumSize(new java.awt.Dimension(500, 400));
        setResizable(false);

        jButton2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton2.setText(bundle.getString("SaveWindow.jButton2.text")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(bundle.getString("SaveWindow.jLabel1.text")); // NOI18N

        copyMapButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        copyMapButton.setText(bundle.getString("SaveWindow.copyMapButton.text")); // NOI18N
        copyMapButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyMapButtonActionPerformed(evt);
            }
        });

        saveToFileButton.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        saveToFileButton.setText(bundle.getString("SaveWindow.saveToFileButton.text")); // NOI18N
        saveToFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveToFileButtonActionPerformed(evt);
            }
        });

        pasteExpiration.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        pasteExpiration.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10 minutes", "1 hour", "1 day", "1 week", "2 weeks", "1 month", "Never delete" }));
        pasteExpiration.setSelectedIndex(5);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText(bundle.getString("SaveWindow.jLabel2.text")); // NOI18N

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/wurmonline/deedplanner/forms/btn_donate_LG.gif"))); // NOI18N
        jLabel3.setText(bundle.getString("SaveWindow.jLabel3.text")); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(saveToFileButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(copyMapButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pasteExpiration, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(copyMapButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveToFileButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(pasteExpiration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            String data;
            data = URLEncoder.encode("api_dev_key", "UTF-8") + "=" + URLEncoder.encode("24844c99ae9971a2da79a2f7d0da7642", "UTF-8");
            data += "&" + URLEncoder.encode("api_paste_code", "UTF-8") + "=" + URLEncoder.encode(serializedMap, "UTF-8");
            data += "&" + URLEncoder.encode("api_option", "UTF-8") + "=" + URLEncoder.encode("paste", "UTF-8");
            
            data += "&" + URLEncoder.encode("api_paste_expire_date", "UTF-8") + "=";
            switch ((String)pasteExpiration.getModel().getSelectedItem()) {
                case "10 minutes":
                data += URLEncoder.encode("10M", "UTF-8");
                break;
                case "1 hour":
                data += URLEncoder.encode("1H", "UTF-8");
                break;
                case "1 day":
                data += URLEncoder.encode("1D", "UTF-8");
                break;
                case "1 week":
                data += URLEncoder.encode("1W", "UTF-8");
                break;
                case "2 weeks":
                data += URLEncoder.encode("2W", "UTF-8");
                break;
                case "1 month":
                data += URLEncoder.encode("1M", "UTF-8");
                break;
                case "Never delete":
                data += URLEncoder.encode("N", "UTF-8");
                break;
            }
            URL url = new URL("http://pastebin.com/api/api_post.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI(line));
                }
                else {
                    StringSelection stringSelection = new StringSelection(line);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(stringSelection, null);
                    JOptionPane.showMessageDialog(null, "Your map is now saved to Pastebin.\n\nAs your Java implementation does not support direct navigation to the web page, link was copied to the system clipboard instead.", "Save to Pastebin", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            wr.close();
            rd.close();
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(SaveWindow.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.dispose();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void copyMapButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyMapButtonActionPerformed
        StringSelection select = new StringSelection(serializedMap);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(select, select);
        setVisible(false);
        dispose();
    }//GEN-LAST:event_copyMapButtonActionPerformed

    private void saveToFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveToFileButtonActionPerformed
        JFileChooser fc = new JFileChooser();
        FileFilter filter = new ExtensionFileFilter(".MAP file", new String[] { "MAP" });
        fc.setFileFilter(filter);
        fc.setCurrentDirectory(new File(Properties.lastDir));
        int returnVal = fc.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            saveToFile(file);
            setVisible(false);
            dispose();
        }
    }//GEN-LAST:event_saveToFileButtonActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        try {
            Desktop.getDesktop().browse(new URI("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=NYTYWR5MV2U68"));
        } catch (URISyntaxException | IOException ex) {
            Log.err(ex);
        }
    }//GEN-LAST:event_jLabel3MouseClicked

    private void saveToFile(File file) {
        if (!file.getPath().contains(".MAP")) {
            file = new File(file.getPath()+".MAP");
        }
        try {
            file.createNewFile();
            PrintWriter out = new PrintWriter(new FileOutputStream(file));
            out.print(serializedMap);
            out.close();
            String ph = file.getPath();
            Properties.lastDir = ph.substring(0, ph.lastIndexOf(File.separator)+1);
            Properties.saveProperties();
        } catch (IOException ex) {
            Logger.getLogger(SaveWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
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
    private javax.swing.JButton copyMapButton;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JComboBox pasteExpiration;
    private javax.swing.JButton saveToFileButton;
    // End of variables declaration//GEN-END:variables
}
