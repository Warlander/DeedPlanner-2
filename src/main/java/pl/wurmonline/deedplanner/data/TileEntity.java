package pl.wurmonline.deedplanner.data;

import com.jogamp.opengl.GL2;
import pl.wurmonline.deedplanner.util.XMLSerializable;

public interface TileEntity extends XMLSerializable {
    
    public abstract Materials getMaterials();
    public abstract void render(GL2 g, Tile tile);
    public abstract TileEntity deepCopy();
    
}
