varying vec2 tex_coord;

void main() {
    gl_Position = ftransform();

    tex_coord = vec2(gl_MultiTexCoord0);
    gl_FrontColor = gl_Color;
}