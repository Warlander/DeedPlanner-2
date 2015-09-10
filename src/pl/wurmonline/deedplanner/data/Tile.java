package pl.wurmonline.deedplanner.data;

import java.util.*;
import java.util.Map.Entry;
import javax.media.opengl.GL2;
import org.w3c.dom.*;
import pl.wurmonline.deedplanner.*;
import pl.wurmonline.deedplanner.data.storage.Data;
import pl.wurmonline.deedplanner.logic.Tab;
import pl.wurmonline.deedplanner.logic.TileFragment;
import pl.wurmonline.deedplanner.util.*;

public final class Tile implements XMLSerializable {

    private static final float[] deformMatrix = new float[] {1, 0, 0, 0,
                                                             0, 1, 0, 0,
                                                             0, 0, 1, 0,
                                                             0, 0, 0, 1};
    
    private final Map map;
    private final int x;
    private final int y;
    
    private int height = 0;
    private Ground ground;
    private final HashMap<EntityData, TileEntity> entities;
    private Label label;
    private BridgePart bridgePart;
    
    private int caveHeight = 5;
    private int caveSize = 30;
    private CaveData cave = Data.caves.get("sw");
    private Label caveLabel;
    
    public Tile(Map map, int x, int y, Element tile) {
        this.map = map;
        this.x = x;
        this.y = y;
        height = (int) Float.parseFloat(tile.getAttribute("height"));
        if (!tile.getAttribute("caveHeight").equals("")) {
            caveHeight = (int) Float.parseFloat(tile.getAttribute("caveHeight"));
        }
        if (!tile.getAttribute("caveSize").equals("")) {
            caveSize = (int) Float.parseFloat(tile.getAttribute("caveSize"));
        }
        ground = new Ground((Element) tile.getElementsByTagName("ground").item(0));
        if (tile.getElementsByTagName("cave").getLength()!=0) {
            cave = CaveData.get((Element) tile.getElementsByTagName("cave").item(0));
        }
        
        NodeList labels = tile.getElementsByTagName("label");
        if (labels.getLength()!=0) {
            label = new Label((Element) labels.item(0));
        }
        NodeList caveLabels = tile.getElementsByTagName("caveLabel");
        if (caveLabels.getLength()!=0) {
            caveLabel = new Label((Element) caveLabels.item(0));
        }
        
        entities = new HashMap<>();
        
        NodeList list = tile.getElementsByTagName("level");
        for (int i=0; i<list.getLength(); i++) {
            Element level = (Element) list.item(i);
            int floor = Integer.parseInt(level.getAttribute("value"));
            NodeList childNodes = level.getElementsByTagName("*");
            for (int i2=0; i2<childNodes.getLength(); i2++) {
                Element entity = (Element) childNodes.item(i2);
                switch (entity.getNodeName().toLowerCase()) {
                    case "floor":
                        entities.put(new EntityData(floor, EntityType.FLOORROOF), new Floor(entity));
                        break;
                    case "hwall":
                        Wall hwall = new Wall(entity);
                        if (hwall.data.houseWall) {
                            entities.put(new EntityData(floor, EntityType.HWALL), hwall);
                        }
                        else {
                            entities.put(new EntityData(floor, EntityType.HFENCE), hwall);
                        }
                        break;
                    case "vwall":
                        Wall vwall = new Wall(entity);
                        if (vwall.data.houseWall) {
                            entities.put(new EntityData(floor, EntityType.VWALL), vwall);
                        }
                        else {
                            entities.put(new EntityData(floor, EntityType.VFENCE), vwall);
                        }
                        break;
                    case "hborder":
                        entities.put(new EntityData(0, EntityType.HBORDER), BorderData.get(entity));
                        break;
                    case "vborder":
                        entities.put(new EntityData(0, EntityType.VBORDER), BorderData.get(entity));
                        break;
                    case "roof":
                        entities.put(new EntityData(floor, EntityType.FLOORROOF), new Roof(entity));
                        break;
                    case "object":
                        ObjectLocation loc = ObjectLocation.parse(entity.getAttribute("position"));
                        entities.put(new ObjectEntityData(floor, loc), new GameObject(entity));
                        break;
                    case "cave":
                        cave = CaveData.get(entity);
                        break;
                }
            }
        }
    }
    
