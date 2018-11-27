package pl.wurmonline.deedplanner.graphics.shaders;

public final class Shaders {  
    
    private static Shaders shaders;
    
    public static Shaders getShaders() {
        if (shaders==null) {
            shaders = new Shaders();
        }
        
        return shaders;
    }
    
    public final Program simple;
    public final Program diagonal;
    
    public Shaders() {
        simple = ShaderUtils.loadVertFragProgram("Simple");
        diagonal = ShaderUtils.loadVertFragProgram("Diagonal");
    }
    
    public String toString() {
        return "shaders instance";
    }
    
}
