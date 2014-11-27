package pl.wurmonline.deedplanner.util.jogl;

import javax.media.opengl.GL2;
import pl.wurmonline.deedplanner.util.DeedPlannerException;

public class MeshData {
    
    private float[] vertices;
    private float[] normals;
    private float[] texcoords;
    private int[] triangles;
    
    MeshData() {
        
    }
    
    public int createModel(GL2 g, boolean ladder) throws DeedPlannerException {
        if (!isValid()) {
            throw new DeedPlannerException("Invalid model");
        }
        
        final int increase;
        if (normals!=null) {
            increase = 3;
        }
        else {
            increase = 2;
        }
        
        int list = g.glGenLists(1);
        g.glNewList(list, GL2.GL_COMPILE);
            g.glRotatef(90, 1, 0, 0);
            g.glBegin(GL2.GL_TRIANGLES);
                for (int i=0; i<triangles.length; i+=increase) {
                    int vertLoc = triangles[i]*3;
                    int normalLoc;
                    int textureLoc;
                    if (normals!=null) {
                        normalLoc = triangles[i+1]*3;
                        textureLoc = triangles[i+2]*2;
                    }
                    else {
                        normalLoc = 0;
                        textureLoc = triangles[i+1]*2;
                    }
                    
                    g.glTexCoord2f(texcoords[textureLoc], texcoords[textureLoc+1]);
                    if (normals!=null) {
                        g.glNormal3f(normals[normalLoc], normals[normalLoc+1], normals[normalLoc+2]);
                    }
                    if (ladder) {
                        g.glVertex3f(vertices[vertLoc], vertices[vertLoc+2], -vertices[vertLoc+1]);
                    }
                    else {
                        g.glVertex3f(vertices[vertLoc], vertices[vertLoc+1], vertices[vertLoc+2]);
                    }
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
    
}
