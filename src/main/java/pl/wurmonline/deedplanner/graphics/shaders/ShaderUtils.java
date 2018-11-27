package pl.wurmonline.deedplanner.graphics.shaders;

import java.net.URL;

class ShaderUtils {
    
    static Program loadVertFragProgram(String shader) {
        String vertexString = shader + ".vert.glsl";
        URL vertexUrl = Shaders.class.getResource(vertexString);
        Shader vertexShader = new Shader(vertexUrl);
        
        String fragmentString = shader + ".frag.glsl";
        URL fragmentUrl = Shaders.class.getResource(fragmentString);
        Shader fragmentShader = new Shader(fragmentUrl);
        
        Shader[] shaders = { vertexShader, fragmentShader };
        
        return new Program(shaders);
    }
    
}
