package pl.wurmonline.deedplanner.logic.floors;

import pl.wurmonline.deedplanner.data.Map;
import pl.wurmonline.deedplanner.data.Tile;
import pl.wurmonline.deedplanner.graphics.Camera;
import pl.wurmonline.deedplanner.input.Mouse;

public abstract class FloorMode {

    private final String name;
    
    public FloorMode(String name) {
        this.name = name;
    }
    
    public final void update(Mouse mouse, Map map, Camera cam) {
        Tile tile = cam.getHoveredTile();
        action(mouse, map, tile);
    }
    
    public abstract void action(Mouse mouse, Map map, Tile tile);
    
    public final String toString() {
        return name;
    }
    
}
