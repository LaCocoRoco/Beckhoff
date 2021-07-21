package twincat.app.common;

import javax.swing.tree.DefaultMutableTreeNode;

public class ScopeTreeNode extends DefaultMutableTreeNode {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /********** constructor **********/
    /*********************************/  
    
    public ScopeTreeNode() {
        super();
    }

    public ScopeTreeNode(Object userObject, boolean allowsChildren) {
        super(userObject, allowsChildren);
    }

    public ScopeTreeNode(Object userObject) {
        super(userObject);
    }
}
