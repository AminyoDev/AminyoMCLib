package dev.aminyo.aminyomclib.utils;

import java.lang.reflect.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * Text utilities for formatting, colors, and string manipulation
 */
public final class TextUtils {

    private static final Pattern HEX_PATTERN = Pattern.compile("&#[a-fA-F0-9]{6}");
    private static final Pattern COLOR_PATTERN = Pattern.compile("&[0-9a-fk-or]");

    private TextUtils() {}

    /**
     * Color codes for legacy Minecraft colors
     */
    public enum ChatColor {
        BLACK('0', "black"),
        DARK_BLUE('1', "dark_blue"),
        DARK_GREEN('2', "dark_green"),
        DARK_AQUA('3', "dark_aqua"),
        DARK_RED('4', "dark_red"),
        DARK_PURPLE('5', "dark_purple"),
        GOLD('6', "gold"),
        GRAY('7', "gray"),
        DARK_GRAY('8', "dark_gray"),
        BLUE('9', "blue"),
        GREEN('a', "green"),
        AQUA('b', "aqua"),
        RED('c', "red"),
        LIGHT_PURPLE('d', "light_purple"),
        YELLOW('e', "yellow"),
        WHITE('f', "white"),
        MAGIC('k', "obfuscated"),
        BOLD('l', "bold"),
        STRIKETHROUGH('m', "strikethrough"),
        UNDERLINE('n', "underline"),
        ITALIC('o', "italic"),
        RESET('r', "reset");

        private final char code;
        private final String name;

        ChatColor(char code, String name) {
            this.code = code;
            this.name = name;
        }

        public char getCode() { return code; }
        public String getName() { return name; }

        @Override
        public String toString() {
            return "§" + code;
        }

        public static String translateAlternateColorCodes(char altColorChar, String textToTranslate) {
            if (textToTranslate == null) return null;

            char[] chars = textToTranslate.toCharArray();
            for (int i = 0; i < chars.length - 1; i++) {
                if (chars[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(chars[i + 1]) > -1) {
                    chars[i] = '§';
                    chars[i + 1] = Character.toLowerCase(chars[i + 1]);
                }
            }

            return new String(chars);
        }

        public static String stripColor(String input) {
            if (input == null) return null;
            return COLOR_PATTERN.matcher(input).replaceAll("");
        }
    }

    /**
     * Translate color codes in text
     * @param text text with color codes
     * @return formatted text
     */
    public static String colorize(String text) {
        if (text == null) return null;

        // Translate hex colors (&#ffffff -> §x§f§f§f§f§f§f)
        text = HEX_PATTERN.matcher(text).replaceAll(match -> {
            String hex = match.group().substring(2); // Remove &#
            StringBuilder builder = new StringBuilder("§x");
            for (char c : hex.toCharArray()) {
                builder.append('§').append(c);
            }
            return builder.toString();
        });

        // Translate legacy colors (&a -> §a)
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * Remove all color codes from text
     * @param text colored text
     * @return clean text
     */
    public static String stripColors(String text) {
        if (text == null) return null;

        // Remove hex colors
        text = HEX_PATTERN.matcher(text).replaceAll("");
        // Remove legacy colors
        return ChatColor.stripColor(text);
    }

    /**
     * Center text with padding
     * @param text text to center
     * @param length total length
     * @param padChar padding character
     * @return centered text
     */
    public static String center(String text, int length, char padChar) {
        if (text == null) text = "";
        if (text.length() >= length) return text;

        int padding = (length - text.length()) / 2;
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < padding; i++) {
            builder.append(padChar);
        }

        builder.append(text);

        while (builder.length() < length) {
            builder.append(padChar);
        }

        return builder.toString();
    }

    /**
     * Create a progress bar
     * @param current current value
     * @param max maximum value
     * @param length bar length
     * @param completeChar character for completed portion
     * @param incompleteChar character for incomplete portion
     * @return progress bar string
     */
    public static String createProgressBar(double current, double max, int length, char completeChar, char incompleteChar) {
        if (max <= 0) return String.valueOf(incompleteChar).repeat(length);

        double percentage = Math.min(1.0, Math.max(0.0, current / max));
        int completeLength = (int) (percentage * length);

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(i < completeLength ? completeChar : incompleteChar);
        }

        return builder.toString();
    }

    /**
     * Wrap text to specified width
     * @param text text to wrap
     * @param width maximum line width
     * @return list of wrapped lines
     */
    public static List<String> wrapText(String text, int width) {
        if (text == null || text.isEmpty()) {
            return Arrays.asList("");
        }

        List<String> lines = new ArrayList<>();
        String[] words = text.split("\\s+");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            if (currentLine.length() + word.length() + 1 > width) {
                if (currentLine.length() > 0) {
                    lines.add(currentLine.toString());
                    currentLine = new StringBuilder();
                }

                // Handle words longer than width
                if (word.length() > width) {
                    while (word.length() > width) {
                        lines.add(word.substring(0, width));
                        word = word.substring(width);
                    }
                    if (!word.isEmpty()) {
                        currentLine.append(word);
                    }
                } else {
                    currentLine.append(word);
                }
            } else {
                if (currentLine.length() > 0) {
                    currentLine.append(" ");
                }
                currentLine.append(word);
            }
        }

        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }

        return lines;
    }

    /**
     * Replace placeholders in text
     * @param text text with placeholders
     * @param placeholders placeholder map
     * @return text with replaced placeholders
     */
    public static String replacePlaceholders(String text, Map<String, String> placeholders) {
        if (text == null || placeholders == null || placeholders.isEmpty()) {
            return text;
        }

        String result = text;
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            String placeholder = "{" + entry.getKey() + "}";
            String value = entry.getValue() != null ? entry.getValue() : "";
            result = result.replace(placeholder, value);
        }

        return result;
    }

    /**
     * Check if string is null or empty
     * @param str string to check
     * @return true if null or empty
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Check if string is not null and not empty
     * @param str string to check
     * @return true if not null and not empty
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
