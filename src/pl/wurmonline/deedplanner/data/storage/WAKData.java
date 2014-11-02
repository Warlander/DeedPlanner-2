package pl.wurmonline.deedplanner.data.storage;

import java.util.HashMap;
import pl.wurmonline.deedplanner.data.*;

public class WAKData {

    public static final HashMap<Integer, GroundData> grounds = new HashMap<>();
    public static final HashMap<Integer, WallData> walls = new HashMap<>();
    public static final HashMap<Integer, ObjectLocation> locations = new HashMap<>();
    public static final HashMap<Integer, GameObjectData> objects = new HashMap<>();
    
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
        
        putLocation(0x01, ObjectLocation.TOP_LEFT);
        putLocation(0x09, ObjectLocation.TOP_CENTER);
        putLocation(0x11, ObjectLocation.TOP_RIGHT);
        putLocation(0x19, ObjectLocation.MIDDLE_LEFT);
        putLocation(0x21, ObjectLocation.MIDDLE_CENTER);
        putLocation(0x29, ObjectLocation.MIDDLE_RIGHT);
        putLocation(0x31, ObjectLocation.BOTTOM_LEFT);
        putLocation(0x39, ObjectLocation.BOTTOM_CENTER);
        putLocation(0x41, ObjectLocation.BOTTOM_RIGHT);
        
        putObject(1, null); //token
        putObject(2, "gAltar"); //gold altar
        putObject(3, "sAltar"); //silver altar
        putObject(4, "stAltar"); //stone altar
        putObject(5, "wAltar"); //wood altar
        putObject(6, "aTarget"); //archery target
        putObject(7, null); //large barrel
        putObject(8, null); //bed
        putObject(9, null); //BSB
        putObject(10, null); //copper candelabra
        putObject(11, null); //gold candelabra
        putObject(12, null); //silver candelabra
        putObject(13, "chair");
        putObject(14, "lChest");
        putObject(15, "coffin");
        putObject(16, null); //forge
        putObject(17, "foun");
        putObject(18, null); //FSB
        putObject(19, null); //brass hanglamp
        putObject(20, null); //bronze hanglamp
        putObject(21, null); //copper hanglamp
        putObject(22, null); //gold hanglamp
        putObject(23, null); //silver hanglamp
        putObject(24, null); //iron torch
        putObject(25, null); //loom
        putObject(26, "stall"); //marketstall 1
        putObject(27, "stall"); //marketstall 2
        putObject(28, null); //oven
        putObject(29, null); //spirit castle
        putObject(30, null); //stone bench
        putObject(31, "lTable");
        putObject(32, "rTable");
        putObject(33, "sTable");
        putObject(34, null); //HOTS tower
        putObject(35, null); //JENN tower
        putObject(36, null); //MOL tower
        putObject(37, null); //training doll
        putObject(38, null); //trash bin
        putObject(39, null); //well
    }
    
    private static void putGround(int id, String shortname) {
        grounds.put(id, Data.grounds.get(shortname));
    }
    
    private static void putWall(int id, String shortname) {
        walls.put(id, Data.walls.get(shortname));
    }
    
    private static void putLocation(int id, ObjectLocation location) {
        locations.put(id, location);
    }
    
    private static void putObject(int id, String shortname) {
        objects.put(id, Data.objects.get(shortname));
    }
    
}
