package pl.wurmonline.deedplanner.logic.labels;

import java.util.ArrayList;
import java.util.function.Consumer;
import pl.wurmonline.deedplanner.data.Map;
import pl.wurmonline.deedplanner.data.Tile;
import pl.wurmonline.deedplanner.graphics.UpCamera;
import pl.wurmonline.deedplanner.input.Mouse;

public class LabelUpdater {

    private static Tile selectedTile = null;
    private static final ArrayList<Consumer<Tile>> listeners = new ArrayList<>();
    
    public static void update(Mouse mouse, Map map, UpCamera cam) {
        if (mouse.hold.left) {
            setSelectedTile(cam.tile);
        }
    }
    
    public static void addListener(Consumer<Tile> listener) {
        listeners.add(listener);
    }
    
    private static void setSelectedTile(Tile tile) {
        selectedTile = tile;
        listeners.stream().forEach((listener) -> {
            listener.accept(tile);
        });
    }
    
    public static Tile getSelectedTile() {
        return selectedTile;
    }
    
}
