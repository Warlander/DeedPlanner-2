package pl.wurmonline.deedplanner.logic.height;

import pl.wurmonline.deedplanner.data.Map;
import pl.wurmonline.deedplanner.data.Tile;
import pl.wurmonline.deedplanner.graphics.UpCamera;
import pl.wurmonline.deedplanner.input.Mouse;
import pl.wurmonline.deedplanner.logic.SelectionType;
import pl.wurmonline.deedplanner.logic.TileFragment;

public abstract class HeightMode {

    private final String name;
    
    public HeightMode(String name) {
        this.name = name;
    }
    
    public final void update(Mouse mouse, Map map, UpCamera cam) {
        TileFragment frag = TileFragment.calculateTileFragment(cam.xTile, cam.yTile);
        Tile tile = cam.tile;
        action(mouse, map, tile, frag);
    }
    
    public abstract void action(Mouse mouse, Map map, Tile tile, TileFragment frag);
    
    public abstract SelectionType getSelectionType();
    
    public final String toString() {
        return name;
    }
    
}
