package pl.wurmonline.deedplanner.logic;

import java.util.*;
import pl.wurmonline.deedplanner.*;
import pl.wurmonline.deedplanner.Properties;
import pl.wurmonline.deedplanner.data.Tile;
import pl.wurmonline.deedplanner.forms.Planner;
import pl.wurmonline.deedplanner.input.*;
import pl.wurmonline.deedplanner.logic.floors.FloorUpdater;
import pl.wurmonline.deedplanner.logic.ground.GroundUpdater;
import pl.wurmonline.deedplanner.logic.height.HeightUpdater;
import pl.wurmonline.deedplanner.logic.roofs.RoofUpdater;
import pl.wurmonline.deedplanner.logic.walls.WallUpdater;

public class LogicLoop extends TimerTask {

    private Planner planner;
    private final MapPanel panel;
    
    private final Keyboard keyboard;
    private final Keybindings keybindings;
    private final Mouse mouse;
    
    private final Timer timer;
    private int timerFPS;
    
    private boolean runFlag = true;
    private boolean stopped = false;
    
    public LogicLoop(MapPanel panel) {
        this.panel = panel;
        
        keyboard = new Keyboard(panel);
        keybindings = new Keybindings(keyboard);
        mouse = new Mouse(panel);
        
        timer = new Timer();
    }
    
    public void start(Planner planner) {
        this.planner = planner;
        
        timerFPS = Properties.logicFps;
        timer.scheduleAtFixedRate(this, 0, 1000/Properties.logicFps);
    }
    
    public void run() {
        if (runFlag) {
            stopped = false;
            if (mouse.hold.left) {
                panel.requestFocus();
            }
            mouse.update();
            keyboard.update();

            if (Globals.upCamera) {
                panel.getUpCamera().update(mouse, keybindings);
                if (panel.getUpCamera().tile!=null) {
                    if (Globals.tab==Tab.ground) {
                        GroundUpdater.update(mouse, panel.getMap(), panel.getUpCamera());
                    }
                    else if (Globals.tab==Tab.height) {
                        HeightUpdater.update(mouse, panel.getMap(), panel.getUpCamera());
                    }
                    else if (Globals.tab==Tab.floors) {
                        FloorUpdater.update(mouse, panel.getMap(), panel.getUpCamera());
                    }
                    else if (Globals.tab==Tab.walls) {
                        WallUpdater.update(mouse, panel.getMap(), panel.getUpCamera());
                    }
                    else if (Globals.tab==Tab.roofs) {
                        RoofUpdater.update(mouse, panel.getMap(), panel.getUpCamera());
                    }
                }
                if (panel.getUpCamera().tile!=null) {
                    Tile t = panel.getUpCamera().tile;
                    TileFragment frag = TileFragment.calculateTileFragment(panel.getUpCamera().xTile, panel.getUpCamera().yTile);
                    if (frag.isCorner()) {
                        planner.tileLabel.setText("Height: "+frag.getTileByCorner(t).getHeight()+"     X: "+t.getX()+" Y: "+t.getY());
                        planner.heightShow.setUpCamera(panel.getUpCamera());
                    }
                    else {
                        planner.tileLabel.setText("X: "+t.getX()+" Y: "+t.getY());
                        planner.heightShow.setUpCamera(null);
                    }
                }
            }
            else {
                panel.getFPPCamera().update(mouse, keybindings);
            }

            if (timerFPS!=Properties.logicFps) {
                timer.cancel();
                timer.scheduleAtFixedRate(this, 0, 1000/Properties.logicFps);
            }
            stopped = true;
        }
        else {
            stopped = true;
        }
    }
    
    public void setRunFlag(boolean runFlag) {
        this.runFlag = runFlag;
    }
    
    public boolean isStopped() {
        return stopped;
    }
    
}
