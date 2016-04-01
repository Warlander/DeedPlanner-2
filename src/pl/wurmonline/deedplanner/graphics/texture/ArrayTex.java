package pl.wurmonline.deedplanner.graphics.texture;

import com.jogamp.opengl.util.GLBuffers;
import java.nio.IntBuffer;
import javax.media.opengl.GL3;

public class ArrayTex {
    
    private final int textureId;
    
    public ArrayTex(GL3 g, int width, int height, int layers) {
        g.glEnable(GL3.GL_TEXTURE_2D_ARRAY);
        IntBuffer singleTexBuffer = GLBuffers.newDirectIntBuffer(1);
        
        g.glGenTextures(1, singleTexBuffer);
        textureId = singleTexBuffer.get();
        g.glBindTexture(GL3.GL_TEXTURE_2D_ARRAY, textureId);
        g.glTexStorage3D(GL3.GL_TEXTURE_2D_ARRAY, 4, GL3.GL_RGBA8, width, height, layers);
    }
    
    public void destroy(GL3 g) {
        IntBuffer singleTexBuffer = GLBuffers.newDirectIntBuffer(1);
        singleTexBuffer.put(textureId);
        singleTexBuffer.rewind();
        g.glDeleteTextures(1, singleTexBuffer);
    }
    
}
