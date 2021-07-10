package twincat.app.container;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import twincat.Resources;
import twincat.Utilities;

public class SymbolTreeRenderer extends DefaultTreeCellRenderer {
    private static final long serialVersionUID = 1L;

    /*************************/
    /** constant attributes **/
    /*************************/

    private static final ImageIcon ICON_BIT = new ImageIcon(
            Utilities.getImageFromFilePath(Resources.PATH_ICON_DATATYPE_BIT));

    private static final ImageIcon ICON_INT8 = new ImageIcon(
            Utilities.getImageFromFilePath(Resources.PATH_ICON_DATATYPE_INT8));

    private static final ImageIcon ICON_UINT8 = new ImageIcon(
            Utilities.getImageFromFilePath(Resources.PATH_ICON_DATATYPE_UINT8));

    private static final ImageIcon ICON_INT16 = new ImageIcon(
            Utilities.getImageFromFilePath(Resources.PATH_ICON_DATATYPE_INT16));

    private static final ImageIcon ICON_UINT16 = new ImageIcon(
            Utilities.getImageFromFilePath(Resources.PATH_ICON_DATATYPE_UINT16));

    private static final ImageIcon ICON_INT32 = new ImageIcon(
            Utilities.getImageFromFilePath(Resources.PATH_ICON_DATATYPE_INT32));

    private static final ImageIcon ICON_UINT32 = new ImageIcon(
            Utilities.getImageFromFilePath(Resources.PATH_ICON_DATATYPE_UINT32));

    private static final ImageIcon ICON_REAL32 = new ImageIcon(
            Utilities.getImageFromFilePath(Resources.PATH_ICON_DATATYPE_REAL32));

    private static final ImageIcon ICON_REAL64 = new ImageIcon(
            Utilities.getImageFromFilePath(Resources.PATH_ICON_DATATYPE_REAL64));

    private static final ImageIcon ICON_BIGTYPE = new ImageIcon(
            Utilities.getImageFromFilePath(Resources.PATH_ICON_DATATYPE_BIGTYPE));

    private static final ImageIcon ICON_STRING = new ImageIcon(
            Utilities.getImageFromFilePath(Resources.PATH_ICON_DATATYPE_STRING));

    private static final ImageIcon ICON_OPEN = new ImageIcon(
            Utilities.getImageFromFilePath(Resources.PATH_ICON_FOLDER_OPEN));

    private static final ImageIcon ICON_CLOSED = new ImageIcon(
Utilities.getImageFromFilePath(Resources.PATH_ICON_FOLDER_CLOSED));

    /*************************/
    /****** constructor ******/
    /*************************/

    public SymbolTreeRenderer() {
        super();
    }

    /*************************/
    /********* public ********/
    /*************************/

    @Override
    public Component getTreeCellRendererComponent(JTree t, Object v,
            boolean s, boolean e, boolean l, int r, boolean hF) {
        
        super.getTreeCellRendererComponent(t, v, s, e, l, r, hF);

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) v;
        Object userObject = node.getUserObject();

        if (userObject instanceof SymbolNode) {
            SymbolNode symbolNode = (SymbolNode) userObject;

            switch (symbolNode.getSymbol().getDataType()) {
                case BIT:
                    this.setIcon(ICON_BIT);
                    break;

                case INT8:
                    this.setIcon(ICON_INT8);
                    break;

                case UINT8:
                    this.setIcon(ICON_UINT8);
                    break;

                case INT16:
                    this.setIcon(ICON_INT16);
                    break;

                case UINT16:
                    this.setIcon(ICON_UINT16);
                    break;

                case INT32:
                    this.setIcon(ICON_INT32);
                    break;

                case UINT32:
                    this.setIcon(ICON_UINT32);
                    break;

                case REAL32:
                    this.setIcon(ICON_REAL32);
                    break;
                case REAL64:
                    this.setIcon(ICON_REAL64);
                    break;

                case STRING:
                    this.setIcon(ICON_STRING);
                    break;

                case BIGTYPE:
                    this.setIcon(ICON_BIGTYPE);
                    break;

                default:
                    break;
            }

        } else if (e) {
            this.setIcon(ICON_OPEN);
        } else {
            this.setIcon(ICON_CLOSED);
        }

        return this;
    }
}
