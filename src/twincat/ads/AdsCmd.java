package twincat.ads;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import twincat.ads.constants.AdsError;
import twincat.ads.constants.AmsPort;
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

    private static final String PARAMETER_EMPTY = "empty";

    private static final String CMD_PATTERN     = "\\s+";
    
    private static final int CMD_ADS_TIMEOUT    = 1000;
    
    /*************************/
    /*** local attributes ****/
    /*************************/

    private long scheduleTime = 0;

    private final Ads ads = new Ads();

    private final Logger logger = AdsLogger.getLogger();

    /*************************/
    /********* public ********/
    /*************************/

    public void send(String commando) {
        if (scheduleTime == 0) {
            scheduleTime = System.currentTimeMillis();

            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

            Runnable task = new Runnable() {
                public void run() {
                	try {
                		cmdDataHandler(commando);
                	} catch (Exception e) {
                		logger.warning("Error: " + e.getMessage());
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

    private void cmdDataHandler(String instruction) {
        String[] data = instruction.split(AdsCmd.CMD_PATTERN);

        switch (data.length) {
            case 1:
                cmdInstructionHandler(data[0], PARAMETER_EMPTY, PARAMETER_EMPTY);
                break;

            case 2:
                cmdInstructionHandler(data[0], data[1], PARAMETER_EMPTY);
                break;

            case 3:
                cmdInstructionHandler(data[0], data[1], data[2]);
                break;
        }
    }

    private void cmdInstructionHandler(String cmd, String par1, String par2) {
        switch (cmd) {
            case COMMAND_ADS:
                cmdAdsVersion();
                break;

            case COMMAND_INFO:
                cmdAdsDeviceInfo();
                break;

            case COMMAND_STATE:
                cmdAddsDeviceState();
                break;

            case COMMAND_NET_ID:
                cmdAdsNetId(par1);
                break;

            case COMMAND_PORT:
                cmdAmsPort(par1);
                break;

            case COMMAND_SYMBOL:
                cmdSymbol(par1);
                break;

            case COMMAND_READ:
                cmdRead(par1);
                break;

            case COMMAND_WRITE:
                cmdWrite(par1, par2);
                break;

            default:
                break;
        }
    }

    private void cmdAdsVersion() {
        logger.info("Version: " + ads.getVersion());
    }

    private void cmdAdsDeviceInfo() {
        try {
            ads.open();
            ads.setTimeout(CMD_ADS_TIMEOUT);
            
            AdsDeviceInfo deviceInfo = ads.readDeviceInfo();
            logger.info("Name : " + deviceInfo.getDeviceName());
            logger.info("Build: " + deviceInfo.getBuildVersion());
            logger.info("Major: " + deviceInfo.getMajorVersion());
            logger.info("Minor: " + deviceInfo.getMinorVersion());
        } catch (AdsException e) {
            logger.info("Error: " + e.getAdsErrorMessage());
        }

        try {
            ads.close();
        } catch (AdsException e) {
            logger.info("Error: " + e.getAdsErrorMessage());
        }
    }

    private void cmdAddsDeviceState() {
        try {
            ads.open();
            ads.setTimeout(CMD_ADS_TIMEOUT);
            
            AdsDeviceState deviceState = ads.readDeviceState();
            logger.info("AdsState   : " + deviceState.getAdsState());
            logger.info("DeviceState: " + deviceState.getDeviceState());
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }
        
        try {
            ads.close();
        } catch (AdsException e) {
            logger.info("Error: " + e.getAdsErrorMessage());
        }
    }

    private void cmdAdsNetId(String parameter) {
        switch (parameter) {
            case PARAMETER_EMPTY:
                cmdAdsNetIdGet();
                break;

            case PARAMETER_LOCAL:
                cmdAdsNetIdLocal();
                break;
                
            default:
                cmdAdsNetIdSet(parameter);
                break;
        }
    }

    private void cmdAdsNetIdGet() {
        String amsNetId = ads.getAmsNetId();
        logger.info(amsNetId);
    }
    
    private void cmdAdsNetIdLocal() {
        try {
            ads.open();
            ads.setTimeout(CMD_ADS_TIMEOUT);
            
            String amsNetId = ads.readAmsNetId();
            ads.setAmsNetId(amsNetId);
            logger.info(amsNetId);
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }
        
        try {
            ads.close();
        } catch (AdsException e) {
            logger.info("Error: " + e.getAdsErrorMessage());
        }
    }

    private void cmdAdsNetIdSet(String data) {
        try {
            ads.setAmsNetId(data);
            logger.info(data);
        } catch (IllegalArgumentException e) {
            logger.info("Net Id does not match pattern");
        }
    }

    private void cmdAmsPort(String parameter) {
        switch (parameter) {
            case PARAMETER_LIST:
                cmdAmsPortList();
                break;

            case PARAMETER_PING:
                cmdAmsPortPing();
                break;

            case PARAMETER_EMPTY:
                cmdAmsPortGet();
                break;

            default:
                cmdAmsPortSet(parameter);
                break;
        }
    }

    private void cmdAmsPortList() {
        for (AmsPort amsPort : AmsPort.values()) {
            if (!amsPort.equals(AmsPort.UNKNOWN)) {
                logger.info(amsPort.toString());
            }
        }
    }

    private void cmdAmsPortPing() {
        try {
            ads.open();
            ads.setTimeout(CMD_ADS_TIMEOUT);
            
            for (AmsPort amsPort : AmsPort.values()) {
                if (!amsPort.equals(AmsPort.UNKNOWN)) {
                    try {
                        ads.setAmsPort(amsPort);
                        ads.readDeviceInfo();
                        logger.info("IO : " + amsPort);
                    } catch (AdsException e) {
                        if (e.getAdsError().equals(AdsError.ADS_ADSPORT_CLOSED)) {
                            logger.info(e.getAdsErrorMessage());
                        } else {
                            logger.info("NIO: " + amsPort);
                        }
                    }
                }
            }
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }
        
        try {
            ads.close();
        } catch (AdsException e) {
            logger.info("Error: " + e.getAdsErrorMessage());
        }
    }

    private void cmdAmsPortGet() {
        logger.info(ads.getAmsPort().toString());
    }

    private void cmdAmsPortSet(String data) {
        AmsPort amsPort = AmsPort.getByString(data);
        
        if (!amsPort.equals(AmsPort.UNKNOWN)) {
            ads.setAmsPort(amsPort);
            logger.info(amsPort.toString());
        }
    }

    private void cmdSymbol(String parameter) {
        switch (parameter) {
            case PARAMETER_LIST:
                cmdSymbolNameList();
                break;

            default:
                cmdSymbolInfoByName(parameter);
                break;
        }
    }

    private void cmdSymbolNameList() {
        try {
            ads.open();
            ads.setTimeout(CMD_ADS_TIMEOUT);
            
            List<AdsSymbolInfo> symbolInfoList = ads.readSymbolInfoList();
            
            for (AdsSymbolInfo symbolInfo : symbolInfoList)
                logger.info(symbolInfo.getSymbolName());
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }

        try {
            ads.close();
        } catch (AdsException e) {
            logger.info("Error: " + e.getAdsErrorMessage());
        }
    }

    private void cmdSymbolInfoByName(String symbolNanme) {
        try {
            ads.open();
            ads.setTimeout(CMD_ADS_TIMEOUT);
            
            AdsSymbolInfo symbolInfo = ads.readSymbolInfoByName(symbolNanme);
            logger.info("Name:        " + symbolInfo.getSymbolName());
            logger.info("DataType:    " + symbolInfo.getDataType());
            logger.info("DataSize:    " + symbolInfo.getDataSize());
            logger.info("IndexGroup:  " + symbolInfo.getIndexGroup());
            logger.info("IndexOffset: " + symbolInfo.getIndexOffset());
            logger.info("Flags:       " + symbolInfo.getSymbolFlag());
            logger.info("Comment:     " + symbolInfo.getComment());
            
            Variable variable = ads.getVariableBySymbolName(symbolInfo.getSymbolName());
            logger.info("Value:       " + variable.read().toString());
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }

        try {
            ads.close();
        } catch (AdsException e) {
            logger.info("Error: " + e.getAdsErrorMessage());
        }
    }

    private void cmdRead(String symbolName) {
        try {
            ads.open();
            ads.setTimeout(CMD_ADS_TIMEOUT);
            
            Variable variable = ads.getVariableBySymbolName(symbolName);
            
            logger.info(variable.read().toString());
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }

        try {
            ads.close();
        } catch (AdsException e) {
            logger.info("Error: " + e.getAdsErrorMessage());
        }
    }

    private void cmdWrite(String symbolName, String value) {
        try {
            ads.open();
            ads.setTimeout(CMD_ADS_TIMEOUT);
            
            Variable variable = ads.getVariableBySymbolName(symbolName);
            
            logger.info(variable.write(value).read().toString());
        } catch (AdsException e) {
            logger.info(e.getAdsErrorMessage());
        }

        try {
            ads.close();
        } catch (AdsException e) {
            logger.info("Error: " + e.getAdsErrorMessage());
        }
    }
}
