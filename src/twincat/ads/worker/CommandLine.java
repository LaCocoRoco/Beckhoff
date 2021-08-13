package twincat.ads.worker;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import twincat.TwincatLogger;
import twincat.ads.AdsClient;
import twincat.ads.AdsException;
import twincat.ads.common.DeviceInfo;
import twincat.ads.common.DeviceState;
import twincat.ads.common.Route;
import twincat.ads.common.RouteSymbolData;
import twincat.ads.common.Symbol;
import twincat.ads.common.SymbolInfo;
import twincat.ads.common.Variable;
import twincat.ads.constant.AdsError;
import twincat.ads.constant.AmsNetId;
import twincat.ads.constant.AmsPort;

public class CommandLine {
    /*********************************/
    /**** local constant variable ****/
    /*********************************/

    private static final String COMMAND_ADS = "ads";

    private static final String COMMAND_INFO = "info";

    private static final String COMMAND_LOGGER = "logger";

    private static final String COMMAND_STATE = "state";

    private static final String COMMAND_NET_ID = "netid";

    private static final String COMMAND_SYMBOL = "symbol";

    private static final String COMMAND_READ = "read";

    private static final String COMMAND_WRITE = "write";

    private static final String COMMAND_PORT = "port";

    private static final String COMMAND_ALL = "all";

    private static final String COMMAND_LOCAL = "local";

    private static final String COMMAND_PING = "ping";

    private static final String COMMAND_LIST = "list";

    private static final String CMD_PATTERN = "\\s+";

    private static final int CMD_ADS_TIMEOUT = 10;

    /*********************************/
    /***** global final variable *****/
    /*********************************/
    
    private final AdsClient adsClient = new AdsClient();

    /*********************************/
    /******** local variable *********/
    /*********************************/

    private long scheduleTime = 0;

    /*********************************/
    /****** local final variable *****/
    /*********************************/

    private final Logger logger = TwincatLogger.getLogger();

    /*********************************/
    /******** setter & getter ********/
    /*********************************/
    
    public AdsClient getAdsClient() {
        return adsClient;
    }

    /*********************************/
    /********* public method *********/
    /*********************************/

    public void send(String commando) {
        logger.info("> " + commando);

        if (scheduleTime == 0) {
            scheduleTime = System.currentTimeMillis();

            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

            Runnable task = new Runnable() {
                public void run() {
                    try {
                        cmdParser(commando);
                    } catch (Exception e) {
                        StringWriter stringWriter = new StringWriter();
                        PrintWriter printWriter = new PrintWriter(stringWriter);
                        e.printStackTrace(printWriter);
                        logger.severe("Exception | " + stringWriter.toString());
                    } finally {
                        scheduleTime = 0;
                    }
                }
            };

            scheduler.schedule(task, 0, TimeUnit.MILLISECONDS);
        } else {
            long threadTime = System.currentTimeMillis() - scheduleTime;
            logger.info("Waiting for Respsonse : Time > " + threadTime);
        }
    }

    /*********************************/
    /******** private method *********/
    /*********************************/

    private void cmdParser(String instruction) {
        String[] data = instruction.split(CommandLine.CMD_PATTERN);

        switch (data.length) {
            case 1:
                cmdHandler(data[0]);
                break;

            case 2:
                cmdHandler(data[0], data[1]);
                break;

            case 3:
                cmdHandler(data[0], data[1], data[2]);
                break;

            case 4:
                cmdHandler(data[0], data[1], data[2], data[3]);
                break;
        }
    }

    private void cmdHandler(String cmd1) {
        switch (cmd1) {
            case COMMAND_ADS:
                cmdAds();
                break;

            case COMMAND_INFO:
                cmdInfo();
                break;

            case COMMAND_STATE:
                cmdState();
                break;

            case COMMAND_NET_ID:
                cmdNetId();
                break;

            case COMMAND_PORT:
                cmdPort();
                break;
        }
    }

