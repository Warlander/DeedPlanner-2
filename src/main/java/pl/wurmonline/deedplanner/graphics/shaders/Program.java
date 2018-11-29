package pl.wurmonline.deedplanner.graphics.shaders;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2GL3;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public final class Program {
    
    private static Program currentProgram;
    
    private final Shader[] sources;
    private int programId = -1;
    
    private final Map<String, Integer> uniformsLocations;
    
    protected Program(Shader[] sources) {
        this.sources = sources;
        this.uniformsLocations = new HashMap<>();
    }
    
    public synchronized int createOrGetProgram(GL2GL3 g) {
        if (programId == -1) {
            programId = g.glCreateProgram();
            for (Shader shader : sources) {
                g.glAttachShader(programId, shader.createOrGetShader(g));
            }
            g.glLinkProgram(programId);
            g.glValidateProgram(programId);
            IntBuffer intBuffer = IntBuffer.allocate(1);
            g.glGetProgramiv(programId, GL2.GL_LINK_STATUS, intBuffer);
            if (intBuffer.get(0) != 1) {
                g.glGetProgramiv(programId, GL2.GL_INFO_LOG_LENGTH, intBuffer);
                int size = intBuffer.get(0);
                System.err.println("Program link error: ");
                if (size > 0) {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(size);
                    g.glGetProgramInfoLog(programId, size, intBuffer, byteBuffer);
                    for (byte b : byteBuffer.array()) {
                        System.err.print((char) b);
                    }
                }
                else {
                    System.err.println("Unknown OpenGL shader link error");
                }
            }
        }
        
        return programId;
    }
    
    public void bind(GL2GL3 g) {
        if (programId == -1) {
            createOrGetProgram(g);
        }
        
        if (currentProgram != this) {
            g.glUseProgram(programId);
            currentProgram = this;
        }
    }
    
    public void unbind(GL2GL3 g) {
        g.glUseProgram(0);
        currentProgram = null;
    }
    
    private int getUniformLocation(GL2GL3 g, String name) {
        Integer location = uniformsLocations.get(name);
        if (location == null) {
            createOrGetProgram(g);
            location = g.glGetUniformLocation(programId, name);
            uniformsLocations.put(name, location);
        }
        
        return location;
    }
    
    public void setInt(GL2GL3 g, String name, int value) {
        bind(g);
        int location = getUniformLocation(g, name);
        g.glUniform1i(location, value);
    }
    
    public int getInt(GL2GL3 g, String name) {
        bind(g);
        int location = getUniformLocation(g, name);
        IntBuffer buffer = IntBuffer.allocate(1);
        g.glGetUniformiv(programId, location, buffer);
        return buffer.get();
    }
    
    public void setFloat(GL2GL3 g, String name, float value) {
        bind(g);
        int location = getUniformLocation(g, name);
        g.glUniform1f(location, value);
    }
    
    public float getFloat(GL2GL3 g, String name) {
        bind(g);
        int location = getUniformLocation(g, name);
        FloatBuffer buffer = FloatBuffer.allocate(1);
        g.glGetUniformfv(programId, location, buffer);
        return buffer.get();
    }
    
    public void setVector3f(GL2GL3 g, String name, Vector3f value) {
        bind(g);
        int location = getUniformLocation(g, name);
        g.glUniform3f(location, value.x, value.y, value.z);
    }
    
    public Vector3f getVector3f(GL2GL3 g, String name) {
        bind(g);
        int location = getUniformLocation(g, name);
        FloatBuffer buffer = FloatBuffer.allocate(3);
        g.glGetUniformfv(programId, location, buffer);
        return new Vector3f(buffer.get(), buffer.get(), buffer.get());
    }
    
    public void setMatrix4f(GL2GL3 g, String name, Matrix4f value) {
        bind(g);
        int location = getUniformLocation(g, name);
        FloatBuffer buffer = FloatBuffer.allocate(16);
        value.get(buffer);
        g.glUniformMatrix4fv(location, 1, false, buffer);
    }
    
    public Matrix4f getMatrix4f(GL2GL3 g, String name) {
        bind(g);
        int location = getUniformLocation(g, name);
        FloatBuffer buffer = FloatBuffer.allocate(16);
        g.glGetUniformfv(programId, location, buffer);
        Matrix4f matrix = new Matrix4f(buffer);
        return matrix;
    }
    
}
