package pl.wurmonline.deedplanner.logic.ground;

import pl.wurmonline.deedplanner.data.Map;
import pl.wurmonline.deedplanner.data.Tile;
import pl.wurmonline.deedplanner.graphics.UpCamera;
import pl.wurmonline.deedplanner.input.Mouse;

public abstract class GroundMode {

    private final String name;
    
    public GroundMode(String name) {
        this.name = name;
    }
    
    public final void update(Mouse mouse, Map map, UpCamera cam) {
        Tile tile = cam.tile;
        action(mouse, map, tile);
    }
    
    public abstract void action(Mouse mouse, Map map, Tile tile);
    
    public final String toString() {
        return name;
    }
    
}
