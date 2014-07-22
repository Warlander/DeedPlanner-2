package pl.wurmonline.deedplanner.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface XMLSerializable {

    public abstract void serialize(Document doc, Element root);
    
}
