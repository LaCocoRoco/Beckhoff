package twincat;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

public final class TwincatLogger {
    /*********************************/
    /*** global constant variable ****/
    /*********************************/

    public static final Level LOGGER_DEFAULT_LEVEL = Level.INFO;   

    public static final String LOGGER_NAME = "TwincatLogger";

    public static final String LOGGER_FORMATTER = "[%1$tT] %2$s %n";

    public static final String LOGGER_FILE_NAME = "AppLogg.txt";

    /*********************************/
    /** public static final method ***/
    /*********************************/

    public static final void setLevel(Level level) {
        Logger logger = TwincatLogger.getLogger();
        logger.setLevel(level);
        logger.getHandlers()[0].setLevel(level);
    }
    
    public static final SimpleFormatter getFormatter() {
        SimpleFormatter consoleFormatter = new SimpleFormatter() {
            @Override
            public synchronized String format(LogRecord logRecord) {
                Date time = new Date(logRecord.getMillis());
                StringBuilder stringBuilder = new StringBuilder();

                // add class name to all none info messages
                if (logRecord.getLevel().intValue() != Level.INFO.intValue()) {
                    String sourceName = logRecord.getSourceClassName();
                    String className = sourceName.substring(sourceName.lastIndexOf('.') + 1);
                    stringBuilder.append(className +" : "); 
                }
                
                // Level "INFO" is only intended for general logging purpose
                stringBuilder.append(logRecord.getMessage());
                return String.format(LOGGER_FORMATTER, time, stringBuilder.toString());
            }
        };

        return consoleFormatter;
    }

    private static final void addConsoleLogger() {
        Logger logger = TwincatLogger.getLogger();

        SimpleFormatter consoleFormatter = TwincatLogger.getFormatter();
        StreamHandler consoleHandler = new StreamHandler(System.out, consoleFormatter) {
            @Override
            public synchronized void publish(LogRecord record) {
                super.publish(record);
                super.flush();
            }
        };
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);
    }
    
    public static final void addFileLogger() {
        Logger logger = TwincatLogger.getLogger();

        try {
            SimpleFormatter twincatFormatter = TwincatLogger.getFormatter();
            FileHandler fileHandler = new FileHandler(LOGGER_FILE_NAME, true);
            fileHandler.setFormatter(twincatFormatter);
            fileHandler.setLevel(Level.ALL);
            logger.addHandler(fileHandler);
        } catch (SecurityException e) {
            logger.severe(e.getMessage());
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }

    public static final Logger getLogger() {
        Enumeration<String> loggerNameList = LogManager.getLogManager().getLoggerNames();

        // get logger if already initialized
        while (loggerNameList.hasMoreElements()) {
            String loggerName = loggerNameList.nextElement();

            if (loggerName.equals(LOGGER_NAME)) {
                return Logger.getLogger(LOGGER_NAME);
            }
        }

        // initialize logger
        Logger logger = Logger.getLogger(LOGGER_NAME);
        logger.setUseParentHandlers(false);
        logger.setLevel(LOGGER_DEFAULT_LEVEL);
        
        // add console logger
        TwincatLogger.addConsoleLogger();

        return logger;
    }
}
