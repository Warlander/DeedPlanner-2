package pl.wurmonline.deedplanner.graphics.shaders;

import javax.media.opengl.GL2;

public class SimpleProgram extends Program {
    
    SimpleProgram() {
        super(ShaderUtils.loadVertFragShaders("Simple"));
    }
    
    protected void findUniformsLocations(GL2 g, int programId) {
        
    }

    public void updateUniforms(GL2 g) {
        
    }
    
}
