package pl.wurmonline.deedplanner.graphics;

import pl.wurmonline.deedplanner.util.Vec3;

public interface GraphicsEntity {
    
    public abstract Vec3 getTranslation();
    public abstract Vec3 getRotation();
    public abstract Vec3 getScale();
    
    
    public default void destroy() {
        GraphicsEngine.getInstance().unregister(this);
    }
    
}
