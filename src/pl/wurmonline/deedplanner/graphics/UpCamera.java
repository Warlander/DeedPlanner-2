package pl.wurmonline.deedplanner.graphics;

import javax.media.opengl.GL2;
import pl.wurmonline.deedplanner.*;
import pl.wurmonline.deedplanner.data.Map;
import pl.wurmonline.deedplanner.data.Tile;
import pl.wurmonline.deedplanner.input.*;

public final class UpCamera {
    
    public float x=0;
    public float y=0;
    
    public Tile tile;
    public float xTile;
    public float yTile;
    public float xMap;
    public float yMap;
    
    private final MapPanel panel;
    
    public UpCamera(MapPanel panel) {
        this.panel = panel;
    }
    
    public void update(Mouse mouse, Keybindings keybindings) {
        Map map = panel.getMap();
        
        int currX = (int)(x/4);
        int currZ = (int)(y/4);
        Globals.visibleDownX = currX-5;
        Globals.visibleUpX = currX+Properties.scale*2;
        Globals.visibleDownY = currZ-5;
        Globals.visibleUpY = currZ+Properties.scale*2;
        
        float tileScaler = (float)panel.getWidth()/(float)panel.getHeight();
        float tileSize = (float)panel.getHeight()/Properties.scale/4;
        float tileX = ((mouse.x+x*tileSize)/((float)panel.getWidth()/Properties.scale/tileScaler));
        float tileY = (((panel.getHeight()-mouse.y)+y*tileSize)/((float)panel.getHeight()/Properties.scale));
        
        double keyboardFraction = Properties.keyboardFractionUp;
        if (keybindings.hold(Keybindings.UP_SPEED_MOD1)) {
            keyboardFraction*=Properties.mod1Fpp;
        }
        else if (keybindings.hold(Keybindings.UP_SPEED_MOD2)) {
            keyboardFraction*=Properties.mod2Fpp;
        }
        
        currX = (int) tileX;
        int currY = (int) tileY;
        
        if (currX>=0 && currX<map.getWidth() && currY>=0 && currY<map.getHeight()) {
            tile = map.getTile(currX, currY);
            xMap = tileX;
            yMap = tileY;
            xTile = tileX%1f;
            yTile = tileY%1f;
        }
        else {
            tile = null;
        }
        
        if (keybindings.pressed(Keybindings.UP_SCALE_MORE)) {
            if (Properties.scale>5) {
                Properties.scale--;
            }
        }
        else if (keybindings.pressed(Keybindings.UP_SCALE_LESS)) {
            if (Properties.scale<40) {
                Properties.scale++;
            }
        }
        
        if (mouse.scrollDown) {
            if (Properties.scale>5) {
                Properties.scale--;
            }
        }
        else if (mouse.scrollUp) {
            if (Properties.scale<40) {
                Properties.scale++;
            }
        }
        else {
            if ((mouse.scrollUp ^ mouse.scrollDown) && !(keybindings.pressed(Keybindings.UP_SPEED_MOD1) || keybindings.pressed(Keybindings.UP_SPEED_MOD2))) {
//                JScrollPane pane = (JScrollPane)HouseCalc.contextPane.getSelectedComponent();
//                JList list = (JList)((JViewport)pane.getComponent(0)).getView();
//                if (mouse.scrollUp) {
//                    list.setSelectedIndex(list.getSelectedIndex()-1);
//                }
//                else if (mouse.scrollDown) {
//                    list.setSelectedIndex(list.getSelectedIndex()+1);
//                }
            }
        }
        if (keybindings.pressed(Keybindings.UP_ELEVATION_DOWN)) {
            //Mapper.floorDown();
        }
        else if (keybindings.pressed(Keybindings.UP_ELEVATION_UP)) {
            //Mapper.floorUp();
        }
        
        if (mouse.pressed.middle) {
            mouse.setGrabbed(true);
        }
        else if (mouse.hold.middle) {
            x-=mouse.diffX*Properties.mouseFractionUp;
            y+=mouse.diffY*Properties.mouseFractionUp;
        }
        else if (mouse.released.middle && !mouse.hold.middle) {
            mouse.setGrabbed(false);
        }
        
        if (keybindings.hold(Keybindings.UP_MOVE_UP)) {
            y+=keyboardFraction;
        }
        if (keybindings.hold(Keybindings.UP_MOVE_DOWN)) {
            y-=keyboardFraction;
        }
        
        if (keybindings.hold(Keybindings.UP_MOVE_RIGHT)) {
            x+=keyboardFraction;
        }
        if (keybindings.hold(Keybindings.UP_MOVE_LEFT)) {
            x-=keyboardFraction;
        }
        
        if (y<0) {
            y=0;
        }
        if (x<0) {
            x=0;
        }
        
        if (y>4*(map.getHeight()-Properties.scale)) {
            y=4*(map.getHeight()-Properties.scale);
        }
        if (x>4*(map.getWidth()-Properties.scale)) {
            x=4*(map.getWidth()-Properties.scale);
        }
    }
    
    public void set(GL2 g) {
        g.glMatrixMode(GL2.GL_PROJECTION);
        g.glLoadIdentity();
        g.glOrtho(0, Properties.scale*4f*(float)panel.getWidth()/(float)panel.getHeight(), 0, Properties.scale*4, 0.001f, 600);
        g.glTranslatef(-x, -y, -200);
        g.glMatrixMode(GL2.GL_MODELVIEW);
        g.glLoadIdentity();
    }
    
    public void reset() {
        x=0;
        y=0;
    }
    
}
