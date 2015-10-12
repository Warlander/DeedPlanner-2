package pl.wurmonline.deedplanner.data.bridges;

import java.util.HashMap;
import pl.wurmonline.deedplanner.data.EntityOrientation;
import pl.wurmonline.deedplanner.data.Map;
import pl.wurmonline.deedplanner.data.Materials;
import pl.wurmonline.deedplanner.data.Tile;
import pl.wurmonline.deedplanner.util.jogl.Renderable;

public abstract class BridgeData {
    
    private static final int[] height0 = {};
    private static final int[] height1 = {20};
    private static final int[] height2 = {15, 30};
    private static final int[] height3 = {10, 30, 40};
    private static final int[] height4 = {8, 28, 48, 57};
    private static final int[] height5 = {6, 22, 42, 59, 65};
    private static final int[] height6 = {5, 20, 40, 60, 75, 80};
    private static final int[] height7 = {4, 17, 35, 55, 73, 85, 90};
    private static final int[] height8 = {4, 15, 32, 52, 72, 89, 101, 105};
    private static final int[] height9 = {3, 13, 29, 48, 68, 86, 102, 112, 115};
    private static final int[] height10 = {3, 12, 27, 45, 65, 85, 103, 117, 126, 129};
    private static final int[] height11 = {3, 11, 24, 41, 60, 80, 99, 116, 129, 138, 141};
    private static final int[] height12 = {3, 10, 23, 39, 57, 77, 97, 116, 132, 144, 152, 155};
    private static final int[] height13 = {2, 10, 21, 36, 54, 73, 93, 112, 130, 145, 156, 164, 166};
    private static final int[] height14 = {2, 9, 20, 34, 51, 70, 90, 110, 129, 146, 160, 171, 178, 180};
    private static final int[] height15 = {2, 8, 18, 32, 48, 66, 86, 106, 125, 144, 160, 173, 183, 189, 191};
    private static final int[] height16 = {2, 8, 17, 30, 46, 63, 83, 103, 123, 142, 159, 175, 188, 197, 203, 205};
    private static final int[] height17 = {2, 7, 16, 28, 43, 60, 79, 98, 118, 138, 157, 174, 188, 201, 209, 215, 217};
    private static final int[] height18 = {2, 7, 15, 27, 41, 58, 76, 95, 115, 135, 155, 173, 189, 203, 215, 223, 229, 230};
    private static final int[] height19 = {2, 7, 15, 26, 39, 55, 72, 91, 111, 131, 151, 170, 187, 203, 217, 228, 236, 241, 242};
    private static final int[] height20 = {2, 6, 14, 24, 37, 53, 70, 88, 108, 128, 148, 167, 186, 203, 218, 231, 242, 249, 254, 256};
    private static final int[] height21 = {1, 6, 13, 23, 36, 50, 67, 85, 104, 124, 144, 164, 183, 201, 217, 232, 244, 254, 262, 266, 268};
    private static final int[] height22 = {1, 6, 13, 22, 34, 49, 65, 82, 101, 121, 141, 161, 180, 199, 217, 233, 247, 259, 268, 275, 280, 281};
    private static final int[] height23 = {1, 5, 12, 21, 33, 47, 62, 79, 97, 117, 137, 157, 176, 196, 214, 231, 247, 260, 272, 281, 288, 292, 293};
    private static final int[] height24 = {1, 5, 12, 21, 32, 45, 60, 77, 95, 114, 133, 153, 173, 193, 212, 230, 247, 262, 275, 286, 295, 301, 305, 306};
    private static final int[] height25 = {1, 5, 11, 20, 30, 43, 58, 74, 91, 110, 129, 149, 169, 189, 208, 227, 245, 261, 275, 288, 299, 307, 314, 317, 319};
    private static final int[] height26 = {1, 5, 11, 19, 29, 42, 56, 72, 89, 107, 126, 146, 166, 186, 206, 225, 243, 260, 276, 290, 302, 313, 321, 327, 331, 332};
    private static final int[] height27 = {1, 5, 10, 18, 28, 40, 54, 69, 86, 104, 123, 142, 162, 182, 202, 221, 240, 258, 275, 290, 304, 316, 326, 334, 339, 343, 344};
    private static final int[] height28 = {1, 4, 10, 18, 27, 39, 52, 67, 84, 101, 120, 139, 159, 179, 199, 218, 238, 256, 274, 290, 305, 318, 330, 340, 347, 353, 356, 357};
    private static final int[] height29 = {1, 4, 10, 17, 26, 38, 51, 65, 81, 98, 116, 135, 155, 175, 195, 215, 234, 253, 271, 288, 304, 319, 332, 343, 352, 360, 365, 368, 369};
    private static final int[] height30 = {1, 4, 9, 17, 26, 37, 49, 63, 79, 96, 114, 132, 152, 171, 191, 211, 231, 250, 269, 287, 304, 319, 334, 346, 357, 366, 373, 378, 382, 383};
    private static final int[] height31 = {1, 4, 9, 16, 25, 35, 48, 61, 77, 93, 110, 129, 148, 168, 187, 207, 227, 247, 266, 284, 302, 318, 333, 347, 359, 370, 379, 386, 391, 394, 395};
    private static final int[] height32 = {1, 4, 9, 16, 24, 34, 46, 60, 75, 91, 108, 126, 145, 164, 184, 204, 224, 244, 263, 282, 300, 317, 333, 348, 362, 374, 384, 393, 399, 404, 407, 408};
    private static final int[] height33 = {1, 4, 9, 15, 23, 33, 45, 58, 73, 88, 105, 123, 141, 161, 180, 200, 220, 240, 260, 279, 297, 315, 332, 348, 362, 375, 387, 397, 405, 412, 417, 419, 420};
    private static final int[] height34 = {1, 4, 8, 15, 23, 32, 44, 57, 71, 86, 103, 120, 138, 157, 177, 197, 217, 237, 257, 276, 295, 313, 331, 347, 363, 377, 390, 401, 411, 419, 425, 430, 433, 434};
    private static final int[] height35 = {1, 4, 8, 14, 22, 32, 43, 55, 69, 84, 100, 117, 135, 154, 173, 193, 213, 233, 253, 272, 292, 310, 329, 346, 362, 377, 391, 403, 414, 424, 432, 438, 442, 445, 446};
    private static final int[] height36 = {1, 3, 8, 14, 21, 31, 41, 54, 67, 82, 98, 115, 132, 151, 170, 190, 209, 229, 249, 269, 289, 308, 326, 344, 361, 377, 392, 405, 417, 428, 437, 445, 451, 455, 458, 459};
    private static final int[] height37 = {1, 3, 8, 13, 21, 30, 40, 52, 66, 80, 96, 112, 130, 148, 167, 186, 206, 226, 246, 266, 285, 305, 323, 342, 359, 376, 391, 406, 419, 431, 441, 450, 458, 464, 468, 470, 471};
    private static final int[] height38 = {1, 3, 7, 13, 20, 29, 39, 51, 64, 78, 93, 110, 127, 145, 164, 183, 202, 222, 242, 262, 282, 302, 321, 339, 357, 375, 391, 406, 420, 433, 445, 455, 464, 471, 477, 481, 484, 484};
    private static final int[] height39 = {1, 3, 7, 13, 20, 28, 38, 50, 62, 76, 91, 107, 124, 142, 160, 179, 199, 218, 238, 258, 278, 298, 317, 336, 355, 373, 389, 405, 420, 434, 447, 458, 468, 477, 484, 489, 493, 496, 497};
    private static final int[] height40 = {1, 3, 7, 12, 19, 28, 38, 49, 61, 75, 89, 105, 122, 139, 157, 176, 195, 215, 235, 255, 275, 295, 314, 334, 352, 371, 388, 405, 420, 435, 449, 461, 472, 482, 490, 497, 503, 507, 509, 510};
    private static final int[] height41 = {1, 3, 7, 12, 19, 27, 37, 48, 60, 73, 87, 103, 119, 136, 154, 173, 192, 211, 231, 251, 271, 291, 311, 330, 349, 368, 386, 403, 419, 435, 449, 462, 475, 485, 495, 503, 510, 515, 519, 521, 522};
    private static final int[] height42 = {1, 3, 7, 12, 19, 27, 36, 47, 58, 71, 86, 101, 117, 134, 152, 170, 189, 208, 228, 248, 268, 288, 308, 327, 347, 365, 384, 401, 418, 434, 450, 464, 477, 489, 499, 509, 517, 523, 529, 532, 535, 535};
    private static final int[] height43 = {1, 3, 7, 12, 18, 26, 35, 45, 57, 70, 84, 99, 114, 131, 149, 167, 185, 205, 224, 244, 264, 284, 304, 324, 343, 362, 381, 399, 416, 433, 449, 464, 478, 491, 502, 513, 522, 530, 536, 541, 545, 547, 548};
    private static final int[] height44 = {1, 3, 6, 11, 18, 25, 34, 45, 56, 68, 82, 97, 112, 129, 146, 164, 182, 201, 221, 240, 260, 280, 300, 320, 340, 359, 378, 397, 415, 432, 448, 464, 479, 492, 505, 516, 526, 535, 543, 549, 554, 558, 560, 561};
    private static final int[] height45 = {1, 3, 6, 11, 17, 25, 34, 44, 55, 67, 80, 95, 110, 126, 143, 161, 179, 198, 217, 237, 257, 277, 297, 316, 336, 356, 375, 394, 412, 430, 447, 463, 478, 493, 506, 518, 530, 540, 548, 556, 562, 567, 570, 572, 573};
    private static final int[] height46 = {1, 3, 6, 11, 17, 24, 33, 43, 54, 66, 79, 93, 108, 124, 141, 158, 176, 195, 214, 233, 253, 273, 293, 313, 333, 353, 372, 391, 410, 428, 445, 462, 478, 493, 507, 520, 533, 543, 553, 562, 569, 575, 580, 583, 585, 586};
    private static final int[] height47 = {1, 3, 6, 11, 17, 24, 32, 42, 53, 64, 77, 91, 106, 122, 138, 155, 173, 192, 211, 230, 249, 269, 289, 309, 329, 349, 369, 388, 407, 425, 443, 460, 477, 492, 507, 521, 534, 546, 557, 566, 575, 582, 588, 593, 596, 598, 599};
    private static final int[] height48 = {1, 3, 6, 10, 16, 23, 32, 41, 52, 63, 76, 90, 104, 120, 136, 153, 171, 189, 208, 227, 246, 266, 286, 306, 326, 346, 365, 385, 404, 423, 441, 459, 476, 492, 507, 522, 536, 548, 560, 571, 580, 588, 595, 601, 606, 609, 611, 612};
    private static final int[] height49 = {1, 3, 6, 10, 16, 23, 31, 40, 51, 62, 74, 88, 102, 117, 133, 150, 168, 186, 204, 223, 243, 262, 282, 302, 322, 342, 362, 381, 401, 420, 438, 456, 474, 490, 507, 522, 536, 550, 562, 573, 584, 593, 601, 608, 614, 618, 621, 623, 624};
    private static final int[] height50 = {1, 3, 6, 10, 16, 22, 30, 39, 50, 61, 73, 86, 100, 115, 131, 148, 165, 183, 201, 220, 239, 259, 279, 299, 319, 339, 358, 378, 398, 417, 436, 454, 472, 489, 506, 522, 537, 551, 564, 576, 587, 598, 607, 615, 621, 627, 631, 635, 636, 637};
    /** arched heights of tiles by length to midpoint. */
    private static final int[][] heights = {height0, // 0-30 + 10 dummy
        height1, height2, height3, height4, height5,
        height6, height7, height8, height9, height10,
        height11, height12, height13, height14, height15,
        height16, height17, height18, height19, height20,
        height21, height22, height23, height24, height25,
        height26, height27, height28, height29, height30,
        height31, height32, height33, height34, height35,
        height36, height37, height38, height39, height40,
        height41, height42, height43, height44, height45,
        height46, height47, height48, height49, height50};
    
