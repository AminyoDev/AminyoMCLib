package dev.aminyo.aminyomclib.velocity;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

/**
 * Velocity command sender wrapper
 */
public class VelocityCommandSender {

    private final CommandSource sender;

    public VelocityCommandSender(CommandSource sender) {
        this.sender = sender;
    }

    /**
     * Send message to sender
     */
    public void sendMessage(String message) {
        if (message != null) {
            Component component = LegacyComponentSerializer.legacyAmpersand().deserialize(message);
            sender.sendMessage(component);
        }
    }

    /**
     * Send multiple messages to sender
     */
    public void sendMessage(String... messages) {
        for (String message : messages) {
            sendMessage(message);
        }
    }

    /**
     * Check if sender has permission
     */
    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }

    /**
     * Check if sender is player
     */
    public boolean isPlayer() {
        return sender instanceof Player;
    }

    /**
     * Get as VelocityPlayer if sender is player
     */
    public VelocityPlayer asPlayer(VelocityPlatformAdapter adapter) {
        if (isPlayer()) {
            return new VelocityPlayer((Player) sender, adapter);
        }
        return null;
    }

    /**
     * Get the underlying CommandSource
     */
    public CommandSource getVelocitySender() {
        return sender;
    }
}
