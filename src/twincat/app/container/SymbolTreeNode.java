package twincat.app.container;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import twincat.ads.constant.AmsPort;
import twincat.app.constant.Filter;

public class SymbolTreeNode extends DefaultMutableTreeNode {
    /***********************************/
    /***** local constant variable *****/
    /***********************************/

    private static final long serialVersionUID = 1L;

    private static final String GLOBAL_SYMBOL_NAME = "Global";

    /***********************************/
    /********* global variable *********/
    /***********************************/

    private boolean isVisible = true;

    /***********************************/
    /*********** constructor ***********/
    /***********************************/

    public SymbolTreeNode() {
        super();
    }

    public SymbolTreeNode(Object userObject) {
        super(userObject);
    }

    public SymbolTreeNode(Object userObject, boolean allowsChildren) {
        super(userObject, allowsChildren);
    }

    /***********************************/
    /********* setter & getter *********/
    /***********************************/

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    /***********************************/
    /********* public function *********/
    /***********************************/

    public SymbolTreeNode getNode(String name) {
        for (int i = 0; i < this.getChildCount(); i++) {
            if (this.getChildAt(i).toString().equals(name)) {
                // return child node
                return (SymbolTreeNode) this.getChildAt(i);
            }
        }

        // add new node
        SymbolTreeNode symbolTreeNode = new SymbolTreeNode(name);
        this.add(symbolTreeNode);
        return symbolTreeNode;
    }

    public void addSymbolNodeFullName(SymbolNode symbolNode) {
        SymbolTreeNode symbolTreeNode = new SymbolTreeNode(symbolNode);
        this.add(symbolTreeNode);
    }

    public void addSymbolNodeFullName(SymbolNode symbolNodeParent, SymbolNode symbolNodeChild) {
        SymbolTreeNode symbolTreeNode = new SymbolTreeNode(symbolNodeChild);
        SymbolTreeNode symbolNodeParentOfParent = (SymbolTreeNode) this.getParent();
        int symbolNodeParentIndex = symbolNodeParentOfParent.getIndex(this);
        symbolNodeParentOfParent.insert(symbolTreeNode, symbolNodeParentIndex);
    }

    public void addSymbolNodeSplitName(SymbolNode symbolNode) {
        AmsPort amsPort = symbolNode.getSymbolLoader().getAmsPort();
        String symbolName = symbolNode.getSymbol().getSymbolName();
        String[] symbolNameArray = symbolName.split("\\.");
       
        // NC structure
        switch (amsPort) {
            case NC:
            case NCSAF:
            case NCSVB:
                symbolNameArray = new String[3];

                int rangeBeg = symbolName.lastIndexOf(".");
                int rangeMid = symbolName.indexOf(".");
                int rangeEnd = symbolName.length();

                symbolNameArray[0] = symbolName.substring(0, rangeMid);
                symbolNameArray[1] = symbolName.substring(rangeMid + 1, rangeBeg);
                symbolNameArray[2] = symbolName.substring(rangeBeg + 1, rangeEnd);
                break;

            default:
                break;
        }

        // set empty symbol name to global
        if (symbolNameArray[0].isEmpty()) {
            symbolNameArray[0] = GLOBAL_SYMBOL_NAME;
        }

        addSymbolNodeSplitName(symbolNameArray, symbolNode);
    }

    public void addSymbolNodeSplitName(SymbolNode symbolNodeParent, SymbolNode symbolNodeChild) {
        String symbolNameParent = symbolNodeParent.getSymbol().getSymbolName();
        String symbolNameChild = symbolNodeChild.getSymbol().getSymbolName();
        String symbolName = symbolNameChild.replace(symbolNameParent, "");

        String[] symbolNameArray = symbolName.split("\\.");
        String[] symbolNameParentArray = symbolNameParent.split("\\.");

        // set array symbol name to parent
        if (symbolName.contains("[")) {
            String symbolNodeNameParent = symbolNameParentArray[symbolNameParentArray.length - 1];
            String symbolNodeName = symbolNodeNameParent + symbolNameArray[0];
            symbolNameArray[0] = symbolNodeName;
        }

        addSymbolNodeSplitName(symbolNameArray, symbolNodeChild);
    }

