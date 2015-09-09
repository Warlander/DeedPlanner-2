package pl.wurmonline.deedplanner.data;

import java.util.ArrayList;
import pl.wurmonline.deedplanner.util.jogl.Mesh;

public final class RoofType {

    public static final RoofType[] roofTypes;
    
    public final Mesh mesh;
    private final byte[][] conditions;
    
    static {
        ArrayList<RoofType> list = new ArrayList<>();
        
        list.add(new RoofType(new Mesh("Data/Special/side.dae", "roof", null, 1),
                              new byte[][] {{ 1, 1, 1},
                                            { 0, 0, 0},
                                            {-1,-2,-1}}));
        
        list.add(new RoofType(new Mesh("Data/Special/sideCorner.dae", "roof", null, 1),
                              new byte[][] {{-1,-2,-1},
                                            { 0, 0,-2},
                                            { 1, 0,-1}}));
        
        list.add(new RoofType(new Mesh("Data/Special/sideCut.dae", "roof", null, 1),
                              new byte[][] {{ 1, 1, 1},
                                            { 0, 0, 1},
                                            {-2, 0, 1}}));
        
        list.add(new RoofType(new Mesh("Data/Special/sideToSpine.dae", "roof", null, 1),
                              new byte[][] {{ 1, 0,-2},
                                            { 1, 0, 0},
                                            { 1, 0,-2}}));
        
        list.add(new RoofType(new Mesh("Data/Special/spine.dae", "roof", null, 1),
                              new byte[][] {{-1,-2,-1},
                                            { 0, 0, 0},
                                            {-1,-2,-1}}));
        
        list.add(new RoofType(new Mesh("Data/Special/spineEnd.dae", "roof", null, 1),
                              new byte[][] {{-1,-2,-1},
                                            { 0, 0,-2},
                                            {-1,-2,-1}}));
        
        list.add(new RoofType(new Mesh("Data/Special/spineEndUp.dae", "roof", null, 1),
                              new byte[][] {{-1,-2,-1},
                                            { 0, 0, 3},
                                            { 1, 0,-1}}));
        
        list.add(new RoofType(new Mesh("Data/Special/spineEndUp.dae", "roof", null,
                                new float[] {-1.0f, 1.0f, 1.0f}),
                              new byte[][] {{-1,-2,-1},
                                            { 3, 0, 0},
                                            {-1, 0, 1}}));

        list.add(new RoofType(new Mesh("Data/Special/spineCorner.dae", "roof", null, 1),
                              new byte[][] {{-1,-2,-1},
                                            {-2, 0, 0},
                                            {-1, 0,-2}}));
        
        list.add(new RoofType(new Mesh("Data/Special/spineCornerUp.dae", "roof", null, 1),
                              new byte[][] {{-2, 0,-2},
                                            { 0, 0, 0},
                                            { 1, 0,-2}}));
        
        list.add(new RoofType(new Mesh("Data/Special/spineCross.dae", "roof", null, 1),
                              new byte[][] {{-2, 0,-2},
                                            { 0, 0, 0},
                                            {-2, 0,-2}}));
        
        list.add(new RoofType(new Mesh("Data/Special/spineTCross.dae", "roof", null, 1),
                              new byte[][] {{-1, 0,-2},
                                            {-2, 0, 0},
                                            {-1, 0,-2}}));
        
        list.add(new RoofType(new Mesh("Data/Special/spineTip.dae", "roof", null, 1),
                              new byte[][] {{-1,-2,-1},
                                            {-2, 0,-2},
                                            {-1,-2,-1}}));
        
        list.add(new RoofType(new Mesh("Data/Special/levelsCross.dae", "roof", null, 1),
                              new byte[][] {{-2, 0, 1},
                                            { 0, 0, 0},
                                            { 1, 0,-2}}));
        
        roofTypes = new RoofType[list.size()];
        list.toArray(roofTypes);
    }
    
    private RoofType(Mesh mesh, byte[][] conditions) {
        this.mesh = mesh;
        this.conditions = conditions;
    }
    
    public byte checkMatch(Tile tile, int height) {
        if (checkCase(tile, height, conditions)) {
            return 0;
        }
        else if (checkCase(tile, height, rotate(conditions, 3))) {
            return 1;
        }
        else if (checkCase(tile, height, rotate(conditions, 2))) {
            return 2;
        }
        else if (checkCase(tile, height, rotate(conditions, 1))) {
            return 3;
        }
        return -1;
    }
    
    private boolean checkCase(Tile tile, int height, byte[][] check) {
        int cX=0;
        int cY=0;
        int roof;
        
        Map map = tile.getMap();
        
        for (int x=-1; x<=1; x++) {
            for (int y=-1; y<=1; y++) {
                if (map.getTile(tile, x, y)==null) {
                    roof=-1;
                }
                else if (!(map.getTile(tile, x, y).getTileContent(height) instanceof Roof)) {
                    roof=-1;
                }
                else {
                    roof = ((Roof) map.getTile(tile, x, y).getTileContent(height)).level;
                }
                switch (check[cX][cY]) {
                    case -2:
                        if (((Roof) tile.getTileContent(height)).level<=roof) {
                            return false;
                        }
                        break;
                    case -1:
                        if (((Roof) tile.getTileContent(height)).level<roof) {
                            return false;
                        }
                        break;
                    case 0:
                        if (((Roof) tile.getTileContent(height)).level!=roof) {
                            return false;
                        }
                        break;
                    case 1:
                        if (((Roof) tile.getTileContent(height)).level>roof) {
                            return false;
                        }
                        break;
                    case 2:
                        if (((Roof) tile.getTileContent(height)).level>=roof) {
                            return false;
                        }
                        break;
                }
                cY++;
            }
            cX++;
            cY=0;
        }
        return true;
    }
    
    private byte[][] rotate(byte[][] in, int r) {
        byte[][] out = new byte[3][3];
        int i=0;
        int i2=0;
        
        switch (r) {
            case 0:
                return in;
            case 1:
                for (int x=0; x<=2; x++) {
                    for (int y=2; y>=0; y--) {
                        out[x][y] = in[i][i2];
                        i++;
                    }
                    i2++;
                    i=0;
                }
                return out;
            case 2:
                for (int x=2; x>=0; x--) {
                    for (int y=2; y>=0; y--) {
                        out[x][y] = in[i][i2];
                        i2++;
                    }
                    i++;
                    i2=0;
                }
                return out;
            case 3:
                for (int x=2; x>=0; x--) {
                    for (int y=0; y<=2; y++) {
                        out[x][y] = in[i][i2];
                        i++;
                    }
                    i2++;
                    i=0;
                }
                return out;
        }
        return out;
    }
    
}
