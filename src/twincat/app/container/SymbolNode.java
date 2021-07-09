package twincat.app.container;

import java.util.List;

import twincat.ads.constant.DataType;
import twincat.ads.container.Symbol;
import twincat.ads.worker.SymbolLoader;

public class SymbolNode {
    /*************************/
    /*** global attributes ***/
    /*************************/

    private boolean isVisible = true;

    /*************************/
    /*** local attributes ****/
    /*************************/

    private final Symbol symbol;

    @SuppressWarnings("unused")
    private final SymbolLoader symbolLoader;

    /*************************/
    /****** constructor ******/
    /*************************/

    public SymbolNode(Symbol symbol, SymbolLoader symbolLoader) {
        this.symbol = symbol;
        this.symbolLoader = symbolLoader;
    }

    /*************************/
    /**** setter & getter ****/
    /*************************/

    public Symbol getSymbol() {
        return symbol;
    }
    
    public boolean isVisible() {
        switch (symbol.getDataType()) {
            case BIT:
                // set bit invisible
                return false;

            default:
                // everything else visible
                return true;
        }
    }

    /*************************/
    /********* public ********/
    /*************************/

    @Override
    public String toString() {
        int indexBeg = symbol.getSymbolName().lastIndexOf(".") + 1;
        int indexEnd = symbol.getSymbolName().length();
        return symbol.getSymbolName().substring(indexBeg, indexEnd);
    }
}
