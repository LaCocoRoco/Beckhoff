package twincat.app.component;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import twincat.Resources;
import twincat.app.constant.Browser;
import twincat.app.constant.Properties;

public class PanelControl extends JSplitPane {
    private static final long serialVersionUID = 1L;

    /*************************/
    /** constant attributes **/
    /*************************/

    private static final double DIVIDER_LOCATION = 0.5;

    /*************************/
    /*** global attributes ***/
    /*************************/

    private final PanelScope panelScope;

    /*************************/
    /*** local attributes ****/
    /*************************/
    
    private Properties properties = Properties.EMPTY;
    
    private final PanelTree panelTree = new PanelTree(this);

    private final PanelProperties panelProperties = new PanelProperties(this);

    /*************************/
    /****** constructor ******/
    /*************************/
    
    public PanelControl(PanelScope panelScope) {
        this.panelScope = panelScope;
        
        this.setLeftComponent(panelTree);
        this.setRightComponent(panelProperties);
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

    /*************************/
    /**** setter & getter ****/
    /*************************/

    public PanelScope getPanelScope() {
        return panelScope;
    }

    /*************************/
    /********* public ********/
    /*************************/
  
    public void displaySearch() {
        properties = panelProperties.getCard();
        panelTree.setCard(Browser.SEARCH);
        panelProperties.setCard(Properties.ACQUISITION);
    }
    
    public void displayBrowser() {
        panelTree.setCard(Browser.BROWSER);
        panelProperties.setCard(properties);
    }
}
