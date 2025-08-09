package dev.aminyo.aminyomclib.spigot.utils;

import dev.aminyo.aminyomclib.utils.LocationUtils;
import dev.aminyo.aminyomclib.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class SpigotPlayerUtils extends PlayerUtils {

    private SpigotPlayerUtils() {
        super();
    }

    /**
     * Get online player by name
     */
    public static Player getPlayer(String name) {
        return Bukkit.getPlayer(name);
    }

    /**
     * Get online player by UUID
     */
    public static Player getPlayer(UUID uuid) {
        return Bukkit.getPlayer(uuid);
    }

    /**
     * Send message to player with color support
     */
    public static void sendMessage(Player player, String message) {
        if (player != null && message != null) {
            player.sendMessage(colorize(message));
        }
    }

    /**
     * Send message to all online players
     */
    public static void broadcast(String message) {
        Bukkit.broadcastMessage(colorize(message));
    }

    /**
     * Send message to players with permission
     */
    public static void broadcastPermission(String message, String permission) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission(permission)) {
                sendMessage(player, message);
            }
        }
    }

    /**
     * Get player's location as SimpleLocation
     */
    public static LocationUtils.SimpleLocation getSimpleLocation(Player player) {
        Location loc = player.getLocation();
        return new LocationUtils.SimpleLocation(
                loc.getWorld().getName(),
                loc.getX(),
                loc.getY(),
                loc.getZ(),
                loc.getYaw(),
                loc.getPitch()
        );
    }

    /**
     * Teleport player to SimpleLocation
     */
    public static boolean teleport(Player player, LocationUtils.SimpleLocation location) {
        World world = Bukkit.getWorld(location.getWorld());
        if (world == null) {
            return false;
        }

        Location bukkitLocation = new Location(
                world,
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch()
        );

        return player.teleport(bukkitLocation);
    }

    /**
     * Check if player is online
     */
    public static boolean isOnline(String playerName) {
        Player player = getPlayer(playerName);
        return player != null && player.isOnline();
    }

    /**
     * Check if player is online by UUID
     */
    public static boolean isOnline(UUID uuid) {
        Player player = getPlayer(uuid);
        return player != null && player.isOnline();
    }

    /**
     * Get all online player names
     */
    public static List<String> getOnlinePlayerNames() {
        List<String> names = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            names.add(player.getName());
        }
        return names;
    }

    /**
     * Get player count
     */
    public static int getPlayerCount() {
        return Bukkit.getOnlinePlayers().size();
    }

    /**
     * Kick player with reason
     */
    public static void kick(Player player, String reason) {
        if (player != null) {
            player.kickPlayer(colorize(reason != null ? reason : "Kicked from server"));
        }
    }

    /**
     * Send title to player (1.8+)
     */
    public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        if (player == null) return;

        try {
            // Use reflection to support different versions
            String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];

            if (version.startsWith("v1_7")) {
                // 1.7 doesn't support titles
                sendMessage(player, title);
                if (subtitle != null) {
                    sendMessage(player, subtitle);
                }
                return;
            }

            // Modern versions (1.8+)
            player.sendTitle(
                    colorize(title != null ? title : ""),
                    colorize(subtitle != null ? subtitle : ""),
                    fadeIn, stay, fadeOut
            );

        } catch (Exception e) {
            // Fallback to chat message
            sendMessage(player, title);
            if (subtitle != null) {
                sendMessage(player, subtitle);
            }
        }
    }

    /**
     * Send action bar message (1.8+)
     */
    public static void sendActionBar(Player player, String message) {
        if (player == null || message == null) return;

        try {
            String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];

            if (version.startsWith("v1_7")) {
                // 1.7 doesn't support action bar
                sendMessage(player, message);
                return;
            }

            // Use Spigot API if available
            player.spigot().sendMessage(
                    net.md_5.bungee.api.ChatMessageType.ACTION_BAR,
                    net.md_5.bungee.api.chat.TextComponent.fromLegacyText(colorize(message))
            );

        } catch (Exception e) {
            // Fallback to chat message
            sendMessage(player, message);
        }
    }

    private static String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
