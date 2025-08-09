package dev.aminyo.aminyomclib.core.exceptions;

public class AminyoException extends Exception{
    public AminyoException(String message) {
        super(message);
    }

    public AminyoException(String message, Throwable cause) {
        super(message, cause);
    }

    public AminyoException(Throwable cause) {
        super(cause);
    }
}
