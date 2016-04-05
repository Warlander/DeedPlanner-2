package pl.wurmonline.deedplanner.graphics.shaders;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.media.opengl.GL2;
import org.apache.commons.io.IOUtils;
import pl.wurmonline.deedplanner.util.Log;

public final class Shader {
    
    private final URL source;
    private final int type;
    
    private int shaderId = -1;
    
    Shader(URL source) {
        String path = source.getPath();
        if (!source.getPath().contains(".glsl")) {
            throw new IllegalArgumentException("Cannot find out non-glsl shader type");
        }
        String extension = path.substring(0, path.lastIndexOf('.'));
        extension = extension.substring(extension.lastIndexOf('.') + 1);
        
        this.source = source;
        this.type = getShaderType(extension);
    }
    
    Shader(URL source, int shaderType) {
        if (!isValidType(shaderType)) {
            throw new IllegalArgumentException("Invalid shader type");
        }
        
        this.source = source;
        this.type = shaderType;
    }
    
    private int getShaderType(String extension) {
        switch (extension) {
            case "vert":
                return GL2.GL_VERTEX_SHADER;
            case "frag":
                return GL2.GL_FRAGMENT_SHADER;
            default:
                throw new IllegalArgumentException("Cannot parse shader type");
        }
    }
    
    private boolean isValidType(int shaderType) {
        return shaderType == GL2.GL_VERTEX_SHADER || shaderType == GL2.GL_FRAGMENT_SHADER;
    }
    
    public synchronized int createOrGetShader(GL2 g) {
        if (shaderId == -1) {
            try (InputStream stream = source.openStream()) {
                String[] line = new String[] { IOUtils.toString(stream) };
                shaderId = g.glCreateShader(type);
                g.glShaderSource(shaderId, 1, line, null, 0);
                g.glCompileShader(shaderId);
            } catch (IOException ex) {
                Log.err(ex);
            }
        }
        
        return shaderId;
    }
    
    public URL getSource() {
        return source;
    }
    
    public int getType() {
        return type;
    }
    
}
