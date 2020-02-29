package com.github.spring.boot.javafx.ui.stage;

import javafx.stage.Stage;

/**
 * Defines that a view controller is aware of the stage events, such as the stage being shown/closed.
 */
public interface StageAware {
    /**
     * Is triggered when the stage is shown.
     *
     * @param stage The stage that triggered the event.
     */
    void onShown(Stage stage);

    /**
     * Is triggered when the stage is closed.
     *
     * @param stage The stage that triggered the event.
     */
    void onClosed(Stage stage);
}
