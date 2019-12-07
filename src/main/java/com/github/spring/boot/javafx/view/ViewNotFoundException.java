package com.github.spring.boot.javafx.view;

/**
 * Exception indicating that the view doesn't exist or couldn't be found with the given name.
 */
public class ViewNotFoundException extends ViewException {
    public ViewNotFoundException(String view) {
        super(view, "View '" + view + "' not found");
    }

    public ViewNotFoundException(String view, Exception ex) {
        super(view, "View '" + view + "' not found", ex);
    }
}
