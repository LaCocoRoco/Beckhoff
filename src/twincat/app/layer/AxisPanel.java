package twincat.app.layer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JScrollPane;

import twincat.TwincatLogger;
import twincat.ads.common.RouteSymbolData;
import twincat.ads.common.Symbol;
import twincat.ads.constant.AmsNetId;
import twincat.ads.constant.AmsPort;
import twincat.ads.constant.DataType;
import twincat.ads.worker.RouteSymbolLoader;
import twincat.ads.worker.SymbolLoader;
import twincat.app.common.AxisAcquisition;
import twincat.java.ScrollablePanel;
import twincat.java.WrapLayout;
import twincat.scope.Acquisition;
import twincat.scope.Axis;
import twincat.scope.Channel;
import twincat.scope.Chart;
import twincat.scope.Scope;
import twincat.scope.TriggerChannel;
import twincat.scope.TriggerGroup;

public class AxisPanel extends JScrollPane {
    private static final long serialVersionUID = 1L;

    /*********************************/
    /**** local constant variable ****/
    /*********************************/

    private static final int BUTTON_WIDTH = 150;
    
    private static final int BUTTON_HEIGHT = 60;
    
    private static final int MAX_TEXT_LENGTH = 40;
    
    private static final String HTML_PREPEND = "<html><div style='text-align: center;'>";

    private static final String HTML_APPEND = "</div></html>";
    
    /*********************************/
    /****** local final variable *****/
    /*********************************/

    private final RouteSymbolLoader routeSymbolLoader = new RouteSymbolLoader();

    private final Logger logger = TwincatLogger.getLogger();

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public AxisPanel(XReference xref) {
        ScrollablePanel scrollablePanel = new ScrollablePanel(new WrapLayout(FlowLayout.LEADING));
        scrollablePanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);

        routeSymbolLoader.loadRouteSymbolDataList(AmsPort.NC);
        routeSymbolLoader.loadRouteSymbolDataList(AmsPort.NCSAF);
        routeSymbolLoader.loadRouteSymbolDataList(AmsPort.NCSVB);

