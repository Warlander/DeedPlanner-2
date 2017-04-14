package pl.wurmonline.deedplanner.graphics.wom;

import pl.wurmonline.deedplanner.graphics.texture.SimpleTex;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.media.opengl.GL2;
import org.joml.Vector3f;
import org.w3c.dom.*;
import pl.wurmonline.deedplanner.graphics.texture.Material;
import pl.wurmonline.deedplanner.util.DeedPlannerException;
import pl.wurmonline.deedplanner.util.DeedPlannerRuntimeException;
import pl.wurmonline.deedplanner.util.Log;
import pl.wurmonline.deedplanner.util.jogl.Renderable;

public final class Model implements Renderable {
    
    private final String location;
    private final String tag;
    private final Vector3f scale;
    
    private final boolean loadTextures;
    private final String oneIncludedMesh;
    private final Map<String, String> textureOverrides;
    
    private Mesh[] meshes;
    
    public Model(String location) {
        this(location, new Vector3f(1, 1, 1), true);
    }
    
    public Model(String location, boolean loadTextures) {
        this(location, new Vector3f(1, 1, 1), loadTextures);
    }
    
    public Model(String location, Vector3f scale, boolean loadTextures) {
        this.location = location;
        this.tag = "";
        this.scale = scale;
        this.loadTextures = loadTextures;
        
        this.textureOverrides = new HashMap<>();
        this.oneIncludedMesh = null;
    }
    
    public Model(Element node) {
        this.tag = node.getAttribute("tag");
        location = node.getAttribute("location");
        String scaleStr = node.getAttribute("scale");
        float scaleFloat = parseScale(scaleStr);
        scale = new Vector3f();
        scale.x = scale.y = scale.z = scaleFloat;
        
        loadTextures = !node.getAttribute("loadTextures").equals("false");
        
        this.textureOverrides = new HashMap<>();
        
        NodeList overrides = node.getElementsByTagName("override");
        for (int i = 0; i < overrides.getLength(); i++) {
            Element override = (Element) overrides.item(i);
            
            String mesh = override.getAttribute("mesh");
            String texture = override.getAttribute("texture");
            textureOverrides.put(mesh, texture);
        }
        
        NodeList includes = node.getElementsByTagName("include");
        //assumption - only one include allowed for now
        if (includes.getLength() == 1) {
            Element include = (Element) includes.item(0);
            oneIncludedMesh = include.getAttribute("mesh");
        }
        else if (includes.getLength() > 1) {
            throw new DeedPlannerRuntimeException("Only one include per model allowed for now");
        }
        else {
            oneIncludedMesh = null;
        }
    }
    
    private float parseScale(String scaleStr) {
        try {
            return Float.parseFloat(scaleStr);
        }
        catch (NumberFormatException ex) {
            return 1;
        }
    }
    
    public void render(GL2 g) {
        if (meshes == null) {
            try {
                loadMeshes(g);
            } catch (IOException | DeedPlannerException ex) {
                Log.err(ex);
                meshes = new Mesh[0];
            }
        }
        
        for (Mesh mesh : meshes) {
            mesh.render(g);
        }
    }
    
    private void loadMeshes(GL2 g) throws IOException, DeedPlannerException {
        byte[] byteArray = Files.readAllBytes(Paths.get(location));
        ByteBuffer buffer = ByteBuffer.wrap(byteArray);
        buffer.rewind();
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        
        int meshCount = buffer.getInt();
        List<Mesh> meshesList = new ArrayList(meshCount);
        
        for (int i = 0; i < meshCount; i++) {
            MeshData data = loadMeshData(buffer);
            int displayList = data.createModel(g);
            
            int materialCount = buffer.getInt();
            Material material = loadMaterial(buffer);
            
            //we need to load material first in order to keep the proper buffer position
            if (data.getName().contains("BoundingBox") || data.getName().contains("Lod") || !isMeshIncluded(data.getName())) {
                continue;
            }
            
            SimpleTex texture = (SimpleTex) material.getTexture();
            if (textureOverrides.containsKey(data.getName())) {
                String textureOverride = textureOverrides.get(data.getName());
                texture = SimpleTex.getTexture(textureOverride);
            }
            else if (!loadTextures) {
                texture = null;
            }
            
            material.setTexture(texture);
            
            meshesList.add(new Mesh(this, texture, displayList, data.getName()));
        }
        
        meshes = meshesList.toArray(new Mesh[0]);
    }
    
