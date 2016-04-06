package pl.wurmonline.deedplanner.graphics;

import org.joml.Vector3f;

public interface GraphicsEntity {
    
    public abstract Vector3f getTranslation();
    public abstract Vector3f getRotation();
    public abstract Vector3f getScale();
    
    
    public default void destroy() {
        GraphicsEngine.getInstance().unregister(this);
    }
    
}
