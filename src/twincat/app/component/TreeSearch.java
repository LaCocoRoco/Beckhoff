package twincat.app.component;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxEditor;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.TreePath;

import twincat.Resources;
import twincat.TwincatLogger;
import twincat.Utilities;
import twincat.ads.constant.DataType;
import twincat.ads.container.RouteSymbolData;
import twincat.ads.container.Symbol;
import twincat.ads.worker.RouteSymbolLoader;
import twincat.ads.worker.SymbolLoader;
import twincat.app.constant.Filter;
import twincat.app.container.SymbolNode;
import twincat.app.container.SymbolTreeModel;
import twincat.app.container.SymbolTreeNode;
import twincat.app.container.SymbolTreeRenderer;

public class TreeSearch extends JPanel {
    /***********************************/
    /***** local constant variable *****/
    /***********************************/

    private static final long serialVersionUID = 1L;

    private static enum Search { LOADING, TREE };
    
    /***********************************/
    /********* local variable **********/
    /***********************************/
 
    private JTree searchTree = new JTree();

    private SymbolTreeModel searchTreeModel = new SymbolTreeModel();

    /***********************************/
    /******* local final variable ******/
    /***********************************/
    
    private final PanelTree panelTree;

    private final JPanel searchPanel = new JPanel();
 
    private final JLabel loadingData = new JLabel();

    private final JTextField searchTextField = new JTextField();

    private final SymbolTreeModel browseTreeModel = new SymbolTreeModel();

    private final JComboBox<String> routeComboBox = new JComboBox<String>();

    private final JComboBox<String> portComboBox = new JComboBox<String>();

    private final List<SymbolNode> searchSymbolNodeList = new ArrayList<SymbolNode>();

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    private final RouteSymbolLoader routeSymbolLoader = new RouteSymbolLoader();

    private final Logger logger = TwincatLogger.getLogger();

    /***********************************/
    /******* predefined variable *******/
    /***********************************/

    private final MouseAdapter acquisitionTreeMouseAdapter = new MouseAdapter() {
        public void mousePressed(MouseEvent mouseEvent) {
            if (mouseEvent.getClickCount() == 2) {
                int x = mouseEvent.getX();
                int y = mouseEvent.getY();

                TreePath treePath = searchTree.getPathForLocation(x, y);

                if (treePath != null) {
                    SymbolTreeNode symbolTreeNode = (SymbolTreeNode) treePath.getLastPathComponent();
                    Object userObject = symbolTreeNode.getUserObject();

                    // handle symbol nodes
                    if (userObject instanceof SymbolNode) {
                        SymbolNode symbolNodeParent = (SymbolNode) userObject;
                        Symbol selectedSymbol = symbolNodeParent.getSymbol();

                        if (selectedSymbol.getDataType().equals(DataType.BIGTYPE)) {
                            SymbolLoader symbolLoader = symbolNodeParent.getSymbolLoader();

                            List<Symbol> symbolList = symbolLoader.getSymbolList(selectedSymbol);
                            for (Symbol symbol : symbolList) {
                                if (searchTree.getModel().equals(browseTreeModel)) {
                                    // add symbol node to browse tree
                                    SymbolNode symbolNodeChild = new SymbolNode(symbol, symbolLoader, false);
                                    symbolTreeNode.addSymbolNodeSplitName(symbolNodeParent, symbolNodeChild);
                                } else {
                                    // add symbol node to search tree
                                    SymbolNode symbolNodeChild = new SymbolNode(symbol, symbolLoader, true);
                                    symbolTreeNode.addSymbolNodeFullName(symbolNodeParent, symbolNodeChild);
                                    searchSymbolNodeList.add(symbolNodeChild);
                                }
                            }

                            // snapshot of symbol tree node path
                            TreePath symbolTreePath = new TreePath(symbolTreeNode.getPath());

                            // update search tree model
                            if (searchTree.getModel().equals(searchTreeModel)) {
                                symbolTreeNode.removeFromParent();
                                searchTreeModel.reload();
                            }

                            // set view to model
                            searchTree.setSelectionPath(symbolTreePath);
                        } else {
                            logger.info(selectedSymbol.getSymbolName());
                        }
                    }
                }
            }
        }
    };

