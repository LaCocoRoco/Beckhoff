package twincat.app.scope;

import java.awt.BorderLayout;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import twincat.LoremIpsum;
import twincat.Utilities;
import twincat.app.constants.Resources;

public class TreePanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /*************************/
    /*** local attributes ****/
    /*************************/

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);  
        
    /*************************/
    /****** constructor ******/
    /*************************/
     
    public TreePanel() {
        JScrollPane graphPanel = new JScrollPane();
        graphPanel.setViewportView(new LoremIpsum());

        JButton treeButtonAddScope = new JButton();
        treeButtonAddScope.setToolTipText(languageBundle.getString(Resources.TEXT_TREE_ADD_SCOPE));
        treeButtonAddScope.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_SCOPE)));
        treeButtonAddScope.setFocusable(false);
  
        JToolBar treeToolBar = new JToolBar();
        treeToolBar.add(treeButtonAddScope);
        treeToolBar.setFloatable(false);
        treeToolBar.setRollover(false);
        treeToolBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        this.setLayout(new BorderLayout());
        this.add(graphPanel, BorderLayout.CENTER);
        this.add(treeToolBar, BorderLayout.PAGE_START);
        this.setBorder(BorderFactory.createEmptyBorder()); 
    }
}
