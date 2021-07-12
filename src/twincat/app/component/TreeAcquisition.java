package twincat.app.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
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
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.OverlayLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
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
import twincat.ads.container.Symbol;
import twincat.ads.worker.RouteSymbolHandler;
import twincat.ads.worker.SymbolLoader;
import twincat.app.constant.Filter;
import twincat.app.container.SymbolNode;
import twincat.app.container.SymbolTreeModel;
import twincat.app.container.SymbolTreeNode;
import twincat.app.container.SymbolTreeRenderer;

public class TreeAcquisition extends JPanel {
    private static final long serialVersionUID = 1L;

    /*************************/
    /*** local attributes ****/
    /*************************/

    private final ItemListener comboBoxItemListener;

    private final JTree acquisitionTree = new JTree();

    private final JTextField searchTextField = new JTextField();

    private final SymbolTreeModel browseTreeModel = new SymbolTreeModel();

    private final SymbolTreeModel searchTreeModel = new SymbolTreeModel();

    private final JComboBox<String> routeComboBox = new JComboBox<String>();

    private final JComboBox<String> portComboBox = new JComboBox<String>();

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    private final List<RouteSymbolHandler> routeSymbolHandlerList = new ArrayList<RouteSymbolHandler>();

    private final List<SymbolNode> searchSymbolNodeList = new ArrayList<SymbolNode>();

    private final Logger logger = TwincatLogger.getLogger();

    /*************************/
    /****** constructor ******/
    /*************************/

    public TreeAcquisition(PanelBrowser panelBrowser) {
        MouseAdapter acquisitionTreeMouseAdapter = new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    int x = mouseEvent.getX();
                    int y = mouseEvent.getY();

                    TreePath treePath = acquisitionTree.getPathForLocation(x, y);

                    if (treePath != null) {
                        // get object information
                        SymbolTreeNode symbolTreeNode = (SymbolTreeNode) treePath.getLastPathComponent();
                        Object userObject = symbolTreeNode.getUserObject();

                        // handle symbol nodes
                        if (userObject instanceof SymbolNode) {
                            SymbolNode symbolNodeParent = (SymbolNode) userObject;
                            Symbol selectedSymbol = symbolNodeParent.getSymbol();

                            if (selectedSymbol.getDataType().equals(DataType.BIGTYPE)) {
                                SymbolLoader symbolLoader = symbolNodeParent.getSymbolLoader();

                                // add new symbol node
                                List<Symbol> symbolList = symbolLoader.getSymbolList(selectedSymbol);
                                for (Symbol symbol : symbolList) {
                                    if (acquisitionTree.getModel().equals(browseTreeModel)) {
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
                                if (acquisitionTree.getModel().equals(searchTreeModel)) {
                                    symbolTreeNode.removeFromParent();
                                    searchTreeModel.reload();
                                }

                                // set view to model
                                acquisitionTree.setSelectionPath(symbolTreePath);
                            } else {
                                logger.info(selectedSymbol.getSymbolName());
                            }
                        }
                    }
                }
            }
        };

        BasicTreeUI acquisitionTreeBasicUI = new BasicTreeUI() {
            @Override
            protected boolean shouldPaintExpandControl(TreePath p, int r, boolean iE, boolean hBE, boolean iL) {
                return false;
            }
        };

