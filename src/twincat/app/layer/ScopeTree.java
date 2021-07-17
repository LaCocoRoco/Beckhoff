package twincat.app.layer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
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
import javax.swing.tree.TreeSelectionModel;

import twincat.Resources;
import twincat.Utilities;
import twincat.app.common.ScopeTreeModel;
import twincat.app.common.ScopeTreeNode;
import twincat.app.common.ScopeTreeRenderer;
import twincat.app.constant.Propertie;
import twincat.app.constant.Tree;
import twincat.scope.Acquisition;
import twincat.scope.Axis;
import twincat.scope.Channel;
import twincat.scope.Chart;
import twincat.scope.Scope;
import twincat.scope.TriggerChannel;
import twincat.scope.TriggerGroup;

public class ScopeTree extends JPanel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /******** cross reference ********/
    /*********************************/

    private final XReference xref;

    /*********************************/
    /****** local final variable *****/
    /*********************************/

    private Propertie card = Propertie.EMPTY;
    
    private final JTree browseTree = new JTree();

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private final ActionListener addScopeActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            addScopeTreeNode();
        }
    };

    private final ActionListener addChartActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            addChartTreeNode();
        }
    };

    private final ActionListener addAxisActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            addAxisTreeNode();
        }
    };

    private final ActionListener addChannelActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            addChannelTreeNode();
        }
    };

    private final ActionListener addTriggerActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            addTriggerChannelTreeNode();
        }
    };

    private final ActionListener searchButtonActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            searchSymbolAcquisition();
        }
    };

    private final ActionListener deleteButtonActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            deleteTreeNode();
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
            TreePath treePath = browseTree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());
            
            if (treePath != null) {
                treeNodeSelectedSingleClick(treePath);      
            }
        }
    };

    private final KeyListener browseTreeKeyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent keyEvent) {
            /* empty */
        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_DELETE) {
                deleteTreeNode(); 
            }
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {
            /* empty */
        }
    };
    
    /*********************************/
    /********** constructor **********/
    /*********************************/

    public ScopeTree(XReference xref) {
        this.xref = xref;

        browseTree.setCellRenderer(new ScopeTreeRenderer());
        browseTree.setBorder(BorderFactory.createEmptyBorder(5, -5, 0, 0));
        browseTree.setRootVisible(false);
        browseTree.setScrollsOnExpand(false);
        browseTree.setShowsRootHandles(true);
        browseTree.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_NORMAL));
        browseTree.setModel(new ScopeTreeModel(new ScopeTreeNode()));
        browseTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        browseTree.addMouseListener(browseTreeMouseAdapter);
        browseTree.setUI(browseTreeUI);
        browseTree.addKeyListener(browseTreeKeyListener);

        JScrollPane treePanel = new JScrollPane();
        treePanel.getVerticalScrollBar().setPreferredSize(new Dimension(Resources.DEFAULT_SCROLLBAR_WIDTH, 0));
        treePanel.setBorder(BorderFactory.createEmptyBorder());
        treePanel.setViewportView(browseTree);

        JButton addScopeButton = new JButton();
        addScopeButton.setToolTipText(languageBundle.getString(Resources.TEXT_SCOPE_TREE_ADD_SCOPE));
        addScopeButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_NAVIGATE_SCOPE)));
        addScopeButton.setFocusable(false);
        addScopeButton.addActionListener(addScopeActionListener);

        JButton addChartButton = new JButton();
        addChartButton.setToolTipText(languageBundle.getString(Resources.TEXT_SCOPE_TREE_ADD_CHART));
        addChartButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_NAVIGATE_CHART)));
        addChartButton.setFocusable(false);
        addChartButton.addActionListener(addChartActionListener);

        JButton addAxisButton = new JButton();
        addAxisButton.setToolTipText(languageBundle.getString(Resources.TEXT_SCOPE_TREE_ADD_AXIS));
        addAxisButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_NAVIGATE_AXIS)));
        addAxisButton.setFocusable(false);
        addAxisButton.addActionListener(addAxisActionListener);

        JButton addChannelButton = new JButton();
        addChannelButton.setToolTipText(languageBundle.getString(Resources.TEXT_SCOPE_TREE_ADD_CHANNEL));
        addChannelButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_NAVIGATE_CHANNEL)));
        addChannelButton.setFocusable(false);
        addChannelButton.addActionListener(addChannelActionListener);

        JButton addTriggerButton = new JButton();
        addTriggerButton.setToolTipText(languageBundle.getString(Resources.TEXT_SCOPE_TREE_ADD_TRIGGER));
        addTriggerButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_NAVIGATE_TRIGGER)));
        addTriggerButton.setFocusable(false);
        addTriggerButton.addActionListener(addTriggerActionListener);

        JButton searchButton = new JButton();
        searchButton.setToolTipText(languageBundle.getString(Resources.TEXT_SCOPE_TREE_SEARCH));
        searchButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_NAVIGATE_SEARCH)));
        searchButton.setFocusable(false);
        searchButton.addActionListener(searchButtonActionListener);

        JButton buttonDelete = new JButton();
        buttonDelete.setToolTipText(languageBundle.getString(Resources.TEXT_SCOPE_TREE_DELETE));
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
        browserToolBar.addSeparator(new Dimension(5, 0));
        browserToolBar.add(addTriggerButton);
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

    public void abortSymbolAcquisition() {
        xref.treePanel.setCard(Tree.SCOPE);
        xref.propertiesPanel.setCard(card);
    }

    public void applySymbolAcquisition(Acquisition acquisition) {
        // get selected tree node
        ScopeTreeNode selectedTreeNode = (ScopeTreeNode) browseTree.getLastSelectedPathComponent();

        // set acquisition of selected tree node
        if (selectedTreeNode != null) {
            Object userObject = selectedTreeNode.getUserObject();

            if (userObject instanceof Channel) {
                Channel Channel = (Channel) userObject;
                xref.channelProperties.setChannel(Channel);
            } 
        }
        
        // set channel acquisition
        ScopeTreeNode channelTreeNode = getChannelTreeNode();
        Object userObject = channelTreeNode.getUserObject();
        Channel channel = (Channel) userObject;
        channel.setAcquisition(acquisition);
        channel.setChannelName(acquisition.getChannelName());
        
        // expand path
        expandTreeNode(channelTreeNode);
        
        // display scope tree view
        xref.treePanel.setCard(Tree.SCOPE);
        xref.propertiesPanel.setCard(Propertie.CHANNEL);
    }

    public void reloadSelectedTreeNode() {
        // get selected tree node
        ScopeTreeNode selectedTreeNode = (ScopeTreeNode) browseTree.getLastSelectedPathComponent();

        // refresh selected tree node
        if (selectedTreeNode != null) {
            expandTreeNode(selectedTreeNode);
        }
    }   
    
    /*********************************/
    /******** private method *********/
    /*********************************/

    private void searchSymbolAcquisition() {
        // get selected tree node
        ScopeTreeNode selectedTreeNode = (ScopeTreeNode) browseTree.getLastSelectedPathComponent();
        
        if (selectedTreeNode != null) {
            Object userObject = selectedTreeNode.getUserObject();

            if (userObject instanceof Channel) {
                // use channel acquisition data
                Channel channel = (Channel) userObject;
                xref.acquisitionProperties.cloneAcquisition(channel.getAcquisition());
            } 
        } else {
            // use new acquisition data
            xref.acquisitionProperties.cloneAcquisition(new Acquisition());
        }
        
        // display symbol acquisition view
        card = xref.propertiesPanel.getCard();
        xref.acquisitionProperties.reloadAcquisition();
        xref.treePanel.setCard(Tree.SYMBOL);
        xref.propertiesPanel.setCard(Propertie.ACQUISITION);
    }

    private void treeNodeSelectedSingleClick(TreePath treePath) {  
        ScopeTreeNode treeNode = (ScopeTreeNode) treePath.getLastPathComponent();
        Object userObject = treeNode.getUserObject();

        if (userObject instanceof Scope) {
            Scope scope = (Scope) userObject;
            xref.propertiesPanel.setCard(Propertie.SCOPE);
            xref.scopeProperties.setScope(scope);
            xref.scopeProperties.reloadScope();
        }

        if (userObject instanceof Chart) {
            Chart chart = (Chart) userObject;
            xref.propertiesPanel.setCard(Propertie.CHART);
            xref.chartProperties.setChart(chart);
            xref.chartProperties.reloadChart();
            xref.chartPanel.setChart(chart);
            xref.chartPanel.reloadChart();
        }

        if (userObject instanceof Axis) {
            Axis axis = (Axis) userObject;
            xref.propertiesPanel.setCard(Propertie.AXIS);
            xref.axisProperties.setAxis(axis);
        }

        if (userObject instanceof Channel) {
            Channel Channel = (Channel) userObject;
            xref.propertiesPanel.setCard(Propertie.CHANNEL);
            xref.channelProperties.setChannel(Channel);
        }

        if (userObject instanceof TriggerGroup) {
            TriggerGroup tgriggerGroup = (TriggerGroup) userObject;
            xref.propertiesPanel.setCard(Propertie.TRIGGER_GROUP);
            xref.triggerGroupProperties.setTriggerGroup(tgriggerGroup);
        }

        if (userObject instanceof TriggerChannel) {
            TriggerChannel triggerChannel = (TriggerChannel) userObject;
            xref.propertiesPanel.setCard(Propertie.TRIGGER_CHANNEL);
            xref.triggerChannelProperties.setTriggerChannel(triggerChannel);
        }
    }

    private void expandTreeNode(ScopeTreeNode treeNode) {
        // expand tree node
        TreePath treePath = new TreePath(treeNode.getPath());
        browseTree.setSelectionPath(treePath);

        // save path of all expanded nodes
        List<TreePath> expandedPath = new ArrayList<>();
        for (int i = 0; i < browseTree.getRowCount() - 1; i++) {
            TreePath currPath = browseTree.getPathForRow(i);
            TreePath nextPath = browseTree.getPathForRow(i + 1);
            if (currPath.isDescendant(nextPath)) {
                expandedPath.add(currPath);
            }
        }

        // reload tree model
        ScopeTreeModel treeModel = (ScopeTreeModel) browseTree.getModel();
        treeModel.reload();

        // expand all nodes
        for (TreePath path : expandedPath) {
            browseTree.expandPath(path);
        }

        // set tree node selected
        browseTree.setSelectionPath(treePath);
        treeNodeSelectedSingleClick(treePath);
    }

    private void deleteTreeNode() {
        // get selected tree node
        ScopeTreeNode selectedTreeNode = (ScopeTreeNode) browseTree.getLastSelectedPathComponent();

        // remove selected tree node
        if (selectedTreeNode != null) {
            ScopeTreeNode selectedParentTreeNode = (ScopeTreeNode) selectedTreeNode.getParent();
            selectedTreeNode.removeFromParent();
            
            if (selectedParentTreeNode != null) {
                expandTreeNode(selectedParentTreeNode);       
            }
        }
        
        // nothing to display
        ScopeTreeNode rootTreeNode = (ScopeTreeNode) browseTree.getModel().getRoot();
        if (rootTreeNode.getChildCount() == 0) {
            xref.propertiesPanel.setCard(Propertie.EMPTY);
        }
    }

    private ScopeTreeNode addScopeTreeNode() {
        // add scope
        Scope scope = new Scope();

        // add chart node
        ScopeTreeModel treeModel = (ScopeTreeModel) browseTree.getModel();
        ScopeTreeNode rootTreeNode = (ScopeTreeNode) treeModel.getRoot();
        ScopeTreeNode scopeTreeNode = new ScopeTreeNode();
        scopeTreeNode.setUserObject(scope);
        rootTreeNode.add(scopeTreeNode);

        // expand path
        expandTreeNode(scopeTreeNode);
        return scopeTreeNode;
    }

    private ScopeTreeNode getScopeTreeNode() {
        // get scope node from this tree path
        TreePath selectedTreePath = browseTree.getSelectionPath();

        if (selectedTreePath != null) {
            Object[] selectedObjectArray = (Object[]) selectedTreePath.getPath();

            for (Object selectedObject : selectedObjectArray) {
                ScopeTreeNode selectedTreeNode = (ScopeTreeNode) selectedObject;
                Object userObject = selectedTreeNode.getUserObject();

                if (userObject instanceof Scope) {
                    return selectedTreeNode;
                }
            }
        }

        // add new scope if no node present
        ScopeTreeNode scopeTreeNode = addScopeTreeNode();
        return scopeTreeNode;
    }

    private ScopeTreeNode addChartTreeNode() {
        // add chart
        Chart chart = new Chart();

        // add scope node
        ScopeTreeNode scopeTreeNode = getScopeTreeNode();
        ScopeTreeNode chartTreeNode = new ScopeTreeNode();
        chartTreeNode.setUserObject(chart);
        scopeTreeNode.add(chartTreeNode);

        // expand path
        expandTreeNode(chartTreeNode);
        return chartTreeNode;
    }

    private ScopeTreeNode getChartTreeNode() {
        // get chart node from this tree path
        TreePath selectedTreePath = browseTree.getSelectionPath();

        if (selectedTreePath != null) {
            Object[] selectedObjectArray = (Object[]) selectedTreePath.getPath();

            for (Object selectedObject : selectedObjectArray) {
                ScopeTreeNode selectedTreeNode = (ScopeTreeNode) selectedObject;
                Object userObject = selectedTreeNode.getUserObject();

                if (userObject instanceof Chart) {
                    return selectedTreeNode;
                }
            }
        }

        // add new scope if no node present
        ScopeTreeNode chartTreeNode = addChartTreeNode();
        return chartTreeNode;
    }

    private ScopeTreeNode addAxisTreeNode() {
        // add chart
        Axis axis = new Axis();
        axis.setAxisColor(Utilities.getRandomColor());
        
        // add scope node
        ScopeTreeNode chartTreeNode = getChartTreeNode();
        ScopeTreeNode axisTreeNode = new ScopeTreeNode();
        axisTreeNode.setUserObject(axis);
        chartTreeNode.add(axisTreeNode);

        // expand path
        expandTreeNode(axisTreeNode);
        return axisTreeNode;
    }

    private ScopeTreeNode getAxisTreeNode() {
        // get chart node from this tree path
        TreePath selectedTreePath = browseTree.getSelectionPath();

        if (selectedTreePath != null) {
            Object[] selectedObjectArray = (Object[]) selectedTreePath.getPath();

            for (Object selectedObject : selectedObjectArray) {
                ScopeTreeNode selectedTreeNode = (ScopeTreeNode) selectedObject;
                Object userObject = selectedTreeNode.getUserObject();

                if (userObject instanceof Axis) {
                    return selectedTreeNode;
                }
            }
        }

        // add new scope if no node present
        ScopeTreeNode chartTreeNode = addAxisTreeNode();
        return chartTreeNode;
    }

    private ScopeTreeNode addChannelTreeNode() {
        // add chart
        Channel channel = new Channel();
        channel.setLineColor(Utilities.getRandomColor());

        // add scope node
        ScopeTreeNode axisTreeNode = getAxisTreeNode();
        ScopeTreeNode channelTreeNode = new ScopeTreeNode();
        channelTreeNode.setUserObject(channel);
        axisTreeNode.add(channelTreeNode);

        // expand path
        expandTreeNode(channelTreeNode);
        return channelTreeNode;
    }

    private ScopeTreeNode getChannelTreeNode() {
        // get chart node from this tree path
        TreePath selectedTreePath = browseTree.getSelectionPath();

        if (selectedTreePath != null) {
            Object[] selectedObjectArray = (Object[]) selectedTreePath.getPath();

            for (Object selectedObject : selectedObjectArray) {
                ScopeTreeNode selectedTreeNode = (ScopeTreeNode) selectedObject;
                Object userObject = selectedTreeNode.getUserObject();

                if (userObject instanceof Channel) {
                    return selectedTreeNode;
                }
            }
        }

        // add new scope if no node present
        ScopeTreeNode channelTreeNode = addChannelTreeNode();
        return channelTreeNode;
    }
    
    // TODO : trigger to trigger channel!
    
    private ScopeTreeNode addTriggerChannelTreeNode() {
        return null;
    }
    
    private ScopeTreeNode getTriggerChannelTreeNode() {
        return null;
    }
}
