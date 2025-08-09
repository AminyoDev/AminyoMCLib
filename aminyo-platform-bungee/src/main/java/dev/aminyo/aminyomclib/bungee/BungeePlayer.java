package dev.aminyo.aminyomclib.bungee;

import dev.aminyo.aminyomclib.utils.TextUtils;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

/**
 * BungeeCord player wrapper
 */
public class BungeePlayer {

    private final ProxiedPlayer player;
    private final BungeePlatformAdapter adapter;

    public BungeePlayer(ProxiedPlayer player, BungeePlatformAdapter adapter) {
        this.player = player;
        this.adapter = adapter;
    }

    /**
     * Get player name
     */
    public String getName() {
        return player.getName();
    }

    /**
     * Get player UUID
     */
    public UUID getUniqueId() {
        return player.getUniqueId();
    }

    /**
     * Send message to player
     */
    public void sendMessage(String message) {
        if (message != null) {
            String colorized = TextUtils.colorize(message);
            player.sendMessage(TextComponent.fromLegacyText(colorized));
        }
    }

    /**
     * Send multiple messages to player
     */
    public void sendMessage(String... messages) {
        for (String message : messages) {
            sendMessage(message);
        }
    }

    /**
     * Check if player has permission
     */
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

    /**
     * Kick player with message
     */
    public void kick(String reason) {
        String colorized = TextUtils.colorize(reason != null ? reason : "Kicked from server");
        player.disconnect(TextComponent.fromLegacyText(colorized));
    }

    /**
     * Get player's server
     */
    public String getServerName() {
        return player.getServer() != null ? player.getServer().getInfo().getName() : null;
    }

    /**
     * Connect player to server
     */
    public void connect(String serverName) {
        if (adapter.getProxy().getServerInfo(serverName) != null) {
            player.connect(adapter.getProxy().getServerInfo(serverName));
        }
    }

    /**
     * Get player's IP address
     */
    public String getAddress() {
        return player.getAddress().getAddress().getHostAddress();
    }

    /**
     * Check if player is online
     */
    public boolean isOnline() {
        return player.isConnected();
    }

    /**
     * Get the underlying ProxiedPlayer
     */
    public ProxiedPlayer getBungeePlayer() {
        return player;
    }
}