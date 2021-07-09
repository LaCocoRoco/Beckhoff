package twincat.app.container;

import java.util.Enumeration;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

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
        /*
        if (isVisible) {
            // is global attribute set
            return true; 
        } else if (userObject instanceof SymbolNode) {
            // is symbol node visible
            SymbolNode symbolNode = (SymbolNode) userObject;
            return symbolNode.isVisible();
        } else {
            // is any child visible
            return SymbolTreeNode.isAnySymbolChildVisible(this);
        }
        */
        
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

    public TreeNode getChildAt(int index, boolean symbolFilter) {
        if (!symbolFilter) {
            return super.getChildAt(index);
        }

        if (children == null) {
            throw new ArrayIndexOutOfBoundsException();
        }

        int realIndex = -1;
        int visibleIndex = -1;

        Enumeration<?> enumerator = children.elements();
        while (enumerator.hasMoreElements()) {
            SymbolTreeNode symbolTreeNode = (SymbolTreeNode) enumerator.nextElement();

            // skip node if it is not visible
            if (symbolTreeNode.isVisible()) {
                visibleIndex++;
            }

            realIndex++;

            if (visibleIndex == index) {
                return (TreeNode) children.elementAt(realIndex);
            }
        }

        throw new ArrayIndexOutOfBoundsException();
    }

    public int getChildCount(boolean symbolFilter) {
        if (!symbolFilter) {
            return super.getChildCount();
        }

        if (children == null) { 
            return 0;
        }

        int count = 0;
        
        Enumeration<?> enumerator = children.elements();
        while (enumerator.hasMoreElements()) {
            SymbolTreeNode symbolTreeNode = (SymbolTreeNode) enumerator.nextElement();

            // skip node if it is not visible
            if (symbolTreeNode.isVisible()) {
                count++;
            }
        }

        return count;
    }

    /*************************/
    /** public static final **/
    /*************************/

    public static final boolean isAnySymbolChildVisible(SymbolTreeNode symbolTreeNode) {
        Object userObject = symbolTreeNode.getUserObject();

        if (userObject instanceof SymbolNode) {
            SymbolNode symbolNode = (SymbolNode) userObject;

            if (symbolNode.isVisible()) {
                return true;
            }
        }

        for (int i = 0; i < symbolTreeNode.getChildCount(); i++) {
            SymbolTreeNode symbolTreeNodeChild = (SymbolTreeNode) symbolTreeNode.getChildAt(i);

            if (SymbolTreeNode.isAnySymbolChildVisible(symbolTreeNodeChild)) {
                return true;
            }
        }

        return false;
    }
}
