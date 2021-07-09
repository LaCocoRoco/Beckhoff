package twincat.app.container;

import twincat.ads.container.Symbol;
import twincat.ads.worker.SymbolLoader;

public class SymbolNode {
    /*************************/
    /*** global attributes ***/
    /*************************/

    private boolean isVisible = true;

    private final boolean fullSymbolName;

    private final Symbol symbol;

    private final SymbolLoader symbolLoader;

    /*************************/
    /****** constructor ******/
    /*************************/

    public SymbolNode(Symbol symbol, SymbolLoader symbolLoader, boolean fullSymbolName) {
        this.symbol = symbol;
        this.symbolLoader = symbolLoader;
        this.fullSymbolName = fullSymbolName;
    }

    /*************************/
    /**** setter & getter ****/
    /*************************/

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public boolean isUsingFullSymbolName() {
        return fullSymbolName;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public SymbolLoader getSymbolLoader() {
        return symbolLoader;
    }

    /*************************/
    /******** override *******/
    /*************************/

    @Override
    public String toString() {
        if (!fullSymbolName) {
            int indexBeg = symbol.getSymbolName().lastIndexOf(".") + 1;
            int indexEnd = symbol.getSymbolName().length();
            return symbol.getSymbolName().substring(indexBeg, indexEnd);
        } else {
            return symbol.getSymbolName();
        }
    }
}
