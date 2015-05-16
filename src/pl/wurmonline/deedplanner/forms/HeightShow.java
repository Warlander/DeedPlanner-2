package pl.wurmonline.deedplanner.forms;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;
import pl.wurmonline.deedplanner.data.Tile;
import pl.wurmonline.deedplanner.graphics.UpCamera;
import pl.wurmonline.deedplanner.logic.TileFragment;
import pl.wurmonline.deedplanner.util.SwingUtils;

public class HeightShow extends JComponent {

    private static final Font basicFont = Font.decode("Arial-10");
    private static final Font boldFont = Font.decode("Arial-BOLD-12");
    
    private UpCamera cam;
    
    public HeightShow() {
        setPreferredSize(new Dimension(70, 70));
    }
    
    public void setUpCamera(UpCamera cam) {
        this.cam = cam;
        repaint();
    }
    
    protected void paintComponent(Graphics g) {
        if (cam!=null && cam.tile!=null) {
            TileFragment frag = TileFragment.calculateTileFragment(cam.xTile, cam.yTile);
            if (!frag.isCorner()) {
                return;
            }
            Tile tile = frag.getTileByCorner(cam.tile);
            g.setColor(Color.black);
            g.setFont(basicFont);
            drawHeightString(g, tile, tile.getMap().getTile(tile, -1, 1), 10, 10);
            drawHeightString(g, tile, tile.getMap().getTile(tile, 0, 1), 35, 10);
            drawHeightString(g, tile, tile.getMap().getTile(tile, 1, 1), 60, 10);
            
            drawHeightString(g, tile, tile.getMap().getTile(tile, -1, 0), 10, 35);
            String heightStr = Integer.toString(tile.getHeight());
            g.setFont(boldFont);
            SwingUtils.drawCenteredString(g, heightStr, 35, 35);
            g.setFont(basicFont);
            drawHeightString(g, tile, tile.getMap().getTile(tile, 1, 0), 60, 35);
            
            drawHeightString(g, tile, tile.getMap().getTile(tile, -1, -1), 10, 60);
            drawHeightString(g, tile, tile.getMap().getTile(tile, 0, -1), 35, 60);
            drawHeightString(g, tile, tile.getMap().getTile(tile, 1, -1), 60, 60);
        }
    }
    
    private void drawHeightString(Graphics g, Tile main, Tile diff, int x, int y) {
        if (diff==null) {
            return;
        }
        
        int heightDiff = diff.getHeight()-main.getHeight();
        String heightStr = Integer.toString(heightDiff);
        
        SwingUtils.drawCenteredString(g, heightStr, x, y);
    }
    
}
