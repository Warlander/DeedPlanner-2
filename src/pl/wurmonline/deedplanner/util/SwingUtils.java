package pl.wurmonline.deedplanner.util;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.UIManager;
import pl.wurmonline.deedplanner.Launcher;
import pl.wurmonline.deedplanner.Properties;

public class SwingUtils {

    public static void centerFrame(JFrame frame) {
            GraphicsDevice defaultScreen = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            Rectangle screenSize = defaultScreen.getDefaultConfiguration().getBounds();

            int midx = screenSize.width/2;
            int midy = screenSize.height/2;

            int posx = midx-(frame.getWidth()/2);
            int posy = midy-(frame.getHeight()/2);

            frame.setLocation(posx, posy);
    }
    
    public static void setLookAndFeel(String name) {
        try {
            if (name.equals("PGS")) {
                UIManager.setLookAndFeel("com.pagosoft.plaf.PgsLookAndFeel");
            }
            else {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if (name.equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Launcher.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
}
