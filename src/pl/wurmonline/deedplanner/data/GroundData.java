package pl.wurmonline.deedplanner.data;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import pl.wurmonline.deedplanner.Properties;
import pl.wurmonline.deedplanner.graphics.texture.TexComplex;

public final class GroundData {

    public final String name;
    public final String shortName;
    
    public final TexComplex tex;
    private Icon icon;
    
    public final boolean diagonal;
    
    public GroundData(String name, String shortName, TexComplex tex, boolean diagonal) {
        this.name = name;
        this.shortName = shortName;
        this.tex = tex;
        this.diagonal = diagonal;
    }
    
    public Icon getIcon() {
        if (Properties.iconSize==0) {
            return null;
        }
        else if (icon==null) {
            try {
                Image img = resizeImage(ImageIO.read(tex.getDefault().getFile()), Properties.iconSize, Properties.iconSize);
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
    
    public String toString() {
        return name;
    }
    
}
