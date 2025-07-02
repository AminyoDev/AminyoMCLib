package dev.aminyo.aminyomclib.bungee.utils;

import net.md_5.bungee.api.ProxyServer;

import java.util.HashMap;
import java.util.Map;

public class ConsoleColorUtil {

    private static final String RESET = "\u001B[0m";

    private static final Map<Character, String> COLOR_MAP = new HashMap<>();
    private static final Map<Character, String> FORMAT_MAP = new HashMap<>();

    static {
        COLOR_MAP.put('0', "\u001B[30m");
        COLOR_MAP.put('1', "\u001B[34m");
        COLOR_MAP.put('2', "\u001B[32m");
        COLOR_MAP.put('3', "\u001B[36m");
        COLOR_MAP.put('4', "\u001B[31m");
        COLOR_MAP.put('5', "\u001B[35m");
        COLOR_MAP.put('6', "\u001B[33m");
        COLOR_MAP.put('7', "\u001B[37m");
        COLOR_MAP.put('8', "\u001B[90m");
        COLOR_MAP.put('9', "\u001B[94m");
        COLOR_MAP.put('a', "\u001B[92m");
        COLOR_MAP.put('b', "\u001B[96m");
        COLOR_MAP.put('c', "\u001B[91m");
        COLOR_MAP.put('d', "\u001B[95m");
        COLOR_MAP.put('e', "\u001B[93m");
        COLOR_MAP.put('f', "\u001B[97m");

        FORMAT_MAP.put('l', "\u001B[1m");
        FORMAT_MAP.put('n', "\u001B[4m");
        FORMAT_MAP.put('o', "\u001B[3m");

        FORMAT_MAP.put('r', RESET);
    }

    public static String translateColorCodesToAnsi(String msg) {
        StringBuilder builder = new StringBuilder();
        char[] chars = msg.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '&' && i + 1 < chars.length) {
                char code = Character.toLowerCase(chars[i + 1]);
                String ansi = COLOR_MAP.get(code);
                if (ansi == null) {
                    ansi = FORMAT_MAP.get(code);
                }
                if (ansi != null) {
                    builder.append(ansi);
                    i++;
                    continue;
                }
            }
            builder.append(chars[i]);
        }
        builder.append(RESET); // reset finale
        return builder.toString();
    }

    public static void sendColoredConsoleMsg(String msg) {
        ProxyServer.getInstance().getLogger().info(translateColorCodesToAnsi(msg));
    }
}
