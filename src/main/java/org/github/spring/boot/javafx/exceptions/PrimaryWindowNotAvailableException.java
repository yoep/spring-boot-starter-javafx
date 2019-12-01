package org.github.spring.boot.javafx.exceptions;

public class PrimaryWindowNotAvailableException extends Exception {
    public PrimaryWindowNotAvailableException() {
        super("Primary window is not available.");
    }
}