    private void cmdHandler(String cmd1, String cmd2) {
        switch (cmd1) {
            case COMMAND_LOGGER:
                cmdLogger(cmd2);
                break;

            case COMMAND_PORT:
                cmdPort(cmd2);
                break;

            case COMMAND_NET_ID:
                cmdNetId(cmd2);
                break;

            case COMMAND_SYMBOL:
                cmdSymbol(cmd2);
                break;

            case COMMAND_READ:
                cmdRead(cmd2);
                break;
        }
    }

    private void cmdHandler(String cmd1, String cmd2, String cmd3) {
        switch (cmd1) {
            case COMMAND_SYMBOL:
                cmdSymbol(cmd2, cmd3);
                break;

            case COMMAND_WRITE:
                cmdWrite(cmd2, cmd3);
                break;
        }
    }

    private void cmdHandler(String cmd1, String cmd2, String cmd3, String par4) {
        switch (cmd1) {
            case COMMAND_READ:
                cmdRead(cmd2, cmd3, par4);
                break;
        }
    }

    private void cmdNetId(String cmd1) {
        switch (cmd1) {
            case COMMAND_LOCAL:
                cmdNetIdLocal();
                break;

            case COMMAND_LIST:
                cmdNetIdList();
                break;

            default:
                cmdNetIdAddress(cmd1);
                break;
        }
    }

    private void cmdPort(String cmd1) {
        switch (cmd1) {
            case COMMAND_LIST:
                cmdPortList();
                break;

            case COMMAND_PING:
                cmdPortPing();
                break;

            default:
                cmdPortValue(cmd1);
                break;
        }
    }

    private void cmdSymbol(String cmd1) {
        switch (cmd1) {
            default:
                cmdSymbolList();
                break;
        }
    }

    private void cmdSymbol(String cmd1, String cmd2) {
        switch (cmd1) {
            case COMMAND_LIST:
                cmdSymbolList(cmd2);
                break;

            case COMMAND_INFO:
                cmdSymbolInfo(cmd2);
                break;
        }
    }

    private void cmdSymbolList(String cmd1) {
        switch (cmd1) {
            case COMMAND_ALL:
                cmdSymbolListAll();
                break;

            default:
                cmdSymbolListName(cmd1);
                break;
        }
    }

    private void cmdAds() {
        logger.info("Version: " + adsClient.getVersion());
    }

    private void cmdInfo() {
        try {
            adsClient.open();
            adsClient.setTimeout(CMD_ADS_TIMEOUT);

            DeviceInfo deviceInfo = adsClient.readDeviceInfo();
            logger.info("Name : " + deviceInfo.getDeviceName());
            logger.info("Build: " + deviceInfo.getBuildVersion());
            logger.info("Major: " + deviceInfo.getMajorVersion());
            logger.info("Minor: " + deviceInfo.getMinorVersion());
        } catch (AdsException e) {
            logger.warning(e.getAdsErrorMessage());
        } finally {
            adsClient.close();
        }
    }

    private void cmdState() {
        try {
            adsClient.open();
            adsClient.setTimeout(CMD_ADS_TIMEOUT);

            DeviceState deviceState = adsClient.readDeviceState();
            logger.info("AdsState   : " + deviceState.getAdsState());
            logger.info("DeviceState: " + deviceState.getDevState());
        } catch (AdsException e) {
            logger.warning(e.getAdsErrorMessage());
        } finally {
            adsClient.close();
        }
    }

    private void cmdNetId() {
        String amsNetId = adsClient.getAmsNetId();
        logger.info(amsNetId);
    }

    private void cmdNetIdLocal() {
        try {
            adsClient.open();
            adsClient.setTimeout(CMD_ADS_TIMEOUT);

            String amsNetId = adsClient.readLocalAmsNetId();
            adsClient.setAmsNetId(amsNetId);

            logger.info(amsNetId);
        } catch (AdsException e) {
            logger.warning(e.getAdsErrorMessage());
        } finally {
            adsClient.close();
        }
    }

