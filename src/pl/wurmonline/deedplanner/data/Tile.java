package pl.wurmonline.deedplanner.data;

import com.jogamp.common.nio.Buffers;
import java.nio.FloatBuffer;
import java.util.Arrays;
import javax.media.opengl.GL2;
import org.w3c.dom.*;
import pl.wurmonline.deedplanner.*;
import pl.wurmonline.deedplanner.data.storage.Data;
import pl.wurmonline.deedplanner.util.DeedPlannerRuntimeException;

public class Tile implements DataEntity {

    private final Map map;
    private final int x;
    private final int y;
    
    private int height = 5;
    private Ground ground;
    private final TileEntity[] tiles;
    private final Wall[] horizontalWalls;
    private final Wall[] verticalWalls;
    
    private final float[][] heightValues;
    
    public Tile(Map map, int x, int y, Element tile) {
        this.map = map;
        this.x = x;
        this.y = y;
        height = (int) Float.parseFloat(tile.getAttribute("height"));
        ground = new Ground((Element) tile.getElementsByTagName("ground").item(0));
        tiles = new TileEntity[Constants.FLOORS_LIMIT];
        horizontalWalls = new Wall[Constants.FLOORS_LIMIT];
        verticalWalls = new Wall[Constants.FLOORS_LIMIT];
        
        heightValues = new float[4][4];
        for (int i=0; i<4; i++) {
            for (int i2=0; i2<4; i2++) {
                heightValues[i][i2] = height;
            }
        }
        
        NodeList list = tile.getElementsByTagName("level");
        for (int i=0; i<list.getLength(); i++) {
            Element level = (Element) list.item(i);
            int floor = Integer.parseInt(level.getAttribute("value"));
            NodeList childNodes = level.getElementsByTagName("*");
            for (int i2=0; i2<childNodes.getLength(); i2++) {
                Element entity = (Element) childNodes.item(i2);
                switch (entity.getNodeName().toLowerCase()) {
                    case "floor":
                        tiles[floor] = FloorData.get(entity);
                        break;
                    case "hwall":
                        horizontalWalls[floor] = new Wall(entity);
                        break;
                    case "vwall":
                        verticalWalls[floor] = new Wall(entity);
                        break;
                    case "roof":
                        tiles[floor] = new Roof(entity);
                        break;
                }
            }
        }
    }
    
    public Tile(Map map, int x, int y) {
        this.map = map;
        this.x = x;
        this.y = y;
        
        heightValues = new float[4][4];
        for (int i=0; i<4; i++) {
            for (int i2=0; i2<4; i2++) {
                heightValues[i][i2] = height;
            }
        }
        
        if (!Data.grounds.isEmpty()) {
            ground = new Ground(Data.grounds.get(0));
        }
        tiles = new TileEntity[Constants.FLOORS_LIMIT];
        horizontalWalls = new Wall[Constants.FLOORS_LIMIT];
        verticalWalls = new Wall[Constants.FLOORS_LIMIT];
    }
    
    public Tile(Tile tile) {
        this(tile.map, tile, tile.x, tile.y);
    }
    
    public Tile(Map map, Tile tile, int x, int y) {
        this.map = map;
        this.x = x;
        this.y = y;
        
        this.height = tile.height;
        this.heightValues = tile.heightValues;
        this.ground = tile.ground;
        this.tiles = Arrays.copyOf(tile.tiles, tile.tiles.length);
        this.horizontalWalls = Arrays.copyOf(tile.horizontalWalls, tile.horizontalWalls.length);
        this.verticalWalls = Arrays.copyOf(tile.verticalWalls, tile.verticalWalls.length);
    }
    
    public void render3d(GL2 g, boolean edge) {
        if (!edge) {
            renderGround(g);
            
            renderEntities(g);
        }
    }
    
    private void renderGround(GL2 g) {
        if (Globals.upCamera && Globals.floor>=0 && Globals.floor<3) {
            switch (Globals.floor) {
                case 1:
                    g.glColor3f(0.6f, 0.6f, 0.6f);
                    break;
                case 2:
                    g.glColor3f(0.25f, 0.25f, 0.25f);
                    break;
            }
            g.glColor3f(2, 2, 2);
            ground.render(g, this);
            g.glColor3f(1, 1, 1);
        }
        else if (!Globals.upCamera) {
            ground.render(g, this);
        }
    }
    
