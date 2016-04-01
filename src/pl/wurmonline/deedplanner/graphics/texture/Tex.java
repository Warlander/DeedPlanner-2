package pl.wurmonline.deedplanner.graphics.texture;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLException;
import pl.wurmonline.deedplanner.util.DeedPlannerRuntimeException;
import pl.wurmonline.deedplanner.util.Log;

public final class Tex {
    
    private static final ArrayList<Tex> textures = new ArrayList<>();
    private static Tex[] activeTex = new Tex[3];
    
    private final File file;
    private Texture texture = null;
    private boolean loaded = false;
    
    public static Tex getTexture(String path) {
        return getTexture(new File(path));
    }
    
    public static Tex getTexture(File path) {
        for (Tex tex : textures) {
            if (tex.file.equals(path)) {
                return tex;
            }
        }
        Tex texture = new Tex(path);
        textures.add(texture);
        return texture;
    }
    
    public static void resetInfo() {
        for (int i=0; i<activeTex.length; i++) {
            activeTex[i] = null;
        }
    }
    
    public static void destroyAll(GL g) {
        textures.forEach((Tex t) -> {t.destroy(g);});
    }
    
    private Tex(File file) {
        this.file = file;
        Log.out(this, "Texture data loaded!");
    }
    
    private Tex(String file) {
        this(new File(file));
    }
    
    public void bind(GL g) {
        bind(g, 0);
    }
    
    public void bind(GL g, int target) {
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
        if (this!=activeTex[target]) {
            init(g);
            g.glActiveTexture(glTarget);
            if (texture != null) {
                texture.bind(g);
                activeTex[target] = this;
            }
            else {
                g.glBindTexture(GL2.GL_TEXTURE_2D, target);
                activeTex[target] = null;
            }
        }
    }
    
    public void destroy(GL g) {
        if (texture!=null) {
            texture.destroy(g);
            Log.out(this, "Texture removed from memory!");
            texture = null;
        }
    }
    
    private void init(GL g) {
        if (!loaded) {
            loaded = true;
            try {
                texture = TextureIO.newTexture(file, true);
                texture.setTexParameteri(g, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
                texture.setTexParameteri(g, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
                Log.out(this, "Texture loaded and ready to use!");
            } catch (GLException | IOException ex) {
                Log.err(ex);
            }
        }
    }
    
    public File getFile() {
        return file;
    }
    
    public String toString() {
        return file.getName();
    }
    
}
