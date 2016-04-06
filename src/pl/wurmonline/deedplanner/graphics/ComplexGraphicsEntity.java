package pl.wurmonline.deedplanner.graphics;

import org.joml.Vector3f;
import pl.wurmonline.deedplanner.util.jogl.Model;

public abstract class ComplexGraphicsEntity implements GraphicsEntity {
    
    private final Model model;
    
    private final Vector3f translation;
    private final Vector3f rotation;
    private final Vector3f scale;
    
    public ComplexGraphicsEntity(Model model) {
        this.model = model;
        
        this.translation = new Vector3f(0, 0, 0);
        this.rotation = new Vector3f(0, 0, 0);
        this.scale = new Vector3f(1, 1, 1);
        
        GraphicsEngine.getInstance().register(this);
    }
    
}
