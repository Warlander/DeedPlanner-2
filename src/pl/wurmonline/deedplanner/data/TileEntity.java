package pl.wurmonline.deedplanner.data;

import javax.media.opengl.GL2;

public interface TileEntity extends DataEntity {

    public abstract void render(GL2 g, Tile tile);
    
}