    private void cmdSymbolListAll() {  
        RouteLoader routeLoader = new RouteLoader();
        routeLoader.loadRouteSymbolDataList();

        for (RouteSymbolData routeSymbolData : routeLoader.getRouteSymbolDataList()) {
            String hostName = routeSymbolData.getRoute().getHostName();

            SymbolLoader symbolLoader = routeSymbolData.getSymbolLoader();
            String amsNetId = symbolLoader.getAmsNetId();
            AmsPort amsPort = symbolLoader.getAmsPort();
            int symbolListSize = symbolLoader.getSymbolList().size();

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("HostName: " + String.format("%-8s", hostName) + " | ");
            stringBuilder.append("AmsNetId: " + String.format("%-8s", amsNetId) + " | ");
            stringBuilder.append("AmsPort: " + String.format("%-8s", amsPort) + " | ");
            stringBuilder.append("SymbolSize: " + symbolListSize);

            logger.info(stringBuilder.toString());
        }
    }

    private void cmdNetIdList() {
        try {
            adsClient.open();
            adsClient.setAmsNetId(AmsNetId.LOCAL);
            adsClient.setAmsPort(AmsPort.SYSTEMSERVICE);
            adsClient.setTimeout(CMD_ADS_TIMEOUT);

            String amsNetId = adsClient.readLocalAmsNetId();
            String hostName = adsClient.readLocalHostName();

            Route localRoute = new Route();
            localRoute.setAmsNetId(amsNetId);
            localRoute.setHostName(hostName);

            List<Route> remoteRouteList = adsClient.readRouteEntrys();

            List<Route> routeList = new ArrayList<Route>();
            routeList.add(localRoute);
            routeList.addAll(remoteRouteList);

            for (Route route : routeList) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("AmsNetId: " + route.getAmsNetId());
                stringBuilder.append("\t| ");
                stringBuilder.append("HostName: " + route.getHostName());
                logger.info(stringBuilder.toString());
            }
        } catch (AdsException e) {
            logger.warning(e.getAdsErrorMessage());
        } finally {
            adsClient.close();
        }
    }

    private void cmdNetIdAddress(String amsNetId) {
        try {
            adsClient.setAmsNetId(amsNetId);
            logger.info(amsNetId);
        } catch (IllegalArgumentException e) {
            logger.warning("AmsNetId Wrong Pattern");
        }
    }

    private void cmdPort() {
        logger.info(adsClient.getAmsPort().toString());
    }

    private void cmdPortList() {
        for (AmsPort amsPort : AmsPort.values()) {
            if (!amsPort.equals(AmsPort.UNKNOWN)) {
                logger.info(amsPort.toString());
            }
        }
    }

    private void cmdPortPing() {
        try {
            adsClient.open();
            adsClient.setTimeout(CMD_ADS_TIMEOUT);

            AmsPort cachPort = adsClient.getAmsPort();

            int portCount = 0;
            for (AmsPort amsPort : AmsPort.values()) {
                if (!amsPort.equals(AmsPort.UNKNOWN)) {
                    try {
                        adsClient.setAmsPort(amsPort);
                        adsClient.readDeviceInfo();

                        portCount++;
                        logger.info(amsPort.toString());
                    } catch (AdsException e) {
                        if (e.getAdsError().equals(AdsError.ADS_ADSPORT_CLOSED)) {
                            logger.info(e.getAdsErrorMessage());
                        }
                    }
                }
            }

            logger.info("Detected: " + portCount);
            adsClient.setAmsPort(cachPort);
        } catch (AdsException e) {
            logger.warning(e.getAdsErrorMessage());
        } finally {
            adsClient.close();
        }
    }

    private void cmdPortValue(String value) {
        AmsPort amsPort = AmsPort.getByString(value);

        if (!amsPort.equals(AmsPort.UNKNOWN)) {
            adsClient.setAmsPort(amsPort);
            logger.info(amsPort.toString());
            return;
        }

        try {
            amsPort = AmsPort.getByValue(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            logger.warning("NumberFormatException");
            return;
        }

        if (!amsPort.equals(AmsPort.UNKNOWN)) {
            adsClient.setAmsPort(amsPort);
            logger.info(amsPort.toString());
        }
    }

    private void cmdSymbolList() {
        SymbolLoader symbolLoader = new SymbolLoader();
        symbolLoader.setAmsNetId(adsClient.getAmsNetId());
        symbolLoader.setAmsPort(adsClient.getAmsPort());
        symbolLoader.parseSymbolList();

        List<Symbol> symbolList = symbolLoader.getSymbolList();

        for (Symbol symbol : symbolList) {
            String name = symbol.getSymbolName();
            String type = String.format("%-8s", symbol.getDataType().toString());
            logger.info("Type: " + type + "| Name: " + name);
        }
    }

    private void cmdSymbolListName(String symbolName) {
        SymbolLoader symbolLoader = new SymbolLoader();
        symbolLoader.setAmsNetId(adsClient.getAmsNetId());
        symbolLoader.setAmsPort(adsClient.getAmsPort());
        symbolLoader.parseSymbolList();

        List<Symbol> symbolList = symbolLoader.getSymbolList(symbolName);

        for (Symbol symbol : symbolList) {
            String name = symbol.getSymbolName();
            String type = String.format("%-8s", symbol.getDataType().toString());
            logger.info("Type: " + type + "| Name: " + name);
        }
    }

    private void cmdSymbolInfo(String symbolNanme) {
        try {
            adsClient.open();
            adsClient.setTimeout(CMD_ADS_TIMEOUT);

            SymbolInfo symbolInfo = adsClient.readSymbolInfoBySymbolName(symbolNanme);
            logger.info("Name:        " + symbolInfo.getSymbolName());
            logger.info("DataType:    " + symbolInfo.getDataType());
            logger.info("DataSize:    " + symbolInfo.getDataSize());
            logger.info("IndexGroup:  " + symbolInfo.getIndexGroup());
            logger.info("IndexOffset: " + symbolInfo.getIndexOffset());
            logger.info("Flags:       " + symbolInfo.getSymbolFlag());
            logger.info("Comment:     " + symbolInfo.getComment());
        } catch (AdsException e) {
            logger.warning(e.getAdsErrorMessage());
        } finally {
            adsClient.close();
        }
    }

    private void cmdRead(String symbolName) {
        try {
            adsClient.open();
            adsClient.setTimeout(CMD_ADS_TIMEOUT);

            Variable variable = adsClient.getVariableBySymbolName(symbolName);

            if (variable != null) logger.info(variable.read().toString());
        } catch (AdsException e) {
            logger.warning(e.getAdsErrorMessage());
        } finally {
            adsClient.close();
        }
    }

    private void cmdRead(String idxGrp, String idxOffs, String size) {
        try {
            adsClient.open();
            adsClient.setTimeout(CMD_ADS_TIMEOUT);

            try {
                byte[] readBuffer = new byte[Integer.parseInt(size)];
                adsClient.read(Integer.parseInt(idxGrp), Integer.parseInt(idxOffs), readBuffer);

                logger.info(new String(readBuffer, "UTF-8"));
            } catch (NumberFormatException e) {
                logger.warning("NumberFormatException");
            } catch (UnsupportedEncodingException e) {
                logger.warning("UnsupportedEncodingException");
            }
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        } finally {
            adsClient.close();
        }
    }

    private void cmdWrite(String symbolName, String value) {
        try {
            adsClient.open();
            adsClient.setTimeout(CMD_ADS_TIMEOUT);

            Variable variable = adsClient.getVariableBySymbolName(symbolName);

            if (variable != null) logger.info(variable.write(value).read().toString());
        } catch (AdsException e) {
            logger.warning(e.getAdsErrorMessage());
        } finally {
            adsClient.close();
        }
    }

    private void cmdLogger(String level) {
        switch (level.toUpperCase()) {
            case "FINE":
                logger.setLevel(Level.FINE);
                break;

            case "INFO":
                logger.setLevel(Level.INFO);
                break;

            case "WARNING":
                logger.setLevel(Level.WARNING);
                break;

            case "SEVERE":
                logger.setLevel(Level.SEVERE);
                break;

            default:
                return;
        }

        logger.info("Level: " + level);
    }
}
