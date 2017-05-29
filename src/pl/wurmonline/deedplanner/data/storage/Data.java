package pl.wurmonline.deedplanner.data.storage;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.DefaultListModel;
import javax.swing.tree.*;
import pl.wurmonline.deedplanner.data.*;
import pl.wurmonline.deedplanner.graphics.texture.SimpleTex;
import pl.wurmonline.deedplanner.util.DeedPlannerException;

public class Data {
    
    private static final ArrayList<String> shortnamesList = new ArrayList<>();
    
    public static final HashMap<String, GroundData> grounds = new HashMap<>();
    public static final DefaultMutableTreeNode groundsTree = new DefaultMutableTreeNode("Grounds");
    
    public static final HashMap<String, FloorData> floors = new HashMap<>();
    public static final DefaultMutableTreeNode floorsTree = new DefaultMutableTreeNode("Floors");
    
    public static final HashMap<String, WallData> walls = new HashMap<>();
    public static final DefaultMutableTreeNode wallsTree = new DefaultMutableTreeNode("Walls");
    
    public static final HashMap<String, RoofData> roofs = new HashMap<>();
    public static final DefaultListModel<RoofData> roofsList = new DefaultListModel<>();
    
    public static final HashMap<String, BorderData> borders = new HashMap<>();
    public static final DefaultListModel<BorderData> bordersList = new DefaultListModel<>();
    
    public static final HashMap<String, GameObjectData> objects = new HashMap<>();
    public static final DefaultMutableTreeNode objectsTree = new DefaultMutableTreeNode("Objects");
    public static DefaultMutableTreeNode filteredObjectsTree = objectsTree;
    
    public static final HashMap<String, AnimalData> animals = new HashMap<>();
    public static final DefaultMutableTreeNode animalsTree = new DefaultMutableTreeNode("Animals");
    
    public static final HashMap<String, CaveData> caves = new HashMap<>();
    public static final DefaultMutableTreeNode cavesTree = new DefaultMutableTreeNode("Caves");
    
    public static final SimpleTex water = SimpleTex.getTexture("Data/Special/water.png");
    
    public static void verifyShortname(String shortname) throws DeedPlannerException {
        if (shortnamesList.contains(shortname)) {
            throw new DeedPlannerException("Duplicate shortname: " + shortname);
        }
        
        shortnamesList.add(shortname);
    }
    
    public static void clearShortnames() {
        shortnamesList.clear();
    }
    
}
