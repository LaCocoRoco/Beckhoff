package twincat.test;

import java.util.ArrayList;
import java.util.List;

import twincat.ads.AdsDataTypeInfo;
import twincat.ads.AdsSymbolInfo;

public class AdsSymbolTable {
    /*************************/
    /** constant attributes **/
    /*************************/

    /*************************/
    /*** local attributes ***/
    /*************************/

    private final List<AdsDataTypeInfo> dataTypeInfoList;
    
    /*************************/
    /*** global attributes ***/
    /*************************/

    /*************************/
    /****** constructor ******/
    /*************************/

    public AdsSymbolTable(List<AdsDataTypeInfo> dataTypeInfoList) {
        this.dataTypeInfoList = dataTypeInfoList;     
    }
    
    /*************************/
    /**** setter & getter ****/
    /*************************/

    /*************************/
    /********* public ********/
    /*************************/

    public List<AdsSymbolInfo> getSymbolTable(List<AdsSymbolInfo> symbolInfoList) {
        List<AdsSymbolInfo> symbolTable = new ArrayList<AdsSymbolInfo>();
        
        for (AdsSymbolInfo symbolInfo : symbolInfoList) {
            for (AdsDataTypeInfo dataTypeInfo : dataTypeInfoList) {
                


                
                
                if (symbolInfo.getType().equals(dataTypeInfo.getDataTypeName())) {
                    // data type complex
                    System.out.println(symbolInfo.getSymbolName());
                    
                    

                    
                } else {
                    // data type primitive
                    symbolTable.add(symbolInfo); 
                }
            }    
        }    
        
        return null;
    }
    
    /*************************/
    /******** private ********/
    /*************************/
}
