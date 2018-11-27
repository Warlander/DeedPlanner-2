package pl.wurmonline.deedplanner.logic;

import pl.wurmonline.deedplanner.data.Tile;

public enum TileFragment {
    CENTER, N, S, W, E, NW, NE, SW, SE;
    
    public static TileFragment calculateTileFragment(float x, float y) {
        if (x<0.2 && y<0.2) {
            return SW;
        }
        else if (x>0.8 && y<0.2) {
            return SE;
        }
        else if (x<0.2 && y>0.8) {
            return NW;
        }
        else if (x>0.8 && y>0.8) {
            return NE;
        }
        else if (x<0.2) {
            return W;
        }
        else if (x>0.8) {
            return E;
        }
        else if (y<0.2) {
            return S;
        }
        else if (y>0.8) {
            return N;
        }
        else {
            return CENTER;
        }
    }
    
    public boolean isNorth() {
        return this==N || this==NW || this==NE;
    }
    
    public boolean isSouth() {
        return this==S || this==SW || this==SE;
    }
    
    public boolean isEast() {
        return this==E || this==SE || this==NE;
    }
    
    public boolean isWest() {
        return this==W || this==SW || this==NW;
    }
    
    public boolean isCorner() {
        return this==NW || this==NE || this==SW || this==SE;
    }
    
    public boolean isCenter() {
        return this == CENTER;
    }
    
    public Tile getTileByCorner(Tile tile) {
        switch (this) {
            case SW:
                return tile;
            case SE:
                return tile.getMap().getTile(tile, 1, 0);
            case NW:
                return tile.getMap().getTile(tile, 0, 1);
            case NE:
                return tile.getMap().getTile(tile, 1, 1);
            default:
                throw new IllegalArgumentException("Cannot get tile by non-corner!");
        }
    }
    
}
