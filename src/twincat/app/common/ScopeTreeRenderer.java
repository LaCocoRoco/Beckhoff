package twincat.app.common;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import twincat.Resources;
import twincat.Utilities;
import twincat.scope.Axis;
import twincat.scope.Channel;
import twincat.scope.Chart;
import twincat.scope.Scope;
import twincat.scope.TriggerChannel;
import twincat.scope.TriggerGroup;

public class ScopeTreeRenderer extends DefaultTreeCellRenderer {
    private static final long serialVersionUID = 1L;
    /*********************************/
    /**** local constant variable ****/
    /*********************************/

    private static final ImageIcon ICON_SCOPE = new ImageIcon(Utilities
            .getScaledIamgeFromFilePath(Resources.PATH_ICON_NAVIGATE_SCOPE, 0.8));

    private static final ImageIcon ICON_CHART = new ImageIcon(Utilities
            .getScaledIamgeFromFilePath(Resources.PATH_ICON_NAVIGATE_CHART, 0.8));
    
    private static final ImageIcon ICON_AXIS = new ImageIcon(Utilities
            .getScaledIamgeFromFilePath(Resources.PATH_ICON_NAVIGATE_AXIS, 0.8));    
    
    private static final ImageIcon ICON_CHANNEL = new ImageIcon(Utilities
            .getScaledIamgeFromFilePath(Resources.PATH_ICON_NAVIGATE_CHANNEL, 0.8));    
    
    private static final ImageIcon ICON_GROUP = new ImageIcon(Utilities
            .getScaledIamgeFromFilePath(Resources.PATH_ICON_NAVIGATE_GROUP, 0.8));    

    /*********************************/
    /******** override method ********/
    /*********************************/

    @Override
    public Component getTreeCellRendererComponent(JTree t, Object v,
            boolean s, boolean e, boolean l, int r, boolean hF) {

        super.getTreeCellRendererComponent(t, v, s, e, l, r, hF);

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) v;
        Object userObject = node.getUserObject();

        if (userObject instanceof Scope) {
            this.setIcon(ICON_SCOPE);

        }
        
        if (userObject instanceof Chart) {
            this.setIcon(ICON_CHART);
        }
        
        if (userObject instanceof Axis) {
            Axis axis = (Axis) userObject;
            this.setForeground(axis.getAxisColor());
            this.setIcon(ICON_AXIS);
        }
        
        if (userObject instanceof Channel) {
            Channel channel = (Channel) userObject;
            this.setForeground(channel.getLineColor());
            this.setIcon(ICON_CHANNEL);
        }
  
        if (userObject instanceof TriggerChannel) {
            this.setIcon(ICON_CHANNEL);
        }
        
        if (userObject instanceof TriggerGroup) {
            this.setIcon(ICON_GROUP);
        }
      
        return this;
    }
}
