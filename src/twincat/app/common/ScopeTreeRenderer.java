package twincat.app.common;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import twincat.Resources;
import twincat.Utilities;
import twincat.constant.DefaultColorTable;
import twincat.scope.Axis;
import twincat.scope.Channel;
import twincat.scope.Chart;
import twincat.scope.Scope;
import twincat.scope.TriggerChannel;
import twincat.scope.TriggerGroup;

public class ScopeTreeRenderer extends DefaultTreeCellRenderer {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /*** global constant variable ****/
    /*********************************/

    public static final Color DEFAULT_BACKGROUND_COLOR = DefaultColorTable.WHITE.color;

    /*********************************/
    /**** local constant variable ****/
    /*********************************/

    private static final Color TEXT_SELECTION_COLOR = DefaultColorTable.BLACK.color;

    private static final Color BORDER_SELECTION_COLOR = DefaultColorTable.GRAY.color;

    private static final Color BACKGROUND_SELECTION_COLOR = DefaultColorTable.JAVAGRAY.color;

    private static final ImageIcon ICON_SCOPE = new ImageIcon(Utilities.getScaledIamgeFromFilePath(Resources.PATH_ICON_APP_SCOPE, 0.8));

    private static final ImageIcon ICON_CHART = new ImageIcon(Utilities.getScaledIamgeFromFilePath(Resources.PATH_ICON_APP_CHART, 0.8));

    private static final ImageIcon ICON_AXIS = new ImageIcon(Utilities.getScaledIamgeFromFilePath(Resources.PATH_ICON_APP_AXIS, 0.8));

    private static final ImageIcon ICON_CHANNEL = new ImageIcon(Utilities.getScaledIamgeFromFilePath(Resources.PATH_ICON_APP_CHANNEL, 0.8));

    private static final ImageIcon ICON_TRIGGER_GROUP = new ImageIcon(Utilities.getScaledIamgeFromFilePath(Resources.PATH_ICON_APP_TRIGGER_GROUP, 0.8));

    private static final ImageIcon ICON_TRIGGER_CHANNEL = new ImageIcon(Utilities.getScaledIamgeFromFilePath(Resources.PATH_ICON_APP_TRIGGER_CHANNEL, 0.8));

    /*********************************/
    /******** override method ********/
    /*********************************/

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        Object userObject = node.getUserObject();

        if (this.getText().length() > 0) {
            if (this.getText().charAt(0) == ' ') {
                this.setText(this.getText().trim());
            }
        }

        if (this.getText().length() == 0) {
            this.setText(" ");
        }

        if (userObject instanceof TriggerChannel) {
            TriggerChannel triggerChannel = (TriggerChannel) userObject;
            this.setForeground(triggerChannel.getChannel().getLineColor());
            this.setIcon(ICON_TRIGGER_CHANNEL);
        }

        if (userObject instanceof TriggerGroup) {
            this.setIcon(ICON_TRIGGER_GROUP);
        }

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
            this.setEnabled(channel.isEnabled());
            this.setForeground(channel.getLineColor());
            this.setIcon(ICON_CHANNEL);
        }

        return this;
    }

    @Override
    public void setTextSelectionColor(Color newColor) {
        super.setTextSelectionColor(TEXT_SELECTION_COLOR);
    }

    @Override
    public void setBorderSelectionColor(Color newColor) {
        super.setBorderSelectionColor(BORDER_SELECTION_COLOR);
    }

    @Override
    public void setBackgroundSelectionColor(Color newColor) {
        super.setBackgroundSelectionColor(BACKGROUND_SELECTION_COLOR);
    }

    @Override
    public Color getBackgroundNonSelectionColor() {
        return DEFAULT_BACKGROUND_COLOR;
    }
}
