package twincat.app.layer;

public class XReference {
    /*********************************/
    /************ layer 6 ************/
    /*********************************/
    
    public final ScopeProperties scopeProperties = new ScopeProperties(this);

    public final ChartProperties chartProperties = new ChartProperties(this);
    
    public final AxisProperties axisProperties = new AxisProperties(this);

    public final ChannelProperties channelProperties = new ChannelProperties(this);

    public final AcquisitionProperties acquisitionProperties = new AcquisitionProperties(this);
 
    public final TriggerGroupProperties triggerGroupProperties = new TriggerGroupProperties(this);

    public final TriggerChannelProperties triggerChannelProperties = new TriggerChannelProperties(this);

    public final ScopeTree scopeTree = new ScopeTree(this);

    public final SymbolTree symbolTree = new SymbolTree(this);

    /*********************************/
    /************ layer 5 ************/
    /*********************************/

    public final PropertiesPanel propertiesPanel = new PropertiesPanel(this);

    public final TreePanel treePanel = new TreePanel(this);

    /*********************************/
    /************ layer 4 ************/
    /*********************************/

    public final ChartPanel chartPanel = new ChartPanel(this);

    public final ControlPanel controlPanel = new ControlPanel(this);

    /*********************************/
    /************ layer 3 ************/
    /*********************************/

    public final SettingsPanel settingsPanel = new SettingsPanel(this);

    public final ScopePanel scopePanel = new ScopePanel(this);

    public final AdsPanel adsPanel = new AdsPanel(this);

    public final AxisPanel axisPanel = new AxisPanel(this);

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
