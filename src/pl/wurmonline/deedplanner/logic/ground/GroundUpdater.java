package pl.wurmonline.deedplanner.logic.ground;

import javax.swing.DefaultComboBoxModel;
import pl.wurmonline.deedplanner.data.*;
import pl.wurmonline.deedplanner.data.storage.Data;
import pl.wurmonline.deedplanner.graphics.UpCamera;
import pl.wurmonline.deedplanner.input.Mouse;

public class GroundUpdater {

    public static GroundMode currentMode;
    public static GroundData currentData = Data.grounds.get("gr");
    
    private static final GroundData defaultData = Data.grounds.get("gr");
    
    public static void update(Mouse mouse, Map map, UpCamera cam) {
        currentMode.update(mouse, map, cam);
    }
    
    public static DefaultComboBoxModel<GroundMode> createComboModel() {
        DefaultComboBoxModel<GroundMode> model = new DefaultComboBoxModel<>();
        
        model.addElement(new GroundMode("Pencil") {

            public void action(Mouse mouse, Map map, Tile tile) {
                if (mouse.hold.left) {
                    if (currentData!=null) {
                        tile.setGround(currentData);
                    }
                }
                else if (mouse.hold.right) {
                    if (currentData!=null) {
                        tile.setGround(defaultData);
                    }
                }
                else if (mouse.released.left || mouse.released.right) {
                    map.newAction();
                }
            }
            
        });
        
        model.addElement(new GroundMode("Fill") {
            
            public void action(Mouse mouse, Map map, Tile tile) {
                if (mouse.pressed.left) {
                    floodFill(map, tile, currentData, tile.getGround().getData());
                }
                else if (mouse.pressed.right) {
                    floodFill(map, tile, defaultData, tile.getGround().getData());
                }
                else if (mouse.released.left || mouse.released.right) {
                    map.newAction();
                }
            }
            
            private void floodFill(Map map, Tile tile, GroundData data, GroundData toReplace) {
                if (tile.getGround().getData()!=toReplace) {
                    return;
                }
                else {
                    tile.setGround(data);
                }
                
                Tile t = map.getTile(tile, -1, 0);
                if (t!=null) {
                    floodFill(map, t, data, toReplace);
                }
                t = map.getTile(tile, 1, 0);
                if (t!=null) {
                    floodFill(map, t, data, toReplace);
                }
                t = map.getTile(tile, 0, -1);
                if (t!=null) {
                    floodFill(map, t, data, toReplace);
                }
                t = map.getTile(tile, 0, 1);
                if (t!=null) {
                    floodFill(map, t, data, toReplace);
                }
            }
            
        });
        
        model.addElement(new GroundMode("Eraser") {

            public void action(Mouse mouse, Map map, Tile tile) {
                if (mouse.hold.left) {
                    tile.setGround(defaultData);
                }
                else if (mouse.hold.right) {
                    tile.setGround(defaultData);
                }
                else if (mouse.released.left || mouse.released.right) {
                    map.newAction();
                }
            }
            
        });
        
        return model;
    }
    
}
