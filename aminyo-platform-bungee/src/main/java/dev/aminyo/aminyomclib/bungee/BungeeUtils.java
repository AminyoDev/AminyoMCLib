package dev.aminyo.aminyomclib.bungee;

import dev.aminyo.aminyomclib.utils.TextUtils;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

/**
 * BungeeCord utility methods
 */
public final class BungeeUtils {

    private BungeeUtils() {}

    /**
     * Get online player by name
     */
    public static BungeePlayer getPlayer(String name, BungeePlatformAdapter adapter) {
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(name);
        return player != null ? new BungeePlayer(player, adapter) : null;
    }

    /**
     * Get online player by UUID
     */
    public static BungeePlayer getPlayer(UUID uuid, BungeePlatformAdapter adapter) {
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
        return player != null ? new BungeePlayer(player, adapter) : null;
    }

    /**
     * Get all online players
     */
    public static java.util.Collection<BungeePlayer> getOnlinePlayers(BungeePlatformAdapter adapter) {
        return ProxyServer.getInstance().getPlayers().stream()
                .map(player -> new BungeePlayer(player, adapter))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Get player count
     */
    public static int getOnlinePlayerCount() {
        return ProxyServer.getInstance().getOnlineCount();
    }

    /**
     * Broadcast message to all players
     */
    public static void broadcast(String message) {
        if (message != null) {
            String colorized = TextUtils.colorize(message);
            ProxyServer.getInstance().broadcast(TextComponent.fromLegacyText(colorized));
        }
    }

    /**
     * Broadcast message to players with permission
     */
    public static void broadcast(String message, String permission) {
        if (message != null) {
            String colorized = TextUtils.colorize(message);
            TextComponent component = (TextComponent) TextComponent.fromLegacyText(colorized)[0];

            for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                if (permission == null || player.hasPermission(permission)) {
                    player.sendMessage(component);
                }
            }
        }
    }

    /**
     * Get server names
     */
    public static java.util.Set<String> getServerNames() {
        return ProxyServer.getInstance().getServers().keySet();
    }

    /**
     * Check if server exists
     */
    public static boolean serverExists(String name) {
        return ProxyServer.getInstance().getServerInfo(name) != null;
    }

    /**
     * Get players on specific server
     */
    public static java.util.Collection<BungeePlayer> getPlayersOnServer(String serverName, BungeePlatformAdapter adapter) {
        if (!serverExists(serverName)) {
            return java.util.Collections.emptyList();
        }

        return ProxyServer.getInstance().getPlayers().stream()
                .filter(player -> player.getServer() != null &&
                        serverName.equals(player.getServer().getInfo().getName()))
                .map(player -> new BungeePlayer(player, adapter))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Get player count on specific server
     */
    public static int getPlayerCountOnServer(String serverName) {
        if (!serverExists(serverName)) {
            return 0;
        }

        return (int) ProxyServer.getInstance().getPlayers().stream()
                .filter(player -> player.getServer() != null &&
                        serverName.equals(player.getServer().getInfo().getName()))
                .count();
    }
}

