package pl.wurmonline.deedplanner.graphics;

import com.jogamp.opengl.GL2;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3d;
import pl.wurmonline.deedplanner.Globals;
import pl.wurmonline.deedplanner.MapPanel;
import pl.wurmonline.deedplanner.data.Tile;
import pl.wurmonline.deedplanner.input.Keybindings;
import pl.wurmonline.deedplanner.input.Mouse;
import pl.wurmonline.deedplanner.logic.TileFragment;

public interface Camera {
    
    public default boolean isEditing() {
        if (!Globals.editMode) {
            return false;
        }
        return isEditingCapable();
    }
    
    /**
     * Updates logic behind the camera - it's the logic phase, no render specific logic should be implemented there.
     * @param panel current map panel
     * @param mouse mouse input
     * @param keybindings keyboard input with keybindings applied
     */
    public abstract void update(MapPanel panel, Mouse mouse, Keybindings keybindings);
    /**
     * Set rendering of the camera - most logic should be done in update method instead.
     * @param g graphics handle
     * @param panel current map panel
     */
    public abstract void set(GL2 g, MapPanel panel);
    /**
     * reset the camera, for example when map is changed
     */
    public abstract void reset();
    
    /**
     * Checks if current camera can be used for editing the map or is preview only.
     * @return is editing the map possible using this camera
     */
    public abstract boolean isEditingCapable();
    /**
     * Returns currently hovered tile. Should always return tile on valid maps.
     * Can return null if edit is not enabled.
     * @return currently hovered tile
     */
    public abstract Tile getHoveredTile();
    /**
     * Returns currently hovered part of the tile. It should return hovered part of tile returned by getHoveredTile() call.
     * Can return null if edit is not enabled.
     * @return currently hovered part of the tile
     */
    public abstract TileFragment getHoveredTileFragment();
    /**
     * Returns coordinates local to currently hovered tile.
     * It should return coordinates of tile returned by getHoveredTile() call.
     * @return coordinates inside currently hovered tile, in 0 to 1 range
     */
    public abstract Vector2f getHoveredTilePosition();
    /**
     * Gets level visiblity in 0 to 1 range, with 0 being level is not leveled at all and 1 being rendered normally.
     * @param level map level to check
     * @return level visibility
     */
    public abstract float getLevelVisibility(int level);
    /**
     * @return lowest x and y that should be rendered on map (or 0,0).
     */
    public abstract Vector2i getLowerRenderBounds();
    /**
     * @return highest x and y that should be rendered on map (or upper map bounds).
     */
    public abstract Vector2i getUpperRenderBounds();
    /**
     * @return should skybox be rendered
     */
    public abstract boolean isSkyboxRendered();
    /**
     * @return this camera type. Should be unique for each camera
     */
    public abstract CameraType getCameraType();
    /**
     * @return current camera position in world space
     */
    public abstract Vector3d getPosition();
    
}
