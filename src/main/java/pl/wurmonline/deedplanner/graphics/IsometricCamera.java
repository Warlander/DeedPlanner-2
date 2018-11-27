package pl.wurmonline.deedplanner.graphics;

import com.jogamp.common.nio.Buffers;
import java.nio.FloatBuffer;
import com.jogamp.opengl.GL2;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3d;
import pl.wurmonline.deedplanner.*;
import pl.wurmonline.deedplanner.data.Map;
import pl.wurmonline.deedplanner.data.Tile;
import pl.wurmonline.deedplanner.input.*;
import pl.wurmonline.deedplanner.logic.TileFragment;

public final class IsometricCamera implements Camera {
    
    private static final int OFFSCREEN_TILES_RENDERED = 3;
    
    public Vector2f cartPos = new Vector2f();
    
    public Tile tile;
    public float xTile;
    public float yTile;
    public float xMap;
    public float yMap;
    
    private float tileScaler;
    
    private static final FloatBuffer matrix;
    
    static {
        matrix = Buffers.newDirectFloatBuffer(16);
        matrix.put(new float[] {1, 0, 0, 0,
                                0, 0, -1, 0,
                                0, 1, 0, 0,
                                0, 0, 0, 1});
        matrix.rewind();
    }
    
    public void update(MapPanel panel, Mouse mouse, Keybindings keybindings) {
        Map map = panel.getMap();

        tileScaler = (float) panel.getWidth() / panel.getHeight();
        
        float isoTileWidth = 4f * (float) Math.sqrt(2);
        float isoTileHeight = isoTileWidth / 2;
        
        float tileSize = (float) panel.getHeight() / Properties.scale / 4;
        float tileX = ((mouse.x / (float) Math.sqrt(2) + cartPos.x * tileSize) / ((float) panel.getWidth() / Properties.scale / tileScaler));
        float tileY = (((panel.getHeight() - mouse.y) / (float) Math.sqrt(2) + cartPos.y * tileSize) / ((float) panel.getHeight() / Properties.scale));
        Vector2f cartTileCoord = new Vector2f(tileX, tileY);
        Vector2f isoTileCoord = isoToCart(cartTileCoord);
        tileX = isoTileCoord.x;
        tileY = isoTileCoord.y;
        
        //System.out.println("CartM: " + cartTileCoord.toString(NumberFormat.getNumberInstance()) + "\t IsoM: " + isoTileCoord.toString(NumberFormat.getNumberInstance()));
        
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
            cartPos.x -= mouse.diffX * Properties.mouseFractionUp;
            cartPos.y += mouse.diffY * Properties.mouseFractionUp;
        }
        else if (mouse.released.middle && !mouse.hold.middle) {
            mouse.setGrabbed(false);
        }
        
        if (keybindings.hold(Keybindings.UP_MOVE_UP)) {
            cartPos.y += keyboardFraction;
        }
        if (keybindings.hold(Keybindings.UP_MOVE_DOWN)) {
            cartPos.y -= keyboardFraction;
        }
        
        if (keybindings.hold(Keybindings.UP_MOVE_RIGHT)) {
            cartPos.x += keyboardFraction;
        }
        if (keybindings.hold(Keybindings.UP_MOVE_LEFT)) {
            cartPos.x -= keyboardFraction;
        }
        
        if (cartPos.y < 0) {
            cartPos.y = 0;
        }
        if (cartPos.x < -isoTileWidth * map.getWidth() / 2) {
            cartPos.x = -isoTileWidth * map.getWidth() / 2;
        }
        
        if (cartPos.y > isoTileHeight * (map.getHeight() - Properties.scale)) {
            cartPos.y = isoTileHeight * (map.getHeight() - Properties.scale);
        }
        if (cartPos.x > isoTileWidth * (map.getWidth() - Properties.scale * tileScaler) / 2) {
            cartPos.x = isoTileWidth * (map.getWidth() - Properties.scale * tileScaler) / 2;
        }
    }
    
    private Vector2f cartToIso(Vector2f cartCoords) {
        float coordX = cartCoords.x - cartCoords.y;
        float coordY = (cartCoords.x + cartCoords.y) / 2;
        return new Vector2f(coordX, coordY).mul(1 / (float) Math.sqrt(2));
    }
    
    private Vector2f isoToCart(Vector2f isoCoords) {
        float coordY = (float) ((2 * isoCoords.y - isoCoords.x) * Math.sqrt(2)) / 2f;
        float coordX = isoCoords.x * (float) Math.sqrt(2) + coordY;
        return new Vector2f(coordX, coordY);
    }
    
    public void set(GL2 g, MapPanel panel) {
        //g.glEnable(GL2.GL_DEPTH_TEST);
        g.glMatrixMode(GL2.GL_PROJECTION);
        g.glLoadIdentity();
        g.glOrtho(0, Properties.scale * 4f * (float)panel.getWidth() / (float)panel.getHeight() / Math.sqrt(2), 0, Properties.scale * 4 / Math.sqrt(2), 0.001f, 8192);
        g.glMatrixMode(GL2.GL_MODELVIEW);
        g.glLoadIdentity();
        g.glTranslatef(-cartPos.x, -cartPos.y, -4000);
        g.glMultMatrixf(matrix);
        g.glRotatef(30f, 1, 0, 0);
        g.glRotatef(45.0f, 0, 0, 1);
    }
    
    public void reset() {
        cartPos.x=0;
        cartPos.y=0;
    }

    public boolean isEditingCapable() {
        return false;
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
        return 1;
//        switch (Math.abs(Globals.floor) - Math.abs(level)) {
//            case 0:
//                return 1;
//            case 1:
//                return 0.6f;
//            case 2:
//                return 0.25f;
//            default:
//                return 0;
//        }
    }

    public Vector2i getLowerRenderBounds() {
        int currX = (int) (cartPos.x / 4);
        int currY = (int) (cartPos.y / 4);
        
        //return new Vector2i(currX - OFFSCREEN_TILES_RENDERED, currY - OFFSCREEN_TILES_RENDERED);
        return new Vector2i(0, 0);
    }

    public Vector2i getUpperRenderBounds() {
        int currX = (int) (cartPos.x / 4);
        int currY = (int) (cartPos.y / 4);
        
        int upperXBound = currX + (int) (Properties.scale * tileScaler) + OFFSCREEN_TILES_RENDERED;
        int upperYBound = currY + Properties.scale + OFFSCREEN_TILES_RENDERED;
        //return new Vector2i(upperXBound, upperYBound);
        return new Vector2i(100, 100);
    }

    public boolean isSkyboxRendered() {
        return false;
    }

    public CameraType getCameraType() {
        return CameraType.ISOMETRIC;
    }

    public Vector3d getPosition() {
        return new Vector3d(xMap, yMap, 0);
    }
    
}
