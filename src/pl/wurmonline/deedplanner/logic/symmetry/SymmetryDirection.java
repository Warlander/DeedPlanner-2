package pl.wurmonline.deedplanner.logic.symmetry;

/**
 * SymmetryDirection
 * Indicates the side of the tile symmetry is locked to.
 * @author Jonathan Walker (Keenan)
 */
public enum SymmetryDirection {

    /**
     * Default, no border or corner is selected. Used for tile locks.
     */
    CENTER,

    /**
     * North tile border.
     */
    N,

    /**
     * East tile border.
     */
    E,

    /**
     * South tile border.
     */
    S,

    /**
     * West tile border.
     */
    W,

    /**
     * Northeast tile corner.
     */
    NE,

    /**
     * Northwest tile corner.
     */
    NW,

    /**
     * Southeast tile corner.
     */
    SE,

    /**
     * Southwest tile corner.
     */
    SW;
        
    /**
     * Returns a string representation of the direction symmetry is locked to.
     * 
     * @param d SymmetryDirection enumerated direction.
     * @return String value of direction.
     */
    public static String toString(SymmetryDirection d) {
        switch(d) {
            case N:
                return "N";
            case S:
                return "S";
            case E:
                return "E";
            case W:
                return "W";
            case NE:
                return "NE";
            case SE:
                return "SE";
            case NW:
                return "NW";
            case SW:
                return "SW";
            default:
                return "";
        }
    }
    
    /**
     * Detects if the lock occurs on a corner.
     * 
     * @param d Enumerated direction
     * @return True if it is a corner, otherwise false.
     */
    public static boolean isCorner(SymmetryDirection d) {
        switch(d) {
            case NE:
            case SE:
            case NW:
            case SW:
                return true;
            default:
                return false;
        }
    }
}
