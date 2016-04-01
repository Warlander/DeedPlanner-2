package pl.wurmonline.deedplanner.graphics;

import pl.wurmonline.deedplanner.util.Vec3;
import pl.wurmonline.deedplanner.util.jogl.Model;

public abstract class ComplexGraphicsEntity implements GraphicsEntity {
    
    private final Model model;
    
    private final Vec3 translation;
    private final Vec3 rotation;
    private final Vec3 scale;
    
    public ComplexGraphicsEntity(Model model) {
        this.model = model;
        
        this.translation = new Vec3(0, 0, 0);
        this.rotation = new Vec3(0, 0, 0);
        this.scale = new Vec3(1, 1, 1);
        
        GraphicsEngine.getInstance().register(this);
    }
    
}
