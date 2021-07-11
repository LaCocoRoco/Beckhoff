package twincat.app.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
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
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
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

    private final JTree acquisitionTree = new JTree();

    private final JTextField searchTextField = new JTextField();

    private final SymbolTreeModel browseTreeModel = new SymbolTreeModel();

    private final SymbolTreeModel searchTreeModel = new SymbolTreeModel();

    private final JComboBox<String> routeComboBox = new JComboBox<String>();

    private final JComboBox<String> portComboBox = new JComboBox<String>();

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    private final List<RouteSymbolHandler> routeSymbolHandlerList = new ArrayList<RouteSymbolHandler>();

    private final List<SymbolNode> searchSymbolNodeList = new ArrayList<SymbolNode>();

    private final ItemListener comboBoxItemListener;

    /*************************/
    /****** constructor ******/
    /*************************/

    public TreeAcquisition(PanelBrowser panelBrowser) {
        acquisitionTree.setCellRenderer(new SymbolTreeRenderer());
        acquisitionTree.setBorder(BorderFactory.createEmptyBorder(5, -5, 0, 0));
        acquisitionTree.setRootVisible(false);
        acquisitionTree.setScrollsOnExpand(false);
        acquisitionTree.setShowsRootHandles(true);
        acquisitionTree.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_NORMAL));
        acquisitionTree.setModel(new SymbolTreeModel());

        acquisitionTree.setUI(new BasicTreeUI() {
            @Override
            protected boolean shouldPaintExpandControl(TreePath p, int r, boolean iE, boolean hBE, boolean iL) {
                return false;
            }
        });

        acquisitionTree.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    int x = mouseEvent.getX();
                    int y = mouseEvent.getY();

                    TreePath treePath = acquisitionTree.getPathForLocation(x, y);
                    
                    if (treePath == null) return;
                    
                    SymbolTreeNode symbolTreeNode = (SymbolTreeNode) treePath.getLastPathComponent();   
                    Object userObject = symbolTreeNode.getUserObject();

                    if (userObject instanceof SymbolNode) {
                        SymbolNode symbolNode = (SymbolNode) userObject;
                        Symbol selectedSymbol = symbolNode.getSymbol();
 
                        if (symbolTreeNode.isLeaf()) {
                            if (selectedSymbol.getDataType().equals(DataType.BIGTYPE)) {
                                SymbolLoader symbolLoader = symbolNode.getSymbolLoader();

                                List<Symbol> symbolList = symbolLoader.getSymbolList(selectedSymbol);
                                for (Symbol symbol : symbolList) {
                                    
                                    if (acquisitionTree.getModel().equals(browseTreeModel)) {
                                        SymbolNode browseSymbolNode = new SymbolNode(symbol, symbolLoader, false);

                                        // TODO : remove path before
                                        
                                        symbolTreeNode.addSymbolNodeAndSplit(browseSymbolNode);
                                        
                                    } else {
                                        SymbolNode searchSymbolNode = new SymbolNode(symbol, symbolLoader, true);
                                        symbolTreeNode.addSymbolNode(searchSymbolNode);
                                        
                                        searchSymbolNodeList.add(searchSymbolNode);
                                    }
                                }
                                
                                // expand children of symbol tree node
                                for (int i = 0; i < symbolTreeNode.getChildCount(); i++) {
                                    SymbolTreeNode symbolTreeNodeChild = (SymbolTreeNode) symbolTreeNode.getChildAt(i);
                                    TreePath symbolTreeNodeChildPath = new TreePath(symbolTreeNodeChild.getPath());
                                    acquisitionTree.setSelectionPath(symbolTreeNodeChildPath);
                                }
                            } else {
                                System.out.println(selectedSymbol.getSymbolName());
                            }
                        }
                    }
                }
            }
        });

        JScrollPane scrollPanel = new JScrollPane();
        scrollPanel.getVerticalScrollBar().setPreferredSize(new Dimension(Resources.DEFAULT_SCROLLBAR_WIDTH, 0));
        scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());
        scrollPanel.setViewportView(acquisitionTree);

        Border routeBorderInside = BorderFactory.createLoweredBevelBorder();
        Border routeBorderOutside = BorderFactory.createEmptyBorder(0, 0, 0, 1);
        CompoundBorder routeCompoundBorder = new CompoundBorder(routeBorderOutside, routeBorderInside);

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

        routeComboBox.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_NORMAL));
        routeComboBox.setBorder(routeCompoundBorder);

        routeComboBox.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    private static final long serialVersionUID = 1L;

                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        routeComboBox.setRenderer(new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getListCellRendererComponent(JList<?> l, Object v, int i, boolean is, boolean chF) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(l, v, i, is, chF);
                lbl.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
                return lbl;
            }
        });

        routeComboBox.addItemListener(comboBoxItemListener);

        portComboBox.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_NORMAL));
        portComboBox.setBorder(BorderFactory.createLoweredBevelBorder());

        portComboBox.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    private static final long serialVersionUID = 1L;

                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        portComboBox.setRenderer(new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getListCellRendererComponent(JList<?> l, Object v, int i, boolean is, boolean chF) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(l, v, i, is, chF);
                lbl.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
                return lbl;
            }
        });

        portComboBox.addItemListener(comboBoxItemListener);

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

        searchTextField.addFocusListener(new FocusListener() {
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
        });

        searchTextField.getDocument().addDocumentListener(new DocumentListener() {
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
        });

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
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelBrowser.getPanelControl().displayBrowser();
            }
        });

        JToolBar applyToolBar = new JToolBar();
        applyToolBar.setLayout(new BorderLayout());
        applyToolBar.setFloatable(false);
        applyToolBar.setRollover(false);
        applyToolBar.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        applyToolBar.add(applyButton);

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                updateAcquisition();
                return null;
            }
        }.execute();

        this.setLayout(new BorderLayout());
        this.add(acquisitionToolbar, BorderLayout.PAGE_START);
        this.add(scrollPanel, BorderLayout.CENTER);
        this.add(applyToolBar, BorderLayout.PAGE_END);
        this.setBorder(BorderFactory.createEmptyBorder());
    }

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
                portBrowseSymbolTreeNode.addSymbolNodeAndSplit(browseSymbolNode);

                SymbolNode searchSymbolNode = new SymbolNode(symbol, symbolLoader, true);
                portSearchSymbolTreeNode.addSymbolNode(searchSymbolNode);

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

    // TODO : probably remove search tree
    
    private void updateSearchTreeModel() {
        String inputText = searchTextField.getText();
        String searchHint = languageBundle.getString(Resources.TEXT_SEARCH_HINT);

        if (!inputText.isEmpty() && !inputText.equals(searchHint)) {
            // hide tree while filtering
            acquisitionTree.setVisible(false);

            // TODO : show loading screen

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

        // TODO : hide loading screen
        // show tree after filtering
        acquisitionTree.setVisible(true);
    }
}
