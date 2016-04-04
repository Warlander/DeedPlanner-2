package pl.wurmonline.deedplanner.graphics.shaders;

import com.jogamp.opengl.util.glsl.ShaderProgram;
import javax.media.opengl.GL2;

public abstract class Shader {
    
    private final ShaderProgram program;
    
    protected Shader(ShaderProgram program) {
        this.program = program;
    }
    
    public abstract void bind(GL2 g);
    
    public abstract void unbind(GL2 g);
    
}
