package twincat.ads;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

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
    
    private boolean pointer;

    private String type;

    /*************************/
    /*** local attributes ****/
    /*************************/
   
    // TODO : point array to string array
    // TODO : get type array ?
    
    private final List<Point> typeArray = new ArrayList<Point>();

    /*************************/
    /****** constructor ******/
    /*************************/

    public AdsTypeInfo() {
        /* empty */
    }
    
    public AdsTypeInfo(String type) {
        parseType(type);  
    }
    
    /*************************/
    /**** setter & getter ****/
    /*************************/

    public boolean isPointer() {
        return pointer;
    }

    public void setPointer(boolean pointer) {
        this.pointer = pointer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    
    public void parseType(String type) {
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
                    // index is variable
                    typeArray.clear();
                    break;
                }
            }
            
            int begIndex = type.indexOf(ARRAY_PATTERN_END) + ARRAY_PATTERN_END.length();
            type = type.substring(begIndex, type.length());
        }
        
        this.type = type;   
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
