package dev.aminyo.aminyomclib.velocity;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.proxy.Player;
import dev.aminyo.aminyomclib.core.AminyoMCLibImpl;
import dev.aminyo.aminyomclib.core.interfaces.AminyoMCLib;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Example Velocity plugin integration class with test command and listener
 */
public class VelocityAminyoPlugin {

    private AminyoVelocityPlugin plugin;
    private final List<VelocityListener> listeners = new ArrayList<>();
    private final Map<String, VelocityCommand> commands = new HashMap<>();

    public VelocityAminyoPlugin(AminyoVelocityPlugin plugin) {
        this.plugin = plugin;
        initialize();
    }

    private void initialize() {
        // Register default listeners
        registerDefaultListeners();

        // Example: Register a test command
        registerCommand(new TestCommand());

        plugin.getLogger().info("VelocityAminyoPlugin initialized!");
    }

    /**
     * Register a command
     */
    public void registerCommand(VelocityCommand command) {
        commands.put(command.getName(), command);
        command.register();
    }

    /**
     * Register a listener
     */
    public void registerListener(VelocityListener listener) {
        listeners.add(listener);
        listener.register();
    }

    private void registerDefaultListeners() {
        registerListener(new DefaultEventListener());
    }

    /**
     * Example test command
     */
    private class TestCommand extends VelocityCommand {

        public TestCommand() {
            super("vaminyotest", "aminyo.test");
        }

        @Override
        public void onCommand(VelocityCommandSender sender, String[] args) {
            if (args.length == 0) {
                sender.sendMessage("&aAminyoMCLib Test Command for Velocity");
                sender.sendMessage("&7Usage: /vaminyotest <info|player|servers>");
                return;
            }

            switch (args[0].toLowerCase()) {
                case "info":
                    AminyoMCLib lib = AminyoMCLibImpl.getInstance();
                    sender.sendMessage("&aAminyoMCLib Information:");
                    sender.sendMessage("&7Version: " + lib.getVersion());
                    sender.sendMessage("&7Platform: " + lib.getPlatformAdapter().getPlatformType());
                    sender.sendMessage("&7Proxy Version: " + lib.getPlatformAdapter().getMinecraftVersion());
                    break;

                case "player":
                    if (!sender.isPlayer()) {
                        sender.sendMessage("&cThis command can only be used by players!");
                        return;
                    }

                    VelocityPlayer player = sender.asPlayer(plugin.getPlatformAdapter());
                    sender.sendMessage("&aPlayer Information:");
                    sender.sendMessage("&7Name: " + player.getName());
                    sender.sendMessage("&7UUID: " + player.getUniqueId());
                    sender.sendMessage("&7Server: " + player.getServerName());
                    sender.sendMessage("&7Online: " + player.isOnline());
                    break;

                case "servers":
                    Set<String> servers = VelocityUtils.getServerNames(plugin.getServer());
                    sender.sendMessage("&aRegistered Servers (" + servers.size() + "):");
                    for (String server : servers) {
                        int playerCount = VelocityUtils.getPlayerCountOnServer(server, plugin.getServer());
                        sender.sendMessage("&7- " + server + " (" + playerCount + " players)");
                    }
                    break;

                default:
                    sender.sendMessage("&cUnknown subcommand: " + args[0]);
                    break;
            }
        }

        @Override
        public List<String> tabComplete(VelocityCommandSender sender, String[] args) {
            if (args.length == 1) {
                List<String> completions = new ArrayList<>();
                completions.add("info");
                completions.add("player");
                completions.add("servers");
                return completions.stream()
                        .filter(completion -> completion.toLowerCase().startsWith(args[0].toLowerCase()))
                        .collect(Collectors.toList());
            }
            return Collections.emptyList();
        }
    }

    /**
     * Default event listener for basic functionality
     */
    private class DefaultEventListener extends VelocityListener {

        @Subscribe
        public void onPlayerJoin(com.velocitypowered.api.event.connection.LoginEvent event) {
            Player player = event.getPlayer();
            plugin.getLogger().info("Player {} joined the proxy", player.getUsername());
        }

        @Subscribe
        public void onPlayerDisconnect(com.velocitypowered.api.event.connection.DisconnectEvent event) {
            Player player = event.getPlayer();
            plugin.getLogger().info("Player {} left the proxy", player.getUsername());
        }

        @Subscribe
        public void onServerConnected(com.velocitypowered.api.event.player.ServerConnectedEvent event) {
            Player player = event.getPlayer();
            String serverName = event.getServer().getServerInfo().getName();
            plugin.getLogger().info("Player {} connected to server {}", player.getUsername(), serverName);
        }
    }
}