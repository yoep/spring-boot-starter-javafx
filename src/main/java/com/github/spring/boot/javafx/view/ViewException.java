package com.github.spring.boot.javafx.view;

import lombok.Getter;

/**
 * Exception which occurred during the loading of a FXML view.
 */
@Getter
public class ViewException extends RuntimeException {
    private String view;

    public ViewException(String message) {
        super(message);
    }

    public ViewException(String view, String message) {
        super(message);
        this.view = view;
    }

    public ViewException(String message, Throwable cause) {
        super(message, cause);
    }

    public ViewException(String view, String message, Throwable cause) {
        super(message, cause);
        this.view = view;
    }
}
