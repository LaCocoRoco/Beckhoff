package twincat.app.layer;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import twincat.Resources;
import twincat.app.constant.Browser;
import twincat.app.constant.Properties;

public class ControlPanel extends JSplitPane {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /******** cross reference ********/
    /*********************************/

    private final XReference xref;

    /*********************************/
    /**** local constant variable ****/
    /*********************************/

    private static final double DIVIDER_LOCATION = 0.5;

    /*********************************/
    /******** local variable *********/
    /*********************************/

    private Properties properties = Properties.EMPTY;

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public ControlPanel(XReference xref) {
        this.xref = xref;

        this.setLeftComponent(xref.treePanel);
        this.setRightComponent(xref.propertiesPanel);
        this.setDividerSize(Resources.DEFAULT_DIVIDER_SIZE);
        this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        this.setContinuousLayout(true);
        this.setOneTouchExpandable(false);
        this.setBorder(BorderFactory.createEmptyBorder());
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                setDividerLocation(DIVIDER_LOCATION);
            }
        });

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setDividerLocation(DIVIDER_LOCATION);
            }
        });
    }

    /*********************************/
    /********* public method *********/
    /*********************************/

    public void displaySearch() {
        properties = xref.propertiesPanel.getCard();
        xref.treePanel.setCard(Browser.SEARCH);
        xref.propertiesPanel.setCard(Properties.ACQUISITION);
    }

    public void displayBrowser() {
        xref.treePanel.setCard(Browser.BROWSER);
        xref.propertiesPanel.setCard(properties);
    }
}
