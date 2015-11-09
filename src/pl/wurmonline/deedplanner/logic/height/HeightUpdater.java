package pl.wurmonline.deedplanner.logic.height;

import javax.swing.*;
import pl.wurmonline.deedplanner.data.*;
import pl.wurmonline.deedplanner.graphics.UpCamera;
import pl.wurmonline.deedplanner.input.Mouse;
import pl.wurmonline.deedplanner.logic.SelectionType;
import pl.wurmonline.deedplanner.logic.TileFragment;
import pl.wurmonline.deedplanner.logic.TileSelection;
import pl.wurmonline.deedplanner.util.DeedPlannerRuntimeException;

public class HeightUpdater {

    public static HeightMode currentMode;
    public static boolean sizeEdit = false;
    
    public static JSpinner setRMB;
    public static JSpinner setLMB;
    
    public static int setLeft = 5;
    public static int setRight = -5;
    public static int add = 1;
    
    public static SelectionType update(Mouse mouse, Map map, UpCamera cam) {
        currentMode.update(mouse, map, cam);
        return currentMode.getSelectionType();
    }
    
    public static ListModel<HeightMode> createListModel() {
        DefaultListModel<HeightMode> model = new DefaultListModel<>();
        
        model.addElement(new HeightMode(java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle").getString("SET HEIGHT")) {

            public void action(Mouse mouse, Map map, Tile tile, TileFragment frag) {
                for (Tile t : tile.getAffectedTiles(frag)) {
                    if (mouse.hold.left) {
                        t.setHeight(setLeft);
                    }
                    else if (mouse.hold.right) {
                        t.setHeight(setRight);
                    }
                    else if (mouse.released.left || mouse.released.right) {
                        map.newAction();
                    }
                }
            }

            public SelectionType getSelectionType() {
                return SelectionType.NONE;
            }
            
        });
        
        
        
        model.addElement(new HeightMode(java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle").getString("ADD HEIGHT")) {

            public void action(Mouse mouse, Map map, Tile tile, TileFragment frag) {
                for (Tile t : tile.getAffectedTiles(frag)) {
                    if (mouse.pressed.left) {
                        t.setHeight(t.getHeight()+add);
                    }
                    else if (mouse.pressed.right) {
                        t.setHeight(t.getHeight()-add);
                    }
                    else if (mouse.released.left || mouse.released.right) {
                        map.newAction();
                    }
                }
            }
            
            public SelectionType getSelectionType() {
                return SelectionType.NONE;
            }
            
        });
        
        
        
        model.addElement(new HeightMode(java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle").getString("REMOVE HEIGHT")) {

            public void action(Mouse mouse, Map map, Tile tile, TileFragment frag) {
                for (Tile t : tile.getAffectedTiles(frag)) {
                    if (mouse.pressed.left) {
                        t.setHeight(t.getHeight()-add);
                    }
                    else if (mouse.pressed.right) {
                        t.setHeight(t.getHeight()+add);
                    }
                    else if (mouse.released.left || mouse.released.right) {
                        map.newAction();
                    }
                }
            }
            
            public SelectionType getSelectionType() {
                return SelectionType.NONE;
            }
            
        });
        
        model.addElement(new HeightMode(java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle").getString("SELECT HEIGHT")) {

            public void action(Mouse mouse, Map map, Tile tile, TileFragment frag) {
                if (frag.isCorner()) {
                    if (mouse.pressed.left) {
                        setLMB.getModel().setValue(tile.getHeight());
                    }
                    else if (mouse.pressed.right) {
                        setRMB.getModel().setValue(tile.getHeight());
                    }
                }
            }
            
            public SelectionType getSelectionType() {
                return SelectionType.NONE;
            }
            
        });
        
        model.addElement(new HeightMode(java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle").getString("RESET HEIGHT")) {

            public void action(Mouse mouse, Map map, Tile tile, TileFragment frag) {
                for (Tile t : tile.getAffectedTiles(frag)) {
                    if (mouse.hold.left || mouse.hold.right) {
                        t.setHeight(5);
                    }
                    else if (mouse.released.left || mouse.released.right) {
                        map.newAction();
                    }
                }
            }
            
            public SelectionType getSelectionType() {
                return SelectionType.NONE;
            }
            
        });
        
        model.addElement(new HeightMode(java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle").getString("SMOOTH HEIGHT")) {

            private Tile tile2;
            
            public void action(Mouse mouse, Map map, Tile tile, TileFragment frag) {
                if (mouse.pressed.right) {
                    tile2 = null;
                }
                if (mouse.pressed.left) {
                    if (tile2==null) {
                        if (frag.isCorner()) {
                            tile2 = frag.getTileByCorner(tile);
                        }
                    }
                    else if (frag.isCorner()) {
                        tile = frag.getTileByCorner(tile);
                        if (tile.getHeight()==tile2.getHeight()) {
                            tile2 = null;
                            return;
                        }
                        if (tile.getX()==tile2.getX() ^ tile.getY()==tile2.getY()) {
                            int min = Math.min(tile.getHeight(), tile2.getHeight());
                            int max = Math.max(tile.getHeight(), tile2.getHeight());
                            int diff = max-min;
                            Tile higher = tile.getHeight()==max ? tile : tile2;
                            Tile lower = tile.getHeight()==min ? tile : tile2;

                            int mult = 0;

                            if (tile.getX()==tile2.getX()) {
                                diff/=Math.abs(higher.getY()-lower.getY());
                                if (higher.getY()>lower.getY()) {
                                    for (int i=lower.getY(); i<higher.getY(); i++) {
                                        map.getTile(tile.getX(), i).setHeight(min + diff*mult);
                                        mult++;
                                    }
                                }
                                else if (lower.getY()>higher.getY()) {
                                    for (int i=lower.getY(); i>higher.getY(); i--) {
                                        map.getTile(tile.getX(), i).setHeight(min + diff*mult);
                                        mult++;
                                    }
                                }
                            }
                            else if (tile.getY()==tile2.getY()) {
                                diff/=Math.abs(higher.getX()-lower.getX());
                                if (higher.getX()>lower.getX()) {
                                    for (int i=lower.getX(); i<higher.getX(); i++) {
                                        map.getTile(i, tile.getY()).setHeight(min + diff*mult);
                                        mult++;
                                    }
                                }
                                else if (lower.getX()>higher.getX()) {
                                    for (int i=lower.getX(); i>higher.getX(); i--) {
                                        map.getTile(i, tile.getY()).setHeight(min + diff*mult);
                                        mult++;
                                    }
                                }
                            }
                            map.newAction();
                        }
                        tile2=null;
                    }
                }
            }
            
            public SelectionType getSelectionType() {
                return SelectionType.NONE;
            }
            
        });
        
        model.addElement(new HeightMode(java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle").getString("LEVEL AREA")) {
            
            public void action(Mouse mouse, Map map, Tile tile, TileFragment frag) {
                if (mouse.released.left) {
                    TileSelection.getMapFragment().forEach(t -> t.setHeight(setLeft));
                    map.newAction();
                    TileSelection.setMapFragment(null);
                }
                if (mouse.released.right) {
                    TileSelection.getMapFragment().forEach(t -> t.setHeight(setRight));
                    map.newAction();
                    TileSelection.setMapFragment(null);
                }
            }
            
            public SelectionType getSelectionType() {
                return SelectionType.MULTIPLE;
            }
            
        });
        
        model.addElement(new HeightMode(java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle").getString("LIFT AREA")) {
            
            public void action(Mouse mouse, Map map, Tile tile, TileFragment frag) {
                if (mouse.released.left || mouse.released.right) {
                    if (mouse.released.left) {
                        TileSelection.getMapFragment().forEach(t -> t.setHeight(t.getHeight()+add));
                    }
                    else {
                        TileSelection.getMapFragment().forEach(t -> t.setHeight(t.getHeight()-add));
                    }
                    map.newAction();
                    TileSelection.setMapFragment(null);
                }
            }
            
            public SelectionType getSelectionType() {
                return SelectionType.MULTIPLE;
            }
            
        });
        
        return model;
    }
}
