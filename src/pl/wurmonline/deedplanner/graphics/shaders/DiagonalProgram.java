package pl.wurmonline.deedplanner.graphics.shaders;

import javax.media.opengl.GL2;
import pl.wurmonline.deedplanner.data.RoadDirection;

public class DiagonalProgram extends Program {

    private int directionLocation;
    private int tex0Location;
    private int tex1Location;
    private int tex2Location;
    
    private int direction;
    
    public DiagonalProgram() {
        super(ShaderUtils.loadVertFragShaders("Diagonal"));
    }

    protected void findUniformsLocations(GL2 g, int programId) {
        directionLocation = g.glGetUniformLocation(programId, "dir");
        tex0Location = g.glGetUniformLocation(programId, "tex0");
        tex1Location = g.glGetUniformLocation(programId, "tex1");
        tex2Location = g.glGetUniformLocation(programId, "tex2");
    }

    public void updateUniforms(GL2 g) {
        g.glUniform1i(directionLocation, direction);
        g.glUniform1i(tex0Location, 0);
        g.glUniform1i(tex1Location, 1);
        g.glUniform1i(tex2Location, 2);
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
    
}
