package pl.wurmonline.deedplanner.forms;

import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.tree.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import pl.wurmonline.deedplanner.*;
import pl.wurmonline.deedplanner.data.*;
import pl.wurmonline.deedplanner.data.storage.Data;
import pl.wurmonline.deedplanner.forms.tools.*;
import pl.wurmonline.deedplanner.graphics.texture.*;
import pl.wurmonline.deedplanner.logic.*;
import pl.wurmonline.deedplanner.logic.floors.*;
import pl.wurmonline.deedplanner.logic.ground.*;
import pl.wurmonline.deedplanner.logic.height.*;
import pl.wurmonline.deedplanner.logic.objects.ObjectsUpdater;
import pl.wurmonline.deedplanner.logic.roofs.RoofUpdater;
import pl.wurmonline.deedplanner.logic.walls.WallUpdater;
import pl.wurmonline.deedplanner.util.*;
import pl.wurmonline.deedplanner.util.jogl.Tex;

public class Planner extends javax.swing.JFrame {
    
    public Planner() {
        initComponents();
        try {
            setIconImage(ImageIO.read(Planner.class.getResourceAsStream("SimpleLogo.png")));
        } catch (IOException ex) {
            Log.err(ex);
        }
        setTitle(Constants.VERSION_STRING);
        SwingUtils.centerFrame(this);
        mapPanel.grabFocus();
        setVisible(true);
        
        groundsTree.setCellRenderer(new DefaultTreeCellRenderer() {
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,boolean leaf, int row, boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, selected,expanded, leaf, row, hasFocus);
                DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) value;
                if (tree.getModel().getRoot().equals(nodo)) {
                    setIcon(null);
                } else if (nodo.getChildCount() > 0) {
                    setIcon(null);
                } else {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                    if (node.getUserObject() instanceof GroundData) {
                        GroundData data = (GroundData) node.getUserObject();
                        setIcon(data.getIcon());
                    }
                    else {
                        setIcon(null);
                    }
                }
                return this;
            }
        });
        
        TreeCellRenderer defaultRenderer = new DefaultTreeCellRenderer() {
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,boolean leaf, int row, boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, selected,expanded, leaf, row, hasFocus);
                DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) value;
                if (tree.getModel().getRoot().equals(nodo)) {
                    setIcon(null);
                } else if (nodo.getChildCount() > 0) {
                    setIcon(null);
                } else {
                    setIcon(null);
                }
                return this;
            }
        };
        
        floorsTree.setCellRenderer(defaultRenderer);
        wallsTree.setCellRenderer(defaultRenderer);
        objectsTree.setCellRenderer(defaultRenderer);
        
        heightList.setModel(HeightUpdater.createListModel());
        heightList.setSelectedIndex(0);
        
        roofsList.setModel(Data.roofsList);
        roofsList.setSelectedIndex(0);
        
        floorsModeCombo.setModel(FloorUpdater.createComboModel());
        floorsModeCombo.setSelectedIndex(0);
        
        groundModeCombo.setModel(GroundUpdater.createComboModel());
        groundModeCombo.setSelectedIndex(0);
        
        mapPanel.getLoop().start(this);
    }
    
    public MapPanel getMapPanel() {
        return mapPanel;
    }
    
    public JTree getGroundsTree() {
        return groundsTree;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        viewGroup = new javax.swing.ButtonGroup();
        seasonGroup = new javax.swing.ButtonGroup();
        wallsGroup = new javax.swing.ButtonGroup();
        elevationGroup = new javax.swing.ButtonGroup();
        mapPanel = new pl.wurmonline.deedplanner.MapPanel();
        statusBar = new javax.swing.JPanel();
        tileLabel = new javax.swing.JLabel();
        sidePanel = new javax.swing.JPanel();
        jSpinner4 = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        tabbedPane = new javax.swing.JTabbedPane();
        groundPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        groundsTree = new javax.swing.JTree();
        diagonalPanel1 = new DiagonalPanel(this);
        groundModeCombo = new javax.swing.JComboBox();
        heightPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        heightLeftSpinner = new javax.swing.JSpinner();
        HeightUpdater.setLMB = heightLeftSpinner;
        heightRightSpinner = new javax.swing.JSpinner();
        HeightUpdater.setRMB = heightRightSpinner;
        jLabel2 = new javax.swing.JLabel();
        addHeightSpinner = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        heightList = new javax.swing.JList();
        heightShow = new pl.wurmonline.deedplanner.forms.HeightShow();
        floorsPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        floorsTree = new javax.swing.JTree();
        floorsModeCombo = new javax.swing.JComboBox();
        wallsPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        wallsTree = new javax.swing.JTree();
        roofsPanel = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        roofsList = new javax.swing.JList();
        objectsPanel = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        objectsTree = new javax.swing.JTree();
        labelsPanel = new pl.wurmonline.deedplanner.forms.LabelEditor();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        resizeItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        saveItem = new javax.swing.JMenuItem();
        loadItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        undoItem = new javax.swing.JMenuItem();
        redoItem = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        upViewItem = new javax.swing.JRadioButtonMenuItem();
        fppViewItem = new javax.swing.JRadioButtonMenuItem();
        wurmianItem = new javax.swing.JRadioButtonMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenu7 = new javax.swing.JMenu();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jMenu10 = new javax.swing.JMenu();
        elevationOnItem = new javax.swing.JRadioButtonMenuItem();
        elevationOffItem = new javax.swing.JRadioButtonMenuItem();
        jMenu11 = new javax.swing.JMenu();
        springItem = new javax.swing.JRadioButtonMenuItem();
        summerItem = new javax.swing.JRadioButtonMenuItem();
        fallItem = new javax.swing.JRadioButtonMenuItem();
        winterItem = new javax.swing.JRadioButtonMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenu9 = new javax.swing.JMenu();
        deedCalculatorItem = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        javax.swing.GroupLayout mapPanelLayout = new javax.swing.GroupLayout(mapPanel);
        mapPanel.setLayout(mapPanelLayout);
        mapPanelLayout.setHorizontalGroup(
            mapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        mapPanelLayout.setVerticalGroup(
            mapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        tileLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tileLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        tileLabel.setText("Height: 0     X: 0 Y: 0");

        javax.swing.GroupLayout statusBarLayout = new javax.swing.GroupLayout(statusBar);
        statusBar.setLayout(statusBarLayout);
        statusBarLayout.setHorizontalGroup(
            statusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tileLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
        );
        statusBarLayout.setVerticalGroup(
            statusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusBarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(tileLabel))
        );

        jSpinner4.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jSpinner4.setModel(new javax.swing.SpinnerNumberModel(1, 1, 17, 1));
        jSpinner4.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner4StateChanged(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel5.setText("Floor");

        tabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabbedPaneStateChanged(evt);
            }
        });

        groundsTree.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        groundsTree.setModel(new DefaultTreeModel(Data.groundsTree));
        groundsTree.setCellRenderer(null);
        groundsTree.setRootVisible(false);
        groundsTree.setShowsRootHandles(true);
        groundsTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                groundsTreeValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(groundsTree);

        javax.swing.GroupLayout diagonalPanel1Layout = new javax.swing.GroupLayout(diagonalPanel1);
        diagonalPanel1.setLayout(diagonalPanel1Layout);
        diagonalPanel1Layout.setHorizontalGroup(
            diagonalPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 130, Short.MAX_VALUE)
        );
        diagonalPanel1Layout.setVerticalGroup(
            diagonalPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        groundModeCombo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        groundModeCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groundModeComboActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout groundPanelLayout = new javax.swing.GroupLayout(groundPanel);
        groundPanel.setLayout(groundPanelLayout);
        groundPanelLayout.setHorizontalGroup(
            groundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(groundPanelLayout.createSequentialGroup()
                .addComponent(diagonalPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(groundModeCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        groundPanelLayout.setVerticalGroup(
            groundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(groundPanelLayout.createSequentialGroup()
                .addComponent(diagonalPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(groundModeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Ground", groundPanel);

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText("Set (LMB):");

        heightLeftSpinner.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        heightLeftSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(5), null, null, Integer.valueOf(1)));
        heightLeftSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                heightLeftSpinnerStateChanged(evt);
            }
        });

        heightRightSpinner.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        heightRightSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(-5), null, null, Integer.valueOf(1)));
        heightRightSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                heightRightSpinnerStateChanged(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("Set (RMB):");

        addHeightSpinner.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        addHeightSpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(1), null, null, Integer.valueOf(1)));
        addHeightSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                addHeightSpinnerStateChanged(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("Add:");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Edit mode");

        heightList.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        heightList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "just to keep interface clean" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        heightList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        heightList.setFixedCellHeight(20);
        heightList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                heightListValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(heightList);

        javax.swing.GroupLayout heightPanelLayout = new javax.swing.GroupLayout(heightPanel);
        heightPanel.setLayout(heightPanelLayout);
        heightPanelLayout.setHorizontalGroup(
            heightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
            .addComponent(jScrollPane2)
            .addGroup(heightPanelLayout.createSequentialGroup()
                .addGroup(heightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(heightPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(heightLeftSpinner))
                    .addGroup(heightPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(heightRightSpinner))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, heightPanelLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addHeightSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(heightShow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        heightPanelLayout.setVerticalGroup(
            heightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(heightPanelLayout.createSequentialGroup()
                .addGroup(heightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(heightPanelLayout.createSequentialGroup()
                        .addGroup(heightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(heightLeftSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(heightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(heightRightSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(heightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(addHeightSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(heightShow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Height", heightPanel);

        floorsTree.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        floorsTree.setModel(new DefaultTreeModel(Data.floorsTree));
        floorsTree.setRootVisible(false);
        floorsTree.setShowsRootHandles(true);
        floorsTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                floorsTreeValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(floorsTree);

        floorsModeCombo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        floorsModeCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                floorsModeComboActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout floorsPanelLayout = new javax.swing.GroupLayout(floorsPanel);
        floorsPanel.setLayout(floorsPanelLayout);
        floorsPanelLayout.setHorizontalGroup(
            floorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
            .addComponent(floorsModeCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        floorsPanelLayout.setVerticalGroup(
            floorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, floorsPanelLayout.createSequentialGroup()
                .addComponent(floorsModeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Floors", floorsPanel);

        wallsTree.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        wallsTree.setModel(new DefaultTreeModel(Data.wallsTree));
        wallsTree.setRootVisible(false);
        wallsTree.setShowsRootHandles(true);
        wallsTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                wallsTreeValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(wallsTree);

        javax.swing.GroupLayout wallsPanelLayout = new javax.swing.GroupLayout(wallsPanel);
        wallsPanel.setLayout(wallsPanelLayout);
        wallsPanelLayout.setHorizontalGroup(
            wallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
        );
        wallsPanelLayout.setVerticalGroup(
            wallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
        );

        tabbedPane.addTab("Walls", wallsPanel);

        roofsList.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        roofsList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Just to keep interface clean" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        roofsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        roofsList.setFixedCellHeight(20);
        roofsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                roofsListValueChanged(evt);
            }
        });
        jScrollPane5.setViewportView(roofsList);

        javax.swing.GroupLayout roofsPanelLayout = new javax.swing.GroupLayout(roofsPanel);
        roofsPanel.setLayout(roofsPanelLayout);
        roofsPanelLayout.setHorizontalGroup(
            roofsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
        );
        roofsPanelLayout.setVerticalGroup(
            roofsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
        );

        tabbedPane.addTab("Roofs", roofsPanel);

        objectsTree.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        objectsTree.setModel(new DefaultTreeModel(Data.objectsTree));
        objectsTree.setRootVisible(false);
        objectsTree.setShowsRootHandles(true);
        objectsTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                objectsTreeValueChanged(evt);
            }
        });
        jScrollPane6.setViewportView(objectsTree);

        javax.swing.GroupLayout objectsPanelLayout = new javax.swing.GroupLayout(objectsPanel);
        objectsPanel.setLayout(objectsPanelLayout);
        objectsPanelLayout.setHorizontalGroup(
            objectsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
        );
        objectsPanelLayout.setVerticalGroup(
            objectsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
        );

        tabbedPane.addTab("Objects", objectsPanel);
        tabbedPane.addTab("Labels and Materials", labelsPanel);

        javax.swing.GroupLayout sidePanelLayout = new javax.swing.GroupLayout(sidePanel);
        sidePanel.setLayout(sidePanelLayout);
        sidePanelLayout.setHorizontalGroup(
            sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSpinner4))
            .addComponent(tabbedPane)
        );
        sidePanelLayout.setVerticalGroup(
            sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addGroup(sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jSpinner4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedPane))
        );

        tabbedPane.getAccessibleContext().setAccessibleDescription("");

        jMenu1.setText("File");

        jMenuItem1.setText("New map");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        resizeItem.setText("Resize");
        resizeItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resizeItemActionPerformed(evt);
            }
        });
        jMenu1.add(resizeItem);
        jMenu1.add(jSeparator3);

        saveItem.setText("Save");
        saveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveItemActionPerformed(evt);
            }
        });
        jMenu1.add(saveItem);

        loadItem.setText("Load");
        loadItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadItemActionPerformed(evt);
            }
        });
        jMenu1.add(loadItem);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        undoItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        undoItem.setText("Undo");
        undoItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoItemActionPerformed(evt);
            }
        });
        jMenu2.add(undoItem);

        redoItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        redoItem.setText("Redo");
        redoItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redoItemActionPerformed(evt);
            }
        });
        jMenu2.add(redoItem);

        jMenuBar1.add(jMenu2);

        jMenu8.setText("View");

        viewGroup.add(upViewItem);
        upViewItem.setSelected(true);
        upViewItem.setText("2d view");
        upViewItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewSwitched(evt);
            }
        });
        jMenu8.add(upViewItem);

        viewGroup.add(fppViewItem);
        fppViewItem.setText("3d view");
        fppViewItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewSwitched(evt);
            }
        });
        jMenu8.add(fppViewItem);

        viewGroup.add(wurmianItem);
        wurmianItem.setText("Wurmian view");
        wurmianItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewSwitched(evt);
            }
        });
        jMenu8.add(wurmianItem);
        jMenu8.add(jSeparator2);

        jMenu7.setText("Walls");

        wallsGroup.add(jRadioButtonMenuItem1);
        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("Type");
        jMenu7.add(jRadioButtonMenuItem1);

        jMenu8.add(jMenu7);

        jMenu10.setText("Elevation");

        elevationGroup.add(elevationOnItem);
        elevationOnItem.setText("On");
        elevationOnItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                elevationDisplayChanged(evt);
            }
        });
        jMenu10.add(elevationOnItem);

        elevationGroup.add(elevationOffItem);
        elevationOffItem.setSelected(true);
        elevationOffItem.setText("Off");
        elevationOffItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                elevationDisplayChanged(evt);
            }
        });
        jMenu10.add(elevationOffItem);

        jMenu8.add(jMenu10);

        jMenu11.setText("Season");

        seasonGroup.add(springItem);
        springItem.setText("Spring");
        springItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seasonChanged(evt);
            }
        });
        jMenu11.add(springItem);

        seasonGroup.add(summerItem);
        summerItem.setSelected(true);
        summerItem.setText("Summer");
        summerItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seasonChanged(evt);
            }
        });
        jMenu11.add(summerItem);

        seasonGroup.add(fallItem);
        fallItem.setText("Fall");
        fallItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seasonChanged(evt);
            }
        });
        jMenu11.add(fallItem);

        seasonGroup.add(winterItem);
        winterItem.setText("Winter");
        winterItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seasonChanged(evt);
            }
        });
        jMenu11.add(winterItem);

        jMenu8.add(jMenu11);

        jMenuBar1.add(jMenu8);

        jMenu6.setText("Options");

        jMenuItem8.setText("Settings");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem8);

        jMenuItem9.setText("Reload textures");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem9);

        jMenuBar1.add(jMenu6);

        jMenu9.setText("Tools");

        deedCalculatorItem.setText("Deed Calculator");
        deedCalculatorItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deedCalculatorItemActionPerformed(evt);
            }
        });
        jMenu9.add(deedCalculatorItem);

        jMenuItem10.setText("Armor defence calculator");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem10);

        jMenuBar1.add(jMenu9);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(sidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mapPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(statusBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mapPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(sidePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void deedCalculatorItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deedCalculatorItemActionPerformed
        new DeedCalculator().setVisible(true);
    }//GEN-LAST:event_deedCalculatorItemActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int option = JOptionPane.showConfirmDialog(null, "Do you really want to close program? All unsaved changes will be lost!", "Close program confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (option==JOptionPane.OK_OPTION) {
            mapPanel.getLoop().syncAndExecute(() -> {
                System.exit(0);
            });
        }
    }//GEN-LAST:event_formWindowClosing

    private void undoItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoItemActionPerformed
        mapPanel.getLoop().syncAndExecute(() -> {
            mapPanel.getMap().undo();
        });
    }//GEN-LAST:event_undoItemActionPerformed

    private void redoItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redoItemActionPerformed
        mapPanel.getLoop().syncAndExecute(() -> {
            mapPanel.getMap().redo();
        });
    }//GEN-LAST:event_redoItemActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        new ArmorDefence().setVisible(true);
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void groundsTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_groundsTreeValueChanged
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) groundsTree.getLastSelectedPathComponent();
        if (node!=null && node.isLeaf() && node.getUserObject()!=GroundUpdater.currentData) {
            mapPanel.getLoop().syncAndExecute(() -> {
                GroundUpdater.currentData = (GroundData) node.getUserObject();
            });
        }
    }//GEN-LAST:event_groundsTreeValueChanged

    private void viewSwitched(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewSwitched
        mapPanel.getLoop().syncAndExecute(() -> {
            final boolean upView = (evt.getSource()==upViewItem);
            Globals.upCamera = upView;
            sidePanel.setVisible(upView);
            statusBar.setVisible(upView);
            Globals.fixedHeight = (evt.getSource()==wurmianItem);
        });
    }//GEN-LAST:event_viewSwitched

    private void seasonChanged(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seasonChanged
        mapPanel.getLoop().syncAndExecute(() -> {
            Object source = evt.getSource();
            if (source==springItem) {
                Globals.season = Season.SPRING;
            }
            else if (source==summerItem) {
                Globals.season = Season.SUMMER;
            }
            else if (source==fallItem) {
                Globals.season = Season.FALL;
            }
            else if (source==winterItem) {
                Globals.season = Season.WINTER;
            }
            Tex.destroyAll(mapPanel.getGL());
        });
    }//GEN-LAST:event_seasonChanged

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        new SettingsWindow().setVisible(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void resizeItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resizeItemActionPerformed
        new ResizeWindow(mapPanel).setVisible(true);
    }//GEN-LAST:event_resizeItemActionPerformed

    private void tabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabbedPaneStateChanged
        Component tab = tabbedPane.getSelectedComponent();
        if (tab==groundPanel) {
            Globals.tab = Tab.ground;
        }
        else if (tab==heightPanel) {
            Globals.tab = Tab.height;
        }
        else if (tab==floorsPanel) {
            Globals.tab = Tab.floors;
        }
        else if (tab==wallsPanel) {
            Globals.tab = Tab.walls;
        }
        else if (tab==roofsPanel) {
            Globals.tab = Tab.roofs;
        }
        else if (tab==objectsPanel) {
            Globals.tab = Tab.objects;
        }
        else if (tab==labelsPanel) {
            Globals.tab = Tab.labels;
        }
    }//GEN-LAST:event_tabbedPaneStateChanged

    private void jSpinner4StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner4StateChanged
        mapPanel.getLoop().syncAndExecute(() -> {
            Globals.floor = (int)jSpinner4.getModel().getValue()-1;
        });
    }//GEN-LAST:event_jSpinner4StateChanged

    private void heightListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_heightListValueChanged
        HeightUpdater.currentMode = (HeightMode) heightList.getSelectedValue();
    }//GEN-LAST:event_heightListValueChanged

    private void heightLeftSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_heightLeftSpinnerStateChanged
        HeightUpdater.setLeft = (int) heightLeftSpinner.getModel().getValue();
    }//GEN-LAST:event_heightLeftSpinnerStateChanged

    private void heightRightSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_heightRightSpinnerStateChanged
        HeightUpdater.setRight = (int) heightRightSpinner.getModel().getValue();
    }//GEN-LAST:event_heightRightSpinnerStateChanged

    private void addHeightSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_addHeightSpinnerStateChanged
        HeightUpdater.add = (int) addHeightSpinner.getModel().getValue();
    }//GEN-LAST:event_addHeightSpinnerStateChanged

    private void floorsTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_floorsTreeValueChanged
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) floorsTree.getLastSelectedPathComponent();
        if (node!=null && node.isLeaf() && node.getUserObject()!=FloorUpdater.currentData) {
            mapPanel.getLoop().syncAndExecute(() -> {
                FloorUpdater.currentData = (FloorData) node.getUserObject();
            });
        }
    }//GEN-LAST:event_floorsTreeValueChanged

    private void floorsModeComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_floorsModeComboActionPerformed
        FloorUpdater.currentMode = (FloorMode) floorsModeCombo.getModel().getSelectedItem();
    }//GEN-LAST:event_floorsModeComboActionPerformed

    private void groundModeComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groundModeComboActionPerformed
        GroundUpdater.currentMode = (GroundMode) groundModeCombo.getModel().getSelectedItem();
    }//GEN-LAST:event_groundModeComboActionPerformed

    private void wallsTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_wallsTreeValueChanged
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) wallsTree.getLastSelectedPathComponent();
        if (node!=null && node.isLeaf() && node.getUserObject()!=WallUpdater.currentData) {
            mapPanel.getLoop().syncAndExecute(() -> {
                WallUpdater.currentData = (WallData) node.getUserObject();
            });
        }
    }//GEN-LAST:event_wallsTreeValueChanged

    private void elevationDisplayChanged(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_elevationDisplayChanged
        if (evt.getSource()==elevationOnItem) {
            Globals.renderHeight = true;
        }
        else {
            Globals.renderHeight = false;
        }
    }//GEN-LAST:event_elevationDisplayChanged

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        int option = JOptionPane.showConfirmDialog(null, "Do you really want to destroy current map and start from scratch?", "New map", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (option==JOptionPane.OK_OPTION) {
            mapPanel.setMap(new Map(50, 50));
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void roofsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_roofsListValueChanged
        RoofUpdater.currentData = (RoofData) roofsList.getSelectedValue();
    }//GEN-LAST:event_roofsListValueChanged

    private void saveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveItemActionPerformed
        try {
            new SaveWindow(mapPanel.getMap().serialize()).setVisible(true);
        } catch (ParserConfigurationException | TransformerException | IOException ex) {
            Log.err(ex);
        }
    }//GEN-LAST:event_saveItemActionPerformed

    private void loadItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadItemActionPerformed
        new LoadWindow(mapPanel).setVisible(true);
    }//GEN-LAST:event_loadItemActionPerformed

    private void objectsTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_objectsTreeValueChanged
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) objectsTree.getLastSelectedPathComponent();
        if (node!=null && node.isLeaf() && node.getUserObject()!=ObjectsUpdater.currentData) {
            mapPanel.getLoop().syncAndExecute(() -> {
                ObjectsUpdater.currentData = (GameObjectData) node.getUserObject();
            });
        }
    }//GEN-LAST:event_objectsTreeValueChanged

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        mapPanel.getLoop().syncAndExecute(() -> {
            Tex.destroyAll(mapPanel.getGL());
        });
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner addHeightSpinner;
    private javax.swing.JMenuItem deedCalculatorItem;
    private pl.wurmonline.deedplanner.forms.DiagonalPanel diagonalPanel1;
    private javax.swing.ButtonGroup elevationGroup;
    private javax.swing.JRadioButtonMenuItem elevationOffItem;
    private javax.swing.JRadioButtonMenuItem elevationOnItem;
    private javax.swing.JRadioButtonMenuItem fallItem;
    private javax.swing.JComboBox floorsModeCombo;
    private javax.swing.JPanel floorsPanel;
    private javax.swing.JTree floorsTree;
    private javax.swing.JRadioButtonMenuItem fppViewItem;
    private javax.swing.JComboBox groundModeCombo;
    private javax.swing.JPanel groundPanel;
    private javax.swing.JTree groundsTree;
    private javax.swing.JSpinner heightLeftSpinner;
    private javax.swing.JList heightList;
    private javax.swing.JPanel heightPanel;
    private javax.swing.JSpinner heightRightSpinner;
    public pl.wurmonline.deedplanner.forms.HeightShow heightShow;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu11;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JSpinner jSpinner4;
    private pl.wurmonline.deedplanner.forms.LabelEditor labelsPanel;
    private javax.swing.JMenuItem loadItem;
    private pl.wurmonline.deedplanner.MapPanel mapPanel;
    private javax.swing.JPanel objectsPanel;
    private javax.swing.JTree objectsTree;
    private javax.swing.JMenuItem redoItem;
    private javax.swing.JMenuItem resizeItem;
    private javax.swing.JList roofsList;
    private javax.swing.JPanel roofsPanel;
    private javax.swing.JMenuItem saveItem;
    private javax.swing.ButtonGroup seasonGroup;
    private javax.swing.JPanel sidePanel;
    private javax.swing.JRadioButtonMenuItem springItem;
    private javax.swing.JPanel statusBar;
    private javax.swing.JRadioButtonMenuItem summerItem;
    private javax.swing.JTabbedPane tabbedPane;
    public javax.swing.JLabel tileLabel;
    private javax.swing.JMenuItem undoItem;
    private javax.swing.JRadioButtonMenuItem upViewItem;
    private javax.swing.ButtonGroup viewGroup;
    private javax.swing.ButtonGroup wallsGroup;
    private javax.swing.JPanel wallsPanel;
    private javax.swing.JTree wallsTree;
    private javax.swing.JRadioButtonMenuItem winterItem;
    private javax.swing.JRadioButtonMenuItem wurmianItem;
    // End of variables declaration//GEN-END:variables
}
