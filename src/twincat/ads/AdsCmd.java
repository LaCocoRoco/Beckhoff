package twincat.ads;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import twincat.TwincatLogger;
import twincat.ads.enums.AdsError;
import twincat.ads.enums.AmsPort;
import twincat.ads.wrapper.Variable;

public class AdsCmd {
    /*************************/
    /*** private constant ****/
    /*************************/

    private static final String COMMAND_ADS     = "ads";

    private static final String COMMAND_INFO    = "info";

    private static final String COMMAND_STATE   = "state";

    private static final String COMMAND_NET_ID  = "netid";
    
    private static final String COMMAND_SYMBOL  = "symbol";

    private static final String COMMAND_READ    = "read";

    private static final String COMMAND_WRITE   = "write";

    private static final String COMMAND_PORT    = "port";

    private static final String PARAMETER_LOCAL = "local";

    private static final String PARAMETER_PING  = "ping";

    private static final String PARAMETER_LIST  = "list";
    
    private static final String PARAMETER_INFO  = "info";

    private static final String CMD_PATTERN     = "\\s+";
    
    private static final int CMD_ADS_TIMEOUT    = 10;
    
    /*************************/
    /*** local attributes ****/
    /*************************/

    private long scheduleTime = 0;

    private final AdsClient adsClient = new AdsClient();

    private final Logger logger = TwincatLogger.getSignedLogger();

    /*************************/
    /********* public ********/
    /*************************/

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
                		logger.severe("Exception: " + stringWriter.toString());
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

    /*************************/
    /******** private ********/
    /*************************/

