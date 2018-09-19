package pl.wurmonline.deedplanner.logic.walls;

import pl.wurmonline.deedplanner.Globals;
import pl.wurmonline.deedplanner.data.*;
import pl.wurmonline.deedplanner.data.storage.Data;
import pl.wurmonline.deedplanner.graphics.Camera;
import pl.wurmonline.deedplanner.input.Mouse;
import pl.wurmonline.deedplanner.logic.TileFragment;

public class WallUpdater {

    public static WallData currentData = Data.walls.values().iterator().next();
    
    public static void update(Mouse mouse, Map map, Camera cam) {
        TileFragment frag = cam.getHoveredTileFragment();
        if (mouse.hold.left) {
            setWalls(map, cam.getHoveredTile(), currentData, frag);
        }
        else if (mouse.hold.right) {
            deleteWalls(map, cam.getHoveredTile(), frag);
        }
        
        if (mouse.released.left || mouse.released.right) {
            map.newAction();
        }
    }
    
    private static void setWalls(Map map, Tile tile, WallData wall, TileFragment frag) {
        if (frag==TileFragment.S) {
            tile.setHorizontalWall(wall, Globals.floor);
            map.getSymmetry().mirrorHorizontalWall(tile, wall, Globals.floor, frag);
        }
        else if (frag==TileFragment.N) {
            map.getTile(tile, 0, 1).setHorizontalWall(wall, Globals.floor);
            map.getSymmetry().mirrorHorizontalWall(map.getTile(tile, 0, 1), wall, Globals.floor, frag);
        }
        
        if (frag==TileFragment.W) {
            tile.setVerticalWall(wall, Globals.floor);
            map.getSymmetry().mirrorVerticalWall(tile, wall, Globals.floor, frag);
        }
        else if (frag==TileFragment.E) {
            map.getTile(tile, 1, 0).setVerticalWall(wall, Globals.floor);
            map.getSymmetry().mirrorVerticalWall(map.getTile(tile, 1, 0), wall, Globals.floor, frag);
            
        }
    }
    
    private static void deleteWalls(Map map, Tile tile, TileFragment frag) {
        if (frag==TileFragment.S) {
            tile.clearHorizontalWalls(Globals.floor);
            map.getSymmetry().mirrorClearHorizontalWalls(tile, Globals.floor, frag);
        }
        else if (frag==TileFragment.N) {
            map.getTile(tile, 0, 1).clearHorizontalWalls(Globals.floor);
            map.getSymmetry().mirrorClearHorizontalWalls(map.getTile(tile, 0, 1), Globals.floor, frag);
        }
        
        if (frag==TileFragment.W) {
            tile.clearVerticalWalls(Globals.floor);
            map.getSymmetry().mirrorClearVerticalWalls(tile, Globals.floor, frag);
        }
        else if (frag==TileFragment.E) {
            map.getTile(tile, 1, 0).clearVerticalWalls(Globals.floor);
            map.getSymmetry().mirrorClearVerticalWalls(map.getTile(tile, 1, 0), Globals.floor, frag);
        }
    }
    
}
