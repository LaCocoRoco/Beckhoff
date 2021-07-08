package twincat.app.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import twincat.Resources;
import twincat.ads.container.Symbol;
import twincat.ads.worker.RouteSymbolHandler;
import twincat.ads.worker.SymbolLoader;
import twincat.app.container.LoremIpsum;
import twincat.app.container.SymbolNode;

public class TreeSearch extends JPanel {
    private static final long serialVersionUID = 1L;

    /*************************/
    /*** local attributes ****/
    /*************************/
    
    private final JTree tree = new JTree();

    private final JComboBox<String> routeComboBox = new JComboBox<String>();

    private final JComboBox<String> portComboBox = new JComboBox<String>();

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    /*************************/
    /****** constructor ******/
    /*************************/

    

    public TreeSearch(PanelBrowser panelBrowser) {
        JScrollPane scrollPanel = new JScrollPane();
        scrollPanel.getVerticalScrollBar().setPreferredSize(new Dimension(Resources.DEFAULT_SCROLLBAR_WIDTH, 0));
        scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());
        scrollPanel.setViewportView(tree);

        Border hostNameComboBoxInside = BorderFactory.createLoweredBevelBorder();
        Border hostNameComboBoxOutside = BorderFactory.createEmptyBorder(0, 0, 0, 1);
        CompoundBorder hostNameComboBoxBorder = new CompoundBorder(hostNameComboBoxOutside, hostNameComboBoxInside);

        routeComboBox.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
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

        routeComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    buildSearchTree();
                }
            }
        });

        portComboBox.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_NORMAL));
        portComboBox.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
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
        searchBotToolbarTextField
                .setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_NORMAL));
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
                // load route symbol handler list
                routeSymbolHandlerList.addAll(RouteSymbolHandler.getRouteHandlerList());
                buildSearchTree();
            }
        });
    }

    private final List<RouteSymbolHandler> routeSymbolHandlerList = new ArrayList<RouteSymbolHandler>();

    private boolean comboBoxEvent = false;

    /*
     * 1. problem : combo box can be reselected 2. problem : search input can change
     * it self all the time 3. problem : on selecting element load new data type
     * information
     */
    private void buildSearchTree() {
        // 1. skip combo box event overload | TODO : fix
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

        // 8. build tree
        List<JTree> routeTreeList = new ArrayList<JTree>();
        for (RouteSymbolHandler routeSymbolHandler : routeSymbolHandlerList) {
            String route = routeSymbolHandler.getRoute().getHostName();
            String port = routeSymbolHandler.getSymbolLoader().getAmsPort().toString();

            if (selectedRoute != allRoutes) {
                if (!selectedRoute.equals(route)) {
                    // skip route
                    continue;
                }
            }

            if (selectedPort != allPorts) {
                if (!selectedPort.equals(port)) {
                    // skip port
                    continue;
                }
            }

            // Route JTree: PLC01-RT1 | Port Tree Node: = NC & Runtime
            // TODO : get / make route and port
            // JTree.setRootVisible() 
            DefaultMutableTreeNode routeTreeNode = getRouteTreeNode(routeTreeList, route, port);

            SymbolLoader symbolLoader = routeSymbolHandler.getSymbolLoader();

            List<Symbol> symbolList = symbolLoader.getSymbolList();

            
            for (Symbol symbol : symbolList) {
                SymbolNode symbolNode = new SymbolNode(symbol, symbolLoader);
                addSymbolNodeToTreeNode(routeTreeNode, symbolNode);

            }
        }
        
 
        // 99. build finished
    }

    private void addSymbolNodeToTreeNode(DefaultMutableTreeNode treeNode, SymbolNode symbolNode) {
        String[] symbolArray = symbolNode.getSymbol().getSymbolName().split("\\.");

        if (symbolArray[0].isEmpty()) symbolArray[0] = "Global";

        nextsymbol:
        for (int i = 0; i < symbolArray.length; i++) {
            String symbolName = symbolArray[i];

            if (treeNode.getChildCount() != 0) {
                // compare with all child nodes
                for (int x = 0; x < treeNode.getChildCount(); x++) {
                    DefaultMutableTreeNode treeChildNode = (DefaultMutableTreeNode) treeNode.getChildAt(x);
                    if (treeChildNode.toString().equals(symbolName)) {
                        // pass reference to child node
                        treeNode = treeChildNode;
                        continue nextsymbol;
                    }
                }
            }

            if (i == symbolArray.length - 1) {
                // this should be using the "to string" method?
                DefaultMutableTreeNode symbolTreeNode = new DefaultMutableTreeNode(symbolNode);
                treeNode.add(symbolTreeNode);
            } else {
                // add symbol as node and pass reference
                DefaultMutableTreeNode symbolTreeNode = new DefaultMutableTreeNode(symbolName);
                treeNode.add(symbolTreeNode);
                treeNode = symbolTreeNode;
            }
        }
    }
 
    // TODO 
    private DefaultMutableTreeNode getRouteTreeNode(JTree treeList, String route, String port) {
        for (JTree tree : treeList) {
            DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
            DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) treeModel.getRoot();

            if (treeNode.toString().equals(route)) {
                return treeNode;
            }
        }

        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(route);
        JTree tree = new JTree(treeNode);
        tree.setBorder(BorderFactory.createEmptyBorder());
        tree.setScrollsOnExpand(false);
        treeList.add(tree);

        return treeNode;
    }
}
