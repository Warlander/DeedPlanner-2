package pl.wurmonline.deedplanner.data;

import java.util.Objects;

public final class ObjectEntityData extends EntityData {

    private final ObjectLocation location;
    
    public ObjectEntityData(int floor, ObjectLocation location) {
        super(floor, EntityType.OBJECT);
        this.location = location;
    }
    
    public ObjectLocation getLocation() {
        return location;
    }

    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + this.getFloor();
        hash = 13 * hash + Objects.hashCode(this.getType());
        hash = 97 * hash + Objects.hashCode(this.location);
        return hash;
    }
    
    public boolean equals(Object obj) {
        if (!(obj instanceof ObjectEntityData)) {
            return false;
        }
        ObjectEntityData data = (ObjectEntityData) obj;
        return data.getFloor() == this.getFloor() && data.getType() == this.getType() && data.getLocation() == this.getLocation();
    }
    
}
