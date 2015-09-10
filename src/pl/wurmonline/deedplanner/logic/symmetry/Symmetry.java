package pl.wurmonline.deedplanner.logic.symmetry;

import javax.media.opengl.GL2;
import pl.wurmonline.deedplanner.Globals;
import pl.wurmonline.deedplanner.data.BorderData;
import pl.wurmonline.deedplanner.data.CaveData;
import pl.wurmonline.deedplanner.data.Floor;
import pl.wurmonline.deedplanner.data.FloorData;
import pl.wurmonline.deedplanner.data.FloorOrientation;
import pl.wurmonline.deedplanner.data.GameObject;
import pl.wurmonline.deedplanner.data.GameObjectData;
import pl.wurmonline.deedplanner.data.GroundData;
import pl.wurmonline.deedplanner.data.ObjectLocation;
import pl.wurmonline.deedplanner.data.RoadDirection;
import static pl.wurmonline.deedplanner.data.RoadDirection.*;
import pl.wurmonline.deedplanner.data.Roof;
import pl.wurmonline.deedplanner.data.RoofData;
import pl.wurmonline.deedplanner.data.Tile;
import pl.wurmonline.deedplanner.data.TileEntity;
import pl.wurmonline.deedplanner.data.WallData;
import pl.wurmonline.deedplanner.logic.TileFragment;
import pl.wurmonline.deedplanner.util.DeedPlannerRuntimeException;

/**
 * Symmetry
 * Allows for locking a line or lines of symmetry to mirror work done.
 * @since 15-09-06
 * @author Jonathan Walker (Keenan)
 */
public class Symmetry {
    private final int x;
    private final int y;
    private final SymmetryType type;
    private final SymmetryDirection direction;
        
    /**
     * Empty constructor for when there is no symmetry lock.
     */
    public Symmetry() {
        this.x = -1;
        this.y = -1;
        this.type = SymmetryType.NONE;
        this.direction = SymmetryDirection.CENTER;
    }
    
    /**
     * Main constructor for border and corner locks.
     * 
     * @param cX X-axis coordinate for the lock, or -1 if none.
     * @param cY Y-axis coordinate for the lock, or -1 if none.
     * @param dir SymmetryDirection of the border or corner of the lock.
     */
    public Symmetry(int cX, int cY, SymmetryDirection dir) {
        
        switch(dir) {
            case SE:
            case E:
                cX += 1;
                break;
            case NW:
            case N:
                cY += 1;
                break;
            case NE:
                cX += 1;
                cY += 1;
                break;
        }
        this.x = cX;
        this.y = cY;
        this.type = SymmetryType.getType(dir);
        this.direction = dir;
    }
    
    /**
     * Constructor for tile locks.
     * 
     * @param x X-axis coordinate of the tile lock.
     * @param y Y-axis coordinate of the tile lock.
     */
    public Symmetry(int x, int y) {
        this.x = x;
        this.y = y;
        this.type = SymmetryType.TILE;
        this.direction = SymmetryDirection.CENTER;
    }
    
    /**
     * Returns the X-axis coordinate for the symmetry lock
     * @return X-axis coordinate
     */
    public int getX() {
        return this.x;
    }
    
    /**
     * Returns the Y-axis coordinate for the symmetry lock
     * @return Y-axis coordinate
     */
    public int getY() {
        return this.y;
    }
    
    /**
     * Returns the type of lock.
     * @return SymmetryType
     */
    public SymmetryType getType() {
        return type;
    }
    
    /**
     * Returns the direction of the lock
     * @return SymmetryDirection
     */
    public SymmetryDirection getDirection() {
        return direction;
    }
    
    private int mirrorX(int curX) {
        int mX = (getX() - curX) + getX();
        switch(direction) {
            case E:
            case SE:
            case NE:
            case SW:
            case NW:
            case W:
                mX -= 1;
                break;
        }
        return mX;
    }
    
    private int mirrorY(int curY) {
        int mY = (getY() - curY) + getY();
        switch(direction) {
            case N:
            case NW:
            case NE:
            case SW:
            case SE:
            case S:
                mY -= 1;
                break;
        }
        return mY;
    }
    
