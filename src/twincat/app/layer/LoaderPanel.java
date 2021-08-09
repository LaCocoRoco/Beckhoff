package twincat.app.layer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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

    private final ActionListener fileOpenListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
                buildScope();
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

    private void buildScope() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    Scope scope = loadScopeFromScopeFile(fileChooser.getSelectedFile().getPath());
                    
                    if (scope != null) {
                        xref.scopeBrowser.addScope(scope);                   
                    }
                } catch (Exception e) {
                    logger.fine(Utilities.exceptionToString(e));
                }

                return null;
            }
        }.execute(); 
    }
    
    private Channel getChannel(Node channelNode) {
        Channel channel = new Channel();
        Acquisition acquisition = new Acquisition();
        channel.setAcquisition(acquisition);

        if (channelNode.getNodeType() == Node.ELEMENT_NODE) {
            Element channelElement = (Element) channelNode;
            NodeList nameList = channelElement.getElementsByTagName("Name");
            NodeList identHandleList = channelElement.getElementsByTagName("IdentHandle");
            
            // channel name
            if (nameList.getLength() > 0) {
                String channelName = nameList.item(0).getTextContent();
                channel.setChannelName(channelName);
            }

            // channel identification handle
            if (identHandleList.getLength() > 0) {
                String identHandle = identHandleList.item(0).getTextContent();
                channel.setIdentHandle(Integer.valueOf(identHandle));
            }

            // acquisition list
            NodeList acquisitionList = channelElement.getElementsByTagName("Acquisition");  
            if (acquisitionList.getLength() > 0) {
                Node acquisitionNode = acquisitionList.item(0);
                if (acquisitionNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element acquisitionElement = (Element) acquisitionNode;

                    NodeList dataTypeList = acquisitionElement.getElementsByTagName("DataType");
                    NodeList indexGroupList = acquisitionElement.getElementsByTagName("IndexGroup");
                    NodeList indexOffsetList = acquisitionElement.getElementsByTagName("IndexOffset");
                    NodeList targetPortList = acquisitionElement.getElementsByTagName("TargetPort");
                    NodeList symbolNameList = acquisitionElement.getElementsByTagName("SymbolName");
                    NodeList amsNetIdStringList = acquisitionElement.getElementsByTagName("AmsNetIdString");
                    NodeList isSymbolBasedList = acquisitionElement.getElementsByTagName("IsSymbolBased");
                    NodeList disabledList = acquisitionElement.getElementsByTagName("Disabled");
                    
                    // acquisition data type
                    if (dataTypeList.getLength() > 0) {
                        String dataType = dataTypeList.item(0).getTextContent();
                        acquisition.setDataType(DataType.valueOf(dataType));
                    }

                    // acquisition index group
                    if (indexGroupList.getLength() > 0) {
                        String indexGroup = indexGroupList.item(0).getTextContent();
                        acquisition.setIndexGroup(Integer.valueOf(indexGroup));
                    }

                    // acquisition index offset
                    if (indexOffsetList.getLength() > 0) {
                        String indexOffset = indexOffsetList.item(0).getTextContent();
                        acquisition.setIndexOffset(Integer.valueOf(indexOffset));
                    }

                    // acquisition target port
                    if (targetPortList.getLength() > 0) {
                        String amsPort = targetPortList.item(0).getTextContent();
                        acquisition.setAmsPort(AmsPort.getByValue(Integer.valueOf(amsPort)));
                    }

                    // acquisition symbol name
                    if (symbolNameList.getLength() > 0) {
                        String symbolName = symbolNameList.item(0).getTextContent();
                        acquisition.setSymbolName(symbolName);
                    }

                    // acquisition ams net id
                    if (amsNetIdStringList.getLength() > 0) {
                        String amsNetId = amsNetIdStringList.item(0).getTextContent();
                        acquisition.setAmsNetId(amsNetId);
                    }
                    
                    // acquisition symbol based
                    if (isSymbolBasedList.getLength() > 0) {
                        String symbolBased = isSymbolBasedList.item(0).getTextContent();
                        acquisition.setSymbolBased(Boolean.valueOf(symbolBased));
                    }
                    
                    // channel enabled
                    if (disabledList.getLength() > 0) {
                        String enabled = disabledList.item(0).getTextContent();
                        channel.setEnabled(!Boolean.valueOf(enabled));
                    }
                }  
                
            }
            
            // channel style list
            NodeList channelStyleList = channelElement.getElementsByTagName("Style");
            if (channelStyleList.getLength() > 0) {
                Node channelStyleNode = channelStyleList.item(0); 
                if (channelStyleNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element channelStyleElement = (Element) channelStyleNode;

                    NodeList lineWidthList = channelStyleElement.getElementsByTagName("LineWidth");
                    NodeList colorValueList = channelStyleElement.getElementsByTagName("ColorValue");
                    NodeList markColorValueList = channelStyleElement.getElementsByTagName("MarkColorValue");
                    NodeList markSizeList = channelStyleElement.getElementsByTagName("MarkSize");
                    NodeList antialiasList = channelStyleElement.getElementsByTagName("Antialias");
                    NodeList marksList = channelStyleElement.getElementsByTagName("Marks");
                    NodeList visibleList = channelStyleElement.getElementsByTagName("Visible");

                    // channel line width
                    if (lineWidthList.getLength() > 0) {
                        String lineWidth = lineWidthList.item(0).getTextContent();
                        channel.setLineWidth(Integer.valueOf(lineWidth));
                    }

                    // channel line color
                    if (colorValueList.getLength() > 0) {
                        String lineColor = colorValueList.item(0).getTextContent();
                        channel.setLineColor(new Color(0xFFFFFF & Integer.valueOf(lineColor)));
                    }

                    // channel plot color
                    if (markColorValueList.getLength() > 0) {
                        String plotColor = markColorValueList.item(0).getTextContent();
                        channel.setPlotColor(new Color(0xFFFFFF & Integer.valueOf(plotColor)));
                    }

                    // channel plot size
                    if (markSizeList.getLength() > 0) {
                        String plotSize = markSizeList.item(0).getTextContent();
                        channel.setPlotSize(Integer.valueOf(plotSize));
                    }

                    // channel anti alias
                    if (antialiasList.getLength() > 0) {
                        String antialias = antialiasList.item(0).getTextContent();
                        channel.setAntialias(Boolean.valueOf(antialias));
                    }

                    // channel plot visible
                    if (marksList.getLength() > 0) {
                        String plotVisible = marksList.item(0).getTextContent();
                        channel.setPlotVisible(Boolean.valueOf(plotVisible));
                    }

                    // channel line visible
                    if (visibleList.getLength() > 0) {
                        String channelVisible = visibleList.item(0).getTextContent();
                        channel.setLineVisible(Boolean.valueOf(channelVisible));
                    }
                }
            }
        }

        return channel;
    }

    private Axis getAxis(Node axisNode) {
        Axis axis = new Axis();

        if (axisNode.getNodeType() == Node.ELEMENT_NODE) {
            Element axisElement = (Element) axisNode;
            NodeList axisNameList = axisElement.getElementsByTagName("Name");
            
            // axis name
            if (axisNameList.getLength() > 0) {
                String axisName = axisNameList.item(0).getTextContent();
                axis.setAxisName(axisName);
            }
            
            // axis style list
            NodeList axisStyleList = axisElement.getElementsByTagName("Style");
            if (axisStyleList.getLength() > 0) {
                Node axisStyleNode = axisStyleList.item(0);
                if (axisStyleNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element axisStyleElement = (Element) axisStyleNode;

                    NodeList enabledList = axisStyleElement.getElementsByTagName("Enabled");
                    NodeList colorValueList = axisStyleElement.getElementsByTagName("ColorValue");
                    NodeList lineWidthList = axisStyleElement.getElementsByTagName("LineWidth");
                    NodeList autoScaleList = axisStyleElement.getElementsByTagName("AutoScale");
                    NodeList showNameList = axisStyleElement.getElementsByTagName("ShowName");
                    NodeList axisMinList = axisStyleElement.getElementsByTagName("AxisMin");
                    NodeList axisMaxList = axisStyleElement.getElementsByTagName("AxisMax");
                    
                    // axis visible
                    if (enabledList.getLength() > 0) {
                        String axisVisible = enabledList.item(0).getTextContent();
                        axis.setAxisVisible(Boolean.valueOf(axisVisible));
                    }
                    
                    // axis color
                    if (colorValueList.getLength() > 0) {
                        String axisColor = colorValueList.item(0).getTextContent();
                        axis.setAxisColor(new Color(0xFFFFFF & Integer.valueOf(axisColor)));
                    }
                    
                    // line width
                    if (lineWidthList.getLength() > 0) {
                        String lineWidth = lineWidthList.item(0).getTextContent();
                        axis.setLineWidth(Integer.valueOf(lineWidth));
                    } 
                    
                    // auto scale
                    if (autoScaleList.getLength() > 0) {
                        String autoscale = autoScaleList.item(0).getTextContent();
                        axis.setAutoscale(Boolean.valueOf(autoscale));
                    }   
                    
                    // axis name visible
                    if (showNameList.getLength() > 0) {
                        String axisNameVisible = showNameList.item(0).getTextContent();
                        axis.setAxisNameVisible(Boolean.valueOf(axisNameVisible));
                    } 
                    
                    // value minimum
                    if (axisMinList.getLength() > 0) {
                        String valueMin = axisMinList.item(0).getTextContent();
                        axis.setValueMin(Double.valueOf(valueMin));
                    }   
               
                    // value maximum
                    if (axisMaxList.getLength() > 0) {
                        String valueMax = axisMaxList.item(0).getTextContent();
                        axis.setValueMax(Double.valueOf(valueMax));
                    }   
                }  
            }
            
            // channels list
            NodeList channelList = axisElement.getElementsByTagName("Channels");
            if (channelList.getLength() > 0 ) {
                Node channelsNode = channelList.item(0);
                if (channelsNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element channelsElement = (Element) channelsNode;

                    // serialized channel list
                    NodeList channelNodeList = channelsElement.getElementsByTagName("ScopeChannelSerializable");
                    for (int channelIndex = 0; channelIndex < channelNodeList.getLength(); channelIndex++) {
                        Node channelNode = channelNodeList.item(channelIndex);
                        Channel channel = getChannel(channelNode);

                        // axis add channel
                        axis.addChannel(channel);
                    }
                }  
            }
        }

        return axis;
    }

    private Chart getChart(Node chartNode) {
        Chart chart = new Chart();

        if (chartNode.getNodeType() == Node.ELEMENT_NODE) {
            Element chartElement = (Element) chartNode;
            NodeList nameList = chartElement.getElementsByTagName("Name");
            
            // chart name
            if (nameList.getLength() > 0) {
                String chartName = nameList.item(0).getTextContent();    
                chart.setChartName(chartName);  
            }
            
            // chart style list
            NodeList chartStyleList = chartElement.getElementsByTagName("Style");
            if (chartStyleList.getLength() > 0)  {
                Node chartStyleNode = chartStyleList.item(0);
                if (chartStyleNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element chartStyleElement = (Element) chartStyleNode;

                    NodeList backgroundColorValueList = chartStyleElement.getElementsByTagName("BackgroundColorValue");
                    NodeList borderColorValueList = chartStyleElement.getElementsByTagName("BorderColorValue");
                    NodeList baseTimeList = chartStyleElement.getElementsByTagName("BaseTime");
                    
                    // chart color
                    if (backgroundColorValueList.getLength() > 0) {
                        String chartColor = backgroundColorValueList.item(0).getTextContent();    
                        chart.setChartColor(new Color(0xFFFFFF & Integer.valueOf(chartColor)));
                    }
                    
                    // chart border color
                    if (borderColorValueList.getLength() > 0) {
                        String borderColor = borderColorValueList.item(0).getTextContent();    
                        chart.setBorderColor(new Color(0xFFFFFF & Integer.valueOf(borderColor)));
                    }
                    
                    // chart display time
                    if (baseTimeList.getLength() > 0) {
                        String displayTimeText = baseTimeList.item(0).getTextContent();    
                        long displayTime = Long.valueOf(displayTimeText) / 10000;
                        long displayTimeMax = Scope.timeFormaterToLong(Scope.TIME_FORMAT_MAX_TIME);
                        if (displayTime > displayTimeMax) displayTime = displayTimeMax;
                        chart.setDisplayTime(displayTime);
                    }
                } 
            }
            
            // xAxis list
            NodeList xAxisList = chartElement.getElementsByTagName("XAxis");
            if (xAxisList.getLength() > 0) {
                Node xAxisNode = xAxisList.item(0);
                if (xAxisNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element xAxisElement = (Element) xAxisNode;
                    
                    // xAxis style list
                    NodeList xAxisStyleList = xAxisElement.getElementsByTagName("Style");
                    if (xAxisStyleList.getLength() > 0) {
                        Node xAxisStyleNode = xAxisStyleList.item(0);
                        if (xAxisStyleNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element xAxisStyleElement = (Element) xAxisStyleNode;

                            NodeList backgroundColorValueList = xAxisStyleElement.getElementsByTagName("BackgroundColorValue");
                            NodeList ticksList = xAxisStyleElement.getElementsByTagName("Ticks");
                            NodeList gridColorValueList = xAxisStyleElement.getElementsByTagName("GridColorValue");
                            NodeList gridLineWidthList = xAxisStyleElement.getElementsByTagName("GridLineWidth");
                            
                            // chart time line color
                            if (backgroundColorValueList.getLength() > 0) {
                                String timeLineColor = backgroundColorValueList.item(0).getTextContent();
                                chart.setTimeLineColor(new Color(0xFFFFFF & Integer.valueOf(timeLineColor)));
                            }
                            
                            // chart time line color
                            if (ticksList.getLength() > 0) {
                                String timeTickCount = ticksList.item(0).getTextContent();
                                chart.setTimeTickCount(Integer.valueOf(timeTickCount));
                            }  
                            
                            // chart grid line color
                            if (gridColorValueList.getLength() > 0) {
                                String gridLineColor = gridColorValueList.item(0).getTextContent();
                                chart.setGridLineColor(new Color(0xFFFFFF & Integer.valueOf(gridLineColor)));
                            }  
     
                            // chart line width
                            if (gridLineWidthList.getLength() > 0) {
                                String lineWidth = gridLineWidthList.item(0).getTextContent();
                                chart.setLineWidth(Integer.valueOf(lineWidth));
                            }       
                        }   
                    }
                } 
            }
            
            // yAxis list
            NodeList yAxisList = chartElement.getElementsByTagName("YAxes");
            if (yAxisList.getLength() > 0) {
                Node yAxisNode = yAxisList.item(0);
                if (yAxisNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element yAxisElement = (Element) yAxisNode;
                    
                    // serialized yAxis list
                    NodeList axisNodeList = yAxisElement.getElementsByTagName("ScopeYAxisSerializable");
                    for (int axisIndex = 0; axisIndex < axisNodeList.getLength(); axisIndex++) {
                        Node axisNode = axisNodeList.item(axisIndex);
                        Axis axis = getAxis(axisNode);

                        // chart add axis
                        chart.addAxis(axis);
                    }
                }
            }
        }

        return chart;
    }

    private TriggerChannel getTriggerChannel(Node triggerChannelNode, Scope scope) {
        TriggerChannel triggerChannel = new TriggerChannel();

        if (triggerChannelNode.getNodeType() == Node.ELEMENT_NODE) {
            Element triggerChannelElement = (Element) triggerChannelNode;

            NodeList thresholdList = triggerChannelElement.getElementsByTagName("Threshold");
            NodeList combineOptionList = triggerChannelElement.getElementsByTagName("CombineOption");
            NodeList releaseOptionList = triggerChannelElement.getElementsByTagName("ReleaseOption");
            NodeList connectedChannelHandleList = triggerChannelElement.getElementsByTagName("ConnectedChannelHandle");
            
            // trigger channel threshold
            if (thresholdList.getLength() > 0) {
                String threshold = thresholdList.item(0).getTextContent();
                triggerChannel.setThreshold(Double.valueOf(threshold));
            }    
            
            // trigger channel combine
            if (combineOptionList.getLength() > 0) {
                String combine = combineOptionList.item(0).getTextContent();
                triggerChannel.setCombine(Combine.valueOf(combine));
            }   
            
            // trigger channel release
            if (releaseOptionList.getLength() > 0) {
                String release = releaseOptionList.item(0).getTextContent();
                triggerChannel.setRelease(release.contains("RisingEdge") ? Release.RISING_EDGE : Release.FALLING_EDGE);
            }   
            
            // trigger channel handle
            if (connectedChannelHandleList.getLength() > 0) {
                String identHandle = connectedChannelHandleList.item(0).getTextContent();
                
                triggerChannelLocated: for (Chart chart : scope.getChartList()) {
                    for (Axis axis : chart.getAxisList()) {
                        for (Channel channel : axis.getChannelList()) {
                            if (channel.getIdentHandle() == Integer.valueOf(identHandle)) {
                                triggerChannel.setChannel(channel);
                                break triggerChannelLocated;
                            }
                        }
                    }
                }
            }
        }

        return triggerChannel;
    }

    private TriggerGroup getTriggerGroup(Node triggerGroupNode, Scope scope) {
        TriggerGroup triggerGroup = new TriggerGroup();

        if (triggerGroupNode.getNodeType() == Node.ELEMENT_NODE) {
            Element triggerGroupElement = (Element) triggerGroupNode;

            NodeList titleList = triggerGroupElement.getElementsByTagName("Title");
            NodeList triggerPrePercentList = triggerGroupElement.getElementsByTagName("TriggerPrePercent");
            NodeList enabledList = triggerGroupElement.getElementsByTagName("Enabled");
            
            // trigger group name
            if (titleList.getLength() > 0) {
                String triggerGroupName = titleList.item(0).getTextContent();
                triggerGroup.setTriggerGroupName(triggerGroupName);
            }   
           
            // trigger group offset
            if (triggerPrePercentList.getLength() > 0) {
                String triggerOffset = triggerPrePercentList.item(0).getTextContent();
                triggerGroup.setTriggerOffset(Integer.valueOf(triggerOffset));
            }  
            
            // trigger group enabled
            if (enabledList.getLength() > 0) {
                String enabled = enabledList.item(0).getTextContent();
                triggerGroup.setEnabled(Boolean.valueOf(enabled));
            }  

            // trigger set list
            NodeList triggerSetsList = triggerGroupElement.getElementsByTagName("TriggerSets");
            if (triggerSetsList.getLength() > 0) {
                Node triggerSetsNode = triggerSetsList.item(0);
                if (triggerSetsNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element triggerSetsElement = (Element) triggerSetsNode;

                    // serialized trigger list
                    NodeList triggerChannelList = triggerSetsElement.getElementsByTagName("ScopeTriggerSingleSetSerializable");
                    for (int triggerChannelIndex = 0; triggerChannelIndex < triggerChannelList.getLength(); triggerChannelIndex++) {
                        Node triggerChannelNode = triggerChannelList.item(triggerChannelIndex);
                        TriggerChannel triggerChannel = getTriggerChannel(triggerChannelNode, scope);

                        // scope add trigger channel
                        triggerGroup.addTriggerChannel(triggerChannel);
                    }
                }    
            }
        }

        return triggerGroup;
    }

    private Scope getScope(Node scopeNode) {
        Scope scope = new Scope();

        if (scopeNode.getNodeType() == Node.ELEMENT_NODE) {
            Element scopeElement = (Element) scopeNode;
            NodeList titleList = scopeElement.getElementsByTagName("Title");
            
            // scope name
            if (titleList.getLength() > 0) {
                String scopeName = titleList.item(0).getTextContent();
                scope.setScopeName(scopeName);
            }
            
            // operating list
            NodeList operationList = scopeElement.getElementsByTagName("Operating");
            if (operationList.getLength() > 0) {
                Node operationNode = operationList.item(0);
                if (operationNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element operationElement = (Element) operationNode;
                    NodeList recordTimeList = operationElement.getElementsByTagName("RecordTime");
                    
                    // scope record time
                    if (recordTimeList.getLength() > 0) {
                        String recordTimeText = recordTimeList.item(0).getTextContent();
                        long recordTime = Long.valueOf(recordTimeText) / 10000;
                        long recordTimeMax = Scope.timeFormaterToLong(Scope.TIME_FORMAT_MAX_TIME);
                        if (recordTime > recordTimeMax) recordTime = recordTimeMax;
                        scope.setRecordTime(recordTime);
                    }
                }         
            }
            
            // charts list
            NodeList chartsList = scopeElement.getElementsByTagName("Charts");
            if (chartsList.getLength() > 0) {
                Node chartsNode = chartsList.item(0);
                if (chartsNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element chartsElement = (Element) chartsNode;

                    // serialized chart list
                    NodeList chartNodeList = chartsElement.getElementsByTagName("ScopeChartSerializable");
                    for (int chartIndex = 0; chartIndex < chartNodeList.getLength(); chartIndex++) {
                        Node chartNode = chartNodeList.item(chartIndex);
                        Chart chart = getChart(chartNode);

                        // scope add chart
                        scope.addChart(chart);
                    }
                }
            }
            
            // trigger module list
            NodeList triggerModuleList = scopeElement.getElementsByTagName("TriggerModule");
            if (triggerModuleList.getLength() > 0) {
                Node triggerModuleNode = triggerModuleList.item(0);
                if (triggerModuleNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element triggerModuleElement = (Element) triggerModuleNode;

                    // trigger group node
                    NodeList triggerGroupsList = triggerModuleElement.getElementsByTagName("TriggerGroups");
                    if (triggerGroupsList.getLength() > 0) {
                        Node triggerGroupsNode = triggerGroupsList.item(0); 
                        if (triggerGroupsNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element triggerGroupsElement = (Element) triggerGroupsNode;

                            // serialized trigger group list
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
            }
        }

        return scope;
    }

    private Scope loadScopeFromScopeFile(String path) throws ParserConfigurationException, SAXException, IOException {
        // build document
        documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(new File(path));
        document.getDocumentElement().normalize();

        // serialized scope list
        NodeList scopeNodeList = document.getElementsByTagName("ScopeViewSerializable");
        
        // get scope
        if  (scopeNodeList.getLength() > 0) {
            Node scopeNode = scopeNodeList.item(0);
            
            return getScope(scopeNode);  
        } else {
            return null;
        }
    }
}
