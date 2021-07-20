package twincat.app.layer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileView;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import twincat.Resources;
import twincat.TwincatLogger;
import twincat.Utilities;
import twincat.ads.constant.AmsPort;
import twincat.ads.constant.DataType;
import twincat.app.constant.Navigation;
import twincat.scope.Acquisition;
import twincat.scope.Axis;
import twincat.scope.Channel;
import twincat.scope.Chart;
import twincat.scope.Scope;
import twincat.scope.TriggerChannel;
import twincat.scope.TriggerChannel.Combine;
import twincat.scope.TriggerChannel.Release;
import twincat.scope.TriggerGroup;

public class LoaderPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /******** cross reference ********/
    /*********************************/

    private final XReference xref;

    /*********************************/
    /**** local constant variable ****/
    /*********************************/

    private static final ImageIcon ICON_CHANNEL = new ImageIcon(Utilities.getImageFromFilePath(Resources.PATH_ICON_APP_CHANNEL));

    /*********************************/
    /******** local variable *********/
    /*********************************/

    private final JFileChooser fileChooser = new JFileChooser();

    private final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

    private final FileNameExtensionFilter filter = new FileNameExtensionFilter("Scope", "sv2");

    private final Logger logger = TwincatLogger.getLogger();

    /*********************************/
    /****** predefined variable ******/
    /*********************************/

    private final FileView fileView = new FileView() {
        @Override
        public Icon getIcon(File file) {
            if (!file.isDirectory() && filter.accept(file)) {
                return ICON_CHANNEL;
            } else {
                return super.getIcon(file);
            }
        }
    };

   private final SwingWorker<Void, Void> loadScopeFileTask = new SwingWorker<Void, Void>() {
       @Override
       protected Void doInBackground() throws Exception {
           Scope scope = loadScopeFile(fileChooser.getSelectedFile().getPath());

           if (scope != null) {
               xref.scopeBrowser.addScope(scope);
           }

           return null;
       }
   };
    
    private final ActionListener fileOpenListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
                loadScopeFileTask.execute();
                xref.navigationPanel.setCard(Navigation.CHART);
            }

            if (actionEvent.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)) {
                xref.navigationPanel.setCard(Navigation.CHART);
            }
        }
    };

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public LoaderPanel(XReference xref) {
        this.xref = xref;

        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setFileFilter(filter);
        fileChooser.setFileView(fileView);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.addActionListener(fileOpenListener);

        this.setLayout(new BorderLayout());
        this.add(fileChooser, BorderLayout.CENTER);
        this.setBorder(BorderFactory.createEmptyBorder());
    }

    /*********************************/
    /******** private method *********/
    /*********************************/

    private Channel getChannel(Node channelNode) {
        Channel channel = new Channel();
        Acquisition acquisition = new Acquisition();
        channel.setAcquisition(acquisition);

        // symbol data
        if (channelNode.getNodeType() == Node.ELEMENT_NODE) {
            Element channelElement = (Element) channelNode;
            String channelName = channelElement.getElementsByTagName("Name").item(0).getTextContent();
            channel.setChannelName(channelName);
            
            logger.fine(String.format("%-14s", "Channel") + " | " + channelName);
            
            // acquisition data
            Node acquisitionNode = channelElement.getElementsByTagName("Acquisition").item(0);
            if (acquisitionNode.getNodeType() == Node.ELEMENT_NODE) {
                Element acquisitionElement = (Element) acquisitionNode;

                String dataType = acquisitionElement.getElementsByTagName("DataType").item(0).getTextContent();
                String indexGroup = acquisitionElement.getElementsByTagName("IndexGroup").item(0).getTextContent();
                String indexOffset = acquisitionElement.getElementsByTagName("IndexOffset").item(0).getTextContent();
                String amsPort = acquisitionElement.getElementsByTagName("TargetPort").item(0).getTextContent();
                String symbolName = acquisitionElement.getElementsByTagName("SymbolName").item(0).getTextContent();
                String amsNetId = acquisitionElement.getElementsByTagName("AmsNetIdString").item(0).getTextContent();
                String symbolBased = acquisitionElement.getElementsByTagName("IsSymbolBased").item(0).getTextContent();

                acquisition.setDataType(DataType.valueOf(dataType));
                acquisition.setIndexGroup(Integer.valueOf(indexGroup));
                acquisition.setIndexOffset(Integer.valueOf(indexOffset));
                acquisition.setAmsPort(AmsPort.getByValue(Integer.valueOf(amsPort)));
                acquisition.setSymbolName(symbolName);
                acquisition.setAmsNetId(amsNetId);
                acquisition.setSymbolBased(Boolean.valueOf(symbolBased));
            }

            // channel style data
            Node channelStyleNode = channelElement.getElementsByTagName("Style").item(0);
            if (channelStyleNode.getNodeType() == Node.ELEMENT_NODE) {
                Element channelStyleElement = (Element) channelStyleNode;

                String lineWidth = channelStyleElement.getElementsByTagName("LineWidth").item(0).getTextContent();
                String lineColor = channelStyleElement.getElementsByTagName("ColorValue").item(0).getTextContent();
                String plotColor = channelStyleElement.getElementsByTagName("MarkColorValue").item(0).getTextContent();
                String plotSize = channelStyleElement.getElementsByTagName("MarkSize").item(0).getTextContent();
                String antialias = channelStyleElement.getElementsByTagName("Antialias").item(0).getTextContent();
                String plotVisible = channelStyleElement.getElementsByTagName("Marks").item(0).getTextContent();
                String channelVisible = channelStyleElement.getElementsByTagName("Visible").item(0).getTextContent();

                channel.setLineWidth(Integer.valueOf(lineWidth));
                channel.setLineColor(new Color(0xFFFFFF & Integer.valueOf(lineColor)));
                channel.setPlotColor(new Color(0xFFFFFF & Integer.valueOf(plotColor)));
                channel.setPlotSize(Integer.valueOf(plotSize));
                channel.setAntialias(Boolean.valueOf(antialias));
                channel.setPlotVisible(Boolean.valueOf(plotVisible));
                channel.setChannelVisible(Boolean.valueOf(channelVisible));
            }
        }
        
        return channel;
    }
    
    private Axis getAxis(Node axisNode)  {
        Axis axis = new Axis();

        // axis data
        if (axisNode.getNodeType() == Node.ELEMENT_NODE) {
            Element axisElement = (Element) axisNode;
            String axisName = axisElement.getElementsByTagName("Name").item(0).getTextContent();
            axis.setAxisName(axisName);
            
            logger.fine(String.format("%-14s", "Axis") + " | " + axisName);

            Node axisStyleNode = axisElement.getElementsByTagName("Style").item(0);
            if (axisStyleNode.getNodeType() == Node.ELEMENT_NODE) {
                Element axisStyleElement = (Element) axisStyleNode;

                String axisVisible = axisStyleElement.getElementsByTagName("Enabled").item(0).getTextContent();
                String axisColor = axisStyleElement.getElementsByTagName("ColorValue").item(0).getTextContent();
                String lineWidth = axisStyleElement.getElementsByTagName("LineWidth").item(0).getTextContent();
                String autoscale = axisStyleElement.getElementsByTagName("AutoScale").item(0).getTextContent();
                String axisNameVisible = axisStyleElement.getElementsByTagName("ShowName").item(0).getTextContent();
                String valueMin = axisStyleElement.getElementsByTagName("AxisMin").item(0).getTextContent();
                String valueMax = axisStyleElement.getElementsByTagName("AxisMax").item(0).getTextContent();
                axis.setAxisVisible(Boolean.valueOf(axisVisible));

                axis.setAxisColor(new Color(0xFFFFFF & Integer.valueOf(axisColor)));
                axis.setLineWidth(Integer.valueOf(lineWidth));
                axis.setAutoscale(Boolean.valueOf(autoscale));
                axis.setAxisNameVisible(Boolean.valueOf(axisNameVisible));
                axis.setValueMin(Long.valueOf(valueMin));
                axis.setValueMax(Long.valueOf(valueMax));
            }

            // channels node
            Node channelsNode = axisElement.getElementsByTagName("Channels").item(0);
            if (channelsNode.getNodeType() == Node.ELEMENT_NODE) {
                Element channelsElement = (Element) channelsNode;

                // channel node array
                NodeList channelNodeList = channelsElement.getElementsByTagName("ScopeChannelSerializable");
                for (int channelIndex = 0; channelIndex < channelNodeList.getLength(); channelIndex++) {
                    Node channelNode = channelNodeList.item(channelIndex); 
                    Channel channel = getChannel(channelNode);
                    
                    // axis add channel
                    axis.addChannel(channel);
                }
            }
        }
        
        return axis;
    }
    
    private Chart getChart(Node chartNode) {
        Chart chart = new Chart();

        // chart data
        if (chartNode.getNodeType() == Node.ELEMENT_NODE) {
            Element chartElement = (Element) chartNode;
            String chartName = chartElement.getElementsByTagName("Name").item(0).getTextContent();
            chart.setChartName(chartName);

            logger.fine(String.format("%-14s", "Chart") + " | " + chartName);
            
            Node chartStyleNode = chartElement.getElementsByTagName("Style").item(0);
            if (chartStyleNode.getNodeType() == Node.ELEMENT_NODE) {
                Element chartStyleElement = (Element) chartStyleNode;

                String chartColor = chartStyleElement.getElementsByTagName("BackgroundColorValue").item(0).getTextContent();
                String borderColor = chartStyleElement.getElementsByTagName("BorderColorValue").item(0).getTextContent();
                String displayTimeText = chartStyleElement.getElementsByTagName("BaseTime").item(0).getTextContent();

                chart.setChartColor(new Color(0xFFFFFF & Integer.valueOf(chartColor)));
                chart.setBorderColor(new Color(0xFFFFFF & Integer.valueOf(borderColor)));

                long displayTime = Long.valueOf(displayTimeText) / 10000;
                long displayTimeMax = Scope.timeFormaterToLong(Scope.TIME_FORMAT_MAX_TIME);

                if (displayTime > displayTimeMax) displayTime = displayTimeMax;

                chart.setDisplayTime(displayTime);
            }

            Node xAxisNode = chartElement.getElementsByTagName("XAxis").item(0);
            if (xAxisNode.getNodeType() == Node.ELEMENT_NODE) {
                Element xAxisElement = (Element) xAxisNode;
                Node xAxisStyleNode = xAxisElement.getElementsByTagName("Style").item(0);
                if (xAxisStyleNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element xAxisStyleElement = (Element) xAxisStyleNode;

                    String timeLineColor = xAxisStyleElement.getElementsByTagName("ColorValue").item(0).getTextContent();
                    String timeTickCount = xAxisStyleElement.getElementsByTagName("Ticks").item(0).getTextContent();
                    String gridLineColor = xAxisStyleElement.getElementsByTagName("GridColorValue").item(0).getTextContent();
                    String lineWidth = xAxisStyleElement.getElementsByTagName("GridLineWidth").item(0).getTextContent();

                    chart.setTimeLineColor(new Color(0xFFFFFF & Integer.valueOf(timeLineColor)));
                    chart.setTimeTickCount(Integer.valueOf(timeTickCount));
                    chart.setGridLineColor(new Color(0xFFFFFF & Integer.valueOf(gridLineColor)));
                    chart.setLineWidth(Integer.valueOf(lineWidth));
                }
            }

            // axis node
            Node yAxisNode = chartElement.getElementsByTagName("YAxes").item(0);
            if (yAxisNode.getNodeType() == Node.ELEMENT_NODE) {
                Element yAxisElement = (Element) yAxisNode;

                // axis node array
                NodeList axisNodeList = yAxisElement.getElementsByTagName("ScopeYAxisSerializable");
                for (int axisIndex = 0; axisIndex < axisNodeList.getLength(); axisIndex++) {
                    Node axisNode = axisNodeList.item(axisIndex);
                    Axis axis = getAxis(axisNode);

                    // chart add axis
                    chart.addAxis(axis);
                }
            }
        }
        
        return chart;
    }
    
    private TriggerChannel getTriggerChannel(Node triggerChannelNode, Scope scope) {
        TriggerChannel triggerChannel = new TriggerChannel();

        if (triggerChannelNode.getNodeType() == Node.ELEMENT_NODE) {
            Element triggerChannelElement = (Element) triggerChannelNode; 
            String threshold = triggerChannelElement.getElementsByTagName("Threshold").item(0).getTextContent();
            String combine = triggerChannelElement.getElementsByTagName("CombineOption").item(0).getTextContent();
            String release = triggerChannelElement.getElementsByTagName("ReleaseOption").item(0).getTextContent();
            String handle = triggerChannelElement.getElementsByTagName("ConnectedChannelHandle").item(0).getTextContent();

            triggerChannel.setThreshold(Double.valueOf(threshold));
            triggerChannel.setCombine(Combine.valueOf(combine));
            triggerChannel.setRelease(release.contains("RisingEdge") ? Release.RISING_EDGE : Release.FALLING_EDGE);

            int identHandle = 1;
            triggerChannelLocated:
            for (Chart chart : scope.getChartList()) {
                for (Axis axis : chart.getAxisList()) {
                    for (Channel channel : axis.getChannelList()) {
                        if (identHandle == Integer.valueOf(handle)) {
                            triggerChannel.setChannel(channel);
                            
                            logger.fine(String.format("%-14s", "TriggerChannel") + " | " + channel.getChannelName());

                            break triggerChannelLocated;
                        }  
                    } 
                }
            }  
        } 
        
        return triggerChannel;
    }
    
    private TriggerGroup getTriggerGroup(Node triggerGroupNode, Scope scope) {
        TriggerGroup triggerGroup = new TriggerGroup();
        
        logger.fine(String.format("%-14s", "TriggerGroup") + " | " + triggerGroup);
        
        // group data
        if (triggerGroupNode.getNodeType() == Node.ELEMENT_NODE) {
            Element triggerGroupElement = (Element) triggerGroupNode;
            String triggerGroupName = triggerGroupElement.getElementsByTagName("Title").item(0).getTextContent();
            String triggerOffset = triggerGroupElement.getElementsByTagName("TriggerPrePercent").item(0).getTextContent();
            String enabled = triggerGroupElement.getElementsByTagName("Enabled").item(0).getTextContent();
            
            triggerGroup.setTriggerGroupName(triggerGroupName);
            triggerGroup.setTriggerOffset(Integer.valueOf(triggerOffset));
            triggerGroup.setEnabled(Boolean.valueOf(enabled));

            // trigger sets node
            Node triggerSetsNode = triggerGroupElement.getElementsByTagName("TriggerSets").item(0);
            if (triggerSetsNode.getNodeType() == Node.ELEMENT_NODE) {
                Element triggerSetsElement = (Element) triggerSetsNode;
                
                NodeList triggerChannelList = triggerSetsElement.getElementsByTagName("ScopeTriggerSingleSetSerializable");
                for (int triggerChannelIndex = 0; triggerChannelIndex < triggerChannelList.getLength(); triggerChannelIndex++) {
                    Node triggerChannelNode = triggerChannelList.item(triggerChannelIndex);
                    TriggerChannel triggerChannel = getTriggerChannel(triggerChannelNode, scope);
                    
                    // scope add trigger channel
                    triggerGroup.addTriggerChannel(triggerChannel);
                }  
            } 
        }
        
        return triggerGroup;
    }

    private Scope getScope(Node scopeNode) {
        Scope scope = new Scope();
        
        if (scopeNode.getNodeType() == Node.ELEMENT_NODE) {
            Element scopeElement = (Element) scopeNode;
            String scopeName = scopeElement.getElementsByTagName("Title").item(0).getTextContent();
            scope.setScopeName(scopeName);
            
            logger.fine(String.format("%-14s", "Scope") + " | " + scopeName);
            
            Node operationNode = scopeElement.getElementsByTagName("Operating").item(0);
            if (operationNode.getNodeType() == Node.ELEMENT_NODE) {
                Element operationElement = (Element) operationNode;
                String recordTimeText = operationElement.getElementsByTagName("RecordTime").item(0).getTextContent();

                long recordTime = Long.valueOf(recordTimeText) / 10000;
                long recordTimeMax = Scope.timeFormaterToLong(Scope.TIME_FORMAT_MAX_TIME);

                if (recordTime > recordTimeMax) recordTime = recordTimeMax;

                scope.setRecordTime(recordTime);
            }

            // charts node
            Node chartsNode = scopeElement.getElementsByTagName("Charts").item(0);
            if (chartsNode.getNodeType() == Node.ELEMENT_NODE) {
                Element chartsElement = (Element) chartsNode;

                // chart node array
                NodeList chartNodeList = chartsElement.getElementsByTagName("ScopeChartSerializable");
                for (int chartIndex = 0; chartIndex < chartNodeList.getLength(); chartIndex++) {
                    Node chartNode = chartNodeList.item(chartIndex);
                    Chart chart = getChart(chartNode);

                    // scope add chart
                    scope.addChart(chart);
                }
            }

            // trigger module
            Node triggerModuleNode = scopeElement.getElementsByTagName("TriggerModule").item(0);
            if (triggerModuleNode.getNodeType() == Node.ELEMENT_NODE) {
                Element triggerModuleElement = (Element) triggerModuleNode;
                
                // trigger group node
                Node triggerGroupsNode = triggerModuleElement.getElementsByTagName("TriggerGroups").item(0);
                if (triggerGroupsNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element triggerGroupsElement = (Element) triggerGroupsNode;
                    
                    // trigger group node array
                    NodeList triggerGroupNodeList = triggerGroupsElement.getElementsByTagName("ScopeTriggerGroupSerializable");
                    for (int triggerGroupIndex = 0; triggerGroupIndex < triggerGroupNodeList.getLength(); triggerGroupIndex++) {
                        Node triggerGroupNode = triggerGroupNodeList.item(triggerGroupIndex);
                        TriggerGroup triggerGroup = getTriggerGroup(triggerGroupNode, scope);

                        // scope add trigger group
                        scope.addTriggerGroup(triggerGroup);
                    }
                }
            }
        }
        
        return scope;
    }
    
    private Scope loadScopeFile(String path) {
        try {
            // build document
            documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(path));
            document.getDocumentElement().normalize();

            logger.fine(String.format("%-14s", "Loading Scope") + " | " + path);
            
            // build scope
            Node scopeNode = document.getElementsByTagName("ScopeViewSerializable").item(0);
            Scope scope = getScope(scopeNode);
            
            logger.fine("Scope Loaded");
            
            return scope;
        } catch (Exception e) {
            logger.fine(Utilities.exceptionToString(e));
        }
        
        return null;
    }
}
