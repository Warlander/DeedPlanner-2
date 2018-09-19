package pl.wurmonline.deedplanner.forms;

import java.awt.*;
import javax.swing.JComponent;
import pl.wurmonline.deedplanner.Globals;
import pl.wurmonline.deedplanner.data.Tile;
import pl.wurmonline.deedplanner.graphics.Camera;
import pl.wurmonline.deedplanner.logic.TileFragment;
import pl.wurmonline.deedplanner.util.SwingUtils;

public class HeightShow extends JComponent {

    private static final Font basicFont = Font.decode("Arial-10");
    private static final Font boldFont = Font.decode("Arial-BOLD-12");
    
    private Camera cam;
    
    public HeightShow() {
        setPreferredSize(new Dimension(70, 70));
    }
    
    public void setCamera(Camera cam) {
        this.cam = cam;
        repaint();
    }
    
    protected void paintComponent(Graphics g) {
        if (cam != null && cam.getHoveredTile() != null) {
            TileFragment frag = cam.getHoveredTileFragment();
            g.setColor(Color.black);
            g.setFont(basicFont);
            if (frag.isCorner()) {
                Tile tile = frag.getTileByCorner(cam.getHoveredTile());
                paintCorner(g, tile);
            }
            else if (frag.isCenter()) {
                Tile tile = cam.getHoveredTile();
                paintCenter(g, tile);
            }
        }
    }
    
    private void paintCorner(Graphics g, Tile tile) {
        drawHeightString(g, tile, tile.getMap().getTile(tile, -1, 1), 10, 10);
        drawHeightString(g, tile, tile.getMap().getTile(tile, 0, 1), 35, 10);
        drawHeightString(g, tile, tile.getMap().getTile(tile, 1, 1), 60, 10);

        drawHeightString(g, tile, tile.getMap().getTile(tile, -1, 0), 10, 35);
        g.setFont(boldFont);
        drawHeightString(g, tile, tile, 35, 35);
        g.setFont(basicFont);
        drawHeightString(g, tile, tile.getMap().getTile(tile, 1, 0), 60, 35);

        drawHeightString(g, tile, tile.getMap().getTile(tile, -1, -1), 10, 60);
        drawHeightString(g, tile, tile.getMap().getTile(tile, 0, -1), 35, 60);
        drawHeightString(g, tile, tile.getMap().getTile(tile, 1, -1), 60, 60);
    }
    
    private void paintCenter(Graphics g, Tile tile) {
        g.setFont(boldFont);
        drawHeightString(g, tile.getMap().getTile(tile, 0, 1), tile.getMap().getTile(tile, 0, 1), 10, 10);
        drawHeightString(g, tile.getMap().getTile(tile, 1, 1), tile.getMap().getTile(tile, 1, 1), 60, 10);
        drawHeightString(g, tile, tile, 10, 60);
        drawHeightString(g, tile.getMap().getTile(tile, 1, 0), tile.getMap().getTile(tile, 1, 0), 60, 60);
        g.setFont(basicFont);
    }
    
    private void drawHeightString(Graphics g, Tile main, Tile diff, int x, int y) {
        if (main == null || diff == null) {
            return;
        }
        
        int shownHeight;
        
        if (main == diff && Globals.floor >= 0) {
            shownHeight = main.getHeight();
        }
        else if (main == diff) {
            shownHeight = main.getCaveHeight();
        }
        else if (Globals.floor >= 0) {
            shownHeight = diff.getHeight() - main.getHeight();
        }
        else {
            shownHeight = diff.getCaveHeight() - main.getCaveHeight();
        }
        String heightStr = Integer.toString(shownHeight);
        
        SwingUtils.drawCenteredString(g, heightStr, x, y);
    }
    
}
