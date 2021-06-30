package twincat.app.components;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class WindowPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /*************************/
    /** constant attributes **/
    /*************************/

    public static final String WINDOW_SCOPE    = "Scope";

    public static final String WINDOW_ADS_INFO = "Ads";

    public static final String WINDOW_AXIS     = "Achsen";

    /*************************/
    /*** local attributes ***/
    /*************************/

    private final AdsInfoPanel adsInfoPanel = new AdsInfoPanel();

    private final ScopePanel scopePanel = new ScopePanel();

    private final AxisPanel axisPanel = new AxisPanel();

    /*************************/
    /****** constructor ******/
    /*************************/

    public WindowPanel() {
        this.setLayout(new CardLayout());
        this.add(scopePanel, WINDOW_SCOPE);
        this.add(adsInfoPanel, WINDOW_ADS_INFO);
        this.add(axisPanel, WINDOW_AXIS);
    }

    /*************************/
    /********* public ********/
    /*************************/
   
    public void displayWindow(String name) {
        CardLayout windowLayout = (CardLayout) (this.getLayout());
        windowLayout.show(this, name);
    }    
}
