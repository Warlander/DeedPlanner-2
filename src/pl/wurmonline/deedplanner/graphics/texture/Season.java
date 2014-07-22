package pl.wurmonline.deedplanner.graphics.texture;

import pl.wurmonline.deedplanner.util.DeedPlannerRuntimeException;

public final class Season {

    private Season() {}
    
    public final static int
            SPRING = 0,
            SUMMER = 1,
            FALL = 2,
            WINTER = 3,
            ALL =    4;
    
    public static int seasonFromString(String name) {
        switch (name.toUpperCase()) {
            case "ALL":
                return ALL;
            case "SPRING":
                return SPRING;
            case "SUMMER":
                return SUMMER;
            case "FALL":
                return FALL;
            case "WINTER":
                return WINTER;
            default:
                throw new DeedPlannerRuntimeException("Unknown season - program data is corrupted!");
        }
    }
    
}
