package pl.wurmonline.deedplanner.forms.bridges;

import javax.swing.JPanel;

public abstract class BridgesPanel extends JPanel {
    
    private final BridgesConstructor constructor;
    
    public BridgesPanel(BridgesConstructor constructor) {
        this.constructor = constructor;
    }
    
    protected BridgesConstructor getConstructor() {
        return constructor;
    }
    
    protected abstract void awake();
    
}
