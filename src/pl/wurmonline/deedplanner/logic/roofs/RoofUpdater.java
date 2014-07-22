package pl.wurmonline.deedplanner.logic.roofs;

import pl.wurmonline.deedplanner.Globals;
import pl.wurmonline.deedplanner.data.*;
import pl.wurmonline.deedplanner.graphics.UpCamera;
import pl.wurmonline.deedplanner.input.Mouse;

public class RoofUpdater {

    public static RoofData currentData = null;
    
    public static void update(Mouse mouse, Map map, UpCamera cam) {
        if (Globals.floor==0) {
            return;
        }
        if (mouse.hold.left) {
            if (currentData!=null) {
                cam.tile.setTileContent(new Roof(currentData), Globals.floor);
                map.recalculateRoofs();
            }
        }
        else if (mouse.hold.right) {
            if (currentData!=null) {
                cam.tile.setTileContent(null, Globals.floor);
                map.recalculateRoofs();
            }
        }
        else if (mouse.released.left || mouse.released.right) {
            map.newAction();
        }
    }
    
}
