package pl.wurmonline.deedplanner.data.bridges;

public enum BridgeType {
    ROPE(3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
    FLAT(0),
    ARCHED(5, 10, 15, 20);
    
    public static BridgeType getType(String str) {
        for (BridgeType type : values()) {
            if (type.toString().equals(str)) {
                return type;
            }
        }
        return null;
    }
    
    private final int[] additionalParameters;
    
    private BridgeType(int... additionalParameters) {
        this.additionalParameters = additionalParameters;
    }
    
    public int[] getAdditionalParameters() {
        return additionalParameters;
    }
}
