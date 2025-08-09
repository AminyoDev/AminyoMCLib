package dev.aminyo.aminyomclib.utils;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Mathematical utilities
 */
public final class MathUtils {

    private static final Random RANDOM = new Random();

    private MathUtils() {}

    /**
     * Clamp value between min and max
     * @param value value to clamp
     * @param min minimum value
     * @param max maximum value
     * @return clamped value
     */
    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Clamp double value between min and max
     * @param value value to clamp
     * @param min minimum value
     * @param max maximum value
     * @return clamped value
     */
    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Check if value is within range (inclusive)
     * @param value value to check
     * @param min minimum value
     * @param max maximum value
     * @return true if in range
     */
    public static boolean inRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    /**
     * Check if double value is within range (inclusive)
     * @param value value to check
     * @param min minimum value
     * @param max maximum value
     * @return true if in range
     */
    public static boolean inRange(double value, double min, double max) {
        return value >= min && value <= max;
    }

    /**
     * Get random integer between min and max (inclusive)
     * @param min minimum value
     * @param max maximum value
     * @return random integer
     */
    public static int randomInt(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }

    /**
     * Get random double between min and max
     * @param min minimum value
     * @param max maximum value
     * @return random double
     */
    public static double randomDouble(double min, double max) {
        return min + (max - min) * RANDOM.nextDouble();
    }

    /**
     * Check if number is prime
     * @param number number to check
     * @return true if prime
     */
    public static boolean isPrime(int number) {
        if (number <= 1) return false;
        if (number <= 3) return true;
        if (number % 2 == 0 || number % 3 == 0) return false;

        for (int i = 5; i * i <= number; i += 6) {
            if (number % i == 0 || number % (i + 2) == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Calculate percentage
     * @param value current value
     * @param max maximum value
     * @return percentage (0.0 to 1.0)
     */
    public static double percentage(double value, double max) {
        if (max == 0) return 0;
        return clamp(value / max, 0.0, 1.0);
    }

    /**
     * Format number with decimal places
     * @param number number to format
     * @param decimals number of decimal places
     * @return formatted number string
     */
    public static String formatNumber(double number, int decimals) {
        StringBuilder pattern = new StringBuilder("#");
        if (decimals > 0) {
            pattern.append(".");
            for (int i = 0; i < decimals; i++) {
                pattern.append("0");
            }
        }

        DecimalFormat formatter = new DecimalFormat(pattern.toString());
        return formatter.format(number);
    }

    /**
     * Round to specified decimal places
     * @param value value to round
     * @param decimals number of decimal places
     * @return rounded value
     */
    public static double round(double value, int decimals) {
        double multiplier = Math.pow(10, decimals);
        return Math.round(value * multiplier) / multiplier;
    }
}
