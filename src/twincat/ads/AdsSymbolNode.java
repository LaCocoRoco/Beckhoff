package twincat.ads;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import twincat.ads.constants.AdsDataType;

public class AdsSymbolNode {
    /*************************/
    /*** global attributes ***/
    /*************************/

    private String name = new String();
    
    private AdsDataType dataType = AdsDataType.UNKNOWN;
    
    private List<Point> dimension = new ArrayList<Point>();

    /*************************/
    /****** constructor ******/
    /*************************/
    
    public AdsSymbolNode(AdsSymbolInfo symbolInfo) {

    }
    
    public AdsSymbolNode(AdsDataTypeInfo dataTypeInfo) {

    }
       
    /*************************/
    /**** setter & getter ****/
    /*************************/

    /*************************/
    /********* public ********/
    /*************************/
 
    /*************************/
    /******** private ********/
    /*************************/
    
    private void getDimension(AdsSymbolInfo symbolInfo) {
        String type = symbolInfo.getType();
        
        if (type.contains("ARRAY ")) {
            int begPattern = type.indexOf("[");
            int endPattern = type.indexOf("]");

            String[] data = type.substring(begPattern + 1, endPattern).split(",");
            List<AdsArray> dimension = new ArrayList<AdsArray>();
            for (String position : data) {
                String[] size = position.replace(" ", "").split("\\..");
                int beg = Integer.valueOf(size[0]);
                int end = Integer.valueOf(size[1]);
                dimension.add(new AdsArray(beg, end));
            } 
        } 
    }
}
