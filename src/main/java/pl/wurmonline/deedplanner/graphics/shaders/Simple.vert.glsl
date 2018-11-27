varying vec2 texture_coordinate;

void main() {
    gl_Position = ftransform();

    texture_coordinate = vec2(gl_TextureMatrix[0] * gl_MultiTexCoord0);
    gl_FrontColor = gl_Color;
}