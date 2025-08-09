package dev.aminyo.aminyomclib.utils;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.Arrays;

/**
 * Logger utilities for enhanced logging functionality
 */
public final class LoggerUtils {

    private LoggerUtils() {}

    /**
     * Create logger with custom name
     * @param name logger name
     * @return logger instance
     */
    public static Logger getLogger(String name) {
        return LoggerFactory.getLogger(name);
    }

    /**
     * Create logger for class
     * @param clazz class
     * @return logger instance
     */
    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    /**
     * Log with custom level
     * @param logger logger instance
     * @param level log level
     * @param message message
     * @param args arguments
     */
    public static void log(Logger logger, String level, String message, Object... args) {
        switch (level.toLowerCase()) {
            case "trace":
                logger.trace(message, args);
                break;
            case "debug":
                logger.debug(message, args);
                break;
            case "info":
                logger.info(message, args);
                break;
            case "warn":
            case "warning":
                logger.warn(message, args);
                break;
            case "error":
                logger.error(message, args);
                break;
            default:
                logger.info(message, args);
                break;
        }
    }

    /**
     * Log exception with custom message
     * @param logger logger instance
     * @param message message
     * @param throwable exception
     */
    public static void logException(Logger logger, String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    /**
     * Create formatted log message
     * @param format message format
     * @param args arguments
     * @return formatted message
     */
    public static String formatMessage(String format, Object... args) {
        try {
            return String.format(format, args);
        } catch (Exception e) {
            return format + " " + Arrays.toString(args);
        }
    }
}
