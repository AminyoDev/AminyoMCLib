package dev.aminyo.aminyomclib.utils;

import java.util.UUID;

/**
 * Player utilities (platform-independent base functionality)
 */
public class PlayerUtils {

    protected PlayerUtils() {}

    /**
     * Validate player name format
     * @param playerName player name
     * @return true if valid
     */
    public static boolean isValidPlayerName(String playerName) {
        if (TextUtils.isEmpty(playerName)) {
            return false;
        }

        // Minecraft username requirements: 3-16 characters, alphanumeric + underscore
        return playerName.length() >= 3 &&
                playerName.length() <= 16 &&
                playerName.matches("^[a-zA-Z0-9_]+$");
    }

    /**
     * Validate UUID format
     * @param uuid UUID string
     * @return true if valid UUID format
     */
    public static boolean isValidUUID(String uuid) {
        if (TextUtils.isEmpty(uuid)) {
            return false;
        }

        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Convert trimmed UUID to full UUID format
     * @param trimmedUuid UUID without dashes
     * @return formatted UUID string
     */
    public static String formatUUID(String trimmedUuid) {
        if (TextUtils.isEmpty(trimmedUuid) || trimmedUuid.length() != 32) {
            return trimmedUuid;
        }

        return String.format("%s-%s-%s-%s-%s",
                trimmedUuid.substring(0, 8),
                trimmedUuid.substring(8, 12),
                trimmedUuid.substring(12, 16),
                trimmedUuid.substring(16, 20),
                trimmedUuid.substring(20, 32));
    }

    /**
     * Remove dashes from UUID
     * @param uuid formatted UUID
     * @return trimmed UUID
     */
    public static String trimUUID(String uuid) {
        return uuid != null ? uuid.replace("-", "") : null;
    }

    /**
     * Generate random UUID
     * @return random UUID
     */
    public static UUID generateUUID() {
        return UUID.randomUUID();
    }
}
