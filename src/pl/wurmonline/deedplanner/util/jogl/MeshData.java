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
        
        int list = g.glGenLists(1);
        g.glNewList(list, GL2.GL_COMPILE);
            g.glRotatef(90, 1, 0, 0);
            g.glBegin(GL2.GL_TRIANGLES);
                for (int i=0; i<triangles.length; i+=3) {
                    int vertLoc = triangles[i]*3;
                    int normalLoc = triangles[i+1]*3;
                    int textureLoc = triangles[i+2]*2;
                    g.glTexCoord2f(texcoords[textureLoc], texcoords[textureLoc+1]);
                    g.glNormal3f(normals[normalLoc], normals[normalLoc+1], normals[normalLoc+2]);
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
        return vertices!=null && texcoords!=null && normals!=null && triangles!=null;
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
