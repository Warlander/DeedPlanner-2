package pl.wurmonline.deedplanner.data.storage;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.tree.*;
import pl.wurmonline.deedplanner.data.*;
import pl.wurmonline.deedplanner.util.jogl.Tex;

public class Data {

    public static final ArrayList<GroundData> grounds = new ArrayList<>();
    public static final DefaultMutableTreeNode groundsTree = new DefaultMutableTreeNode("Grounds");
    
    public static final ArrayList<FloorData> floors = new ArrayList<>();
    public static final DefaultMutableTreeNode floorsTree = new DefaultMutableTreeNode("Floors");
    
    public static final ArrayList<WallData> walls = new ArrayList<>();
    public static final DefaultMutableTreeNode wallsTree = new DefaultMutableTreeNode("Walls");
    
    public static final DefaultListModel<RoofData> roofs = new DefaultListModel<>();
    
    public static Tex water = Tex.getTexture("Data/Special/water.png");
    
}
