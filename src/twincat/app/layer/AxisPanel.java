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
import twincat.ads.constant.AmsPort;
import twincat.ads.worker.RouteLoader;
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
    /******** cross reference ********/
    /*********************************/

    private final XReference xref;

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

    private final RouteLoader routeLoader = new RouteLoader();

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public AxisPanel(XReference xref) {
        this.xref = xref;
        
        ScrollablePanel scrollablePanel = new ScrollablePanel(new WrapLayout(FlowLayout.LEADING));
        scrollablePanel.setScrollableWidth(ScrollablePanel.ScrollableSizeHint.FIT);

        routeLoader.loadRouteSymbolDataList(AmsPort.NC);
        routeLoader.loadRouteSymbolDataList(AmsPort.NCSAF);
        routeLoader.loadRouteSymbolDataList(AmsPort.NCSVB);

        for (RouteSymbolData routeSymbolData : routeLoader.getRouteSymbolDataList()) {
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
                    // build axis acquisition
                    AxisAcquisition axisAcquisition = new AxisAcquisition();
                    axisAcquisition.setAmsNetId(amsNetId);
                    axisAcquisition.setAmsPort(amsPort);
                    axisAcquisition.setAxisName(axisName);
                    axisAcquisition.setAxisSymbolName(axisSymbolName);
                    axisAcquisitionList.add(axisAcquisition);
                    axisNameList.add(axisName);
 
                    
                    // TODO : search for torque symbol
                    // TODO : symbolLoader.findRouteBySymbolName();
                    // TODO : make special symbol port parse list
          
                    
                    // build axis button
                    JButton axisButton = new JButton();
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
        }

        this.setViewportView(scrollablePanel);
    }

    /*********************************/
    /******** private method *********/
    /*********************************/

    public void buildAxisScope(AxisAcquisition axisAcquisition) {
        // scope
        Scope scope = new Scope();
        scope.setScopeName(axisAcquisition.getAxisName());
        scope.setRecordTime("00:00:00.000");

        // chart
        Chart chart = new Chart(20);
        chart.setDisplayTime(500);
        chart.setDebug(true);

        // add chart
        scope.addChart(chart);

        // axis VELO
        Axis axisVelo = new Axis();
        axisVelo.setAxisName("Velo");

        // acquisition ACTVELO
        Acquisition acquisitionActVelo = new Acquisition();
        acquisitionActVelo.setSampleTime(1);
        acquisitionActVelo.setSymbolBased(true);
        acquisitionActVelo.setSymbolName(axisAcquisition.getAxisSymbolName() + ".ACTVELO");
        acquisitionActVelo.setAmsNetId(axisAcquisition.getAmsNetId());
        acquisitionActVelo.setAmsPort(axisAcquisition.getAmsPort());
        
        // channel ACTVELO
        Channel channelActVelo = new Channel();
        channelActVelo.setAcquisition(acquisitionActVelo);
        channelActVelo.setChannelName("ACTVELO");
        channelActVelo.setLineColor(Color.RED);

        // acquisition SETVELO
        Acquisition acquisitionSetVelo = new Acquisition();
        acquisitionSetVelo.setSampleTime(1);
        acquisitionSetVelo.setSymbolBased(true);
        acquisitionSetVelo.setSymbolName(axisAcquisition.getAxisSymbolName() + ".SETVELO");
        acquisitionSetVelo.setAmsNetId(axisAcquisition.getAmsNetId());
        acquisitionSetVelo.setAmsPort(axisAcquisition.getAmsPort());
        
        // channel SETVELO
        Channel channelSetVelo = new Channel();
        channelSetVelo.setAcquisition(acquisitionSetVelo);
        channelSetVelo.setChannelName("SETVELO");
        channelSetVelo.setLineColor(Color.GREEN);

        // add VELO to chart
        chart.addAxis(axisVelo);
        axisVelo.addChannel(channelActVelo);
        axisVelo.addChannel(channelSetVelo); 
        
        // axis POS
        Axis axisPosition = new Axis();
        axisPosition.setAxisName("Pos");

        // acquisition ACTPOS
        Acquisition acquisitionActPos = new Acquisition();
        acquisitionActPos.setSampleTime(1);
        acquisitionActPos.setSymbolBased(true);
        acquisitionActPos.setSymbolName(axisAcquisition.getAxisSymbolName() + ".ACTPOS");
        acquisitionActPos.setAmsNetId(axisAcquisition.getAmsNetId());
        acquisitionActPos.setAmsPort(axisAcquisition.getAmsPort());
        
        // channel ACTPOS
        Channel channelActPos = new Channel();
        channelActPos.setAcquisition(acquisitionActPos);
        channelActPos.setChannelName("ACTPOS");
        channelActPos.setLineColor(Color.ORANGE);

        // acquisition SETPOS
        Acquisition acquisitionSetPos = new Acquisition();
        acquisitionSetPos.setSampleTime(1);
        acquisitionSetPos.setSymbolBased(true);
        acquisitionSetPos.setSymbolName(axisAcquisition.getAxisSymbolName() + ".SETPOS");
        acquisitionSetPos.setAmsNetId(axisAcquisition.getAmsNetId());
        acquisitionSetPos.setAmsPort(axisAcquisition.getAmsPort());
        
        // channel SETPOS
        Channel channelSetPos = new Channel();
        channelSetPos.setAcquisition(acquisitionSetPos);
        channelSetPos.setChannelName("SETPOS");
        channelSetPos.setLineColor(Color.CYAN);

        // add POS to chart
        chart.addAxis(axisPosition);
        axisPosition.addChannel(channelActPos);
        axisPosition.addChannel(channelSetPos); 
        
        // axis ACCEL
        Axis axisAcceleration = new Axis();
        axisAcceleration.setAxisName("Accel");

        // acquisition ACTACC
        Acquisition acquisitionActAcc = new Acquisition();
        acquisitionActAcc.setSampleTime(1);
        acquisitionActAcc.setSymbolBased(true);
        acquisitionActAcc.setSymbolName(axisAcquisition.getAxisSymbolName() + ".ACTACC");
        acquisitionActAcc.setAmsNetId(axisAcquisition.getAmsNetId());
        acquisitionActAcc.setAmsPort(axisAcquisition.getAmsPort());

        // channel ACTACC
        Channel channelActAcc = new Channel();
        channelActAcc.setAcquisition(acquisitionActAcc);
        channelActAcc.setChannelName("ACTACC");
        channelActAcc.setLineColor(Color.MAGENTA);

        // acquisition SETACC
        Acquisition acquisitionSetAcc = new Acquisition();
        acquisitionSetAcc.setSampleTime(1);
        acquisitionSetAcc.setSymbolBased(true);
        acquisitionSetAcc.setSymbolName(axisAcquisition.getAxisSymbolName() + ".SETACC");
        acquisitionSetAcc.setAmsNetId(axisAcquisition.getAmsNetId());
        acquisitionSetAcc.setAmsPort(axisAcquisition.getAmsPort());

        // channel SETACC
        Channel channelSetAcc = new Channel();
        channelSetAcc.setAcquisition(acquisitionSetAcc);
        channelSetAcc.setChannelName("SETACC");
        channelSetAcc.setLineColor(Color.BLACK);

        // add ACC to chart
        chart.addAxis(axisAcceleration);
        axisAcceleration.addChannel(channelActAcc);
        axisAcceleration.addChannel(channelSetAcc); 
        
        // axis POSDIFF
        Axis axisPosDiff = new Axis();
        axisPosDiff.setAxisName("PosDiff");

        // acquisition POSDIFF
        Acquisition acquisitionPosDiff = new Acquisition();
        acquisitionPosDiff.setSampleTime(1);
        acquisitionPosDiff.setSymbolBased(true);
        acquisitionPosDiff.setSymbolName(axisAcquisition.getAxisSymbolName() + ".POSDIFF");
        acquisitionPosDiff.setAmsNetId(axisAcquisition.getAmsNetId());
        acquisitionPosDiff.setAmsPort(axisAcquisition.getAmsPort());

        // channel POSDIFF
        Channel channelPosDiff = new Channel();
        channelPosDiff.setAcquisition(acquisitionPosDiff);
        channelPosDiff.setChannelName("POSDIFF");
        channelPosDiff.setLineColor(Color.BLUE);

        // add POS to chart
        chart.addAxis(axisPosDiff);
        axisPosDiff.addChannel(channelPosDiff);

        // trigger channel SETVELO
        TriggerChannel triggerChannelSetVelo = new TriggerChannel();
        triggerChannelSetVelo.setChannel(channelSetVelo);
        triggerChannelSetVelo.setThreshold(0.1);
        
        // trigger group
        TriggerGroup triggerGroup = new TriggerGroup();
        triggerGroup.addTriggerChannel(triggerChannelSetVelo);
        triggerGroup.setTriggerOffset(0);

        // add trigger group to chart
        chart.addTriggerGroup(triggerGroup);
 
        xref.scopeTree.addScope(scope);
    }
}