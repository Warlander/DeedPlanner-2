package pl.wurmonline.deedplanner.data;

import javax.media.opengl.GL2;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Animal extends GameObject {
    
    private final AnimalAge animalAge;
    private final AnimalGender animalGender;
    
    public Animal(GameObjectData data, AnimalAge animalAge, AnimalGender animalGender) {
        super(data);
        this.animalAge = animalAge;
        this.animalGender = animalGender;
    }
    
    public Animal(Element object) {
        super(object);
        
        animalAge = AnimalAge.getAge(object.getAttribute("age"));
        animalGender = AnimalGender.getGender(object.getAttribute("gender"));
    }
    
    public void serialize(Document doc, Element root) {
        super.serialize(doc, root);
        root.setAttribute("age", animalAge.toString());
        root.setAttribute("gender", animalGender.toString());
    }
    
    public void render(GL2 g, Tile tile) {
        float scaleMod = animalAge.getScaleMod();
        g.glScalef(scaleMod, scaleMod, scaleMod);
        animalGender.getColorMod().use(g);
        super.render(g, tile);
    }
    
    public Animal deepCopy() {
        return new Animal(getData(), animalAge, animalGender);
    }
    
}
