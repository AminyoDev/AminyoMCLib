package dev.aminyo.aminyomclib.core;

import dev.aminyo.aminyomclib.core.exceptions.AminyoException;
import dev.aminyo.aminyomclib.core.exceptions.AminyoRuntimeException;
import dev.aminyo.aminyomclib.core.interfaces.Configurable;
import dev.aminyo.aminyomclib.core.interfaces.Toggleable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AminyoModule implements Configurable, Toggleable {

    protected final Logger logger;
    protected final String moduleName;
    protected boolean enabled;
    protected boolean loaded;

    protected AminyoModule(String moduleName) {
        this.moduleName = moduleName;
        this.logger = LoggerFactory.getLogger(getClass());
        this.enabled = false;
        this.loaded = false;
    }

    /**
     * Get module name
     * @return module name
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * Get module logger
     * @return logger instance
     */
    public Logger getLogger() {
        return logger;
    }

    @Override
    public void enable() throws AminyoRuntimeException {
        if (!enabled) {
            try {
                onEnable();
                enabled = true;
                logger.info("Module '{}' enabled", moduleName);
            } catch (Exception e) {
                logger.error("Failed to enable module '{}'", moduleName, e);
                throw new AminyoRuntimeException("Failed to enable module: " + moduleName, e);
            }
        }
    }

    @Override
    public void disable() {
        if (enabled) {
            try {
                onDisable();
                enabled = false;
                logger.info("Module '{}' disabled", moduleName);
            } catch (Exception e) {
                logger.error("Failed to disable module '{}'", moduleName, e);
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isLoaded() {
        return loaded;
    }

    /**
     * Called when module is being enabled
     */
    protected abstract void onEnable() throws AminyoException;

    /**
     * Called when module is being disabled
     */
    protected abstract void onDisable() throws AminyoException;
}
