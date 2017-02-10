varying vec2 texture_coordinate;

uniform sampler2D tex;

void main() {
    gl_FragColor = texture2D(tex, vec2(texture_coordinate))*gl_Color;
}