package twincat.ads.constant;

import java.awt.Color;

public enum ColorTable {
    /*********************************/
    /*** global constant variable ****/
    /*********************************/

    BLACK           (new Color(0x00, 0x00, 0x00)),
    RED             (new Color(0xFF, 0x00, 0x00)),
    DEEP_PINK       (new Color(0xFF, 0x14, 0x93)),
    VIOLET          (new Color(0xEE, 0x82, 0xEE)),
    PURPLE          (new Color(0x80, 0x00, 0x80)),
    BLUE            (new Color(0x00, 0x00, 0xFF)),
    DARK_GREEN      (new Color(0x00, 0x64, 0x00)),
    DARK_RED        (new Color(0x8B, 0x00, 0x00)),
    GOLDENROD       (new Color(0xDA, 0xA5, 0x20)),
    DARK_SLATE_GRAY (new Color(0x2F, 0x4F, 0x4F)),
    ORANGE          (new Color(0xFF, 0xA5, 0x00)),
    OLIVE           (new Color(0x80, 0x80, 0x00)),
    ORANGE_RED      (new Color(0xFF, 0x45, 0x00));
 
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
