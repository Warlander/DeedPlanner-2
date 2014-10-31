package pl.wurmonline.deedplanner.logic.objects;

import java.awt.geom.Point2D;
import pl.wurmonline.deedplanner.Globals;
import pl.wurmonline.deedplanner.data.*;
import pl.wurmonline.deedplanner.graphics.UpCamera;
import pl.wurmonline.deedplanner.input.Mouse;

public class ObjectsUpdater {

    public static GameObjectData currentData = null;
    
    private static Tile tile;
    private static Point2D point;
    private static GameObject object;
    
    public static void update(Mouse mouse, Map map, UpCamera cam) {
        if (currentData!=null) {
            if (mouse.pressed.left) {
                tile = cam.tile;
                ObjectLocation location;
                if (currentData.centerOnly) {
                    location = ObjectLocation.MIDDLE_CENTER;
                }
                else {
                    location = ObjectLocation.calculateObjectLocation(cam.xTile, cam.yTile);
                }
                point = new Point2D.Double(tile.getX()+location.getHorizontalAlign()/4f, tile.getY()+location.getVerticalAlign()/4f);
                cam.tile.setGameObject(currentData, location, Globals.floor);
                object = cam.tile.getGameObject(Globals.floor, location);
                map.newAction();
            }
            else if (object!=null && mouse.hold.left) {
                float deltaX = cam.xMap - (float) point.getX();
                float deltaY = cam.yMap - (float) point.getY();
                object.setRotation(Math.atan2(deltaY, deltaX)+Math.PI/2f);
            }
            
            if (mouse.released.left) {
                tile = null;
                point = null;
                object = null;
            }
            
            if (mouse.hold.right && !mouse.hold.left) {
                ObjectLocation location = ObjectLocation.calculateObjectLocation(cam.xTile, cam.yTile);
                cam.tile.setGameObject(null, location, Globals.floor);
                map.newAction();
            }
        }
        
        
    }
    
}
