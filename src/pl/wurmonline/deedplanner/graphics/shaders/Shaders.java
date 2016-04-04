package pl.wurmonline.deedplanner.graphics.shaders;

import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;
import javax.media.opengl.GL2;
import pl.wurmonline.deedplanner.util.Log;

public final class Shaders {

    private static Shaders shaders;
    
    public static Shaders getShaders(GL2 g) {
        if (shaders==null) {
            shaders = new Shaders(g);
        }
        
        return shaders;
    }
    
    public final ShaderProgram simple;
    public final ShaderProgram diagonal;
    
    public Shaders(GL2 g) {
        simple = loadShader(g, "Simple", "Simple");
        diagonal = loadShader(g, "Diagonal", "Diagonal");
    }
    
    private ShaderProgram loadShader(GL2 g, String vert, String frag) {
        ShaderProgram shader = new ShaderProgram();
        ShaderCode vertCode = ShaderCode.create(g, GL2.GL_VERTEX_SHADER, this.getClass(), "", null, vert, false);
        ShaderCode fragCode = ShaderCode.create(g, GL2.GL_FRAGMENT_SHADER, this.getClass(), "", null, frag, false);
        shader.add(g, vertCode, System.err);
        shader.add(g, fragCode, System.err);
        shader.link(g, System.err);
        shader.validateProgram(g, System.err);
        shader.useProgram(g, false);
        Log.out(Shaders.class, "Shader loaded from files: \""+vert+"\" and \""+frag+"\"");
        return shader;
    }
    
    public String toString() {
        return "shaders instance";
    }
    
}
