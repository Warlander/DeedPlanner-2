package pl.wurmonline.deedplanner.graphics.wom;

import com.jogamp.opengl.GL2;
import pl.wurmonline.deedplanner.util.DeedPlannerException;

public class MeshData {
    
    private String name;
    
    private float[] vertices;
    private float[] normals;
    private float[] texcoords;
    private int[] triangles;
    
    MeshData() {
        
    }
    
    public int createDisplayList(GL2 g) throws DeedPlannerException {
        if (!isValid()) {
            throw new DeedPlannerException("Invalid model");
        }
        
        int list = g.glGenLists(1);
        g.glNewList(list, GL2.GL_COMPILE);
            g.glBegin(GL2.GL_TRIANGLES);
                for (int i=0; i<triangles.length; i++) {
                    int point = triangles[i];
                    int vertLoc = point * 3;
                    int normalLoc = point * 3;
                    int textureLoc = point * 2;

                    g.glTexCoord2f(texcoords[textureLoc], 1 - texcoords[textureLoc+1]);
                    g.glNormal3f(normals[normalLoc], normals[normalLoc+1], normals[normalLoc+2]);
                    g.glVertex3f(vertices[vertLoc], -vertices[vertLoc+2], vertices[vertLoc+1]);
                }
            g.glEnd();
        g.glEndList();
        
        return list;
    }
    
    public boolean isValid() {
        return vertices!=null && texcoords!=null && triangles!=null;
    }
    
    public void setVertices(float[] vertices) {
        this.vertices = vertices;
    }
    
    public void setNormals(float[] normals) {
        this.normals = normals;
    }
    
    public void setTexcoords(float[] texcoords) {
        this.texcoords = texcoords;
    }
    
    public void setTriangles(int[] triangles) {
        this.triangles = triangles;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean isNameContaining(String other) {
        return name.toUpperCase().contains(other.toUpperCase());
    }
    
    public String toString() {
        return name;
    }
}