package twincat.app.layer;

import java.awt.BorderLayout;
import java.awt.Color;
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
import twincat.app.constant.Browser;
import twincat.app.constant.Navigation;
import twincat.app.constant.Propertie;
import twincat.app.constant.Window;
import twincat.scope.Acquisition;
import twincat.scope.Axis;
import twincat.scope.Channel;
import twincat.scope.Chart;
import twincat.scope.Scope;
import twincat.scope.TriggerChannel;
import twincat.scope.TriggerGroup;

public class ScopeBrowser extends JPanel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /******** cross reference ********/
    /*********************************/

    private final XReference xref;

    /*********************************/
    /****** local final variable *****/
    /*********************************/

    private final JTree browseTree = new JTree();

    private final List<Scope> scopeList = new ArrayList<Scope>();

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

    private final ActionListener addTriggerChannelActionListener = new ActionListener() {
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
            removeTreeNode();
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
                laodPropertie(treePath);
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
                removeTreeNode();
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

    public ScopeBrowser(XReference xref) {
        this.xref = xref;

        browseTree.setCellRenderer(new ScopeTreeRenderer());
        browseTree.setBorder(BorderFactory.createEmptyBorder(5, -5, 0, 0));
        browseTree.setRootVisible(false);
        browseTree.setScrollsOnExpand(false);
        browseTree.setShowsRootHandles(true);
        browseTree.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_NORMAL));
        browseTree.setModel(new ScopeTreeModel(new ScopeTreeNode("Browser")));
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
        addScopeButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_APP_SCOPE)));
        addScopeButton.setFocusable(false);
        addScopeButton.addActionListener(addScopeActionListener);

        JButton addChartButton = new JButton();
        addChartButton.setToolTipText(languageBundle.getString(Resources.TEXT_SCOPE_TREE_ADD_CHART));
        addChartButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_APP_CHART)));
        addChartButton.setFocusable(false);
        addChartButton.addActionListener(addChartActionListener);

        JButton addAxisButton = new JButton();
        addAxisButton.setToolTipText(languageBundle.getString(Resources.TEXT_SCOPE_TREE_ADD_AXIS));
        addAxisButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_APP_AXIS)));
        addAxisButton.setFocusable(false);
        addAxisButton.addActionListener(addAxisActionListener);

        JButton addChannelButton = new JButton();
        addChannelButton.setToolTipText(languageBundle.getString(Resources.TEXT_SCOPE_TREE_ADD_CHANNEL));
        addChannelButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_APP_CHANNEL)));
        addChannelButton.setFocusable(false);
        addChannelButton.addActionListener(addChannelActionListener);

        JButton addTriggerChannelButton = new JButton();
        addTriggerChannelButton.setToolTipText(languageBundle.getString(Resources.TEXT_SCOPE_TREE_ADD_TRIGGER));
        addTriggerChannelButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_APP_TRIGGER_CHANNEL)));
        addTriggerChannelButton.setFocusable(false);
        addTriggerChannelButton.addActionListener(addTriggerChannelActionListener);

        JButton searchButton = new JButton();
        searchButton.setToolTipText(languageBundle.getString(Resources.TEXT_SCOPE_TREE_SEARCH));
        searchButton.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_APP_SEARCH)));
        searchButton.setFocusable(false);
        searchButton.addActionListener(searchButtonActionListener);

        JButton buttonDelete = new JButton();
        buttonDelete.setToolTipText(languageBundle.getString(Resources.TEXT_SCOPE_TREE_DELETE));
        buttonDelete.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_APP_DELETE)));
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
        browserToolBar.add(addTriggerChannelButton);
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

    public void removeScope() {
        ScopeTreeNode rootNode = (ScopeTreeNode) browseTree.getModel().getRoot();
        
        // remove scope node
        for (int i = 0; i < rootNode.getChildCount(); i++) {
            ScopeTreeNode scopeNode = (ScopeTreeNode) rootNode.getChildAt(i);
            removeScopeTreeNode(scopeNode);
        }
        
        // reload
        reloadTreeNode(rootNode);
        
        // remove scope
        for (Scope scope : scopeList) {
            scope.close();
        }
    }

    public void addScope(Scope scope) {
        // add scope
        ScopeTreeNode scopeTreeNode = addScopeTreeNode(scope);

        List<Chart> chartList = scope.getChartList();
        for (Chart chart : chartList) {
            
            // add chart
            addChartTreeNode(chart);

            List<Axis> axisList = chart.getAxisList();
            for (Axis axis : axisList) {
                // add axis
                addAxisTreeNode(axis);

                List<Channel> channelList = axis.getChannelList();
                for (Channel channel : channelList) {
                    // add channel
                    addChannelTreeNode(channel);
                }
            }

            List<TriggerGroup> triggerGroupList = chart.getTriggerGroupList();
            if (!triggerGroupList.isEmpty()) {
                
                for (TriggerGroup triggerGroup : triggerGroupList) {
                    // add trigger group
                    addTriggerGroupTreeNode(triggerGroup);
                    
                    List<TriggerChannel> triggerChannelList = triggerGroup.getTriggerChannelList();
                    for (TriggerChannel triggerChannel : triggerChannelList) {
                        // add trigger channel
                        addTriggerChannelTreeNode(triggerChannel);
                    }
                }
            }
            
        }

        // select scope
        reloadTreeNode(scopeTreeNode);
        loadScopeProperties(scopeTreeNode);

        // display scope tree
        xref.browserPanel.setCard(Browser.SCOPE);
        xref.propertiesPanel.setCard(Propertie.EMPTY);
        xref.windowPanel.setCard(Window.SCOPE);
        xref.navigationPanel.setCard(Navigation.CHART); 
    }

    public void abortSymbolAcquisition() {
        xref.browserPanel.setCard(Browser.SCOPE);
        xref.propertiesPanel.lastCard();
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
        reloadTreeNode(channelTreeNode);

        // display scope tree view
        xref.browserPanel.setCard(Browser.SCOPE);
        xref.propertiesPanel.setCard(Propertie.CHANNEL);
    }

    public void reloadSelectedTreeNode() {
        // get selected tree node
        ScopeTreeNode selectedTreeNode = (ScopeTreeNode) browseTree.getLastSelectedPathComponent();

        // refresh selected tree node
        if (selectedTreeNode != null) {
            reloadTreeNode(selectedTreeNode);
        }
    }

    /*********************************/
    /******** private method *********/
    /*********************************/

    private void reloadTreeNode(ScopeTreeNode treeNode) {
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

        // expand nodes to old state
        for (TreePath path : expandedPath) {
            browseTree.expandPath(path);
        }
        
        // select tree node
        TreePath treePath = new TreePath(treeNode.getPath());
        browseTree.setSelectionPath(treePath);
        browseTree.scrollPathToVisible(treePath);
    }

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
        xref.acquisitionProperties.reloadAcquisition();
    }

    private void laodPropertie(TreePath treePath) {
        ScopeTreeNode treeNode = (ScopeTreeNode) treePath.getLastPathComponent();
        Object userObject = treeNode.getUserObject();
        
        if (userObject instanceof Scope) {
            loadScopeProperties(treeNode);
        }

        if (userObject instanceof Chart) {
            loadChartProperties(treeNode);
        }

        if (userObject instanceof Axis) {
            loadAxisProperties(treeNode);
        }

        if (userObject instanceof Channel) {
            loadChannelProperties(treeNode);
        }

        if (userObject instanceof TriggerGroup) {
            loadTriggerGroupProperties(treeNode);
        }

        if (userObject instanceof TriggerChannel) {
            loadTriggerChannelProperties(treeNode);
        } 
    }
    
    private void loadScopeProperties(ScopeTreeNode treeNode) {
        Object userObject = treeNode.getUserObject();
        Scope scope = (Scope) userObject;
        xref.scopeProperties.setScope(scope);  
    }
    
    private void loadChartProperties(ScopeTreeNode treeNode) {
        Object userObject = treeNode.getUserObject();
        Chart chart = (Chart) userObject;
        xref.chartProperties.setChart(chart);
        xref.chartPanel.setChart(chart);
    }
    
    private void loadAxisProperties(ScopeTreeNode treeNode) {
        Object userObject = treeNode.getUserObject();
        Axis axis = (Axis) userObject;
        xref.axisProperties.setAxis(axis);
    }
    
    private void loadChannelProperties(ScopeTreeNode treeNode) {
        Object userObject = treeNode.getUserObject();
        Channel Channel = (Channel) userObject;
        xref.channelProperties.setChannel(Channel);
    }
    
    private void loadTriggerGroupProperties(ScopeTreeNode treeNode) {
        Object userObject = treeNode.getUserObject();
        TriggerGroup tgriggerGroup = (TriggerGroup) userObject;
        xref.triggerGroupProperties.setTriggerGroup(tgriggerGroup);
    }
    
    private void loadTriggerChannelProperties(ScopeTreeNode treeNode) {
        Object userObject = treeNode.getUserObject();
        TriggerChannel triggerChannel = (TriggerChannel) userObject;
        xref.triggerChannelProperties.setTriggerChannel(triggerChannel);
    }

    private void removeTreeNode() {
        ScopeTreeNode selectedTreeNode = (ScopeTreeNode) browseTree.getLastSelectedPathComponent();

        if (selectedTreeNode != null) {
            Object selectedObject = selectedTreeNode.getUserObject();

            if (selectedObject instanceof Scope) {
                selectedTreeNode = removeScopeTreeNode(selectedTreeNode);
            }

            if (selectedObject instanceof Chart) {
                selectedTreeNode = removeChartTreeNode(selectedTreeNode);
            }

            if (selectedObject instanceof Axis) {
                selectedTreeNode = removeAxisTreeNode(selectedTreeNode);
            }

            if (selectedObject instanceof Channel) {
                selectedTreeNode = removeChannelTreeNode(selectedTreeNode);
            }

            if (selectedObject instanceof TriggerGroup) {
                selectedTreeNode = removeTriggerGroupTreeNode(selectedTreeNode);
            }

            if (selectedObject instanceof TriggerChannel) {
                selectedTreeNode = removeTriggerChannelTreeNode(selectedTreeNode);
            }

            // expand tree node
            if (selectedTreeNode != null) {
                reloadTreeNode(selectedTreeNode);
            }

            // display empty properties panel if root is empty
            ScopeTreeNode rootTreeNode = (ScopeTreeNode) browseTree.getModel().getRoot();
            if (rootTreeNode.getChildCount() == 0) {
                xref.propertiesPanel.setCard(Propertie.EMPTY);
            }
        }
    }

    private ScopeTreeNode removeScopeTreeNode(ScopeTreeNode treeNode) {
        // remove scope tree node
        ScopeTreeNode parentTreeNode = (ScopeTreeNode) treeNode.getParent();
        treeNode.removeFromParent();
        
        return parentTreeNode;
    }


    private ScopeTreeNode removeChartTreeNode(ScopeTreeNode treeNode) {
        // remove axis of chart
        for (int i = 0; i < treeNode.getChildCount(); i++) {
            ScopeTreeNode axisTreeNode = (ScopeTreeNode) treeNode.getChildAt(i);
            removeAxisTreeNode(axisTreeNode);
        }
        
        // remove chart
        ScopeTreeNode parentTreeNode = (ScopeTreeNode) treeNode.getParent();
        treeNode.removeFromParent();

        return parentTreeNode;
    }
 
    private ScopeTreeNode removeAxisTreeNode(ScopeTreeNode treeNode) {
        // remove channel of axis
        for (int i = 0; i < treeNode.getChildCount(); i++) {
            ScopeTreeNode channelTreeNode = (ScopeTreeNode) treeNode.getChildAt(i);
            removeChannelTreeNode(channelTreeNode);
        }
        
        // remove axis
        ScopeTreeNode parentTreeNode = (ScopeTreeNode) treeNode.getParent();
        treeNode.removeFromParent();
        
        return parentTreeNode;
    }

    private ScopeTreeNode removeChannelTreeNode(ScopeTreeNode treeNode) {
        // get channel
        Channel channel = (Channel) treeNode.getUserObject();
        TreePath treePath = browseTree.getSelectionPath();
        Object[] objectArray = (Object[]) treePath.getPath();

        // lookup for trigger channel
        for (Object object : objectArray) {
            ScopeTreeNode chartTreeNode = (ScopeTreeNode) object;
            Object chartUserObject = chartTreeNode.getUserObject();

            // get scope of channel
            if (chartUserObject instanceof Scope) {
                for (int i = 0; i < chartTreeNode.getChildCount(); i++) {
                    ScopeTreeNode triggerGroupTreeNode = (ScopeTreeNode) chartTreeNode.getChildAt(i);
                    Object triggerGroupUserObject = triggerGroupTreeNode.getUserObject();

                    // get trigger group of scope
                    if (triggerGroupUserObject instanceof TriggerGroup) {
                        for (int x = 0; x < triggerGroupTreeNode.getChildCount(); x++) {
                            ScopeTreeNode triggerChannelTreeNode = (ScopeTreeNode) triggerGroupTreeNode.getChildAt(x);
                            Object triggerChannelUserObject = triggerChannelTreeNode.getUserObject();
                            TriggerChannel triggerChannel = (TriggerChannel) triggerChannelUserObject;

                            // remove trigger channel
                            if (triggerChannel.getChannel().equals(channel)) {
                                removeTriggerChannelTreeNode(triggerChannelTreeNode);
                            }
                        }
                    }
                }
            }
        }

        // remove channel
        ScopeTreeNode parentTreeNode = (ScopeTreeNode) treeNode.getParent();
        treeNode.removeFromParent();

        return parentTreeNode;
    }
  
    private ScopeTreeNode removeTriggerGroupTreeNode(ScopeTreeNode treeNode) {
        ScopeTreeNode parentTreeNode = (ScopeTreeNode) treeNode.getParent();
        treeNode.removeFromParent();
        
        return parentTreeNode;
    }

    private ScopeTreeNode removeTriggerChannelTreeNode(ScopeTreeNode treeNode) {
        ScopeTreeNode parentTreeNode = (ScopeTreeNode) treeNode.getParent();
        treeNode.removeFromParent();

        // remove trigger group if it is empty
        if (parentTreeNode.getChildCount() == 0) {
            return removeTriggerGroupTreeNode(parentTreeNode);
        }

        return parentTreeNode;
    }

    private ScopeTreeNode getScopeTreeNode() {
        // get scope node from this tree path
        TreePath selectedTreePath = browseTree.getSelectionPath();

        if (selectedTreePath != null) {
            Object[] selectedObjectArray = (Object[]) selectedTreePath.getPath();

            // loop through tree path
            for (Object selectedObject : selectedObjectArray) {
                ScopeTreeNode selectedTreeNode = (ScopeTreeNode) selectedObject;
                Object userObject = selectedTreeNode.getUserObject();

                // get scope
                if (userObject instanceof Scope) {
                    return selectedTreeNode;
                }
            }
        }

        // add new scope node if none present
        ScopeTreeNode scopeTreeNode = addScopeTreeNode();
        
        return scopeTreeNode;
    }

    private ScopeTreeNode getChartTreeNode() {
        // get chart node from this tree path
        TreePath selectedTreePath = browseTree.getSelectionPath();

        if (selectedTreePath != null) {
            Object[] selectedObjectArray = (Object[]) selectedTreePath.getPath();

            // loop through tree path
            for (Object selectedObject : selectedObjectArray) {
                ScopeTreeNode selectedTreeNode = (ScopeTreeNode) selectedObject;
                Object userObject = selectedTreeNode.getUserObject();

                // get chart
                if (userObject instanceof Chart) {
                    return selectedTreeNode;
                }
            }
        }

        // add new chart node if none present
        ScopeTreeNode chartTreeNode = addChartTreeNode();

        return chartTreeNode;
    }

    private ScopeTreeNode getAxisTreeNode() {
        // get chart node from this tree path
        TreePath selectedTreePath = browseTree.getSelectionPath();

        if (selectedTreePath != null) {
            Object[] selectedObjectArray = (Object[]) selectedTreePath.getPath();

            // loop through tree path
            for (Object selectedObject : selectedObjectArray) {
                ScopeTreeNode selectedTreeNode = (ScopeTreeNode) selectedObject;
                Object userObject = selectedTreeNode.getUserObject();

                // get axis
                if (userObject instanceof Axis) {
                    return selectedTreeNode;
                }
            }
        }

        // add new axis node if none present
        ScopeTreeNode chartTreeNode = addAxisTreeNode();

        return chartTreeNode;
    }

    private ScopeTreeNode getChannelTreeNode() {
        // get chart node from this tree path
        TreePath selectedTreePath = browseTree.getSelectionPath();

        if (selectedTreePath != null) {
            Object[] selectedObjectArray = (Object[]) selectedTreePath.getPath();

            // loop through tree path
            for (Object selectedObject : selectedObjectArray) {
                ScopeTreeNode selectedTreeNode = (ScopeTreeNode) selectedObject;
                Object userObject = selectedTreeNode.getUserObject();

                // get channel
                if (userObject instanceof Channel) {
                    return selectedTreeNode;
                }
            }
        }

        // add new scope node if none present
        ScopeTreeNode channelTreeNode = addChannelTreeNode();

        return channelTreeNode;
    }

    private ScopeTreeNode getTriggerGroupTreeNode() {
        // get chart node from this tree path
        TreePath selectedTreePath = browseTree.getSelectionPath();

        if (selectedTreePath != null) {
            Object[] selectedObjectArray = (Object[]) selectedTreePath.getPath();

            // loop through tree path
            for (Object selectedObject : selectedObjectArray) {
                ScopeTreeNode selectedTreeNode = (ScopeTreeNode) selectedObject;
                Object userObject = selectedTreeNode.getUserObject();

                // get trigger group
                if (userObject instanceof TriggerGroup) {
                    return selectedTreeNode;
                }
            }
        }

        // add new trigger group node if none present
        ScopeTreeNode channelTreeNode = addTriggerGroupTreeNode();

        return channelTreeNode;
    }
 
    private ScopeTreeNode addScopeTreeNode() {
        // initialize scope
        Scope scope = new Scope();
        
        // add scope tree node
        ScopeTreeNode scopeTreeNode = addScopeTreeNode(scope);
        
        // load scope tree node properties
        loadScopeProperties(scopeTreeNode);
        
        return scopeTreeNode;
    }

    private ScopeTreeNode addScopeTreeNode(Scope scope) {
        // add scope node
        ScopeTreeModel treeModel = (ScopeTreeModel) browseTree.getModel();
        ScopeTreeNode rootTreeNode = (ScopeTreeNode) treeModel.getRoot();
        ScopeTreeNode scopeTreeNode = new ScopeTreeNode();
        scopeTreeNode.setUserObject(scope);
        rootTreeNode.add(scopeTreeNode);

        // add scope
        scopeList.add(scope);

        // reload tree node
        reloadTreeNode(scopeTreeNode);
 
        return scopeTreeNode;
    }

    private ScopeTreeNode addChartTreeNode() {
        // initialize chart
        Chart chart = new Chart();

        // add chart tree node
        ScopeTreeNode chartTreeNode = addChartTreeNode(chart);

        // load chart tree node properties
        loadChartProperties(chartTreeNode);
        
        return chartTreeNode;
    }

    private ScopeTreeNode addChartTreeNode(Chart chart) {
        // add chart node
        ScopeTreeNode scopeTreeNode = getScopeTreeNode();
        ScopeTreeNode chartTreeNode = new ScopeTreeNode();
        chartTreeNode.setUserObject(chart);
        scopeTreeNode.add(chartTreeNode);

        // add chart
        Scope scope = (Scope) scopeTreeNode.getUserObject();
        scope.addChart(chart);

        // reload tree node
        reloadTreeNode(chartTreeNode);

        return chartTreeNode;
    }

    private ScopeTreeNode addAxisTreeNode() {
        // initialize axis
        Axis axis = new Axis();
        axis.setAxisColor(Utilities.getRandomTableColor());

        // add axis tree node
        ScopeTreeNode axisTreeNode =  addAxisTreeNode(axis);
        
        // reload axis tree node properties
        loadAxisProperties(axisTreeNode);
        
        return axisTreeNode;
    }

    private ScopeTreeNode addAxisTreeNode(Axis axis) {
        // add axis node
        ScopeTreeNode chartTreeNode = getChartTreeNode();
        ScopeTreeNode axisTreeNode = new ScopeTreeNode();
        axisTreeNode.setUserObject(axis);
        chartTreeNode.add(axisTreeNode);

        // add axis
        Chart chart = (Chart) chartTreeNode.getUserObject();
        chart.addAxis(axis);

        // reload tree node
        reloadTreeNode(axisTreeNode);
 
        return axisTreeNode;
    }

    private ScopeTreeNode addChannelTreeNode() {
        // initialize channel
        Channel channel = new Channel();
        Color color = Utilities.getRandomTableColor();
        channel.setLineColor(color);
        channel.setPlotColor(color);

        // add channel tree node
        ScopeTreeNode channelTreeNode =  addChannelTreeNode(channel);
        
        // reload channel tree node properties
        loadChannelProperties(channelTreeNode);
        
        return channelTreeNode;
    }

    private ScopeTreeNode addChannelTreeNode(Channel channel) {
        // add channel node
        ScopeTreeNode axisTreeNode = getAxisTreeNode();
        ScopeTreeNode channelTreeNode = new ScopeTreeNode();
        channelTreeNode.setUserObject(channel);
        axisTreeNode.add(channelTreeNode);

        // add axis
        Axis axis = (Axis) axisTreeNode.getUserObject();
        axis.addChannel(channel);

        // reload tree node
        reloadTreeNode(channelTreeNode);

        return channelTreeNode;
    }

    private ScopeTreeNode addTriggerGroupTreeNode() {
        // initialize trigger group
        TriggerGroup triggerGroup = new TriggerGroup();
        
        // add trigger group tree node
        ScopeTreeNode triggerGroupTreeNode =  addTriggerGroupTreeNode(triggerGroup);
        
        // load trigger group tree node properties
        loadTriggerGroupProperties(triggerGroupTreeNode);
        
        return triggerGroupTreeNode;
    }

    private ScopeTreeNode addTriggerGroupTreeNode(TriggerGroup triggerGroup) {
        // add trigger group node
        ScopeTreeNode scopeTreeNode = getScopeTreeNode();
        ScopeTreeNode triggerGroupTreeNode = new ScopeTreeNode();
        triggerGroupTreeNode.setUserObject(triggerGroup);
        scopeTreeNode.insert(triggerGroupTreeNode, 0);

        // add trigger group
        Scope scope = (Scope) scopeTreeNode.getUserObject();
        scope.getTriggerGroupList().add(triggerGroup);

        // reload tree node
        reloadTreeNode(triggerGroupTreeNode);

        return triggerGroupTreeNode;
    }

    private ScopeTreeNode addTriggerChannelTreeNode() {
        // get selected tree node
        ScopeTreeNode selectedTreeNode = (ScopeTreeNode) browseTree.getLastSelectedPathComponent();

        if (selectedTreeNode != null) {
            Object selectedUserObject = selectedTreeNode.getUserObject();

            // is channel
            if (selectedUserObject instanceof Channel) {
                Channel channel = (Channel) selectedUserObject;
                TreePath treePath = browseTree.getSelectionPath();
                Object[] objectArray = (Object[]) treePath.getPath();

                // lookup for scope
                for (Object object : objectArray) {
                    ScopeTreeNode treeNode = (ScopeTreeNode) object;
                    Object userObject = treeNode.getUserObject();

                    // get scope of channel
                    if (userObject instanceof Scope) {
                        Scope scope = (Scope) userObject;
                        ScopeTreeNode triggerGroupTreeNode = new ScopeTreeNode();

                        for (int i = 0; i < treeNode.getChildCount(); i++) {
                            ScopeTreeNode childTreeNode = (ScopeTreeNode) treeNode.getChildAt(i);
                            Object childUserObject = childTreeNode.getUserObject();

                            if (childUserObject instanceof TriggerGroup) {
                                // use existing trigger group tree node
                                triggerGroupTreeNode = childTreeNode;
                                break;
                            }
                        }

                        if (triggerGroupTreeNode.getUserObject() == null) {
                            // initialize trigger group
                            TriggerGroup triggerGroup = new TriggerGroup();

                            // add trigger group node
                            triggerGroupTreeNode.setUserObject(triggerGroup);
                            treeNode.insert(triggerGroupTreeNode, 0);

                            // add trigger group
                            scope.addTriggerGroup(triggerGroup);
                        }

                        // initialize trigger channel
                        TriggerChannel triggerChannel = new TriggerChannel();
                        triggerChannel.setChannel(channel);

                        // add trigger channel node
                        ScopeTreeNode triggerChannelTreeNode = new ScopeTreeNode();
                        triggerChannelTreeNode.setUserObject(triggerChannel);
                        triggerGroupTreeNode.add(triggerChannelTreeNode);
                        
                        // reload tree node
                        reloadTreeNode(triggerChannelTreeNode);
                        
                        // load trigger channel properties
                        loadTriggerChannelProperties(triggerChannelTreeNode);

                        // add trigger channel to trigger group
                        TriggerGroup triggerGroup = scope.getTriggerGroupList().get(0);
                        triggerGroup.addTriggerChannel(triggerChannel);

                        return triggerChannelTreeNode;
                    }
                }
            }
        }

        return null;
    }

    private ScopeTreeNode addTriggerChannelTreeNode(TriggerChannel triggerChannel) {
        // add trigger channel node
        ScopeTreeNode triggerGroupTreeNode = getTriggerGroupTreeNode();
        ScopeTreeNode triggerChannelTreeNode = new ScopeTreeNode();
        triggerChannelTreeNode.setUserObject(triggerChannel);
        triggerGroupTreeNode.add(triggerChannelTreeNode);

        // add trigger channel
        TriggerGroup triggerGroup = (TriggerGroup) triggerGroupTreeNode.getUserObject();
        triggerGroup.addTriggerChannel(triggerChannel);

        // reload tree node
        reloadTreeNode(triggerChannelTreeNode);

        return triggerChannelTreeNode;
    }

}
