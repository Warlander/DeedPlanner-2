package pl.wurmonline.deedplanner.logic.walls;

import pl.wurmonline.deedplanner.Globals;
import pl.wurmonline.deedplanner.data.*;
import pl.wurmonline.deedplanner.data.storage.Data;
import pl.wurmonline.deedplanner.graphics.UpCamera;
import pl.wurmonline.deedplanner.input.Mouse;
import pl.wurmonline.deedplanner.logic.TileFragment;

public class WallUpdater {

    public static WallData currentData = Data.walls.get(0);
    
    public static void update(Mouse mouse, Map map, UpCamera cam) {
        TileFragment frag = TileFragment.calculateTileFragment(cam.xTile, cam.yTile);
        if (mouse.hold.left) {
            setWalls(map, cam.tile, currentData, frag);
        }
        else if (mouse.hold.right) {
            setWalls(map, cam.tile, null, frag);
        }
        
        if (mouse.released.left || mouse.released.right) {
            map.newAction();
        }
    }
    
    private static void setWalls(Map map, Tile tile, WallData wall, TileFragment frag) {
        if (frag==TileFragment.S) {
            tile.setHorizontalWall(wall, Globals.floor);
        }
        else if (frag==TileFragment.N) {
            map.getTile(tile, 0, 1).setHorizontalWall(wall, Globals.floor);
        }
        
        if (frag==TileFragment.W) {
            tile.setVerticalWall(wall, Globals.floor);
        }
        else if (frag==TileFragment.E) {
            map.getTile(tile, 1, 0).setVerticalWall(wall, Globals.floor);
        }
    }
    
}
