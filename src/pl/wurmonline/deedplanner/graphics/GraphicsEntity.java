package pl.wurmonline.deedplanner.graphics;

import org.joml.Vector3f;

public abstract class GraphicsEntity {
    
    public abstract Vector3f getTranslation();
    public abstract Vector3f getRotation();
    public abstract Vector3f getScale();
    
    public GraphicsEntity() {
        GraphicsEngine.getInstance().register(this);
    }
    
    public final void destroy() {
        GraphicsEngine.getInstance().unregister(this);
    }
    
}
