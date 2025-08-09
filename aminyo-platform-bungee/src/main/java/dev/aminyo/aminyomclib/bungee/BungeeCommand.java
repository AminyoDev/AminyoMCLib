package dev.aminyo.aminyomclib.bungee;

import dev.aminyo.aminyomclib.utils.TextUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BungeeCord command base class for easy command creation
 */
public abstract class BungeeCommand extends net.md_5.bungee.api.plugin.Command {

    protected final AminyoBungeePlugin plugin;
    protected final Logger logger;

    public BungeeCommand(String name, String permission, String... aliases) {
        super(name, permission, aliases);
        this.plugin = AminyoBungeePlugin.getInstance();
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public BungeeCommand(String name) {
        this(name, null);
    }

    @Override
    public final void execute(CommandSender sender, String[] args) {
        try {
            BungeeCommandSender wrappedSender = new BungeeCommandSender(sender);
            onCommand(wrappedSender, args);
        } catch (Exception e) {
            logger.error("Error executing command: {}", getName(), e);
            sender.sendMessage(TextComponent.fromLegacyText(
                    TextUtils.colorize("&cAn error occurred while executing this command.")));
        }
    }

    /**
     * Handle command execution
     */
    public abstract void onCommand(BungeeCommandSender sender, String[] args);

    /**
     * Register this command
     */
    public void register() {
        ProxyServer.getInstance().getPluginManager().registerCommand(plugin, this);
    }
}
