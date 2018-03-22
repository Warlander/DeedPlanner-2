package pl.wurmonline.deedplanner.util;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;
import java.util.Locale;

public class FontWrapper {

    private static final FontWrapper[] wrappers;
    
    static {
        Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
        wrappers = Arrays.stream(fonts)
                .map(f -> new FontWrapper(f))
                .toArray(FontWrapper[]::new);
    }
    
    public static FontWrapper[] getWrappers() {
        return wrappers;
    }
    
    public static FontWrapper getWrapper(Font font) {
        return Arrays.stream(wrappers)
                .filter(wrapper -> wrapper.font.getFontName().equals(font.getFontName()) && wrapper.font.getStyle() == font.getStyle())
                .findFirst()
                .orElse(null);
    }
    
    public final Font font;
    
    private FontWrapper(Font font) {
        this.font = font;
    }
    
    public Font getFont() {
        return font;
    }
    
    public String toString() {
        return font.getFontName(Locale.UK).replace("Bold", "B").replace("Italic", "I").replace("Oblique", "O");
    }
    
}