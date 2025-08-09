package dev.aminyo.aminyomclib.spigot;

import dev.aminyo.aminyomclib.core.*;
import dev.aminyo.aminyomclib.core.interfaces.AminyoMCLib;
import dev.aminyo.aminyomclib.spigot.adapter.SpigotPlatformAdapter;
import dev.aminyo.aminyomclib.spigot.commands.SpigotCommand;
import dev.aminyo.aminyomclib.spigot.listeners.SpigotListener;
import dev.aminyo.aminyomclib.spigot.tasks.SpigotScheduler;
import dev.aminyo.aminyomclib.spigot.utils.SpigotPlayerUtils;
import dev.aminyo.aminyomclib.utils.*;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Example Spigot plugin integration class
 */
public class SpigotAminyoPlugin extends JavaPlugin {

    private AminyoMCLib aminyoLib;
    private SpigotScheduler scheduler;
    private final ConcurrentMap<String, SpigotCommand> commands = new ConcurrentHashMap<>();
    private final List<SpigotListener> listeners = new ArrayList<>();

    @Override
    public void onEnable() {
        // Initialize AminyoMCLib
        SpigotPlatformAdapter adapter = new SpigotPlatformAdapter(this);
        this.aminyoLib = AminyoMCLibImpl.initialize(adapter);
        this.scheduler = new SpigotScheduler(this);

        getLogger().info("AminyoMCLib initialized for Spigot!");
        getLogger().info("Platform: " + adapter.getPlatformType());
        getLogger().info("Minecraft Version: " + adapter.getMinecraftVersion());

        // Register default listeners
        registerDefaultListeners();

        // Example: Register a test command
        registerCommand(new TestCommand(this));
    }

    @Override
    public void onDisable() {
        // Shutdown AminyoMCLib
        if (aminyoLib != null) {
            aminyoLib.shutdown();
        }

        // Cancel all scheduled tasks
        if (scheduler != null) {
            scheduler.cancelAllTasks();
        }

        getLogger().info("AminyoMCLib disabled!");
    }

    /**
     * Register a command
     */
    public void registerCommand(SpigotCommand command) {
        commands.put(command.getName(), command);
        command.register();
    }

    /**
     * Register a listener
     */
    public void registerListener(SpigotListener listener) {
        listeners.add(listener);
        listener.register();
    }

    private void registerDefaultListeners() {
        registerListener(new DefaultEventListener(this));
    }

    public AminyoMCLib getAminyoLib() {
        return aminyoLib;
    }

    public SpigotScheduler getAminyoScheduler() {
        return scheduler;
    }

    /**
     * Example test command
     */
    private static class TestCommand extends SpigotCommand {

        public TestCommand(JavaPlugin plugin) {
            super(plugin, "aminyotest", "aminyo.test", "Test AminyoMCLib features");
        }

        @Override
        protected void execute(CommandSender sender, String[] args) {
            if (args.length == 0) {
                sendMessage(sender, "&aAminyoMCLib Test Command");
                sendMessage(sender, "&7Usage: /aminyotest <info|player|location>");
                return;
            }

            switch (args[0].toLowerCase()) {
                case "info":
                    AminyoMCLib lib = AminyoMCLibImpl.getInstance();
                    sendMessage(sender, "&aAminyoMCLib Information:");
                    sendMessage(sender, "&7Version: " + lib.getVersion());
                    sendMessage(sender, "&7Platform: " + lib.getPlatformAdapter().getPlatformType());
                    sendMessage(sender, "&7MC Version: " + lib.getPlatformAdapter().getMinecraftVersion());
                    break;

                case "player":
                    if (!isPlayer(sender)) {
                        sendMessage(sender, "&cThis command can only be used by players!");
                        return;
                    }

                    Player player = getPlayer(sender);
                    sendMessage(sender, "&aPlayer Information:");
                    sendMessage(sender, "&7Name: " + player.getName());
                    sendMessage(sender, "&7UUID: " + player.getUniqueId());
                    sendMessage(sender, "&7Online: " + SpigotPlayerUtils.isOnline(player.getName()));
                    break;

                case "location":
                    if (!isPlayer(sender)) {
                        sendMessage(sender, "&cThis command can only be used by players!");
                        return;
                    }

                    Player p = getPlayer(sender);
                    LocationUtils.SimpleLocation loc = SpigotPlayerUtils.getSimpleLocation(p);
                    sendMessage(sender, "&aYour Location:");
                    sendMessage(sender, "&7World: " + loc.getWorld());
                    sendMessage(sender, "&7X: " + MathUtils.formatNumber(loc.getX(), 2));
                    sendMessage(sender, "&7Y: " + MathUtils.formatNumber(loc.getY(), 2));
                    sendMessage(sender, "&7Z: " + MathUtils.formatNumber(loc.getZ(), 2));
                    break;

                default:
                    sendMessage(sender, "&cUnknown subcommand: " + args[0]);
                    break;
            }
        }

        @Override
        protected List<String> tabComplete(CommandSender sender, String[] args) {
            if (args.length == 1) {
                List<String> completions = new ArrayList<>();
                completions.add("info");
                completions.add("player");
                completions.add("location");
                return completions;
            }
            return new ArrayList<>();
        }
    }

    /**
     * Default event listener for basic functionality
     */
    private static class DefaultEventListener extends SpigotListener {

        public DefaultEventListener(JavaPlugin plugin) {
            super(plugin);
        }

        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent event) {
            Player player = event.getPlayer();
            plugin.getLogger().info("Player " + player.getName() + " joined the server");

            // Example: Send welcome message
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                SpigotPlayerUtils.sendMessage(player, "&aWelcome to the server, " + player.getName() + "!");
            }, 20L); // 1 second delay
        }

        @EventHandler
        public void onPlayerQuit(PlayerQuitEvent event) {
            Player player = event.getPlayer();
            plugin.getLogger().info("Player " + player.getName() + " left the server");
        }
    }
}
