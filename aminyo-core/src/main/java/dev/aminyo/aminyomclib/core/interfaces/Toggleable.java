package it.aminyo.aminyomclib.core.interfaces;

import it.aminyo.aminyomclib.core.exceptions.AminyoRuntimeException;

public interface Toggleable {
    /**
     * Enable the component
     */
    void enable() throws AminyoRuntimeException;

    /**
     * Disable the component
     */
    void disable();

    /**
     * Check if component is enabled
     * @return true if enabled
     */
    boolean isEnabled();
}
