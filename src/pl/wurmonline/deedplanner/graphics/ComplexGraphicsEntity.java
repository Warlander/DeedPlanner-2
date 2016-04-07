package pl.wurmonline.deedplanner.graphics;

import org.joml.Vector3f;
import pl.wurmonline.deedplanner.util.jogl.Model;

public abstract class ComplexGraphicsEntity extends GraphicsEntity {
    
    private final Model model;
    
    private GraphicsEntity parent;
    private final Vector3f translation;
    private final Vector3f rotation;
    private final Vector3f scale;
    
    public ComplexGraphicsEntity(Model model) {
        this.model = model;
        
        this.translation = new Vector3f(0, 0, 0);
        this.rotation = new Vector3f(0, 0, 0);
        this.scale = new Vector3f(1, 1, 1);
    }
    
    public final Vector3f getTranslation() {
        return translation;
    }
    
    public final void setTranslation(float x, float y, float z) {
        translation.set(x, y, z);
    }
    
    public final void setTranslation(Vector3f other) {
        translation.set(other);
    }
    
    public final Vector3f getRotation() {
        return rotation;
    }
    
    public final void setRotation(float x, float y, float z) {
        rotation.set(x, y, z);
    }
    
    public final void setRotation(Vector3f other) {
        rotation.set(other);
    }
    
    public final Vector3f getScale() {
        return scale;
    }
    
    public final void setScale(float x, float y, float z) {
        scale.set(x, y, z);
    }
    
    public final void setScale(Vector3f other) {
        scale.set(other);
    }
    
}
