package pl.wurmonline.deedplanner.data.io;

import java.io.*;
import java.util.ArrayList;
import javax.swing.JProgressBar;
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
    
    public static void loadData(Loading loading, File file) throws ParserConfigurationException, IOException, SAXException {
        Document doc = FileUtils.fileToXMLDoc(file);
        
        loading.increaseProgress("Loading ground data");
        loadGrounds(doc);
        loading.increaseProgress("Loading floor data");
        loadFloors(doc);
        loading.increaseProgress("Loading wall data");
        loadWalls(doc);
        loading.increaseProgress("Loading roof data");
        loadRoofs(doc);
//        loading.increaseProgress("Loading object data");
//        loadObjects(loading.currentBar, doc);
        loading.increaseProgress("Launching");
    }
    
    private static void loadGrounds(Document doc) {
        NodeList entities = doc.getElementsByTagName("ground");
        
        for (int i=0; i<entities.getLength(); i++) {
            String name;
            String shortName;
            TexComplex tex = null;
            ArrayList<String[]> categories = new ArrayList<>();
            byte noiseStr = 0;
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
                    case "noise":
                        String power = node.getAttributes().getNamedItem("power").getNodeValue();
                        noiseStr = Byte.parseByte(power);
                        break;
                    case "diagonal":
                        diagonal = true;
                        break;
                }
            }
            
            GroundData data = new GroundData(name, shortName, tex, noiseStr, diagonal);
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
                }
            }
                
            FloorData data = new FloorData(model, name, shortName, opening);
            Log.out(DataLoader.class, "Floor data "+data+" loaded and ready to use!");
            Data.floors.put(shortName, data);

            addToCategories(Data.floorsTree, categories, data, name);
        }
    }
    
    private static void loadWalls(Document doc) throws IOException {
        NodeList entities = doc.getElementsByTagName("wall");
        
        for (int i=0; i<entities.getLength(); i++) {
            String name;
            String shortName;
            Color color = null;
            float scale;
            boolean houseWall = false;
            Model model = null;
            ArrayList<String[]> categories = new ArrayList<>();
            
            Node entity = entities.item(i);
            
            NamedNodeMap map = entity.getAttributes();
            name = map.getNamedItem("name").getNodeValue();
            shortName = map.getNamedItem("shortname").getNodeValue();
            scale = Float.parseFloat(map.getNamedItem("scale").getNodeValue());
            
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
                    case "house":
                        houseWall = true;
                        break;
                }
            }
                
            WallData data = new WallData(model, name, shortName, color, scale, houseWall);
            Log.out(DataLoader.class, "Wall data "+data+" loaded and ready to use!");
            Data.walls.put(shortName, data);

            addToCategories(Data.wallsTree, categories, data, name);
        }
    }
    
    private static void loadRoofs(Document doc) throws IOException {
        NodeList entities = doc.getElementsByTagName("roof");
        
        for (int i=0; i<entities.getLength(); i++) {
            String name;
            String shortName;
            Tex tex;
            
            Node entity = entities.item(i);
            
            NamedNodeMap map = entity.getAttributes();
            name = map.getNamedItem("name").getNodeValue();
            shortName = map.getNamedItem("shortname").getNodeValue();
            tex = Tex.getTexture(map.getNamedItem("tex").getNodeValue());
                
            RoofData data = new RoofData(name, shortName, tex);
            Log.out(DataLoader.class, "Roof data "+data+" loaded and ready to use!");
            Data.roofs.put(shortName, data);
            Data.roofsList.addElement(data);
        }
    }
    
    private static void loadObjects(Document doc) throws IOException {
        NodeList entities = doc.getElementsByTagName("object");
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
