package pl.wurmonline.deedplanner;

import javax.swing.SwingUtilities;
import pl.wurmonline.deedplanner.forms.Planner;
import pl.wurmonline.deedplanner.graphics.GraphicsLoop;
import pl.wurmonline.deedplanner.logic.LogicLoop;
import pl.wurmonline.deedplanner.util.Log;

public class MainLoop {
    
    private final LogicLoop logic;
    private final GraphicsLoop graphics;
    
    private boolean started = false;
    
    public MainLoop(MapPanel panel) {
        logic = new LogicLoop(panel);
        graphics = new GraphicsLoop(panel);
    }
    
    public void start(Planner planner) {
        if (!started) {
            started = true;
            logic.start(planner);
            graphics.start();
        }
    }
    
    public void syncAndExecute(Runnable run) {
        logic.setRunFlag(false);
        graphics.setRunFlag(false);
        SwingUtilities.invokeLater(() -> {
            while (true) {
                if (logic.isStopped() && graphics.isStopped()) {
                    run.run();
                    logic.setRunFlag(true);
                    graphics.setRunFlag(true);
                    return;
                }
                else {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        Log.err(ex);
                    }
                }
            }
        });
        
    }
    
}
