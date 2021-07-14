package twincat.app.common;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class ScopeTreeModel extends DefaultTreeModel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public ScopeTreeModel(TreeNode root, boolean asksAllowsChildren) {
        super(root, asksAllowsChildren);
    }

    public ScopeTreeModel(TreeNode root) {
        super(root);
    }
}
