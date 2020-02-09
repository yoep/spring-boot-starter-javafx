package com.github.spring.boot.javafx.view;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Optional;

public interface ViewManager {
    /**
     * Get the current policy for the view manager.
     *
     * @return Returns the current policy.
     */
    ViewManagerPolicy getPolicy();

    /**
     * Set the policy for the view manager which it follows when all windows are closed.
     *
     * @param policy Set the policy that needs to be applied.
     */
    void setPolicy(ViewManagerPolicy policy);

    /**
     * Get the total amount of windows which are currently being shown.
     *
     * @return Returns the total amount of shown windows.
     */
    int getTotalWindows();

    /**
     * Initialize the window manager by with the primary stage of the JavaFX application.
     *
     * @param primaryStage The primary stage of JavaFX.
     * @param scene        The scene for the primary stage.
     */
    void initialize(Stage primaryStage, Scene scene);

    /**
     * Get the primary window of the JavaFX application.
     *
     * @return Returns the primary window if present, else {@link Optional#empty()}.
     */
    Optional<Stage> getPrimaryStage();

    /**
     * Get the JavaFX window by the given name.
     *
     * @param name Set the name of the window.
     * @return Returns the window if found, else {@link Optional#empty()}.
     */
    Optional<Stage> getStage(String name);

    /**
     * Register the primary stage of the JavaFX application.
     *
     * @param primaryStage The primary stage to register in this manager.
     */
    void registerPrimaryStage(Stage primaryStage);

    /**
     * Add a new opened window to the manager.
     *
     * @param window Set the new window.
     * @param view   Set the corresponding loaded view of the window.
     */
    void addWindowView(Stage window, Scene view);
}