    /**
     *
     * @return Status string of X-axis lock
     */
    public String getXStatus() {
        if(getX() == -1)
            return "X: No Lock";
        String status = "X: ";
        if(null != type) switch(type) {
            case BORDER:
                status += "Border Lock @ " + SymmetryDirection.toString(direction) + " ";
                break;
            case TILE:
                status += "Tile Lock @ ";
                break;
            case CORNER:
                status += "Corner Lock @ " + SymmetryDirection.toString(direction) + " ";
                break;
            default:
                status += "No Lock";                        
        }
        status += getX();
        return status;
    }
    
    /**
     *
     * @return Status string of Y-axis lock
     */
    public String getYStatus() {
        if(getY() == -1)
            return "Y: No Lock";
        String status = "Y: ";
        if(null != type) switch(type) {
            case BORDER:
                status += "Border Lock @ " + SymmetryDirection.toString(direction) + " ";
                break;
            case TILE:
                status += "Tile Lock @ ";
                break;
            case CORNER:
                status += "Corner Lock @ " + SymmetryDirection.toString(direction) + " ";
                break;
            default:
                status += "No Lock";                        
        }
        status += getY();
        return status;
    }
    
    private static RoadDirection mirrorDirX(RoadDirection dir) {
        switch(dir) {
            case CENTER:
                return CENTER;
            case NW:
                return NE;
            case NE:
                return NW;
            case SW:
                return SE;
            case SE:
                return SW;
            default:
                throw new DeedPlannerRuntimeException("This exception is impossible anyway");
        }
    }
    
    private static RoadDirection mirrorDirY(RoadDirection dir) {
        switch(dir) {
            case CENTER:
                return CENTER;
            case NW:
                return SW;
            case NE:
                return SE;
            case SW:
                return NW;
            case SE:
                return NE;
            default:
                throw new DeedPlannerRuntimeException("This exception is impossible anyway");
        }
    }
    
    private static RoadDirection mirrorDirXY(RoadDirection dir) {
        return mirrorDirY(mirrorDirX(dir));
    }
    
