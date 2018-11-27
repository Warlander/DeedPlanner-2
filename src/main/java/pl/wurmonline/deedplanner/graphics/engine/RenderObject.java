package pl.wurmonline.deedplanner.graphics.engine;

final class RenderObject {

    private final Transform transform;
    private final Renderable renderable;
    
    RenderObject(Transform transform, Renderable renderable) {
        this.transform = transform;
        this.renderable = renderable;
    }
    
    public Transform getTransform() {
        return transform;
    }
    
    public Renderable getRenderable() {
        return renderable;
    }
    
}
