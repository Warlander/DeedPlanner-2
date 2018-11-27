package pl.wurmonline.deedplanner;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.awt.GLJPanel;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.HashMap;
import pl.wurmonline.deedplanner.data.Map;
import pl.wurmonline.deedplanner.graphics.*;
import pl.wurmonline.deedplanner.util.DeedPlannerRuntimeException;
import pl.wurmonline.deedplanner.util.jogl.GLInit;

public class MapPanel extends GLJPanel implements ComponentListener {

    private Map map;
    private Map destroyingMap;
    
    private final MainLoop loop;
    
    private final HashMap<CameraType, Camera> cameras;
    private Camera currentCamera;
    
    public MapPanel() {
        this(GLInit.getCapabilities());
    }
    
    public MapPanel(GLCapabilities capabilities) {
        super(capabilities);
        addComponentListener(this);
        
        map = new Map(50, 50);
        
        cameras = new HashMap();
        addCamera(new UpCamera());
        addCamera(new IsometricCamera());
        addCamera(new FPPCamera());
        
        loop = new MainLoop(this);
    }
    
    private void addCamera(Camera camera) {
        CameraType type = camera.getCameraType();
        if (cameras.containsKey(type)) {
            throw new DeedPlannerRuntimeException("Camera already exists for type " + type);
        }
        
        cameras.put(type, camera);
        if (currentCamera == null) {
            setCamera(type);
        }
    }
    
    public Camera getCamera() {
        return currentCamera;
    }
    
    public void setCamera(CameraType type) {
        Camera camera = cameras.get(type);
        if (camera == null) {
            throw new DeedPlannerRuntimeException("Camera not existing for type " + type);
        }
        
        currentCamera = camera;
        Globals.camera = currentCamera;
    }
    
    public MainLoop getLoop() {
        return loop;
    }
    
    public Map getMap() {
        return map;
    }
    
    public void setMap(Map map) {
        destroyingMap = map;
        loop.syncAndExecute(() -> {
            this.map = map;
        });
    }
    
    public Map getAndClearDestroyingMap() {
        Map map = destroyingMap;
        destroyingMap = null;
        return map;
    }

    public void componentResized(ComponentEvent e) {
        Globals.glWindowWidth = getWidth();
        Globals.glWindowHeight = getHeight();
        Globals.glAspectRatio = (float)getWidth()/(float)getHeight();
    }

    public void componentMoved(ComponentEvent e) {}

    public void componentShown(ComponentEvent e) {}

    public void componentHidden(ComponentEvent e) {}
    
}
