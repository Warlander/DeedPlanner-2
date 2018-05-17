package pl.wurmonline.deedplanner;

import java.util.Random;

public class Constants {
    
    public static final Random random = new Random();
    
    public static final int FLOORS_LIMIT = 17;
    public static final int CAVE_LIMIT = -7;
    
    public static final float HEIGHT_MOD = 10f;
    
    public static final String VERSION_STRING = "2.9.2";
    public static final String TITLE_STRING = "DeedPlanner " + VERSION_STRING;
    
    public static final String TREE_TYPE = "tree";
    public static final String BUSH_TYPE = "bush";
    
    public static final float FLOOR_MODEL_HEIGHT = 0.25f;
    
    public static final String GOOGLE_ANALYTICS_ID = "UA-104973502-1";
    public static final String GOOGLE_ANALYTICS_APP_NAME = "DeedPlanner Java";
    
    public static final int GOOGLE_ANALYTICS_DIMENSION_OPENGL_PROFILE = 1;
    public static final int GOOGLE_ANALYTICS_DIMENSION_SHADING_LANGUAGE_VERSION = 2;
    
    public static final String GITHUB_API_RELEASES_URL = "https://api.github.com/repos/Warlander/DeedPlanner-2/releases?page=1";
    
}
