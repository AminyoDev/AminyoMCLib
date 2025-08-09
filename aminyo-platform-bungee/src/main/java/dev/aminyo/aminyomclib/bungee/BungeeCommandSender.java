package dev.aminyo.aminyomclib.bungee;

import dev.aminyo.aminyomclib.utils.TextUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * BungeeCord command sender wrapper
 */
public class BungeeCommandSender {

    private final CommandSender sender;

    public BungeeCommandSender(CommandSender sender) {
        this.sender = sender;
    }

    /**
     * Get sender name
     */
    public String getName() {
        return sender.getName();
    }

    /**
     * Send message to sender
     */
    public void sendMessage(String message) {
        if (message != null) {
            String colorized = TextUtils.colorize(message);
            sender.sendMessage(TextComponent.fromLegacyText(colorized));
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
        return sender instanceof ProxiedPlayer;
    }

    /**
     * Get as BungeePlayer if sender is player
     */
    public BungeePlayer asPlayer(BungeePlatformAdapter adapter) {
        if (isPlayer()) {
            return new BungeePlayer((ProxiedPlayer) sender, adapter);
        }
        return null;
    }

    /**
     * Get the underlying CommandSender
     */
    public CommandSender getBungeeSender() {
        return sender;
    }
}
