package pl.wurmonline.deedplanner.graphics.shaders;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import javax.media.opengl.GL2;

public abstract class Program {
    
    private final Shader[] sources;
    private int programId = -1;
    
    protected Program(Shader[] sources) {
        this.sources = sources;
    }
    
    public synchronized int createOrGetProgram(GL2 g) {
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
            else {
                findUniformsLocations(g, programId);
            }
        }
        
        return programId;
    }
    
    public void bind(GL2 g) {
        if (programId == -1) {
            createOrGetProgram(g);
        }
        
        g.glUseProgram(programId);
        updateUniforms(g);
    }
    
    public void unbind(GL2 g) {
        g.glUseProgram(0);
    }
    
    protected abstract void findUniformsLocations(GL2 g, int programId);
    
    public abstract void updateUniforms(GL2 g);
    
}
