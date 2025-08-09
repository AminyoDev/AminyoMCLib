package dev.aminyo.aminyomclib.velocity;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Velocity utility methods
 */
public final class VelocityUtils {

    private VelocityUtils() {}

    /**
     * Get online player by name
     */
    public static VelocityPlayer getPlayer(String name, VelocityPlatformAdapter adapter) {
        Optional<Player> player = adapter.getServer().getPlayer(name);
        return player.map(p -> new VelocityPlayer(p, adapter)).orElse(null);
    }

    /**
     * Get online player by UUID
     */
    public static VelocityPlayer getPlayer(UUID uuid, VelocityPlatformAdapter adapter) {
        Optional<Player> player = adapter.getServer().getPlayer(uuid);
        return player.map(p -> new VelocityPlayer(p, adapter)).orElse(null);
    }

    /**
     * Get all online players
     */
    public static Collection<VelocityPlayer> getOnlinePlayers(VelocityPlatformAdapter adapter) {
        return adapter.getServer().getAllPlayers().stream()
                .map(player -> new VelocityPlayer(player, adapter))
                .collect(Collectors.toList());
    }

    /**
     * Get player count
     */
    public static int getOnlinePlayerCount(ProxyServer server) {
        return server.getPlayerCount();
    }

    /**
     * Broadcast message to all players
     */
    public static void broadcast(String message, ProxyServer server) {
        if (message != null) {
            Component component = LegacyComponentSerializer.legacyAmpersand().deserialize(message);
            server.sendMessage(component);
        }
    }

    /**
     * Broadcast message to players with permission
     */
    public static void broadcast(String message, String permission, ProxyServer server) {
        if (message != null) {
            Component component = LegacyComponentSerializer.legacyAmpersand().deserialize(message);

            for (Player player : server.getAllPlayers()) {
                if (permission == null || player.hasPermission(permission)) {
                    player.sendMessage(component);
                }
            }
        }
    }

    /**
     * Get server names
     */
    public static Set<String> getServerNames(ProxyServer server) {
        return server.getAllServers().stream()
                .map(registeredServer -> registeredServer.getServerInfo().getName())
                .collect(Collectors.toSet());
    }

    /**
     * Check if server exists
     */
    public static boolean serverExists(String name, ProxyServer server) {
        return server.getServer(name).isPresent();
    }

    /**
     * Get players on specific server
     */
    public static Collection<VelocityPlayer> getPlayersOnServer(String serverName, VelocityPlatformAdapter adapter) {
        Optional<RegisteredServer> server = adapter.getServer().getServer(serverName);
        if (!server.isPresent()) {
            return Collections.emptyList();
        }

        return adapter.getServer().getAllPlayers().stream()
                .filter(player -> player.getCurrentServer()
                        .map(connection -> serverName.equals(connection.getServerInfo().getName()))
                        .orElse(false))
                .map(player -> new VelocityPlayer(player, adapter))
                .collect(Collectors.toList());
    }

    /**
     * Get player count on specific server
     */
    public static int getPlayerCountOnServer(String serverName, ProxyServer server) {
        Optional<RegisteredServer> registeredServer = server.getServer(serverName);
        if (!registeredServer.isPresent()) {
            return 0;
        }

        return (int) server.getAllPlayers().stream()
                .filter(player -> player.getCurrentServer()
                        .map(connection -> serverName.equals(connection.getServerInfo().getName()))
                        .orElse(false))
                .count();
    }
}