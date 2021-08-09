package twincat.constant;

import java.awt.Color;

public enum DefaultColorTable {
    /*********************************/
    /*** global constant variable ****/
    /*********************************/
    
    SCOPEBLUE      (new Color(0x77, 0x88, 0x99)),
    SCOPEGREEN     (new Color(0x29, 0x3D, 0x4A)),
    SCOPEGRAY      (new Color(0xC0, 0xC0, 0xC0)),
    
    JAVAGRAY       (new Color(0xEE, 0xEE, 0xEE)),
    
    GRAY           (new Color(0x96, 0x96, 0x96)),   
    WHITE          (new Color(0xFF, 0xFF, 0xFF)),
    BLACK          (new Color(0x00, 0x00, 0x00)),
    RED            (new Color(0xFF, 0x00, 0x00)),
    ORANGE         (new Color(0xFF, 0xA5, 0x00)),
    VIOLET         (new Color(0xEE, 0x82, 0xEE)),
    PURPLE         (new Color(0x80, 0x00, 0x80)),
    BLUE           (new Color(0x00, 0x00, 0xFF)),
    PINK           (new Color(0xFF, 0x14, 0x93)),
    GREEN          (new Color(0x00, 0x64, 0x00)),
    
    OLIVE          (new Color(0x80, 0x80, 0x00)),
    DARKRED        (new Color(0x8B, 0x00, 0x00)),
    GOLDENROD      (new Color(0xDA, 0xA5, 0x20)),
    DARKSLATEGRAY  (new Color(0x2F, 0x4F, 0x4F)),
    ORANGERED      (new Color(0xFF, 0x45, 0x00));
 
    /*********************************/
    /******** global variable ********/
    /*********************************/

    public final Color color;

    /*********************************/
    /********** constructor **********/
    /*********************************/

    private DefaultColorTable(Color color) {
        this.color = color;
    }

    /*********************************/
    /** public static final method ***/
    /*********************************/

    public static final DefaultColorTable getByValue(Color color) {
        for (DefaultColorTable colorTable : DefaultColorTable.values()) {
            if (colorTable.color == color) {
                return colorTable;
            }
        }
        
        return DefaultColorTable.RED;
    }
    
    public static final DefaultColorTable getByString(String value) {
        for (DefaultColorTable colorTable : DefaultColorTable.values()) {
            if (colorTable.name().equalsIgnoreCase(value)) {
                return colorTable;
            }
        }
        
        return DefaultColorTable.RED;
    } 
}
