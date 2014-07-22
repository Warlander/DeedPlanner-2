package pl.wurmonline.deedplanner.util.jogl;

import java.lang.ref.*;

public class DisplayListReference extends PhantomReference<RenderableEntity> {

    public int listID;
    
    public DisplayListReference(RenderableEntity referent, ReferenceQueue q) {
        super(referent, q);
    }

}
