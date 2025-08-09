package dev.aminyo.aminyomclib.spigot.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Spigot command framework
 */
public abstract class SpigotCommand implements CommandExecutor, TabCompleter {

    protected final JavaPlugin plugin;
    protected final String name;
    protected final String permission;
    protected final String description;

    public SpigotCommand(JavaPlugin plugin, String name, String permission, String description) {
        this.plugin = plugin;
        this.name = name;
        this.permission = permission;
        this.description = description;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            // Permission check
            if (permission != null && !sender.hasPermission(permission)) {
                sendMessage(sender, "&cYou don't have permission to use this command!");
                return true;
            }

            // Execute command
            execute(sender, args);
            return true;

        } catch (Exception e) {
            sendMessage(sender, "&cAn error occurred while executing the command!");
            plugin.getLogger().severe("Error executing command '" + name + "': " + e.getMessage());
            e.printStackTrace();
            return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (permission != null && !sender.hasPermission(permission)) {
            return new ArrayList<>();
        }

        try {
            return tabComplete(sender, args);
        } catch (Exception e) {
            plugin.getLogger().warning("Error in tab completion for command '" + name + "': " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Execute the command
     */
    protected abstract void execute(CommandSender sender, String[] args);

    /**
     * Provide tab completion
     */
    protected List<String> tabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }

    /**
     * Send colored message to sender
     */
    protected void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    /**
     * Check if sender is player
     */
    protected boolean isPlayer(CommandSender sender) {
        return sender instanceof Player;
    }

    /**
     * Get player from sender (with null check)
     */
    protected Player getPlayer(CommandSender sender) {
        return isPlayer(sender) ? (Player) sender : null;
    }

    /**
     * Register this command
     */
    public void register() {
        plugin.getCommand(name).setExecutor(this);
        plugin.getCommand(name).setTabCompleter(this);
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public String getDescription() {
        return description;
    }
}