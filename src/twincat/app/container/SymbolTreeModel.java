package twincat.app.container;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class SymbolTreeModel extends DefaultTreeModel {
    private static final long serialVersionUID = 1L;

    /*************************/
    /****** constructor ******/
    /*************************/

    public SymbolTreeModel(TreeNode root, boolean asksAllowsChildren) {
        super(root, asksAllowsChildren);
    }

    public SymbolTreeModel(TreeNode root) {
        super(root);
    }
}
