package twincat.app.container;

import twincat.ads.constant.DataType;
import twincat.ads.container.Symbol;
import twincat.ads.worker.SymbolLoader;

public class SymbolNode {
    /*************************/
    /*** local attributes ****/
    /*************************/

    private final Symbol symbol;
    
    private final SymbolLoader symbolLoader;

    /*************************/
    /****** constructor ******/
    /*************************/

    public SymbolNode(Symbol symbol, SymbolLoader symbolLoader) {
        this.symbol = symbol;
        this.symbolLoader = symbolLoader;
    }

    /*************************/
    /********* public ********/
    /*************************/
 
    @Override
    public String toString() {
        int indexBeg = symbol.getSymbolName().lastIndexOf(".");
        int indexEnd = symbol.getSymbolName().length();
        return symbol.getSymbolName().substring(indexBeg, indexEnd);
    }
    
    public boolean isBigType() {
        return symbol.getDataType().equals(DataType.BIGTYPE) ? true : false;
    }
}
