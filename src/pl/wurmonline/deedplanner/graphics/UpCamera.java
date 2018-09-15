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
    
    private static final float TILE_TO_SCREEN_RATIO = 4.0f;
    private static final float SCREEN_TO_TILE_RATIO = 1.0f / TILE_TO_SCREEN_RATIO;
    
    
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
        
        float screenTileSize = (float) panel.getHeight() / Properties.scale;
        float tileSize = screenTileSize * SCREEN_TO_TILE_RATIO;
        float tileX = ((mouse.x + x * tileSize) / ((float) panel.getWidth() / Properties.scale / tileScaler));
        float tileY = (((panel.getHeight() - mouse.y) + y * tileSize) / ((float) panel.getHeight() / Properties.scale));
        
        double keyboardFraction = Properties.keyboardFractionUp;
        if (keybindings.hold(Keybindings.UP_SPEED_MOD1)) {
            keyboardFraction *= Properties.mod1Fpp;
        }
        else if (keybindings.hold(Keybindings.UP_SPEED_MOD2)) {
            keyboardFraction *= Properties.mod2Fpp;
        }
        
        if (mouse.scrollDown || keybindings.pressed(Keybindings.UP_SCALE_MORE)) {
            if (Properties.scale > 5) {
                x += (tileX - x * tileSize / ((float) panel.getWidth() / Properties.scale / tileScaler)) * TILE_TO_SCREEN_RATIO / Properties.scale;
                y += (tileY - y * tileSize / ((float) panel.getHeight() / Properties.scale)) * TILE_TO_SCREEN_RATIO / Properties.scale;
                Properties.scale--;
            }
        }
        else if (mouse.scrollUp || keybindings.pressed(Keybindings.UP_SCALE_LESS)) {
            if (Properties.scale < 200) {
                Properties.scale++;
            }
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
        
        float totalWidth = map.getWidth() * screenTileSize;
        float totalHeight = map.getHeight() * screenTileSize;
        boolean fitsHorizontally = totalWidth < panel.getWidth();
        boolean fitsVertically = totalHeight < panel.getHeight();
        
        if (x < 0 && !fitsVertically) {
            x = 0;
        }
        if (y < 0 && !fitsVertically) {
            y = 0;
        }
        
        if (x > 4 * (map.getWidth() - Properties.scale * tileScaler) && !fitsVertically) {
            x = 4 * (map.getWidth() - Properties.scale * tileScaler);
        }
        if (y > 4 * (map.getHeight() - Properties.scale) && !fitsVertically) {
            y = 4 * (map.getHeight() - Properties.scale);
        }
        
        if (fitsHorizontally) {
            float tilesOnScreen = panel.getWidth() / screenTileSize;
            float unboundTiles = tilesOnScreen - map.getWidth();
            x = -unboundTiles / 2 * TILE_TO_SCREEN_RATIO;
        }
        if (fitsVertically) {
            float tilesOnScreen = panel.getHeight() / screenTileSize;
            float unboundTiles = tilesOnScreen - map.getHeight();
            y = -unboundTiles / 2 * TILE_TO_SCREEN_RATIO;
        }
    }
    
    public void set(GL2 g, MapPanel panel) {
        g.glMatrixMode(GL2.GL_PROJECTION);
        g.glLoadIdentity();
        g.glOrtho(0, Properties.scale * TILE_TO_SCREEN_RATIO * (float)panel.getWidth() / (float)panel.getHeight(), 0, Properties.scale * TILE_TO_SCREEN_RATIO, 0.001f, 8192);
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
        int currX = (int) (x * SCREEN_TO_TILE_RATIO);
        int currY = (int) (y * SCREEN_TO_TILE_RATIO);
        
        return new Vector2i(currX - OFFSCREEN_TILES_RENDERED, currY - OFFSCREEN_TILES_RENDERED);
    }

    public Vector2i getUpperRenderBounds() {
        int currX = (int) (x * SCREEN_TO_TILE_RATIO);
        int currY = (int) (y * SCREEN_TO_TILE_RATIO);
        
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
