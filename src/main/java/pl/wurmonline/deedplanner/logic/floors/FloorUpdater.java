package pl.wurmonline.deedplanner.logic.floors;

import javax.swing.DefaultComboBoxModel;
import pl.wurmonline.deedplanner.Globals;
import pl.wurmonline.deedplanner.data.*;
import pl.wurmonline.deedplanner.graphics.Camera;
import pl.wurmonline.deedplanner.input.Mouse;

public class FloorUpdater {

    public static FloorMode currentMode;
    public static FloorData currentData = null;
    
    public static void update(Mouse mouse, Map map, Camera cam) {
        currentMode.update(mouse, map, cam);
    }
    
    public static DefaultComboBoxModel<FloorMode> createComboModel() {
        DefaultComboBoxModel<FloorMode> model = new DefaultComboBoxModel<>();
        
        model.addElement(new FloorMode(java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle").getString("PENCIL")) {

            public void action(Mouse mouse, Map map, Tile tile) {
                if (mouse.hold.left) {
                    if (currentData!=null && !(tile.getTileContent(Globals.floor) instanceof Roof)) {
                        if (currentData.opening && Globals.floor==0) {
                            return;
                        }
                        Floor floor;
                        if (currentData==null) {
                            floor = null;
                        }
                        else {
                            floor = new Floor(currentData, Globals.floorOrientation);
                        }
                        tile.setTileContent(floor, Globals.floor);
                        map.getSymmetry().mirrorFloor(tile, currentData, Globals.floorOrientation, Globals.floor);
                    }
                }
                else if (mouse.hold.right) {
                    if (!(tile.getTileContent(Globals.floor) instanceof Roof)) {
                        tile.setTileContent(null, Globals.floor);
                        map.getSymmetry().mirrorFloor(tile, null, Globals.floorOrientation, Globals.floor);
                    }
                }
                else if (mouse.released.left || mouse.released.right) {
                    map.newAction();
                }
            }
            
        });
        
        model.addElement(new FloorMode(java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle").getString("ERASER")) {

            public void action(Mouse mouse, Map map, Tile tile) {
                if (mouse.hold.left  && !(tile.getTileContent(Globals.floor) instanceof Roof)) {
                    tile.setTileContent(null, Globals.floor);
                    map.getSymmetry().mirrorFloor(tile, null, Globals.floorOrientation, Globals.floor);
                }
                else if (mouse.hold.right  && !(tile.getTileContent(Globals.floor) instanceof Roof)) {
                    tile.setTileContent(null, Globals.floor);
                    map.getSymmetry().mirrorFloor(tile, null, Globals.floorOrientation, Globals.floor);
                }
                else if (mouse.released.left || mouse.released.right) {
                    map.newAction();
                }
            }
            
        });
        
        return model;
    }
    
}
