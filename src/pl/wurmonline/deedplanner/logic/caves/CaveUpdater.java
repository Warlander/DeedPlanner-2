package pl.wurmonline.deedplanner.logic.caves;

import pl.wurmonline.deedplanner.data.CaveData;
import pl.wurmonline.deedplanner.data.Map;
import pl.wurmonline.deedplanner.data.Tile;
import pl.wurmonline.deedplanner.data.storage.Data;
import pl.wurmonline.deedplanner.graphics.UpCamera;
import pl.wurmonline.deedplanner.input.Mouse;

public class CaveUpdater {
    
    public static CaveData currentData = Data.caves.get("sw");
    
    private static final CaveData defaultData = Data.caves.get("sw");
    
    public static void update(Mouse mouse, Map map, UpCamera cam) {
        Tile tile = cam.tile;
        if (mouse.hold.left) {
            if (currentData!=null) {
                tile.setCaveEntity(currentData);
                map.getSymmetry().mirrorCaveEntity(tile, currentData);
            }
        }
        else if (mouse.hold.right) {
            if (currentData!=null) {
                tile.setCaveEntity(defaultData);
                map.getSymmetry().mirrorCaveEntity(tile, defaultData);
            }
        }
        else if (mouse.released.left || mouse.released.right) {
            map.newAction();
        }
    }
    
}
