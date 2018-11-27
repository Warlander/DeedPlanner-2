package pl.wurmonline.deedplanner.data;

import java.util.HashMap;
import org.w3c.dom.Node;

public final class Materials extends HashMap<String, Integer> {

    public Materials() {
        super();
    }
    
    public Materials(Node node) {
        super();
        String content = node.getTextContent();
        String[] materials = content.split(",");
        for (String material : materials) {
            String[] parts = material.split("=");
            String name = parts[0].trim();
            int count = Integer.parseInt(parts[1].trim());
            put(name, count);
        }
    }
    
    public void put(Materials materials) {
        if (materials==null) {
            return;
        }
        materials.entrySet().stream().forEach((entry) -> {
            put(entry.getKey(), entry.getValue());
        });
    }
    
    public Integer put(String name, Integer count) {
        if(containsKey(name)) {
            return super.put(name, get(name)+count);
        }
        return super.put(name, count);
    }
    
    public String toString() {
        StringBuilder build = new StringBuilder();
        
        build.append(java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle").getString("MATERIALS NEEDED:")).append(System.lineSeparator());
        build.append(System.lineSeparator());
        
        forEach((name, count) -> {build.append(name).append(" = ").append(count).append(System.lineSeparator());});
        if (isEmpty()) {
            build.append(java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle").getString("NO MATERIALS. IF THERE ARE ANY STRUCTURES ON MAP AND THIS MESSAGE IS SHOWN, PLEASE CONTACT PROGRAM DEVELOPER ON WURM ONLINE FORUM."));
        }
        
        return build.toString();
    }
    
}
