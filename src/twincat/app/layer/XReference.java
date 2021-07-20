package twincat.app.layer;

public class XReference {
    /*********************************/
    /************ layer 6 ************/
    /*********************************/

    public final ScopeBrowser scopeBrowser = new ScopeBrowser(this);

    public final SymbolBrowser symbolBrowser = new SymbolBrowser(this);

    public final ColorProperties colorProperties = new ColorProperties(this);
      
    public final ScopeProperties scopeProperties = new ScopeProperties(this);

    public final ChartProperties chartProperties = new ChartProperties(this);
    
    public final AxisProperties axisProperties = new AxisProperties(this);

    public final ChannelProperties channelProperties = new ChannelProperties(this);

    public final AcquisitionProperties acquisitionProperties = new AcquisitionProperties(this);
 
    public final TriggerGroupProperties triggerGroupProperties = new TriggerGroupProperties(this);

    public final TriggerChannelProperties triggerChannelProperties = new TriggerChannelProperties(this);

    /*********************************/
    /************ layer 5 ************/
    /*********************************/

    public final ChartPanel chartPanel = new ChartPanel(this);

    public final LoaderPanel loaderPanel = new LoaderPanel(this);

    public final BrowserPanel browserPanel = new BrowserPanel(this);
      
    public final PropertiesPanel propertiesPanel = new PropertiesPanel(this);

    /*********************************/
    /************ layer 4 ************/
    /*********************************/

    public final NavigationPanel navigationPanel = new NavigationPanel(this);

    public final ControlPanel controlPanel = new ControlPanel(this);

    /*********************************/
    /************ layer 3 ************/
    /*********************************/

    public final AdsPanel adsPanel = new AdsPanel(this);

    public final AxisPanel axisPanel = new AxisPanel(this);

    public final SettingsPanel settingsPanel = new SettingsPanel(this);

    public final ScopePanel scopePanel = new ScopePanel(this);

    /*********************************/
    /************ layer 2 ************/
    /*********************************/

    public final ConsolePanel consolePanel = new ConsolePanel(this);

    public final WindowPanel windowPanel = new WindowPanel(this);

    /*********************************/
    /************ layer 1 ************/
    /*********************************/

    public final ContentPanel contentPanel = new ContentPanel(this);
}
