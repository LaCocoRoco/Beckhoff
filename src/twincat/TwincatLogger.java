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
    /*************************/
    /** constant attributes **/
    /*************************/

    public static final String LOGGER_NAME = "TwincatLogger";

    public static final String LOGGER_FORMATTER = "[%1$tT] %2$s %n";

    public static final String LOGGER_FILE_NAME = "AppLogg.txt";

    private static final Level LOGGER_DEFAULT_LEVEL = Level.INFO;

    /*************************/
    /** public static final **/
    /*************************/

    public static final void resetLogger() {
        TwincatLogger.getSignedLogger();
    }
    
    public static final void loadDefaults() {
        TwincatLogger.getLogger();
    }
    
    public static final SimpleFormatter getFormatter() {
        SimpleFormatter consoleFormatter = new SimpleFormatter() {
            @Override
            public synchronized String format(LogRecord logRecord) {
                Date time = new Date(logRecord.getMillis());
                String message = logRecord.getMessage();
                return String.format(LOGGER_FORMATTER, time, message);
            }
        };

        return consoleFormatter;
    }

    public static final void addConsoleLogger() {
        Logger logger = TwincatLogger.getLogger();

        SimpleFormatter consoleFormatter = TwincatLogger.getFormatter();
        StreamHandler consoleHandler = new StreamHandler(System.out, consoleFormatter) {
            @Override
            public synchronized void publish(LogRecord record) {
                super.publish(record);
                super.flush();
            }
        };

        logger.addHandler(consoleHandler);
    }
    
    public static final void addFileLogger() {
        Logger logger = TwincatLogger.getLogger();

        try {
            SimpleFormatter twincatFormatter = TwincatLogger.getFormatter();
            FileHandler fileHandler = new FileHandler(LOGGER_FILE_NAME, true) {
                @Override
                public synchronized void publish(LogRecord record) {
                    // TODO : delete (use global level)
                    super.publish(record);
                }
            };

            fileHandler.setFormatter(twincatFormatter);
            logger.addHandler(fileHandler);
        } catch (SecurityException e) {
            logger.severe(e.getMessage());
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }

    public static final Logger getLogger() {
        System.out.println("First");
        return Logger.getLogger(LOGGER_NAME);
    }

    // package wide initialization
    public static final Logger getSignedLogger() {
        Enumeration<String> loggerNameList = LogManager.getLogManager().getLoggerNames();

        // get logger if already initialized
        while (loggerNameList.hasMoreElements()) {
            String loggerName = loggerNameList.nextElement();

            if (loggerName.equals(LOGGER_NAME)) {
                return Logger.getLogger(LOGGER_NAME);
            }
        }

        System.out.println("Second");
        
        // reset logger
        LogManager.getLogManager().reset();
        Logger.getGlobal().setLevel(Level.OFF);
        Logger logger = Logger.getLogger(LOGGER_NAME);
        logger.setLevel(LOGGER_DEFAULT_LEVEL);

        // add console logger
        TwincatLogger.addConsoleLogger();

        return logger;
    }
}
