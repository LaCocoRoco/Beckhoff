package twincat.app.container;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import twincat.app.constant.Filter;

public class SymbolTreeNode extends DefaultMutableTreeNode {
    private static final long serialVersionUID = 1L;

    /*************************/
    /** constant attributes **/
    /*************************/

    private static final String GLOBAL_SYMBOL_NAME = "Global";

    /*************************/
    /*** global attributes ***/
    /*************************/

    private boolean isVisible = true;
   
    /*************************/
    /****** constructor ******/
    /*************************/

    public SymbolTreeNode() {
        super();
    }

    public SymbolTreeNode(Object userObject) {
        super(userObject);
    }

    public SymbolTreeNode(Object userObject, boolean allowsChildren) {
        super(userObject, allowsChildren);
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
    
    /*************************/
    /********* public ********/
    /*************************/

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

    public void addSymbolNode(SymbolNode symbolNode) {
        SymbolTreeNode symbolTreeNode = new SymbolTreeNode(symbolNode);
        this.add(symbolTreeNode);
    }

    // substring
    // 0. .JUINT_STRUCT.ST_VALUE 
    // 1. .JUINT_STRUCT
    // 2. .ST_VALUE
    
    public void addSymbolNodeAndSplit(SymbolNode symbolNodeChild, SymbolNode symbolNodeParent) {
        
    }
    
    public void addSymbolNodeAndSplit(SymbolNode symbolNode) {
        String[] symbolNodeNameArray = symbolNode.getSymbol().getSymbolName().split("\\.");

        // set symbol name of dot to global
        if (symbolNodeNameArray[0].isEmpty()) {
            symbolNodeNameArray[0] = GLOBAL_SYMBOL_NAME;
        }

        SymbolTreeNode symbolTreeNodePointer = this;

        symbolNodeName: for (int i = 0; i < symbolNodeNameArray.length; i++) {
            String symbolNodeName = symbolNodeNameArray[i];

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

            if (i == symbolNodeNameArray.length - 1) {
                // add mew symbol node to tree
                symbolTreeNodePointer.addSymbolNode(symbolNode);
            } else {
                // add new folder node to tree
                SymbolTreeNode symbolTreeNode = new SymbolTreeNode(symbolNodeName);
                symbolTreeNodePointer.add(symbolTreeNode);
                symbolTreeNodePointer = symbolTreeNode;
            }
        }
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

    /*************************/
    /******** private ********/
    /*************************/

    private boolean isFilterNode() {
        return isVisible;
    }

    private boolean isFilterSymbols() {
        return SymbolTreeNode.isAnySymbolNodeVisible(this);
    }
    
    private boolean isFilterAll() {
        if (userObject instanceof SymbolNode) {
            return SymbolTreeNode.isAnySymbolNodeVisible(this);    
        } else  {
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

    /*************************/
    /** public static final **/
    /*************************/

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