    public TreeNode getChildAt(int index, Filter filterLevel) {
        // filter is disabled
        if (filterLevel.equals(Filter.NONE)) {
            return super.getChildAt(index);
        }

        // no children
        if (children == null) {
            throw new ArrayIndexOutOfBoundsException();
        }

        // evaluate index
        int realIndex = -1;
        int visibleIndex = -1;
        Enumeration<?> enumerator = children.elements();
        while (enumerator.hasMoreElements()) {
            SymbolTreeNode symbolTreeNode = (SymbolTreeNode) enumerator.nextElement();

            // skip node if it is not visible
            if (symbolTreeNode.isVisible(filterLevel)) {
                visibleIndex++;
            }

            realIndex++;

            if (visibleIndex == index) {
                return (TreeNode) children.elementAt(realIndex);
            }
        }

        throw new ArrayIndexOutOfBoundsException();
    }

    public int getChildCount(Filter filterLevel) {
        // filter is disabled
        if (filterLevel.equals(Filter.NONE)) {
            return super.getChildCount();
        }

        // no children
        if (children == null) {
            return 0;
        }

        // evaluate child count
        int count = 0;
        Enumeration<?> enumerator = children.elements();
        while (enumerator.hasMoreElements()) {
            SymbolTreeNode symbolTreeNode = (SymbolTreeNode) enumerator.nextElement();

            // skip node if it is not visible
            if (symbolTreeNode.isVisible(filterLevel)) {
                count++;
            }
        }

        return count;
    }

    /***********************************/
    /******** private function *********/
    /***********************************/

    private boolean isFilterNode() {
        return isVisible;
    }

    private boolean isFilterSymbols() {
        return SymbolTreeNode.isAnySymbolNodeVisible(this);
    }

    private boolean isFilterAll() {
        if (userObject instanceof SymbolNode) {
            return SymbolTreeNode.isAnySymbolNodeVisible(this);
        } else {
            return isVisible;
        }
    }

    private boolean isVisible(Filter filterLevel) {
        switch (filterLevel) {
            case NODE:
                return isFilterNode();

            case SYMBOLS:
                return isFilterSymbols();

            case ALL:
                return isFilterAll();

            default:
                return true;
        }
    }

    private void addSymbolNodeSplitName(String[] symbolNameArray, SymbolNode symbolNode) {
        SymbolTreeNode symbolTreeNodePointer = this;

        symbolNodeName: for (int i = 0; i < symbolNameArray.length; i++) {
            String symbolNodeName = symbolNameArray[i];

            if (symbolNodeName.isEmpty()) continue;

            if (this.getChildCount() != 0) {
                for (int x = 0; x < symbolTreeNodePointer.getChildCount(); x++) {
                    SymbolTreeNode symbolTreeNodeChild = (SymbolTreeNode) symbolTreeNodePointer.getChildAt(x);

                    // pass reference and continue to next symbol name
                    if (symbolTreeNodeChild.toString().equals(symbolNodeName)) {
                        symbolTreeNodePointer = symbolTreeNodeChild;
                        continue symbolNodeName;
                    }
                }
            }

            if (i == symbolNameArray.length - 1) {
                // add mew symbol node to tree
                SymbolTreeNode symbolTreeNode = new SymbolTreeNode(symbolNode);
                symbolTreeNodePointer.add(symbolTreeNode);
            } else {
                // add new symbol node as folder to tree
                SymbolTreeNode symbolTreeNode = new SymbolTreeNode(symbolNodeName);
                symbolTreeNodePointer.add(symbolTreeNode);
                symbolTreeNodePointer = symbolTreeNode;
            }
        }
    }

    /***********************************/
    /** public static final function ***/
    /***********************************/

    public static final boolean isAnySymbolNodeVisible(SymbolTreeNode symbolTreeNode) {
        Object userObject = symbolTreeNode.getUserObject();

        if (userObject instanceof SymbolNode) {
            SymbolNode symbolNode = (SymbolNode) userObject;

            if (symbolNode.isVisible()) {
                return true;
            }
        }

        for (int i = 0; i < symbolTreeNode.getChildCount(); i++) {
            SymbolTreeNode symbolTreeNodeChild = (SymbolTreeNode) symbolTreeNode.getChildAt(i);

            if (SymbolTreeNode.isAnySymbolNodeVisible(symbolTreeNodeChild)) {
                return true;
            }
        }

        return false;
    }
}
