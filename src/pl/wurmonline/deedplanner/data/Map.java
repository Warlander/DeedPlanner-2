package pl.wurmonline.deedplanner.data;

import com.jogamp.opengl.util.glsl.ShaderProgram;
import java.io.*;
import java.util.Scanner;
import java.util.Stack;
import javax.media.opengl.GL2;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import pl.wurmonline.deedplanner.*;
import pl.wurmonline.deedplanner.data.storage.Data;
import pl.wurmonline.deedplanner.data.storage.WAKData;
import pl.wurmonline.deedplanner.graphics.*;
import pl.wurmonline.deedplanner.logic.Tab;
import pl.wurmonline.deedplanner.util.*;

public final class Map {

    private final int width;
    private final int height;
    
    private boolean newAction = true;
    private final Stack<Action> undo = new Stack<>();
    private final Stack<Action> redo = new Stack<>();
    
    private int waterID = 0;
    private final Tile[][] tiles;
    
    private float minElevation = 5;
    private float maxElevation = 5;
    private float diffElevation = 0;
    
    public static Map parseMap(byte[] mapData) throws DeedPlannerException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new ByteArrayInputStream(mapData));
            return new Map(doc);
        } catch (SAXException | ParserConfigurationException | IOException ex) {
            Log.out(Map.class, "Unable to load map as a DeedPlanner 2 map, trying to load as WAK map.");
        }
        
        try (InputStream is = new ByteArrayInputStream(mapData);
        LittleEndianDataInputStream dis = new LittleEndianDataInputStream(is);) {
            return new Map(dis);
        } catch (IOException ex) {
            Log.err(ex);
        } catch (DeedPlannerException ex) {
            Log.out(Map.class, "Unable to load map as a Wurm Army Knife map, trying to load as old DeedPlanner map.");
        }
        
        try (InputStream is = new ByteArrayInputStream(mapData);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        Scanner scan = new Scanner(br);) {
            return new Map(scan);
        } catch (DeedPlannerException | IOException ex) {
            Log.out(Map.class, "Unable to load map as a old DeedPlanner map.");
        }
        
        throw new DeedPlannerException("Unable to load map - no known map format is found.");
    }
    
    // <editor-fold defaultstate="collapsed" desc="Map loading backends">
    
    private Map(Document doc) {
        Element map = doc.getDocumentElement();
        width = Integer.parseInt(map.getAttribute("width"));
        height = Integer.parseInt(map.getAttribute("height"));
        tiles = new Tile[width+1][height+1];
        
        NodeList list = doc.getElementsByTagName("tile");
        for (int i=0; i<list.getLength(); i++) {
            Element tile = (Element) list.item(i);
            int x = Integer.parseInt(tile.getAttribute("x"));
            int y = Integer.parseInt(tile.getAttribute("y"));
            tiles[x][y] = new Tile(this, x, y, tile);
        }
        
        createHeightData();
    }
    
    private Map(LittleEndianDataInputStream stream) throws DeedPlannerException, IOException {
        byte[] wakWordBytes = new byte[3];
        stream.read(wakWordBytes);
        String wakWordString = new String(wakWordBytes, "US-ASCII");
        if (!"WMP".equals(wakWordString)) {
            throw new DeedPlannerException("Not a WAK map");
        }
        stream.skipBytes(20);
        width = Math.max(stream.readInt(), 50);
        height = Math.max(stream.readInt(), 50);
        tiles = new Tile[width+1][height+1];
        for (int i=0; i<=width; i++) {
            for (int i2=0; i2<=height; i2++) {
                tiles[i][i2] = new Tile(this, i, i2);
            }
        }
        
        boolean bigMap = width > 255 || height> 255;
        stream.skipBytes(34);
        int tileCount = stream.readInt();
        
        for (int i=0; i<tileCount; i++) {
            final int x = readCoordinate(stream, bigMap);
            final int y = height - readCoordinate(stream, bigMap) - 1;
            final int layers = stream.read();
            if (layers==1 || layers==3) {
                tiles[x][y] = readTile(stream, x, y);
            }
            if (layers==2 || layers==3) {
                readTile(stream, x, y);
            }
        }
        
        createHeightData();
    }
    
    private int readCoordinate(LittleEndianDataInputStream stream, boolean bigMap) throws IOException {
        if (bigMap) {
            return stream.readInt();
        }
        else {
            return stream.read();
        }
    }
    
    private Tile readTile(LittleEndianDataInputStream stream, int x, int y) throws IOException {
        Tile tile = new Tile(tiles[x][y]);
        int attributes = stream.read();
        if (attributes==1 || attributes==3) {
            //TODO implement after labels are ready
            int length = stream.read();
            stream.skipBytes(length+3);
        }
        if (attributes==2 || attributes==3) {
            stream.skipBytes(4);
        }
        int terrain = stream.read();
        GroundData ground = WAKData.grounds.get(terrain);
        tile.setGround(ground, RoadDirection.CENTER, false);
        int itemsNumber = stream.readInt();
        
        for (int i=0; i<itemsNumber; i++) {
            readObject(stream, tile);
        }
        
        return tile;
    }
    
    private void readObject(LittleEndianDataInputStream stream, Tile tile) throws IOException {
        int pos = stream.read();
        if (pos==2 || pos==10) {
            int type = stream.read();
            WallData wallData = WAKData.walls.get(type);
            if (pos==2) {
                Tile t = getTile(tile, 0, 1);
                if (t!=null) {
                    t.setHorizontalWall(wallData, 0, false);
                }
            }
            if (pos==10) {
                tile.setVerticalWall(wallData, 0, false);
            }
            stream.skipBytes(1);
        }
        else {
            //TODO add objects after implementation
            stream.skipBytes(2);
        }
    }
    
    private Map(Scanner scan) throws DeedPlannerException {
        scan.next();
        width = Math.max(scan.nextInt(), 50);
        height = Math.max(scan.nextInt(), 50);
        tiles = new Tile[width+1][height+1];
        for (int i=0; i<=width; i++) {
            for (int i2=0; i2<=height; i2++) {
                tiles[i][i2] = new Tile(this, i, i2);
            }
        }
        
        while (scan.hasNext()) {
            switch (scan.next()) {
                case "H":
                    readHeight(scan);
                    break;
                case "G":
                    readGround(scan);
                    break;
                case "C":
                    scan.nextLine();
                    break;
                case "O":
                    scan.nextLine();
                    break;
                case "T":
                    readTile(scan);
                    break;
                case "BX":
                    readHorizontalBorder(scan);
                    break;
                case "BY":
                    readVerticalBorder(scan);
                    break;
                case "L":
                    scan.nextLine();
                    break;
                case "W":
                    scan.nextLine();
                    break;
            }
        }
        
        createHeightData();
    }
    
    private void readHeight(Scanner scan) {
        int x = scan.nextInt();
        int y = scan.nextInt();
        int val = scan.nextInt();
        tiles[x][y].setHeight(val, false);
    }
    
    private void readGround(Scanner scan) {
        int x = scan.nextInt();
        int y = scan.nextInt();
        String shortName = scan.next();
        GroundData data = Data.grounds.get(shortName);
        if (data!=null) {
            tiles[x][y].setGround(data, RoadDirection.CENTER, false);
        }
    }
    
    private void readHorizontalBorder(Scanner scan) {
        int x = scan.nextInt() - 1;
        int y = scan.nextInt();
        int z = scan.nextInt();
        String shortName = scan.next();
        scan.next(); scan.next(); scan.next();
        WallData data = Data.walls.get(shortName);
        if (data!=null) {
            tiles[x][z].setHorizontalWall(data, y, false);
        }
    }
    
    private void readVerticalBorder(Scanner scan) {
        int x = scan.nextInt();
        int y = scan.nextInt();
        int z = scan.nextInt();
        String shortName = scan.next();
        scan.next(); scan.next(); scan.next();
        WallData data = Data.walls.get(shortName);
        if (data!=null) {
            tiles[x][z].setVerticalWall(data, y, false);
        }
    }
    
    private void readTile(Scanner scan) {
        int x = scan.nextInt() - 1;
        int y = scan.nextInt();
        int z = scan.nextInt();
        String shortName = scan.next();
        FloorData floor = Data.floors.get(shortName);
        if (floor!=null) {
            tiles[x][z].setTileContent(floor, y, false);
            return;
        }
        RoofData roof = Data.roofs.get(shortName);
        if (roof!=null) {
            tiles[x][z].setTileContent(new Roof(roof), y, false);
        }
    }
    
    // </editor-fold>
    
    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width+1][height+1];
        for (int i=0; i<=width; i++) {
            for (int i2=0; i2<=height; i2++) {
                tiles[i][i2] = new Tile(this, i, i2);
            }
        }
        recalculateHeight();
        recalculateRoofs();
    }
    
    public Map(Map map, int startX, int startY, int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width+1][height+1];
        for (int x=0; x<=width; x++) {
            for (int y=0; y<=height; y++) {
                int getX = x - startX;
                int getY = y - startY;
                
                if (getX<0 || getY<0 || getX>=map.width+1 || getY>=map.height+1) {
                    tiles[x][y] = new Tile(this, x, y);
                }
                else {
                    tiles[x][y] = new Tile(this, map.tiles[getX][getY], x, y);
                }
            }
        }
        recalculateHeight();
        recalculateRoofs();
    }
    
    private void createHeightData() {
        recalculateHeight();
        recalculateRoofs();
        
        for (int i=0; i<width; i++) {
            for (int i2=0; i2<height; i2++) {
                tiles[i][i2].recalculateHeights();
            }
        }
    }
    
    public void render(GL2 g) {
        ShaderProgram program = Shaders.getShaders(g).simple;
        program.useProgram(g, true);
        int startX = Math.max(0, Globals.visibleDownX);
        int endX = Math.min(width, Globals.visibleUpX);
        int startY = Math.max(0, Globals.visibleDownY);
        int endY = Math.min(height, Globals.visibleUpY);
        for (int i=startX; i<=endX; i++) {
            for (int i2=startY; i2<=endY; i2++) {
                g.glPushMatrix();
                    g.glTranslatef(i*4, i2*4, 0);
                    if (i==width || i2==height) {
                        tiles[i][i2].render3d(g, true);
                    }
                    else {
                        tiles[i][i2].render3d(g, false);
                    }
                g.glPopMatrix();
            }
        }
        
        if (waterID==0) {
            waterID = Water.prepareWater(g, this);
        }
        
        if (Globals.upCamera && Globals.floor>=0 && Globals.floor<3) {
            switch (Globals.floor) {
                case 0:
                    g.glColor4f(1, 1, 1, 0.5f);
                    break;
                case 1:
                    g.glColor4f(0.6f, 0.6f, 0.6f, 0.5f);
                    break;
                case 2:
                    g.glColor4f(0.25f, 0.25f, 0.25f, 0.5f);
                    break;
            }
            renderWater(g);
            g.glColor4f(1, 1, 1, 1);
        }
        else if (!Globals.upCamera) {
            g.glColor4f(1, 1, 1, 0.5f);
            renderWater(g);
            g.glColor4f(1, 1, 1, 1);
        }
        
        if (Properties.showGrid && Globals.upCamera) {
            renderGrid(g);
        }
    }
    
    private void renderWater(GL2 g) {
        g.glEnable(GL2.GL_BLEND);
        g.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
        
        Data.water.bind(g);
        g.glCallList(waterID);
        
        g.glDisable(GL2.GL_BLEND);
    }
    
    private void renderGrid(GL2 g) {
        boolean renderColors = (Globals.renderHeight || Globals.tab==Tab.height);
        
        g.glUseProgram(0);
        g.glDisable(GL2.GL_TEXTURE_2D);
        g.glDisable(GL2.GL_DEPTH_TEST);
        g.glColor3f(0.4f, 0.4f, 0.4f);
        g.glBegin(GL2.GL_LINES);
            for (int i=Globals.visibleDownX; i<Globals.visibleUpX; i++) {
                for (int i2=Globals.visibleDownY; i2<Globals.visibleUpY; i2++) {
                    if (i<0 || i2<0 || i>=width || i2>=height) {
                        continue;
                    }
                    if (renderColors) {
                        float color = ((float)tiles[i][i2].getHeight() - minElevation) / diffElevation;
                        g.glColor3f(color, 1-color, 0);
                    }
                    g.glVertex2f(i*4, i2*4);
                    if (renderColors) {
                        float color = ((float)tiles[i][i2+1].getHeight() - minElevation) / diffElevation;
                        g.glColor3f(color, 1-color, 0);
                    }
                    g.glVertex2f(i*4, i2*4+4);

                    if (renderColors) {
                        float color = ((float)tiles[i][i2].getHeight() - minElevation) / diffElevation;
                        g.glColor3f(color, 1-color, 0);
                    }
                    g.glVertex2f(i*4, i2*4);
                    if (renderColors) {
                        float color = ((float)tiles[i+1][i2].getHeight() - minElevation) / diffElevation;
                        g.glColor3f(color, 1-color, 0);
                    }
                    g.glVertex2f(i*4+4, i2*4);
                }
            }
        g.glEnd();
        g.glColor3f(1f, 1f, 1f);
        g.glEnable(GL2.GL_DEPTH_TEST);
        g.glEnable(GL2.GL_TEXTURE_2D);
    }
    
    public String serialize() throws ParserConfigurationException, TransformerConfigurationException, TransformerException, IOException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();
        
        Element main = doc.createElement("map");
        main.setAttribute("width", Integer.toString(width));
        main.setAttribute("height", Integer.toString(height));
        doc.appendChild(main);
        
        for (int i=0; i<=width; i++) {
            for (int i2=0; i2<=height; i2++) {
                tiles[i][i2].serialize(doc, main);
            }
        }
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        
        return writer.getBuffer().toString();
    }
    
    public void undo() {
        if (!undo.isEmpty()) {
            Action action = undo.pop();
            action.undo(this);
            redo.add(action);
        }
    }
    
    public void redo() {
        if (!redo.isEmpty()) {
            Action action = redo.pop();
            action.redo(this);
            undo.add(action);
        }
    }
            
    public void addUndo(Tile newTile, Tile oldTile) {
        if (newAction) {
            if (undo.isEmpty() || !undo.peek().isEmpty()) {
                undo.push(new Action(newTile, oldTile));
                redo.clear();
            }
            newAction = false;
        }
        else {
            undo.peek().add(newTile, oldTile);
        }
    }
    
    public void newAction() {
        newAction = true;
    }
    
    public Tile getTile(int x, int y) {
        if (x<0 || y<0 || x>=width+1 || y>=height+1) {
            try {
                throw new DeedPlannerException("Invalid tile coordinates: "+x+", "+y);
            } catch (DeedPlannerException ex) {
                Log.err(ex);
                return null;
            }
        }
        return tiles[x][y];
    }
    
    /**
     * This method allows to get map tile in relation to another tile.
     * 
     * @param tile reference tile.
     * @param xOffset x axis offset of tile to get in relation to given tile.
     * @param yOffset y axis offset of tile to get in relation to given tile.
     * @return Tile or null if out of bounds.
     */
    public Tile getTile(Tile tile, int xOffset, int yOffset) {
        int x = tile.getX() + xOffset;
        int y = tile.getY() + yOffset;
        
        if (x<0 || y<0 || x>=width+1 || y>=height+1) {
            return null;
        }
        
        return tiles[x][y];
    }
    
    void setTile(Tile tile, int x, int y) {
        tiles[x][y] = tile;
        for (int i=-1; i<=1; i++) {
            for (int i2=-1; i2<=1; i2++) {
                Tile t = getTile(tile, i, i2);
                if (t!=null) {
                    t.recalculateHeights();
                    t.getGround().redraw();
                }
            }
        }
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public void recalculateRoofs() {
        for (int i=0; i<=width; i++) {
            for (int i2=0; i2<=height; i2++) {
                for (int i3=0; i3<Constants.FLOORS_LIMIT; i3++) {
                    if (tiles[i][i2].getTileContent(i3) instanceof Roof) {
                        ((Roof) tiles[i][i2].getTileContent(i3)).recalculateLevel(tiles[i][i2], i3);
                    }
                }
            }
        }
        
        for (int i=0; i<=width; i++) {
            for (int i2=0; i2<=height; i2++) {
                for (int i3=0; i3<Constants.FLOORS_LIMIT; i3++) {
                    if (tiles[i][i2].getTileContent(i3) instanceof Roof) {
                        ((Roof) tiles[i][i2].getTileContent(i3)).recalculateMesh(tiles[i][i2], i3);
                    }
                }
            }
        }
    }
    
    void recalculateHeight() {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i=0; i<=width; i++) {
            for (int i2=0; i2<=height; i2++) {
                int elevation = tiles[i][i2].getHeight();
                if (elevation>max) {
                    max = elevation;
                }
                if (elevation<min) {
                    min = elevation;
                }
            }
        }
        maxElevation = max;
        minElevation = min;
        diffElevation = max - min;
    }
    
    public String toString() {
        return "Map";
    }
    
}