    /**
     *
     * @param g
     */
    public void renderTiles(GL2 g) {
        g.glDisable(GL2.GL_ALPHA_TEST);
        g.glEnable(GL2.GL_BLEND);
        g.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
        double color = System.currentTimeMillis();
        color%=2000d; color-=1000d; color = Math.abs(color); color/=1000d;
        g.glColor4d(0, 0, 1, 0.1d+0.2d*color);
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
    
    /**
     *
     * @param g
     */
    public void renderXBorder(GL2 g) {
        g.glRotatef(90, 0, 0, 1);
        g.glColor3f(0.5f, 0.5f, 1.0f);
        g.glPointSize(3);
        g.glBegin(GL2.GL_POINTS);
            g.glVertex2f(0, 0);
            g.glVertex2f(1, 0);
            g.glVertex2f(2, 0);
            g.glVertex2f(3, 0);
            g.glVertex2f(4, 0);
        g.glEnd();
        g.glRotatef(-90, 0, 0, 1);
    }
    
    /**
     *
     * @param g
     */
    public void renderYBorder(GL2 g) {
        g.glColor3f(0.5f, 0.5f, 1.0f);
        g.glPointSize(3);
        g.glBegin(GL2.GL_POINTS);
            g.glVertex2f(0, 0);
            g.glVertex2f(1, 0);
            g.glVertex2f(2, 0);
            g.glVertex2f(3, 0);
            g.glVertex2f(4, 0);
        g.glEnd();
    }
    
    /**
     *
     * @param tile
     * @param entity
     * @param level
     */
    public void mirrorTileContent(Tile tile, TileEntity entity, int level) {
        if(Globals.xSymLock && getX() > -1) {
            int mX = mirrorX(tile.getX());
            if(mX >= 0) {
                Tile t = tile.getMap().getTile(mX, tile.getY());
                if(t != null) {
                    t.setTileContent(entity, level);
                }
            }
        }

        if(Globals.ySymLock && getY() > -1) {
            int mY = mirrorY(tile.getY());
            if(mY >= 0) {
                Tile t = tile.getMap().getTile(tile.getX(), mY);
                if(t != null) {
                    t.setTileContent(entity, level);
                }
            }
        }
        
        if(Globals.ySymLock && Globals.xSymLock 
                && getX() > -1 && getY() > -1) {
            int mX = mirrorX(tile.getX());
            int mY = mirrorY(tile.getY());
            if(mX >= 0 && mY >= 0) {
                Tile t = tile.getMap().getTile(mX, mY);
                if( t != null) {
                    t.setTileContent(entity, level);
                }
            }
        }
    }

    private FloorOrientation mirrorFloorOrientationX(FloorOrientation fl) {
        if(fl != null && Globals.mirrorFloors) switch(fl) {
            case LEFT:
                return FloorOrientation.RIGHT;
            case RIGHT:
                return FloorOrientation.LEFT;
        }
        return fl;
    }
    
    private FloorOrientation mirrorFloorOrientationY(FloorOrientation fl) {
        if(fl != null && Globals.mirrorFloors) switch(fl) {
            case UP:
                return FloorOrientation.DOWN;
            case DOWN:
                return FloorOrientation.UP;
        }
        return fl;
    }
    
    private FloorOrientation mirrorFloorOrientationXY(FloorOrientation fl) {
        return mirrorFloorOrientationX(mirrorFloorOrientationY(fl));
    }
    
    /**
     *
     * @param tile
     * @param data
     * @param fl
     * @param level
     */
    public void mirrorFloor(Tile tile, FloorData data, FloorOrientation fl, int level) {
        if(data == null) {
            this.mirrorTileContent(tile, null, level);
            return;
        }
        
        if(Globals.xSymLock && getX() > -1) {
            int mX = mirrorX(tile.getX());
            if(mX >= 0) {
                Tile t = tile.getMap().getTile(mX, tile.getY());
                if(t != null) {
                    t.setTileContent(new Floor(data, mirrorFloorOrientationX(fl)), level);
                }
            }
        }

        if(Globals.ySymLock && getY() > -1) {
            int mY = mirrorY(tile.getY());
            if(mY >= 0) {
                Tile t = tile.getMap().getTile(tile.getX(), mY);
                if(t != null) {
                    t.setTileContent(new Floor(data, mirrorFloorOrientationY(fl)), level);
                }
            }
        }
        
        if(Globals.ySymLock && Globals.xSymLock 
                && getX() > -1 && getY() > -1) {
            int mX = mirrorX(tile.getX());
            int mY = mirrorY(tile.getY());
            if(mX >= 0 && mY >= 0) {
                Tile t = tile.getMap().getTile(mX, mY);
                if( t != null) {
                    t.setTileContent(new Floor(data, mirrorFloorOrientationXY(fl)), level);
                }
            }
        }
    }
    
    /**
     *
     * @param tile
     * @param data
     * @param level
     */
    public void mirrorRoof(Tile tile, RoofData data, int level) {
        if(data == null) {
            this.mirrorTileContent(tile, null, level);
            return;
        }
        
        if(Globals.xSymLock && getX() > -1) {
            int mX = mirrorX(tile.getX());
            if(mX >= 0) {
                Tile t = tile.getMap().getTile(mX, tile.getY());
                if(t != null) {
                    t.setTileContent(new Roof(data), level);
                }
            }
        }

        if(Globals.ySymLock && getY() > -1) {
            int mY = mirrorY(tile.getY());
            if(mY >= 0) {
                Tile t = tile.getMap().getTile(tile.getX(), mY);
                if(t != null) {
                    t.setTileContent(new Roof(data), level);
                }
            }
        }
        
        if(Globals.ySymLock && Globals.xSymLock 
                && getX() > -1 && getY() > -1) {
            int mX = mirrorX(tile.getX());
            int mY = mirrorY(tile.getY());
            if(mX >= 0 && mY >= 0) {
                Tile t = tile.getMap().getTile(mX, mY);
                if( t != null) {
                    t.setTileContent(new Roof(data), level);
                }
            }
        }
    }

    /**
     *
     * @param tile
     * @param data
     */
    public void mirrorGround(Tile tile, GroundData data) {
        RoadDirection dir = Globals.roadDirection;
        if(Globals.xSymLock && getX() > -1) {
            int mX = mirrorX(tile.getX());
            if(mX >= 0) {
                Tile t = tile.getMap().getTile(mX, tile.getY());
                if(t != null)
                    t.setGround(data, mirrorDirX(dir));
            }
        }

        if(Globals.ySymLock && getY() > -1) {
            int mY = mirrorY(tile.getY());
            if(mY >= 0) {
                Tile t = tile.getMap().getTile(tile.getX(), mY);
                if(t != null)
                    t.setGround(data, mirrorDirY(dir));
            }
        }
        
        if(Globals.ySymLock && Globals.xSymLock 
                && getX() > -1 && getY() > -1) {
            int mX = mirrorX(tile.getX());
            int mY = mirrorY(tile.getY());
            if(mX >= 0 && mY >= 0) {
                Tile t = tile.getMap().getTile(mX, mY);
                if( t != null)
                    t.setGround(data, mirrorDirXY(dir));
            }
        }
    }

    /**
     *
     * @param tile
     * @param wall
     * @param level
     * @param frag
     */
    public void mirrorHorizontalWall(Tile tile, WallData wall, int level, TileFragment frag) {
        if(Globals.xSymLock && getX() > -1) {
            int mX = mirrorX(tile.getX());
            if(mX >= 0) {
                Tile t = tile.getMap().getTile(mX, tile.getY());
                if(t != null)
                    t.setHorizontalWall(wall, level);
            }
        }

        if(Globals.ySymLock && getY() > -1) {
            int mY = mirrorY(tile.getY());
            if(mY >= 0) {
                Tile t = tile.getMap().getTile(tile.getX(), mY + 1);
                if(t != null)
                    t.setHorizontalWall(wall, level);
            }
        }
        
        if(Globals.ySymLock && Globals.xSymLock 
                && getX() > -1 && getY() > -1) {
            int mX = mirrorX(tile.getX());
            int mY = mirrorY(tile.getY());
            if(mX >= 0 && mY >= 0) {
                Tile t = tile.getMap().getTile(mX, mY + 1);
                if( t != null)
                    t.setHorizontalWall(wall, level);
            }
        }
    }

    /**
     *
     * @param tile
     * @param wall
     * @param level
     * @param frag
     */
    public void mirrorVerticalWall(Tile tile, WallData wall, int level, TileFragment frag) {
        if(Globals.xSymLock && getX() > -1) {
            int mX = mirrorX(tile.getX());
            if(mX >= 0) {
                Tile t = tile.getMap().getTile(mX + 1, tile.getY());
                if(t != null)
                    t.setVerticalWall(wall, level);
            }
        }
        if(Globals.ySymLock && getY() > -1) {
            int mY = mirrorY(tile.getY());
            if(mY >= 0) {
                Tile t = tile.getMap().getTile(tile.getX(), mY);
                if(t != null)
                    t.setVerticalWall(wall, level);
            }
        }
        if(Globals.ySymLock && Globals.xSymLock 
                && getX() > -1 && getY() > -1) {
            int mX = mirrorX(tile.getX());
            int mY = mirrorY(tile.getY());
            if(mX >= 0 && mY >= 0) {
                Tile t = tile.getMap().getTile(mX +1, mY);
                if( t != null)
                    t.setVerticalWall(wall, level);
            }
        }
    }
    
    /**
     *
     * @param tile
     * @param level
     * @param frag
     */
    public void mirrorClearVerticalWalls(Tile tile, int level, TileFragment frag) {
        if(Globals.xSymLock && getX() > -1) {
            int mX = mirrorX(tile.getX());
            if(mX >= 0) {
                Tile t = tile.getMap().getTile(mX + 1, tile.getY());
                if(t != null)
                    t.clearVerticalWalls(level);
            }
        }
        if(Globals.ySymLock && getY() > -1) {
            int mY = mirrorY(tile.getY());
            if(mY >= 0) {
                Tile t = tile.getMap().getTile(tile.getX(), mY);
                if(t != null)
                    t.clearVerticalWalls(level);
            }
        }
        if(Globals.ySymLock && Globals.xSymLock 
                && getX() > -1 && getY() > -1) {
            int mX = mirrorX(tile.getX());
            int mY = mirrorY(tile.getY());
            if(mX >= 0 && mY >= 0) {
                Tile t = tile.getMap().getTile(mX +1, mY);
                if( t != null)
                    t.clearVerticalWalls(level);
            }
        }
    }
   
    /**
     *
     * @param tile
     * @param level
     * @param frag
     */
    public void mirrorClearHorizontalWalls(Tile tile, int level, TileFragment frag) {
        if(Globals.xSymLock && getX() > -1) {
            int mX = mirrorX(tile.getX());
            if(mX >= 0) {
                Tile t = tile.getMap().getTile(mX, tile.getY());
                if(t != null)
                    t.clearHorizontalWalls(level);
            }
        }
        if(Globals.ySymLock && getY() > -1) {
            int mY = mirrorY(tile.getY());
            if(mY >= 0) {
                Tile t = tile.getMap().getTile(tile.getX(), mY + 1);
                if(t != null)
                    t.clearHorizontalWalls(level);
            }
        }
        if(Globals.ySymLock && Globals.xSymLock 
                && getX() > -1 && getY() > -1) {
            int mX = mirrorX(tile.getX());
            int mY = mirrorY(tile.getY());
            if(mX >= 0 && mY >= 0) {
                Tile t = tile.getMap().getTile(mX, mY + 1);
                if( t != null)
                    t.clearHorizontalWalls(level);
            }
        }
    }

    /**
     *
     * @param tile
     * @param border
     * @param frag
     */
    public void mirrorHorizontalBorder(Tile tile, BorderData border, TileFragment frag) {
        if(Globals.xSymLock && getX() > -1) {
            int mX = mirrorX(tile.getX());
            if(mX >= 0) {
                Tile t = tile.getMap().getTile(mX, tile.getY());
                if(t != null)
                    t.setHorizontalBorder(border);
            }
        }

        if(Globals.ySymLock && getY() > -1) {
            int mY = mirrorY(tile.getY());
            if(mY >= 0) {
                Tile t = tile.getMap().getTile(tile.getX(), mY + 1);
                if(t != null)
                    t.setHorizontalBorder(border);
            }
        }
        
        if(Globals.ySymLock && Globals.xSymLock 
                && getX() > -1 && getY() > -1) {
            int mX = mirrorX(tile.getX());
            int mY = mirrorY(tile.getY());
            if(mX >= 0 && mY >= 0) {
                Tile t = tile.getMap().getTile(mX, mY + 1);
                if( t != null)
                    t.setHorizontalBorder(border);
            }
        }
    }

    /**
     *
     * @param tile
     * @param border
     * @param frag
     */
    public void mirrorVerticalBorder(Tile tile, BorderData border, TileFragment frag) {
        if(Globals.xSymLock && getX() > -1) {
            int mX = mirrorX(tile.getX());
            if(mX >= 0) {
                Tile t = tile.getMap().getTile(mX + 1, tile.getY());
                if(t != null)
                    t.setVerticalBorder(border);
            }
        }
        if(Globals.ySymLock && getY() > -1) {
            int mY = mirrorY(tile.getY());
            if(mY >= 0) {
                Tile t = tile.getMap().getTile(tile.getX(), mY);
                if(t != null)
                    t.setVerticalBorder(border);
            }
        }
        if(Globals.ySymLock && Globals.xSymLock 
                && getX() > -1 && getY() > -1) {
            int mX = mirrorX(tile.getX());
            int mY = mirrorY(tile.getY());
            if(mX >= 0 && mY >= 0) {
                Tile t = tile.getMap().getTile(mX +1, mY);
                if( t != null)
                    t.setVerticalBorder(border);
            }
        }
    }

    /**
     *
     * @param tile
     * @param entity
     */
    public void mirrorCaveEntity(Tile tile, CaveData entity) {
        if(Globals.xSymLock && getX() > -1) {
            int mX = mirrorX(tile.getX());
            if(mX >= 0) {
                Tile t = tile.getMap().getTile(mX, tile.getY());
                if(t != null)
                    t.setCaveEntity(entity);
            }
        }

        if(Globals.ySymLock && getY() > -1) {
            int mY = mirrorY(tile.getY());
            if(mY >= 0) {
                Tile t = tile.getMap().getTile(tile.getX(), mY);
                if(t != null)
                    t.setCaveEntity(entity);
            }
        }
        
        if(Globals.ySymLock && Globals.xSymLock 
                && getX() > -1 && getY() > -1) {
            int mX = mirrorX(tile.getX());
            int mY = mirrorY(tile.getY());
            if(mX >= 0 && mY >= 0) {
                Tile t = tile.getMap().getTile(mX, mY);
                if( t != null)
                    t.setCaveEntity(entity);
            }
        }
    }
    
    private ObjectLocation mirrorObjectLocationX(ObjectLocation loc) {
        if(loc != null) switch(loc) {
            case TOP_LEFT:
                return ObjectLocation.TOP_RIGHT;
            case MIDDLE_LEFT:
                return ObjectLocation.MIDDLE_RIGHT;
            case BOTTOM_LEFT:
                return ObjectLocation.BOTTOM_RIGHT;
            case TOP_RIGHT:
                return ObjectLocation.TOP_LEFT;
            case MIDDLE_RIGHT:
                return ObjectLocation.MIDDLE_LEFT;
            case BOTTOM_RIGHT:
                return ObjectLocation.BOTTOM_LEFT;
        }
        return loc;
    }
    
    private ObjectLocation mirrorObjectLocationY(ObjectLocation loc) {
        if(loc != null) switch(loc) {
            case TOP_LEFT:
                return ObjectLocation.BOTTOM_LEFT;
            case TOP_CENTER:
                return ObjectLocation.BOTTOM_CENTER;
            case TOP_RIGHT:
                return ObjectLocation.BOTTOM_RIGHT;
            case BOTTOM_LEFT:
                return ObjectLocation.TOP_LEFT;
            case BOTTOM_CENTER:
                return ObjectLocation.TOP_CENTER;
            case BOTTOM_RIGHT:
                return ObjectLocation.TOP_RIGHT;
        }
        return loc;
    }
    
    private ObjectLocation mirrorObjectLocationXY(ObjectLocation loc) {
        if(loc != null)
            return mirrorObjectLocationY(mirrorObjectLocationX(loc));
        return ObjectLocation.MIDDLE_CENTER;
    }
    
    /**
     *
     * @param tile
     * @param data
     * @param location
     * @param floor
     */
    public void mirrorObject(Tile tile, GameObjectData data, ObjectLocation location, int floor) {
        if(Globals.xSymLock && getX() > -1) {
            int mX = mirrorX(tile.getX());
            if(mX >= 0) {
                Tile t = tile.getMap().getTile(mX, tile.getY());
                if(t != null)
                    t.setGameObject(data, mirrorObjectLocationX(location), floor);
            }
        }

        if(Globals.ySymLock && getY() > -1) {
            int mY = mirrorY(tile.getY());
            if(mY >= 0) {
                Tile t = tile.getMap().getTile(tile.getX(), mY);
                if(t != null)
                    t.setGameObject(data, mirrorObjectLocationY(location), floor);
            }
        }
        
        if(Globals.ySymLock && Globals.xSymLock 
                && getX() > -1 && getY() > -1) {
            int mX = mirrorX(tile.getX());
            int mY = mirrorY(tile.getY());
            if(mX >= 0 && mY >= 0) {
                Tile t = tile.getMap().getTile(mX, mY);
                if( t != null)
                    t.setGameObject(data, mirrorObjectLocationXY(location), floor);
            }
        }
    }
    
    /**
     *
     * @param tile
     * @param object
     * @param rotation
     * @param location
     * @param floor
     */
    public void mirrorObjectRotation(Tile tile, GameObject object, double rotation,
            ObjectLocation location, int floor) {
        if(Globals.xSymLock && getX() > -1) {
            int mX = mirrorX(tile.getX());
            ObjectLocation l = mirrorObjectLocationX(location);
            if(mX >= 0) {
                Tile t = tile.getMap().getTile(mX, tile.getY());
                if(t != null) {
                    GameObject o = t.getGameObject(floor, l);
                    if(o != null) {
                        double r = (Math.PI * 2) - rotation;
                        o.setRotation(r);
                    }
                }
            }
        }

        if(Globals.ySymLock && getY() > -1) {
            int mY = mirrorY(tile.getY());
            ObjectLocation l = mirrorObjectLocationY(location);
            if(mY >= 0) {
                Tile t = tile.getMap().getTile(tile.getX(), mY);
                if(t != null) {
                    GameObject o = t.getGameObject(floor, l);
                    if(o != null) {
                        double r = Math.PI - rotation;
                        o.setRotation(r);
                    }
                }
            }
        }
        
        if(Globals.ySymLock && Globals.xSymLock 
                && getX() > -1 && getY() > -1) {
            int mX = mirrorX(tile.getX());
            int mY = mirrorY(tile.getY());
            ObjectLocation l = mirrorObjectLocationXY(location);
            if(mX >= 0 && mY >= 0) {
                Tile t = tile.getMap().getTile(mX, mY);
                if(t != null) {
                    GameObject o = t.getGameObject(floor, l);
                    if(o != null) {
                        double r = Math.PI + rotation;
                        o.setRotation(r);
                    }
                }
            }
        }        
    }
    
}
