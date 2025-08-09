package dev.aminyo.aminyomclib.core.interfaces;

import dev.aminyo.aminyomclib.core.exceptions.AminyoException;

public interface Configurable {
    /**
     * Reload configuration
     */
    void reload() throws AminyoException;

    /**
     * Save configuration
     */
    void save() throws AminyoException;

    /**
     * Check if configuration is loaded
     * @return true if loaded
     */
    boolean isLoaded();
}
