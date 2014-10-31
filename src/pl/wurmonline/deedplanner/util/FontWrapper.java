package pl.wurmonline.deedplanner.util;

import java.awt.Font;
import java.util.Locale;

public class FontWrapper {

    public final Font font;
    
    public FontWrapper(Font font) {
        this.font = font;
    }
    
    public String toString() {
        return font.getFontName(Locale.UK).replace("Bold", "B").replace("Italic", "I").replace("Oblique", "O");
    }
    
}