    private static final HashMap<String, BridgeData> bridgeTypes = new HashMap<>();
    
    static {
        addBridgeData(new RopeBridgeData());
        addBridgeData(new WoodenBridgeData());
        addBridgeData(new MarbleBridgeData());
        addBridgeData(new StoneBridgeData());
    }
    
    private static void addBridgeData(BridgeData data) {
        bridgeTypes.put(data.getName(), data);
    }
    
    public static BridgeData getData(String str) {
        return bridgeTypes.get(str);
    }
    
    public static BridgeType[] getAllBridgesData() {
        return bridgeTypes.values().stream().toArray(BridgeType[]::new);
    }
    
    private final HashMap<BridgePartType, Materials> materials;
    protected final int maxWidth;
    
    public BridgeData(int maxWidth) {
        this.maxWidth = maxWidth;
        
        this.materials = new HashMap<>();
        prepareMaterialsMap(materials);
    }
    
    public final void constructBridge(Map map, Bridge bridge, int startX, int startY, int endX, int endY, int firstFloor, int secondFloor, BridgeType type, BridgePartType[] segments, int additionalData) {
        if (type == BridgeType.FLAT) {
            constructFlatBridge(map, bridge, startX, startY, endX, endY, firstFloor, secondFloor, segments);
        }
        else if (type == BridgeType.ARCHED) {
            constructArchedBridge(map, bridge, startX, startY, endX, endY, firstFloor, secondFloor, segments, additionalData);
        }
        else if (type == BridgeType.ROPE) {
            constructRopeBridge(map, bridge, startX, startY, endX, endY, firstFloor, secondFloor, segments, additionalData);
        }
    }
    
