package dev.aminyo.aminyomclib.velocity;


import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.title.Title;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Velocity player wrapper
 */
public class VelocityPlayer {

    private final Player player;
    private final VelocityPlatformAdapter adapter;

    public VelocityPlayer(Player player, VelocityPlatformAdapter adapter) {
        this.player = player;
        this.adapter = adapter;
    }

    /**
     * Get player name
     */
    public String getName() {
        return player.getUsername();
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
            Component component = LegacyComponentSerializer.legacyAmpersand().deserialize(message);
            player.sendMessage(component);
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
        String kickReason = reason != null ? reason : "Kicked from server";
        Component component = LegacyComponentSerializer.legacyAmpersand().deserialize(kickReason);
        player.disconnect(component);
    }

    /**
     * Get player's server
     */
    public String getServerName() {
        return player.getCurrentServer()
                .map(connection -> connection.getServerInfo().getName())
                .orElse(null);
    }

    /**
     * Connect player to server
     */
    public CompletableFuture<Boolean> connect(String serverName) {
        Optional<RegisteredServer> server = adapter.getServer().getServer(serverName);
        if (server.isPresent()) {
            return player.createConnectionRequest(server.get())
                    .connect()
                    .thenApply(result -> result.isSuccessful());
        }
        return CompletableFuture.completedFuture(false);
    }

    /**
     * Get player's IP address
     */
    public String getAddress() {
        return player.getRemoteAddress().getAddress().getHostAddress();
    }

    /**
     * Check if player is online
     */
    public boolean isOnline() {
        return player.isActive();
    }

    /**
     * Send title to player
     */
    public void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        Component titleComponent = title != null ?
                LegacyComponentSerializer.legacyAmpersand().deserialize(title) : Component.empty();
        Component subtitleComponent = subtitle != null ?
                LegacyComponentSerializer.legacyAmpersand().deserialize(subtitle) : Component.empty();

        Title titleObj = Title.title(
                titleComponent,
                subtitleComponent,
                Title.Times.times(
                        Duration.ofMillis(fadeIn * 50),
                        Duration.ofMillis(stay * 50),
                        Duration.ofMillis(fadeOut * 50)
                )
        );

        player.showTitle(titleObj);
    }

    /**
     * Send action bar message
     */
    public void sendActionBar(String message) {
        if (message != null) {
            Component component = LegacyComponentSerializer.legacyAmpersand().deserialize(message);
            player.sendActionBar(component);
        }
    }

    /**
     * Get the underlying Velocity Player
     */
    public Player getVelocityPlayer() {
        return player;
    }
}
