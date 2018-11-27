package pl.wurmonline.deedplanner.data.bridges;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pl.wurmonline.deedplanner.Globals;
import pl.wurmonline.deedplanner.data.Map;
import pl.wurmonline.deedplanner.data.Tile;
import pl.wurmonline.deedplanner.util.XMLSerializable;

public class Bridge implements XMLSerializable {
    
    public static Bridge createBridge(Map map, Element element) {
        BridgeData data = BridgeData.getData(element.getAttribute("data"));
        BridgeType type = BridgeType.getType(element.getAttribute("type"));
        int firstX = Integer.parseInt(element.getAttribute("firstX"));
        int firstY = Integer.parseInt(element.getAttribute("firstY"));
        Tile firstTile = map.getTile(firstX, firstY);
        int firstFloor = Integer.parseInt(element.getAttribute("firstFloor"));
        int secondX = Integer.parseInt(element.getAttribute("secondX"));
        int secondY = Integer.parseInt(element.getAttribute("secondY"));
        Tile secondTile = map.getTile(secondX, secondY);
        int secondFloor = Integer.parseInt(element.getAttribute("secondFloor"));
        int additionalData = Integer.parseInt(element.getAttribute("sag"));
        boolean verticalOrientation = Boolean.parseBoolean(element.getAttribute("orientation"));
        boolean surfaced = true;
        if (element.hasAttribute("surfaced")) {
            surfaced = Boolean.parseBoolean(element.getAttribute("surfaced"));
        }
        
        BridgePartType[] segments = BridgePartType.decodeSegments(element.getTextContent());
        
        Bridge bridge = new Bridge(map, data, type, firstTile, secondTile, firstFloor, secondFloor, segments, additionalData, verticalOrientation, surfaced);
        data.constructBridge(map, bridge, firstX, firstY, secondX, secondY, firstFloor, secondFloor, type, segments, additionalData, verticalOrientation);
        map.addBridge(bridge);
        return bridge;
    }
    
    public static Bridge createBridge(Map map, Tile firstTile, Tile secondTile, int firstFloor, int secondFloor, BridgeData data, BridgeType type, BridgePartType[] segments, int additionalData) {
        if (!data.isCompatibleType(type)) {
            return null;
        }
        
        boolean surfaced = Globals.floor >= 0;
        
        int startX = Math.min(firstTile.getX(), secondTile.getX());
        int endX = Math.max(firstTile.getX(), secondTile.getX());
        int startY = Math.min(firstTile.getY(), secondTile.getY());
        int endY = Math.max(firstTile.getY(), secondTile.getY());
        
        boolean verticalOrientation = (endY - startY) > (endX - startX);
        
        if (verticalOrientation) {
            startY += 1;
            endY -= 1;
        }
        else {
            startX += 1;
            endX -= 1;
        }
        
        firstTile = map.getTile(startX, startY);
        secondTile = map.getTile(endX, endY);
        
        int maxWidth = data.getMaxWidth() - 1;
        if (maxWidth < endX - startX && maxWidth < endY - startY) {
            return null;
        }
        
        Bridge bridge = new Bridge(map, data, type, firstTile, secondTile, firstFloor, secondFloor, segments, additionalData, verticalOrientation, surfaced);
        data.constructBridge(map, bridge, startX, startY, endX, endY, firstFloor, secondFloor, type, segments, additionalData, verticalOrientation);
        map.addBridge(bridge);
        
        return bridge;
    }
    
    private final Map map;
    private final BridgeData data;
    private final BridgeType type;
    private final Tile firstTile;
    private final Tile secondTile;
    private final int firstFloor;
    private final int secondFloor;
    private final BridgePartType[] segments;
    private final int additionalData;
    private final boolean verticalOrientation;
    private final boolean aboveGround;
    
    private Bridge(Map map, BridgeData data, BridgeType type, Tile firstTile, Tile secondTile, int firstFloor, int secondFloor, BridgePartType[] segments, int additionalData, boolean verticalOrientation, boolean aboveGround) {
        this.map = map;
        this.data = data;
        this.type = type;
        this.firstTile = firstTile;
        this.secondTile = secondTile;
        this.firstFloor = firstFloor;
        this.secondFloor = secondFloor;
        this.segments = segments;
        this.additionalData = additionalData;
        this.verticalOrientation = verticalOrientation;
        this.aboveGround = aboveGround;
    }
    
    public void destroy() {
        int startX = Math.min(firstTile.getX(), secondTile.getX());
        int endX = Math.max(firstTile.getX(), secondTile.getX());
        int startY = Math.min(firstTile.getY(), secondTile.getY());
        int endY = Math.max(firstTile.getY(), secondTile.getY());
        
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                map.getTile(x, y).setBridgePart(null, aboveGround);
            }
        }
        
        map.removeBridge(this);
    }
    
    public BridgeData getData() {
        return data;
    }
    
    public BridgeType getType() {
        return type;
    }
    
    public boolean isAboveGround() {
        return aboveGround;
    }

    public void serialize(Document doc, Element root) {
        Element bridgeElement = doc.createElement("bridge");
        bridgeElement.setAttribute("firstX", Integer.toString(firstTile.getX()));
        bridgeElement.setAttribute("firstY", Integer.toString(firstTile.getY()));
        bridgeElement.setAttribute("firstFloor", Integer.toString(firstFloor));
        bridgeElement.setAttribute("secondX", Integer.toString(secondTile.getX()));
        bridgeElement.setAttribute("secondY", Integer.toString(secondTile.getY()));
        bridgeElement.setAttribute("secondFloor", Integer.toString(secondFloor));
        bridgeElement.setAttribute("data", data.getName());
        bridgeElement.setAttribute("type", type.toString());
        bridgeElement.setAttribute("sag", Integer.toString(additionalData));
        bridgeElement.setAttribute("orientation", Boolean.toString(verticalOrientation));
        bridgeElement.setAttribute("surfaced", Boolean.toString(aboveGround));
        
        bridgeElement.setTextContent(BridgePartType.encodeSegments(segments));
        
        root.appendChild(bridgeElement);
    }
    
}
