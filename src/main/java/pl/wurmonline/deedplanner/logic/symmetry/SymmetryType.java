package pl.wurmonline.deedplanner.logic.symmetry;

/**
 * SymmetryType
 * Indicates what type of symmetry lock is stored.
 * @author Jonathan Walker (Keenan)
 */
public enum SymmetryType {

    /**
     * Default for when there is no symmetry lock stored.
     */
    NONE,

    /**
     * Symmetry has been locked along a tile border.
     */
    BORDER,

    /**
     * Symmetry has been locked on a tile.
     */
    TILE,

    /**
     * Symmetry has been locked at an intersecting borders.
     */
    CORNER;
    
    /**
     * Gets the SymmetryType based on direction.
     * 
     * @param d SymmetryDirection
     * @return SymmetryType of BORDER, CORNER, or NONE
     */
    public static SymmetryType getType(SymmetryDirection d) {
        switch(d) {
            case NE:
            case SE:
            case NW:
            case SW:
                return SymmetryType.CORNER;
            case N:
            case S:
            case E:
            case W:
                return SymmetryType.BORDER;
            default:
                return SymmetryType.NONE;
        }
    }
}
