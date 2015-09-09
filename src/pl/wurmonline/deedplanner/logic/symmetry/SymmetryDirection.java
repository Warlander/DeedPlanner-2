package pl.wurmonline.deedplanner.logic.symmetry;

public enum SymmetryDirection {
    CENTER, N, E, S, W, NE, NW, SE, SW;
    
    public static SymmetryDirection getDirFromString(String dir) {
        dir = dir.toLowerCase();
        switch(dir) {
            case "n":
                return N;
            case "e":
                return E;
            case "w":
                return W;
            case "s":
                return S;
            case "ne":
                return NE;
            case "se":
                return SE;
            case "nw":
                return NW;
            case "sw":
                return SW;
            default:
                return CENTER;
        }
    }
    
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
