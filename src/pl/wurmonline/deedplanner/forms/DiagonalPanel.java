package pl.wurmonline.deedplanner.forms;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;
import pl.wurmonline.deedplanner.Globals;
import pl.wurmonline.deedplanner.MainLoop;
import pl.wurmonline.deedplanner.data.RoadDirection;

public class DiagonalPanel extends JPanel implements MouseListener {
    
    private final Planner planner;
    
    private final Rectangle2D.Float fullRect = new Rectangle.Float(10, 10, 50, 50);
    private final Rectangle2D.Float nw = new Rectangle.Float(70, 10, 20, 20);
    private final Rectangle2D.Float ne = new Rectangle.Float(100, 10, 20, 20);
    private final Rectangle2D.Float sw = new Rectangle.Float(70, 40, 20, 20);
    private final Rectangle2D.Float se = new Rectangle.Float(100, 40, 20, 20);
    
    public DiagonalPanel() {
        planner = null;
    }
    
    public DiagonalPanel(Planner planner) {
        setSize(130, 70);
        addMouseListener(this);
        this.planner = planner;
    }
    
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setColor(Color.black);
        g2.fillRect(0, 0, 10, 70);
        g2.fillRect(60, 0, 10, 70);
        g2.fillRect(120, 0, 10, 70);
        
        g2.fillRect(0, 0, 130, 10);
        g2.fillRect(0, 60, 130, 10);
        
        g2.fillRect(70, 30, 50, 10);
        g2.fillRect(90, 10, 10, 50);
        
        fill(g2, fullRect, RoadDirection.CENTER);
        fill(g2, nw, RoadDirection.NW);
        fill(g2, ne, RoadDirection.NE);
        fill(g2, sw, RoadDirection.SW);
        fill(g2, se, RoadDirection.SE);
    }
    
    private void fill(Graphics2D g, Rectangle2D.Float rect, RoadDirection dir) {
        if (Globals.roadDirection==dir) {
            g.setColor(Color.green);
        }
        else {
            g.setColor(Color.red);
        }
        g.fill(rect);
    }

    public void mouseClicked(MouseEvent e) {
        Point p = e.getPoint();
        MainLoop loop = planner.getMapPanel().getLoop();
        final RoadDirection dir;
        
        if (fullRect.contains(p)) {
            dir = RoadDirection.CENTER;
        }
        else if (nw.contains(p)) {
            dir = RoadDirection.NW;
        }
        else if (ne.contains(p)) {
            dir = RoadDirection.NE;
        }
        else if (sw.contains(p)) {
            dir = RoadDirection.SW;
        }
        else if (se.contains(p)) {
            dir = RoadDirection.SE;
        }
        else {
            dir = null;
        }
        
        if (dir!=null) {
            loop.syncAndExecute(() -> {
                Globals.roadDirection = dir;
                repaint();
            });
        }
    }

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}
    
}
