package twincat.app.layer;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;

import twincat.TwincatLogger;
import twincat.Utilities;
import twincat.ads.common.RouteSymbolData;
import twincat.ads.common.Symbol;
import twincat.ads.constant.AmsPort;
import twincat.ads.worker.RouteLoader;
import twincat.ads.worker.SymbolLoader;
import twincat.app.common.AxisAcquisition;
import twincat.app.components.ScrollablePanel;
import twincat.app.components.WrapLayout;
import twincat.constant.DefaultColorTable;
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

    private final Logger logger = TwincatLogger.getLogger();

    /*********************************/
    /********** constructor **********/
    /*********************************/

    public AxisPanel(XReference xref) {
        this.xref = xref;
        
        Logger logger = TwincatLogger.getLogger();
        logger.fine("Load AxisPanel");

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

                    // build axis button
                    JButton axisButton = new JButton();
                    StringBuilder stringBuilder = new StringBuilder(axisName);

                    if (stringBuilder.length() > MAX_TEXT_LENGTH) {
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
                        public void actionPerformed(ActionEvent actionEvent) {

                            new SwingWorker<Void, Void>() {
                                @Override
                                protected Void doInBackground() throws Exception {
                                    try {
                                        buildAxisScope(axisAcquisition);
                                    } catch (Exception e) {
                                        logger.fine(Utilities.exceptionToString(e));
                                    }

                                    return null;
                                }
                            }.execute();
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

    private void buildAxisScope(AxisAcquisition axisAcquisition) {
        // scope
        Scope scope = new Scope();
        scope.setScopeName(axisAcquisition.getAxisName());

        // chart
        Chart chart = new Chart();
        chart.setDisplayTime(500);
        chart.setDebug(true);

        // add chart
        scope.addChart(chart);

        // axis VELO
        Axis axisVelo = new Axis();
        axisVelo.setAxisName("Velo");
        axisVelo.setAxisColor(DefaultColorTable.RED.color);
        axisVelo.setScaleSymetrical(true);

        // acquisition ACTVELO
        String actVeloSymbolName = axisAcquisition.getAxisSymbolName() + ".ACTVELO";
        logger.fine(actVeloSymbolName);

        Acquisition acquisitionActVelo = new Acquisition();
        acquisitionActVelo.setSymbolBased(true);
        acquisitionActVelo.setSymbolName(actVeloSymbolName);
        acquisitionActVelo.setAmsNetId(axisAcquisition.getAmsNetId());
        acquisitionActVelo.setAmsPort(axisAcquisition.getAmsPort());

        // channel ACTVELO
        Channel channelActVelo = new Channel();
        channelActVelo.setAcquisition(acquisitionActVelo);
        channelActVelo.setChannelName("ACTVELO");
        channelActVelo.setLineColor(DefaultColorTable.RED.color);
        channelActVelo.setPlotColor(DefaultColorTable.RED.color);

        // acquisition SETVELO
        String setVeloSymbolName = axisAcquisition.getAxisSymbolName() + ".SETVELO";
        logger.fine(setVeloSymbolName);

        Acquisition acquisitionSetVelo = new Acquisition();
        acquisitionSetVelo.setSymbolBased(true);
        acquisitionSetVelo.setSymbolName(setVeloSymbolName);
        acquisitionSetVelo.setAmsNetId(axisAcquisition.getAmsNetId());
        acquisitionSetVelo.setAmsPort(axisAcquisition.getAmsPort());

        // channel SETVELO
        Channel channelSetVelo = new Channel();
        channelSetVelo.setAcquisition(acquisitionSetVelo);
        channelSetVelo.setChannelName("SETVELO");
        channelSetVelo.setLineColor(DefaultColorTable.GREEN.color);
        channelSetVelo.setPlotColor(DefaultColorTable.GREEN.color);

        // add VELO to chart
        chart.addAxis(axisVelo);
        axisVelo.addChannel(channelActVelo);
        axisVelo.addChannel(channelSetVelo);

        // axis POS
        Axis axisPosition = new Axis();
        axisPosition.setAxisName("Pos");
        axisPosition.setAxisColor(DefaultColorTable.PURPLE.color);
        axisPosition.setAxisVisible(false);
        axisPosition.setAxisNameVisible(false);

        // acquisition ACTPOS
        String actPosSymbolName = axisAcquisition.getAxisSymbolName() + ".ACTPOS";
        logger.fine(actPosSymbolName);

        Acquisition acquisitionActPos = new Acquisition();
        acquisitionActPos.setSymbolBased(true);
        acquisitionActPos.setSymbolName(actPosSymbolName);
        acquisitionActPos.setAmsNetId(axisAcquisition.getAmsNetId());
        acquisitionActPos.setAmsPort(axisAcquisition.getAmsPort());

        // channel ACTPOS
        Channel channelActPos = new Channel();
        channelActPos.setAcquisition(acquisitionActPos);
        channelActPos.setChannelName("ACTPOS");
        channelActPos.setLineColor(DefaultColorTable.PURPLE.color);
        channelActPos.setPlotColor(DefaultColorTable.PURPLE.color);

        // acquisition SETPOS
        String setPosSymbolName = axisAcquisition.getAxisSymbolName() + ".SETPOS";
        logger.fine(setPosSymbolName);

        Acquisition acquisitionSetPos = new Acquisition();
        acquisitionSetPos.setSymbolBased(true);
        acquisitionSetPos.setSymbolName(setPosSymbolName);
        acquisitionSetPos.setAmsNetId(axisAcquisition.getAmsNetId());
        acquisitionSetPos.setAmsPort(axisAcquisition.getAmsPort());

        // channel SETPOS
        Channel channelSetPos = new Channel();
        channelSetPos.setAcquisition(acquisitionSetPos);
        channelSetPos.setChannelName("SETPOS");
        channelSetPos.setLineColor(DefaultColorTable.OLIVE.color);
        channelSetPos.setPlotColor(DefaultColorTable.OLIVE.color);

        // add POS to chart
        chart.addAxis(axisPosition);
        axisPosition.addChannel(channelActPos);
        axisPosition.addChannel(channelSetPos);

        // axis ACCEL
        Axis axisAcceleration = new Axis();
        axisAcceleration.setAxisName("Accel");
        axisAcceleration.setAxisColor(DefaultColorTable.ORANGE.color);
        
        axisAcceleration.setScaleSymetrical(true);

        // acquisition ACTACC
        String actAccSymbolName = axisAcquisition.getAxisSymbolName() + ".ACTACC";
        logger.fine(actAccSymbolName);

        Acquisition acquisitionActAcc = new Acquisition();
        acquisitionActAcc.setSymbolBased(true);
        acquisitionActAcc.setSymbolName(actAccSymbolName);
        acquisitionActAcc.setAmsNetId(axisAcquisition.getAmsNetId());
        acquisitionActAcc.setAmsPort(axisAcquisition.getAmsPort());

        // channel ACTACC
        Channel channelActAcc = new Channel();
        channelActAcc.setAcquisition(acquisitionActAcc);
        channelActAcc.setChannelName("ACTACC");
        channelActAcc.setLineColor(DefaultColorTable.ORANGE.color);
        channelActAcc.setPlotColor(DefaultColorTable.ORANGE.color);

        // acquisition SETACC
        String setAccSymbolName = axisAcquisition.getAxisSymbolName() + ".SETACC";
        logger.fine(setAccSymbolName);

        Acquisition acquisitionSetAcc = new Acquisition();
        acquisitionSetAcc.setSymbolBased(true);
        acquisitionSetAcc.setSymbolName(setAccSymbolName);
        acquisitionSetAcc.setAmsNetId(axisAcquisition.getAmsNetId());
        acquisitionSetAcc.setAmsPort(axisAcquisition.getAmsPort());

        // channel SETACC
        Channel channelSetAcc = new Channel();
        channelSetAcc.setAcquisition(acquisitionSetAcc);
        channelSetAcc.setChannelName("SETACC");
        channelSetAcc.setLineColor(DefaultColorTable.BLACK.color);
        channelSetAcc.setPlotColor(DefaultColorTable.BLACK.color);

        // add ACC to chart
        chart.addAxis(axisAcceleration);
        axisAcceleration.addChannel(channelActAcc);
        axisAcceleration.addChannel(channelSetAcc);

        // axis POSDIFF
        Axis axisPosDiff = new Axis();
        axisPosDiff.setAxisName("PosDiff");
        axisPosDiff.setAxisColor(DefaultColorTable.BLUE.color);
        axisPosDiff.setScaleSymetrical(true);

        // acquisition POSDIFF
        String posDiffSymbolName = axisAcquisition.getAxisSymbolName() + ".POSDIFF";
        logger.fine(posDiffSymbolName);

        Acquisition acquisitionPosDiff = new Acquisition();
        acquisitionPosDiff.setSymbolBased(true);
        acquisitionPosDiff.setSymbolName(posDiffSymbolName);
        acquisitionPosDiff.setAmsNetId(axisAcquisition.getAmsNetId());
        acquisitionPosDiff.setAmsPort(axisAcquisition.getAmsPort());

        // channel POSDIFF
        Channel channelPosDiff = new Channel();
        channelPosDiff.setAcquisition(acquisitionPosDiff);
        channelPosDiff.setChannelName("POSDIFF");
        channelPosDiff.setLineColor(DefaultColorTable.BLUE.color);
        channelPosDiff.setPlotColor(DefaultColorTable.BLUE.color);

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
        scope.addTriggerGroup(triggerGroup);

        // add scope
        xref.scopeBrowser.addScope(scope);
    }
}