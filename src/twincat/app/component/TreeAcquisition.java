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
import twincat.ads.container.Symbol;
import twincat.ads.worker.RouteSymbolHandler;
import twincat.ads.worker.SymbolLoader;
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
   
    private final ItemListener routeItemListener;

    private final ItemListener portItemListener;

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

                    // TODO : expand big type
                    // TODO : log none big type
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

        routeComboBox.addItemListener(routeItemListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateRouteComboBox();
                    updateTreeModel();
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
                JLabel lbl = (JLabel) super.getListCellRendererComponent(l, v, i, is, chF);
                lbl.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 0));
                return lbl;
            }
        });

        portComboBox.addItemListener(portItemListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    updateTreeModel();
                }
            }
        });

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
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSearchText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSearchText();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSearchText();
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
        routeSymbolHandlerList.addAll(RouteSymbolHandler.getRouteHandlerList());

        SymbolTreeNode rootBrowseTreeNode = (SymbolTreeNode) browseTreeModel.getRoot();
        SymbolTreeNode rootSearchTreeNode = (SymbolTreeNode) searchTreeModel.getRoot();

        browseTreeModel.setSymbolFilter(false);
        searchTreeModel.setSymbolFilter(false);

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

        browseTreeModel.setSymbolFilter(true);
        searchTreeModel.setSymbolFilter(true);

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

        routeComboBox.removeItemListener(routeItemListener);
        portComboBox.removeItemListener(portItemListener);
        
        for (String route : routeList) {
            routeComboBox.addItem(route);
        }

        for (String port : portList) {
            portComboBox.addItem(port);
        }

        routeComboBox.addItemListener(routeItemListener);
        portComboBox.addItemListener(portItemListener);
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

        portComboBox.removeItemListener(portItemListener);
        portComboBox.removeAllItems();
        
        for (String port : portList) {
            portComboBox.addItem(port);
        }

        if (!portList.contains(selectedPort)) {
            selectedPort = allPorts;
        }

        portComboBox.setSelectedItem(selectedPort);
        portComboBox.addItemListener(portItemListener);
    }

    private void updateTreeModel() {
        String allRoutes = languageBundle.getString(Resources.TEXT_SEARCH_ALL_ROUTES);
        String allPorts = languageBundle.getString(Resources.TEXT_SEARCH_ALL_PORTS);

        Object selectedItemRoute = routeComboBox.getSelectedItem();
        Object selectedItemPort = portComboBox.getSelectedItem();

        String selectedRoute = selectedItemRoute != null ? selectedItemRoute.toString() : allRoutes;
        String selectedPort = selectedItemPort != null ? selectedItemPort.toString() : allPorts;

        SymbolTreeNode rootBrowseTreeNode = (SymbolTreeNode) browseTreeModel.getRoot();

        for (int i = 0; i < rootBrowseTreeNode.getChildCount(); i++) {
            SymbolTreeNode routeTreeNode = (SymbolTreeNode) rootBrowseTreeNode.getChildAt(i);

            if (selectedRoute != allRoutes) {
                if (routeTreeNode.toString().equals(selectedRoute)) {
                    routeTreeNode.setHide(false);
                } else {
                    routeTreeNode.setHide(true);
                }
            } else {
                routeTreeNode.setHide(false);
            }

            if (!routeTreeNode.isHidden()) {
                for (int x = 0; x < routeTreeNode.getChildCount(); x++) {
                    SymbolTreeNode portTreeNode = (SymbolTreeNode) routeTreeNode.getChildAt(x);

                    if (selectedPort != allPorts) {
                        if (portTreeNode.toString().equals(selectedPort)) {
                            portTreeNode.setHide(false);
                        } else {
                            portTreeNode.setHide(true);
                        }

                    } else {
                        portTreeNode.setHide(false);
                    }
                }
            }
        }

        browseTreeModel.reload();
        searchTreeModel.reload();

        acquisitionTree.expandRow(0);
    }
 
    private void updateSearchText() {
        String inputText = searchTextField.getText();
        String searchHint = languageBundle.getString(Resources.TEXT_SEARCH_HINT);
        
        if (!inputText.isEmpty() && !inputText.equals(searchHint))  {
            // set tree model
            if (acquisitionTree.getModel() != searchTreeModel) {
                acquisitionTree.setModel(searchTreeModel);
            }
            
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
            
            // expand tree full level
            for (int i = 0; i < acquisitionTree.getRowCount(); i++) {
                acquisitionTree.expandRow(i);
            }
            
        } else {
            // set tree model
            if (acquisitionTree.getModel() != browseTreeModel) {
                acquisitionTree.setModel(browseTreeModel);
            }
            
            // expand tree top level
            acquisitionTree.expandRow(0);
        }
    }  
}
