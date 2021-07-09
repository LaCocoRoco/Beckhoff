package twincat.app.container;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class SymbolTreeModel extends DefaultTreeModel {
    private static final long serialVersionUID = 1L;

    /*************************/
    /*** local attributes ****/
    /*************************/

    protected boolean symbolFilter = false;

    /*************************/
    /****** constructor ******/
    /*************************/
    
    public SymbolTreeModel() {
        super(new SymbolTreeNode());
    }

    public SymbolTreeModel(TreeNode root) {
        super(root);
    }

    public SymbolTreeModel(TreeNode root, boolean asksAllowsChildren) {
        super(root, asksAllowsChildren);
    }

    /*************************/
    /********* public ********/
    /*************************/

    public void setSymbolFilter(boolean symbolFilter) {
        this.symbolFilter = symbolFilter;
    }

    /*************************/
    /******** override *******/
    /*************************/

    @Override
    public Object getChild(Object parent, int index) {
        if (symbolFilter) {
            if (parent instanceof SymbolTreeNode) {
                return ((SymbolTreeNode) parent).getChildAt(index, symbolFilter);
            }
        }

        return super.getChild(parent, index);
    }

    @Override
    public int getChildCount(Object parent) {
        if (symbolFilter) {
            if (parent instanceof SymbolTreeNode) {
                return ((SymbolTreeNode) parent).getChildCount(symbolFilter);
            }
        }

        return super.getChildCount(parent);
    }
}
