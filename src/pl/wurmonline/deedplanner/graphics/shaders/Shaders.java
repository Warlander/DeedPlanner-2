package pl.wurmonline.deedplanner.graphics.shaders;

public final class Shaders {  
    
    private static Shaders shaders;
    
    public static Shaders getShaders() {
        if (shaders==null) {
            shaders = new Shaders();
        }
        
        return shaders;
    }
    
    public final SimpleProgram simple;
    public final DiagonalProgram diagonal;
    
    public Shaders() {
        simple = new SimpleProgram();
        diagonal = new DiagonalProgram();
    }
    
    public String toString() {
        return "shaders instance";
    }
    
}
