package twincat.ads.constant;

import java.awt.Color;

public enum ColorTable {
    /*********************************/
    /*** global constant variable ****/
    /*********************************/

    RED     (Color.RED);

    /*********************************/
    /******** global variable ********/
    /*********************************/

    public final Color color;

    /*********************************/
    /********** constructor **********/
    /*********************************/

    private ColorTable(Color color) {
        this.color = color;
    }

    /*********************************/
    /** public static final method ***/
    /*********************************/

    public static final ColorTable getByValue(Color color) {
        for (ColorTable primitiveColor : ColorTable.values()) {
            if (primitiveColor.color == color) {
                return primitiveColor;
            }
        }
        
        return ColorTable.RED;
    }
    
    public static final ColorTable getByString(String value) {
        for (ColorTable primitiveColor : ColorTable.values()) {
            if (primitiveColor.name().equalsIgnoreCase(value)) {
                return primitiveColor;
            }
        }
        return ColorTable.RED;
    } 
}
