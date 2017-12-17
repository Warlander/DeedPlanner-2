package pl.wurmonline.deedplanner.graphics;

import javax.media.opengl.GL2;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3d;
import pl.wurmonline.deedplanner.*;
import pl.wurmonline.deedplanner.data.Map;
import pl.wurmonline.deedplanner.data.Tile;
import pl.wurmonline.deedplanner.input.*;
import pl.wurmonline.deedplanner.logic.TileFragment;

public final class UpCamera implements Camera {
    
    private static final int OFFSCREEN_TILES_RENDERED = 3;
    
    public float x=0;
    public float y=0;
    
    public Tile tile;
    public float xTile;
    public float yTile;
    public float xMap;
    public float yMap;
    
    private float tileScaler;
    
    public void update(MapPanel panel, Mouse mouse, Keybindings keybindings) {
        Map map = panel.getMap();
        
        tileScaler = (float) panel.getWidth() / panel.getHeight();
        
        float tileSize = (float) panel.getHeight() / Properties.scale / 4;
        float tileX = ((mouse.x + x * tileSize) / ((float) panel.getWidth() / Properties.scale / tileScaler));
        float tileY = (((panel.getHeight() - mouse.y) + y * tileSize) / ((float) panel.getHeight() / Properties.scale));
        
        double keyboardFraction = Properties.keyboardFractionUp;
        if (keybindings.hold(Keybindings.UP_SPEED_MOD1)) {
            keyboardFraction *= Properties.mod1Fpp;
        }
        else if (keybindings.hold(Keybindings.UP_SPEED_MOD2)) {
            keyboardFraction *= Properties.mod2Fpp;
        }
        
        int currX = (int) tileX;
        int currY = (int) tileY;
        
        if (currX >= 0 && currX < map.getWidth() && currY >= 0 && currY < map.getHeight()) {
            tile = map.getTile(currX, currY);
            xMap = tileX;
            yMap = tileY;
            xTile = tileX%1f;
            yTile = tileY%1f;
        }
        else {
            tile = null;
        }
        
        if (mouse.scrollDown || keybindings.pressed(Keybindings.UP_SCALE_MORE)) {
            if (Properties.scale > 5) {
                Properties.scale--;
            }
        }
        else if (mouse.scrollUp || keybindings.pressed(Keybindings.UP_SCALE_LESS)) {
            if (Properties.scale < 40) {
                Properties.scale++;
            }
        }
        
        if (mouse.pressed.middle) {
            mouse.setGrabbed(true);
        }
        else if (mouse.hold.middle) {
            x -= mouse.diffX * Properties.mouseFractionUp;
            y += mouse.diffY * Properties.mouseFractionUp;
        }
        else if (mouse.released.middle && !mouse.hold.middle) {
            mouse.setGrabbed(false);
        }
        
        if (keybindings.hold(Keybindings.UP_MOVE_UP)) {
            y += keyboardFraction;
        }
        if (keybindings.hold(Keybindings.UP_MOVE_DOWN)) {
            y -= keyboardFraction;
        }
        
        if (keybindings.hold(Keybindings.UP_MOVE_RIGHT)) {
            x += keyboardFraction;
        }
        if (keybindings.hold(Keybindings.UP_MOVE_LEFT)) {
            x -= keyboardFraction;
        }
        
        if (y < 0) {
            y = 0;
        }
        if (x < 0) {
            x = 0;
        }
        
        if (y > 4 * (map.getHeight() - Properties.scale)) {
            y = 4 * (map.getHeight() - Properties.scale);
        }
        if (x > 4 * (map.getWidth() - Properties.scale * tileScaler)) {
            x = 4 * (map.getWidth() - Properties.scale * tileScaler);
        }
    }
    
    public void set(GL2 g, MapPanel panel) {
        g.glMatrixMode(GL2.GL_PROJECTION);
        g.glLoadIdentity();
        g.glOrtho(0, Properties.scale * 4f * (float)panel.getWidth() / (float)panel.getHeight(), 0, Properties.scale * 4, 0.001f, 8192);
        g.glTranslatef(-x, -y, -4000);
        g.glMatrixMode(GL2.GL_MODELVIEW);
        g.glLoadIdentity();
    }
    
    public void reset() {
        x=0;
        y=0;
    }

    public boolean isEditingCapable() {
        return true;
    }

    public Tile getHoveredTile() {
        return tile;
    }

    public TileFragment getHoveredTileFragment() {
        return TileFragment.calculateTileFragment(xTile, yTile);
    }
    
    public Vector2f getHoveredTilePosition() {
        return new Vector2f(xTile, yTile);
    }

    public float getLevelVisibility(int level) {
        switch (Math.abs(Globals.floor) - Math.abs(level)) {
            case 0:
                return 1;
            case 1:
                return 0.6f;
            case 2:
                return 0.25f;
            default:
                return 0;
        }
    }

    public Vector2i getLowerRenderBounds() {
        int currX = (int) (x / 4);
        int currY = (int) (y / 4);
        
        return new Vector2i(currX - OFFSCREEN_TILES_RENDERED, currY - OFFSCREEN_TILES_RENDERED);
    }

    public Vector2i getUpperRenderBounds() {
        int currX = (int) (x / 4);
        int currY = (int) (y / 4);
        
        int upperXBound = currX + (int) (Properties.scale * tileScaler) + OFFSCREEN_TILES_RENDERED;
        int upperYBound = currY + Properties.scale + OFFSCREEN_TILES_RENDERED;
        return new Vector2i(upperXBound, upperYBound);
    }

    public boolean isSkyboxRendered() {
        return false;
    }

    public CameraType getCameraType() {
        return CameraType.TOP_VIEW;
    }

    public Vector3d getPosition() {
        return new Vector3d(xMap, yMap, 0);
    }
    
}