        for (RouteSymbolData routeSymbolData : routeSymbolLoader.getRouteSymbolDataList()) {
            String amsNetId = routeSymbolData.getRoute().getAmsNetId();
            AmsPort amsPort = routeSymbolData.getSymbolLoader().getAmsPort();

            SymbolLoader symbolLoader = routeSymbolData.getSymbolLoader();

            List<String> axisNameList = new ArrayList<String>();
            List<Symbol> routeSymbolList = symbolLoader.getSymbolList();
            List<AxisAcquisition> axisAcquisitionList = new ArrayList<AxisAcquisition>();
            for (Symbol symbol : routeSymbolList) {
                String symbolName = symbol.getSymbolName();

                int rangeBeg = symbolName.indexOf(".");
                int rangeEnd = symbolName.lastIndexOf(".");

                String axisName = symbolName.substring(rangeBeg + 1, rangeEnd);
                String axisSymbolName = symbolName.substring(0, rangeEnd);

                if (!axisNameList.contains(axisName)) {
                    AxisAcquisition axisAcquisition = new AxisAcquisition();
                    axisAcquisition.setAmsNetId(amsNetId);
                    axisAcquisition.setAmsPort(amsPort);
                    axisAcquisition.setAxisName(axisName);
                    axisAcquisition.setAxisSymbolName(axisSymbolName);
                    axisAcquisitionList.add(axisAcquisition);
                    axisNameList.add(axisName);
                }
            }

            
            for (AxisAcquisition axisAcquisition : axisAcquisitionList) {
                JButton axisButton = new JButton();
                
                String axisName = axisAcquisition.getAxisName();
                StringBuilder stringBuilder = new StringBuilder(axisName);

                if (stringBuilder.length() > MAX_TEXT_LENGTH)  {
                    stringBuilder.delete(MAX_TEXT_LENGTH, stringBuilder.length());
                }
                
                stringBuilder.insert(0, HTML_PREPEND);
                stringBuilder.append(HTML_APPEND);  
 
                axisButton.setText(stringBuilder.toString());
                axisButton.setFocusPainted(false);
                axisButton.setContentAreaFilled(false);
                axisButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

                axisButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        buildAxisScope(axisAcquisition);
                    }
                });

                scrollablePanel.add(axisButton);
            }
        }

        this.setViewportView(scrollablePanel);
    }

    /*********************************/
    /******** private method *********/
    /*********************************/

    public void buildAxisScope(AxisAcquisition axisAcquisition) {
        logger.info(axisAcquisition.getAmsNetId());
        logger.info(axisAcquisition.getAmsPort().toString());
        logger.info(axisAcquisition.getAxisName());
        logger.info(axisAcquisition.getAxisSymbolName());

        // scope
        Scope scope = new Scope();
        scope.setRecordTime("00:00:00.000");

        // chart
        Chart chart = new Chart(20);
        chart.setDisplayTime(500);
        chart.setDebug(true);

        // axis 1
        Axis axis1 = new Axis();
        axis1.setAxisColor(Color.RED);
        axis1.setAutoscale(false);
        axis1.setValueMax(120);
        axis1.setValueMin(-30);

        // axis 2
        Axis axis2 = new Axis();
        axis2.setAxisColor(Color.BLUE);
        axis2.setAutoscale(false);
        axis2.setValueMax(300);
        axis2.setValueMin(-450);

        // axis 3
        Axis axis3 = new Axis();
        axis3.setAxisColor(Color.BLACK);
        axis3.setAutoscale(false);
        axis3.setValueMax(225);
        axis3.setValueMin(-275);

        // axis 4
        Axis axis4 = new Axis();
        axis4.setAxisColor(Color.ORANGE);
        axis4.setAutoscale(false);
        axis4.setValueMax(750);
        axis4.setValueMin(-750);

        // acquisition 1
        Acquisition acquisition1 = new Acquisition();
        acquisition1.setTaskTime(1);
        acquisition1.setSymbolBased(true);
        acquisition1.setSymbolName("Main.lr_channel_1");
        acquisition1.setDataType(DataType.REAL64);
        acquisition1.setAmsNetId(AmsNetId.LOCAL);
        acquisition1.setAmsPort(AmsPort.TC2PLC1);

        // acquisition 2
        Acquisition acquisition2 = new Acquisition();
        acquisition2.setTaskTime(1);
        acquisition2.setSymbolBased(true);
        acquisition2.setSymbolName("Main.lr_channel_2");
        acquisition2.setDataType(DataType.REAL64);
        acquisition2.setAmsNetId(AmsNetId.LOCAL);
        acquisition2.setAmsPort(AmsPort.TC2PLC1);

        // acquisition 3
        Acquisition acquisition3 = new Acquisition();
        acquisition3.setTaskTime(1);
        acquisition3.setSymbolBased(true);
        acquisition3.setSymbolName("Main.lr_channel_3");
        acquisition3.setDataType(DataType.REAL64);
        acquisition3.setAmsNetId(AmsNetId.LOCAL);
        acquisition3.setAmsPort(AmsPort.TC2PLC1);

        // acquisition 4
        Acquisition acquisition4 = new Acquisition();
        acquisition4.setTaskTime(1);
        acquisition4.setSymbolBased(true);
        acquisition4.setSymbolName("Main.lr_channel_4");
        acquisition4.setDataType(DataType.REAL64);
        acquisition4.setAmsNetId(AmsNetId.LOCAL);
        acquisition4.setAmsPort(AmsPort.TC2PLC1);

        // acquisition 5
        Acquisition acquisition5 = new Acquisition();
        acquisition5.setTaskTime(1);
        acquisition5.setSymbolBased(true);
        acquisition5.setSymbolName("Main.lr_channel_5");
        acquisition5.setDataType(DataType.REAL64);
        acquisition5.setAmsNetId(AmsNetId.LOCAL);
        acquisition5.setAmsPort(AmsPort.TC2PLC1);

        // acquisition 6
        Acquisition acquisition6 = new Acquisition();
        acquisition6.setTaskTime(1);
        acquisition6.setSymbolBased(true);
        acquisition6.setSymbolName("Main.lr_channel_6");
        acquisition6.setDataType(DataType.REAL64);
        acquisition6.setAmsNetId(AmsNetId.LOCAL);
        acquisition6.setAmsPort(AmsPort.TC2PLC1);

        // acquisition 7
        Acquisition acquisition7 = new Acquisition();
        acquisition7.setTaskTime(1);
        acquisition7.setSymbolBased(true);
        acquisition7.setSymbolName("Main.lr_channel_7");
        acquisition7.setDataType(DataType.REAL64);
        acquisition7.setAmsNetId(AmsNetId.LOCAL);
        acquisition7.setAmsPort(AmsPort.TC2PLC1);

        // acquisition 8
        Acquisition acquisition8 = new Acquisition();
        acquisition8.setTaskTime(1);
        acquisition8.setSymbolBased(true);
        acquisition8.setSymbolName("Main.lr_channel_8");
        acquisition8.setDataType(DataType.REAL64);
        acquisition8.setAmsNetId(AmsNetId.LOCAL);
        acquisition8.setAmsPort(AmsPort.TC2PLC1);

        // channel 1
        Channel channel1 = new Channel();
        channel1.setAcquisition(acquisition1);
        channel1.setChannelName("Channel1");
        channel1.setChannelVisible(true);
        channel1.setAntialias(true);
        channel1.setLineVisible(true);
        channel1.setLineColor(Color.RED);
        channel1.setLineWidth(1);
        channel1.setPlotVisible(true);
        channel1.setPlotColor(Color.RED);
        channel1.setPlotSize(4);
        channel1.setWatchdogEnabled(true);

        // channel 2
        Channel channel2 = new Channel();
        channel2.setAcquisition(acquisition2);
        channel2.setChannelName("Channel2");
        channel2.setChannelVisible(true);
        channel2.setAntialias(true);
        channel2.setLineVisible(true);
        channel2.setLineColor(Color.BLACK);
        channel2.setLineWidth(1);
        channel2.setPlotVisible(true);
        channel2.setPlotColor(Color.BLACK);
        channel2.setPlotSize(4);
        channel2.setWatchdogEnabled(true);

        // channel 3
        Channel channel3 = new Channel();
        channel3.setAcquisition(acquisition3);
        channel3.setChannelName("Channel3");
        channel3.setChannelVisible(true);
        channel3.setAntialias(true);
        channel3.setLineVisible(true);
        channel3.setLineColor(Color.BLUE);
        channel3.setLineWidth(1);
        channel3.setPlotVisible(true);
        channel3.setPlotColor(Color.BLUE);
        channel3.setPlotSize(4);
        channel3.setWatchdogEnabled(true);

        // channel 4
        Channel channel4 = new Channel();
        channel4.setAcquisition(acquisition4);
        channel4.setChannelName("Channel4");
        channel4.setChannelVisible(true);
        channel4.setAntialias(true);
        channel4.setLineVisible(true);
        channel4.setLineColor(Color.ORANGE);
        channel4.setLineWidth(1);
        channel4.setPlotVisible(true);
        channel4.setPlotColor(Color.ORANGE);
        channel4.setPlotSize(4);
        channel4.setWatchdogEnabled(true);

        // channel 5
        Channel channel5 = new Channel();
        channel5.setAcquisition(acquisition5);
        channel5.setChannelName("Channel5");
        channel5.setChannelVisible(true);
        channel5.setAntialias(false);
        channel5.setLineVisible(true);
        channel5.setLineColor(Color.GREEN);
        channel5.setLineWidth(1);
        channel5.setPlotVisible(true);
        channel5.setPlotColor(Color.GREEN);
        channel5.setPlotSize(4);
        channel5.setWatchdogEnabled(true);

        // channel 6
        Channel channel6 = new Channel();
        channel6.setAcquisition(acquisition6);
        channel6.setChannelName("Channel6");
        channel2.setChannelVisible(true);
        channel6.setAntialias(false);
        channel6.setLineVisible(true);
        channel6.setLineColor(Color.GRAY);
        channel6.setLineWidth(1);
        channel6.setPlotVisible(true);
        channel6.setPlotColor(Color.GRAY);
        channel6.setPlotSize(4);
        channel6.setWatchdogEnabled(true);

        // channel 7
        Channel channel7 = new Channel();
        channel7.setAcquisition(acquisition7);
        channel7.setChannelName("Channel7");
        channel7.setChannelVisible(true);
        channel7.setAntialias(false);
        channel7.setLineVisible(true);
        channel7.setLineColor(Color.MAGENTA);
        channel7.setLineWidth(1);
        channel7.setPlotVisible(true);
        channel7.setPlotColor(Color.MAGENTA);
        channel7.setPlotSize(4);
        channel7.setWatchdogEnabled(true);

        // channel 8
        Channel channel8 = new Channel();
        channel8.setAcquisition(acquisition8);
        channel8.setChannelName("Channel8");
        channel8.setChannelVisible(true);
        channel8.setAntialias(false);
        channel8.setLineVisible(true);
        channel8.setLineColor(Color.WHITE);
        channel8.setLineWidth(1);
        channel8.setPlotVisible(true);
        channel8.setPlotColor(Color.WHITE);
        channel8.setPlotSize(4);
        channel8.setWatchdogEnabled(true);

        // trigger channel
        TriggerChannel triggerChannel = new TriggerChannel(channel1);
        triggerChannel.setThreshold(0);
        triggerChannel.setCombine(TriggerChannel.Combine.AND);
        triggerChannel.setRelease(TriggerChannel.Release.RISING_EDGE);

        // trigger group
        TriggerGroup triggerGroup = new TriggerGroup();
        triggerGroup.setTriggerOffset(0);
        triggerGroup.setEnabled(true);
        triggerGroup.addTrigger(triggerChannel);

        // scope add chart
        scope.addChart(chart);

        // chart add trigger group
        chart.addTrigger(triggerGroup);

        // chart add axis
        chart.addAxis(axis1);
        chart.addAxis(axis2);
        chart.addAxis(axis3);
        chart.addAxis(axis4);

        // axis add channel
        axis1.addChannel(channel1);
        axis1.addChannel(channel2);
        axis2.addChannel(channel3);
        axis2.addChannel(channel4);
        axis3.addChannel(channel5);
        axis3.addChannel(channel6);
        axis4.addChannel(channel7);
        axis4.addChannel(channel8);
    }
}