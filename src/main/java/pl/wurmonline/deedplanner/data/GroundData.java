package pl.wurmonline.deedplanner.data;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import net.npe.dds.DDSReader;
import pl.wurmonline.deedplanner.Properties;
import pl.wurmonline.deedplanner.graphics.texture.SimpleTex;
import pl.wurmonline.deedplanner.util.Log;

public final class GroundData {

    public final String name;
    public final String shortName;
    
    private final SimpleTex tex2d;
    private final SimpleTex tex3d;
    private Icon icon;
    
    public final boolean diagonal;
    
    public GroundData(String name, String shortName, SimpleTex tex2d, SimpleTex tex3d, boolean diagonal) {
        this.name = name;
        this.shortName = shortName;
        this.tex2d = tex2d;
        this.tex3d = tex3d;
        this.diagonal = diagonal;
    }
    
    public Icon getOrCreateIcon() {
        if (Properties.iconSize==0) {
            return null;
        }
        else if (icon == null) {
            BufferedImage unscaledImage = loadUnscaledImage();
            Image scaledImage = resizeImage(unscaledImage, Properties.iconSize, Properties.iconSize);
            icon = new ImageIcon(scaledImage);
            Log.out(this, "Icon loaded!");
        }
        
        return icon;
    }
    
    private BufferedImage loadUnscaledImage() {
        try (FileInputStream fis = new FileInputStream(tex2d.getFile())) {
            if (tex2d.getFile().getName().endsWith(".dds")) {
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                int[] pixels = DDSReader.read(buffer, DDSReader.ARGB, 0);
                int width = DDSReader.getWidth(buffer);
                int height = DDSReader.getHeight(buffer);
                BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                img.setRGB(0, 0, width, height, pixels, 0, width);
                return img;
            }
            else {
                return ImageIO.read(fis);
            }
        } catch (IOException ex) {
            Logger.getLogger(GroundData.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private Image resizeImage(BufferedImage originalImage, int width, int height){
        return originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
    
    public SimpleTex getTexture(TextureType type) {
        if (type == TextureType.TEXTURE_2D_VIEW) {
            return tex2d;
        }
        else if (type == TextureType.TEXTURE_3D_VIEW) {
            return tex3d;
        }
        else {
            throw new IllegalArgumentException("Invalid parameter - TEXTURE_2D_VIEW or TEXTURE_3D_VIEW expected.");
        }
    }
    
    public String toString() {
        return name;
    }
    
    public static enum TextureType {
        TEXTURE_2D_VIEW, TEXTURE_3D_VIEW;
    }
    
}