    private void renderEntities(GL2 g) {
        for (int i=0; i<Constants.FLOORS_LIMIT; i++) {
            float colorMod = 1;
            if (Globals.upCamera) {
                switch (Globals.floor-i) {
                    case 0:
                        colorMod = 1;
                        break;
                    case 1:
                        colorMod = 0.6f;
                        break;
                    case 2:
                        colorMod = 0.25f;
                        break;
                    default:
                        continue;
                }
            }
            if(tiles[i]!=null) {
                g.glPushMatrix();
                    g.glTranslatef(4, 0, 3*i + getFloorHeight()/Constants.HEIGHT_MOD);
                    g.glColor3f(colorMod, colorMod, colorMod);
                    tiles[i].render(g, this);
                g.glPopMatrix();
            }

            if (verticalWalls[i]!=null) {
                g.glPushMatrix();
                    g.glTranslatef(0, 0, 3*i + getVerticalWallHeight()/Constants.HEIGHT_MOD);
                    g.glRotatef(90, 0, 0, 1);
                    float diff = getVerticalWallHeightDiff()/47f;
                    if (diff<0) {
                        g.glTranslatef(0, 0, -diff*4f);
                    }
                    deform(g, diff);
                    
                    Wall wall = verticalWalls[i];
                    if (Globals.upCamera) {
                        wall.data.color.use(g, colorMod);
                    }
                    else {
                        g.glColor3f(1, 1, 1);
                    }
                    wall.render(g, this);
                    g.glColor3f(1, 1, 1);
                g.glPopMatrix();
            }

            if (horizontalWalls[i]!=null) {
                g.glPushMatrix();
                    g.glTranslatef(0, 0, 3*i + getHorizontalWallHeight()/Constants.HEIGHT_MOD);
                    float diff = getHorizontalWallHeightDiff()/47f;
                    if (diff<0) {
                        g.glTranslatef(0, 0, -diff*4f);
                    }
                    deform(g, diff);
                    Wall wall = horizontalWalls[i];
                    if (Globals.upCamera) {
                        wall.data.color.use(g, colorMod);
                    }
                    else {
                        g.glColor3f(1, 1, 1);
                    }
                    wall.render(g, this);
                    g.glColor3f(1, 1, 1);
                g.glPopMatrix();
            }
        }
        g.glColor3f(1, 1, 1);
    }
    
    private float getFloorHeight() {
        float h00 = getHeight();
        float h10 = getMap().getTile(this, 1, 0)!=null ? getMap().getTile(this, 1, 0).getHeight() : 0;
        float h01 = getMap().getTile(this, 0, 1)!=null ? getMap().getTile(this, 0, 1).getHeight() : 0;
        float h11 = getMap().getTile(this, 1, 1)!=null ? getMap().getTile(this, 1, 1).getHeight() : 0;
        return Math.max(Math.max(h00, h10), Math.max(h01, h11));
    }
    
    private float getVerticalWallHeight() {
        return Math.min(getHeight(), getMap().getTile(this, 0, 1).getHeight());
    }
    
    private float getVerticalWallHeightDiff() {
        return getMap().getTile(this, 0, 1).getHeight() - getHeight();
    }
    
    private float getHorizontalWallHeight() {
        return Math.min(getHeight(), getMap().getTile(this, 1, 0).getHeight());
    }
    
    private float getHorizontalWallHeightDiff() {
        return getMap().getTile(this, 1, 0).getHeight() - getHeight();
    }
    
    private void deform(GL2 g, float scale) {
        FloatBuffer matrix = (FloatBuffer)Buffers.newDirectFloatBuffer(new float[] {1, 0, scale, 0,
                                                                                    0, 1, 0, 0,
                                                                                    0, 0, 1, 0,
                                                                                    0, 0, 0, 1}).flip();
        g.glMultMatrixf(matrix);
    }
    
    public void render2d(GL2 g, boolean edge) {
        
    }
    
    public void serialize(Document doc, Element root) {
        Element tile = doc.createElement("tile");
        tile.setAttribute("x", Integer.toString(x));
        tile.setAttribute("y", Integer.toString(y));
        tile.setAttribute("height", Float.toString(height));
        root.appendChild(tile);
        
        ground.serialize(doc, tile);
        for (int i=0; i<Constants.FLOORS_LIMIT; i++) {
            if (tiles[i]!=null || horizontalWalls[i]!=null || verticalWalls[i]!=null) {
                Element level = doc.createElement("level");
                level.setAttribute("value", Integer.toString(i));
                if (tiles[i]!=null) {
                    tiles[i].serialize(doc, level);
                }
                if (horizontalWalls[i]!=null) {
                    Element hWall = doc.createElement("hWall");
                    horizontalWalls[i].serialize(doc, hWall);
                    level.appendChild(hWall);
                }
                if (verticalWalls[i]!=null) {
                    Element vWall = doc.createElement("vWall");
                    verticalWalls[i].serialize(doc, vWall);
                    level.appendChild(vWall);
                }
                tile.appendChild(level);
            }
            
        }
    }
    
    public Map getMap() {
        return map;
    }
    
    public Ground getGround() {
        return ground;
    }
    
    public void setGround(GroundData data, RoadDirection dir) {
        setGround(data, dir, true);
    }
    
    public void setGround(GroundData data) {
        setGround(data, Globals.roadDirection, true);
    }
    
