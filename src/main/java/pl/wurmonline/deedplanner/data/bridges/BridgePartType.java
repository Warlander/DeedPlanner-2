package pl.wurmonline.deedplanner.data.bridges;

import java.util.HashMap;

public enum BridgePartType {
    FLOATING('f'), ABUTMENT('a'), BRACING('b'), CROWN('c'), DOUBLE_BRACING('d'), DOUBLE_ABUTMENT('e'), SUPPORT('s'), EXTENSION(' ');
    
    private static final HashMap<Character, BridgePartType> charToSegment = new HashMap<>();
    
    static {
        for (BridgePartType segment : values()) {
            charToSegment.put(segment.symbol, segment);
        }
    }
    
    public static String encodeSegments(BridgePartType[] segments) {
        StringBuilder build = new StringBuilder();
        for (BridgePartType segment : segments) {
            build.append(segment.symbol);
        }
        return build.toString();
    }
    
    public static BridgePartType[] decodeSegments(String sequence) {
        char[] chars = sequence.toCharArray();
        BridgePartType[] segments = new BridgePartType[chars.length];
        for (int i = 0; i < chars.length; i++) {
            segments[i] = charToSegment.get(chars[i]);
        }
        
        return segments;
    }
    
    private final char symbol;
    
    private BridgePartType(char symbol) {
        this.symbol = symbol;
    }
    
}
