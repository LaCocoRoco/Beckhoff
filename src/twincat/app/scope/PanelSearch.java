package twincat.app.scope;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;

import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.AdsSymbolLoader;
import twincat.app.constants.Resources;

public class PanelSearch extends JPanel {
    private static final long serialVersionUID = 1L;

    /*************************/
    /*** local attributes ****/
    /*************************/

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    /*************************/
    /****** constructor ******/
    /*************************/

    public PanelSearch(PanelTree panelTree) {
        JScrollPane searchPanel = new JScrollPane();
        searchPanel.getVerticalScrollBar().setPreferredSize(new Dimension(Resources.DEFAULT_SCROLLBAR_WIDTH, 0));
        searchPanel.setBorder(BorderFactory.createEmptyBorder());
        searchPanel.setViewportView(new LoremIpsum());

        
        
        String[] searchNetIds = {" " + languageBundle.getString(Resources.TEXT_SEARCH_ALL_NET_IDS)};
        String[] searchAmsPort = {" " + languageBundle.getString(Resources.TEXT_SEARCH_ALL_PORTS)};

        JComboBox<String> amsNetIdComboBox = new JComboBox<String>(searchNetIds);
        amsNetIdComboBox.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        amsNetIdComboBox.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_NORMAL));
        amsNetIdComboBox.setBorder(BorderFactory.createLoweredBevelBorder());
        amsNetIdComboBox.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    private static final long serialVersionUID = 1L;

                    public int getWidth() {
                        return 0;
                    }
                };
            }
        });

        JComboBox<String> amsPortComboBox = new JComboBox<String>(searchAmsPort);
        amsPortComboBox.setFont(new Font(Resources.DEFAULT_FONT, Font.BOLD, Resources.DEFAULT_FONT_SIZE_NORMAL));
        amsPortComboBox.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        amsPortComboBox.setBorder(BorderFactory.createLoweredBevelBorder());
        amsPortComboBox.setUI(new BasicComboBoxUI() {
            protected JButton createArrowButton() {
                return new JButton() {
                    private static final long serialVersionUID = 1L;

                    public int getWidth() {
                        return 0;
                    }
                };
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
    }
    
    /*************************/
    /******** private ********/
    /*************************/

    private final List<AdsSymbolLoader> symbolLoaderList = new ArrayList<AdsSymbolLoader>();
    
    private void doSomething() {
        AdsClient ads = new AdsClient();
        
        try {
            // TODO:
            // symbol loader wieder durch ads ersetzen
            // wrapper klasse für loader & netid & name
            // wrapper klasse read remote address
            // wrapper klasse für netid & name (vermutlich enum list)
            
            String localNetId = ads.readAmsNetId();
            String localHostName = ads.readLocalHostName();  
        } catch (AdsException e) {
            e.printStackTrace();
        }
        
 
        
    }
}
