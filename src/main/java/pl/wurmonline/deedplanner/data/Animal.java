package pl.wurmonline.deedplanner.data;

import com.jogamp.opengl.GL2;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pl.wurmonline.deedplanner.data.storage.Data;
import pl.wurmonline.deedplanner.util.jogl.Color;

public class Animal implements GridTileEntity {
    
    private final AnimalData data;
    private double rotation = 0;
    private final AnimalAge animalAge;
    private final AnimalGender animalGender;
    
    public Animal(AnimalData data, AnimalAge animalAge, AnimalGender animalGender) {
        this.data = data;
        this.animalAge = animalAge;
        this.animalGender = animalGender;
    }
    
    public Animal(Element object) {
        String shortname = object.getAttribute("id");
        this.data = Data.animals.get(shortname);
        rotation = Double.parseDouble(object.getAttribute("rotation"));
        this.animalAge = AnimalAge.getAge(object.getAttribute("age"));
        this.animalGender = AnimalGender.getGender(object.getAttribute("gender"));
    }
    
    public void serialize(Document doc, Element root) {
        root.setAttribute("id", data.shortName);
        root.setAttribute("rotation", Double.toString(rotation));
        root.setAttribute("age", animalAge.toString());
        root.setAttribute("gender", animalGender.toString());
    }
    
    public void render(GL2 g, Tile tile) {
        float scaleMod = animalAge.getScaleMod();
        g.glScalef(scaleMod, scaleMod, scaleMod);
        g.glRotated(Math.toDegrees(rotation), 0, 0, 1);
        
        data.getModelForSex(animalGender).render(g);
    }
    
    public Animal deepCopy() {
        return new Animal(data, animalAge, animalGender);
    }
    
    public void setRotation(double rotation) {
        this.rotation = rotation;
    }
    
    public Color getTintColor() {
        if (data.getModelForSex(AnimalGender.MALE) != data.getModelForSex(AnimalGender.FEMALE)) {
            return Color.WHITE;
        }
        
        return animalGender.getColorMod();
    }

    public Materials getMaterials() {
        return null;
    }
    
}