    private void cmdParser(String instruction) {
        String[] data = instruction.split(AdsCmd.CMD_PATTERN);

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

    private void cmdHandler(String par1) {
        switch (par1) {
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

    private void cmdHandler(String par1, String par2) {
        switch (par1) {
            case COMMAND_PORT:
                cmdPort(par2);
                break;

            case COMMAND_NET_ID:
                cmdNetId(par2);
                break;

            case COMMAND_SYMBOL:
                cmdSymbol(par2);
                break;

            case COMMAND_READ:
                cmdRead(par2);
                break;
        }
    }      

    private void cmdHandler(String par1, String par2, String par3) {
        switch (par1) {
            case COMMAND_SYMBOL:
                cmdSymbol(par2, par3);
                break;

            case COMMAND_WRITE:
                cmdWrite(par2, par3);
                break;
        }
    }      
      
    private void cmdHandler(String par1, String par2, String par3, String par4) {
        switch (par1) {
            case COMMAND_READ:
                cmdRead(par2, par3, par4);
                break;
        }
    }      
    
    private void cmdNetId(String par1) {
        switch (par1) {
            case PARAMETER_LOCAL:
                cmdNetIdLocal();
                break;
                
            case PARAMETER_LIST:
                cmdNetIdList();
                break;
                
            default:
                cmdNetIdAddress(par1);
                break;
        }
    }
   
    private void cmdPort(String par1) {
        switch (par1) {
            case PARAMETER_LIST:
                cmdPortList();
                break;

            case PARAMETER_PING:
                cmdPortPing();
                break;

            default:
                cmdPortValue(par1);
                break;
        }
    }
    
    private void cmdSymbol(String par1) {
        switch (par1) {
            default:
                cmdSymbolList();
                break;
        }
    }   
    
    private void cmdSymbol(String par1, String par2) {
        switch (par1) {
            case PARAMETER_LIST:
                cmdSymbolList(par2);
                break;

            case PARAMETER_INFO:
                cmdSymbolInfo(par2);
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
            
            AdsDeviceInfo deviceInfo = adsClient.readDeviceInfo();
            logger.info("Name : " + deviceInfo.getDeviceName());
            logger.info("Build: " + deviceInfo.getBuildVersion());
            logger.info("Major: " + deviceInfo.getMajorVersion());
            logger.info("Minor: " + deviceInfo.getMinorVersion());
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        } finally {
            adsClient.close();
        }
    }

    private void cmdState() {
        try {
            adsClient.open();
            adsClient.setTimeout(CMD_ADS_TIMEOUT);
            
            AdsDeviceState deviceState = adsClient.readDeviceState();
            logger.info("AdsState   : " + deviceState.getAdsState());
            logger.info("DeviceState: " + deviceState.getDevState());
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
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
            logger.info(e.getAdsErrorMessage());
        } finally {
            adsClient.close();
        }
    }

    private void cmdNetIdList() {
        try {
            adsClient.open();
            adsClient.setTimeout(CMD_ADS_TIMEOUT);
            
            String amsNetId = adsClient.readLocalAmsNetId();
            String hostName = adsClient.readLocalHostName();
    
            AdsRoute localRoute = new AdsRoute();
            localRoute.setAmsNetId(amsNetId);
            localRoute.setHostName(hostName);
            
            List<AdsRoute> remoteRouteList = adsClient.readRouteEntrys();
            
            List<AdsRoute> routeList = new ArrayList<AdsRoute>();
            routeList.add(localRoute);
            routeList.addAll(remoteRouteList);
            
            for (AdsRoute route : routeList) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("AmsNetId: ");
                stringBuilder.append(route.getAmsNetId());
                stringBuilder.append("\t| ");
                stringBuilder.append("HostName: ");
                stringBuilder.append(route.getHostName());
                logger.info(stringBuilder.toString());      
            }
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        } finally {
            adsClient.close();
        }
    } 
    
    private void cmdNetIdAddress(String par1) {
        try {
            adsClient.setAmsNetId(par1);
            logger.info(par1);
        } catch (IllegalArgumentException e) {
            logger.info("AmsNetId Wrong Pattern");
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
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
            logger.info(e.getAdsErrorMessage());
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
        
        try  {
            amsPort = AmsPort.getByValue(Integer.parseInt(value));
        } catch(NumberFormatException e) {}    
        
        if (!amsPort.equals(AmsPort.UNKNOWN)) {
            adsClient.setAmsPort(amsPort);
            logger.info(amsPort.toString());
        }
    }

    private void cmdSymbolList() {
        try {      
            adsClient.setTimeout(CMD_ADS_TIMEOUT);

            AdsSymbolLoader symbolLoader = new AdsSymbolLoader(adsClient);   
            List<AdsSymbol> symbolList = new ArrayList<AdsSymbol>();

            symbolList.addAll(symbolLoader.getSymbols());
            
            for (AdsSymbol symbol : symbolList) {
                String name = symbol.getName();
                String type = String.format("%-8s", symbol.getDataType().toString());
                logger.info("Type: " + type + "| Name: " + name);        
            } 
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }    
    }
    
    private void cmdSymbolList(String symbolName) {
        try {      
            adsClient.setTimeout(CMD_ADS_TIMEOUT);
            
            AdsSymbolLoader symbolLoader = new AdsSymbolLoader(adsClient);   
            List<AdsSymbol> symbolList = new ArrayList<AdsSymbol>();
            
            symbolList.addAll(symbolLoader.getSymbolsBySymbolName(symbolName));
            
            for (AdsSymbol symbol : symbolList) {
                String name = symbol.getName();
                String type = String.format("%-8s", symbol.getDataType().toString());
                logger.info("Type: " + type + "| Name: " + name);        
            } 
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }
    }

    private void cmdSymbolInfo(String symbolNanme) {
        try {
            adsClient.open();
            adsClient.setTimeout(CMD_ADS_TIMEOUT);
            
            AdsSymbolInfo symbolInfo = adsClient.readSymbolInfoBySymbolName(symbolNanme);
            logger.info("Name:        " + symbolInfo.getSymbolName());
            logger.info("DataType:    " + symbolInfo.getDataType());
            logger.info("DataSize:    " + symbolInfo.getDataSize());
            logger.info("IndexGroup:  " + symbolInfo.getIndexGroup());
            logger.info("IndexOffset: " + symbolInfo.getIndexOffset());
            logger.info("Flags:       " + symbolInfo.getSymbolFlag());
            logger.info("Comment:     " + symbolInfo.getComment());
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
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
            logger.info(e.getAdsErrorMessage());
        } finally {
            adsClient.close();
        }
    }

    private void cmdRead(String idxGrp, String idxOffs, String size) {
        try {
            adsClient.open();
            adsClient.setTimeout(CMD_ADS_TIMEOUT);
            
            try  {
                byte[] readBuffer = new byte[Integer.parseInt(size)];
                adsClient.read(Integer.parseInt(idxGrp), Integer.parseInt(idxOffs), readBuffer);
                
                logger.info(new String(readBuffer, "UTF-8"));
            } catch (NumberFormatException e) {
                logger.info("NumberFormatException");
            } catch (UnsupportedEncodingException e) {
                logger.info("UnsupportedEncodingException");
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
            logger.info(e.getAdsErrorMessage());
        } finally {
            adsClient.close();
        }
    }
}
