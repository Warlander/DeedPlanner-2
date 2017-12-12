package pl.wurmonline.deedplanner;

import pl.wurmonline.deedplanner.data.AnimalAge;
import pl.wurmonline.deedplanner.data.AnimalGender;
import pl.wurmonline.deedplanner.data.EntityOrientation;
import pl.wurmonline.deedplanner.data.RoadDirection;
import pl.wurmonline.deedplanner.graphics.CameraType;
import pl.wurmonline.deedplanner.logic.Tab;

public class Globals {
    
    public static boolean renderBridges2d = true;
    
    public static boolean renderTrees3d = true;
    public static boolean renderTrees2d = true;
    
    public static CameraType cameraType = CameraType.TOP_VIEW;
    public static boolean fixedHeight = false;
    public static boolean renderHeight = false;
    
    public static boolean reverseWall = false;
    public static boolean autoReverseWall = true;
    
    public static boolean editSize;
    
    public static RoadDirection roadDirection = RoadDirection.CENTER;
    public static EntityOrientation floorOrientation = EntityOrientation.UP;
    public static Tab tab = Tab.ground;
    
    public static AnimalGender animalGender = AnimalGender.UNISEX;
    public static AnimalAge animalAge = AnimalAge.ADULT;
    
    public static int floor = 0;
    
    public static int glWindowWidth;
    public static int glWindowHeight;
    public static float glAspectRatio;

    // For use with symmetry
    public static boolean xSymLock = false;
    public static boolean ySymLock = false;
    public static boolean mirrorFloors = false;

}
