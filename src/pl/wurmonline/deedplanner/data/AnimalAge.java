package pl.wurmonline.deedplanner.data;

public enum AnimalAge {
    CHILD(0.7f),
    ADULT(1.0f),
    CHAMPION(1.5f);
    
    public static AnimalAge getAge(String str) {
        for (AnimalAge type : values()) {
            if (type.toString().equals(str)) {
                return type;
            }
        }
        return null;
    }
    
    private final float scaleMod;
    
    private AnimalAge(float scaleMod) {
        this.scaleMod = scaleMod;
    }
    
    public float getScaleMod() {
        return scaleMod;
    }
    
}
