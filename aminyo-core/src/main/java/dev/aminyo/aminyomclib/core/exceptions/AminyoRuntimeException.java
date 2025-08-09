package dev.aminyo.aminyomclib.core.exceptions;

public class AminyoRuntimeException extends Exception {
    public AminyoRuntimeException(String message) {
        super(message);
    }

    public AminyoRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public AminyoRuntimeException(Throwable cause) {
        super(cause);
    }
}
