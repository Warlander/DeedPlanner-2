package pl.wurmonline.deedplanner.forms.bridges;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import pl.wurmonline.deedplanner.data.Map;
import pl.wurmonline.deedplanner.data.Tile;
import pl.wurmonline.deedplanner.util.SwingUtils;

public class BridgesConstructor extends JFrame {
    
    private final Map map;
    private final Tile startTile;
    private final Tile endTile;
    
    private final JPanel subpanelPanel;
    private int currentPanelIndex;
    private final BridgesPanel[] panels;
    
    private final Font defaultFont;
    private final JPanel buttonsPanel;
    
    private final JButton nextButton;
    private final JButton previousButton;
    private final JButton cancelButton;
    
    private BridgeListItem bridgeSpecs;
    
    public BridgesConstructor(Map map, Tile startTile, Tile endTile) {
        this.map = map;
        this.startTile = startTile;
        this.endTile = endTile;
        
        setTitle("Bridges Constructor");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        JPanel rootPanel = new JPanel();
        
        defaultFont = new Font("Arial", Font.PLAIN, 12);
        buttonsPanel = new JPanel();
        
        cancelButton = addButton(new JButton("Cancel"), (evt) -> dispose());
        
        previousButton = addButton(new JButton("Back"), (evt) -> setPreviousPanel());
        previousButton.setEnabled(false);
        
        nextButton = addButton(new JButton("Next"), (evt) -> setNextPanel());
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
        setPanel(panels[0]);
        
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
    
    private void setNextPanel() {
        currentPanelIndex++;
        if (currentPanelIndex != panels.length) {
            panels[currentPanelIndex].awake();
            setPanel(panels[currentPanelIndex]);
            nextButton.setEnabled(false);
        }
        else {
            //finalize bridge
        }
        
        if (currentPanelIndex == panels.length - 1) {
            nextButton.setText("Finish");
        }
        else {
            nextButton.setText("Next");
        }
    }
    
    private void setPreviousPanel() {
        currentPanelIndex--;
        if (currentPanelIndex != -1) {
            panels[currentPanelIndex].awake();
            setPanel(panels[currentPanelIndex]);
        }
        else {
            currentPanelIndex++;
        }
        
        previousButton.setEnabled(currentPanelIndex != 0);
    }
    
    private void setPanel(BridgesPanel panel) {
        subpanelPanel.removeAll();
        subpanelPanel.add(panel, BorderLayout.CENTER);
        pack();
    }
    
}
