package pl.wurmonline.deedplanner.data.bridges;

public enum BridgeType {
    ROPE, FLAT, ARCHED;
    
    public static BridgeType getType(String str) {
        for (BridgeType type : values()) {
            if (type.toString().equals(str)) {
                return type;
            }
        }
        return null;
    }
}
