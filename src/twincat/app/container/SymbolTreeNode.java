package twincat.app.container;

import javax.swing.tree.DefaultMutableTreeNode;

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

    /*
    public static final boolean isAnySymbolChildVisible(SymbolTreeNode symbolTreeNode) {
        // wir rufen alle kinder auf, ist eines sichtbar wird der tree eingeblendet

        Object userObject = symbolTreeNode.getUserObject();

        // ist das hier ein node mit symbol ?
        if (userObject instanceof SymbolNode) {
            SymbolNode symbolNode = (SymbolNode) userObject;

            // ist dieses symbol sichtbar ?
            if (symbolNode.isVisible()) {
                return true;
            }
        }

        // durchsuche das naechste kind
        for (int i = 0; i < symbolTreeNode.getChildCount(); i++) {
            SymbolTreeNode symbolTreeNodeChild = (SymbolTreeNode) symbolTreeNode.getChildAt(i);

            // das kind nochmals aufrufen und kontrollieren
            if (SymbolTreeNode.isAnySymbolChildVisible(symbolTreeNodeChild)) {
                return true;
            }
        }

        // keines sichtbar. kann ausgeblendet werden
        return false;
    }

    public boolean isVisible() {
        if (userObject instanceof SymbolNode) {
            SymbolNode symbolNode = (SymbolNode) userObject;
            return symbolNode.isVisible();
        } else {
            return SymbolTreeNode.isAnySymbolChildVisible(this);
        }
    }
    */

    /*************************/
    /********* public ********/
    /*************************/

    public SymbolTreeNode getTreeNode(String name) {
        for (int i = 0; i < this.getChildCount(); i++) {
            if (this.getChildAt(i).toString().equals(name)) {
                // return node by node name
                return (SymbolTreeNode) this.getChildAt(i);
            }
        }

        // add new node by node name
        SymbolTreeNode node = new SymbolTreeNode(name);
        this.add(node);
        return node;
    }

    public SymbolTreeNode addSymbolNode(SymbolNode symbolNode) {
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
                SymbolTreeNode symbolTreeNode = new SymbolTreeNode(symbolNode);
                symbolTreeNodePointer.add(symbolTreeNode);
            } else {
                // add new folder node to tree
                SymbolTreeNode symbolTreeNode = new SymbolTreeNode(symbolNodeName);
                symbolTreeNodePointer.add(symbolTreeNode);
                symbolTreeNodePointer = symbolTreeNode;
            }
        }

        return symbolTreeNodePointer;
    }

    /*************************/
    /******** override *******/
    /*************************/

    /*
    @Override
    public TreeNode getChildAt(int index) {
        if (children == null) throw new ArrayIndexOutOfBoundsException();

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

    @Override
    public int getChildCount() {
        if (children == null) return 0;

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
    */
}
