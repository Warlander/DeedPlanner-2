package pl.wurmonline.deedplanner.data;

import java.util.LinkedList;

public final class Action {

    private final LinkedList<Tile> newTiles;
    private final LinkedList<Tile> oldTiles;
    
    public Action(Tile newTile, Tile oldTile) {
        this.newTiles = new LinkedList<>();
        this.oldTiles = new LinkedList<>();
        add(newTile, oldTile);
    }
    
    public void add(Tile newTile, Tile oldTile) {
        newTiles.add(newTile);
        oldTiles.add(oldTile);
    }
    
    public void undo(Map map) {
        oldTiles.stream().forEach((t) -> {action(map, t);});
    }
    
    public void redo(Map map) {
        newTiles.stream().forEach((t) -> {action(map, t);});
    }
    
    private void action(Map map, Tile tile) {
        map.setTile(tile, tile.getX(), tile.getY(), false);
    }
    
    public boolean isEmpty() {
        return newTiles.isEmpty();
    }
    
}
