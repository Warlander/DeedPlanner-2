package pl.wurmonline.deedplanner.forms;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import pl.wurmonline.deedplanner.*;
import pl.wurmonline.deedplanner.data.*;
import pl.wurmonline.deedplanner.data.storage.Data;
import pl.wurmonline.deedplanner.graphics.CameraType;
import pl.wurmonline.deedplanner.graphics.texture.SimpleTex;
import pl.wurmonline.deedplanner.logic.*;
import pl.wurmonline.deedplanner.logic.borders.BorderUpdater;
import pl.wurmonline.deedplanner.logic.caves.CaveUpdater;
import pl.wurmonline.deedplanner.logic.floors.*;
import pl.wurmonline.deedplanner.logic.ground.*;
import pl.wurmonline.deedplanner.logic.height.*;
import pl.wurmonline.deedplanner.logic.objects.ObjectsUpdater;
import pl.wurmonline.deedplanner.logic.roofs.RoofUpdater;
import pl.wurmonline.deedplanner.logic.walls.WallUpdater;
import pl.wurmonline.deedplanner.util.*;

public class Planner extends javax.swing.JFrame {
    
    public Planner() {
        initComponents();
        try {
            ArrayList<Image> images = new ArrayList();
            images.add(ImageIO.read(Planner.class.getResourceAsStream("logoL.jpg")));
            images.add(ImageIO.read(Planner.class.getResourceAsStream("logoM.png")));
            images.add(ImageIO.read(Planner.class.getResourceAsStream("logoS.png")));
            setIconImages(images);
        } catch (IOException ex) {
            Log.err(ex);
        }
        setTitle(Constants.TITLE_STRING);
        SwingUtils.centerFrame(this);
        mapPanel.grabFocus();
        setVisible(true);
        
        Analytics.getInstance().screenView().screenName(Globals.tab.toString()).sessionControl("start").postAsync();
        
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
                        setIcon(data.createIcon());
                    }
                    else {
                        setIcon(null);
                    }
                }
                return this;
            }
        });
        
        wallsTree.setCellRenderer(new DefaultTreeCellRenderer() {
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,boolean leaf, int row, boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, selected,expanded, leaf, row, hasFocus);
                DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) value;
                if (tree.getModel().getRoot().equals(nodo)) {
                    setIcon(null);
                } else if (nodo.getChildCount() > 0) {
                    setIcon(null);
                } else {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                    if (node.getUserObject() instanceof WallData) {
                        WallData data = (WallData) node.getUserObject();
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
        
        objectsSearchBox.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                applyObjectsTreeFilter();
            }

            public void removeUpdate(DocumentEvent e) {
                applyObjectsTreeFilter();
            }

            public void changedUpdate(DocumentEvent e) {
                applyObjectsTreeFilter();
            }
        });
        
        floorsTree.setCellRenderer(defaultRenderer);
        objectsTree.setCellRenderer(defaultRenderer);
        cavesTree.setCellRenderer(defaultRenderer);
        animalsTree.setCellRenderer(defaultRenderer);
        
        heightList.setModel(HeightUpdater.createListModel());
        heightList.setSelectedIndex(0);
        
        roofsList.setModel(Data.roofsList);
        roofsList.setSelectedIndex(0);
        
        bordersList.setModel(Data.bordersList);
        bordersList.setSelectedIndex(0);
        
        floorsModeCombo.setModel(FloorUpdater.createComboModel());
        floorsModeCombo.setSelectedIndex(0);
        
        groundModeCombo.setModel(GroundUpdater.createComboModel());
        groundModeCombo.setSelectedIndex(0);
        
        tabbedPane.remove(cavesPanel);
        
        mapPanel.getLoop().start(this);
        
        if (Properties.showTip) {
            TipWindow tipWindow = new TipWindow();
            tipWindow.setVisible(true);
            SwingUtilities.invokeLater(() -> tipWindow.pack());
        }
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
        heightEditGroup = new javax.swing.ButtonGroup();
        treesVisibilityGroup = new javax.swing.ButtonGroup();
        bridgesVisibilityGroup = new javax.swing.ButtonGroup();
        animalSizeGroup = new javax.swing.ButtonGroup();
        animalSexGroup = new javax.swing.ButtonGroup();
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
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lmbSelectedGroundLabel = new javax.swing.JLabel();
        rmbSelectedGroundLabel = new javax.swing.JLabel();
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
        jLabel7 = new javax.swing.JLabel();
        heightRadio = new javax.swing.JRadioButton();
        sizeRadio = new javax.swing.JRadioButton();
        floorsPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        floorsTree = new javax.swing.JTree();
        floorsModeCombo = new javax.swing.JComboBox();
        floorOrientationBox = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        wallsPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        wallsTree = new javax.swing.JTree();
        wallReversedBox = new javax.swing.JCheckBox();
        wallReversedAutoBox = new javax.swing.JCheckBox();
        roofsPanel = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        roofsList = new javax.swing.JList();
        objectsPanel = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        objectsTree = new javax.swing.JTree();
        objectsSearchBox = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        animalsPanel = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        childAnimalSizeRadio = new javax.swing.JRadioButton();
        adultAnimalSizeRadio = new javax.swing.JRadioButton();
        championAnimalSizeRadio = new javax.swing.JRadioButton();
        jLabel13 = new javax.swing.JLabel();
        maleAnimalSexRadio = new javax.swing.JRadioButton();
        unisexAnimalSexRadio = new javax.swing.JRadioButton();
        femaleAnimalSexRadio = new javax.swing.JRadioButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        animalsTree = new javax.swing.JTree();
        labelsPanel = new pl.wurmonline.deedplanner.forms.LabelEditor();
        bordersPanel = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        bordersList = new javax.swing.JList();
        cavesPanel = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        cavesTree = new javax.swing.JTree();
        symmetryPanel = new pl.wurmonline.deedplanner.forms.SymmetryEditor();
        bridgesPanel = new BridgesEditor(this);
        jLabel8 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        newMapItem = new javax.swing.JMenuItem();
        resizeItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        saveItem = new javax.swing.JMenuItem();
        loadItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        exitItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        undoItem = new javax.swing.JMenuItem();
        redoItem = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenu5 = new javax.swing.JMenu();
        objectsRemoveItem = new javax.swing.JMenuItem();
        treesRemoveItem = new javax.swing.JMenuItem();
        bushesRemoveItem = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        wallsFencesRemoveItem = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        bordersRemoveItem = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        upViewItem = new javax.swing.JRadioButtonMenuItem();
        isoViewItem = new javax.swing.JRadioButtonMenuItem();
        fppViewItem = new javax.swing.JRadioButtonMenuItem();
        wurmianItem = new javax.swing.JRadioButtonMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenu3 = new javax.swing.JMenu();
        treesAllItem = new javax.swing.JRadioButtonMenuItem();
        trees3dItem = new javax.swing.JRadioButtonMenuItem();
        treesNothingItem = new javax.swing.JRadioButtonMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jMenu10 = new javax.swing.JMenu();
        elevationOnItem = new javax.swing.JRadioButtonMenuItem();
        elevationOffItem = new javax.swing.JRadioButtonMenuItem();
        jMenu4 = new javax.swing.JMenu();
        bridgesAllItem = new javax.swing.JRadioButtonMenuItem();
        bridges3dItem = new javax.swing.JRadioButtonMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();

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
            .addGap(0, 713, Short.MAX_VALUE)
        );

        tileLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tileLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle"); // NOI18N
        tileLabel.setText(bundle.getString("Planner.tileLabel.text")); // NOI18N

        javax.swing.GroupLayout statusBarLayout = new javax.swing.GroupLayout(statusBar);
        statusBar.setLayout(statusBarLayout);
        statusBarLayout.setHorizontalGroup(
            statusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tileLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
        );
        statusBarLayout.setVerticalGroup(
            statusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusBarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(tileLabel))
        );

        jSpinner4.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jSpinner4.setModel(new javax.swing.SpinnerNumberModel(1, -7, 17, 1));
        jSpinner4.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinner4StateChanged(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        jLabel5.setText(bundle.getString("Planner.jLabel5.text")); // NOI18N

        tabbedPane.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
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
        groundsTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                groundsTreeMousePressed(evt);
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

        jLabel9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel9.setText(bundle.getString("Planner.jLabel9.text")); // NOI18N

        jLabel10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel10.setText(bundle.getString("Planner.jLabel10.text")); // NOI18N

        lmbSelectedGroundLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lmbSelectedGroundLabel.setText(bundle.getString("Planner.lmbSelectedGroundLabel.text")); // NOI18N

        rmbSelectedGroundLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        rmbSelectedGroundLabel.setText(bundle.getString("Planner.rmbSelectedGroundLabel.text")); // NOI18N

        javax.swing.GroupLayout groundPanelLayout = new javax.swing.GroupLayout(groundPanel);
        groundPanel.setLayout(groundPanelLayout);
        groundPanelLayout.setHorizontalGroup(
            groundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(groundPanelLayout.createSequentialGroup()
                .addComponent(diagonalPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(groundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(groundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lmbSelectedGroundLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                    .addComponent(rmbSelectedGroundLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
            .addComponent(groundModeCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        groundPanelLayout.setVerticalGroup(
            groundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(groundPanelLayout.createSequentialGroup()
                .addGroup(groundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(diagonalPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(groundPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(groundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(lmbSelectedGroundLabel))
                        .addGap(18, 18, 18)
                        .addGroup(groundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(rmbSelectedGroundLabel))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(groundModeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE))
        );

        tabbedPane.addTab(bundle.getString("Planner.groundPanel.TabConstraints.tabTitle"), groundPanel); // NOI18N

        jLabel1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel1.setText(bundle.getString("Planner.jLabel1.text")); // NOI18N

        heightLeftSpinner.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        heightLeftSpinner.setModel(new javax.swing.SpinnerNumberModel(5, null, null, 1));
        heightLeftSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                heightLeftSpinnerStateChanged(evt);
            }
        });

        heightRightSpinner.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        heightRightSpinner.setModel(new javax.swing.SpinnerNumberModel(-5, null, null, 1));
        heightRightSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                heightRightSpinnerStateChanged(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText(bundle.getString("Planner.jLabel2.text")); // NOI18N

        addHeightSpinner.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        addHeightSpinner.setModel(new javax.swing.SpinnerNumberModel(1, null, null, 1));
        addHeightSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                addHeightSpinnerStateChanged(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText(bundle.getString("Planner.jLabel3.text")); // NOI18N

        jLabel4.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText(bundle.getString("Planner.jLabel4.text")); // NOI18N

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

        jLabel7.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText(bundle.getString("Planner.jLabel7.text")); // NOI18N

        heightEditGroup.add(heightRadio);
        heightRadio.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        heightRadio.setSelected(true);
        heightRadio.setText(bundle.getString("Planner.heightRadio.text")); // NOI18N
        heightRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                heightRadioActionPerformed(evt);
            }
        });

        heightEditGroup.add(sizeRadio);
        sizeRadio.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        sizeRadio.setText(bundle.getString("Planner.sizeRadio.text")); // NOI18N
        sizeRadio.setEnabled(false);
        sizeRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sizeRadioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout heightPanelLayout = new javax.swing.GroupLayout(heightPanel);
        heightPanel.setLayout(heightPanelLayout);
        heightPanelLayout.setHorizontalGroup(
            heightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(heightPanelLayout.createSequentialGroup()
                .addGroup(heightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 163, Short.MAX_VALUE)
                        .addComponent(heightShow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(heightPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(heightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 341, Short.MAX_VALUE)
                            .addGroup(heightPanelLayout.createSequentialGroup()
                                .addComponent(heightRadio)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sizeRadio)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(heightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(heightRadio)
                    .addComponent(sizeRadio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
        );

        tabbedPane.addTab(bundle.getString("Planner.heightPanel.TabConstraints.tabTitle"), heightPanel); // NOI18N

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

        floorOrientationBox.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        floorOrientationBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Up", "Left", "Down", "Right" }));
        floorOrientationBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                floorOrientationBoxActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel6.setText(bundle.getString("Planner.jLabel6.text")); // NOI18N

        javax.swing.GroupLayout floorsPanelLayout = new javax.swing.GroupLayout(floorsPanel);
        floorsPanel.setLayout(floorsPanelLayout);
        floorsPanelLayout.setHorizontalGroup(
            floorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
            .addComponent(floorsModeCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, floorsPanelLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(floorOrientationBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        floorsPanelLayout.setVerticalGroup(
            floorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, floorsPanelLayout.createSequentialGroup()
                .addComponent(floorsModeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(floorsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(floorOrientationBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE))
        );

        tabbedPane.addTab(bundle.getString("Planner.floorsPanel.TabConstraints.tabTitle"), floorsPanel); // NOI18N

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

        wallReversedBox.setText(bundle.getString("Planner.wallReversedBox.text")); // NOI18N
        wallReversedBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wallReversedBoxActionPerformed(evt);
            }
        });

        wallReversedAutoBox.setSelected(true);
        wallReversedAutoBox.setText(bundle.getString("Planner.wallReversedAutoBox.text")); // NOI18N
        wallReversedAutoBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wallReversedAutoBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout wallsPanelLayout = new javax.swing.GroupLayout(wallsPanel);
        wallsPanel.setLayout(wallsPanelLayout);
        wallsPanelLayout.setHorizontalGroup(
            wallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4)
            .addGroup(wallsPanelLayout.createSequentialGroup()
                .addComponent(wallReversedBox)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(wallReversedAutoBox, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        wallsPanelLayout.setVerticalGroup(
            wallsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, wallsPanelLayout.createSequentialGroup()
                .addComponent(wallReversedBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(wallReversedAutoBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE))
        );

        tabbedPane.addTab(bundle.getString("Planner.wallsPanel.TabConstraints.tabTitle"), wallsPanel); // NOI18N

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
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
        );
        roofsPanelLayout.setVerticalGroup(
            roofsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
        );

        tabbedPane.addTab(bundle.getString("Planner.roofsPanel.TabConstraints.tabTitle"), roofsPanel); // NOI18N

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

        objectsSearchBox.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        objectsSearchBox.setText(bundle.getString("Planner.objectsSearchBox.text")); // NOI18N

        jLabel11.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel11.setText(bundle.getString("Planner.jLabel11.text")); // NOI18N

        javax.swing.GroupLayout objectsPanelLayout = new javax.swing.GroupLayout(objectsPanel);
        objectsPanel.setLayout(objectsPanelLayout);
        objectsPanelLayout.setHorizontalGroup(
            objectsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
            .addGroup(objectsPanelLayout.createSequentialGroup()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(objectsSearchBox))
        );
        objectsPanelLayout.setVerticalGroup(
            objectsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, objectsPanelLayout.createSequentialGroup()
                .addGroup(objectsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(objectsSearchBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE))
        );

        tabbedPane.addTab(bundle.getString("Planner.objectsPanel.TabConstraints.tabTitle"), objectsPanel); // NOI18N

        jLabel12.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel12.setText(bundle.getString("Planner.jLabel12.text")); // NOI18N

        animalSizeGroup.add(childAnimalSizeRadio);
        childAnimalSizeRadio.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        childAnimalSizeRadio.setText(bundle.getString("Planner.childAnimalSizeRadio.text")); // NOI18N
        childAnimalSizeRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                animalSizeRadioActionPerformed(evt);
            }
        });

        animalSizeGroup.add(adultAnimalSizeRadio);
        adultAnimalSizeRadio.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        adultAnimalSizeRadio.setSelected(true);
        adultAnimalSizeRadio.setText(bundle.getString("Planner.adultAnimalSizeRadio.text")); // NOI18N
        adultAnimalSizeRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                animalSizeRadioActionPerformed(evt);
            }
        });

        animalSizeGroup.add(championAnimalSizeRadio);
        championAnimalSizeRadio.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        championAnimalSizeRadio.setText(bundle.getString("Planner.championAnimalSizeRadio.text")); // NOI18N
        championAnimalSizeRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                animalSizeRadioActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel13.setText(bundle.getString("Planner.jLabel13.text")); // NOI18N

        animalSexGroup.add(maleAnimalSexRadio);
        maleAnimalSexRadio.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        maleAnimalSexRadio.setText(bundle.getString("Planner.maleAnimalSexRadio.text")); // NOI18N
        maleAnimalSexRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                animalGenderRadioActionPerformed(evt);
            }
        });

        animalSexGroup.add(unisexAnimalSexRadio);
        unisexAnimalSexRadio.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        unisexAnimalSexRadio.setSelected(true);
        unisexAnimalSexRadio.setText(bundle.getString("Planner.unisexAnimalSexRadio.text")); // NOI18N
        unisexAnimalSexRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                animalGenderRadioActionPerformed(evt);
            }
        });

        animalSexGroup.add(femaleAnimalSexRadio);
        femaleAnimalSexRadio.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        femaleAnimalSexRadio.setText(bundle.getString("Planner.femaleAnimalSexRadio.text")); // NOI18N
        femaleAnimalSexRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                animalGenderRadioActionPerformed(evt);
            }
        });

        animalsTree.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        animalsTree.setModel(new DefaultTreeModel(Data.animalsTree));
        animalsTree.setRootVisible(false);
        animalsTree.setShowsRootHandles(true);
        animalsTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                animalsTreeValueChanged(evt);
            }
        });
        jScrollPane9.setViewportView(animalsTree);

        javax.swing.GroupLayout animalsPanelLayout = new javax.swing.GroupLayout(animalsPanel);
        animalsPanel.setLayout(animalsPanelLayout);
        animalsPanelLayout.setHorizontalGroup(
            animalsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(animalsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(animalsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(childAnimalSizeRadio)
                    .addComponent(adultAnimalSizeRadio)
                    .addComponent(championAnimalSizeRadio))
                .addGap(66, 66, 66)
                .addGroup(animalsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(unisexAnimalSexRadio)
                    .addComponent(jLabel13)
                    .addComponent(maleAnimalSexRadio)
                    .addComponent(femaleAnimalSexRadio))
                .addContainerGap(135, Short.MAX_VALUE))
            .addComponent(jScrollPane9)
        );
        animalsPanelLayout.setVerticalGroup(
            animalsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(animalsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(animalsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(animalsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(childAnimalSizeRadio)
                    .addComponent(unisexAnimalSexRadio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(animalsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(adultAnimalSizeRadio)
                    .addComponent(maleAnimalSexRadio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(animalsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(championAnimalSizeRadio)
                    .addComponent(femaleAnimalSexRadio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE))
        );

        tabbedPane.addTab(bundle.getString("Planner.animalsPanel.TabConstraints.tabTitle"), animalsPanel); // NOI18N
        tabbedPane.addTab(bundle.getString("Planner.labelsPanel.TabConstraints.tabTitle"), labelsPanel); // NOI18N

        bordersList.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        bordersList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        bordersList.setFixedCellHeight(20);
        bordersList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                bordersListValueChanged(evt);
            }
        });
        jScrollPane7.setViewportView(bordersList);

        javax.swing.GroupLayout bordersPanelLayout = new javax.swing.GroupLayout(bordersPanel);
        bordersPanel.setLayout(bordersPanelLayout);
        bordersPanelLayout.setHorizontalGroup(
            bordersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
        );
        bordersPanelLayout.setVerticalGroup(
            bordersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
        );

        tabbedPane.addTab(bundle.getString("Planner.bordersPanel.TabConstraints.tabTitle"), bordersPanel); // NOI18N

        cavesTree.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        cavesTree.setModel(new DefaultTreeModel(Data.cavesTree));
        cavesTree.setRootVisible(false);
        cavesTree.setShowsRootHandles(true);
        cavesTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                cavesTreeValueChanged(evt);
            }
        });
        jScrollPane8.setViewportView(cavesTree);

        javax.swing.GroupLayout cavesPanelLayout = new javax.swing.GroupLayout(cavesPanel);
        cavesPanel.setLayout(cavesPanelLayout);
        cavesPanelLayout.setHorizontalGroup(
            cavesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
        );
        cavesPanelLayout.setVerticalGroup(
            cavesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
        );

        tabbedPane.addTab(bundle.getString("Planner.cavesPanel.TabConstraints.tabTitle"), cavesPanel); // NOI18N
        tabbedPane.addTab(bundle.getString("Planner.symmetryPanel.TabConstraints.tabTitle"), symmetryPanel); // NOI18N
        tabbedPane.addTab(bundle.getString("Planner.bridgesPanel.TabConstraints.tabTitle"), bridgesPanel); // NOI18N

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/wurmonline/deedplanner/forms/btn_donate_LG.gif"))); // NOI18N
        jLabel8.setText(bundle.getString("Planner.jLabel8.text")); // NOI18N
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout sidePanelLayout = new javax.swing.GroupLayout(sidePanel);
        sidePanel.setLayout(sidePanelLayout);
        sidePanelLayout.setHorizontalGroup(
            sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSpinner4))
            .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        sidePanelLayout.setVerticalGroup(
            sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidePanelLayout.createSequentialGroup()
                .addGroup(sidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jSpinner4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addContainerGap())
        );

        tabbedPane.getAccessibleContext().setAccessibleDescription(bundle.getString("Planner.tabbedPane.AccessibleContext.accessibleDescription")); // NOI18N

        jMenu1.setText(bundle.getString("Planner.jMenu1.text")); // NOI18N

        newMapItem.setText(bundle.getString("Planner.newMapItem.text")); // NOI18N
        newMapItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMapItemActionPerformed(evt);
            }
        });
        jMenu1.add(newMapItem);

        resizeItem.setText(bundle.getString("Planner.resizeItem.text")); // NOI18N
        resizeItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resizeItemActionPerformed(evt);
            }
        });
        jMenu1.add(resizeItem);
        jMenu1.add(jSeparator3);

        saveItem.setText(bundle.getString("Planner.saveItem.text")); // NOI18N
        saveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveItemActionPerformed(evt);
            }
        });
        jMenu1.add(saveItem);

        loadItem.setText(bundle.getString("Planner.loadItem.text")); // NOI18N
        loadItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadItemActionPerformed(evt);
            }
        });
        jMenu1.add(loadItem);
        jMenu1.add(jSeparator1);

        exitItem.setText(bundle.getString("Planner.exitItem.text")); // NOI18N
        exitItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitItemActionPerformed(evt);
            }
        });
        jMenu1.add(exitItem);

        jMenuBar1.add(jMenu1);

        jMenu2.setText(bundle.getString("Planner.jMenu2.text")); // NOI18N

        undoItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        undoItem.setText(bundle.getString("Planner.undoItem.text")); // NOI18N
        undoItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoItemActionPerformed(evt);
            }
        });
        jMenu2.add(undoItem);

        redoItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        redoItem.setText(bundle.getString("Planner.redoItem.text")); // NOI18N
        redoItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redoItemActionPerformed(evt);
            }
        });
        jMenu2.add(redoItem);
        jMenu2.add(jSeparator4);

        jMenu5.setText(bundle.getString("Planner.jMenu5.text")); // NOI18N

        objectsRemoveItem.setText(bundle.getString("Planner.objectsRemoveItem.text")); // NOI18N
        objectsRemoveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAllMenuActionPerformed(evt);
            }
        });
        jMenu5.add(objectsRemoveItem);

        treesRemoveItem.setText(bundle.getString("Planner.treesRemoveItem.text")); // NOI18N
        treesRemoveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAllMenuActionPerformed(evt);
            }
        });
        jMenu5.add(treesRemoveItem);

        bushesRemoveItem.setText(bundle.getString("Planner.bushesRemoveItem.text")); // NOI18N
        bushesRemoveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAllMenuActionPerformed(evt);
            }
        });
        jMenu5.add(bushesRemoveItem);
        jMenu5.add(jSeparator5);

        wallsFencesRemoveItem.setText(bundle.getString("Planner.wallsFencesRemoveItem.text")); // NOI18N
        wallsFencesRemoveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAllMenuActionPerformed(evt);
            }
        });
        jMenu5.add(wallsFencesRemoveItem);
        jMenu5.add(jSeparator6);

        bordersRemoveItem.setText(bundle.getString("Planner.bordersRemoveItem.text")); // NOI18N
        bordersRemoveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAllMenuActionPerformed(evt);
            }
        });
        jMenu5.add(bordersRemoveItem);

        jMenu2.add(jMenu5);

        jMenuBar1.add(jMenu2);

        jMenu8.setText(bundle.getString("Planner.jMenu8.text")); // NOI18N

        viewGroup.add(upViewItem);
        upViewItem.setSelected(true);
        upViewItem.setText(bundle.getString("Planner.upViewItem.text")); // NOI18N
        upViewItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewSwitched(evt);
            }
        });
        jMenu8.add(upViewItem);

        viewGroup.add(isoViewItem);
        isoViewItem.setText(bundle.getString("Planner.isoViewItem.text")); // NOI18N
        isoViewItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewSwitched(evt);
            }
        });
        jMenu8.add(isoViewItem);

        viewGroup.add(fppViewItem);
        fppViewItem.setText(bundle.getString("Planner.fppViewItem.text")); // NOI18N
        fppViewItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewSwitched(evt);
            }
        });
        jMenu8.add(fppViewItem);

        viewGroup.add(wurmianItem);
        wurmianItem.setText(bundle.getString("Planner.wurmianItem.text")); // NOI18N
        wurmianItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewSwitched(evt);
            }
        });
        jMenu8.add(wurmianItem);
        jMenu8.add(jSeparator2);

        jMenu3.setText(bundle.getString("Planner.jMenu3.text")); // NOI18N

        treesVisibilityGroup.add(treesAllItem);
        treesAllItem.setSelected(true);
        treesAllItem.setText(bundle.getString("Planner.treesAllItem.text")); // NOI18N
        treesAllItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vegetationDisplayChanged(evt);
            }
        });
        jMenu3.add(treesAllItem);

        treesVisibilityGroup.add(trees3dItem);
        trees3dItem.setText(bundle.getString("Planner.trees3dItem.text")); // NOI18N
        trees3dItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vegetationDisplayChanged(evt);
            }
        });
        jMenu3.add(trees3dItem);

        treesVisibilityGroup.add(treesNothingItem);
        treesNothingItem.setText(bundle.getString("Planner.treesNothingItem.text")); // NOI18N
        treesNothingItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vegetationDisplayChanged(evt);
            }
        });
        jMenu3.add(treesNothingItem);

        jMenu8.add(jMenu3);

        jMenu7.setText(bundle.getString("Planner.jMenu7.text")); // NOI18N

        wallsGroup.add(jRadioButtonMenuItem1);
        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText(bundle.getString("Planner.jRadioButtonMenuItem1.text")); // NOI18N
        jMenu7.add(jRadioButtonMenuItem1);

        jMenu8.add(jMenu7);

        jMenu10.setText(bundle.getString("Planner.jMenu10.text")); // NOI18N

        elevationGroup.add(elevationOnItem);
        elevationOnItem.setText(bundle.getString("Planner.elevationOnItem.text")); // NOI18N
        elevationOnItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                elevationDisplayChanged(evt);
            }
        });
        jMenu10.add(elevationOnItem);

        elevationGroup.add(elevationOffItem);
        elevationOffItem.setSelected(true);
        elevationOffItem.setText(bundle.getString("Planner.elevationOffItem.text")); // NOI18N
        elevationOffItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                elevationDisplayChanged(evt);
            }
        });
        jMenu10.add(elevationOffItem);

        jMenu8.add(jMenu10);

        jMenu4.setText(bundle.getString("Planner.jMenu4.text")); // NOI18N

        bridgesVisibilityGroup.add(bridgesAllItem);
        bridgesAllItem.setSelected(true);
        bridgesAllItem.setText(bundle.getString("Planner.bridgesAllItem.text")); // NOI18N
        bridgesAllItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bridgesDisplayChanged(evt);
            }
        });
        jMenu4.add(bridgesAllItem);

        bridgesVisibilityGroup.add(bridges3dItem);
        bridges3dItem.setText(bundle.getString("Planner.bridges3dItem.text")); // NOI18N
        bridges3dItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bridgesDisplayChanged(evt);
            }
        });
        jMenu4.add(bridges3dItem);

        jMenu8.add(jMenu4);

        jMenuBar1.add(jMenu8);

        jMenu6.setText(bundle.getString("Planner.jMenu6.text")); // NOI18N

        jMenuItem8.setText(bundle.getString("Planner.jMenuItem8.text")); // NOI18N
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem8);

        jMenuItem9.setText(bundle.getString("Planner.jMenuItem9.text")); // NOI18N
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem9);

        jMenuBar1.add(jMenu6);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(sidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(statusBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mapPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int option = JOptionPane.showConfirmDialog(null, java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle").getString("DO YOU REALLY WANT TO CLOSE PROGRAM? ALL UNSAVED CHANGES WILL BE LOST!"), java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle").getString("CLOSE PROGRAM CONFIRMATION"), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (option==JOptionPane.OK_OPTION) {
            mapPanel.getLoop().syncAndExecute(() -> {
                Analytics.getInstance().event().sessionControl("end").post();
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

    private void viewSwitched(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewSwitched
        mapPanel.getLoop().syncAndExecute(() -> {
            CameraType cameraType;
            if (evt.getSource() == upViewItem) {
                cameraType = CameraType.TOP_VIEW;
            }
            else if (evt.getSource() == isoViewItem) {
                cameraType = CameraType.ISOMETRIC;
            }
            else {
                cameraType = CameraType.SPECTATOR;
            }
            
            mapPanel.setCamera(cameraType);
            final boolean editable = mapPanel.getCamera().isEditingCapable();
            sidePanel.setVisible(editable);
            statusBar.setVisible(editable);
            Globals.fixedHeight = (evt.getSource()==wurmianItem);
        });
    }//GEN-LAST:event_viewSwitched

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        new SettingsWindow().setVisible(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void resizeItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resizeItemActionPerformed
        new ResizeWindow(mapPanel).setVisible(true);
    }//GEN-LAST:event_resizeItemActionPerformed

    private void tabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabbedPaneStateChanged
        Component tab = tabbedPane.getSelectedComponent();
        if (tab == groundPanel) {
            Globals.tab = Tab.ground;
        }
        else if (tab == heightPanel) {
            Globals.tab = Tab.height;
        }
        else if (tab == floorsPanel) {
            Globals.tab = Tab.floors;
        }
        else if (tab == wallsPanel) {
            Globals.tab = Tab.walls;
        }
        else if (tab == roofsPanel) {
            Globals.tab = Tab.roofs;
        }
        else if (tab == bordersPanel) {
            Globals.tab = Tab.borders;
        }
        else if (tab == objectsPanel) {
            Globals.tab = Tab.objects;
        }
        else if (tab == labelsPanel) {
            Globals.tab = Tab.labels;
        }
        else if (tab == cavesPanel) {
            Globals.tab = Tab.caves;
        }
        else if (tab == symmetryPanel) {
            Globals.tab = Tab.symmetry;
        }
        else if (tab == bridgesPanel) {
            Globals.tab = Tab.bridges;
        }
        else if (tab == animalsPanel) {
            Globals.tab = Tab.animals;
        }
        
        Analytics.getInstance().screenView().screenName(Globals.tab.toString()).postAsync();
    }//GEN-LAST:event_tabbedPaneStateChanged

    private void jSpinner4StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinner4StateChanged
        mapPanel.getLoop().syncAndExecute(() -> {
            int newFloor = (int) jSpinner4.getModel().getValue() - 1;
            boolean switchedUnderground = newFloor == -1 && Globals.floor == 0;
            boolean switchedAboveground = newFloor == 0 && Globals.floor == -1;
            Globals.floor = (int)jSpinner4.getModel().getValue()-1;
            if (switchedUnderground) {
                boolean groundTabSelected = tabbedPane.getSelectedComponent() == groundPanel;
                tabbedPane.insertTab("Caves", null, cavesPanel, null, 0);
                tabbedPane.remove(groundPanel);
                sizeRadio.setEnabled(true);
                
                if (groundTabSelected) {
                    tabbedPane.setSelectedComponent(cavesPanel);
                }
            }
            else if (switchedAboveground) {
                boolean cavesTabSelected = tabbedPane.getSelectedComponent() == cavesPanel;
                tabbedPane.insertTab("Ground", null, groundPanel, null, 0);
                tabbedPane.remove(cavesPanel);
                sizeRadio.setEnabled(false);
                
                if (cavesTabSelected) {
                    tabbedPane.setSelectedComponent(groundPanel);
                }
            }
        });
    }//GEN-LAST:event_jSpinner4StateChanged

    private void heightListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_heightListValueChanged
        HeightUpdater.setCurrentMode((HeightMode) heightList.getSelectedValue());
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

    private void newMapItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMapItemActionPerformed
        int option = JOptionPane.showConfirmDialog(null, java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle").getString("DO YOU REALLY WANT TO DESTROY CURRENT MAP AND START FROM SCRATCH?"), java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle").getString("NEW MAP"), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (option==JOptionPane.OK_OPTION) {
            mapPanel.setMap(new Map(50, 50));
        }
    }//GEN-LAST:event_newMapItemActionPerformed

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
        if (node!=null && node.isLeaf() && node.getUserObject()!=ObjectsUpdater.objectsCurrentData) {
            mapPanel.getLoop().syncAndExecute(() -> {
                ObjectsUpdater.objectsCurrentData = (GameObjectData) node.getUserObject();
            });
        }
    }//GEN-LAST:event_objectsTreeValueChanged

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        mapPanel.getLoop().syncAndExecute(() -> {
            SimpleTex.destroyAll(mapPanel.getGL());
        });
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void wallReversedBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wallReversedBoxActionPerformed
        Globals.reverseWall = wallReversedBox.isSelected();
    }//GEN-LAST:event_wallReversedBoxActionPerformed

    private void bordersListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_bordersListValueChanged
        BorderUpdater.currentData = (BorderData) bordersList.getSelectedValue();
    }//GEN-LAST:event_bordersListValueChanged

    private void floorOrientationBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_floorOrientationBoxActionPerformed
        switch((String) floorOrientationBox.getSelectedItem()) {
            case "Up":
                Globals.floorOrientation = EntityOrientation.UP;
                break;
            case "Left":
                Globals.floorOrientation = EntityOrientation.LEFT;
                break;
            case "Down":
                Globals.floorOrientation = EntityOrientation.DOWN;
                break;
            case "Right":
                Globals.floorOrientation = EntityOrientation.RIGHT;
                break;
        }
    }//GEN-LAST:event_floorOrientationBoxActionPerformed

    private void wallReversedAutoBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wallReversedAutoBoxActionPerformed
        Globals.autoReverseWall = wallReversedAutoBox.isSelected();
    }//GEN-LAST:event_wallReversedAutoBoxActionPerformed

    private void cavesTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_cavesTreeValueChanged
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) cavesTree.getLastSelectedPathComponent();
        if (node!=null && node.isLeaf() && node.getUserObject() != CaveUpdater.currentData) {
            mapPanel.getLoop().syncAndExecute(() -> {
                CaveUpdater.currentData = (CaveData) node.getUserObject();
            });
        }
    }//GEN-LAST:event_cavesTreeValueChanged

    private void heightRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_heightRadioActionPerformed
        Globals.editSize = false;
    }//GEN-LAST:event_heightRadioActionPerformed

    private void sizeRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sizeRadioActionPerformed
        Globals.editSize = true;
    }//GEN-LAST:event_sizeRadioActionPerformed

    private void exitItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitItemActionPerformed
        int option = JOptionPane.showConfirmDialog(null, java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle").getString("DO YOU REALLY WANT TO CLOSE PROGRAM? ALL UNSAVED CHANGES WILL BE LOST!"), java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle").getString("CLOSE PROGRAM CONFIRMATION"), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (option==JOptionPane.OK_OPTION) {
            mapPanel.getLoop().syncAndExecute(() -> {
                System.exit(0);
            });
        }        
    }//GEN-LAST:event_exitItemActionPerformed

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        try {
            Desktop.getDesktop().browse(new URI("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=NYTYWR5MV2U68"));
        } catch (URISyntaxException | IOException ex) {
            Log.err(ex);
        }
    }//GEN-LAST:event_jLabel8MouseClicked

    private void vegetationDisplayChanged(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vegetationDisplayChanged
        if (treesAllItem.isSelected()) {
            Globals.renderTreesEditing = true;
            Globals.renderTreesSpectating = true;
        }
        else if (trees3dItem.isSelected()) {
            Globals.renderTreesEditing = false;
            Globals.renderTreesSpectating = true;
        }
        else if (treesNothingItem.isSelected()) {
            Globals.renderTreesEditing = false;
            Globals.renderTreesSpectating = false;
        }
    }//GEN-LAST:event_vegetationDisplayChanged

    private void groundsTreeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_groundsTreeMousePressed
        boolean isRightClick = SwingUtilities.isRightMouseButton(evt);
        
        if (isRightClick) {
            int selRow = groundsTree.getRowForLocation(evt.getX(), evt.getY());
            TreePath selPath = groundsTree.getPathForLocation(evt.getX(), evt.getY());
            groundsTree.setSelectionPath(selPath); 
            if (selRow > -1) {
               groundsTree.setSelectionRow(selRow); 
            }
        }
        
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) groundsTree.getLastSelectedPathComponent();
        if (node != null && node.isLeaf()) {
            GroundData selectedData = (GroundData) node.getUserObject();
            if (!isRightClick && node.getUserObject() != GroundUpdater.lmbData) {
                lmbSelectedGroundLabel.setText(selectedData.name);
                mapPanel.getLoop().syncAndExecute(() -> {
                    GroundUpdater.lmbData = selectedData;
                });
            }
            else if (isRightClick && node.getUserObject() != GroundUpdater.rmbData) {
                rmbSelectedGroundLabel.setText(selectedData.name);
                mapPanel.getLoop().syncAndExecute(() -> {
                    GroundUpdater.rmbData = selectedData;
                });
            }
        }
    }//GEN-LAST:event_groundsTreeMousePressed

    private void bridgesDisplayChanged(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bridgesDisplayChanged
        Globals.renderBridgesEditing = bridgesAllItem.isSelected();
    }//GEN-LAST:event_bridgesDisplayChanged

    private void animalsTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_animalsTreeValueChanged
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) animalsTree.getLastSelectedPathComponent();
        if (node!=null && node.isLeaf() && node.getUserObject() != ObjectsUpdater.animalsCurrentData) {
            mapPanel.getLoop().syncAndExecute(() -> {
                ObjectsUpdater.animalsCurrentData = (AnimalData) node.getUserObject();
            });
        }
    }//GEN-LAST:event_animalsTreeValueChanged

    private void animalSizeRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_animalSizeRadioActionPerformed
        if (childAnimalSizeRadio.isSelected()) {
            Globals.animalAge = AnimalAge.CHILD;
        }
        else if (adultAnimalSizeRadio.isSelected()) {
            Globals.animalAge = AnimalAge.ADULT;
        }
        else if (championAnimalSizeRadio.isSelected()) {
            Globals.animalAge = AnimalAge.CHAMPION;
        }
    }//GEN-LAST:event_animalSizeRadioActionPerformed

    private void animalGenderRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_animalGenderRadioActionPerformed
        if (unisexAnimalSexRadio.isSelected()) {
            Globals.animalGender = AnimalGender.UNISEX;
        }
        else if (maleAnimalSexRadio.isSelected()) {
            Globals.animalGender = AnimalGender.MALE;
        }
        else if (femaleAnimalSexRadio.isSelected()) {
            Globals.animalGender = AnimalGender.FEMALE;
        }
    }//GEN-LAST:event_animalGenderRadioActionPerformed

    private void removeAllMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeAllMenuActionPerformed
        Object source = evt.getSource();
        mapPanel.getLoop().syncAndExecute(() -> {
            Map map = mapPanel.getMap();
            for (int i = 0; i <= map.getWidth(); i++) {
                for (int i2 = 0; i2 <= map.getHeight(); i2++) {
                    Tile tile = map.getTile(i, i2);
                    
                    for (int h = Constants.CAVE_LIMIT; h < Constants.FLOORS_LIMIT; h++) {
                        if (source == objectsRemoveItem) {
                            tile.setGameObject(null, null, h);
                        }
                        else if (source == treesRemoveItem) {
                            for (ObjectLocation loc : ObjectLocation.getValues()) {
                                GameObject obj = tile.getGameObject(loc, h);
                                if (obj != null && obj.getData().type.equals("tree")) {
                                    tile.setGameObject(null, loc, h);
                                }
                            }
                        }
                        else if (source == bushesRemoveItem) {
                            for (ObjectLocation loc : ObjectLocation.getValues()) {
                                GameObject obj = tile.getGameObject(loc, h);
                                if (obj != null && obj.getData().type.equals("bush")) {
                                    tile.setGameObject(null, loc, h);
                                }
                            }
                        }
                        else if (source == wallsFencesRemoveItem) {
                            tile.setHorizontalWall(null, h);
                            tile.setVerticalWall(null, h);
                        }
                        else if (source == bordersRemoveItem) {
                            tile.setHorizontalBorder(null);
                            tile.setVerticalBorder(null);
                        }
                    }
                }
            }
            
            map.newAction();
        });
    }//GEN-LAST:event_removeAllMenuActionPerformed
    
    private void applyObjectsTreeFilter() {
        String toFind = objectsSearchBox.getText();
        
        if (toFind.equals("")) {
            objectsTree.setModel(new DefaultTreeModel(Data.objectsTree));
        }
        
        ArrayList<GameObjectData> sortedObjects = new ArrayList();
        
        for (Entry<String, GameObjectData> entry : Data.objects.entrySet()) {
            GameObjectData gameObjectData = entry.getValue();
            if (gameObjectData.name.toUpperCase().contains(toFind.toUpperCase())) {
                sortedObjects.add(gameObjectData);
            }
        }
        
        Data.filteredObjectsTree = createFilteredTree(Data.objectsTree, sortedObjects);
        objectsTree.setModel(new DefaultTreeModel(Data.filteredObjectsTree));
    }
    
    private DefaultMutableTreeNode createFilteredTree(DefaultMutableTreeNode currentNode, ArrayList allowedObjects) {
        if (currentNode.isLeaf()) {
            if (allowedObjects.contains(currentNode.getUserObject())) {
                return new DefaultMutableTreeNode(currentNode.getUserObject());
            }
            else {
                return null;
            }
        }
        
        DefaultMutableTreeNode filteredNode = null;
        for (int i = 0; i < currentNode.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) currentNode.getChildAt(i);
            
            DefaultMutableTreeNode filteredChild = createFilteredTree(child, allowedObjects);
            if (filteredChild == null) {
                continue;
            }
            
            if (filteredNode == null) {
                filteredNode = new DefaultMutableTreeNode(currentNode.getUserObject());
            }
            
            filteredNode.add(filteredChild);
        }
        
        return filteredNode;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner addHeightSpinner;
    private javax.swing.JRadioButton adultAnimalSizeRadio;
    private javax.swing.ButtonGroup animalSexGroup;
    private javax.swing.ButtonGroup animalSizeGroup;
    private javax.swing.JPanel animalsPanel;
    private javax.swing.JTree animalsTree;
    private javax.swing.JList bordersList;
    private javax.swing.JPanel bordersPanel;
    private javax.swing.JMenuItem bordersRemoveItem;
    private javax.swing.JRadioButtonMenuItem bridges3dItem;
    private javax.swing.JRadioButtonMenuItem bridgesAllItem;
    private pl.wurmonline.deedplanner.forms.BridgesEditor bridgesPanel;
    private javax.swing.ButtonGroup bridgesVisibilityGroup;
    private javax.swing.JMenuItem bushesRemoveItem;
    private javax.swing.JPanel cavesPanel;
    private javax.swing.JTree cavesTree;
    private javax.swing.JRadioButton championAnimalSizeRadio;
    private javax.swing.JRadioButton childAnimalSizeRadio;
    private pl.wurmonline.deedplanner.forms.DiagonalPanel diagonalPanel1;
    private javax.swing.ButtonGroup elevationGroup;
    private javax.swing.JRadioButtonMenuItem elevationOffItem;
    private javax.swing.JRadioButtonMenuItem elevationOnItem;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JRadioButton femaleAnimalSexRadio;
    private javax.swing.JComboBox floorOrientationBox;
    private javax.swing.JComboBox floorsModeCombo;
    private javax.swing.JPanel floorsPanel;
    private javax.swing.JTree floorsTree;
    private javax.swing.JRadioButtonMenuItem fppViewItem;
    private javax.swing.JComboBox groundModeCombo;
    private javax.swing.JPanel groundPanel;
    private javax.swing.JTree groundsTree;
    private javax.swing.ButtonGroup heightEditGroup;
    private javax.swing.JSpinner heightLeftSpinner;
    private javax.swing.JList heightList;
    private javax.swing.JPanel heightPanel;
    private javax.swing.JRadioButton heightRadio;
    private javax.swing.JSpinner heightRightSpinner;
    public pl.wurmonline.deedplanner.forms.HeightShow heightShow;
    private javax.swing.JRadioButtonMenuItem isoViewItem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JSpinner jSpinner4;
    private pl.wurmonline.deedplanner.forms.LabelEditor labelsPanel;
    private javax.swing.JLabel lmbSelectedGroundLabel;
    private javax.swing.JMenuItem loadItem;
    private javax.swing.JRadioButton maleAnimalSexRadio;
    private pl.wurmonline.deedplanner.MapPanel mapPanel;
    private javax.swing.JMenuItem newMapItem;
    private javax.swing.JPanel objectsPanel;
    private javax.swing.JMenuItem objectsRemoveItem;
    private javax.swing.JTextField objectsSearchBox;
    private javax.swing.JTree objectsTree;
    private javax.swing.JMenuItem redoItem;
    private javax.swing.JMenuItem resizeItem;
    private javax.swing.JLabel rmbSelectedGroundLabel;
    private javax.swing.JList roofsList;
    private javax.swing.JPanel roofsPanel;
    private javax.swing.JMenuItem saveItem;
    private javax.swing.ButtonGroup seasonGroup;
    private javax.swing.JPanel sidePanel;
    private javax.swing.JRadioButton sizeRadio;
    private javax.swing.JPanel statusBar;
    private pl.wurmonline.deedplanner.forms.SymmetryEditor symmetryPanel;
    private javax.swing.JTabbedPane tabbedPane;
    public javax.swing.JLabel tileLabel;
    private javax.swing.JRadioButtonMenuItem trees3dItem;
    private javax.swing.JRadioButtonMenuItem treesAllItem;
    private javax.swing.JRadioButtonMenuItem treesNothingItem;
    private javax.swing.JMenuItem treesRemoveItem;
    private javax.swing.ButtonGroup treesVisibilityGroup;
    private javax.swing.JMenuItem undoItem;
    private javax.swing.JRadioButton unisexAnimalSexRadio;
    private javax.swing.JRadioButtonMenuItem upViewItem;
    private javax.swing.ButtonGroup viewGroup;
    private javax.swing.JCheckBox wallReversedAutoBox;
    private javax.swing.JCheckBox wallReversedBox;
    private javax.swing.JMenuItem wallsFencesRemoveItem;
    private javax.swing.ButtonGroup wallsGroup;
    private javax.swing.JPanel wallsPanel;
    private javax.swing.JTree wallsTree;
    private javax.swing.JRadioButtonMenuItem wurmianItem;
    // End of variables declaration//GEN-END:variables
}
