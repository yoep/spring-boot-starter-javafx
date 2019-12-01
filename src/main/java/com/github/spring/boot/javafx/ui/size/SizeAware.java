package com.github.spring.boot.javafx.ui.size;

import javafx.stage.Stage;

/**
 * Defines that the view controller is aware of the size of it's window and can handle size changes within the window.
 */
public interface SizeAware {
    /**
     * Set the initial size of the window.
     *
     * @param window Set the window to set the size on.
     */
    void setInitialSize(Stage window);

    /**
     * Is triggered when the size of the window is changed.
     *
     * @param width       The new width of the window.
     * @param height      The new height of the window.
     * @param isMaximized The maximized state of the window.
     */
    void onSizeChange(Number width, Number height, boolean isMaximized);
}
