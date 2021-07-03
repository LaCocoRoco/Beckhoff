package twincat.app.scope;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import twincat.app.constants.Resources;

public class PanelSearch extends JPanel {
    private static final long serialVersionUID = 1L;

    /*************************/
    /****** constructor ******/
    /*************************/

    public PanelSearch() {
        JScrollPane searchTree = new JScrollPane();
        searchTree.getVerticalScrollBar().setPreferredSize(new Dimension(Resources.DEFAULT_SCROLLBAR_WIDTH, 0));
        searchTree.setBorder(BorderFactory.createEmptyBorder());
        searchTree.setViewportView(new LoremIpsum());

        String[] searchNetIds = {"LOCAL"};
        String[] searchAmsPort = {"Port"};
        
        JComboBox<String> comboBoxAmsNetIds = new JComboBox<String>(searchNetIds);
        comboBoxAmsNetIds.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        
        JComboBox<String> comboBoxAmsPort = new JComboBox<String>(searchAmsPort);
        comboBoxAmsPort.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        
        JTextField searchTextField = new JTextField();
        searchTextField.setBorder(BorderFactory.createLoweredBevelBorder()); 
        searchTextField.setFont(new Font(Resources.DEFAULT_FONT, Font.PLAIN, Resources.DEFAULT_FONT_SIZE_NORMAL));
        
        JToolBar topToolBar = new JToolBar();
        topToolBar.setFloatable(false);
        topToolBar.setRollover(false);
        topToolBar.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2)); 
        topToolBar.add(comboBoxAmsNetIds);
        topToolBar.add(Box.createHorizontalStrut(2));
        topToolBar.add(comboBoxAmsPort);
        
        JToolBar botToolBar = new JToolBar();
        botToolBar.setFloatable(false);
        botToolBar.setRollover(false);
        botToolBar.add(searchTextField);

        JPanel searchToolbar = new JPanel();
        searchToolbar.setLayout(new GridBagLayout());
        
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridx = 0;
        
        gridBagConstraints.gridy = 0;
        searchToolbar.add(topToolBar, gridBagConstraints);
        
        gridBagConstraints.gridy = 1;
        searchToolbar.add(botToolBar, gridBagConstraints);
        
        this.setLayout(new BorderLayout());
        this.add(searchToolbar, BorderLayout.PAGE_START);
        this.add(searchTree, BorderLayout.CENTER);
        this.setBorder(BorderFactory.createEmptyBorder());
    }
    

    /*************************/
    /******** private ********/
    /*************************/
}
