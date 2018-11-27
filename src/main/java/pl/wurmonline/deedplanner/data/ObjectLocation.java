package pl.wurmonline.deedplanner.data;

import pl.wurmonline.deedplanner.util.DeedPlannerRuntimeException;

public enum ObjectLocation {
    CORNER(0, 0),
    TOP_LEFT(Constants.LEFT_ALIGN, Constants.TOP_ALIGN), TOP_CENTER(Constants.CENTER_ALIGN, Constants.TOP_ALIGN), TOP_RIGHT(Constants.RIGHT_ALIGN, Constants.TOP_ALIGN),
    MIDDLE_LEFT(Constants.LEFT_ALIGN, Constants.MIDDLE_ALIGN), MIDDLE_CENTER(Constants.CENTER_ALIGN, Constants.MIDDLE_ALIGN), MIDDLE_RIGHT(Constants.RIGHT_ALIGN, Constants.MIDDLE_ALIGN),
    BOTTOM_LEFT(Constants.LEFT_ALIGN, Constants.BOTTOM_ALIGN), BOTTOM_CENTER(Constants.CENTER_ALIGN, Constants.BOTTOM_ALIGN), BOTTOM_RIGHT(Constants.RIGHT_ALIGN, Constants.BOTTOM_ALIGN);
    
    private static final class Constants {
        //ugly, but sadly there is probably no other way to assign constants inside enum without creating external class
        private static final float BOTTOM_ALIGN = 2f/3f;
        private static final float MIDDLE_ALIGN = 2f;
        private static final float TOP_ALIGN = 10f/3f;

        private static final float LEFT_ALIGN = 2f/3f;
        private static final float CENTER_ALIGN = 2f;
        private static final float RIGHT_ALIGN = 10f/3f;
    }
    
    private static final float TILE_SIZE = 1;
    private static final float CORNER_RADIUS = 0.15f;
    private static final float FIRST_THRESHOLD = 1f/3f;
    private static final float SECOND_THRESHOLD = 2f/3f;
    
    // JVM creates new array on every values() call, so we are caching it
    private static final ObjectLocation[] values = values();
    
    public static ObjectLocation calculateObjectLocation(float x, float y) {
        if (x<TILE_SIZE*FIRST_THRESHOLD) {
            if (y<TILE_SIZE*FIRST_THRESHOLD) {
                return BOTTOM_LEFT;
            }
            else if (y<TILE_SIZE*SECOND_THRESHOLD) {
                return MIDDLE_LEFT;
            }
            else {
                return TOP_LEFT;
            }
        }
        else if (x<TILE_SIZE*SECOND_THRESHOLD) {
            if (y<TILE_SIZE*FIRST_THRESHOLD) {
                return BOTTOM_CENTER;
            }
            else if (y<TILE_SIZE*SECOND_THRESHOLD) {
                return MIDDLE_CENTER;
            }
            else {
                return TOP_CENTER;
            }
        }
        else {
            if (y<TILE_SIZE*FIRST_THRESHOLD) {
                return BOTTOM_RIGHT;
            }
            else if (y<TILE_SIZE*SECOND_THRESHOLD) {
                return MIDDLE_RIGHT;
            }
            else {
                return TOP_RIGHT;
            }
        }
    }
    
    public static ObjectLocation parse(String string) {
        for (ObjectLocation loc : values()) {
            if (loc.toString().equals(string)) {
                return loc;
            }
        }
        throw new DeedPlannerRuntimeException("Corrupted save file - invalid object location.");
    }
    
    public static float getCornerRadius() {
        return CORNER_RADIUS;
    }
    
    public static ObjectLocation[] getValues() {
        return values;
    }
    
    private final float horizontalAlign;
    private final float verticalAlign;
    
    private ObjectLocation(float horizontalAlign, float verticalAlign) {
        this.horizontalAlign = horizontalAlign;
        this.verticalAlign = verticalAlign;
    }
    
    public float getHorizontalAlign() {
        return horizontalAlign;
    }
    
    public float getVerticalAlign() {
        return verticalAlign;
    }
    
}
