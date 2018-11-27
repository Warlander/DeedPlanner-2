package pl.wurmonline.deedplanner.graphics.engine;

import java.util.LinkedList;
import java.util.ListIterator;
import com.jogamp.opengl.GL2;
import org.joml.Matrix4f;
import pl.wurmonline.deedplanner.graphics.shaders.Program;
import pl.wurmonline.deedplanner.graphics.texture.Tex;
import pl.wurmonline.deedplanner.util.DeedPlannerRuntimeException;

public class RenderEngine {
    
    private Program currentProgram;
    private Tex[] currentTextures;
    
    private final LinkedList<RenderObject> renderObjects;
    
    private final float[] matrixArray;
    
    public RenderEngine() {
        currentTextures = new Tex[3];
        renderObjects = new LinkedList();
        
        matrixArray = new float[16];
    }
    
    public void register(Transform transform, Renderable renderable) {
        if (renderable.isDestroying()) {
            return;
        }
        
        RenderObject renderObject = new RenderObject(transform, renderable);
        renderObjects.add(renderObject);
    }
    
    public void poll(GL2 g) {
        ListIterator<RenderObject> iterator = renderObjects.listIterator();
        while (iterator.hasNext()) {
            RenderObject obj = iterator.next();
            if (obj.getRenderable().isDestroying()) {
                obj.getRenderable().destroy(g);
                iterator.remove();
            }
        }
        
        renderObjects.sort((RenderObject obj0, RenderObject obj1) -> {
            int firstShaderId = obj0.getRenderable().getProgram().createOrGetProgram(g);
            int secondShaderId = obj1.getRenderable().getProgram().createOrGetProgram(g);
            
            if (firstShaderId != secondShaderId) {
                return secondShaderId - firstShaderId;
            }
            
            int firstTexId = getFirstTexIdOrZero(g, obj0.getRenderable().getTextures());
            int secondTexId = getFirstTexIdOrZero(g, obj1.getRenderable().getTextures());
            
            return secondTexId - firstTexId;
        });
        
        iterator = renderObjects.listIterator();
        while (iterator.hasNext()) {
            RenderObject obj = iterator.next();
            Program program = obj.getRenderable().getProgram();
            Tex[] textures = obj.getRenderable().getTextures();
            
            if (program != currentProgram) {
                currentProgram = program;
                program.bind(g);
            }
            
            for (int i = 0; i < currentTextures.length; i++) {
                Tex texture = getTexOrNull(textures, i);
                if (currentTextures[i] != texture) {
                    currentTextures[i] = texture;
                    
                    g.glActiveTexture(getGLTextureTarget(i));
                    if (texture == null) {
                        g.glBindTexture(GL2.GL_TEXTURE_2D, 0);
                    }
                    else {
                        int texId = texture.getId(g);
                        g.glBindTexture(GL2.GL_TEXTURE_2D, texId);
                    }
                }
            }
            
            int listId = obj.getRenderable().getOrCreateDisplayListID(g);
            
            Transform currentTransform = obj.getTransform();
            
            g.glPushMatrix();
            while (currentTransform != null) {
                g.glTranslatef(currentTransform.getLocalTranslation().x, currentTransform.getLocalTranslation().y, currentTransform.getLocalTranslation().z);
                g.glRotatef(1, currentTransform.getLocalRotation().x, currentTransform.getLocalRotation().y, currentTransform.getLocalRotation().z);
                g.glScalef(currentTransform.getLocalScale().x, currentTransform.getLocalScale().y, currentTransform.getLocalScale().z);
                if (currentTransform.getLocalCustomTransform() != null) {
                    Matrix4f matrix = currentTransform.getLocalCustomTransform();
                    matrix.get(matrixArray);
                    g.glMultMatrixf(matrixArray, 0);
                }
                
                currentTransform = currentTransform.getParent();
            }
            
            g.glCallList(listId);
            g.glPopMatrix();
        }
    }
    
    private int getFirstTexIdOrZero(GL2 g, Tex[] textures) {
        if (textures == null || textures.length == 0) {
            return 0;
        }
        
        return textures[0].getId(g);
    }
    
    private Tex getTexOrNull(Tex[] textures, int index) {
        if (textures == null || textures.length <= index) {
            return null;
        }
        
        return textures[index];
    }
    
    private int getGLTextureTarget(int target) {
        switch (target) {
            case 0:
                return GL2.GL_TEXTURE0;
            case 1:
                return GL2.GL_TEXTURE1;
            case 2:
                return GL2.GL_TEXTURE2;
            default:
                throw new DeedPlannerRuntimeException("Invalid texture unit!");
        }
    }
    
}