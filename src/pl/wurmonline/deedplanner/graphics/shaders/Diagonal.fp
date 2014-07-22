varying vec2 tex_coord;

uniform sampler2D tex0, tex1, tex2;
uniform int dir;

void main() {
    bool left = false;
    bool right = false;
    bool up = false;
    bool down = false;
    
    if (dir==0) {
        left = true;
        up = true;
    }
    else if (dir==1) {
        right = true;
        up = true;
    }
    else if (dir==2) {
        left = true;
        down = true;
    }
    else {
        right = true;
        down = true;
    }

    int tex = 0;

    float x = tex_coord.x;
    float y = tex_coord.y;

    if (x>y) {
        if (x+y<1.0) {
            if (!down) {
                tex = 1;
            }
        }
        else {
            if (!right) {
                tex = 2;
            }
        }
    }
    else {
        if (x+y<1.0) {
            if (!left) {
                tex = 2;
            }
        }
        else {
            if (!up) {
                tex = 1;
            }
        }
    }
    if (tex==0) {
        gl_FragColor = texture2D(tex0, tex_coord)*gl_Color;
    }
    else if (tex==1) {
        gl_FragColor = texture2D(tex1, tex_coord)*gl_Color;
    }
    else {
        gl_FragColor = texture2D(tex2, tex_coord)*gl_Color;
    }
}