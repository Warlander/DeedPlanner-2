package pl.wurmonline.deedplanner.logic;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.function.Consumer;
import pl.wurmonline.deedplanner.data.Map;
import pl.wurmonline.deedplanner.data.MapFragment;
import pl.wurmonline.deedplanner.data.Tile;
import pl.wurmonline.deedplanner.graphics.Camera;
import pl.wurmonline.deedplanner.input.Keyboard;
import pl.wurmonline.deedplanner.input.Mouse;

public class TileSelection {

    private static Tile selectedTile = null;
    private static MapFragment fragment = null;
    private static MapFragment clipboard = null;
    private static final ArrayList<Consumer<MapFragment>> listeners = new ArrayList<>();
    
    public static void update(Mouse mouse, Keyboard keyboard, Map map, Camera cam, SelectionType selectionType) {
        switch (selectionType) {
            case MULTIPLE:
                if (mouse.pressed.left || mouse.pressed.right) {
                    selectedTile = cam.getHoveredTile();
                }
                if (mouse.hold.left || mouse.hold.right) {
                    int minX = Math.min(selectedTile.getX(), cam.getHoveredTile().getX());
                    int maxX = Math.max(selectedTile.getX(), cam.getHoveredTile().getX());
                    int minY = Math.min(selectedTile.getY(), cam.getHoveredTile().getY());
                    int maxY = Math.max(selectedTile.getY(), cam.getHoveredTile().getY());
                    fragment = new MapFragment(map, minX, minY, maxX, maxY);
                    listeners.stream().forEach((listener) -> {
                        listener.accept(fragment);
                    });
                }
                if (keyboard.isHold(KeyEvent.VK_CONTROL) && keyboard.isPressed(KeyEvent.VK_C)) {
                    clipboard = fragment.createDeepCopy();
                }
                if (keyboard.isHold(KeyEvent.VK_CONTROL) && keyboard.isPressed(KeyEvent.VK_V)) {
                    int minX = Math.min(selectedTile.getX(), cam.getHoveredTile().getX());
                    int minY = Math.min(selectedTile.getY(), cam.getHoveredTile().getY());
                    clipboard.paste(map, minX, minY);
                }
                break;
            case SINGLE:
                if (mouse.hold.left || mouse.hold.right) {
                    selectedTile = cam.getHoveredTile();
                    fragment = new MapFragment(map, selectedTile.getX(), selectedTile.getY(), selectedTile.getX(), selectedTile.getY());
                    listeners.stream().forEach((listener) -> {
                        listener.accept(fragment);
                    });
                }
                break;
        }
    }
    
    public static void addTileListener(Consumer<MapFragment> listener) {
        listeners.add(listener);
    }
    
    public static Tile getSelectedTile() {
        return selectedTile;
    }
    
    public static MapFragment getMapFragment() {
        return fragment;
    }
    
    public static void setMapFragment(MapFragment frag) {
        TileSelection.fragment = frag;
    }
    
}
