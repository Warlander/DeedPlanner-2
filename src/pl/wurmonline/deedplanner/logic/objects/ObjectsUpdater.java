package pl.wurmonline.deedplanner.logic.objects;

import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import pl.wurmonline.deedplanner.Constants;
import pl.wurmonline.deedplanner.Globals;
import pl.wurmonline.deedplanner.data.*;
import pl.wurmonline.deedplanner.graphics.UpCamera;
import pl.wurmonline.deedplanner.input.Keyboard;
import pl.wurmonline.deedplanner.input.Mouse;

public class ObjectsUpdater {

    public static GameObjectData currentData = null;
    
    private static Tile tile;
    private static Point2D point;
    private static GameObject object;
    private static ObjectLocation location;
    
    public static void update(Mouse mouse, Keyboard keyboard, Map map, UpCamera cam) {
        if (currentData!=null) {
            if (Globals.floor < 0 && (currentData.type.equals(Constants.TREE_TYPE) || currentData.type.equals(Constants.BUSH_TYPE))) {
                return;
            }
            
            if (mouse.pressed.left) {
                tile = cam.tile;
                if (currentData.centerOnly) {
                    location = ObjectLocation.MIDDLE_CENTER;
                }
                else {
                    location = ObjectLocation.calculateObjectLocation(cam.xTile, cam.yTile);
                }
                point = new Point2D.Double(tile.getX()+location.getHorizontalAlign()/4f, tile.getY()+location.getVerticalAlign()/4f);
                cam.tile.setGameObject(currentData, location, Globals.floor);
                map.getSymmetry().mirrorObject(cam.tile, currentData, location, Globals.floor);
                object = cam.tile.getGameObject(Globals.floor, location);
                map.newAction();
            }
            else if (object!=null && mouse.hold.left) {
                float deltaX = cam.xMap - (float) point.getX();
                float deltaY = cam.yMap - (float) point.getY();
                double rotation = Math.atan2(deltaY, deltaX)+Math.PI/2f;
                if (!keyboard.isHold(KeyEvent.VK_SHIFT)) {
                    rotation = getClampedRotation(rotation);
                }
                object.setRotation(rotation);
                map.getSymmetry().mirrorObjectRotation(tile, object, rotation, location, Globals.floor);
            }
            
            if (mouse.released.left) {
                tile = null;
                point = null;
                object = null;
                location = null;
            }
            
            if (mouse.hold.right && !mouse.hold.left) {
                location = ObjectLocation.calculateObjectLocation(cam.xTile, cam.yTile);
                cam.tile.setGameObject(null, location, Globals.floor);
                map.getSymmetry().mirrorObject(cam.tile, null, location, Globals.floor);
                map.newAction();
            }
        }
    }
    
    private static double getClampedRotation(double rotation) {
        final double quarterPI = (Math.PI / 4);
        return quarterPI * Math.round(rotation / quarterPI);
    }
    
}
