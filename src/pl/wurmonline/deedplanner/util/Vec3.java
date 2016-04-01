package pl.wurmonline.deedplanner.util;

public class Vec3 {
    
    public float x;
    public float y;
    public float z;
    
    public Vec3() {
        this(0, 0, 0);
    }
    
    public Vec3(Vec3 other) {
        this(other.x, other.y, other.z);
    }
    
    public Vec3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
    
    public void set(Vec3 other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }
    
}
