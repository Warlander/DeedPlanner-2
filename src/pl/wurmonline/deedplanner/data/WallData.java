package pl.wurmonline.deedplanner.data;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import pl.wurmonline.deedplanner.Properties;
import pl.wurmonline.deedplanner.util.jogl.Color;
import pl.wurmonline.deedplanner.graphics.wom.Model;

public class WallData {

    public final Model bottomModel;
    public final Model normalModel;
    public final String name;
    public final String shortName;
    public final Color color;
    public final float scale;
    public final boolean houseWall;
    public final boolean arch;
    public final boolean archBuildable;
    
    private final String iconLocation;
    private Icon icon;
    
    private final Materials materials;
    
    public WallData(Model bottomModel, Model normalModel, String name, String shortName, Color color, float scale, boolean houseWall, boolean arch, boolean archBuildable, Materials materials, String iconLocation) {
        this.bottomModel = bottomModel;
        this.normalModel = normalModel;
        this.name = name;
        this.shortName = shortName;
        this.color = color;
        this.scale = scale;
        this.houseWall = houseWall;
        this.arch = arch;
        this.archBuildable = archBuildable;
        this.iconLocation = iconLocation;
        if (materials!=null) {
            this.materials = materials;
        }
        else {
            this.materials = new Materials();
        }
    }
    
    public Icon getIcon() {
        if (Properties.iconSize==0 || iconLocation==null) {
            return null;
        }
        else if (icon==null) {
            try {
                Image img = resizeImage(ImageIO.read(new File(iconLocation)), Properties.iconSize, Properties.iconSize);
                icon = new ImageIcon(img);
            } catch (IOException ex) {
                Logger.getLogger(GroundData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return icon;
    }
    
    private Image resizeImage(BufferedImage originalImage, int width, int height){
        return originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
    
    public Materials getMaterials() {
        return materials;
    }
    
    public String toString() {
        return name;
    }
    
}
