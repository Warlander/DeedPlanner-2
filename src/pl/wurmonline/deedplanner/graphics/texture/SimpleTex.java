package pl.wurmonline.deedplanner.graphics.texture;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2GL3;
import javax.media.opengl.GLException;
import javax.media.opengl.GLProfile;
import org.w3c.dom.Element;
import pl.wurmonline.deedplanner.util.DeedPlannerRuntimeException;
import pl.wurmonline.deedplanner.util.Log;
import pl.wurmonline.deedplanner.util.jogl.GLInit;

public final class SimpleTex implements Tex {
    
    private static final ExecutorService backgroundTextureLoader;
    
    private static final ArrayList<SimpleTex> textures = new ArrayList<>();
    private static SimpleTex[] activeTex = new SimpleTex[3];
    private static boolean lastTextureFlippedVertically = false;
    
    private final File file;
    private Texture texture;
    private TextureData textureData;
    
    static {
        backgroundTextureLoader = Executors.newSingleThreadExecutor();
    }
    
    public static SimpleTex getTexture(Element element) {
        String path = element.getAttribute("location");
        return getTexture(path);
    }
    
    public static SimpleTex getTexture(String path) {
        return getTexture(new File(path));
    }
    
    public static SimpleTex getTexture(File path) {
        for (SimpleTex tex : textures) {
            if (tex.file.equals(path)) {
                return tex;
            }
        }
        SimpleTex texture = new SimpleTex(path);
        textures.add(texture);
        return texture;
    }
    
    public static void resetInfo() {
        for (int i=0; i<activeTex.length; i++) {
            activeTex[i] = null;
        }
    }
    
    public static void destroyAll(GL g) {
        textures.forEach((SimpleTex t) -> {t.destroy(g);});
    }
    
    private SimpleTex(File file) {
        this.file = file;
        Log.out(this, "Texture data starts loading!");
        
        backgroundTextureLoader.submit(() -> {
            try {
                textureData = TextureIO.newTextureData(GLInit.getProfile(), file, true, null);
                Log.out(this, "Texture data loaded!");
            } catch (IOException ex) {
                Log.err(ex);
            }
        });
    }
    
    private SimpleTex(String file) {
        this(new File(file));
    }
    
    public void bind(GL2 g) {
        bind(g, 0);
    }
    
    public void bind(GL2 g, int target) {
        final int glTarget;
        switch (target) {
            case 0:
                glTarget = GL2.GL_TEXTURE0;
                break;
            case 1:
                glTarget = GL2.GL_TEXTURE1;
                break;
            case 2:
                glTarget = GL2.GL_TEXTURE2;
                break;
            default:
                throw new DeedPlannerRuntimeException("Invalid texture unit!");
        }
        if (this != activeTex[target]) {
            init(g);
            
            g.glActiveTexture(glTarget);
            if (texture == null) {
                g.glBindTexture(GL2.GL_TEXTURE_2D, 0);
                activeTex[target] = null;
            }
            else {
                boolean flipTexture = lastTextureFlippedVertically != texture.getMustFlipVertically();
                lastTextureFlippedVertically = texture.getMustFlipVertically();
                if (flipTexture) {
                    g.glMatrixMode(GL2.GL_TEXTURE);
                    g.glLoadIdentity();
                    if (texture.getMustFlipVertically()) {
                        g.glScalef(1.0f, -1.0f, 1.0f);
                    }
                    g.glMatrixMode(GL2.GL_MODELVIEW);
                }
                
                texture.bind(g);
                activeTex[target] = this;
            }
        }
    }
    
    public void destroy(GL g) {
        if (texture==null) {
            return;
        }
        
        texture.destroy(g);
        Log.out(this, "Texture removed from memory!");
        texture = null;
    }
    
    private void init(GL g) {
        if (texture != null || textureData == null) {
            return;
        }
        
        try {
            texture = TextureIO.newTexture(g, textureData);
            if (file.getName().endsWith(".dds")) {
                texture.setMustFlipVertically(true);
            }
            texture.setTexParameteri(g, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
            texture.setTexParameteri(g, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
            texture.setTexParameteri(g, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
            texture.setTexParameteri(g, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR_MIPMAP_NEAREST);
            Log.out(this, "Texture loaded and ready to use!");
            
            textureData = null;
        } catch (GLException ex) {
            Log.err(ex);
        }
    }
    
    /**
     * Empty overridable method - not needed in this case due to simple texture constant nature.
     */
    public void update(GL2GL3 g) {
        
    }
    
    public int getId(GL2GL3 g) {
        init(g);
        return texture.getTextureObject();
    }
    
    public int getWidth(GL g) {
        init(g);
        return texture.getWidth();
    }
    
    public int getHeight(GL g) {
        init(g);
        return texture.getHeight();
    }
    
    public File getFile() {
        return file;
    }
    
    public String toString() {
        return file.getName();
    }

    public int getType() {
        return GL2GL3.GL_TEXTURE_2D;
    }
    
}
