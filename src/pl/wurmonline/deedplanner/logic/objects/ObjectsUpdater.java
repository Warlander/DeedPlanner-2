package pl.wurmonline.deedplanner.logic.objects;

import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3d;
import pl.wurmonline.deedplanner.Constants;
import pl.wurmonline.deedplanner.Globals;
import pl.wurmonline.deedplanner.data.*;
import pl.wurmonline.deedplanner.graphics.Camera;
import pl.wurmonline.deedplanner.graphics.UpCamera;
import pl.wurmonline.deedplanner.input.Keyboard;
import pl.wurmonline.deedplanner.input.Mouse;
import pl.wurmonline.deedplanner.logic.Tab;

public class ObjectsUpdater {

    public static GameObjectData objectsCurrentData = null;
    public static AnimalData animalsCurrentData = null;
    
    private static Tile tile;
    private static Vector2d point;
    private static GridTileEntity object;
    private static ObjectLocation location;
    
    public static void update(Mouse mouse, Keyboard keyboard, Map map, Camera cam) {
        if (mouse.pressed.left) {
            if (!containsData() || !checkUndergroundPermissions()) {
                return;
            }
            tile = cam.getHoveredTile();
            location = calculateLocation(cam);
            point = new Vector2d(tile.getX()+location.getHorizontalAlign()/4f, tile.getY()+location.getVerticalAlign()/4f);
            placeEntity(map, tile, location, Globals.floor);
            object = tile.getGridEntity(Globals.floor, location);
            map.newAction();
        }
        else if (object != null && mouse.hold.left) {
            Vector3d camPosition = cam.getPosition();
            double deltaX = camPosition.x - point.x;
            double deltaY = camPosition.y - point.y;
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
            location = calculateLocation(cam);
            cam.getHoveredTile().setGameObject(null, null, Globals.floor);
            map.getSymmetry().mirrorObject(cam.getHoveredTile(), null, location, Globals.floor);
            map.newAction();
        }
    }
    
    private static boolean containsData() {
        if (isObjectEditor()) {
            return objectsCurrentData != null;
        }
        else {
            return animalsCurrentData != null;
        }
    }
    
    private static boolean checkUndergroundPermissions() {
        if (!isObjectEditor()) {
            return true;
        }
        
        return !(Globals.floor < 0 && (objectsCurrentData.type.equals(Constants.TREE_TYPE) || objectsCurrentData.type.equals(Constants.BUSH_TYPE)));
    }
    
    private static ObjectLocation calculateLocation(Camera cam) {
        Vector2f tilePos = cam.getHoveredTilePosition();
        float xPos = tilePos.x;
        float yPos = tilePos.y;
        float xDist = Math.min(xPos, 1 - xPos);
        float yDist = Math.min(yPos, 1 - yPos);
        float distToCorner = (float) Math.sqrt(xDist * xDist + yDist * yDist);
        boolean corner = distToCorner < ObjectLocation.getCornerRadius();
        
        if (corner || (isObjectEditor() && objectsCurrentData.cornerOnly)) {
            Tile hoveredTile = cam.getHoveredTile();
            if (xPos > 0.5f) {
                tile = hoveredTile.getMap().getTile(hoveredTile, 1, 0);
            }
            if (yPos > 0.5f) {
                tile = hoveredTile.getMap().getTile(hoveredTile, 0, 1);
            }
            
            return ObjectLocation.CORNER;
        }
        
        if (isObjectEditor() && objectsCurrentData.centerOnly) {
            return ObjectLocation.MIDDLE_CENTER;
        }
        
        return ObjectLocation.calculateObjectLocation(xPos, yPos);
    }
    
    private static void placeEntity(Map map, Tile tile, ObjectLocation location, int floor) {
        if (isObjectEditor()) {
            tile.setGameObject(objectsCurrentData, location, Globals.floor);
            map.getSymmetry().mirrorObject(tile, objectsCurrentData, location, Globals.floor);
        }
        else {
            tile.setAnimal(animalsCurrentData, location, Globals.floor);
            map.getSymmetry().mirrorAnimal(tile, animalsCurrentData, location, Globals.floor);
        }
    }
    
    private static boolean isObjectEditor() {
        return Globals.tab == Tab.objects;
    }
    
    private static double getClampedRotation(double rotation) {
        final double quarterPI = (Math.PI / 4);
        return quarterPI * Math.round(rotation / quarterPI);
    }
    
}
