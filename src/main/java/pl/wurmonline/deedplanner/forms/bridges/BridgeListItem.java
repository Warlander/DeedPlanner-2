package pl.wurmonline.deedplanner.forms.bridges;

import pl.wurmonline.deedplanner.data.bridges.BridgeData;
import pl.wurmonline.deedplanner.data.bridges.BridgeType;

final class BridgeListItem {
    
    final BridgeType type;
    final BridgeData data;
    
    BridgeListItem(BridgeType type, BridgeData data) {
        this.type = type;
        this.data = data;
    }
    
    public String toString() {
        if (type == BridgeType.ROPE) {
            return "rope bridge";
        }
        else {
            return type.toString().toLowerCase() + " " + data.getName() + " bridge";
        }
    }
    
}