    void setGround(GroundData data, RoadDirection dir, boolean undo) {
        if (!new Ground(data).equals(ground)) {
            ground.destroy();
            Tile oldTile = new Tile(this);
            if (!data.diagonal) {
                dir = RoadDirection.CENTER;
            }
            ground = new Ground(data, dir);
            for (int i=-1; i<=1; i++) {
                for (int i2=-1; i2<=1; i2++) {
                    Tile t = map.getTile(this, i, i2);
                    if (t!=null) {
                        t.recalculateHeights();
                        t.ground.redraw();
                    }
                }
            }
            
            if (undo) {
                map.addUndo(this, oldTile);
            }
        }
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setHeight(int height) {
        setHeight(height, true);
    }
    
    void setHeight(int height, boolean undo) {
        if (this.height!=height) {
            Tile oldTile = new Tile(this);
            this.height = height;
            recalculateHeights();
            ground.redraw();
            if (x!=0) {
                map.getTile(this, -1, 0).recalculateHeights();
                map.getTile(this, -1, 0).ground.redraw();
            }
            if (y!=0) {
                map.getTile(this, 0, -1).recalculateHeights();
                map.getTile(this, 0, -1).ground.redraw();
            }
            if (x!=0 && y!=0) {
                map.getTile(this, -1, -1).recalculateHeights();
                map.getTile(this, -1, -1).ground.redraw();
            }
            map.recalculateHeight();
            if (undo) {
                map.addUndo(this, oldTile);
            }
        }
    }
    
    public void recalculateHeights() {
        for (int i=0; i<4; i++) {
            for (int i2=0; i2<4; i2++) {
                float xPart = (float)i / 4f;
                float yPart = (float)i2 / 4f;
                heightValues[i][i2] = getHeight(xPart, yPart);
            }
        }
    }
    
    private float getHeight(final float xPart, final float yPart) {
        final float xPartRev = 1f - xPart;
        final float yPartRev = 1f - yPart;
        
        final float h00 = height;
        final float h10 = x!=map.getWidth() ? map.getTile(this, 1, 0).height : 0;
        final float h01 = y!=map.getHeight() ? map.getTile(this, 0, 1).height : 0;
        final float h11 = (x!=map.getWidth() && y!=map.getHeight()) ? map.getTile(this, 1, 1).height : 0;
        
        final float x0 = (h00*xPartRev + h10*xPart);
        final float x1 = (h01*xPartRev + h11*xPart);
        
        return (x0*yPartRev + x1*yPart);
    }
    
    public float getHeight(int xPart, int yPart) {
        if (xPart<4 && yPart<4) {
            return heightValues[xPart][yPart];
        }
        else if (xPart==4 && yPart==4) {
            return map.getTile(this, 1, 1).heightValues[0][0];
        }
        else if (xPart==4) {
            return map.getTile(this, 1, 0).heightValues[0][yPart];
        }
        else if (yPart==4) {
            return map.getTile(this, 0, 1).heightValues[xPart][0];
        }
        else {
            throw new DeedPlannerRuntimeException("Invalid xPart or yPart: must be from 0 to 3");
        }
    }
    
    public int getHeight() {
        return height;
    }
    
    public boolean isFlat() {
        return getHeight()==map.getTile(this, 1, 1).getHeight() &&
               getHeight()==map.getTile(this, 1, 0).getHeight() &&
               getHeight()==map.getTile(this, 0, 1).getHeight();
    }
    
    public void setTileContent(TileEntity entity, int level) {
        setTileContent(entity, level, true);
    }
    
    void setTileContent(TileEntity entity, int level, boolean undo) {
        if (!isFlat()) {
            return;
        }
        
        if (entity instanceof Roof) {
            for (TileEntity e : tiles) {
                if (e instanceof Roof) {
                    return;
                }
            }
        }
        
        if (entity!=tiles[level]) {
            Tile oldTile = new Tile(this);
            tiles[level] = entity;

            if (undo) {
                map.addUndo(this, oldTile);
            }
        }
    }
    
    public TileEntity getTileContent(int level) {
        return tiles[level];
    }
    
    public void setHorizontalWall(WallData wall, int level) {
        setHorizontalWall(wall, level, true);
    }
    
    void setHorizontalWall(WallData wall, int level, boolean undo) {
        if (wall!=null && wall.houseWall) {
            if (!(isFlat() || map.getTile(this, 0, -1).isFlat())) {
                return;
            }
        }
        
        if (!(new Wall(wall).equals(horizontalWalls[level]))) {
            Tile oldTile = new Tile(this);
            if (wall!=null) {
                horizontalWalls[level] = new Wall(wall);
            }
            else {
                horizontalWalls[level] = null;
            }

            if (undo) {
                map.addUndo(this, oldTile);
            }
        }
    }
    
    public Wall getHorizontalWall(int level) {
        return horizontalWalls[level];
    }
    
    public void setVerticalWall(WallData wall, int level) {
        setVerticalWall(wall, level, true);
    }
    
    void setVerticalWall(WallData wall, int level, boolean undo) {
        if (wall!=null && wall.houseWall) {
            if (!(isFlat() || map.getTile(this, -1, 0).isFlat())) {
                return;
            }
        }
        
        if (!(new Wall(wall).equals(verticalWalls[level]))) {
            Tile oldTile = new Tile(this);
            if (wall!=null) {
                verticalWalls[level] = new Wall(wall);
            }
            else {
                verticalWalls[level] = null;
            }

            if (undo) {
                map.addUndo(this, oldTile);
            }
        }
    }
    
    public Wall getVerticalWall(int level) {
        return verticalWalls[level];
    }
    
}
