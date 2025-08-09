package dev.aminyo.aminyomclib.velocity;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.aopalliance.intercept.Invocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * Velocity command base class for easy command creation
 */
public abstract class VelocityCommand implements SimpleCommand {

    protected final AminyoVelocityPlugin plugin;
    protected final Logger logger;
    protected final String name;
    protected final String permission;

    public VelocityCommand(String name, String permission) {
        this.name = name;
        this.permission = permission;
        this.plugin = AminyoVelocityPlugin.getInstance();
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public VelocityCommand(String name) {
        this(name, null);
    }

    @Override
    public final void execute(Invocation invocation) {
        try {
            CommandSource source = invocation.source();
            String[] args = invocation.arguments();

            // Permission check
            if (permission != null && !source.hasPermission(permission)) {
                sendMessage(source, "&cYou don't have permission to use this command!");
                return;
            }

            VelocityCommandSender wrappedSender = new VelocityCommandSender(source);
            onCommand(wrappedSender, args);

        } catch (Exception e) {
            logger.error("Error executing command: {}", name, e);
            Component errorMessage = LegacyComponentSerializer.legacyAmpersand()
                    .deserialize("&cAn error occurred while executing this command.");
            invocation.source().sendMessage(errorMessage);
        }
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        try {
            CommandSource source = invocation.source();
            String[] args = invocation.arguments();

            if (permission != null && !source.hasPermission(permission)) {
                return Collections.emptyList();
            }

            VelocityCommandSender wrappedSender = new VelocityCommandSender(source);
            return tabComplete(wrappedSender, args);

        } catch (Exception e) {
            logger.warn("Error in tab completion for command: {}", name, e);
            return Collections.emptyList();
        }
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return permission == null || invocation.source().hasPermission(permission);
    }

    /**
     * Handle command execution
     */
    public abstract void onCommand(VelocityCommandSender sender, String[] args);

    /**
     * Handle tab completion
     */
    public List<String> tabComplete(VelocityCommandSender sender, String[] args) {
        return Collections.emptyList();
    }

    /**
     * Send message to command source
     */
    protected void sendMessage(CommandSource source, String message) {
        Component component = LegacyComponentSerializer.legacyAmpersand().deserialize(message);
        source.sendMessage(component);
    }

    /**
     * Register this command
     */
    public void register() {
        plugin.getServer().getCommandManager().register(name, this);
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }
}
