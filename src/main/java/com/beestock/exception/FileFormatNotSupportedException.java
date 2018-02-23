package com.beestock.exception;

/**
 * This class extends {@code Exception} with some custom message.
 *
 * It throws a message that indicates a not supported footage format.
 */
public class FileFormatNotSupportedException extends Exception {
    public FileFormatNotSupportedException(String extension) {
        super(String.format("Footage extension '%s' is not supported", extension));
    }
}
