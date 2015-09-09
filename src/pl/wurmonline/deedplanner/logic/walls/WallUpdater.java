package pl.wurmonline.deedplanner.logic.walls;

import pl.wurmonline.deedplanner.Globals;
import pl.wurmonline.deedplanner.data.*;
import pl.wurmonline.deedplanner.data.storage.Data;
import pl.wurmonline.deedplanner.graphics.UpCamera;
import pl.wurmonline.deedplanner.input.Mouse;
import pl.wurmonline.deedplanner.logic.TileFragment;

public class WallUpdater {

    public static WallData currentData = Data.walls.values().iterator().next();
    
    public static void update(Mouse mouse, Map map, UpCamera cam) {
        TileFragment frag = TileFragment.calculateTileFragment(cam.xTile, cam.yTile);
        if (mouse.hold.left) {
            setWalls(map, cam.tile, currentData, frag);
        }
        else if (mouse.hold.right) {
            deleteWalls(map, cam.tile, frag);
        }
        
        if (mouse.released.left || mouse.released.right) {
            map.newAction();
        }
    }
    
    private static void setWalls(Map map, Tile tile, WallData wall, TileFragment frag) {
        if (frag==TileFragment.S) {
            tile.setHorizontalWall(wall, Globals.floor);
            map.getSymmetry().mirrorHorWall(tile, wall, Globals.floor, frag);
        }
        else if (frag==TileFragment.N) {
            map.getTile(tile, 0, 1).setHorizontalWall(wall, Globals.floor);
            map.getSymmetry().mirrorHorWall(map.getTile(tile, 0, 1), wall, Globals.floor, frag);
        }
        
        if (frag==TileFragment.W) {
            tile.setVerticalWall(wall, Globals.floor);
            map.getSymmetry().mirrorVertWall(tile, wall, Globals.floor, frag);
        }
        else if (frag==TileFragment.E) {
            map.getTile(tile, 1, 0).setVerticalWall(wall, Globals.floor);
            map.getSymmetry().mirrorVertWall(map.getTile(tile, 1, 0), wall, Globals.floor, frag);
            
        }
    }
    
    private static void deleteWalls(Map map, Tile tile, TileFragment frag) {
        if (frag==TileFragment.S) {
            tile.clearHorizontalWalls(Globals.floor);
            map.getSymmetry().mirrorClearHorzWall(tile, Globals.floor, frag);
        }
        else if (frag==TileFragment.N) {
            map.getTile(tile, 0, 1).clearHorizontalWalls(Globals.floor);
            map.getSymmetry().mirrorClearHorzWall(map.getTile(tile, 0, 1), Globals.floor, frag);
        }
        
        if (frag==TileFragment.W) {
            tile.clearVerticalWalls(Globals.floor);
            map.getSymmetry().mirrorClearVertWall(tile, Globals.floor, frag);
        }
        else if (frag==TileFragment.E) {
            map.getTile(tile, 1, 0).clearVerticalWalls(Globals.floor);
            map.getSymmetry().mirrorClearVertWall(map.getTile(tile, 1, 0), Globals.floor, frag);
        }
    }
    
}
