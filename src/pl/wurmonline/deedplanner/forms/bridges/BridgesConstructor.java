package pl.wurmonline.deedplanner.forms.bridges;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import pl.wurmonline.deedplanner.data.Map;
import pl.wurmonline.deedplanner.data.Tile;
import pl.wurmonline.deedplanner.data.bridges.Bridge;
import pl.wurmonline.deedplanner.data.bridges.BridgePartType;
import pl.wurmonline.deedplanner.util.SwingUtils;

public class BridgesConstructor extends JFrame {
    
    private final Map map;
    private final Tile startTile;
    private final Tile endTile;
    private final int startFloor;
    private final int endFloor;
    
    private final JPanel subpanelPanel;
    private int currentPanelIndex = -1;
    private final BridgesPanel[] panels;
    
    private final Font defaultFont;
    private final JPanel buttonsPanel;
    
    private final JButton nextButton;
    private final JButton previousButton;
    private final JButton cancelButton;
    
    private BridgeListItem bridgeSpecs;
    private BridgePartType[] segments;
    private int additionalData;
    
    public BridgesConstructor(Map map, Tile startTile, Tile endTile, int startFloor, int endFloor) {
        this.map = map;
        this.startTile = startTile;
        this.endTile = endTile;
        this.startFloor = startFloor;
        this.endFloor = endFloor;
        
        setTitle("Bridges Constructor");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        JPanel rootPanel = new JPanel();
        
        defaultFont = new Font("Arial", Font.PLAIN, 12);
        buttonsPanel = new JPanel();
        
        cancelButton = addButton(new JButton("Cancel"), (evt) -> dispose());
        
        previousButton = addButton(new JButton("Back"), (evt) -> setPanel(-1));
        previousButton.setEnabled(false);
        
        nextButton = addButton(new JButton("Next"), (evt) -> setPanel(1));
        nextButton.setEnabled(false);
        
        subpanelPanel = new JPanel();
        
        rootPanel.setLayout(new BorderLayout());
        rootPanel.add(buttonsPanel, BorderLayout.PAGE_END);
        rootPanel.add(subpanelPanel, BorderLayout.CENTER);
        setContentPane(rootPanel);
        
        panels = new BridgesPanel[] {
            new BridgesTypePanel(this),
            new BridgesStructurePanel(this)
        };
        setPanel(1);
        
        SwingUtils.centerFrame(this);
        setVisible(true);
    }
    
    private JButton addButton(JButton button, ActionListener action) {
        button.setFont(defaultFont);
        buttonsPanel.add(button);
        button.addActionListener(action);
        return button;
    }
    
    protected Map getMap() {
        return map;
    }
    
    protected Tile getStartTile() {
        return startTile;
    }
    
    protected Tile getEndTile() {
        return endTile;
    }
    
    protected void setCanAdvance(boolean allow) {
        nextButton.setEnabled(allow);
    }
    
    void setBridgeSpecs(BridgeListItem specs) {
        this.bridgeSpecs = specs;
    }
    
    BridgeListItem getBridgeSpecs() {
        return bridgeSpecs;
    }
    
    void setSegments(BridgePartType[] segments) {
        this.segments = segments;
    }
    
    void setAdditionalData(int additionalData) {
        this.additionalData = additionalData;
    }
    
    private void setPanel(int direction) {
        currentPanelIndex += direction;
        
        if (currentPanelIndex >= 0 && currentPanelIndex < panels.length) {
            nextButton.setEnabled(false);
            panels[currentPanelIndex].awake();
            setPanel(panels[currentPanelIndex]);
        }
        else if (currentPanelIndex == panels.length) {
            Bridge.createBridge(map, startTile, endTile, startFloor, endFloor, bridgeSpecs.data, bridgeSpecs.type, segments, additionalData);
            dispose();
            return;
        }
        else {
            currentPanelIndex -= direction;
            return;
        }
        
        if (currentPanelIndex == panels.length - 1) {
            nextButton.setText("Finish");
        }
        else {
            nextButton.setText("Next");
        }
        
        previousButton.setEnabled(currentPanelIndex != 0);
    }

    private void setPanel(BridgesPanel panel) {
        subpanelPanel.removeAll();
        subpanelPanel.add(panel, BorderLayout.CENTER);
        pack();
    }
    
}
