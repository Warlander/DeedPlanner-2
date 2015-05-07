package pl.wurmonline.deedplanner.data.io;

import java.io.*;
import java.util.ArrayList;
import java.util.function.Consumer;
import javax.media.opengl.GL2;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import pl.wurmonline.deedplanner.data.*;
import pl.wurmonline.deedplanner.data.storage.Data;
import pl.wurmonline.deedplanner.forms.Loading;
import pl.wurmonline.deedplanner.graphics.texture.TexComplex;
import pl.wurmonline.deedplanner.util.*;
import pl.wurmonline.deedplanner.util.jogl.*;

public final class DataLoader {

    private DataLoader() {}
    
    public static void loadData(Loading loading, File file) throws ParserConfigurationException, IOException, SAXException, DeedPlannerException {
        Document doc = XMLUtils.fileToXMLDoc(file);
        
        loading.increaseProgress(java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle").getString("LOADING GROUND DATA"));
        loadGrounds(doc);
        loading.increaseProgress(java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle").getString("LOADING FLOOR DATA"));
        loadFloors(doc);
        loading.increaseProgress(java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle").getString("LOADING WALL DATA"));
        loadWalls(doc);
        loading.increaseProgress(java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle").getString("LOADING BORDERS DATA"));
        loadBorders();
        loading.increaseProgress(java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle").getString("LOADING ROOF DATA"));
        loadRoofs(doc);
        loading.increaseProgress(java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle").getString("LOADING OBJECT DATA"));
        loadObjects(doc);
        loading.increaseProgress(java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle").getString("LOADING CAVE DATA"));
        loadCaves(doc);
        loading.increaseProgress(java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle").getString("LAUNCHING"));
    }
    
    private static void loadGrounds(Document doc) {
        NodeList entities = doc.getElementsByTagName("ground");
        
        for (int i=0; i<entities.getLength(); i++) {
            String name;
            String shortName;
            TexComplex tex = null;
            ArrayList<String[]> categories = new ArrayList<>();
            boolean diagonal = false;
            
            Node entity = entities.item(i);
            
            NamedNodeMap map = entity.getAttributes();
            name = map.getNamedItem("name").getNodeValue();
            shortName = map.getNamedItem("shortname").getNodeValue();
            
            NodeList list = entity.getChildNodes();
            
            for (int i2=0; i2<list.getLength(); i2++) {
                Node node = list.item(i2);
                switch (node.getNodeName()) {
                    case "tex":
                        tex = new TexComplex(node);
                        break;
                    case "category":
                        categories.add(node.getTextContent().split("/"));
                        break;
                    case "diagonal":
                        diagonal = true;
                        break;
                }
            }
            
            GroundData data = new GroundData(name, shortName, tex, diagonal);
            Log.out(DataLoader.class, "Ground data "+data+" loaded and ready to use!");
            Data.grounds.put(shortName, data);
            addToCategories(Data.groundsTree, categories, data, name);
        }
    }
    
    private static void loadFloors(Document doc) throws IOException {
        NodeList entities = doc.getElementsByTagName("floor");
        
        for (int i=0; i<entities.getLength(); i++) {
            String name;
            String shortName;
            Model model = null;
            ArrayList<String[]> categories = new ArrayList<>();
            boolean opening = false;
            Materials materials = null;
            
            Node entity = entities.item(i);
            
            NamedNodeMap map = entity.getAttributes();
            name = map.getNamedItem("name").getNodeValue();
            shortName = map.getNamedItem("shortname").getNodeValue();
            
            NodeList list = entity.getChildNodes();
            
            for (int i2=0; i2<list.getLength(); i2++) {
                Node node = list.item(i2);
                switch (node.getNodeName()) {
                    case "model":
                        model = new Model((Element) node);
                        break;
                    case "category":
                        categories.add(node.getTextContent().split("/"));
                        break;
                    case "opening":
                        opening = true;
                        break;
                    case "materials":
                        materials = new Materials(node);
                        break;
                }
            }
                
            FloorData data = new FloorData(model, name, shortName, opening, materials);
            Log.out(DataLoader.class, "Floor data "+data+" loaded and ready to use!");
            Data.floors.put(shortName, data);

            addToCategories(Data.floorsTree, categories, data, name);
        }
    }
    
    private static void loadWalls(Document doc) throws IOException, DeedPlannerException {
        NodeList entities = doc.getElementsByTagName("wall");
        
        for (int i=0; i<entities.getLength(); i++) {
            final String name;
            final String shortName;
            Color color = null;
            final float scale;
            final boolean houseWall;
            final boolean arch;
            final boolean archBuildable;
            Model model = null;
            String iconLocation = null;
            ArrayList<String[]> categories = new ArrayList<>();
            Materials materials = null;
            
            Node entity = entities.item(i);
            
            NamedNodeMap map = entity.getAttributes();
            name = map.getNamedItem("name").getNodeValue();
            shortName = map.getNamedItem("shortname").getNodeValue();
            scale = Float.parseFloat(map.getNamedItem("scale").getNodeValue());
            String type = map.getNamedItem("type").getNodeValue();
            houseWall = "house".equals(type) || "arch".equals(type);
            arch = "arch".equals(type);
            archBuildable = "lowfence".equals(type);
            
            NodeList list = entity.getChildNodes();
            
            for (int i2=0; i2<list.getLength(); i2++) {
                Node node = list.item(i2);
                switch (node.getNodeName()) {
                    case "model":
                        model = new Model((Element) node);
                        break;
                    case "category":
                        categories.add(node.getTextContent().split("/"));
                        break;
                    case "color":
                        color = new Color((Element) node);
                        break;
                    case "materials":
                        materials = new Materials(node);
                        break;
                    case "icon":
                        iconLocation = node.getAttributes().getNamedItem("location").getNodeValue();
                        break;
                }
            }
                
            WallData data = new WallData(model, name, shortName, color, scale, houseWall, arch, archBuildable, materials, iconLocation);
            Log.out(DataLoader.class, "Wall data "+data+" loaded and ready to use!");
            Data.walls.put(shortName, data);

            addToCategories(Data.wallsTree, categories, data, name);
        }
    }
    
    private static void loadBorders() {
        loadBorderCategory("line", "l", DataLoader::straightLineRender);
        loadBorderCategory("dotted line", "dl", DataLoader::dottedLineRender);
        loadBorderCategory("zigzag line", "zl", DataLoader::zigzagLineRender);
    }
    
    private static void loadBorderCategory(String name, String shortName, Consumer<GL2> drawCall) {
        loadBorder("Blue "+name, "be"+shortName, drawCall, new Color(java.awt.Color.blue));
        loadBorder("Black "+name, "bk"+shortName, drawCall, new Color(java.awt.Color.black));
        loadBorder("Cyan "+name, "cn"+shortName, drawCall, new Color(java.awt.Color.cyan));
        loadBorder("Green "+name, "gn"+shortName, drawCall, new Color(java.awt.Color.green));
        loadBorder("Magenta "+name, "ma"+shortName, drawCall, new Color(java.awt.Color.magenta));
        loadBorder("Orange "+name, "oe"+shortName, drawCall, new Color(java.awt.Color.orange));
        loadBorder("Pink "+name, "pk"+shortName, drawCall, new Color(java.awt.Color.pink));
        loadBorder("Purple "+name, "pe"+shortName, drawCall, new Color(new java.awt.Color(128, 0, 128)));
        loadBorder("Red "+name, "rd"+shortName, drawCall, new Color(java.awt.Color.red));
        loadBorder("White "+name, "we"+shortName, drawCall, new Color(java.awt.Color.white));
        loadBorder("Yellow "+name, "yw"+shortName, drawCall, new Color(java.awt.Color.yellow));
    }
    
    private static void loadBorder(String name, String shortName, Consumer<GL2> drawCall, Color color) {
        BorderData data = new BorderData(name, shortName, drawCall, color);
        Data.borders.put(shortName, data);
        Data.bordersList.addElement(data);
    }
    
    private static void straightLineRender(GL2 g) {
        g.glLineWidth(2);
        g.glBegin(GL2.GL_LINES);
            g.glVertex2f(0, 0);
            g.glVertex2f(4, 0);
        g.glEnd();
    }
    
    private static void dottedLineRender(GL2 g) {
        g.glPointSize(3);
        g.glBegin(GL2.GL_POINTS);
            g.glVertex2f(0, 0);
            g.glVertex2f(1, 0);
            g.glVertex2f(2, 0);
            g.glVertex2f(3, 0);
            g.glVertex2f(4, 0);
        g.glEnd();
    }
    
    private static void zigzagLineRender(GL2 g) {
        g.glLineWidth(2);
        g.glBegin(GL2.GL_LINE_STRIP);
            g.glVertex2f(0, 0);
            g.glVertex2f(0.5f, 0.25f);
            g.glVertex2f(1.5f, -0.25f);
            g.glVertex2f(2.5f, 0.25f);
            g.glVertex2f(3.5f, -0.25f);
            g.glVertex2f(4, 0);
        g.glEnd();
    }
    
    private static void loadRoofs(Document doc) throws IOException {
        NodeList entities = doc.getElementsByTagName("roof");
        
        for (int i=0; i<entities.getLength(); i++) {
            String name;
            String shortName;
            Tex tex;
            Materials materials = null;
            
            Node entity = entities.item(i);
            
            NamedNodeMap map = entity.getAttributes();
            name = map.getNamedItem("name").getNodeValue();
            shortName = map.getNamedItem("shortname").getNodeValue();
            tex = Tex.getTexture(map.getNamedItem("tex").getNodeValue());
            Node materialsNode = entity.getChildNodes().item(1);
            if (materialsNode!=null) {
                materials = new Materials(materialsNode);
            }
            
            RoofData data = new RoofData(name, shortName, tex, materials);
            Log.out(DataLoader.class, "Roof data "+data+" loaded and ready to use!");
            Data.roofs.put(shortName, data);
            Data.roofsList.addElement(data);
        }
    }
    
    private static void loadObjects(Document doc) throws IOException {
        NodeList entities = doc.getElementsByTagName("object");
        
        for (int i=0; i<entities.getLength(); i++) {
            final String name;
            final String shortName;
            final boolean centerOnly;
            Model model = null;
            ArrayList<String[]> categories = new ArrayList<>();
            Materials materials = null;
            
            Node entity = entities.item(i);
            
            NamedNodeMap map = entity.getAttributes();
            name = map.getNamedItem("name").getNodeValue();
            shortName = map.getNamedItem("shortname").getNodeValue();
            String type = map.getNamedItem("type").getNodeValue();
            centerOnly = "centered".equals(type);
            
            NodeList list = entity.getChildNodes();
            
            for (int i2=0; i2<list.getLength(); i2++) {
                Node node = list.item(i2);
                switch (node.getNodeName()) {
                    case "model":
                        model = new Model((Element) node);
                        break;
                    case "category":
                        categories.add(node.getTextContent().split("/"));
                        break;
                    case "materials":
                        materials = new Materials(node);
                }
            }
                
            GameObjectData data = new GameObjectData(model, name, shortName, centerOnly, materials);
            Log.out(DataLoader.class, "Object data "+data+" loaded and ready to use!");
            Data.objects.put(shortName, data);

            addToCategories(Data.objectsTree, categories, data, name);
        }
    }
    
    private static void loadCaves(Document doc) throws IOException {
        
    }
    
    private static void addToCategories(DefaultMutableTreeNode node, ArrayList<String[]> categories, Object data, String name) {
        for (String[] path : categories) {
            DefaultMutableTreeNode current = node;
            dirLoop:
            for (String dir : path) {
                for (int i2=0; i2<current.getChildCount(); i2++) {
                    if (current.getChildAt(i2).toString().equals(dir)) {
                        current = (DefaultMutableTreeNode) current.getChildAt(i2);
                        continue dirLoop;
                    }
                }
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(dir);
                current.add(newNode);
                current = newNode;
            }
            DefaultMutableTreeNode endNode = new DefaultMutableTreeNode(name);
            endNode.setUserObject(data);
            current.add(endNode);
        }
    }
    
}
