package twincat.app.layer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.TreePath;

import twincat.Resources;
import twincat.Utilities;
import twincat.app.common.SymbolTreeModel;
import twincat.app.common.SymbolTreeNode;
import twincat.app.common.SymbolTreeRenderer;

public class BrowserTree extends JPanel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /******** cross reference ********/
    /*********************************/

    private final XReference xref;

    /*********************************/
    /****** local final variable *****/
    /*********************************/

    private final JTree browseTree = new JTree();

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private final ActionListener addScopeActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    };

    private final ActionListener addChartActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    };

    private final ActionListener addAxisActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    };

    private final ActionListener addChannelActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    };

    private final ActionListener searchButtonActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            xref.controlPanel.displaySearch();
        }
    };

    private final ActionListener deleteButtonActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    };

    private final BasicTreeUI browseTreeUI = new BasicTreeUI() {
        @Override
        protected boolean shouldPaintExpandControl(TreePath p, int r, boolean iE, boolean hBE, boolean iL) {
            return false;
        }
    };

    private final MouseAdapter browseTreeMouseAdapter = new MouseAdapter() {
        public void mousePressed(MouseEvent mouseEvent) {
            if (mouseEvent.getClickCount() == 2) {
                int x = mouseEvent.getX();
                int y = mouseEvent.getY();

                TreePath treePath = browseTree.getPathForLocation(x, y);

                if (treePath != null) {
                    scopeTreeNodeSelected(treePath);
                }
            }
        }
    };

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public BrowserTree(XReference xref) {
        this.xref = xref;

        browseTree.setCellRenderer(new SymbolTreeRenderer());
        browseTree.setBorder(BorderFactory.createEmptyBorder(5, -5, 0, 0));
        browseTree.setRootVisible(false);
        browseTree.setScrollsOnExpand(false);
        browseTree.setShowsRootHandles(true);
        browseTree.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_NORMAL));
        browseTree.setModel(new SymbolTreeModel());
        browseTree.addMouseListener(browseTreeMouseAdapter);
        browseTree.setUI(browseTreeUI);

        JScrollPane treePanel = new JScrollPane();
        treePanel.getVerticalScrollBar().setPreferredSize(new Dimension(Resources.DEFAULT_SCROLLBAR_WIDTH, 0));
        treePanel.setBorder(BorderFactory.createEmptyBorder());
        treePanel.setViewportView(browseTree);

        JButton addScopeButton = new JButton();
        addScopeButton.setToolTipText(languageBundle.getString(Resources.TEXT_BROWSER_ADD_SCOPE));
        addScopeButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_NAVIGATE_SCOPE)));
        addScopeButton.setFocusable(false);
        addScopeButton.addActionListener(addScopeActionListener);

        JButton addChartButton = new JButton();
        addChartButton.setToolTipText(languageBundle.getString(Resources.TEXT_BROWSER_ADD_CHART));
        addChartButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_NAVIGATE_CHART)));
        addChartButton.setFocusable(false);
        addChartButton.addActionListener(addChartActionListener);

        JButton addAxisButton = new JButton();
        addAxisButton.setToolTipText(languageBundle.getString(Resources.TEXT_BROWSER_ADD_AXIS));
        addAxisButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_NAVIGATE_AXIS)));
        addAxisButton.setFocusable(false);
        addAxisButton.addActionListener(addAxisActionListener);

        JButton addChannelButton = new JButton();
        addChannelButton.setToolTipText(languageBundle.getString(Resources.TEXT_BROWSER_ADD_CHANNEL));
        addChannelButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_NAVIGATE_CHANNEL)));
        addChannelButton.setFocusable(false);
        addChannelButton.addActionListener(addChannelActionListener);

        JButton searchButton = new JButton();
        searchButton.setToolTipText(languageBundle.getString(Resources.TEXT_BROWSER_SEARCH));
        searchButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_NAVIGATE_SEARCH)));
        searchButton.setFocusable(false);
        searchButton.addActionListener(searchButtonActionListener);

        JButton buttonDelete = new JButton();
        buttonDelete.setToolTipText(languageBundle.getString(Resources.TEXT_BROWSER_DELETE));
        buttonDelete.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_NAVIGATE_DELETE)));
        buttonDelete.setFocusable(false);
        buttonDelete.addActionListener(deleteButtonActionListener);

        JToolBar browserToolBar = new JToolBar();
        browserToolBar.setFloatable(false);
        browserToolBar.setRollover(false);
        browserToolBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        browserToolBar.add(addScopeButton);
        browserToolBar.addSeparator(new Dimension(5, 0));
        browserToolBar.add(addChartButton);
        browserToolBar.addSeparator(new Dimension(5, 0));
        browserToolBar.add(addAxisButton);
        browserToolBar.addSeparator(new Dimension(5, 0));
        browserToolBar.add(addChannelButton);
        browserToolBar.addSeparator(new Dimension(30, 0));
        browserToolBar.add(searchButton);
        browserToolBar.addSeparator(new Dimension(30, 0));
        browserToolBar.add(buttonDelete);

        this.setLayout(new BorderLayout());
        this.add(treePanel, BorderLayout.CENTER);
        this.add(browserToolBar, BorderLayout.PAGE_START);
        this.setBorder(BorderFactory.createEmptyBorder());
    }

    /*********************************/
    /********* public method *********/
    /*********************************/

    public void addScopeToTree() {
        
    }
    
    /*********************************/
    /******** private method *********/
    /*********************************/

    private void scopeTreeNodeSelected(TreePath treePath) {
        SymbolTreeNode symbolTreeNode = (SymbolTreeNode) treePath.getLastPathComponent();
        Object userObject = symbolTreeNode.getUserObject();

        System.out.println(userObject);
    }
}
