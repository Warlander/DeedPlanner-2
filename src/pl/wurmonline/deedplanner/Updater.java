package pl.wurmonline.deedplanner;

import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONObject;
import pl.wurmonline.deedplanner.util.DeedPlannerRuntimeException;

public class Updater {
    
    public static boolean checkUpdate(JSONArray updateInfoArray) {
        JSONObject latestRelease = updateInfoArray.optJSONObject(0);

        String tag = latestRelease.getString("tag_name");
        int[] newVersionParts = parseVersionString(tag);
        if (newVersionParts.length != 3) {
            throw new DeedPlannerRuntimeException("Invalid version string: must be in sematic versioning format, like \"1.0.0\"");
        }
        int[] currentVersionParts = parseVersionString(Constants.VERSION_STRING);
        boolean newerVersion = isVersionNewer(currentVersionParts, newVersionParts);
        return newerVersion;
    }
    
    public static String getLatestReleaseUrl(JSONArray updateInfoArray) {
        JSONObject latestRelease = updateInfoArray.optJSONObject(0);
        
        return latestRelease.getString("html_url");
    }
    
    public static String getLatestDownloadUrl(JSONArray updateInfoArray) {
        JSONObject latestRelease = updateInfoArray.optJSONObject(0);
        
        JSONArray assets = latestRelease.getJSONArray("assets");
        JSONObject fileInformation = assets.getJSONObject(0);
        String downloadUrl = fileInformation.getString("browser_download_url");
        
        return downloadUrl;
    }
    
    public static String getLatestDownloadName(JSONArray updateInfoArray) {
        JSONObject latestRelease = updateInfoArray.optJSONObject(0);
        
        JSONArray assets = latestRelease.getJSONArray("assets");
        JSONObject fileInformation = assets.getJSONObject(0);
        String downloadUrl = fileInformation.getString("browser_download_url");
        
        return downloadUrl;
    }
    
    public static String getNewestVersionString(JSONArray updateInfoArray) {
        JSONObject latestRelease = updateInfoArray.optJSONObject(0);
        
        String tag = latestRelease.getString("tag_name");
        String versionString = tag.replace("v", "");
        
        return versionString;
    }
    
    public static String parseMarkdownChangelog(JSONArray updateInfoArray) {
        StringBuilder changelog = new StringBuilder();
        
        for (Object obj : updateInfoArray) {
            if (!(obj instanceof JSONObject)) {
                continue;
            }
            
            JSONObject releaseJson = (JSONObject) obj;
            
            String tag = releaseJson.getString("tag_name");
            int[] newVersionParts = parseVersionString(tag);
            if (newVersionParts.length != 3) {
                throw new DeedPlannerRuntimeException("Invalid version string: must be in sematic versioning format, like \"1.0.0\"");
            }
            int[] currentVersionParts = parseVersionString(Constants.VERSION_STRING);
            boolean newVersion = isVersionNewer(currentVersionParts, newVersionParts);
            if (!newVersion) {
                break;
            }
            
            String name = releaseJson.getString("name");
            
            String rawDateTime = releaseJson.getString("published_at");
            String rawDate = rawDateTime.split("T")[0];
            String[] dateParts = rawDate.split("-");
            String year = dateParts[0];
            String month = dateParts[1];
            String day = dateParts[2];
            String assembledDate = day + "." + month + "." + year;
            
            String body = releaseJson.getString("body");
            
            changelog.append("# ").append(name).append(" #").append("\r\n");
            changelog.append("## ").append(assembledDate).append(" ##").append("\r\n");
            changelog.append(body).append("\r\n\r\n");
        }
        
        return changelog.toString();
    }
    
    private static int[] parseVersionString(String versionString) {
        versionString = versionString.replace("v", "");
        String[] versionPartsString = versionString.split("\\.");
        int[] versionParts = Arrays.stream(versionPartsString).mapToInt(Integer::parseInt).toArray();
        return versionParts;
    }
    
    private static boolean isVersionNewer(int[] currentVersion, int[] newVersion) {
        int newMajor = newVersion[0];
        int newMinor = newVersion[1];
        int newPatch = newVersion[2];
        int currentMajor = currentVersion[0];
        int currentMinor = currentVersion[1];
        int currentPatch = currentVersion[2];
        
        if (newMajor > currentMajor) {
            return true;
        }
        if (newMajor == currentMajor && newMinor > currentMinor) {
            return true;
        }
        if (newMajor == currentMajor && newMinor == currentMinor && newPatch > currentPatch) {
            return true;
        }
        
        return false;
    }
    
}
