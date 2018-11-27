package pl.wurmonline.deedplanner.data;

import pl.wurmonline.deedplanner.util.jogl.Color;

public enum AnimalGender {
    UNISEX(new Color(1, 1, 1)),
    MALE(new Color(0.7f, 0.7f, 1)),
    FEMALE(new Color(1, 0.7f, 0.7f));
    
    public static AnimalGender getGender(String str) {
        for (AnimalGender type : values()) {
            if (type.toString().equals(str)) {
                return type;
            }
        }
        return null;
    }
    
    private final Color colorMod;
    
    private AnimalGender(Color colorMod) {
        this.colorMod = colorMod;
    }
    
    public Color getColorMod() {
        return colorMod;
    }
    
}
