package pl.wurmonline.deedplanner.util;

public class Mat4 {
    
    public final float[] matrix;
    
    public Mat4() {
        matrix = new float[] {1, 0, 0, 0,
                              0, 1, 0, 0,
                              0, 0, 1, 0,
                              0, 0, 0, 1};
    }

    public float[] getMatrix() {
        return matrix;
    }
    
    public float get(int column, int row) {
        return matrix[row * 4 + column];
    }
    
    public void set(int column, int row, float value) {
        matrix[row * 4 + column] = value;
    }
    
}
