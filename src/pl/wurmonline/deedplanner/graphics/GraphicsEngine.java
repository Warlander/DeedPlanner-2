package pl.wurmonline.deedplanner.graphics;

public class GraphicsEngine {
    
    private static final GraphicsEngine INSTANCE;
    
    static {
        INSTANCE = new GraphicsEngine();
    }
    
    public static GraphicsEngine getInstance() {
        return INSTANCE;
    }
    
    
    
    private GraphicsEngine() {
        
    }
    
    public void register(GraphicsEntity entity) {
        
    }
    
    public void unregister(GraphicsEntity entity) {
        
    }
    
}
