package twincat.ads;

<<<<<<< HEAD
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
=======
import java.util.Date;
import java.util.Enumeration;
>>>>>>> 58a89527366fffdbf90d9364e05771af6ab1f1f4
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

public final class AdsLogger {
    /*************************/
    /** constant attributes **/
<<<<<<< HEAD
    /*************************/

    public static final String LOGGER_NAME = "AdsLogger";

    public static final String LOGGER_FORMATTER = "[%1$tT] %2$s %n";

    public static final String LOGGER_FILE_NAME = "AppLogg.txt";
    
    private static final Level LOGGER_CONSOLE_LEVEL = Level.INFO;

    private static final Level LOGGER_FILE_LEVEL = Level.OFF;

    /*************************/
    /** public static final **/
    /*************************/

=======
    /*************************/     
    
    public static final String LOGGER_NAME = "AdsLogger";

    public static final String LOGGER_FORMATTER = "[%1$tT] %2$s %n";
    
    /*************************/
    /** public static final **/
    /*************************/    
    
>>>>>>> 58a89527366fffdbf90d9364e05771af6ab1f1f4
    public static final Logger getLogger() {
        Enumeration<String> loggerNameList = LogManager.getLogManager().getLoggerNames();

        while (loggerNameList.hasMoreElements()) {
            String loggerName = loggerNameList.nextElement();
<<<<<<< HEAD

            if (loggerName.equals(LOGGER_NAME)) {
                return Logger.getLogger(LOGGER_NAME);
            }
        }

        // reset logger
        LogManager.getLogManager().reset();
        Logger.getGlobal().setLevel(Level.OFF);
        Logger logger = Logger.getLogger(LOGGER_NAME);
        logger.setLevel(LOGGER_CONSOLE_LEVEL);

        // logger formatter
        SimpleFormatter consoleFormatter = new SimpleFormatter() {
=======
            
            if (loggerName.equals(LOGGER_NAME)) {
                return Logger.getLogger(LOGGER_NAME);
            }      
        }

        LogManager.getLogManager().reset();
        Logger.getGlobal().setLevel(Level.OFF);
        Logger logger = Logger.getLogger(LOGGER_NAME);

        SimpleFormatter simpleFormatter = new SimpleFormatter() {
>>>>>>> 58a89527366fffdbf90d9364e05771af6ab1f1f4
            @Override
            public synchronized String format(LogRecord logRecord) {
                Date time = new Date(logRecord.getMillis());
                String message = logRecord.getMessage();
                return String.format(LOGGER_FORMATTER, time, message);
            }
        };

<<<<<<< HEAD
        // console logger
        StreamHandler consoleHandler = new StreamHandler(System.out, consoleFormatter) {
=======
        StreamHandler streamHandler = new StreamHandler(System.out, simpleFormatter) {
>>>>>>> 58a89527366fffdbf90d9364e05771af6ab1f1f4
            @Override
            public synchronized void publish(LogRecord record) {
                super.publish(record);
                super.flush();
            }
        };
<<<<<<< HEAD

        logger.addHandler(consoleHandler);

        if (!LOGGER_FILE_LEVEL.equals(Level.OFF)) {
            try {
                // file logger
                FileHandler fileHandler = new FileHandler(LOGGER_FILE_NAME, true) {
                    @Override
                    public synchronized void publish(LogRecord record) {
                        if (record.getLevel().intValue() >= LOGGER_FILE_LEVEL.intValue()) {
                            super.publish(record);
                        }
                    }
                };

                fileHandler.setFormatter(consoleFormatter);
                logger.addHandler(fileHandler);
            } catch (SecurityException e) {
                logger.severe(e.getMessage());
            } catch (IOException e) {
                logger.severe(e.getMessage());
            }    
        }

        return logger;
    }

    public static final SimpleFormatter getFormatter() {
        Logger logger = AdsLogger.getLogger();
        Handler[] handlers = logger.getHandlers();
        return (SimpleFormatter) handlers[0].getFormatter();
    }
    
=======
        
        logger.addHandler(streamHandler);
        
        return logger;
    }

>>>>>>> 58a89527366fffdbf90d9364e05771af6ab1f1f4
}
