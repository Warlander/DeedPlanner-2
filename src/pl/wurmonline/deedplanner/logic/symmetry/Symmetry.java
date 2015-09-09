package pl.wurmonline.deedplanner.logic.symmetry;

import javax.media.opengl.GL2;
import pl.wurmonline.deedplanner.Globals;
import pl.wurmonline.deedplanner.data.BorderData;
import pl.wurmonline.deedplanner.data.CaveData;
import pl.wurmonline.deedplanner.data.Floor;
import pl.wurmonline.deedplanner.data.FloorData;
import pl.wurmonline.deedplanner.data.FloorOrientation;
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

public class Symmetry {
    private int x = -1;
    private int y = -1;
    private SymmetryType type = SymmetryType.NONE;
    private SymmetryDirection direction = SymmetryDirection.CENTER;
    private boolean isMirroring = false;
    
    public Symmetry() {
        
    }
    
    public Symmetry(int x, int y, SymmetryType type, SymmetryDirection dir) {
        this.x = x;
        this.y = y;
        
        if(type == SymmetryType.CORNER || type == SymmetryType.BORDER ) switch(dir) {
            case SE:
            case E:
                this.x += 1;
                break;
            case N:
            case NW:
                this.y += 1;
                break;
            case NE:
                this.x += 1;
                this.y += 1;
                break;
        }
        this.type = type;
        this.direction = dir;
    }
    
    public Symmetry(int x, int y, SymmetryType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setType(SymmetryType type) {
        this.type = type;
    }
    
    public SymmetryType getType() {
        return type;
    }
    
    public void setDirection(SymmetryDirection dir) {
        this.direction = dir;
    }
    
    public SymmetryDirection getDirection() {
        return direction;
    }
    
    public int mirrorX(int curX) {
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
    
    public int mirrorY(int curY) {
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
    
    public String getStatus() {
        String status = "";
        if(getX() > -1)
            status += getXStatus();
        if(getY() > -1)
            status += getYStatus();
        
        if(status != "")
            status = "Symmetry - " + status;
        
        return status;
    }
    
    public static RoadDirection mirrorDirX(RoadDirection dir) {
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
    
    public static RoadDirection mirrorDirY(RoadDirection dir) {
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
    
    public static RoadDirection mirrorDirXY(RoadDirection dir) {
        return mirrorDirY(mirrorDirX(dir));
    }
    
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

    private FloorOrientation mirrorFlOrX(FloorOrientation fl) {
        if(fl != null && Globals.mirrorFloors) switch(fl) {
            case LEFT:
                return FloorOrientation.RIGHT;
            case RIGHT:
                return FloorOrientation.LEFT;
        }
        return fl;
    }
    
    private FloorOrientation mirrorFlOrY(FloorOrientation fl) {
        if(fl != null && Globals.mirrorFloors) switch(fl) {
            case UP:
                return FloorOrientation.DOWN;
            case DOWN:
                return FloorOrientation.UP;
        }
        return fl;
    }
    
    private FloorOrientation mirrorFlOrXY(FloorOrientation fl) {
        return mirrorFlOrX(mirrorFlOrY(fl));
    }
    
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
                    t.setTileContent(new Floor(data, mirrorFlOrX(fl)), level);
                }
            }
        }

        if(Globals.ySymLock && getY() > -1) {
            int mY = mirrorY(tile.getY());
            if(mY >= 0) {
                Tile t = tile.getMap().getTile(tile.getX(), mY);
                if(t != null) {
                    t.setTileContent(new Floor(data, mirrorFlOrY(fl)), level);
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
                    t.setTileContent(new Floor(data, mirrorFlOrXY(fl)), level);
                }
            }
        }
    }
    
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

    public void mirrorGround(Tile tile, GroundData data) {
        mirrorGround(tile, data, Globals.roadDirection);
    }
    
    public void mirrorGround(Tile tile, GroundData data, RoadDirection dir) {
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

    public void mirrorHorWall(Tile tile, WallData wall, int level, TileFragment frag) {
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

    public void mirrorVertWall(Tile tile, WallData wall, int level, TileFragment frag) {
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
    
   public void mirrorClearVertWall(Tile tile, int level, TileFragment frag) {
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
   
   public void mirrorClearHorzWall(Tile tile, int level, TileFragment frag) {
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

       public void mirrorHorzBorder(Tile tile, BorderData border, TileFragment frag) {
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

    public void mirrorVertBorder(Tile tile, BorderData border, TileFragment frag) {
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
    
}
