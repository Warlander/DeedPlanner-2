package pl.wurmonline.deedplanner.data;

import java.util.HashMap;
import java.util.function.Consumer;
import pl.wurmonline.deedplanner.Constants;

public class MapFragment {
    
    private final int width;
    private final int height;
    private final Tile[][] tiles;
    private final boolean copy;
    
    public MapFragment(Map map, int startX, int startY, int endX, int endY) {
        if (startX<0 || startY<0) {
            throw new IllegalArgumentException("Start values cannot be smaller than 0");
        }
        else if (endX>map.getWidth()+1 || endY>map.getHeight()+1) {
            throw new IllegalArgumentException("End values cannot be higher than map width and height");
        }
        else if (startX>endX || startY>endY) {
            throw new IllegalArgumentException("Start values cannot be higher than end values");
        }
        
        width = endX - startX + 1;
        height = endY - startY + 1;
        tiles = new Tile[width+1][height+1];
        copy = false;
        
        for (int i = 0; i <= width; i++) {
            for (int i2 = 0; i2 <= height; i2++) {
                tiles[i][i2] = map.getTile(i+startX, i2+startY);
            }
        }
    }
    
    private MapFragment(MapFragment toCopy) {
        this.width = toCopy.width;
        this.height = toCopy.height;
        tiles = new Tile[width+1][height+1];
        copy = true;
        
        for (int i = 0; i <= width; i++) {
            for (int i2 = 0; i2 <= height; i2++) {
                tiles[i][i2] = new Tile(toCopy.tiles[i][i2]);
            }
        }
    }
    
    public Materials getMaterials() {
        Materials materials = new Materials();
        for (int i=0; i<width; i++) {
            for (int i2=0; i2<height; i2++) {
                boolean withRight = i==width-1;
                boolean withTop = i2==height-1;
                materials.put(tiles[i][i2].getMaterials(withRight, withTop));
            }
        }
        return materials;
    }
    
    public MapFragment createDeepCopy() {
        return new MapFragment(this);
    }
    
    public void paste(Map map, int startX, int startY) {
        for (int i = 0; i < width; i++) {
            for (int i2 = 0; i2 < height; i2++) {
                map.setTile(new Tile(map, tiles[i][i2], startX+i, startY+i2), startX+i, startY+i2);
            }
        }
        map.createHeightData();
        
        for (int i = 0; i <= width; i++) {
            Tile mapTile = map.getTile(i+startX, height+startY);
            mapTile.clearHorizontalWalls();
            for (int i2 = 0; i2 < Constants.FLOORS_LIMIT; i2++) {
                WallData data = tiles[i][height].getHorizontalWall(i2)!=null ? tiles[i][height].getHorizontalWall(i2).data : null;
                if (data!=null) {
                    mapTile.setHorizontalWall(data, i2);
                }
                data = tiles[i][height].getHorizontalFence(i2)!=null ? tiles[i][height].getHorizontalFence(i2).data : null;
                if (data!=null) {
                    mapTile.setHorizontalWall(data, i2);
                }
            }
        }
        
        for (int i = 0; i <= height; i++) {
            Tile mapTile = map.getTile(width+startX, i+startY);
            mapTile.clearVerticalWalls();
            for (int i2 = 0; i2 < Constants.FLOORS_LIMIT; i2++) {
                WallData data = tiles[width][i].getVerticalWall(i2)!=null ? tiles[width][i].getVerticalWall(i2).data : null;
                if (data!=null) {
                    mapTile.setVerticalWall(data, i2);
                }
                data = tiles[width][i].getHorizontalFence(i2)!=null ? tiles[width][1].getVerticalFence(i2).data : null;
                if (data!=null) {
                    mapTile.setVerticalWall(data, i2);
                }
            }
        }
        
        map.newAction();
    }
    
    public boolean isDeepCopy() {
        return copy;
    }
    
    public boolean contains(Tile tile) {
        for (int i = 0; i < width; i++) {
            for (int i2 = 0; i2 < height; i2++) {
                if (tile.getX()==tiles[i][i2].getX() && tile.getY()==tiles[i][i2].getY()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void forEach(Consumer<Tile> consumer) {
        for (int i = 0; i <= width; i++) {
            for (int i2 = 0; i2 <= height; i2++) {
                consumer.accept(tiles[i][i2]);
            }
        }
    }
    
    public Tile getSingleTile() {
        if (width!=1 || height!=1) {
            return null;
        }
        return tiles[0][0];
    }
    
}
