package pl.wurmonline.deedplanner.util;

public class MathUtils {
    
    /**
     * Method which checks if two integers have the same sign (0 being treated as positive number)
     * 
     * @param i0 first integer
     * @param i1 second integer
     * @return true if the same sign, false otherwise
     */
    public static boolean isSameSign(int i0, int i1) {
        return (i0 < 0) == (i1 < 0);
    }
    
}
