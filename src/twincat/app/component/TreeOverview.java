package twincat.app.component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import twincat.LoremIpsum;
import twincat.Resources;
import twincat.Utilities;

public class TreeOverview extends JPanel {
    private static final long serialVersionUID = 1L;

    /*************************/
    /*** local attributes ****/
    /*************************/

    private final ResourceBundle languageBundle = ResourceBundle.getBundle(Resources.PATH_LANGUAGE);

    /*************************/
    /****** constructor ******/
    /*************************/

    public TreeOverview(PanelBrowser panelBrowser) {
        JScrollPane browserPanel = new JScrollPane();
        browserPanel.getVerticalScrollBar().setPreferredSize(new Dimension(Resources.DEFAULT_SCROLLBAR_WIDTH, 0));
        browserPanel.setBorder(BorderFactory.createEmptyBorder());
        browserPanel.setViewportView(new LoremIpsum());

        // TODO : tree build
        // build from scope list

        // TODO : tree navigation
        // on select (scope example):
        // set local reference (delete & add)
        // show properties panel

        JButton browserButtonAddScope = new JButton();
        browserButtonAddScope.setToolTipText(languageBundle.getString(Resources.TEXT_BROWSER_ADD_SCOPE));
        browserButtonAddScope.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_NAVIGATE_SCOPE)));
        browserButtonAddScope.setFocusable(false);
        browserButtonAddScope.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // add scope to scope list (reference)
                // add scope to tree node (reference)
            }
        });

        JButton browserButtonAddChart = new JButton();
        browserButtonAddChart.setToolTipText(languageBundle.getString(Resources.TEXT_BROWSER_ADD_CHART));
        browserButtonAddChart.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_NAVIGATE_CHART)));
        browserButtonAddChart.setFocusable(false);
        browserButtonAddChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // add chart to to scope list (reference)
                // refresh tree view
            }
        });

        JButton browserButtonAddAxis = new JButton();
        browserButtonAddAxis.setToolTipText(languageBundle.getString(Resources.TEXT_BROWSER_ADD_AXIS));
        browserButtonAddAxis.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_NAVIGATE_AXIS)));
        browserButtonAddAxis.setFocusable(false);
        browserButtonAddAxis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // add axis to to scope list (reference)
                // refresh tree view
            }
        });

        JButton browserButtonAddChannel = new JButton();
        browserButtonAddChannel.setToolTipText(languageBundle.getString(Resources.TEXT_BROWSER_ADD_CHANNEL));
        browserButtonAddChannel.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_NAVIGATE_CHANNEL)));
        browserButtonAddChannel.setFocusable(false);
        browserButtonAddChannel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // add channel to scope list (reference)
                // refresh tree view
            }
        });

        JButton browserButtonSearch = new JButton();
        browserButtonSearch.setToolTipText(languageBundle.getString(Resources.TEXT_BROWSER_SEARCH));
        browserButtonSearch.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_NAVIGATE_SEARCH)));
        browserButtonSearch.setFocusable(false);
        browserButtonSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelBrowser.getPanelControl().displaySearch();
            }
        });

        JButton browserButtonDelete = new JButton();
        browserButtonDelete.setToolTipText(languageBundle.getString(Resources.TEXT_BROWSER_DELETE));
        browserButtonDelete.setIcon(new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_NAVIGATE_DELETE)));
        browserButtonDelete.setFocusable(false);
        browserButtonSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // delete selected element from scope
                // refresh tree view
            }
        });

        JToolBar browserToolBar = new JToolBar();
        browserToolBar.setFloatable(false);
        browserToolBar.setRollover(false);
        browserToolBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        browserToolBar.add(browserButtonAddScope);
        browserToolBar.addSeparator(new Dimension(5, 0));
        browserToolBar.add(browserButtonAddChart);
        browserToolBar.addSeparator(new Dimension(5, 0));
        browserToolBar.add(browserButtonAddAxis);
        browserToolBar.addSeparator(new Dimension(5, 0));
        browserToolBar.add(browserButtonAddChannel);
        browserToolBar.addSeparator(new Dimension(30, 0));
        browserToolBar.add(browserButtonSearch);
        browserToolBar.addSeparator(new Dimension(30, 0));
        browserToolBar.add(browserButtonDelete);

        this.setLayout(new BorderLayout());
        this.add(browserPanel, BorderLayout.CENTER);
        this.add(browserToolBar, BorderLayout.PAGE_START);
        this.setBorder(BorderFactory.createEmptyBorder());
    }
}
