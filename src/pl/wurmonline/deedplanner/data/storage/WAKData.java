package pl.wurmonline.deedplanner.data.storage;

import java.util.HashMap;
import pl.wurmonline.deedplanner.data.GroundData;
import pl.wurmonline.deedplanner.data.WallData;

public class WAKData {

    public static final HashMap<Integer, GroundData> grounds = new HashMap<>();
    public static final HashMap<Integer, WallData> walls = new HashMap<>();
    
    static {
        putGround(0, "gr");
        putGround(1, "ro"); //placeholder
        putGround(2, "cy");
        putGround(3, "cl");
        putGround(4, "cs");
        putGround(5, "ro"); //placeholder
        putGround(6, "di");
        putGround(7, "ro"); //placeholder
        putGround(8, "ef");
        putGround(9, "eg");
        putGround(10, "fi");
        putGround(11, "fo");
        putGround(12, "fm");
        putGround(13, "ro"); //placeholder
        putGround(14, "gr");
        putGround(15, "gv");
        putGround(16, "fm");
        putGround(17, "ro"); //placeholder
        putGround(18, "la");
        putGround(19, "ro"); //placeholder
        putGround(20, "ma");
        putGround(21, "mo");
        putGround(22, "my");
        putGround(23, "pd");
        putGround(24, "pe");
        putGround(25, "pl");
        putGround(26, "ro"); //placeholder
        putGround(27, "ro");
        putGround(28, "sa");
        putGround(29, "ro"); //placeholder
        putGround(30, "sl");
        putGround(31, "st");
        putGround(32, "ta");
        putGround(33, "ro"); //placeholder
        putGround(34, "tu");
        putGround(35, "gr");
        putGround(36, "wa");
        putGround(37, "ro"); //placeholder
        
        putWall(1, "iFence");
        putWall(2, "iFenceG");
        putWall(3, "lsFence");
        putWall(4, "pFence");
        putWall(5, "pFenceG");
        putWall(6, "sWall");
        putWall(7, "sDoor");
        putWall(8, "tsFence");
        putWall(9, "wFence");
        putWall(10, "wFenceG");
        putWall(11, "wWall");
        putWall(12, "wDoor");
    }
    
    private static void putGround(int id, String shortname) {
        grounds.put(id, Data.grounds.get(shortname));
    }
    
    private static void putWall(int id, String shortname) {
        walls.put(id, Data.walls.get(shortname));
    }
    
}
