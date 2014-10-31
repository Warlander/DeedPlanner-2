package pl.wurmonline.deedplanner.data;

import java.util.Objects;

public class EntityData {

    private final int floor;
    private final EntityType type;
            
    public EntityData(int floor, EntityType type) {
        this.floor = floor;
        this.type = type;
    }

    public int getFloor() {
        return floor;
    }
    
    public EntityType getType() {
        return type;
    }
    
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + this.floor;
        hash = 13 * hash + Objects.hashCode(this.type);
        return hash;
    }
    
    public boolean equals(Object obj) {
        if (!(obj instanceof EntityData)) {
            return false;
        }
        EntityData data = (EntityData) obj;
        return data.floor == this.floor && data.type == this.type;
    }
    
}
