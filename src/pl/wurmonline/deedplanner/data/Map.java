package pl.wurmonline.deedplanner.data;

import com.google.common.io.LittleEndianDataInputStream;
import com.jogamp.opengl.util.glsl.ShaderProgram;
import java.awt.Font;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
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
import pl.wurmonline.deedplanner.logic.TileSelection;
import pl.wurmonline.deedplanner.util.DeedPlannerException;
import pl.wurmonline.deedplanner.util.Log;
import pl.wurmonline.deedplanner.util.jogl.Color;

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
    
    private float minCaveElevation = 5;
    private float maxCaveElevation = 5;
    private float diffCaveElevation = 0;
    
    private float minCaveSize = 5;
    private float maxCaveSize = 5;
    private float diffCaveSize = 0;
    
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
            int length = stream.read();
            byte[] textBytes = new byte[length];
            stream.read(textBytes);
            String text = new String(textBytes, "US-ASCII");
            tile.setLabel(new Label(Font.decode("Arial-18"), text, new Color(0, 0, 0)), false);
            stream.skipBytes(3);
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
        int type = stream.read();
        if (pos==2 || pos==10) {
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
            ObjectLocation loc = WAKData.locations.get(pos);
            GameObjectData data = WAKData.objects.get(type);
            if (data!=null) {
                tile.setGameObject(data, loc, 0);
            }
            stream.skipBytes(1);
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
                    readObject(scan);
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
                    readLabel(scan);
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
        FloorData floorData = Data.floors.get(shortName);
        if (floorData!=null) {
            Floor floor = new Floor(floorData);
            tiles[x][z].setTileContent(floor, y, false);
            return;
        }
        RoofData roof = Data.roofs.get(shortName);
        if (roof!=null) {
            tiles[x][z].setTileContent(new Roof(roof), y, false);
        }
    }
    
    private void readObject(Scanner scan) {
        int x = scan.nextInt();
        int y = scan.nextInt();
        int z = scan.nextInt();
        String shortName = scan.next();
        double rPaint = Double.parseDouble(scan.next());
        double gPaint = Double.parseDouble(scan.next());
        double bPaint = Double.parseDouble(scan.next());
        int rotation = Integer.parseInt(scan.next());
        
        int xPart = x%4;
        int zPart = z%4;
        x/=4;
        z/=4;
        
        GameObjectData object = Data.objects.get(shortName);
        final ObjectLocation loc;
        if (object.centerOnly) {
            loc = ObjectLocation.MIDDLE_CENTER;
        }
        else if (xPart<2 && zPart<2) {
            loc = ObjectLocation.BOTTOM_LEFT;
        }
        else if (xPart>=2 && zPart<2) {
            loc = ObjectLocation.BOTTOM_RIGHT;
        }
        else if (xPart<2 && zPart>=2) {
            loc = ObjectLocation.TOP_LEFT;
        }
        else {
            loc = ObjectLocation.TOP_RIGHT;
        }
        tiles[x][z].setGameObject(object, loc, y);
    }
    
    private void readLabel(Scanner scan) {
        final int x = scan.nextInt();
        final int y = scan.nextInt();
        String text = scan.next().replace("_", " ").replace("\\n", "\n");
        String font = scan.next().replace("_", " ");
        final int size = scan.nextInt();
        final int rPaint = scan.nextInt();
        final int gPaint = scan.nextInt();
        final int bPaint = scan.nextInt();
        final int aPaint = scan.nextInt();
        boolean cave = false;
        if (scan.hasNextBoolean()) {
            cave = scan.nextBoolean();
        }
        if (cave = true) {
            return;
        }
        
        final Font f = new Font(font, Font.PLAIN, size);
        final Color c = new Color(new java.awt.Color(rPaint, gPaint, bPaint, aPaint));
        tiles[x][y].setLabel(new Label(f, text, c), false);
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
        
        BridgePartType[] segments = new BridgePartType[] {BridgePartType.ABUTMENT, BridgePartType.CROWN, BridgePartType.ABUTMENT, BridgePartType.SUPPORT, BridgePartType.ABUTMENT, BridgePartType.CROWN, BridgePartType.ABUTMENT};
        
        Bridge.createBridge(this, tiles[5][5], tiles[11][5], new WoodenBridgeData(), BridgeType.FLAT, segments, height);
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
    }
    
    public void createHeightData() {
        recalculateHeight();
        recalculateRoofs();
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
        
        if (Globals.upCamera) {
            g.glDisable(GL2.GL_TEXTURE_2D);
            g.glDisable(GL2.GL_DEPTH_TEST);
            if (Properties.showGrid) {
                renderGrid(g);
            }
            for (int i=startX; i<=endX; i++) {
                for (int i2=startY; i2<=endY; i2++) {
                    g.glPushMatrix();
                        g.glTranslatef(i*4, i2*4, 0);
                        tiles[i][i2].render2d(g);
                    g.glPopMatrix();
                }
            }
            for (int i=startX; i<=endX; i++) {
                for (int i2=startY; i2<=endY; i2++) {
                    if (TileSelection.getMapFragment()!=null && TileSelection.getMapFragment().contains(tiles[i][i2])) {
                        g.glPushMatrix();
                            g.glTranslatef(i*4, i2*4, 0);
                            tiles[i][i2].renderSelection(g);
                        g.glPopMatrix();
                    }
                }
            }
            for (int i=startX; i<=endX; i++) {
                for (int i2=startY; i2<=endY; i2++) {
                    if (Globals.floor>=0) {
                        if (tiles[i][i2].getLabel()!=null) {
                            g.glPushMatrix();
                                g.glTranslatef(i*4, i2*4, 0);
                                tiles[i][i2].renderLabel(g);
                            g.glPopMatrix();
                        }
                    }
                    else {
                        if (tiles[i][i2].getCaveLabel()!=null) {
                            g.glPushMatrix();
                                g.glTranslatef(i*4, i2*4, 0);
                                tiles[i][i2].renderCaveLabel(g);
                            g.glPopMatrix();
                        }
                    }
                }
            }
            g.glEnable(GL2.GL_DEPTH_TEST);
            g.glEnable(GL2.GL_TEXTURE_2D);
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
        g.glColor3f(0.4f, 0.4f, 0.4f);
        for (int i=Globals.visibleDownX; i<Globals.visibleUpX; i++) {
            for (int i2=Globals.visibleDownY; i2<Globals.visibleUpY; i2++) {
                if (i<0 || i2<0 || i>=width || i2>=height) {
                    continue;
                }
                
                if (tiles[i][i2].getVerticalWall(Globals.floor)!=null && tiles[i][i2].getVerticalFence(Globals.floor)!=null) {
                    g.glLineWidth(3);
                }
                g.glBegin(GL2.GL_LINES);
                    if (renderColors) {
                        applyColor(g, tiles[i][i2], false);
                    }
                    g.glVertex2f(i*4, i2*4);
                    if (renderColors) {
                        applyColor(g, tiles[i][i2+1], false);
                    }
                    g.glVertex2f(i*4, i2*4+4);
                g.glEnd();
                g.glLineWidth(1);

                if (tiles[i][i2].getHorizontalWall(Globals.floor)!=null && tiles[i][i2].getHorizontalFence(Globals.floor)!=null) {
                    g.glLineWidth(3);
                }
                g.glBegin(GL2.GL_LINES);
                    if (renderColors) {
                        applyColor(g, tiles[i][i2], false);
                    }
                    g.glVertex2f(i*4, i2*4);
                    if (renderColors) {
                        applyColor(g, tiles[i+1][i2], false);
                    }
                    g.glVertex2f(i*4+4, i2*4);
                g.glEnd();
                g.glLineWidth(1);
                
                if (Globals.floor==-1 && renderColors) {
                    g.glPointSize(10);
                    g.glBegin(GL2.GL_POINTS);
                        applyColor(g, tiles[i][i2], true);
                        g.glVertex2f(i*4, i2*4);
                    g.glEnd();
                }
            }
        }
        g.glColor3f(1f, 1f, 1f);
    }
    
    private void applyColor(GL2 g, Tile t, boolean dot) {
        if (Globals.floor>=0) {
            applyColor(g, t::getHeight, dot);
        }
        else if (!dot) {
            applyColor(g, t::getCaveHeight, dot);
        }
        else {
            applyColor(g, t::getCaveSize, dot);
        }
    }
    
    private void applyColor(GL2 g, IntSupplier func, boolean dot) {
        float color;
        if (Globals.floor>=0) {
            color = ((float)func.getAsInt() - minElevation) / diffElevation;
        }
        else if (!dot) {
            color = ((float)func.getAsInt() - minCaveElevation) / diffCaveElevation;
        }
        else {
            color = ((float)func.getAsInt() - minCaveSize) / diffCaveSize;
        }
        if (Float.isNaN(color)) {
            color = 0.5f;
        }
        
        if (!Properties.colorblind) {
            g.glColor3f(color, 1-color, 0);
        }
        else {
            g.glColor3f(color, 0, 1-color);
        }
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
    
    public void getTileAndExecute(Tile tile, int xOffset, int yOffset, Consumer<Tile> consumer) {
        Tile t = getTile(tile, xOffset, yOffset);
        if (t!=null) {
            consumer.accept(t);
        }
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
        setTile(tile, x, y, true);
    }
    
    void setTile(Tile tile, int x, int y, boolean undo) {
        Tile oldTile = tiles[x][y];
        tiles[x][y] = tile;
        
        if (undo) {
            addUndo(tile, oldTile);
        }
    }
    
    public Materials getTotalMaterials() {
        Materials materials = new Materials();
        for (int i = 0; i < width; i++) {
            for (int i2 = 0; i2 < height; i2++) {
                materials.put(tiles[i][i2].getMaterials());
            }
        }
        return materials;
    }
    
    public void deleteBuildingOnTile(Tile startTile) {
        ArrayList<Tile> houseTiles = getRoomTiles(startTile);
        
        houseTiles = getAllHouseTiles(houseTiles);
        
        if (houseTiles==null) {
            return;
        }
        
        for (Tile t : houseTiles) {
            boolean includeTop = !houseTiles.contains(getTile(t, 0, 1));
            boolean includeRight = !houseTiles.contains(getTile(t, 1, 0));
            for (int i = 0; i < Constants.FLOORS_LIMIT; i++) {
                t.setTileContent(null, i, true);
                t.setHorizontalWall(null, i, true);
                t.setVerticalWall(null, i, true);
                if (includeTop) {
                    getTile(t, 0, 1).setHorizontalWall(null, i, true);
                }
                if (includeRight) {
                    getTile(t, 1, 0).setVerticalWall(null, i, true);
                }
            }
        }
        newAction();
    }
    
    public HouseResults getMaterialsOfBuilding(Tile startTile) {
        ArrayList<Tile> houseTiles = getRoomTiles(startTile);
        
        houseTiles = getAllHouseTiles(houseTiles);
        
        if (houseTiles==null) {
            return null;
        }
        
        int carpentry = 0;
        for (Tile t : houseTiles) {
            carpentry++;
            if (!houseTiles.contains(getTile(t, -1, 0))) {
                carpentry++;
            }
            if (!houseTiles.contains(getTile(t, 1, 0))) {
                carpentry++;
            }
            if (!houseTiles.contains(getTile(t, 0, -1))) {
                carpentry++;
            }
            if (!houseTiles.contains(getTile(t, 0, 1))) {
                carpentry++;
            }
        }
        
        Materials mats = new Materials();
        for (Tile t : houseTiles) {
            boolean includeTop = !houseTiles.contains(getTile(t, 0, 1));
            boolean includeRight = !houseTiles.contains(getTile(t, 1, 0));
            mats.put(t.getMaterials(includeRight, includeTop));
        }
        return new HouseResults(mats, carpentry);
    }
    
    private ArrayList<Tile> getAllHouseTiles(ArrayList<Tile> houseTiles) {
        if (houseTiles.isEmpty()) {
            return null;
        }
        ArrayList<Tile> newHouseTiles = new ArrayList<>();
        newHouseTiles.addAll(houseTiles);
        for (Tile t : houseTiles) {
            if (!newHouseTiles.contains(getTile(t, -1, 0))) {
                newHouseTiles.addAll(getRoomTiles(getTile(t, -1, 0)));
            }
            if (!newHouseTiles.contains(getTile(t, 1, 0))) {
                newHouseTiles.addAll(getRoomTiles(getTile(t, 1, 0)));
            }
            if (!newHouseTiles.contains(getTile(t, 0, -1))) {
                newHouseTiles.addAll(getRoomTiles(getTile(t, 0, -1)));
            }
            if (!newHouseTiles.contains(getTile(t, 0, 1))) {
                newHouseTiles.addAll(getRoomTiles(getTile(t, 0, 1)));
            }
        }
        if (newHouseTiles.equals(houseTiles)) {
            return newHouseTiles;
        }
        else {
            return getAllHouseTiles(newHouseTiles);
        }
    }
    
    private ArrayList<Tile> getRoomTiles(Tile startTile) {
        ArrayList<Tile> tilesList = new ArrayList<>();
        Stack<Tile> stack = new Stack<>();
        
        stack.push(startTile);
        
        while (!stack.isEmpty()) {
            if (tilesList.size()>100) {
                return new ArrayList<>();
            }
            
            Tile anchor = stack.pop();
            if (!tilesList.contains(anchor)) {
                tilesList.add(anchor);
            }
            
            if (anchor.isPassable(TileBorder.SOUTH)) {
                checkAndAddTile(tilesList, stack, getTile(anchor, 0, -1));
            }
            if (anchor.isPassable(TileBorder.NORTH)) {
                checkAndAddTile(tilesList, stack, getTile(anchor, 0, 1));
            }
            if (anchor.isPassable(TileBorder.WEST)) {
                checkAndAddTile(tilesList, stack, getTile(anchor, -1, 0));
            }
            if (anchor.isPassable(TileBorder.EAST)) {
                checkAndAddTile(tilesList, stack, getTile(anchor, 1, 0));
            }
        }
        
        return tilesList;
    }
    
    private void checkAndAddTile(ArrayList<Tile> tilesList, Stack<Tile> stack, Tile tile) {
        if (tile!=null && !tilesList.contains(tile)) {
            stack.push(tile);
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
        int caveMin = Integer.MAX_VALUE;
        int caveMax = Integer.MIN_VALUE;
        int caveSizeMin = Integer.MAX_VALUE;
        int caveSizeMax = Integer.MIN_VALUE;
        for (int i=0; i<=width; i++) {
            for (int i2=0; i2<=height; i2++) {
                int elevation = tiles[i][i2].getHeight();
                int caveElevation = tiles[i][i2].getCaveHeight();
                int caveSize = tiles[i][i2].getCaveSize();
                if (elevation>max) {
                    max = elevation;
                }
                if (elevation<min) {
                    min = elevation;
                }
                if (caveElevation>caveMax) {
                    caveMax = caveElevation;
                }
                if (caveElevation<caveMin) {
                    caveMin = caveElevation;
                }
                if (caveSize>caveSizeMax) {
                    caveSizeMax = caveSize;
                }
                if (caveSize<caveSizeMin) {
                    caveSizeMin = caveSize;
                }
            }
        }
        maxElevation = max;
        minElevation = min;
        diffElevation = max - min;
        maxCaveElevation = caveMax;
        minCaveElevation = caveMin;
        diffCaveElevation = caveMax - caveMin;
        maxCaveSize = caveSizeMax;
        minCaveSize = caveSizeMin;
        diffCaveSize = caveSizeMax - caveSizeMin;
    }
    
    public String toString() {
        return "Map";
    }
    
}