    public Tile(Map map, int x, int y) {
        this.map = map;
        this.x = x;
        this.y = y;
        
        if (!Data.grounds.isEmpty()) {
            ground = new Ground(Data.grounds.get("gr"));
        }
        entities = new HashMap<>();
    }
    
    public Tile(Tile tile) {
        this(tile.map, tile, tile.x, tile.y);
    }
    
    public Tile(Map map, Tile tile, int x, int y) {
        this.map = map;
        this.x = x;
        this.y = y;
        
        this.height = tile.height;
        this.ground = tile.ground;
        this.cave = tile.cave;
        this.label = tile.label;
        HashMap<EntityData, TileEntity> entities = new HashMap<>();
        for (Entry<EntityData, TileEntity> entrySet : tile.entities.entrySet()) {
            EntityData key = entrySet.getKey();
            TileEntity value = entrySet.getValue();
            entities.put(key, value.deepCopy());
        }
        this.entities = new HashMap(tile.entities);
    }
    
    public void render3d(GL2 g, boolean edge) {
        if (!edge) {
            if (Globals.floor>=0) {
                renderWorld(g);
            }
            else {
                renderUnderground(g);
            }
        }
    }
    
    private void renderWorld(GL2 g) {
        renderGround(g);
        
        if (bridgePart != null) {
            bridgePart.render(g, this);
        }
        
        renderEntities(g);
    }
    
    private void renderGround(GL2 g) {
        if ((Globals.upCamera && Globals.floor>=0 && Globals.floor<3) || !Globals.upCamera) {
            if (Globals.upCamera && Globals.floor>=0 && Globals.floor<3) {
                switch (Globals.floor) {
                    case 0:
                        g.glColor3f(1, 1, 1);
                        break;
                    case 1:
                        g.glColor3f(0.6f, 0.6f, 0.6f);
                        break;
                    case 2:
                        g.glColor3f(0.25f, 0.25f, 0.25f);
                        break;
                }
            }
            ground.render(g, this);
        }
    }
    
