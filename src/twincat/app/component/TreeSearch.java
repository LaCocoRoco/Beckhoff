package twincat.app.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;

import twincat.ads.worker.RouteSymbolLoader;
import twincat.app.constant.Resources;
import twincat.app.container.LoremIpsum;

public class TreeSearch extends JPanel {
    private static final long serialVersionUID = 1L;

    /*************************/
    /*** local attributes ****/
    /*************************/

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    /*************************/
    /****** constructor ******/
    /*************************/

    public TreeSearch(PanelTree panelTree) {
        JScrollPane searchPanel = new JScrollPane();
        searchPanel.getVerticalScrollBar().setPreferredSize(new Dimension(Resources.DEFAULT_SCROLLBAR_WIDTH, 0));
        searchPanel.setBorder(BorderFactory.createEmptyBorder());
        searchPanel.setViewportView(new LoremIpsum());


        String defaultAmsNetId = " " + languageBundle.getString(Resources.TEXT_SEARCH_ALL_NET_IDS);
        String defaultAmsPort = " " + languageBundle.getString(Resources.TEXT_SEARCH_ALL_PORTS);
        
        String[] amsNetIdList = {defaultAmsNetId};
        String[] amsPortList = {defaultAmsPort};

        JComboBox<String> amsNetIdComboBox = new JComboBox<String>();
        amsNetIdComboBox.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        amsNetIdComboBox.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_NORMAL));
        amsNetIdComboBox.setBorder(BorderFactory.createLoweredBevelBorder());
        amsNetIdComboBox.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                // remove arrow button
                return new JButton() {
                    private static final long serialVersionUID = 1L;

                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // combo box cell height bug fix
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
                amsNetIdComboBox.setModel(model);
                for (String amsNetId : amsNetIdList) {
                    amsNetIdComboBox.addItem(amsNetId);
                }
            }
        });   
        
        JComboBox<String> amsPortComboBox = new JComboBox<String>();
        amsPortComboBox.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_NORMAL));
        amsPortComboBox.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        amsPortComboBox.setBorder(BorderFactory.createLoweredBevelBorder());
        amsPortComboBox.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                // remove arrow button
                return new JButton() {
                    private static final long serialVersionUID = 1L;

                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // combo box cell height bug fix
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
                amsPortComboBox.setModel(model);
                for (String amsPort : amsPortList) {
                    amsPortComboBox.addItem(amsPort);
                }
            }
        }); 

        JToolBar searchTopToolBar = new JToolBar();
        searchTopToolBar.setFloatable(false);
        searchTopToolBar.setRollover(false);
        searchTopToolBar.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        searchTopToolBar.add(amsNetIdComboBox);
        searchTopToolBar.add(Box.createHorizontalStrut(2));
        searchTopToolBar.add(amsPortComboBox);

        

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
                panelTree.getPanelControl().displayBrowser();
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
        this.add(searchPanel, BorderLayout.CENTER);
        this.add(applyToolBar, BorderLayout.PAGE_END);
        this.setBorder(BorderFactory.createEmptyBorder());
        
        
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                buildSearchTree();
            }
        }); 
    }
    
    private void buildSearchTree() {
        List<RouteSymbolLoader> routeSymbolLoaderList = RouteSymbolLoader.getRouteLoaderList();
        
        for (RouteSymbolLoader routeSymbolLoader : routeSymbolLoaderList) {
            
            // TODO : do something

        } 
    }
}
