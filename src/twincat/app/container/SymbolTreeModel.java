package twincat.app.container;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import twincat.app.constant.Filter;

public class SymbolTreeModel extends DefaultTreeModel {
    /***********************************/
    /***** local constant variable *****/
    /***********************************/

    private static final long serialVersionUID = 1L;

    /***********************************/
    /********* global variable *********/
    /***********************************/

    private Filter filterLevel = Filter.NONE;

    /***********************************/
    /*********** constructor ***********/
    /***********************************/

    public SymbolTreeModel() {
        super(new SymbolTreeNode());
    }

    public SymbolTreeModel(TreeNode root) {
        super(root);
    }

    public SymbolTreeModel(TreeNode root, boolean asksAllowsChildren) {
        super(root, asksAllowsChildren);
    }

    /***********************************/
    /********* public function *********/
    /***********************************/

    public Filter getFilterLevel() {
        return filterLevel;
    }

    public void setFilterLevel(Filter filterLevel) {
        this.filterLevel = filterLevel;
    }

    /***********************************/
    /******** override function ********/
    /***********************************/

    @Override
    public Object getChild(Object parent, int index) {
        if (parent instanceof SymbolTreeNode) {
            return ((SymbolTreeNode) parent).getChildAt(index, filterLevel);
        }

        return super.getChild(parent, index);
    }

    @Override
    public int getChildCount(Object parent) {
        if (parent instanceof SymbolTreeNode) {
            return ((SymbolTreeNode) parent).getChildCount(filterLevel);
        }

        return super.getChildCount(parent);
    }
}