    private boolean isMeshIncluded(String name) {
        if (oneIncludedMesh == null) {
            return true;
        }
        
        return oneIncludedMesh.equals(name);
    }
    
    private MeshData loadMeshData(ByteBuffer buffer) {
        boolean hasTangents = buffer.get() != 0;
        boolean hasBinormal = buffer.get() != 0;
        boolean hasVertexColor = buffer.get() != 0;
        
        String name = readString(buffer);
        Log.out(this, "Loading mesh "+name);
        
        int verticesCount = buffer.getInt();
        
        float[] vertexArray = new float[verticesCount * 3];
        float[] normalArray = new float[verticesCount * 3];
        float[] uvArray = new float[verticesCount * 2];    
        
        for (int i = 0; i < verticesCount; i++) {
            vertexArray[i * 3] = buffer.getFloat();
            vertexArray[i * 3 + 1] = buffer.getFloat();
            vertexArray[i * 3 + 2] = buffer.getFloat();
            
            normalArray[i * 3] = buffer.getFloat();
            normalArray[i * 3 + 1] = buffer.getFloat();
            normalArray[i * 3 + 2] = buffer.getFloat();
            
            uvArray[i * 2] = buffer.getFloat();
            uvArray[i * 2 + 1] = buffer.getFloat();
            
            if (hasVertexColor) {
                buffer.getFloat();
                buffer.getFloat();
                buffer.getFloat();           
            }

            if (hasTangents) {
                buffer.getFloat(); 
                buffer.getFloat();
                buffer.getFloat();
            }

            if (hasBinormal) {
                buffer.getFloat(); 
                buffer.getFloat();
                buffer.getFloat();
            }
        }
        
        int trianglesCount = buffer.getInt();
        int[] triangles = new int[trianglesCount];
        
        for (int i = 0; i < trianglesCount; ++i) {
            triangles[i] = buffer.getShort();
        } 
        
        MeshData data = new MeshData();
        data.setName(name);
        data.setVertices(vertexArray);
        data.setNormals(normalArray);
        data.setTexcoords(uvArray);
        data.setTriangles(triangles);
        
        return data;
    }
    
    private Material loadMaterial(ByteBuffer buffer) {
        String texName = readString(buffer);
        String matName = readString(buffer);
        Material material = new Material();
        
        String parentLoc = location.substring(0, location.lastIndexOf("/") + 1);
        
        String texLoc = parentLoc + texName;
        SimpleTex texture = SimpleTex.getTexture(texLoc);
        material.setTexture(texture);
        
        boolean hasMaterialProperties = buffer.get() != 0;
        
        if (hasMaterialProperties) {
            boolean hasEmissive = buffer.get() != 0;
            if (hasEmissive) {
                material.getEmissive().set(buffer.getFloat(), buffer.getFloat(), buffer.getFloat(), buffer.getFloat());
            }
            
            boolean hasShininess = buffer.get() != 0;
            if (hasShininess) {
                material.setShininess(buffer.getFloat());
            }
            
            boolean hasSpecular = buffer.get() != 0;
            if (hasSpecular) {
                material.getSpecular().set(buffer.getFloat(), buffer.getFloat(), buffer.getFloat(), buffer.getFloat());
            }
            
            boolean hasTransparencyColor = buffer.get() != 0;
            if (hasTransparencyColor) {
                material.getTransparencyColor().set(buffer.getFloat(), buffer.getFloat(), buffer.getFloat(), buffer.getFloat());
            }
        }
        
        return material;
    }
    
    private String readString(ByteBuffer buffer) {
        int length = buffer.getInt();
        byte[] strArray = new byte[length];
        
        buffer.get(strArray, 0, length);

        try {
            return new String(strArray, 0, length, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Log.err(ex);
            throw new DeedPlannerRuntimeException("This exception is impossible. If it happened... Something is very, very wrong.");
        }
    }
    
    public Vector3f getScale() {
        return scale;
    }
    
    public void setScale(Vector3f other) {
        scale.set(other);
    }
    
    public String getTag() {
        return tag;
    }
    
    public String toString() {
        return "Model "+location;
    }
    
}
