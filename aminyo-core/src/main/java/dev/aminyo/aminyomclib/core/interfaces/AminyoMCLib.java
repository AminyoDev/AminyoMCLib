package it.aminyo.aminyomclib.core.interfaces;

import org.slf4j.Logger;

public interface AminyoMCLib {
    /**
     * Get the current version of AminyoMCLib
     * @return version string
     */
    String getVersion();

    /**
     * Get the logger instance
     * @return logger
     */
    Logger getLogger();

    /**
     * Get the platform adapter
     * @return platform adapter
     */
    PlatformAdapter getPlatformAdapter();

    /**
     * Shutdown the library
     */
    void shutdown();
}
