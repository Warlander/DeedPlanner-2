package pl.wurmonline.deedplanner.graphics.texture;

import com.jogamp.opengl.util.GLBuffers;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import com.jogamp.opengl.GL2GL3;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLProfile;
import org.apache.commons.io.FilenameUtils;
import pl.wurmonline.deedplanner.util.Log;

public class ArrayTex<T> implements Tex {
    
    private int textureId = -1;
    
    private final int width;
    private final int height;
    private final int depth;
    
    private final Map<T, Integer> subtextures;
    private final Map<Integer, String> subtexturesLocations;
    private int subtexturesCount = 0;
    private int lastUpdate = 0;
    
    public ArrayTex(int width, int height, int depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        
        subtextures = new HashMap<>();
        subtexturesLocations = new HashMap<>();
    }
    
    public void update(GL2GL3 g) {
        if (textureId == -1) {
            g.glEnable(GL2GL3.GL_TEXTURE_2D_ARRAY);
            IntBuffer singleTexBuffer = GLBuffers.newDirectIntBuffer(1);

            g.glGenTextures(1, singleTexBuffer);
            textureId = singleTexBuffer.get();
            g.glBindTexture(GL2GL3.GL_TEXTURE_2D_ARRAY, textureId);
            g.glTexStorage3D(GL2GL3.GL_TEXTURE_2D_ARRAY, 255, GL2GL3.GL_RGBA8, width, height, depth);
        }
        
        if (lastUpdate == subtexturesCount) {
            return;
        }
        
        g.glBindTexture(GL2GL3.GL_TEXTURE_2D_ARRAY, textureId);
        
        for (int i = lastUpdate; i < subtexturesCount; i++) {
            try {
                String texturePath = subtexturesLocations.get(i);
                TextureData data = TextureIO.newTextureData(GLProfile.getDefault(), new File(texturePath), true, FilenameUtils.getExtension(texturePath));
                
                if (data.getWidth() != width || data.getHeight() != height) {
                    throw new IllegalArgumentException("Textures width/height does not match.");
                }
                
                g.glTexSubImage3D(GL2GL3.GL_TEXTURE_2D_ARRAY, 0, 0, 0, 0, width, height, subtexturesCount, GL2GL3.GL_RGBA, GL2GL3.GL_UNSIGNED_BYTE, data.getBuffer());
                g.glGenerateMipmap(GL2GL3.GL_TEXTURE_2D_ARRAY);
                
                if (g.glGetError() != GL2GL3.GL_NO_ERROR) {
                    throw new GLException("Failed subtexture loading");
                }
            } catch (IOException ex) {
                Log.err(ex);
            }
        }
        
        lastUpdate = subtexturesCount;
    }
    
    public void addSubtexture(T key, String texturePath) {
        if (subtexturesCount >= depth) {
            throw new IllegalArgumentException("Array texture is at max capacity: cannot add more subtextures.");
        }
        
        subtextures.put(key, subtexturesCount);
        subtexturesLocations.put(subtexturesCount, texturePath);
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
    
    public void destroy(GL2GL3 g) {
        IntBuffer singleTexBuffer = GLBuffers.newDirectIntBuffer(1);
        singleTexBuffer.put(textureId);
        singleTexBuffer.rewind();
        g.glDeleteTextures(1, singleTexBuffer);
    }

    public int getId(GL2GL3 g) {
        return textureId;
    }

    public int getType() {
        return GL2GL3.GL_TEXTURE_2D_ARRAY;
    }
    
}
