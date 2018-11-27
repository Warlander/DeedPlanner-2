package pl.wurmonline.deedplanner.graphics.texture;

import org.joml.Vector4f;

public class Material {
    
    private Tex texture;
    
    private float shininess;
    private final Vector4f emissive;
    private final Vector4f specular;
    private final Vector4f transparencyColor;
    
    public Material() {
        shininess = 0.1f;
        emissive = new Vector4f(0, 0, 0, 0);
        specular = new Vector4f(1, 1, 1, 1);
        transparencyColor = new Vector4f(1, 1, 1, 1);
    }
    
    public boolean hasEmissive() {
        return emissive.w != 0;
    }
    
    public boolean hasTransparency() {
        return transparencyColor.w != 0;
    }

    public float getShininess() {
        return shininess;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }

    public Vector4f getEmissive() {
        return emissive;
    }

    public Vector4f getSpecular() {
        return specular;
    }

    public Vector4f getTransparencyColor() {
        return transparencyColor;
    }

    public Tex getTexture() {
        return texture;
    }

    public void setTexture(Tex texture) {
        this.texture = texture;
    }
    
}
