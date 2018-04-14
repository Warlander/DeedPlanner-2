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
import pl.wurmonline.deedplanner.graphics.FPPCamera;
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
        
        if (Properties.leftSideInterface) {
            Container contentPanel = getContentPane();
            contentPanel.remove(sideHolderPanel);
            contentPanel.add(sideHolderPanel, BorderLayout.WEST);
            
            sideHolderPanel.remove(floorSelectionPanel);
            sideHolderPanel.add(floorSelectionPanel, BorderLayout.EAST);
        }
        
        bridgesPanel.updateState();
        
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
                        setIcon(data.getOrCreateIcon());
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
        
        lmbSelectedGroundLabel.setText(GroundUpdater.lmbData.name);
        lmbSelectedGroundLabel.setIcon(GroundUpdater.lmbData.getOrCreateIcon());
        
        rmbSelectedGroundLabel.setText(GroundUpdater.rmbData.name);
        rmbSelectedGroundLabel.setIcon(GroundUpdater.rmbData.getOrCreateIcon());
        
        setVisible(true);
        
        mapPanel.grabFocus();
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

        viewMenuGroup = new javax.swing.ButtonGroup();
        viewSidebarGroup = new javax.swing.ButtonGroup();
        seasonGroup = new javax.swing.ButtonGroup();
        wallsGroup = new javax.swing.ButtonGroup();
        elevationGroup = new javax.swing.ButtonGroup();
        heightEditGroup = new javax.swing.ButtonGroup();
        treesVisibilityGroup = new javax.swing.ButtonGroup();
        bridgesVisibilityGroup = new javax.swing.ButtonGroup();
        animalSizeGroup = new javax.swing.ButtonGroup();
        animalSexGroup = new javax.swing.ButtonGroup();
        tabGroup = new javax.swing.ButtonGroup();
        floorGroup = new javax.swing.ButtonGroup();
        cavesToggle = new javax.swing.JToggleButton();
        mapHolderPanel = new javax.swing.JPanel();
        mapPanel = new pl.wurmonline.deedplanner.MapPanel();
        statusBar = new javax.swing.JPanel();
        tileLabel = new javax.swing.JLabel();
        sideHolderPanel = new javax.swing.JPanel();
        sidePanel = new javax.swing.JPanel();
        donateLabel = new javax.swing.JLabel();
        tabIconsPanel = new javax.swing.JPanel();
        groundToggle = new javax.swing.JToggleButton();
        heightToggle = new javax.swing.JToggleButton();
        floorsToggle = new javax.swing.JToggleButton();
        wallsToggle = new javax.swing.JToggleButton();
        roofsToggle = new javax.swing.JToggleButton();
        objectsToggle = new javax.swing.JToggleButton();
        animalsToggle = new javax.swing.JToggleButton();
        labelsToggle = new javax.swing.JToggleButton();
        bordersToggle = new javax.swing.JToggleButton();
        bridgesToggle = new javax.swing.JToggleButton();
        symmetryToggle = new javax.swing.JToggleButton();
        tabPanel = new javax.swing.JPanel();
        groundPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        groundsTree = new javax.swing.JTree();
        diagonalPanel = new DiagonalPanel(this);
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
        floorSelectionPanel = new javax.swing.JPanel();
        upViewToggle = new javax.swing.JToggleButton();
        fppViewToggle = new javax.swing.JToggleButton();
        isoViewToggle = new javax.swing.JToggleButton();
        wurmianViewToggle = new javax.swing.JToggleButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 27), new java.awt.Dimension(0, 27), new java.awt.Dimension(32767, 23));
        floorToggle16 = new javax.swing.JToggleButton();
        floorToggle15 = new javax.swing.JToggleButton();
        floorToggle14 = new javax.swing.JToggleButton();
        floorToggle13 = new javax.swing.JToggleButton();
        floorToggle12 = new javax.swing.JToggleButton();
        floorToggle11 = new javax.swing.JToggleButton();
        floorToggle10 = new javax.swing.JToggleButton();
        floorToggle9 = new javax.swing.JToggleButton();
        floorToggle8 = new javax.swing.JToggleButton();
        floorToggle7 = new javax.swing.JToggleButton();
        floorToggle6 = new javax.swing.JToggleButton();
        floorToggle5 = new javax.swing.JToggleButton();
        floorToggle4 = new javax.swing.JToggleButton();
        floorToggle3 = new javax.swing.JToggleButton();
        floorToggle2 = new javax.swing.JToggleButton();
        floorToggle1 = new javax.swing.JToggleButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 27), new java.awt.Dimension(0, 27), new java.awt.Dimension(32767, 23));
        floorToggleNegative1 = new javax.swing.JToggleButton();
        floorToggleNegative2 = new javax.swing.JToggleButton();
        floorToggleNegative3 = new javax.swing.JToggleButton();
        floorToggleNegative4 = new javax.swing.JToggleButton();
        floorToggleNegative5 = new javax.swing.JToggleButton();
        floorToggleNegative6 = new javax.swing.JToggleButton();
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
        fppViewItem = new javax.swing.JRadioButtonMenuItem();
        isoViewItem = new javax.swing.JRadioButtonMenuItem();
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

        tabGroup.add(cavesToggle);
        cavesToggle.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        cavesToggle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/wurmonline/deedplanner/forms/icons/007-cave.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle"); // NOI18N
        cavesToggle.setText(bundle.getString("Planner.cavesToggle.text")); // NOI18N
        cavesToggle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        cavesToggle.setMargin(new java.awt.Insets(2, 0, 2, 0));
        cavesToggle.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        cavesToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabToggleActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 600));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        mapHolderPanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout mapPanelLayout = new javax.swing.GroupLayout(mapPanel);
        mapPanel.setLayout(mapPanelLayout);
        mapPanelLayout.setHorizontalGroup(
            mapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        mapPanelLayout.setVerticalGroup(
            mapPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 731, Short.MAX_VALUE)
        );

        mapHolderPanel.add(mapPanel, java.awt.BorderLayout.CENTER);

        tileLabel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tileLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        tileLabel.setText(bundle.getString("Planner.tileLabel.text")); // NOI18N

        javax.swing.GroupLayout statusBarLayout = new javax.swing.GroupLayout(statusBar);
        statusBar.setLayout(statusBarLayout);
        statusBarLayout.setHorizontalGroup(
            statusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tileLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
        );
        statusBarLayout.setVerticalGroup(
            statusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statusBarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(tileLabel))
        );

        mapHolderPanel.add(statusBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(mapHolderPanel, java.awt.BorderLayout.CENTER);

        sideHolderPanel.setLayout(new java.awt.BorderLayout());

        sidePanel.setLayout(new java.awt.BorderLayout());

        donateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        donateLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/wurmonline/deedplanner/forms/btn_donate_LG.gif"))); // NOI18N
        donateLabel.setText(bundle.getString("Planner.donateLabel.text")); // NOI18N
        donateLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        donateLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        donateLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                donateLabelMouseClicked(evt);
            }
        });
        sidePanel.add(donateLabel, java.awt.BorderLayout.SOUTH);

        tabIconsPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        tabIconsPanel.setLayout(new java.awt.GridLayout(2, 6, 3, 3));

        tabGroup.add(groundToggle);
        groundToggle.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        groundToggle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/wurmonline/deedplanner/forms/icons/017-flowers.png"))); // NOI18N
        groundToggle.setSelected(true);
        groundToggle.setText(bundle.getString("Planner.groundToggle.text")); // NOI18N
        groundToggle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        groundToggle.setMargin(new java.awt.Insets(2, 0, 2, 0));
        groundToggle.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        groundToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabToggleActionPerformed(evt);
            }
        });
        tabIconsPanel.add(groundToggle);

        tabGroup.add(heightToggle);
        heightToggle.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        heightToggle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/wurmonline/deedplanner/forms/icons/013-mountain.png"))); // NOI18N
        heightToggle.setText(bundle.getString("Planner.heightToggle.text")); // NOI18N
        heightToggle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        heightToggle.setMargin(new java.awt.Insets(2, 0, 2, 0));
        heightToggle.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        heightToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabToggleActionPerformed(evt);
            }
        });
        tabIconsPanel.add(heightToggle);

        tabGroup.add(floorsToggle);
        floorsToggle.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        floorsToggle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/wurmonline/deedplanner/forms/icons/011-parquet.png"))); // NOI18N
        floorsToggle.setText(bundle.getString("Planner.floorsToggle.text")); // NOI18N
        floorsToggle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        floorsToggle.setMargin(new java.awt.Insets(2, 0, 2, 0));
        floorsToggle.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        floorsToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabToggleActionPerformed(evt);
            }
        });
        tabIconsPanel.add(floorsToggle);

        tabGroup.add(wallsToggle);
        wallsToggle.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        wallsToggle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/wurmonline/deedplanner/forms/icons/015-fence.png"))); // NOI18N
        wallsToggle.setText(bundle.getString("Planner.wallsToggle.text")); // NOI18N
        wallsToggle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        wallsToggle.setMargin(new java.awt.Insets(2, 0, 2, 0));
        wallsToggle.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        wallsToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabToggleActionPerformed(evt);
            }
        });
        tabIconsPanel.add(wallsToggle);

        tabGroup.add(roofsToggle);
        roofsToggle.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        roofsToggle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/wurmonline/deedplanner/forms/icons/016-roof.png"))); // NOI18N
        roofsToggle.setText(bundle.getString("Planner.roofsToggle.text")); // NOI18N
        roofsToggle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        roofsToggle.setMargin(new java.awt.Insets(2, 0, 2, 0));
        roofsToggle.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        roofsToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabToggleActionPerformed(evt);
            }
        });
        tabIconsPanel.add(roofsToggle);

        tabGroup.add(objectsToggle);
        objectsToggle.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        objectsToggle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/wurmonline/deedplanner/forms/icons/014-armchair.png"))); // NOI18N
        objectsToggle.setText(bundle.getString("Planner.objectsToggle.text")); // NOI18N
        objectsToggle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        objectsToggle.setMargin(new java.awt.Insets(2, 0, 2, 0));
        objectsToggle.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        objectsToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabToggleActionPerformed(evt);
            }
        });
        tabIconsPanel.add(objectsToggle);

        tabGroup.add(animalsToggle);
        animalsToggle.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        animalsToggle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/wurmonline/deedplanner/forms/icons/012-bird.png"))); // NOI18N
        animalsToggle.setText(bundle.getString("Planner.animalsToggle.text")); // NOI18N
        animalsToggle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        animalsToggle.setMargin(new java.awt.Insets(2, 0, 2, 0));
        animalsToggle.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        animalsToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabToggleActionPerformed(evt);
            }
        });
        tabIconsPanel.add(animalsToggle);

        tabGroup.add(labelsToggle);
        labelsToggle.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        labelsToggle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/wurmonline/deedplanner/forms/icons/009-price-tag.png"))); // NOI18N
        labelsToggle.setText(bundle.getString("Planner.labelsToggle.text")); // NOI18N
        labelsToggle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        labelsToggle.setMargin(new java.awt.Insets(2, 0, 2, 0));
        labelsToggle.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        labelsToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabToggleActionPerformed(evt);
            }
        });
        tabIconsPanel.add(labelsToggle);

        tabGroup.add(bordersToggle);
        bordersToggle.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        bordersToggle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/wurmonline/deedplanner/forms/icons/006-broken-lines-square-border.png"))); // NOI18N
        bordersToggle.setText(bundle.getString("Planner.bordersToggle.text")); // NOI18N
        bordersToggle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bordersToggle.setMargin(new java.awt.Insets(2, 0, 2, 0));
        bordersToggle.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bordersToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabToggleActionPerformed(evt);
            }
        });
        tabIconsPanel.add(bordersToggle);

        tabGroup.add(bridgesToggle);
        bridgesToggle.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        bridgesToggle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/wurmonline/deedplanner/forms/icons/010-bridge.png"))); // NOI18N
        bridgesToggle.setText(bundle.getString("Planner.bridgesToggle.text")); // NOI18N
        bridgesToggle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bridgesToggle.setMargin(new java.awt.Insets(2, 0, 2, 0));
        bridgesToggle.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bridgesToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabToggleActionPerformed(evt);
            }
        });
        tabIconsPanel.add(bridgesToggle);

        tabGroup.add(symmetryToggle);
        symmetryToggle.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N
        symmetryToggle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/wurmonline/deedplanner/forms/icons/008-horizontal-symmetry.png"))); // NOI18N
        symmetryToggle.setText(bundle.getString("Planner.symmetryToggle.text")); // NOI18N
        symmetryToggle.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        symmetryToggle.setMargin(new java.awt.Insets(2, 0, 2, 0));
        symmetryToggle.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        symmetryToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tabToggleActionPerformed(evt);
            }
        });
        tabIconsPanel.add(symmetryToggle);

        sidePanel.add(tabIconsPanel, java.awt.BorderLayout.NORTH);

        tabPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 3, 0, 3));
        tabPanel.setLayout(new java.awt.CardLayout());

        groundPanel.setName("ground"); // NOI18N
        groundPanel.setPreferredSize(new java.awt.Dimension(320, 587));

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

        javax.swing.GroupLayout diagonalPanelLayout = new javax.swing.GroupLayout(diagonalPanel);
        diagonalPanel.setLayout(diagonalPanelLayout);
        diagonalPanelLayout.setHorizontalGroup(
            diagonalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 130, Short.MAX_VALUE)
        );
        diagonalPanelLayout.setVerticalGroup(
            diagonalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        groundModeCombo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        groundModeCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groundModeComboActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/wurmonline/deedplanner/forms/icons/004-mouse-left-button.png"))); // NOI18N
        jLabel9.setText(bundle.getString("Planner.jLabel9.text")); // NOI18N

        jLabel10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pl/wurmonline/deedplanner/forms/icons/005-mouse-right-button.png"))); // NOI18N
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
                .addComponent(diagonalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(groundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(groundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lmbSelectedGroundLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                    .addComponent(rmbSelectedGroundLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(groundModeCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        groundPanelLayout.setVerticalGroup(
            groundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(groundPanelLayout.createSequentialGroup()
                .addGroup(groundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(diagonalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(groundPanelLayout.createSequentialGroup()
                        .addGroup(groundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lmbSelectedGroundLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(groundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rmbSelectedGroundLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(groundModeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE))
        );

        tabPanel.add(groundPanel, "ground");

        heightPanel.setName("height"); // NOI18N

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                        .addComponent(heightShow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(heightPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(heightPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE))
        );

        tabPanel.add(heightPanel, "height");

        floorsPanel.setName("floors"); // NOI18N

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
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
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
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE))
        );

        tabPanel.add(floorsPanel, "floors");

        wallsPanel.setName("walls"); // NOI18N

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
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE))
        );

        tabPanel.add(wallsPanel, "walls");

        roofsPanel.setName("roofs"); // NOI18N

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
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
        );
        roofsPanelLayout.setVerticalGroup(
            roofsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
        );

        tabPanel.add(roofsPanel, "roofs");

        objectsPanel.setName("objects"); // NOI18N

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
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
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
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE))
        );

        tabPanel.add(objectsPanel, "objects");

        animalsPanel.setName("animals"); // NOI18N

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
                .addContainerGap(35, Short.MAX_VALUE))
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
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE))
        );

        tabPanel.add(animalsPanel, "animals");

        labelsPanel.setName("labels"); // NOI18N
        tabPanel.add(labelsPanel, "labels");

        bordersPanel.setName("borders"); // NOI18N

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
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
        );
        bordersPanelLayout.setVerticalGroup(
            bordersPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
        );

        tabPanel.add(bordersPanel, "borders");

        cavesPanel.setName("caves"); // NOI18N

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
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
        );
        cavesPanelLayout.setVerticalGroup(
            cavesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
        );

        tabPanel.add(cavesPanel, "caves");

        symmetryPanel.setName("symmetry"); // NOI18N
        tabPanel.add(symmetryPanel, "symmetry");

        bridgesPanel.setName("bridges"); // NOI18N
        tabPanel.add(bridgesPanel, "bridges");

        sidePanel.add(tabPanel, java.awt.BorderLayout.CENTER);

        sideHolderPanel.add(sidePanel, java.awt.BorderLayout.CENTER);

        floorSelectionPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        floorSelectionPanel.setPreferredSize(new java.awt.Dimension(40, 734));
        floorSelectionPanel.setLayout(new javax.swing.BoxLayout(floorSelectionPanel, javax.swing.BoxLayout.PAGE_AXIS));

        viewSidebarGroup.add(upViewToggle);
        upViewToggle.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        upViewToggle.setSelected(true);
        upViewToggle.setText(bundle.getString("Planner.upViewToggle.text")); // NOI18N
        upViewToggle.setMargin(new java.awt.Insets(2, 2, 2, 2));
        upViewToggle.setMaximumSize(new java.awt.Dimension(99, 23));
        upViewToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewSwitched(evt);
            }
        });
        floorSelectionPanel.add(upViewToggle);

        viewSidebarGroup.add(fppViewToggle);
        fppViewToggle.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        fppViewToggle.setText(bundle.getString("Planner.fppViewToggle.text")); // NOI18N
        fppViewToggle.setMargin(new java.awt.Insets(2, 2, 2, 2));
        fppViewToggle.setMaximumSize(new java.awt.Dimension(99, 23));
        fppViewToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewSwitched(evt);
            }
        });
        floorSelectionPanel.add(fppViewToggle);

        viewSidebarGroup.add(isoViewToggle);
        isoViewToggle.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        isoViewToggle.setText(bundle.getString("Planner.isoViewToggle.text")); // NOI18N
        isoViewToggle.setMargin(new java.awt.Insets(2, 2, 2, 2));
        isoViewToggle.setMaximumSize(new java.awt.Dimension(99, 23));
        isoViewToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewSwitched(evt);
            }
        });
        floorSelectionPanel.add(isoViewToggle);

        viewSidebarGroup.add(wurmianViewToggle);
        wurmianViewToggle.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        wurmianViewToggle.setText(bundle.getString("Planner.wurmianViewToggle.text")); // NOI18N
        wurmianViewToggle.setMargin(new java.awt.Insets(2, 2, 2, 2));
        wurmianViewToggle.setMaximumSize(new java.awt.Dimension(99, 23));
        wurmianViewToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewSwitched(evt);
            }
        });
        floorSelectionPanel.add(wurmianViewToggle);
        floorSelectionPanel.add(filler2);

        floorGroup.add(floorToggle16);
        floorToggle16.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        floorToggle16.setText(bundle.getString("Planner.floorToggle16.text")); // NOI18N
        floorToggle16.setMargin(new java.awt.Insets(2, 2, 2, 2));
        floorToggle16.setMaximumSize(new java.awt.Dimension(99, 23));
        floorToggle16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                floorToggleActionPerformed(evt);
            }
        });
        floorSelectionPanel.add(floorToggle16);

        floorGroup.add(floorToggle15);
        floorToggle15.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        floorToggle15.setText(bundle.getString("Planner.floorToggle15.text")); // NOI18N
        floorToggle15.setMargin(new java.awt.Insets(2, 2, 2, 2));
        floorToggle15.setMaximumSize(new java.awt.Dimension(99, 23));
        floorToggle15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                floorToggleActionPerformed(evt);
            }
        });
        floorSelectionPanel.add(floorToggle15);

        floorGroup.add(floorToggle14);
        floorToggle14.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        floorToggle14.setText(bundle.getString("Planner.floorToggle14.text")); // NOI18N
        floorToggle14.setMargin(new java.awt.Insets(2, 2, 2, 2));
        floorToggle14.setMaximumSize(new java.awt.Dimension(99, 23));
        floorToggle14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                floorToggleActionPerformed(evt);
            }
        });
        floorSelectionPanel.add(floorToggle14);

        floorGroup.add(floorToggle13);
        floorToggle13.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        floorToggle13.setText(bundle.getString("Planner.floorToggle13.text")); // NOI18N
        floorToggle13.setMargin(new java.awt.Insets(2, 2, 2, 2));
        floorToggle13.setMaximumSize(new java.awt.Dimension(99, 23));
        floorToggle13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                floorToggleActionPerformed(evt);
            }
        });
        floorSelectionPanel.add(floorToggle13);

        floorGroup.add(floorToggle12);
        floorToggle12.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        floorToggle12.setText(bundle.getString("Planner.floorToggle12.text")); // NOI18N
        floorToggle12.setMargin(new java.awt.Insets(2, 2, 2, 2));
        floorToggle12.setMaximumSize(new java.awt.Dimension(99, 23));
        floorToggle12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                floorToggleActionPerformed(evt);
            }
        });
        floorSelectionPanel.add(floorToggle12);

        floorGroup.add(floorToggle11);
        floorToggle11.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        floorToggle11.setText(bundle.getString("Planner.floorToggle11.text")); // NOI18N
        floorToggle11.setMargin(new java.awt.Insets(2, 2, 2, 2));
        floorToggle11.setMaximumSize(new java.awt.Dimension(99, 23));
        floorToggle11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                floorToggleActionPerformed(evt);
            }
        });
        floorSelectionPanel.add(floorToggle11);

        floorGroup.add(floorToggle10);
        floorToggle10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        floorToggle10.setText(bundle.getString("Planner.floorToggle10.text")); // NOI18N
        floorToggle10.setMargin(new java.awt.Insets(2, 2, 2, 2));
        floorToggle10.setMaximumSize(new java.awt.Dimension(99, 23));
        floorToggle10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                floorToggleActionPerformed(evt);
            }
        });
        floorSelectionPanel.add(floorToggle10);

        floorGroup.add(floorToggle9);
        floorToggle9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        floorToggle9.setText(bundle.getString("Planner.floorToggle9.text")); // NOI18N
        floorToggle9.setMargin(new java.awt.Insets(2, 2, 2, 2));
        floorToggle9.setMaximumSize(new java.awt.Dimension(99, 23));
        floorToggle9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                floorToggleActionPerformed(evt);
            }
        });
        floorSelectionPanel.add(floorToggle9);

        floorGroup.add(floorToggle8);
        floorToggle8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        floorToggle8.setText(bundle.getString("Planner.floorToggle8.text")); // NOI18N
        floorToggle8.setMargin(new java.awt.Insets(2, 2, 2, 2));
        floorToggle8.setMaximumSize(new java.awt.Dimension(99, 23));
        floorToggle8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                floorToggleActionPerformed(evt);
            }
        });
        floorSelectionPanel.add(floorToggle8);

        floorGroup.add(floorToggle7);
        floorToggle7.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        floorToggle7.setText(bundle.getString("Planner.floorToggle7.text")); // NOI18N
        floorToggle7.setMargin(new java.awt.Insets(2, 2, 2, 2));
        floorToggle7.setMaximumSize(new java.awt.Dimension(99, 23));
        floorToggle7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                floorToggleActionPerformed(evt);
            }
        });
        floorSelectionPanel.add(floorToggle7);

        floorGroup.add(floorToggle6);
        floorToggle6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        floorToggle6.setText(bundle.getString("Planner.floorToggle6.text")); // NOI18N
        floorToggle6.setMargin(new java.awt.Insets(2, 2, 2, 2));
        floorToggle6.setMaximumSize(new java.awt.Dimension(99, 23));
        floorToggle6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                floorToggleActionPerformed(evt);
            }
        });
        floorSelectionPanel.add(floorToggle6);

        floorGroup.add(floorToggle5);
        floorToggle5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        floorToggle5.setText(bundle.getString("Planner.floorToggle5.text")); // NOI18N
        floorToggle5.setMargin(new java.awt.Insets(2, 2, 2, 2));
        floorToggle5.setMaximumSize(new java.awt.Dimension(99, 23));
        floorToggle5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                floorToggleActionPerformed(evt);
            }
        });
        floorSelectionPanel.add(floorToggle5);

        floorGroup.add(floorToggle4);
        floorToggle4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        floorToggle4.setText(bundle.getString("Planner.floorToggle4.text")); // NOI18N
        floorToggle4.setMargin(new java.awt.Insets(2, 2, 2, 2));
        floorToggle4.setMaximumSize(new java.awt.Dimension(99, 23));
        floorToggle4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                floorToggleActionPerformed(evt);
            }
        });
        floorSelectionPanel.add(floorToggle4);

        floorGroup.add(floorToggle3);
        floorToggle3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        floorToggle3.setText(bundle.getString("Planner.floorToggle3.text")); // NOI18N
        floorToggle3.setMargin(new java.awt.Insets(2, 2, 2, 2));
        floorToggle3.setMaximumSize(new java.awt.Dimension(99, 23));
        floorToggle3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                floorToggleActionPerformed(evt);
            }
        });
        floorSelectionPanel.add(floorToggle3);

        floorGroup.add(floorToggle2);
        floorToggle2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        floorToggle2.setText(bundle.getString("Planner.floorToggle2.text")); // NOI18N
        floorToggle2.setMargin(new java.awt.Insets(2, 2, 2, 2));
        floorToggle2.setMaximumSize(new java.awt.Dimension(99, 23));
        floorToggle2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                floorToggleActionPerformed(evt);
            }
        });
        floorSelectionPanel.add(floorToggle2);

        floorGroup.add(floorToggle1);
        floorToggle1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        floorToggle1.setSelected(true);
        floorToggle1.setText(bundle.getString("Planner.floorToggle1.text")); // NOI18N
        floorToggle1.setMargin(new java.awt.Insets(2, 2, 2, 2));
        floorToggle1.setMaximumSize(new java.awt.Dimension(99, 23));
        floorToggle1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                floorToggleActionPerformed(evt);
            }
        });
        floorSelectionPanel.add(floorToggle1);
        floorSelectionPanel.add(filler1);

        floorGroup.add(floorToggleNegative1);
        floorToggleNegative1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        floorToggleNegative1.setText(bundle.getString("Planner.floorToggleNegative1.text")); // NOI18N
        floorToggleNegative1.setMargin(new java.awt.Insets(2, 2, 2, 2));
        floorToggleNegative1.setMaximumSize(new java.awt.Dimension(99, 23));
        floorToggleNegative1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                floorToggleActionPerformed(evt);
            }
        });
        floorSelectionPanel.add(floorToggleNegative1);

        floorGroup.add(floorToggleNegative2);
        floorToggleNegative2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        floorToggleNegative2.setText(bundle.getString("Planner.floorToggleNegative2.text")); // NOI18N
        floorToggleNegative2.setMargin(new java.awt.Insets(2, 2, 2, 2));
        floorToggleNegative2.setMaximumSize(new java.awt.Dimension(99, 23));
        floorToggleNegative2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                floorToggleActionPerformed(evt);
            }
        });
        floorSelectionPanel.add(floorToggleNegative2);

        floorGroup.add(floorToggleNegative3);
        floorToggleNegative3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        floorToggleNegative3.setText(bundle.getString("Planner.floorToggleNegative3.text")); // NOI18N
        floorToggleNegative3.setMargin(new java.awt.Insets(2, 2, 2, 2));
        floorToggleNegative3.setMaximumSize(new java.awt.Dimension(99, 23));
        floorToggleNegative3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                floorToggleActionPerformed(evt);
            }
        });
        floorSelectionPanel.add(floorToggleNegative3);

        floorGroup.add(floorToggleNegative4);
        floorToggleNegative4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        floorToggleNegative4.setText(bundle.getString("Planner.floorToggleNegative4.text")); // NOI18N
        floorToggleNegative4.setMargin(new java.awt.Insets(2, 2, 2, 2));
        floorToggleNegative4.setMaximumSize(new java.awt.Dimension(99, 23));
        floorToggleNegative4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                floorToggleActionPerformed(evt);
            }
        });
        floorSelectionPanel.add(floorToggleNegative4);

        floorGroup.add(floorToggleNegative5);
        floorToggleNegative5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        floorToggleNegative5.setText(bundle.getString("Planner.floorToggleNegative5.text")); // NOI18N
        floorToggleNegative5.setMargin(new java.awt.Insets(2, 2, 2, 2));
        floorToggleNegative5.setMaximumSize(new java.awt.Dimension(99, 23));
        floorToggleNegative5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                floorToggleActionPerformed(evt);
            }
        });
        floorSelectionPanel.add(floorToggleNegative5);

        floorGroup.add(floorToggleNegative6);
        floorToggleNegative6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        floorToggleNegative6.setText(bundle.getString("Planner.floorToggleNegative6.text")); // NOI18N
        floorToggleNegative6.setMargin(new java.awt.Insets(2, 2, 2, 2));
        floorToggleNegative6.setMaximumSize(new java.awt.Dimension(99, 23));
        floorToggleNegative6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                floorToggleActionPerformed(evt);
            }
        });
        floorSelectionPanel.add(floorToggleNegative6);

        sideHolderPanel.add(floorSelectionPanel, java.awt.BorderLayout.WEST);

        getContentPane().add(sideHolderPanel, java.awt.BorderLayout.EAST);

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

        viewMenuGroup.add(upViewItem);
        upViewItem.setSelected(true);
        upViewItem.setText(bundle.getString("Planner.upViewItem.text")); // NOI18N
        upViewItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewSwitched(evt);
            }
        });
        jMenu8.add(upViewItem);

        viewMenuGroup.add(fppViewItem);
        fppViewItem.setText(bundle.getString("Planner.fppViewItem.text")); // NOI18N
        fppViewItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewSwitched(evt);
            }
        });
        jMenu8.add(fppViewItem);

        viewMenuGroup.add(isoViewItem);
        isoViewItem.setText(bundle.getString("Planner.isoViewItem.text")); // NOI18N
        isoViewItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewSwitched(evt);
            }
        });
        jMenu8.add(isoViewItem);

        viewMenuGroup.add(wurmianItem);
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
        boolean upCameraSwitch = evt.getSource() == upViewItem || evt.getSource() == upViewToggle;
        boolean isoCameraSwitch = evt.getSource() == isoViewItem || evt.getSource() == isoViewToggle;
        boolean fppCameraSwitch = evt.getSource() == fppViewItem || evt.getSource() == fppViewToggle;
        boolean wurmianCameraSwitch = evt.getSource() == wurmianItem || evt.getSource() == wurmianViewToggle;
        
        mapPanel.getLoop().syncAndExecute(() -> {
            CameraType cameraType;
            if (upCameraSwitch) {
                cameraType = CameraType.TOP_VIEW;
                upViewItem.setSelected(true);
                upViewToggle.setSelected(true);
            }
            else if (isoCameraSwitch) {
                cameraType = CameraType.ISOMETRIC;
                isoViewItem.setSelected(true);
                isoViewToggle.setSelected(true);
            }
            else if (fppCameraSwitch) {
                cameraType = CameraType.SPECTATOR;
                fppViewItem.setSelected(true);
                fppViewToggle.setSelected(true);
            }
            else if (wurmianCameraSwitch) {
                cameraType = CameraType.SPECTATOR;
                wurmianItem.setSelected(true);
                wurmianViewToggle.setSelected(true);
            }
            else {
                return;
            }
            
            mapPanel.setCamera(cameraType);
            final boolean editable = mapPanel.getCamera().isEditingCapable();
            sidePanel.setVisible(editable);
            statusBar.setVisible(editable);
            Globals.fixedHeight = wurmianCameraSwitch;
        });
    }//GEN-LAST:event_viewSwitched

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        new SettingsWindow().setVisible(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void resizeItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resizeItemActionPerformed
        new ResizeWindow(mapPanel).setVisible(true);
    }//GEN-LAST:event_resizeItemActionPerformed

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

    private void donateLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_donateLabelMouseClicked
        try {
            Desktop.getDesktop().browse(new URI("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=NYTYWR5MV2U68"));
        } catch (URISyntaxException | IOException ex) {
            Log.err(ex);
        }
    }//GEN-LAST:event_donateLabelMouseClicked

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
                lmbSelectedGroundLabel.setIcon(selectedData.getOrCreateIcon());
                mapPanel.getLoop().syncAndExecute(() -> {
                    GroundUpdater.lmbData = selectedData;
                });
            }
            else if (isRightClick && node.getUserObject() != GroundUpdater.rmbData) {
                rmbSelectedGroundLabel.setText(selectedData.name);
                rmbSelectedGroundLabel.setIcon(selectedData.getOrCreateIcon());
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

    private void tabToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tabToggleActionPerformed
        Object source = evt.getSource();
        
        if (source == groundToggle) {
            Globals.tab = Tab.ground;
            setCurrentTab(groundPanel);
        }
        else if (source == heightToggle) {
            Globals.tab = Tab.height;
            setCurrentTab(heightPanel);
        }
        else if (source == floorsToggle) {
            Globals.tab = Tab.floors;
            setCurrentTab(floorsPanel);
        }
        else if (source == wallsToggle) {
            Globals.tab = Tab.walls;
            setCurrentTab(wallsPanel);
        }
        else if (source == roofsToggle) {
            Globals.tab = Tab.roofs;
            setCurrentTab(roofsPanel);
        }
        else if (source == objectsToggle) {
            Globals.tab = Tab.objects;
            setCurrentTab(objectsPanel);
        }
        else if (source == animalsToggle) {
            Globals.tab = Tab.animals;
            setCurrentTab(animalsPanel);
        }
        else if (source == labelsToggle) {
            Globals.tab = Tab.labels;
            setCurrentTab(labelsPanel);
        }
        else if (source == bordersToggle) {
            Globals.tab = Tab.borders;
            setCurrentTab(bordersPanel);
        }
        else if (source == bridgesToggle) {
            Globals.tab = Tab.bridges;
            setCurrentTab(bridgesPanel);
        }
        else if (source == symmetryToggle) {
            Globals.tab = Tab.symmetry;
            setCurrentTab(symmetryPanel);
        }
        else if (source == cavesToggle) {
            Globals.tab = Tab.caves;
            setCurrentTab(cavesPanel);
        }
        
        Analytics.getInstance().screenView().screenName(Globals.tab.toString()).postAsync();
    }//GEN-LAST:event_tabToggleActionPerformed

    private void setCurrentTab(JPanel panel) {
        CardLayout layout = (CardLayout) tabPanel.getLayout();
        
        layout.show(tabPanel, panel.getName());
        
        tabPanel.repaint();
    }
    
    private void floorToggleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_floorToggleActionPerformed
        JToggleButton button = (JToggleButton) evt.getSource();
        
        String floorString = button.getText();
        int floor = Integer.parseInt(floorString);
        if (floor > 0) {
            floor--;
        }
        
        if (floor < 0) {
            tabIconsPanel.remove(groundToggle);
            tabIconsPanel.add(cavesToggle, 0);
            if (Globals.tab == Tab.ground) {
                Globals.tab = Tab.caves;
                cavesToggle.setSelected(true);
                setCurrentTab(cavesPanel);
            }
        }
        else {
            tabIconsPanel.remove(cavesToggle);
            tabIconsPanel.add(groundToggle, 0);
            if (Globals.tab == Tab.caves) {
                Globals.tab = Tab.ground;
                groundToggle.setSelected(true);
                setCurrentTab(groundPanel);
            }
        }
        
        Globals.floor = floor;
        
        labelsPanel.updatePanel(TileSelection.getMapFragment());
    }//GEN-LAST:event_floorToggleActionPerformed
    
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
    private javax.swing.JToggleButton animalsToggle;
    private javax.swing.JTree animalsTree;
    private javax.swing.JList bordersList;
    private javax.swing.JPanel bordersPanel;
    private javax.swing.JMenuItem bordersRemoveItem;
    private javax.swing.JToggleButton bordersToggle;
    private javax.swing.JRadioButtonMenuItem bridges3dItem;
    private javax.swing.JRadioButtonMenuItem bridgesAllItem;
    private pl.wurmonline.deedplanner.forms.BridgesEditor bridgesPanel;
    private javax.swing.JToggleButton bridgesToggle;
    private javax.swing.ButtonGroup bridgesVisibilityGroup;
    private javax.swing.JMenuItem bushesRemoveItem;
    private javax.swing.JPanel cavesPanel;
    private javax.swing.JToggleButton cavesToggle;
    private javax.swing.JTree cavesTree;
    private javax.swing.JRadioButton championAnimalSizeRadio;
    private javax.swing.JRadioButton childAnimalSizeRadio;
    private pl.wurmonline.deedplanner.forms.DiagonalPanel diagonalPanel;
    private javax.swing.JLabel donateLabel;
    private javax.swing.ButtonGroup elevationGroup;
    private javax.swing.JRadioButtonMenuItem elevationOffItem;
    private javax.swing.JRadioButtonMenuItem elevationOnItem;
    private javax.swing.JMenuItem exitItem;
    private javax.swing.JRadioButton femaleAnimalSexRadio;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.ButtonGroup floorGroup;
    private javax.swing.JComboBox floorOrientationBox;
    private javax.swing.JPanel floorSelectionPanel;
    private javax.swing.JToggleButton floorToggle1;
    private javax.swing.JToggleButton floorToggle10;
    private javax.swing.JToggleButton floorToggle11;
    private javax.swing.JToggleButton floorToggle12;
    private javax.swing.JToggleButton floorToggle13;
    private javax.swing.JToggleButton floorToggle14;
    private javax.swing.JToggleButton floorToggle15;
    private javax.swing.JToggleButton floorToggle16;
    private javax.swing.JToggleButton floorToggle2;
    private javax.swing.JToggleButton floorToggle3;
    private javax.swing.JToggleButton floorToggle4;
    private javax.swing.JToggleButton floorToggle5;
    private javax.swing.JToggleButton floorToggle6;
    private javax.swing.JToggleButton floorToggle7;
    private javax.swing.JToggleButton floorToggle8;
    private javax.swing.JToggleButton floorToggle9;
    private javax.swing.JToggleButton floorToggleNegative1;
    private javax.swing.JToggleButton floorToggleNegative2;
    private javax.swing.JToggleButton floorToggleNegative3;
    private javax.swing.JToggleButton floorToggleNegative4;
    private javax.swing.JToggleButton floorToggleNegative5;
    private javax.swing.JToggleButton floorToggleNegative6;
    private javax.swing.JComboBox floorsModeCombo;
    private javax.swing.JPanel floorsPanel;
    private javax.swing.JToggleButton floorsToggle;
    private javax.swing.JTree floorsTree;
    private javax.swing.JRadioButtonMenuItem fppViewItem;
    private javax.swing.JToggleButton fppViewToggle;
    private javax.swing.JComboBox groundModeCombo;
    private javax.swing.JPanel groundPanel;
    private javax.swing.JToggleButton groundToggle;
    private javax.swing.JTree groundsTree;
    private javax.swing.ButtonGroup heightEditGroup;
    private javax.swing.JSpinner heightLeftSpinner;
    private javax.swing.JList heightList;
    private javax.swing.JPanel heightPanel;
    private javax.swing.JRadioButton heightRadio;
    private javax.swing.JSpinner heightRightSpinner;
    public pl.wurmonline.deedplanner.forms.HeightShow heightShow;
    private javax.swing.JToggleButton heightToggle;
    private javax.swing.JRadioButtonMenuItem isoViewItem;
    private javax.swing.JToggleButton isoViewToggle;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
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
    private pl.wurmonline.deedplanner.forms.LabelEditor labelsPanel;
    private javax.swing.JToggleButton labelsToggle;
    private javax.swing.JLabel lmbSelectedGroundLabel;
    private javax.swing.JMenuItem loadItem;
    private javax.swing.JRadioButton maleAnimalSexRadio;
    private javax.swing.JPanel mapHolderPanel;
    private pl.wurmonline.deedplanner.MapPanel mapPanel;
    private javax.swing.JMenuItem newMapItem;
    private javax.swing.JPanel objectsPanel;
    private javax.swing.JMenuItem objectsRemoveItem;
    private javax.swing.JTextField objectsSearchBox;
    private javax.swing.JToggleButton objectsToggle;
    private javax.swing.JTree objectsTree;
    private javax.swing.JMenuItem redoItem;
    private javax.swing.JMenuItem resizeItem;
    private javax.swing.JLabel rmbSelectedGroundLabel;
    private javax.swing.JList roofsList;
    private javax.swing.JPanel roofsPanel;
    private javax.swing.JToggleButton roofsToggle;
    private javax.swing.JMenuItem saveItem;
    private javax.swing.ButtonGroup seasonGroup;
    private javax.swing.JPanel sideHolderPanel;
    private javax.swing.JPanel sidePanel;
    private javax.swing.JRadioButton sizeRadio;
    private javax.swing.JPanel statusBar;
    private pl.wurmonline.deedplanner.forms.SymmetryEditor symmetryPanel;
    private javax.swing.JToggleButton symmetryToggle;
    private javax.swing.ButtonGroup tabGroup;
    private javax.swing.JPanel tabIconsPanel;
    private javax.swing.JPanel tabPanel;
    public javax.swing.JLabel tileLabel;
    private javax.swing.JRadioButtonMenuItem trees3dItem;
    private javax.swing.JRadioButtonMenuItem treesAllItem;
    private javax.swing.JRadioButtonMenuItem treesNothingItem;
    private javax.swing.JMenuItem treesRemoveItem;
    private javax.swing.ButtonGroup treesVisibilityGroup;
    private javax.swing.JMenuItem undoItem;
    private javax.swing.JRadioButton unisexAnimalSexRadio;
    private javax.swing.JRadioButtonMenuItem upViewItem;
    private javax.swing.JToggleButton upViewToggle;
    private javax.swing.ButtonGroup viewMenuGroup;
    private javax.swing.ButtonGroup viewSidebarGroup;
    private javax.swing.JCheckBox wallReversedAutoBox;
    private javax.swing.JCheckBox wallReversedBox;
    private javax.swing.JMenuItem wallsFencesRemoveItem;
    private javax.swing.ButtonGroup wallsGroup;
    private javax.swing.JPanel wallsPanel;
    private javax.swing.JToggleButton wallsToggle;
    private javax.swing.JTree wallsTree;
    private javax.swing.JRadioButtonMenuItem wurmianItem;
    private javax.swing.JToggleButton wurmianViewToggle;
    // End of variables declaration//GEN-END:variables
}
