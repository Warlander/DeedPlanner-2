package pl.wurmonline.deedplanner.data;

import pl.wurmonline.deedplanner.Constants;

public final class HouseResults {

    public final Materials materials;
    public final int carpentry;
    
    public HouseResults(Materials materials, int carpentry) {
        this.materials = materials;
        this.carpentry = carpentry;
    }
    
    public String toString() {
        StringBuilder build = new StringBuilder();
        build.append("You need ").append(carpentry).append(" carpentry to build this house.").append(Constants.ENTER);
        build.append(Constants.ENTER);
        build.append(materials.toString());
        return build.toString();
    }
    
}
