package pl.wurmonline.deedplanner.data;

import java.util.HashMap;
import org.w3c.dom.Node;
import pl.wurmonline.deedplanner.Constants;

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
        
        build.append("Materials needed:").append(Constants.ENTER);
        build.append(Constants.ENTER);
        
        forEach((name, count) -> {build.append(name).append(" = ").append(count).append(System.getProperty("line.separator"));});
        if (isEmpty()) {
            build.append("No materials. If there are any structures on map and this message is shown, please contact program developer on Wurm Online forum.");
        }
        
        return build.toString();
    }
    
}
