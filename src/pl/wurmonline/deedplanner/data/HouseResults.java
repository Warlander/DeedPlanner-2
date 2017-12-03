package pl.wurmonline.deedplanner.data;

public final class HouseResults {

    public final Materials materials;
    public final int carpentry;
    
    public HouseResults(Materials materials, int carpentry) {
        this.materials = materials;
        this.carpentry = carpentry;
    }
    
    public String toString() {
        StringBuilder build = new StringBuilder();
        build.append(java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle").getString("YOU NEED ")).append(carpentry).append(java.util.ResourceBundle.getBundle("pl/wurmonline/deedplanner/forms/Bundle").getString(" CARPENTRY TO BUILD THIS HOUSE.")).append(System.lineSeparator());
        build.append(System.lineSeparator());
        build.append(materials.toString());
        return build.toString();
    }
    
}
