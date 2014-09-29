package pl.wurmonline.deedplanner;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLJPanel;
import pl.wurmonline.deedplanner.data.Map;
import pl.wurmonline.deedplanner.graphics.*;
import pl.wurmonline.deedplanner.util.jogl.GLInit;

public class MapPanel extends GLJPanel implements ComponentListener {

    private Map map;
    
    private final MainLoop loop;
    
    private final FPPCamera fppCamera;
    private final UpCamera upCamera;
    
    public MapPanel() {
        this(GLInit.prepareGLCapabilities());
    }
    
    public MapPanel(GLCapabilities capabilities) {
        super(capabilities);
        
        map = new Map(50, 50);
        
        fppCamera = new FPPCamera(this);
        upCamera = new UpCamera(this);
        
        loop = new MainLoop(this);
    }
    
    public MainLoop getLoop() {
        return loop;
    }
    
    public FPPCamera getFPPCamera() {
        return fppCamera;
    }
    
    public UpCamera getUpCamera() {
        return upCamera;
    }
    
    public Map getMap() {
        return map;
    }
    
    public void setMap(Map map) {
        loop.syncAndExecute(() -> {
            for (int i=0; i<map.getWidth(); i++) {
                for (int i2=0; i2<map.getHeight(); i2++) {
                    map.getTile(i, i2).getGround().destroy();
                }
            }
            this.map = map;
        });
    }

    public void componentResized(ComponentEvent e) {
        Globals.glWindowWidth = getWidth();
        Globals.glWindowHeight = getHeight();
    }

    public void componentMoved(ComponentEvent e) {}

    public void componentShown(ComponentEvent e) {}

    public void componentHidden(ComponentEvent e) {}
    
}
