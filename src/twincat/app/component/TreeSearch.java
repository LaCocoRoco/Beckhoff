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
import java.util.logging.Logger;

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
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.tree.TreePath;

import twincat.Resources;
import twincat.TwincatLogger;
import twincat.ads.constant.DataType;
import twincat.ads.container.Symbol;
import twincat.ads.worker.RouteSymbolHandler;
import twincat.ads.worker.SymbolLoader;
import twincat.app.container.SymbolNode;
import twincat.app.container.SymbolTreeModel;
import twincat.app.container.SymbolTreeNode;
import twincat.app.container.SymbolTreeRenderer;

public class TreeSearch extends JPanel {
    private static final long serialVersionUID = 1L;

    /*************************/
    /*** local attributes ****/
    /*************************/

    private final JTree symbolTree = new JTree();
    
    private final JComboBox<String> routeComboBox = new JComboBox<String>();

    private final JComboBox<String> portComboBox = new JComboBox<String>();

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    private final Logger logger = TwincatLogger.getLogger();

    /*************************/
    /****** constructor ******/
    /*************************/

    public TreeSearch(PanelBrowser panelBrowser) {
        symbolTree.setCellRenderer(new SymbolTreeRenderer());      
        symbolTree.setBorder(BorderFactory.createEmptyBorder(5, -5, 0, 0));
        symbolTree.setRootVisible(false);
        symbolTree.setScrollsOnExpand(false);
        symbolTree.setShowsRootHandles(true);
        symbolTree.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_NORMAL));
        
        symbolTree.setUI(new BasicTreeUI() {
            @Override
            protected boolean shouldPaintExpandControl(TreePath p, int r, boolean iE, boolean hBE, boolean iL) {
                return false; 
            } 
        });
        
        symbolTree.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    int x = mouseEvent.getX();
                    int y = mouseEvent.getY();
                    
                    /*
                    TreePath treePath = symbolTree.getPathForLocation(x, y);
                    SymbolTreeNode symbolTreeNode = (SymbolTreeNode) treePath.getLastPathComponent();
                    Object userObject = symbolTreeNode.getUserObject();
                     
                    if (userObject instanceof SymbolNode) {
                        SymbolNode symbolNode = (SymbolNode) symbolTreeNode.getUserObject();
                        
                        if (symbolNode.getSymbol().getDataType().equals(DataType.BIGTYPE)) {
                            // TODO : add new list | Note : is leaf ?
                            SymbolTreeNode symbolTreeNodePort = symbolTreeNode.getTreeNode(route).getTreeNode(port);
                            SymbolLoader symbolLoader = routeSymbolHandler.getSymbolLoader();

                            List<Symbol> symbolList = symbolLoader.getSymbolList();
                            for (Symbol symbol : symbolList) {
                                SymbolNode symbolNode = new SymbolNode(symbol, symbolLoader);
                                symbolTreeNodePort.addSymbolNode(symbolNode);
                            }
                        }
                        
                        System.out.println(symbolNode.getSymbol().getSymbolName());
                    }

                    //Object[] resourceList = treePath.getPath();
                    //Object test = resourceList[0]; 
                    */
                }
            }
        });
        
        JScrollPane scrollPanel = new JScrollPane();
        scrollPanel.getVerticalScrollBar().setPreferredSize(new Dimension(Resources.DEFAULT_SCROLLBAR_WIDTH, 0));
        scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());
        scrollPanel.setViewportView(symbolTree);

        Border hostNameComboBoxInside = BorderFactory.createLoweredBevelBorder();
        Border hostNameComboBoxOutside = BorderFactory.createEmptyBorder(0, 0, 0, 1);
        CompoundBorder hostNameComboBoxBorder = new CompoundBorder(hostNameComboBoxOutside, hostNameComboBoxInside);

        routeComboBox.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_NORMAL));
        routeComboBox.setBorder(hostNameComboBoxBorder);
        
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
                JLabel lbl = (JLabel) super.getListCellRendererComponent(l, v, i, is,  chF);
                lbl.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
                return lbl;
            }  
        });
       
        routeComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    buildSearchTree();
                }
            }
        });

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
                JLabel lbl = (JLabel) super.getListCellRendererComponent(l, v, i, is,  chF);
                lbl.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
                return lbl;
            }   
        });
        
        portComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    buildSearchTree();
                }
            }
        });

        JToolBar searchTopToolBar = new JToolBar();
        searchTopToolBar.setFloatable(false);
        searchTopToolBar.setRollover(false);
        searchTopToolBar.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        searchTopToolBar.setLayout(new GridLayout(0, 2));
        searchTopToolBar.add(routeComboBox);
        searchTopToolBar.add(portComboBox);

        JTextField searchBotToolbarTextField = new JTextField();
        Border searchTextLowerBorder = BorderFactory.createLoweredBevelBorder();
        Border searchTextEmptyBorder = BorderFactory.createEmptyBorder(0, 4, 0, 0);
        CompoundBorder searchTextBorder = new CompoundBorder(searchTextLowerBorder, searchTextEmptyBorder);
        searchBotToolbarTextField.setBorder(searchTextBorder);
        searchBotToolbarTextField.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_NORMAL));
        searchBotToolbarTextField.setText(languageBundle.getString(Resources.TEXT_SEARCH_HINT));
        searchBotToolbarTextField.setForeground(Color.GRAY);
        searchBotToolbarTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                String tempString = searchBotToolbarTextField.getText();
                if (tempString.equals(languageBundle.getString(Resources.TEXT_SEARCH_HINT))) {
                    searchBotToolbarTextField.setText("");
                    searchBotToolbarTextField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String tempString = searchBotToolbarTextField.getText();
                if (tempString.equals("")) {
                    searchBotToolbarTextField.setForeground(Color.GRAY);
                    searchBotToolbarTextField.setText(languageBundle.getString(Resources.TEXT_SEARCH_HINT));
                }
            }
        });

        JToolBar searchBotToolBar = new JToolBar();
        searchBotToolBar.setFloatable(false);
        searchBotToolBar.setRollover(false);
        searchBotToolBar.add(searchBotToolbarTextField);

        JPanel searchToolbar = new JPanel();
        searchToolbar.setLayout(new BoxLayout(searchToolbar, BoxLayout.Y_AXIS));
        searchToolbar.add(searchTopToolBar);
        searchToolbar.add(searchBotToolBar);

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

        this.setLayout(new BorderLayout());
        this.add(searchToolbar, BorderLayout.PAGE_START);
        this.add(scrollPanel, BorderLayout.CENTER);
        this.add(applyToolBar, BorderLayout.PAGE_END);
        this.setBorder(BorderFactory.createEmptyBorder());

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                routeSymbolHandlerList.addAll(RouteSymbolHandler.getRouteHandlerList());
                buildSearchTree();
            }
        });
    }

    // TODO : background process
    // TODO : rework combo box
    // TODO : filter

    private final List<RouteSymbolHandler> routeSymbolHandlerList = new ArrayList<RouteSymbolHandler>();

    private boolean comboBoxEvent = false;

    private void buildSearchTree() {
        // 1. skip combo box event overload
        if (comboBoxEvent) return;
        comboBoxEvent = true;

        String allRoutes = languageBundle.getString(Resources.TEXT_SEARCH_ALL_ROUTES);
        String allPorts = languageBundle.getString(Resources.TEXT_SEARCH_ALL_PORTS);

        Object selectedItemRoute = routeComboBox.getSelectedItem();
        Object selectedItemPort = portComboBox.getSelectedItem();

        // 2. get selected combo box items
        String selectedRoute = selectedItemRoute != null ? selectedItemRoute.toString() : allRoutes;
        String selectedPort = selectedItemPort != null ? selectedItemPort.toString() : allPorts;

        // 3. get full route and port list
        List<String> routeList = new ArrayList<String>();
        List<String> portList = new ArrayList<String>();

        routeList.add(allRoutes);
        portList.add(allPorts);

        for (RouteSymbolHandler routeSymbolLoader : routeSymbolHandlerList) {
            String route = routeSymbolLoader.getRoute().getHostName();
            String port = routeSymbolLoader.getSymbolLoader().getAmsPort().toString();

            if (!routeList.contains(route)) {
                routeList.add(route);
            }

            if (!portList.contains(port)) {
                portList.add(port);
            }
        }

        // 4. reset combo box items
        routeComboBox.removeAllItems();
        portComboBox.removeAllItems();

        // 5. update combo box route
        for (String route : routeList) {
            routeComboBox.addItem(route);
        }

        // 6. update combo box port
        if (!selectedRoute.equals(allRoutes)) {
            portComboBox.addItem(allPorts);

            for (RouteSymbolHandler routeSymbolLoader : routeSymbolHandlerList) {
                if (routeSymbolLoader.getRoute().getHostName().equals(selectedRoute)) {
                    portComboBox.addItem(routeSymbolLoader.getSymbolLoader().getAmsPort().toString());
                }
            }
        } else {
            for (String amsPort : portList) {
                portComboBox.addItem(amsPort);
            }
        }

        // 7. set selected item
        routeComboBox.setSelectedItem(selectedRoute);
        portComboBox.setSelectedItem(selectedPort);

        // 8. combo box done
        comboBoxEvent = false;

        // 9. build tree
        SymbolTreeNode symbolTreeNode = new SymbolTreeNode();
        SymbolTreeModel symbolTreeModel = new SymbolTreeModel(symbolTreeNode);

        logger.info("Build RouteSymbolHandler Start");
        long startTime = System.currentTimeMillis();
        
        for (RouteSymbolHandler routeSymbolHandler : routeSymbolHandlerList) {
            String route = routeSymbolHandler.getRoute().getHostName();
            String port = routeSymbolHandler.getSymbolLoader().getAmsPort().toString();

            if (selectedRoute != allRoutes) {
                if (!selectedRoute.equals(route)) {
                    continue; // skip route
                }
            }

            if (selectedPort != allPorts) {
                if (!selectedPort.equals(port)) {
                    continue; // skip port
                }
            }

            SymbolTreeNode symbolTreeNodePort = symbolTreeNode.getTreeNode(route).getTreeNode(port);
            SymbolLoader symbolLoader = routeSymbolHandler.getSymbolLoader();

            List<Symbol> symbolList = symbolLoader.getSymbolList();
            for (Symbol symbol : symbolList) {
                SymbolNode symbolNode = new SymbolNode(symbol, symbolLoader);
                symbolTreeNodePort.addSymbolNode(symbolNode);
            }
        }

        logger.info("Build RouteSymbolHandler Stop: " + (System.currentTimeMillis() - startTime));
        
        symbolTree.setModel(symbolTreeModel);
        symbolTree.expandRow(0);    // expand to port level

        // 99. build finished
    }
}