        acquisitionTree.setCellRenderer(new SymbolTreeRenderer());
        acquisitionTree.setBorder(BorderFactory.createEmptyBorder(5, -5, 0, 0));
        acquisitionTree.setRootVisible(false);
        acquisitionTree.setScrollsOnExpand(false);
        acquisitionTree.setShowsRootHandles(true);
        acquisitionTree.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_NORMAL));
        acquisitionTree.setModel(new SymbolTreeModel());
        acquisitionTree.addMouseListener(acquisitionTreeMouseAdapter);
        acquisitionTree.setUI(acquisitionTreeBasicUI);

        JScrollPane scrollPanel = new JScrollPane();
        scrollPanel.getVerticalScrollBar().setPreferredSize(new Dimension(Resources.DEFAULT_SCROLLBAR_WIDTH, 0));
        scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());
        scrollPanel.setViewportView(acquisitionTree);

        comboBoxItemListener = new ItemListener() {
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

        BasicComboBoxUI routeComboBoxBasicUI = new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    private static final long serialVersionUID = 1L;

                    public int getWidth() {
                        return 0;
                    }
                };
            }
        };

        DefaultListCellRenderer routeComboBoxDefaultListCellRenderer = new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getListCellRendererComponent(JList<?> l, Object v, int i, boolean is, boolean chF) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(l, v, i, is, chF);

                lbl.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
                return lbl;
            }
        };

        Border routeBorderInside = BorderFactory.createLoweredBevelBorder();
        Border routeBorderOutside = BorderFactory.createEmptyBorder(0, 0, 0, 1);
        CompoundBorder routeCompoundBorder = new CompoundBorder(routeBorderOutside, routeBorderInside);

        routeComboBox.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_NORMAL));
        routeComboBox.addItemListener(comboBoxItemListener);
        routeComboBox.setBorder(routeCompoundBorder);
        routeComboBox.setUI(routeComboBoxBasicUI);
        routeComboBox.setRenderer(routeComboBoxDefaultListCellRenderer);

        BasicComboBoxUI portComboBoxBasicUI = new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    private static final long serialVersionUID = 1L;

                    public int getWidth() {
                        return 0;
                    }
                };
            }
        };

        DefaultListCellRenderer portComboBoxDefaultListCellRenderer = new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getListCellRendererComponent(JList<?> l, Object v, int i, boolean is, boolean chF) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(l, v, i, is, chF);
                lbl.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
                return lbl;
            }
        };

        portComboBox.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_NORMAL));
        portComboBox.setBorder(BorderFactory.createLoweredBevelBorder());
        portComboBox.addItemListener(comboBoxItemListener);
        portComboBox.setUI(portComboBoxBasicUI);
        portComboBox.setRenderer(portComboBoxDefaultListCellRenderer);

        JToolBar routeToolBar = new JToolBar();
        routeToolBar.setFloatable(false);
        routeToolBar.setRollover(false);
        routeToolBar.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        routeToolBar.setLayout(new GridLayout(0, 2));
        routeToolBar.add(routeComboBox);
        routeToolBar.add(portComboBox);

        FocusListener searchTextFieldFocusListener = new FocusListener() {
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

        DocumentListener searchTextDocumentListener = new DocumentListener() {
            private static final int DELAY_TIME = 100;

            private final Runnable task = new Runnable() {
                public void run() {
                    updateSearchTreeModel();
                }
            };

            private ScheduledFuture<?> schedule = null;

            private void delayUpdateSearchTreeModel() {
                Utilities.stopSchedule(schedule);
                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
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

        ActionListener applyButtonActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelBrowser.getPanelControl().displayBrowser();
            }
        };

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

        SwingWorker<Void, Void> backgroundTask = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                updateAcquisition();
                return null;
            }
        };

        backgroundTask.execute();

        
        
        JLabel loadingData = new JLabel("Loading");
        loadingData.setAlignmentX(CENTER_ALIGNMENT);
        loadingData.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_BIG));

        JLabel loadingLabel = new JLabel("Some Data");
        loadingLabel.setAlignmentX(CENTER_ALIGNMENT);
        loadingLabel.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_SMALL));

        JPanel loadingScreen = new JPanel();
        loadingScreen.setLayout(new BoxLayout(loadingScreen, BoxLayout.Y_AXIS));
        loadingScreen.add(Box.createVerticalGlue());
        loadingScreen.add(loadingData);
        loadingScreen.add(loadingLabel);
        loadingScreen.add(Box.createVerticalGlue());

        // TODO : card layout
        
        this.setLayout(new BorderLayout());
        this.add(acquisitionToolbar, BorderLayout.PAGE_START);
        this.add(loadingScreen, BorderLayout.CENTER);
        this.add(applyToolBar, BorderLayout.PAGE_END);
        this.setBorder(BorderFactory.createEmptyBorder());
    }

    /*************************/
    /******** private ********/
    /*************************/

    private void updateAcquisition() {
        // TODO : show loading screen

        routeSymbolHandlerList.addAll(RouteSymbolHandler.getRouteHandlerList());

        SymbolTreeNode rootBrowseTreeNode = (SymbolTreeNode) browseTreeModel.getRoot();
        SymbolTreeNode rootSearchTreeNode = (SymbolTreeNode) searchTreeModel.getRoot();

        browseTreeModel.setFilterLevel(Filter.NONE);
        searchTreeModel.setFilterLevel(Filter.NONE);

        for (RouteSymbolHandler routeSymbolHandler : routeSymbolHandlerList) {
            String route = routeSymbolHandler.getRoute().getHostName();
            String port = routeSymbolHandler.getSymbolLoader().getAmsPort().toString();

            SymbolTreeNode portBrowseSymbolTreeNode = rootBrowseTreeNode.getNode(route).getNode(port);
            SymbolTreeNode portSearchSymbolTreeNode = rootSearchTreeNode.getNode(route).getNode(port);

            SymbolLoader symbolLoader = routeSymbolHandler.getSymbolLoader();

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

        acquisitionTree.setModel(browseTreeModel);
        acquisitionTree.expandRow(0);

        String allRoutes = languageBundle.getString(Resources.TEXT_SEARCH_ALL_ROUTES);
        String allPorts = languageBundle.getString(Resources.TEXT_SEARCH_ALL_PORTS);

        List<String> routeList = new ArrayList<String>();
        List<String> portList = new ArrayList<String>();

        routeList.add(allRoutes);
        portList.add(allPorts);

        for (RouteSymbolHandler routeSymbolHandler : routeSymbolHandlerList) {
            String route = routeSymbolHandler.getRoute().getHostName();
            String port = routeSymbolHandler.getSymbolLoader().getAmsPort().toString();

            if (!routeList.contains(route)) {
                routeList.add(route);
            }

            if (!portList.contains(port)) {
                portList.add(port);
            }
        }

        routeComboBox.removeItemListener(comboBoxItemListener);
        portComboBox.removeItemListener(comboBoxItemListener);

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
            for (RouteSymbolHandler routeSymbolHandler : routeSymbolHandlerList) {
                String port = routeSymbolHandler.getSymbolLoader().getAmsPort().toString();

                if (!portList.contains(port)) {
                    portList.add(port);
                }
            }
        } else {
            for (RouteSymbolHandler routeSymbolLoader : routeSymbolHandlerList) {
                if (routeSymbolLoader.getRoute().getHostName().equals(selectedRoute)) {
                    portList.add(routeSymbolLoader.getSymbolLoader().getAmsPort().toString());
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

    private void updateTreeModel() {
        // update tree model route and ports visibility
        updateTreeModelVisibility(browseTreeModel);
        updateTreeModelVisibility(searchTreeModel);

        // update tree models
        if (acquisitionTree.getModel().equals(browseTreeModel)) {
            // tree model browse reload
            browseTreeModel.reload();
            // tree model browse expand top level
            acquisitionTree.expandRow(0);
        } else {
            // tree model search reload
            searchTreeModel.reload();
            // tree model search expand full
            for (int i = 0; i < acquisitionTree.getRowCount(); i++) {
                acquisitionTree.expandRow(i);
            }
        }
    }

    private void updateTreeModelVisibility(SymbolTreeModel symbolTreeModel) {
        String allRoutes = languageBundle.getString(Resources.TEXT_SEARCH_ALL_ROUTES);
        String allPorts = languageBundle.getString(Resources.TEXT_SEARCH_ALL_PORTS);

        Object selectedItemRoute = routeComboBox.getSelectedItem();
        Object selectedItemPort = portComboBox.getSelectedItem();

        String selectedRoute = selectedItemRoute != null ? selectedItemRoute.toString() : allRoutes;
        String selectedPort = selectedItemPort != null ? selectedItemPort.toString() : allPorts;

        SymbolTreeNode rootBrowseTreeNode = (SymbolTreeNode) symbolTreeModel.getRoot();

        for (int i = 0; i < rootBrowseTreeNode.getChildCount(); i++) {
            SymbolTreeNode routeTreeNode = (SymbolTreeNode) rootBrowseTreeNode.getChildAt(i);

            if (selectedRoute != allRoutes) {
                if (routeTreeNode.toString().equals(selectedRoute)) {
                    routeTreeNode.setVisible(true);
                } else {
                    routeTreeNode.setVisible(false);
                }
            } else {
                routeTreeNode.setVisible(true);
            }

            if (routeTreeNode.isVisible()) {
                for (int x = 0; x < routeTreeNode.getChildCount(); x++) {
                    SymbolTreeNode portTreeNode = (SymbolTreeNode) routeTreeNode.getChildAt(x);

                    if (selectedPort != allPorts) {
                        if (portTreeNode.toString().equals(selectedPort)) {
                            portTreeNode.setVisible(true);
                        } else {
                            portTreeNode.setVisible(false);
                        }

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
            // filter tree model
            Pattern pattern = Pattern.compile("(.*)(" + inputText + ")(.*)", Pattern.CASE_INSENSITIVE);

            for (SymbolNode symbolNode : searchSymbolNodeList) {
                String symbolName = symbolNode.getSymbol().getSymbolName();
                Matcher matcher = pattern.matcher(symbolName);

                if (matcher.matches()) {
                    symbolNode.setVisible(true);
                } else {
                    symbolNode.setVisible(false);
                }
            }

            // reload tree model
            searchTreeModel.reload();

            // set tree model search
            if (acquisitionTree.getModel() != searchTreeModel) {
                acquisitionTree.setModel(searchTreeModel);
            }

            // expand tree model search full
            for (int i = 0; i < acquisitionTree.getRowCount(); i++) {
                acquisitionTree.expandRow(i);
            }
        } else {
            // set tree model browse
            if (acquisitionTree.getModel() != browseTreeModel) {
                acquisitionTree.setModel(browseTreeModel);
                // expand tree model browse top level
                acquisitionTree.expandRow(0);
            }
        }
    }

    private void disableAcquisition() {


        UIManager.put("ComboBox.disabledForeground", Color.GRAY);
        searchTextField.setDisabledTextColor(Color.GRAY);
        
        portComboBox.setEnabled(false);
        routeComboBox.setEnabled(false);
        searchTextField.setEnabled(false);
        acquisitionTree.setEnabled(false);

    }
}
