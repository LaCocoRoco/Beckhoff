package twincat.app.container;

import javax.swing.tree.DefaultMutableTreeNode;

public class SymbolTreeNode extends DefaultMutableTreeNode {
    private static final long serialVersionUID = 1L;

    public SymbolTreeNode() {
        super();
    }

    public SymbolTreeNode(Object userObject, boolean allowsChildren) {
        super(userObject, allowsChildren);
    }

    public SymbolTreeNode(Object userObject) {
        super(userObject);
    }

    public SymbolTreeNode getTreeNode(String name) {
        for (int i = 0; i < this.getChildCount(); i++) {
            if (this.getChildAt(i).toString().equals(name)) {
                return (SymbolTreeNode) this.getChildAt(i);
            }
        }
        
        SymbolTreeNode node = new SymbolTreeNode(name);
        this.add(node);
        return node;
    } 
    
    private static final String GLOBAL_SYMBOL_NAME = "Global";
    
    public SymbolTreeNode addSymbolNode(SymbolNode symbolNode) {
        String[] symbolNodeNameArray = symbolNode.getSymbol().getSymbolName().split("\\.");

        if (symbolNodeNameArray[0].isEmpty()) {
            symbolNodeNameArray[0] = GLOBAL_SYMBOL_NAME;   
        }

        SymbolTreeNode symbolTreeNodePointer = this;

        nextSymbolTreeNode:
        for (int i = 0; i < symbolNodeNameArray.length; i++) {
            String symbolNodeName = symbolNodeNameArray[i];

            if (this.getChildCount() != 0) {
                for (int x = 0; x < symbolTreeNodePointer.getChildCount(); x++) {  
                    SymbolTreeNode symbolTreeNodeChild = (SymbolTreeNode) symbolTreeNodePointer.getChildAt(x);

                    if (symbolTreeNodeChild.toString().equals(symbolNodeName)) {
                        symbolTreeNodePointer = symbolTreeNodeChild;
                        continue nextSymbolTreeNode;
                    }
                }
            }

            if (i == symbolNodeNameArray.length - 1) {
                SymbolTreeNode symbolTreeNode = new SymbolTreeNode(symbolNode);
                symbolTreeNodePointer.add(symbolTreeNode);
            } else {
                SymbolTreeNode symbolTreeNode = new SymbolTreeNode(symbolNodeName);
                symbolTreeNodePointer.add(symbolTreeNode);
                symbolTreeNodePointer = symbolTreeNode;
            }
        }
        
        return symbolTreeNodePointer;
    } 
    
    
    
    
    
    
}
