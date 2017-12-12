package pl.wurmonline.deedplanner.logic.roofs;

import pl.wurmonline.deedplanner.Globals;
import pl.wurmonline.deedplanner.data.*;
import pl.wurmonline.deedplanner.graphics.Camera;
import pl.wurmonline.deedplanner.graphics.UpCamera;
import pl.wurmonline.deedplanner.input.Mouse;

public class RoofUpdater {

    public static RoofData currentData = null;
    
    public static void update(Mouse mouse, Map map, Camera cam) {
        if (Globals.floor <= 0) {
            return;
        }
        if (mouse.hold.left) {
            if (currentData!=null) {
                cam.getHoveredTile().setTileContent(new Roof(currentData), Globals.floor);
                map.getSymmetry().mirrorRoof(cam.getHoveredTile(), currentData, Globals.floor);
                map.recalculateRoofs();
            }
        }
        else if (mouse.hold.right) {
            if (currentData!=null) {
                cam.getHoveredTile().setTileContent(null, Globals.floor);
                map.getSymmetry().mirrorRoof(cam.getHoveredTile(), null, Globals.floor);
                map.recalculateRoofs();
            }
        }
        else if (mouse.released.left || mouse.released.right) {
            map.newAction();
        }
        map.recalculateRoofs();
    }
    
}
