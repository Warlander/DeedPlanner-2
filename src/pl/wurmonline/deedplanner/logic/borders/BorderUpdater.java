/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.wurmonline.deedplanner.logic.borders;

import pl.wurmonline.deedplanner.data.BorderData;
import pl.wurmonline.deedplanner.data.Map;
import pl.wurmonline.deedplanner.data.Tile;
import pl.wurmonline.deedplanner.data.storage.Data;
import pl.wurmonline.deedplanner.graphics.UpCamera;
import pl.wurmonline.deedplanner.input.Mouse;
import pl.wurmonline.deedplanner.logic.TileFragment;

/**
 *
 * @author Maciej
 */
public class BorderUpdater {
    
    public static BorderData currentData = Data.borders.get(0);
    
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
    
    private static void setWalls(Map map, Tile tile, BorderData border, TileFragment frag) {
        if (frag==TileFragment.S) {
            tile.setHorizontalBorder(border);
            map.getSymmetry().mirrorHorizontalBorder(tile, border, frag);
        }
        else if (frag==TileFragment.N) {
            map.getTile(tile, 0, 1).setHorizontalBorder(border);
            map.getSymmetry().mirrorHorizontalBorder(map.getTile(tile, 0, 1), border, frag);
        }
        
        if (frag==TileFragment.W) {
            tile.setVerticalBorder(border);
            map.getSymmetry().mirrorVerticalBorder(tile, border, frag);
        }
        else if (frag==TileFragment.E) {
            map.getTile(tile, 1, 0).setVerticalBorder(border);
            map.getSymmetry().mirrorVerticalBorder(map.getTile(tile, 1, 0), border, frag);
        }
    }
    
}
