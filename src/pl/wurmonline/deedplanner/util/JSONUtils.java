package pl.wurmonline.deedplanner.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;

public class JSONUtils {
    
    public static JSONArray downloadJSONArray(String url) throws IOException {
        URL jsonArrayUrl = new URL(url);
        String jsonString;
        try (InputStream stream = jsonArrayUrl.openStream()) {
            jsonString = IOUtils.toString(stream);
        }
        
        JSONArray jsonArray = new JSONArray(jsonString);
        return jsonArray;
    }
    
}