    private void renderEntities(GL2 g) {
        for (Entry<EntityData, TileEntity> e : entities.entrySet()) {
            EntityData key = e.getKey();
            final int floor = key.getFloor();
            float colorMod = 1;
            if (Globals.upCamera) {
                switch (Globals.floor-floor) {
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
            TileEntity entity = e.getValue();
            g.glPushMatrix();
                switch (key.getType()) {
                    case FLOORROOF:
                        g.glTranslatef(4, 0, 3*floor + getFloorHeight()/Constants.HEIGHT_MOD);
                        g.glColor3f(colorMod, colorMod, colorMod);
                        entity.render(g, this);
                        break;
                    case VWALL: case VFENCE:
                        g.glTranslatef(0, 0, 3*floor + getVerticalWallHeight()/Constants.HEIGHT_MOD);
                        g.glRotatef(90, 0, 0, 1);
                        float vdiff = getVerticalWallHeightDiff()/47f;
                        if (vdiff<0) {
                            g.glTranslatef(0, 0, -vdiff*4f);
                        }
                        deform(g, vdiff);

                        Wall vwall = (Wall) entity;
                        if (Globals.upCamera) {
                            vwall.data.color.use(g, colorMod);
                        }
                        else {
                            g.glColor3f(1, 1, 1);
                        }
                        vwall.render(g, this);
                        g.glColor3f(1, 1, 1);
                        break;
                    case HWALL: case HFENCE:
                        g.glTranslatef(0, 0, 3*floor + getHorizontalWallHeight()/Constants.HEIGHT_MOD);
                        float hdiff = getHorizontalWallHeightDiff()/47f;
                        if (hdiff<0) {
                            g.glTranslatef(0, 0, -hdiff*4f);
                        }
                        deform(g, hdiff);
                        Wall hwall = (Wall) entity;
                        if (Globals.upCamera) {
                            hwall.data.color.use(g, colorMod);
                        }
                        else {
                            g.glColor3f(1, 1, 1);
                        }
                        hwall.render(g, this);
                        g.glColor3f(1, 1, 1);
                        break;
                    case OBJECT:
                        ObjectEntityData objData = (ObjectEntityData) key;
                        ObjectLocation loc = objData.getLocation();
                        GameObject obj = (GameObject) entity;
                        g.glColor3f(colorMod, colorMod, colorMod);
                        g.glTranslatef(loc.getHorizontalAlign(), loc.getVerticalAlign(), 3*floor + getHeight(loc.getHorizontalAlign()/4f, loc.getVerticalAlign()/4f)/Constants.HEIGHT_MOD);
                        obj.render(g, this);
                        break;
                }
            g.glPopMatrix();
            g.glColor3f(1, 1, 1);
        }
    }
    
    private void renderUnderground(GL2 g) {
        cave.render(g, this);
    }
    
    public void render2d(GL2 g) {
        for (Entry<EntityData, TileEntity> e : entities.entrySet()) {
            EntityData key = e.getKey();
            TileEntity entity = e.getValue();
            g.glPushMatrix();
                switch (key.getType()) {
                    case VBORDER:
                        g.glRotatef(90, 0, 0, 1);
                        BorderData vBorder = (BorderData) entity;
                        if (Globals.upCamera) {
                            vBorder.render(g, this);
                        }
                        g.glColor3f(1, 1, 1);
                        break;
                    case HBORDER:
                        BorderData hBorder = (BorderData) entity;
                        if (Globals.upCamera) {
                            hBorder.render(g, this);
                        }
                        g.glColor3f(1, 1, 1);
                        break;
                }
            g.glPopMatrix();
            g.glColor3f(1, 1, 1);
        }
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
        deformMatrix[2] = scale;
        g.glMultMatrixf(deformMatrix, 0);
    }
    
    public void renderSelection(GL2 g) {
        if ((Globals.tab == Tab.labels || Globals.tab == Tab.height)) {
            g.glDisable(GL2.GL_ALPHA_TEST);
            g.glEnable(GL2.GL_BLEND);
            g.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
            double color = System.currentTimeMillis();
            color%=2000d; color-=1000d; color = Math.abs(color); color/=1000d;
            g.glColor4d(1, 1, 0, 0.1d+0.2d*color);
            g.glBegin(GL2.GL_QUADS);
                g.glVertex2f(0, 0);
                g.glVertex2f(0, 4);
                g.glVertex2f(4, 4);
                g.glVertex2f(4, 0);
            g.glEnd();
            g.glColor4f(1, 1, 1, 1);
            g.glDisable(GL2.GL_BLEND);
            g.glEnable(GL2.GL_ALPHA_TEST);
        }
    }
    
    public void renderLabel(GL2 g) {
        if (label!=null) {
            label.render(g, this);
        }
    }
    
    public void renderCaveLabel(GL2 g) {
        if (caveLabel!=null) {
            caveLabel.render(g, this);
        }
    }
    
    public void serialize(Document doc, Element root) {
        Element tile = doc.createElement("tile");
        tile.setAttribute("x", Integer.toString(x));
        tile.setAttribute("y", Integer.toString(y));
        tile.setAttribute("height", Float.toString(height));
        tile.setAttribute("caveHeight", Float.toString(caveHeight));
        tile.setAttribute("caveSize", Float.toString(caveSize));
        root.appendChild(tile);
        
        ground.serialize(doc, tile);
        cave.serialize(doc, tile);
        if (label!=null) {
            label.serialize(doc, tile, false);
        }
        if (caveLabel!=null) {
            caveLabel.serialize(doc, tile, true);
        }
        
        final HashMap<Integer, Element> levels = new HashMap<>();
        
        for (Entry<EntityData, TileEntity> e : entities.entrySet()) {
            final EntityData key = e.getKey();
            final TileEntity entity = e.getValue();
            final int floor = key.getFloor();
            
            Element level = levels.get(floor);
            if (level==null) {
                level = doc.createElement("level");
                level.setAttribute("value", Integer.toString(key.getFloor()));
                levels.put(key.getFloor(), level);
                tile.appendChild(level);
            }
            
            switch (key.getType()) {
                case FLOORROOF:
                    entity.serialize(doc, level);
                    break;
                case HWALL: case HFENCE:
                    Element hWall = doc.createElement("hWall");
                    entity.serialize(doc, hWall);
                    level.appendChild(hWall);
                    break;
                case VWALL: case VFENCE:
                    Element vWall = doc.createElement("vWall");
                    entity.serialize(doc, vWall);
                    level.appendChild(vWall);
                    break;
                case HBORDER:
                    Element hDeco = doc.createElement("hBorder");
                    entity.serialize(doc, hDeco);
                    level.appendChild(hDeco);
                    break;
                case VBORDER:
                    Element vDeco = doc.createElement("vBorder");
                    entity.serialize(doc, vDeco);
                    level.appendChild(vDeco);
                    break;
                case OBJECT:
                    ObjectEntityData objectData = (ObjectEntityData) key;
                    Element objectElement = doc.createElement("object");
                    objectElement.setAttribute("position", objectData.getLocation().toString());
                    entity.serialize(doc, objectElement);
                    level.appendChild(objectElement);
                    break;
            }
        }
    }
    
    protected int getEntityFloor(TileEntity entity) {
        for (Entry<EntityData, TileEntity> entry : entities.entrySet()) {
            if (entry.getValue() == entity) {
                return entry.getKey().getFloor();
            }
        }
        
        throw new DeedPlannerRuntimeException("Cannot find entity: "+entity);
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
            Tile oldTile = new Tile(this);
            if (!data.diagonal) {
                dir = RoadDirection.CENTER;
            }
            ground = new Ground(data, dir);
            
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
        if (Globals.floor>=0) {
            setHeight(height, true);
        }
        else if (!Globals.editSize) {
            setCaveHeight(height, true);
        }
        else {
            setCaveSize(height, true);
        }
    }
    
    void setHeight(int height, boolean undo) {
        if (this.height!=height) {
            Tile oldTile = new Tile(this);
            this.height = height;
            map.recalculateHeight();
            if (undo) {
                map.addUndo(this, oldTile);
            }
        }
    }
    
    public float getHeight(final float xPart, final float yPart) {
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
    
    public int getHeight() {
        return height;
    }
    
    void setCaveHeight(int height, boolean undo) {
        if (this.caveHeight!=height) {
            Tile oldTile = new Tile(this);
            this.caveHeight = height;
            map.recalculateHeight();
            if (undo) {
                map.addUndo(this, oldTile);
            }
        }
    }
    
    public int getCaveHeight() {
        return caveHeight;
    }
    
    void setCaveSize(int size, boolean undo) {
        if (size<30 || size>300) {
            return;
        }
        if (this.caveSize!=size) {
            Tile oldTile = new Tile(this);
            this.caveSize = size;
            map.recalculateHeight();
            if (undo) {
                map.addUndo(this, oldTile);
            }
        }
    }
    
    public int getCaveSize() {
        return caveSize;
    }
    
    public void setCaveEntity(CaveData entity) {
        setCaveEntity(entity, true);
    }
    
    void setCaveEntity(CaveData entity, boolean undo) {
        if (this.cave != entity) {
            Tile oldTile = new Tile(this);
            this.cave = entity;
            if (undo) {
                map.addUndo(this, oldTile);
            }
        }
    }
    
    public CaveData getCaveEntity() {
        return cave;
    }
    
    public boolean isFlat() {
        return map.getTile(this, 1, 1)!=null && getHeight()==map.getTile(this, 1, 1).getHeight() &&
               map.getTile(this, 1, 0)!=null && getHeight()==map.getTile(this, 1, 0).getHeight() &&
               map.getTile(this, 0, 1)!=null && getHeight()==map.getTile(this, 0, 1).getHeight();
    }
    
    public void setTileContent(TileEntity entity, int level) {
        setTileContent(entity, level, true);
    }
    
    void setTileContent(TileEntity entity, int level, boolean undo) {
        if (!isFlat()) {
            return;
        }
        
        if (entity instanceof Roof) {
            for (TileEntity e : entities.values()) {
                if (e instanceof Roof) {
                    return;
                }
            }
        }
        
        final EntityData entityData = new EntityData(level, EntityType.FLOORROOF);
        if (entity!=entities.get(entityData)) {
            Tile oldTile = new Tile(this);
            
            if (entity!=null) {
                entities.put(entityData, entity);
            }
            else {
                entities.remove(entityData);
            }

            if (undo) {
                map.addUndo(this, oldTile);
            }
        }
    }
    
    public TileEntity getTileContent(int level) {
        return entities.get(new EntityData(level, EntityType.FLOORROOF));
    }
    
    public void setHorizontalWall(WallData wall, int level) {
        setHorizontalWall(wall, level, true);
    }
    
    void setHorizontalWall(WallData wall, int level, boolean undo) {
        if (wall!=null && wall.houseWall) {
            if (!(isFlat() || (map.getTile(this, 0, -1)!=null && map.getTile(this, 0, -1).isFlat()))) {
                return;
            }
        }
        
        final EntityData entityData;
        if (wall!=null && wall.houseWall) {
            entityData = new EntityData(level, EntityType.HWALL);
        }
        else if (wall!=null) {
            entityData = new EntityData(level, EntityType.HFENCE);
        }
        else {
            entityData = null;
        }
        
        final EntityData wallEntity = new EntityData(level, EntityType.HWALL);
        final EntityData fenceEntity = new EntityData(level, EntityType.HFENCE);
        final Wall currentWall = (Wall) entities.get(wallEntity);
        final Wall currentFence = (Wall) entities.get(fenceEntity);
        
        boolean reversed;
        if (wall!=null && wall.houseWall) {
            if (Globals.autoReverseWall) {
                reversed = getTileContent(level)!=null && map.getTile(this, 0, -1).getTileContent(level)==null;
                if (reversed == false) {
                    reversed = Globals.reverseWall;
                }
            }
            else {
                reversed = Globals.reverseWall;
            }
        }
        else {
            reversed = false;
        }
        
        if (!(new Wall(wall, reversed).equals(entities.get(entityData)))) {
            Tile oldTile = new Tile(this);
            if (wall!=null) {
                entities.put(entityData, new Wall(wall, reversed));
                if (wall.houseWall && !(wall.arch && currentFence!=null && currentFence.data.archBuildable)) {
                    entities.remove(fenceEntity);
                }
                else if (!wall.houseWall && !(wall.archBuildable && currentWall!=null && currentWall.data.arch)) {
                    entities.remove(wallEntity);
                }
            }
            else {
                entities.remove(wallEntity);
                entities.remove(fenceEntity);
            }

            if (undo) {
                map.addUndo(this, oldTile);
            }
        }
    }
    
    public Wall getHorizontalWall(int level) {
        return (Wall) entities.get(new EntityData(level, EntityType.HWALL));
    }
    
    public Wall getHorizontalFence(int level) {
        return (Wall) entities.get(new EntityData(level, EntityType.HFENCE));
    }
    
    public void clearHorizontalWalls() {
        for (int i = 0; i < Constants.FLOORS_LIMIT; i++) {
            clearHorizontalWalls(i);
        }
    }
    
    public void clearHorizontalWalls(int level) {
        entities.remove(new EntityData(level, EntityType.HWALL));
        entities.remove(new EntityData(level, EntityType.HFENCE));
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
        
        final EntityData entityData;
        if (wall!=null && wall.houseWall) {
            entityData = new EntityData(level, EntityType.VWALL);
        }
        else if (wall!=null) {
            entityData = new EntityData(level, EntityType.VFENCE);
        }
        else {
            entityData = null;
        }
        
        final EntityData wallEntity = new EntityData(level, EntityType.VWALL);
        final EntityData fenceEntity = new EntityData(level, EntityType.VFENCE);
        final Wall currentWall = (Wall) entities.get(wallEntity);
        final Wall currentFence = (Wall) entities.get(fenceEntity);
        
        boolean reversed;
        if (wall!=null && wall.houseWall) {
            if (Globals.autoReverseWall) {
                reversed = getTileContent(level)==null && map.getTile(this, -1, 0).getTileContent(level)!=null;
                if (reversed == false) {
                    reversed = Globals.reverseWall;
                }
            }
            else {
                reversed = Globals.reverseWall;
            }
        }
        else {
            reversed = false;
        }
        
        if (!(new Wall(wall, reversed).equals(entities.get(entityData)))) {
            Tile oldTile = new Tile(this);
            if (wall!=null) {
                entities.put(entityData, new Wall(wall, reversed));
                if (wall.houseWall && !(wall.arch && currentFence!=null && currentFence.data.archBuildable)) {
                    entities.remove(fenceEntity);
                }
                else if (!wall.houseWall && !(wall.archBuildable && currentWall!=null && currentWall.data.arch)) {
                    entities.remove(wallEntity);
                }
            }
            else {
                entities.remove(wallEntity);
                entities.remove(fenceEntity);
            }

            if (undo) {
                map.addUndo(this, oldTile);
            }
        }
    }
    
    public Wall getVerticalWall(int level) {
        return (Wall) entities.get(new EntityData(level, EntityType.VWALL));
    }
    
    public Wall getVerticalFence(int level) {
        return (Wall) entities.get(new EntityData(level, EntityType.VFENCE));
    }
    
    public void clearVerticalWalls() {
        for (int i = 0; i < Constants.FLOORS_LIMIT; i++) {
            clearVerticalWalls(i);
        }
    }
    
    public void clearVerticalWalls(int level) {
        entities.remove(new EntityData(level, EntityType.VWALL));
        entities.remove(new EntityData(level, EntityType.VFENCE));
    }
    
    public void setHorizontalBorder(BorderData border) {
        setHorizontalBorder(border, true);
    }
    
    void setHorizontalBorder(BorderData border, boolean undo) {
        final EntityData entityData = new EntityData(0, EntityType.HBORDER);
        if (border!=entities.get(entityData)) {
            Tile oldTile = new Tile(this);
            
            if (border!=null) {
                entities.put(entityData, border);
            }
            else {
                entities.remove(entityData);
            }

            if (undo) {
                map.addUndo(this, oldTile);
            }
        }
    }
    
    public BorderData getHorizontalBorder() {
        return (BorderData) entities.get(new EntityData(0, EntityType.HBORDER));
    }
    
    public void setVerticalBorder(BorderData border) {
        setVerticalBorder(border, true);
    }
    
    void setVerticalBorder(BorderData border, boolean undo) {
        final EntityData entityData = new EntityData(0, EntityType.VBORDER);
        if (border!=entities.get(entityData)) {
            Tile oldTile = new Tile(this);
            
            if (border!=null) {
                entities.put(entityData, border);
            }
            else {
                entities.remove(entityData);
            }

            if (undo) {
                map.addUndo(this, oldTile);
            }
        }
    }
    
    public BorderData getVerticalBorder() {
        return (BorderData) entities.get(new EntityData(0, EntityType.VBORDER));
    }
    
    public void setLabel(Label label) {
        setLabel(label, true);
    }
    
    void setLabel(Label label, boolean undo) {
        Tile oldTile = new Tile(this);
        this.label = label;
        if (undo) {
            map.addUndo(this, oldTile);
        }
    }
    
    public Label getLabel() {
        return label;
    }
    
    public void setCaveLabel(Label caveLabel) {
        setCaveLabel(caveLabel, true);
    }
    
    void setCaveLabel(Label caveLabel, boolean undo) {
        Tile oldTile = new Tile(this);
        this.caveLabel = caveLabel;
        if (undo) {
            map.addUndo(this, oldTile);
        }
    }
    
    public Label getCaveLabel() {
        return caveLabel;
    }
    
    public void setGameObject(GameObjectData data, ObjectLocation location, int floor) {
        setGameObject(data, location, floor, true);
    }
    
    void setGameObject(GameObjectData data, ObjectLocation location, int floor, boolean undo) {
        Tile oldTile = new Tile(this);
        if (data!=null) {
            entities.put(new ObjectEntityData(floor, location), new GameObject(data));
        }
        else {
            for (ObjectLocation loc : ObjectLocation.values()) {
                entities.remove(new ObjectEntityData(floor, loc));
            }
        }
        if (undo) {
            map.addUndo(this, oldTile);
        }
    }
    
    public GameObject getGameObject(int level, ObjectLocation location) {
        //assumption - ObjectEntityData key always have GameObject value.
        return (GameObject) entities.get(new ObjectEntityData(level, location));
    }
    
    public void setBridgePart(BridgePart bridgePart) {
        this.bridgePart = bridgePart;
    }
    
    public Materials getMaterials() {
        return getMaterials(false, false);
    }
    
    public Materials getMaterials(boolean withRight, boolean withTop) {
        Materials materials = new Materials();
        entities.values().stream().forEach((entity) -> {
            materials.put(entity.getMaterials());
        });
        
        if (bridgePart != null) {
            materials.put(bridgePart.getMaterials());
        }
        
        if (withRight) {
            for (int i = 0; i<Constants.FLOORS_LIMIT; i++) {
                Wall wall = map.getTile(this, 1, 0).getVerticalWall(i);
                Wall fence = map.getTile(this, 1, 0).getVerticalFence(i);
                if (wall!=null) {
                    materials.put(wall.getMaterials());
                }
                if (fence!=null) {
                    materials.put(fence.getMaterials());
                }
            }
        }
        if (withTop) {
            for (int i = 0; i<Constants.FLOORS_LIMIT; i++) {
                Wall wall = map.getTile(this, 0, 1).getHorizontalWall(i);
                Wall fence = map.getTile(this, 0, 1).getHorizontalFence(i);
                if (wall!=null) {
                    materials.put(wall.getMaterials());
                }
                if (fence!=null) {
                    materials.put(fence.getMaterials());
                }
            }
        }
        return materials;
    }
    
    public boolean isPassable(TileBorder border) {
        switch (border) {
            case SOUTH:
                return getHorizontalWall(0)==null;
            case NORTH:
                return map.getTile(this, 0, 1)!=null &&
                       map.getTile(this, 0, 1).getHorizontalWall(0)==null;
            case WEST:
                return getVerticalWall(0)==null;
            case EAST:
                return map.getTile(this, 1, 0)!=null &&
                       map.getTile(this, 1, 0).getVerticalWall(0)==null;
            default:
                return false;
        }
    }
    
    public String toString() {
        return "Tile: ("+x+"; "+y+")";
    }
    
    public Tile[] getAffectedTiles(TileFragment frag) {
        final Tile[] tiles;
        if (null != frag) switch (frag) {
            case CENTER:
                tiles = new Tile[4];
                tiles[0] = this;
                tiles[1] = this.getMap().getTile(this, 1, 0);
                tiles[2] = this.getMap().getTile(this, 1, 1);
                tiles[3] = this.getMap().getTile(this, 0, 1);
                return tiles;
            case S:
                tiles = new Tile[2];
                tiles[0] = this;
                tiles[1] = this.getMap().getTile(this, 1, 0);
                return tiles;
            case N:
                tiles = new Tile[2];
                tiles[0] = this.getMap().getTile(this, 0, 1);
                tiles[1] = this.getMap().getTile(this, 1, 1);
                return tiles;
            case W:
                tiles = new Tile[2];
                tiles[0] = this;
                tiles[1] = this.getMap().getTile(this, 0, 1);
                return tiles;
            case E:
                tiles = new Tile[2];
                tiles[0] = this.getMap().getTile(this, 1, 0);
                tiles[1] = this.getMap().getTile(this, 1, 1);
                return tiles;
            case SW:
                return new Tile[]{this};
            case SE:
                return new Tile[]{this.getMap().getTile(this, 1, 0)};
            case NW:
                return new Tile[]{this.getMap().getTile(this, 0, 1)};
            case NE:
                return new Tile[]{this.getMap().getTile(this, 1, 1)};
            default:
                throw new DeedPlannerRuntimeException("Illegal argument");
        }
        return null;
    }
}
