package pl.wurmonline.deedplanner.util;

import java.awt.*;
import java.awt.geom.Rectangle2D;
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
    
    public static void drawCenteredString(Graphics g, String str, int x, int y) {
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        Rectangle2D rect = metrics.getStringBounds(str, g);
        int w = (int)(rect.getWidth());
        int h = (int)(rect.getHeight());
        g.drawString(str, x-w/2, y+h/2);
    }
    
}
