package twincat.ads;

import java.util.Date;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

public final class AdsLogger {
    /*************************/
    /** constant attributes **/
    /*************************/     
    
    public static final String LOGGER_NAME = "AdsLogger";

    public static final String LOGGER_FORMATTER = "[%1$tT] %2$s %n";
    
    /*************************/
    /** public static final **/
    /*************************/    
    
    public static final Logger getLogger() {
        Enumeration<String> loggerNameList = LogManager.getLogManager().getLoggerNames();

        while (loggerNameList.hasMoreElements()) {
            String loggerName = loggerNameList.nextElement();
            
            if (loggerName.equals(LOGGER_NAME)) {
                return Logger.getLogger(LOGGER_NAME);
            }      
        }

        LogManager.getLogManager().reset();
        Logger.getGlobal().setLevel(Level.OFF);
        Logger logger = Logger.getLogger(LOGGER_NAME);

        SimpleFormatter simpleFormatter = new SimpleFormatter() {
            @Override
            public synchronized String format(LogRecord logRecord) {
                Date time = new Date(logRecord.getMillis());
                String message = logRecord.getMessage();
                return String.format(LOGGER_FORMATTER, time, message);
            }
        };

        StreamHandler streamHandler = new StreamHandler(System.out, simpleFormatter) {
            @Override
            public synchronized void publish(LogRecord record) {
                super.publish(record);
                super.flush();
            }
        };
        
        logger.addHandler(streamHandler);
        
        return logger;
    }

}
