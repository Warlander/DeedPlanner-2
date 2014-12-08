package pl.wurmonline.deedplanner.data;

import com.jogamp.common.nio.Buffers;
import java.nio.FloatBuffer;
import java.util.*;
import java.util.Map.Entry;
import javax.media.opengl.GL2;
import org.w3c.dom.*;
import pl.wurmonline.deedplanner.*;
import pl.wurmonline.deedplanner.data.storage.Data;
import pl.wurmonline.deedplanner.logic.Tab;
import pl.wurmonline.deedplanner.logic.TileSelection;
import pl.wurmonline.deedplanner.util.*;

public final class Tile implements XMLSerializable {

    private final Map map;
    private final int x;
    private final int y;
    
    private int height = 5;
    private Ground ground;
    private final HashMap<EntityData, TileEntity> entities;
    private Label label;
    private Label caveLabel;
    
    private final float[][] heightValues;
    
    public Tile(Map map, int x, int y, Element tile) {
        this.map = map;
        this.x = x;
        this.y = y;
        height = (int) Float.parseFloat(tile.getAttribute("height"));
        ground = new Ground((Element) tile.getElementsByTagName("ground").item(0));
        
        NodeList labels = tile.getElementsByTagName("label");
        if (labels.getLength()!=0) {
            label = new Label((Element) labels.item(0));
        }
        
        entities = new HashMap<>();
        
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
                        entities.put(new EntityData(floor, EntityType.FLOORROOF), FloorData.get(entity));
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
                    case "roof":
                        entities.put(new EntityData(floor, EntityType.FLOORROOF), new Roof(entity));
                        break;
                    case "object":
                        ObjectLocation loc = ObjectLocation.parse(entity.getAttribute("position"));
                        entities.put(new ObjectEntityData(floor, loc), new GameObject(entity));
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
        this.heightValues = tile.heightValues;
        this.ground = tile.ground;
        this.label = tile.label;
        this.entities = new HashMap(tile.entities);
    }
    
    public void render3d(GL2 g, boolean edge) {
        if (!edge) {
            renderGround(g);
            
            renderEntities(g);
        }
    }
    
    private void renderGround(GL2 g) {
        if ((Globals.upCamera && Globals.floor>=0 && Globals.floor<3) || !Globals.upCamera) {
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
        if (Globals.upCamera) {
            if ((Globals.tab == Tab.labels || Globals.tab == Tab.height) && TileSelection.getSelectedTile()==this) {
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
            }
            if (label!=null) {
                label.render(g, this);
            }
        }
    }
    
    public void serialize(Document doc, Element root) {
        Element tile = doc.createElement("tile");
        tile.setAttribute("x", Integer.toString(x));
        tile.setAttribute("y", Integer.toString(y));
        tile.setAttribute("height", Float.toString(height));
        root.appendChild(tile);
        
        ground.serialize(doc, tile);
        if (label!=null) {
            label.serialize(doc, tile);
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
        
        if (!(new Wall(wall).equals(entities.get(entityData)))) {
            Tile oldTile = new Tile(this);
            if (wall!=null) {
                entities.put(entityData, new Wall(wall));
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
        
        if (!(new Wall(wall).equals(entities.get(entityData)))) {
            Tile oldTile = new Tile(this);
            if (wall!=null) {
                entities.put(entityData, new Wall(wall));
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
            entities.remove(new ObjectEntityData(floor, location));
        }
        if (undo) {
            map.addUndo(this, oldTile);
        }
    }
    
    public GameObject getGameObject(int level, ObjectLocation location) {
        //assumption - ObjectEntityData key always have GameObject value.
        return (GameObject) entities.get(new ObjectEntityData(level, location));
    }
    
    public Materials getMaterials() {
        return getMaterials(false, false);
    }
    
    public Materials getMaterials(boolean withRight, boolean withTop) {
        Materials materials = new Materials();
        entities.values().stream().forEach((entity) -> {
            materials.put(entity.getMaterials());
        });
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
    
}
