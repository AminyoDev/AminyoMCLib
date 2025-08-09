package dev.aminyo.aminyomclib.utils;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Time utilities for formatting and parsing time values
 */
public final class TimeUtils {

    private static final Map<String, TimeUnit> TIME_UNITS = new HashMap<>();

    static {
        TIME_UNITS.put("s", TimeUnit.SECONDS);
        TIME_UNITS.put("sec", TimeUnit.SECONDS);
        TIME_UNITS.put("second", TimeUnit.SECONDS);
        TIME_UNITS.put("seconds", TimeUnit.SECONDS);

        TIME_UNITS.put("m", TimeUnit.MINUTES);
        TIME_UNITS.put("min", TimeUnit.MINUTES);
        TIME_UNITS.put("minute", TimeUnit.MINUTES);
        TIME_UNITS.put("minutes", TimeUnit.MINUTES);

        TIME_UNITS.put("h", TimeUnit.HOURS);
        TIME_UNITS.put("hr", TimeUnit.HOURS);
        TIME_UNITS.put("hour", TimeUnit.HOURS);
        TIME_UNITS.put("hours", TimeUnit.HOURS);

        TIME_UNITS.put("d", TimeUnit.DAYS);
        TIME_UNITS.put("day", TimeUnit.DAYS);
        TIME_UNITS.put("days", TimeUnit.DAYS);
    }

    private TimeUtils() {}

    /**
     * Parse time string to milliseconds
     * @param timeString time string (e.g., "1h 30m 45s")
     * @return milliseconds
     */
    public static long parseTime(String timeString) {
        if (TextUtils.isEmpty(timeString)) {
            return 0;
        }

        long totalMillis = 0;
        String[] parts = timeString.toLowerCase().split("\\s+");

        for (String part : parts) {
            if (part.trim().isEmpty()) continue;

            StringBuilder numberBuilder = new StringBuilder();
            StringBuilder unitBuilder = new StringBuilder();

            boolean parsingNumber = true;
            for (char c : part.toCharArray()) {
                if (Character.isDigit(c) || c == '.') {
                    if (parsingNumber) {
                        numberBuilder.append(c);
                    }
                } else {
                    parsingNumber = false;
                    unitBuilder.append(c);
                }
            }

            try {
                double value = Double.parseDouble(numberBuilder.toString());
                String unit = unitBuilder.toString();

                TimeUnit timeUnit = TIME_UNITS.get(unit);
                if (timeUnit != null) {
                    totalMillis += timeUnit.toMillis((long) value);
                }
            } catch (NumberFormatException e) {
                // Skip invalid parts
            }
        }

        return totalMillis;
    }

    /**
     * Format milliseconds to human readable string
     * @param millis milliseconds
     * @return formatted time string
     */
    public static String formatTime(long millis) {
        if (millis <= 0) {
            return "0s";
        }

        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        seconds %= 60;
        minutes %= 60;
        hours %= 24;

        StringBuilder builder = new StringBuilder();

        if (days > 0) {
            builder.append(days).append("d ");
        }
        if (hours > 0) {
            builder.append(hours).append("h ");
        }
        if (minutes > 0) {
            builder.append(minutes).append("m ");
        }
        if (seconds > 0 || builder.length() == 0) {
            builder.append(seconds).append("s");
        }

        return builder.toString().trim();
    }

    /**
     * Format duration between two instants
     * @param start start time
     * @param end end time
     * @return formatted duration
     */
    public static String formatDuration(Instant start, Instant end) {
        Duration duration = Duration.between(start, end);
        return formatTime(duration.toMillis());
    }

    /**
     * Get current timestamp in specified format
     * @param format date format pattern
     * @return formatted timestamp
     */
    public static String getCurrentTimestamp(String format) {
        return DateTimeFormatter.ofPattern(format).format(LocalDateTime.now());
    }

    /**
     * Get current timestamp in default format
     * @return formatted timestamp (yyyy-MM-dd HH:mm:ss)
     */
    public static String getCurrentTimestamp() {
        return getCurrentTimestamp("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Parse date string
     * @param dateString date string
     * @param format date format pattern
     * @return LocalDateTime or null if parsing fails
     */
    public static LocalDateTime parseDate(String dateString, String format) {
        try {
            return LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(format));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Check if time has elapsed since a reference point
     * @param referenceMillis reference time in milliseconds
     * @param durationMillis duration to check
     * @return true if time has elapsed
     */
    public static boolean hasElapsed(long referenceMillis, long durationMillis) {
        return System.currentTimeMillis() - referenceMillis >= durationMillis;
    }

    /**
     * Get remaining time until expiration
     * @param expirationMillis expiration time in milliseconds
     * @return remaining milliseconds, 0 if expired
     */
    public static long getRemaining(long expirationMillis) {
        long remaining = expirationMillis - System.currentTimeMillis();
        return Math.max(0, remaining);
    }
}
