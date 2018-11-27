package pl.wurmonline.deedplanner.data;

import pl.wurmonline.deedplanner.graphics.wom.Model;

public class AnimalData {

    private final Model unisexModel;
    private final Model maleModel;
    private final Model femaleModel;
    public final String name;
    public final String shortName;
    
    public AnimalData(Model unisexModel, Model maleModel, Model femaleModel, String name, String shortName) {
        this.unisexModel = unisexModel;
        this.maleModel = maleModel;
        this.femaleModel = femaleModel;
        this.name = name;
        this.shortName = shortName;
    }
    
    public Model getModelForSex(AnimalGender gender) {
        if (gender == AnimalGender.UNISEX) {
            return unisexModel;
        }
        else if (gender == AnimalGender.MALE) {
            return maleModel;
        }
        else {
            return femaleModel;
        }
    }
    
    public String toString() {
        return name;
    }
    
}
