package pl.wurmonline.deedplanner.util.jogl;

import javax.media.opengl.GL2;
import org.w3c.dom.*;
import pl.wurmonline.deedplanner.util.*;

public class Color implements XMLSerializable {

    public final float r;
    public final float g;
    public final float b;
    public final float a;
    
    public Color(Element node) {
        String type = node.getAttribute("type");
        final String rStr = node.getAttribute("r");
        final String gStr = node.getAttribute("g");
        final String bStr = node.getAttribute("b");
        final String aStr = node.getAttribute("a");
        switch (type) {
            case "dec":
                this.r = Float.parseFloat(rStr);
                this.g = Float.parseFloat(gStr);
                this.b = Float.parseFloat(bStr);
                if (aStr.isEmpty()) {
                    this.a = 1;
                }
                else {
                    this.a = Float.parseFloat(aStr);
                }
                break;
            case "byte":
                this.r = Float.parseFloat(rStr)/255f;
                this.g = Float.parseFloat(gStr)/255f;
                this.b = Float.parseFloat(bStr)/255f;
                if (aStr.isEmpty()) {
                    this.a = 1;
                }
                else {
                    this.a = Float.parseFloat(aStr)/255f;
                }
                break;
            default:
                throw new DeedPlannerRuntimeException("Invalid color type");
        }
    }
    
    public Color(java.awt.Color color) {
        this.r = (float)color.getRed()/255f;
        this.g = (float)color.getGreen()/255f;
        this.b = (float)color.getBlue()/255f;
        this.a = (float)color.getAlpha()/255f;
    }
    
    public Color() {
        this(1, 1, 1, 1);
    }
    
    public Color(float r, float g, float b) {
        this(r, g, b, 1);
    }
    
    public Color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }
    
    public void use(GL2 gl) {
        use(gl, 1);
    }
    
    public void use(GL2 gl, float mod) {
        gl.glColor4f(r*mod, g*mod, b*mod, a);
    }
    
    public java.awt.Color toAWTColor() {
        return new java.awt.Color(r, g, b, a);
    }

    public void serialize(Document doc, Element root) {
        Element colorElement = doc.createElement("color");
        colorElement.setAttribute("type", "dec");
        colorElement.setAttribute("r", Float.toString(r));
        colorElement.setAttribute("g", Float.toString(g));
        colorElement.setAttribute("b", Float.toString(b));
        colorElement.setAttribute("a", Float.toString(a));
        root.appendChild(colorElement);
    }
    
}
