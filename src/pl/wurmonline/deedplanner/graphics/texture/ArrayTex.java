package pl.wurmonline.deedplanner.graphics.texture;

import com.jogamp.opengl.util.GLBuffers;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import javax.media.opengl.GL3;
import javax.media.opengl.GLException;
import javax.media.opengl.GLProfile;
import org.apache.commons.io.FilenameUtils;

public class ArrayTex<T> {
    
    private final int textureId;
    
    private final int width;
    private final int height;
    private final int depth;
    
    private final Map<T, Integer> subtextures;
    private int subtexturesCount = 0;
    
    public ArrayTex(GL3 g, int width, int height, int depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        
        g.glEnable(GL3.GL_TEXTURE_2D_ARRAY);
        IntBuffer singleTexBuffer = GLBuffers.newDirectIntBuffer(1);
        
        g.glGenTextures(1, singleTexBuffer);
        textureId = singleTexBuffer.get();
        g.glBindTexture(GL3.GL_TEXTURE_2D_ARRAY, textureId);
        g.glTexStorage3D(GL3.GL_TEXTURE_2D_ARRAY, 255, GL3.GL_RGBA8, width, height, depth);
        
        subtextures = new HashMap<>();
    }
    
    public void addSubtexture(GL3 g, T key, String texturePath) throws IOException {
        TextureData data = TextureIO.newTextureData(GLProfile.getDefault(), new File(texturePath), true, FilenameUtils.getExtension(texturePath));
        
        if (data.getWidth() != width || data.getHeight() != height) {
            throw new IllegalArgumentException("Textures width/height does not match.");
        }
        
        if (subtexturesCount >= depth) {
            throw new IllegalArgumentException("Array texture is at max capacity: cannot add more subtextures.");
        }
        
        g.glTexSubImage3D(GL3.GL_TEXTURE_2D_ARRAY, 0, 0, 0, 0, width, height, subtexturesCount, GL3.GL_RGBA, GL3.GL_UNSIGNED_BYTE, data.getBuffer());
        g.glGenerateMipmap(GL3.GL_TEXTURE_2D_ARRAY);
        
        if (g.glGetError() != GL3.GL_NO_ERROR) {
            throw new GLException("Failed subtexture loading");
        }
        
        subtextures.put(key, subtexturesCount);
        subtexturesCount++;
    }
    
    public int getSubtextureIndex(T key) {
        return subtextures.get(key);
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getDepth() {
        return depth;
    }
    
    public int getSubtexturesCount() {
        return subtexturesCount;
    }
    
    public void destroy(GL3 g) {
        IntBuffer singleTexBuffer = GLBuffers.newDirectIntBuffer(1);
        singleTexBuffer.put(textureId);
        singleTexBuffer.rewind();
        g.glDeleteTextures(1, singleTexBuffer);
    }
    
}
