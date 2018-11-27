package pl.wurmonline.deedplanner.data;

import pl.wurmonline.deedplanner.util.DeedPlannerRuntimeException;

public enum RoadDirection {
    CENTER, NW, NE, SW, SE;
    
    public static int toInt(RoadDirection dir) {
        switch (dir) {
            case CENTER:
                return 4;
            case NW:
                return 0;
            case NE:
                return 1;
            case SW:
                return 2;
            case SE:
                return 3;
            default:
                throw new DeedPlannerRuntimeException("This exception is impossible anyway");
        }
    }
}