    private void constructFlatBridge(Map map, Bridge bridge, int startX, int startY, int endX, int endY, int firstFloor, int secondFloor, BridgePartType[] segments) {
        int bridgeWidth = Math.max(endX - startX, endY - startY) + 1;
        float startHeight = getAbsoluteHeight(map.getTile(startX, startY), firstFloor);
        float endHeight = getAbsoluteHeight(map.getTile(endX + 1, endY + 1), secondFloor);
        float heightStep = (endHeight - startHeight) / bridgeWidth;
        boolean verticalOrientation = (endY - startY) > (endX - startX);
        
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                int currentSegment = verticalOrientation ? y - startY : x - startX;
                float currentHeight = startHeight + heightStep * currentSegment;
                BridgePartType segment = segments[currentSegment];
                BridgePartSide side = getPartSide(startX, startY, endX, endY, map.getTile(x, y), verticalOrientation);
                EntityOrientation orientation = getPartOrientation(segments, verticalOrientation, currentSegment);
                
                map.getTile(x, y).setBridgePart(new BridgePart(bridge, map.getTile(x, y), side, segment, orientation, currentHeight, heightStep));
            }
        }
    }
    
    private void constructArchedBridge(Map map, Bridge bridge, int startX, int startY, int endX, int endY, int firstFloor, int secondFloor, BridgePartType[] segments, int additionalData) {
        int bridgeWidth = Math.max(endX - startX, endY - startY) + 1;
        float startHeight = getAbsoluteHeight(map.getTile(startX, startY), firstFloor);
        float endHeight = getAbsoluteHeight(map.getTile(endX + 1, endY + 1), secondFloor);
        float heightStep = (endHeight - startHeight) / bridgeWidth;
        boolean verticalOrientation = (endY - startY) > (endX - startX);
        
        int[] archHeights = heights[bridgeWidth / 2];
        
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                int currentSegment = verticalOrientation ? y - startY : x - startX;
                
                int currentArch = currentSegment - (bridgeWidth / 2);
                int index = Math.abs(currentArch) - (bridgeWidth / 2);
                int previousIndex = Math.abs(index + 1);
                index = currentArch == 0 ? Math.abs(index + 1) : Math.abs(index);
                
                float previousArch;
                float archHeight;
                if (currentArch < 0) {
                    previousArch = index == 0 ? 0 : archHeights[previousIndex];
                    archHeight = archHeights[index];
                }
                else {
                    previousArch = archHeights[index];
                    archHeight = index == 0 ? 0 : archHeights[previousIndex];
                }
                
                previousArch *= (additionalData / 20);
                archHeight *= (additionalData / 20);
                
                float currentHeight = startHeight + heightStep * currentSegment;
                BridgePartType segment = segments[currentSegment];
                BridgePartSide side = getPartSide(startX, startY, endX, endY, map.getTile(x, y), verticalOrientation);
                EntityOrientation orientation = getPartOrientation(segments, verticalOrientation, currentSegment);
                
                map.getTile(x, y).setBridgePart(new BridgePart(bridge, map.getTile(x, y), side, segment, orientation, currentHeight + previousArch, heightStep + (archHeight - previousArch)));
            }
        }
    }
    
    private void constructRopeBridge(Map map, Bridge bridge, int startX, int startY, int endX, int endY, int firstFloor, int secondFloor, BridgePartType[] segments, int additionalData) {
        int bridgeWidth = Math.max(endX - startX, endY - startY) + 1;
        float startHeight = getAbsoluteHeight(map.getTile(startX, startY), firstFloor);
        float endHeight = getAbsoluteHeight(map.getTile(endX + 1, endY + 1), secondFloor);
        float heightStep = (endHeight - startHeight) / bridgeWidth;
        boolean verticalOrientation = (endY - startY) > (endX - startX);
        
        int[] sags = getSags(bridgeWidth, additionalData, (int) startHeight, (int) endHeight);
        
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                int currentSegment = verticalOrientation ? y - startY : x - startX;
                
                float currentSag = (sags[currentSegment + 1] - sags[currentSegment]);
                
                BridgePartType segment = segments[currentSegment];
                BridgePartSide side = getPartSide(startX, startY, endX, endY, map.getTile(x, y), verticalOrientation);
                EntityOrientation orientation = getPartOrientation(segments, verticalOrientation, currentSegment);
                
                map.getTile(x, y).setBridgePart(new BridgePart(bridge, map.getTile(x, y), side, segment, orientation, sags[currentSegment], heightStep + currentSag));
            }
        }
    }
    
    private int getAbsoluteHeight(Tile tile, int floor) {
        if (floor == 0) {
            return tile.getHeight();
        }
        else {
            return tile.getHeight();
            //return tile.getHeight() + floor * 35 + 3;
        }
    }
    
    private int[] getSags(int bridgeLength, int steepness, int startHeight, int endHeight) {
        final int lenp1 = bridgeLength + 1;
        final int lowBorder = lenp1 >>> 1;
        final int hiBorder = bridgeLength - lowBorder;
        final float totalSag = steepness / 100f;
        final float sagDistance =  lenp1 * 4f * totalSag;
        final double scaleCosh = 5E-05 ; //Math.cosh(1 / 100) - 1;
        final double scaleFactor = ((lenp1 * 4f) * totalSag) / scaleCosh;
        final int htDiff = endHeight - startHeight;
        final float htd = htDiff / 10f;
        //
        final int[] borders = new int[lenp1];
        final float[] scale = new float[lenp1];
        final double[] floatSag = new double[lenp1];
        final float[] adjust = new float[lenp1];
        final float[] slopeSag = new float[lenp1];
        final int[] dirtSag = new int[lenp1];
        final int[] hts = new int[lenp1];
        //
        // create borders array, start at lowBorder and end up at -hiBorder
        for (int x = 0; x < lenp1; x++)
            borders[x] = lowBorder - x;
        // Now the scale for each border...
        for (int x = 0; x < lenp1; x++)
        {
            if (borders[x] >= 0)
                scale[x] = borders[x] / (float) lowBorder;
            else
                scale[x] = -borders[x] / (float) hiBorder;
        }
        // Now the sag
        for (int x = 0; x < lenp1; x++)
            floatSag[x] = (scaleFactor * Math.cosh(scale[x] / 100) - scaleFactor - sagDistance);
        // Now the adjust
        for (int x = 0; x < lenp1; x++)
            adjust[x] = Math.abs((Math.signum(borders[x]) * scale[x] * htd) - htd) / 2f;
        // Now the slopeSag
        for (int x = 0; x < lenp1; x++)
            slopeSag[x] = (float) (floatSag[x] + adjust[x]);
        // Now the dirtSag
        for (int x = 0; x < lenp1; x++)
            dirtSag[x] = (int) (slopeSag[x] * 10f);
        // Now raise to start level...
        for (int x = 0; x < lenp1; x++)
        {
            if (htDiff >= 0)
                hts[x] = startHeight + dirtSag[x];
            else
                hts[x] = endHeight + dirtSag[lenp1 - x - 1];
        }
        // force the last tiles to be at correct height
        hts[0] = startHeight;
        hts[hts.length - 1] = endHeight;
        
        return hts;
    }
    
    public final int getMaxWidth() {
        return maxWidth;
    }
    
    public final Materials getMaterialsForPartType(BridgePartType type) {
        return materials.get(type);
    }
    
    public abstract boolean isCompatibleType(BridgeType type);
    public abstract Renderable getRenderableForPart(BridgePartSide orientation, BridgePartType type);
    public abstract int getSupportHeight();
    public abstract String getName();
    
    protected abstract void prepareMaterialsMap(HashMap<BridgePartType, Materials> materials);
    
    protected BridgePartSide getPartSide(int startX, int startY, int endX, int endY, Tile checkedTile, boolean isVertical) {
        if (startX == endX || startY == endY) {
            return BridgePartSide.NARROW;
        }
        
        int x = checkedTile.getX();
        int y = checkedTile.getY();
        
        if ((startX == x && isVertical) || (startY == y && !isVertical)) {
            return BridgePartSide.RIGHT;
        }
        else if ((endX == x && isVertical) || (endY == y && !isVertical)) {
            return BridgePartSide.LEFT;
        }
        else {
            return BridgePartSide.CENTER;
        }
    }
    
    protected EntityOrientation getPartOrientation(BridgePartType[] segments, boolean isVertical, int segment) {
        int dist = 1;
        while (true) {
            BridgePartType previousSegment = segment - dist < 0 ? null : segments[segment - dist];
            BridgePartType nextSegment = segment + dist >= segments.length ? null : segments[segment + dist];

            if (isVertical) {
                if (isSupport(nextSegment)) {
                    return EntityOrientation.UP;
                }
                else if (isSupport(previousSegment)) {
                    return EntityOrientation.DOWN;
                }
            }
            else {
                if (isSupport(nextSegment)) {
                    return EntityOrientation.RIGHT;
                }
                else if (isSupport(previousSegment)) {
                    return EntityOrientation.LEFT;
                }
            }
            
            dist++;
        }
    }
    
    private boolean isSupport(BridgePartType type) {
        return type == BridgePartType.SUPPORT || type == null;
    }
    
}
