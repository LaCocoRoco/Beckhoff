package twincat.ads;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

//TODO : add constructor to parse

public class AdsTypeInfo {
    /*************************/
    /** constant attributes **/
    /*************************/
   
    private static final String POINTER_PATTERN   = "POINTER TO";
     
    private static final String ARRAY_PATTERN_BEG = "ARRAY ";
    
    private static final String ARRAY_PATTERN_END = "OF ";
    
    /*************************/
    /*** global attributes ***/
    /*************************/
    
    private final boolean pointer;

    private final String typeName;
    
    private final List<Point> typeArray = new ArrayList<Point>();

    /*************************/
    /****** constructor ******/
    /*************************/

    public AdsTypeInfo(String type) {
        pointer = type.contains(POINTER_PATTERN) ? true : false;

        if (type.contains(ARRAY_PATTERN_BEG)) {
            int begPattern = type.indexOf("[");
            int endPattern = type.indexOf("]");

            String[] data = type.substring(begPattern + 1, endPattern).split(",");
            for (String position : data) {
                String[] size = position.replace(" ", "").split("\\..");
                
                try {
                    int beg = Integer.valueOf(size[0]);
                    int end  = Integer.valueOf(size[1]);
                    typeArray.add(new Point(beg, end));
                } catch (NumberFormatException e) {
                    typeArray.clear();
                    break;
                }
            }
            
            int begIndex = type.indexOf(ARRAY_PATTERN_END) + ARRAY_PATTERN_END.length();
            type = type.substring(begIndex, type.length());
        }
        
        this.typeName = type;   
    }
    
    /*************************/
    /**** setter & getter ****/
    /*************************/
    
    public boolean isPointer() {
        return pointer;
    }

    public List<Point> getTypeArray() {
        return typeArray;
    }

    public String getTypeName() {
        return typeName;
    }

    /*************************/
    /********* public ********/
    /*************************/

    public boolean isArray() {
        return typeArray.isEmpty() ? false : true;
    }
    
    public List<String> getNamedTypeArray() {
        return typeArrayToNamedList(typeArray, 0);
    }
    
    /*************************/
    /******** private ********/
    /*************************/

    private List<String> typeArrayToNamedList(List<Point> typeArrayList, int index) {
        List<String> namedList = new ArrayList<String>();
        Point typeArray = typeArrayList.get(index);
        String end = index == 0 ? "[" : "";
        for (int i = typeArray.x; i <= typeArray.y; i++) {
            if (typeArrayList.size() > index + 1) {
                for (String r : typeArrayToNamedList(typeArrayList, index + 1))
                    namedList.add(end + Integer.toString(i) + "," + r);
            } else {
                namedList.add(end + Integer.toString(i) + "]");
            }
        }

        return namedList;
    } 
}
