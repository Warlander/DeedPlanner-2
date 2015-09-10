package pl.wurmonline.deedplanner;

import pl.wurmonline.deedplanner.data.EntityOrientation;
import pl.wurmonline.deedplanner.data.RoadDirection;
import pl.wurmonline.deedplanner.graphics.texture.Season;
import pl.wurmonline.deedplanner.logic.Tab;

public class Globals {

    public static boolean upCamera = true;
    public static boolean fixedHeight = false;
    public static boolean renderHeight = false;
    
    public static boolean reverseWall = false;
    public static boolean autoReverseWall = true;
    
    public static boolean editSize;
    
    public static int season = Season.SUMMER;
    public static RoadDirection roadDirection = RoadDirection.CENTER;
    public static EntityOrientation floorOrientation = EntityOrientation.UP;
    public static Tab tab = Tab.ground;
    
    public static int floor = 0;
    public static int visibleDownX;
    public static int visibleUpX;
    public static int visibleDownY;
    public static int visibleUpY;
    
    public static int glWindowWidth;
    public static int glWindowHeight;
    public static float glAspectRatio;
    
}
