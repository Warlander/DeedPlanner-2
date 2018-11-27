package pl.wurmonline.deedplanner.graphics.engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform {
    
    public static final Vector3f DEFAULT_TRANSLATION = new Vector3f();
    public static final Vector3f DEFAULT_ROTATION = new Vector3f();
    public static final Vector3f DEFAULT_SCALE = new Vector3f(1);
    
    private final Transform parent;
    private final Vector3f localTranslation;
    private final Vector3f localRotation;
    private final Vector3f localScale;
    private final Matrix4f localCustomTransform;
    
    public Transform(Vector3f translation) {
        this(null, translation, DEFAULT_ROTATION, DEFAULT_SCALE, null);
    }
    
    public Transform(Transform parent, Vector3f translation) {
        this(parent, translation, DEFAULT_ROTATION, DEFAULT_SCALE, null);
    }
    
    public Transform(Vector3f translation, Vector3f rotation, Vector3f scale) {
        this(null, translation, rotation, scale, null);
    }
    
    public Transform(Transform parent, Vector3f translation, Vector3f rotation, Vector3f scale) {
        this(parent, translation, rotation, scale, null);
    }
    
    public Transform(Transform parent, Vector3f translation, Vector3f rotation, Vector3f scale, Matrix4f customTransform) {
        this.parent = parent;
        this.localTranslation = translation;
        this.localRotation = rotation;
        this.localScale = scale;
        this.localCustomTransform = customTransform;
    }
    
    public Transform getParent() {
        return parent;
    }
    
    public Vector3f getLocalTranslation() {
        return localTranslation;
    }
    
    public Vector3f getLocalRotation() {
        return localRotation;
    }
    
    public Vector3f getLocalScale() {
        return localScale;
    }
    
    public Matrix4f getLocalCustomTransform() {
        return localCustomTransform;
    }
    
}