    private final BasicTreeUI acquisitionTreeBasicUI = new BasicTreeUI() {
        @Override
        protected boolean shouldPaintExpandControl(TreePath p, int r, boolean iE, boolean hBE, boolean iL) {
            return false;
        }
    };

    private final ItemListener comboBoxItemListener = new ItemListener() {
        private ScheduledFuture<?> schedule = null;

        private final Runnable task = new Runnable() {
            public void run() {
                updateRouteComboBox();
                updateTreeModel();
            }
        };

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Utilities.stopSchedule(schedule);
                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
                schedule = scheduler.schedule(task, 0, TimeUnit.MILLISECONDS);
            }
        }
    };

    private final BasicComboBoxUI routeComboBoxBasicUI = new BasicComboBoxUI() {
        protected JButton createArrowButton() {
            return new JButton() {
                private static final long serialVersionUID = 1L;

                public int getWidth() {
                    return 0;
                }
            };
        }
    };

    private final DefaultListCellRenderer routeComboBoxDefaultListCellRenderer = new DefaultListCellRenderer() {
        private static final long serialVersionUID = 1L;

        @Override
        public Component getListCellRendererComponent(JList<?> l, Object v, int i, boolean is, boolean chF) {
            JLabel lbl = (JLabel) super.getListCellRendererComponent(l, v, i, is, chF);
            lbl.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
            return lbl;
        }
    };

    private final BasicComboBoxUI portComboBoxBasicUI = new BasicComboBoxUI() {
        protected JButton createArrowButton() {
            return new JButton() {
                private static final long serialVersionUID = 1L;

                public int getWidth() {
                    return 0;
                }
            };
        }
    };

    private final DefaultListCellRenderer portComboBoxDefaultListCellRenderer = new DefaultListCellRenderer() {
        private static final long serialVersionUID = 1L;

        @Override
        public Component getListCellRendererComponent(JList<?> l, Object v, int i, boolean is, boolean chF) {
            JLabel lbl = (JLabel) super.getListCellRendererComponent(l, v, i, is, chF);
            lbl.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
            return lbl;
        }
    };

    private final FocusListener searchTextFieldFocusListener = new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {
            String tempString = searchTextField.getText();
            if (tempString.equals(languageBundle.getString(Resources.TEXT_SEARCH_HINT))) {
                searchTextField.setText("");
                searchTextField.setForeground(Color.BLACK);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            String tempString = searchTextField.getText();
            if (tempString.equals("")) {
                searchTextField.setForeground(Color.GRAY);
                searchTextField.setText(languageBundle.getString(Resources.TEXT_SEARCH_HINT));
            }
        }
    };

    private final DocumentListener searchTextDocumentListener = new DocumentListener() {
        private static final int DELAY_TIME = 300;

        private ScheduledFuture<?> schedule = null;

        private final Runnable task = new Runnable() {
            public void run() {
                updateSearchTreeModel();
            }
        };

        private void delayUpdateSearchTreeModel() {
            Utilities.stopSchedule(schedule);
            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            schedule = scheduler.schedule(task, DELAY_TIME, TimeUnit.MILLISECONDS);

        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            delayUpdateSearchTreeModel();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            delayUpdateSearchTreeModel();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            delayUpdateSearchTreeModel();
        }
    };

    private final ActionListener applyButtonActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            panelTree.getPanelControl().displayBrowser();
        }
    };

    private final SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
        @Override
        protected Void doInBackground() throws Exception {
            panelSearchDisable();
            panelSearchBuild();
            panelSearchEnable();
            return null;
        }
    };

    /***********************************/
    /*********** constructor ***********/
    /***********************************/

    public TreeSearch(PanelTree panelTree) {
        this.panelTree = panelTree;
        
        searchTree.setCellRenderer(new SymbolTreeRenderer());
        searchTree.setBorder(BorderFactory.createEmptyBorder(5, -5, 0, 0));
        searchTree.setRootVisible(false);
        searchTree.setScrollsOnExpand(false);
        searchTree.setShowsRootHandles(true);
        searchTree.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_NORMAL));
        searchTree.setModel(new SymbolTreeModel());
        searchTree.addMouseListener(acquisitionTreeMouseAdapter);
        searchTree.setUI(acquisitionTreeBasicUI);

        JScrollPane treePanel = new JScrollPane();
        treePanel.getVerticalScrollBar().setPreferredSize(new Dimension(Resources.DEFAULT_SCROLLBAR_WIDTH, 0));
        treePanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        treePanel.setBorder(BorderFactory.createEmptyBorder());
        treePanel.setViewportView(searchTree);

        Border routeBorderInside = BorderFactory.createLoweredBevelBorder();
        Border routeBorderOutside = BorderFactory.createEmptyBorder(0, 0, 0, 1);
        CompoundBorder routeCompoundBorder = new CompoundBorder(routeBorderOutside, routeBorderInside);

        routeComboBox.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_NORMAL));
        routeComboBox.setBorder(routeCompoundBorder);
        routeComboBox.setUI(routeComboBoxBasicUI);
        routeComboBox.setRenderer(routeComboBoxDefaultListCellRenderer);
        routeComboBox.addItem(languageBundle.getString(Resources.TEXT_SEARCH_LOADING));

        portComboBox.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_NORMAL));
        portComboBox.setBorder(BorderFactory.createLoweredBevelBorder());
        portComboBox.setUI(portComboBoxBasicUI);
        portComboBox.setRenderer(portComboBoxDefaultListCellRenderer);
        portComboBox.addItem(languageBundle.getString(Resources.TEXT_SEARCH_LOADING));

        JToolBar routeToolBar = new JToolBar();
        routeToolBar.setFloatable(false);
        routeToolBar.setRollover(false);
        routeToolBar.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        routeToolBar.setLayout(new GridLayout(0, 2));
        routeToolBar.add(routeComboBox);
        routeToolBar.add(portComboBox);

        Border searchBorderInside = BorderFactory.createLoweredBevelBorder();
        Border searchBorderOutside = BorderFactory.createEmptyBorder(0, 4, 0, 0);
        CompoundBorder searchCompoundBorder = new CompoundBorder(searchBorderInside, searchBorderOutside);

        searchTextField.setBorder(searchCompoundBorder);
        searchTextField.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_NORMAL));
        searchTextField.setText(languageBundle.getString(Resources.TEXT_SEARCH_HINT));
        searchTextField.setForeground(Color.GRAY);
        searchTextField.addFocusListener(searchTextFieldFocusListener);
        searchTextField.getDocument().addDocumentListener(searchTextDocumentListener);

        JToolBar searchToolBar = new JToolBar();
        searchToolBar.setFloatable(false);
        searchToolBar.setRollover(false);
        searchToolBar.add(searchTextField);

        JPanel acquisitionToolbar = new JPanel();
        acquisitionToolbar.setLayout(new BoxLayout(acquisitionToolbar, BoxLayout.Y_AXIS));
        acquisitionToolbar.add(routeToolBar);
        acquisitionToolbar.add(searchToolBar);

        JButton applyButton = new JButton(languageBundle.getString(Resources.TEXT_SEARCH_APPLY));
        applyButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        applyButton.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_NORMAL));
        applyButton.setFocusable(false);
        applyButton.addActionListener(applyButtonActionListener);

        JToolBar applyToolBar = new JToolBar();
        applyToolBar.setLayout(new BorderLayout());
        applyToolBar.setFloatable(false);
        applyToolBar.setRollover(false);
        applyToolBar.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        applyToolBar.add(applyButton);

        JLabel loadingText = new JLabel();
        loadingText.setText(languageBundle.getString(Resources.TEXT_SEARCH_LOADING));
        loadingText.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_BIG));  
        loadingData.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_SMALL)); 
        
        JPanel loadingBackground = new JPanel();
        loadingBackground.setLayout(new GridBagLayout());
        loadingBackground.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        loadingBackground.setMaximumSize(new Dimension(200, 50));
        loadingBackground.setBackground(Color.WHITE);  

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        loadingBackground.add(loadingText, gridBagConstraints);      
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        loadingBackground.add(loadingData, gridBagConstraints);
 
        JPanel loadingPanel = new JPanel();
        loadingPanel.setLayout(new BoxLayout(loadingPanel, BoxLayout.PAGE_AXIS));
        loadingPanel.add(Box.createVerticalGlue());
        loadingPanel.add(loadingBackground);
        loadingPanel.add(Box.createVerticalGlue());
        
        searchPanel.setLayout(new CardLayout());
        searchPanel.add(loadingPanel, Search.LOADING.toString());
        searchPanel.add(treePanel, Search.TREE.toString());
        
        backgroundTask.execute();

        this.setLayout(new BorderLayout());
        this.add(acquisitionToolbar, BorderLayout.PAGE_START);
        this.add(searchPanel, BorderLayout.CENTER);
        this.add(applyToolBar, BorderLayout.PAGE_END);
        this.setBorder(BorderFactory.createEmptyBorder());
    }

    /***********************************/
    /******** private function *********/
    /***********************************/

    private void setSearchPanelCard(Search card) {
        CardLayout cardLayout = (CardLayout) (searchPanel.getLayout());
        cardLayout.show(searchPanel, card.toString());
    }
    
    private void panelSearchDisable() {
        setSearchPanelCard(Search.LOADING);
        
        portComboBox.setEditable(true);
        ComboBoxEditor portComboBoxEditor = portComboBox.getEditor();
        JTextField portComboBoxEditorTextField = (JTextField) portComboBoxEditor.getEditorComponent();
        portComboBoxEditorTextField.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
        portComboBoxEditorTextField.setDisabledTextColor(Color.GRAY);
        portComboBoxEditorTextField.setOpaque(false);
        portComboBox.setEnabled(false);

        routeComboBox.setEditable(true);
        ComboBoxEditor routeComboBoxEditor = routeComboBox.getEditor();
        JTextField routeComboBoxEditorTextField = (JTextField) routeComboBoxEditor.getEditorComponent();
        routeComboBoxEditorTextField.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
        routeComboBoxEditorTextField.setDisabledTextColor(Color.GRAY);
        routeComboBoxEditorTextField.setOpaque(false);
        routeComboBox.setEnabled(false);

        searchTextField.setDisabledTextColor(Color.GRAY);
        searchTextField.setEnabled(false);
    }

    private void panelSearchEnable() {
        setSearchPanelCard(Search.TREE);
        
        portComboBox.setEditable(false);
        portComboBox.setEnabled(true);

        routeComboBox.setEditable(false);
        routeComboBox.setEnabled(true);

        searchTextField.setEnabled(true);
    }

    private void panelSearchBuild() {
        routeSymbolLoader.addObserver(new Observer() {
            @Override
            public void update(Observable observable, Object object) {
                loadingData.setText(routeSymbolLoader.getLoadingState());
            } 
        });

        routeSymbolLoader.loadRouteSymbolDataList();
        
        SymbolTreeNode rootBrowseTreeNode = (SymbolTreeNode) browseTreeModel.getRoot();
        SymbolTreeNode rootSearchTreeNode = (SymbolTreeNode) searchTreeModel.getRoot();

        for (RouteSymbolData routeSymbolData : routeSymbolLoader.getRouteSymbolDataList()) {
            String route = routeSymbolData.getRoute().getHostName();
            String port = routeSymbolData.getSymbolLoader().getAmsPort().toString();

            loadingData.setText(route + " | " + port);
            
            SymbolTreeNode portBrowseSymbolTreeNode = rootBrowseTreeNode.getNode(route).getNode(port);
            SymbolTreeNode portSearchSymbolTreeNode = rootSearchTreeNode.getNode(route).getNode(port);

            SymbolLoader symbolLoader = routeSymbolData.getSymbolLoader();

            List<Symbol> routeSymbolList = symbolLoader.getSymbolList();
            for (Symbol symbol : routeSymbolList) {
                SymbolNode browseSymbolNode = new SymbolNode(symbol, symbolLoader, false);
                portBrowseSymbolTreeNode.addSymbolNodeSplitName(browseSymbolNode);

                SymbolNode searchSymbolNode = new SymbolNode(symbol, symbolLoader, true);
                portSearchSymbolTreeNode.addSymbolNodeFullName(searchSymbolNode);

                searchSymbolNodeList.add(searchSymbolNode);
            }
        }

        browseTreeModel.setFilterLevel(Filter.NODE);
        searchTreeModel.setFilterLevel(Filter.ALL);

        reloadTreeModelBrowse();

        String allRoutes = languageBundle.getString(Resources.TEXT_SEARCH_ALL_ROUTES);
        String allPorts = languageBundle.getString(Resources.TEXT_SEARCH_ALL_PORTS);

        List<String> routeList = new ArrayList<String>();
        List<String> portList = new ArrayList<String>();

        routeList.add(allRoutes);
        portList.add(allPorts);

        for (RouteSymbolData routeSymbolData : routeSymbolLoader.getRouteSymbolDataList()) {
            String route = routeSymbolData.getRoute().getHostName();
            String port = routeSymbolData.getSymbolLoader().getAmsPort().toString();

            if (!routeList.contains(route)) {
                routeList.add(route);
            }

            if (!portList.contains(port)) {
                portList.add(port);
            }
        }

        routeComboBox.removeAllItems();
        portComboBox.removeAllItems();

        for (String route : routeList) {
            routeComboBox.addItem(route);
        }

        for (String port : portList) {
            portComboBox.addItem(port);
        }

        routeComboBox.addItemListener(comboBoxItemListener);
        portComboBox.addItemListener(comboBoxItemListener);
    }

    private void updateRouteComboBox() {
        String allRoutes = languageBundle.getString(Resources.TEXT_SEARCH_ALL_ROUTES);
        String allPorts = languageBundle.getString(Resources.TEXT_SEARCH_ALL_PORTS);

        Object selectedItemRoute = routeComboBox.getSelectedItem();
        Object selectedItemPort = portComboBox.getSelectedItem();

        String selectedRoute = selectedItemRoute != null ? selectedItemRoute.toString() : allRoutes;
        String selectedPort = selectedItemPort != null ? selectedItemPort.toString() : allPorts;

        List<String> portList = new ArrayList<String>();
        portList.add(allPorts);

        if (selectedRoute.equals(allRoutes)) {
            for (RouteSymbolData routeSymbolData : routeSymbolLoader.getRouteSymbolDataList()) {
                String port = routeSymbolData.getSymbolLoader().getAmsPort().toString();

                if (!portList.contains(port)) {
                    portList.add(port);
                }
            }
        } else {
            for (RouteSymbolData routeSymbolData : routeSymbolLoader.getRouteSymbolDataList()) {
                if (routeSymbolData.getRoute().getHostName().equals(selectedRoute)) {
                    portList.add(routeSymbolData.getSymbolLoader().getAmsPort().toString());
                }
            }
        }

        portComboBox.removeItemListener(comboBoxItemListener);
        portComboBox.removeAllItems();

        for (String port : portList) {
            portComboBox.addItem(port);
        }

        if (!portList.contains(selectedPort)) {
            selectedPort = allPorts;
        }

        portComboBox.setSelectedItem(selectedPort);
        portComboBox.addItemListener(comboBoxItemListener);
    }
    
    private void reloadTreeModelSearch() {
        setSearchPanelCard(Search.LOADING);
        searchTextField.setEnabled(false);  
        loadingData.setText("Reload Tree");
        
        searchTree.setModel(searchTreeModel);
        searchTreeModel.reload();
        
        // expand tree nodes
        for (int i = 0; i < searchTree.getRowCount(); i++) {
            searchTree.expandRow(i);
        }
        
        setSearchPanelCard(Search.TREE);
        searchTextField.setEnabled(true);
        searchTextField.requestFocus();
    }
    
    private void reloadTreeModelBrowse() {
        searchTree.setModel(browseTreeModel);
        browseTreeModel.reload();
        
        // expand tree nodes
        SymbolTreeNode rootSymbolTreeNode = (SymbolTreeNode) searchTree.getModel().getRoot();
        for (int i = 0; i < rootSymbolTreeNode.getChildCount(); i++) {
            SymbolTreeNode routeSymbolTreeNode = (SymbolTreeNode) rootSymbolTreeNode.getChildAt(i);
            for (int x = 0; x < routeSymbolTreeNode.getChildCount(); x++) {
                SymbolTreeNode portSymbolTreeNode = (SymbolTreeNode) routeSymbolTreeNode.getChildAt(x);
                TreePath symbolTreePath = new TreePath(portSymbolTreeNode.getPath());   
                searchTree.setSelectionPath(symbolTreePath);        
            }
        }
    }
    
    private void updateTreeModel() {
        // update tree model route and ports visibility
        updateTreeModelVisibility(browseTreeModel);
        updateTreeModelVisibility(searchTreeModel);
 
        // reload tree models
        if (searchTree.getModel().equals(browseTreeModel)) {
            reloadTreeModelBrowse();
        } else {
            reloadTreeModelSearch();
        }
    }

    private void updateTreeModelVisibility(SymbolTreeModel symbolTreeModel) {
        String allRoutes = languageBundle.getString(Resources.TEXT_SEARCH_ALL_ROUTES);
        String allPorts = languageBundle.getString(Resources.TEXT_SEARCH_ALL_PORTS);

        Object selectedItemRoute = routeComboBox.getSelectedItem();
        Object selectedItemPort = portComboBox.getSelectedItem();

        String selectedRoute = selectedItemRoute != null ? selectedItemRoute.toString() : allRoutes;
        String selectedPort = selectedItemPort != null ? selectedItemPort.toString() : allPorts;

        SymbolTreeNode rootSymbolTreeNode = (SymbolTreeNode) symbolTreeModel.getRoot();

        for (int i = 0; i < rootSymbolTreeNode.getChildCount(); i++) {
            SymbolTreeNode routeSymbolTreeNode = (SymbolTreeNode) rootSymbolTreeNode.getChildAt(i);

            if (selectedRoute != allRoutes && !routeSymbolTreeNode.toString().equals(selectedRoute)) {
                routeSymbolTreeNode.setVisible(false);
            } else {
                routeSymbolTreeNode.setVisible(true);
            }

            if (routeSymbolTreeNode.isVisible()) {
                for (int x = 0; x < routeSymbolTreeNode.getChildCount(); x++) {
                    SymbolTreeNode portTreeNode = (SymbolTreeNode) routeSymbolTreeNode.getChildAt(x);

                    if (selectedPort != allPorts && !portTreeNode.toString().equals(selectedPort)) {
                        portTreeNode.setVisible(false);
                    } else {
                        portTreeNode.setVisible(true);
                    }
                }
            }
        }
    }

    private void updateSearchTreeModel() {
        String inputText = searchTextField.getText();
        String searchHint = languageBundle.getString(Resources.TEXT_SEARCH_HINT);

        if (!inputText.isEmpty() && !inputText.equals(searchHint)) {
            String regex = "(.*)(" + inputText.replace(" ", ".*") + ")(.*)";
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

            for (SymbolNode symbolNode : searchSymbolNodeList) {
                String symbolName = symbolNode.getSymbol().getSymbolName();
                Matcher matcher = pattern.matcher(symbolName);

                if (matcher.matches()) {
                    symbolNode.setVisible(true);
                } else {
                    symbolNode.setVisible(false);
                }
            }

            reloadTreeModelSearch();
        } else {
            reloadTreeModelBrowse();
        }
    }
}
