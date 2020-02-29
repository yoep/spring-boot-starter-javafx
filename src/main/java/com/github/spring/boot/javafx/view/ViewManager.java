package com.github.spring.boot.javafx.view;

import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyProperty;
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
     * Get the policy property of the view manager.
     *
     * @return Returns the policy property.
     */
    Property<ViewManagerPolicy> policyProperty();

    /**
     * Set the policy for the view manager which it follows when all windows are closed.
     *
     * @param policy Set the policy that needs to be applied.
     */
    void setPolicy(ViewManagerPolicy policy);

    /**
     * Get the primary window of the JavaFX application.
     *
     * @return Returns the primary window if present, else {@link Optional#empty()}.
     */
    Optional<Stage> getPrimaryStage();

    /**
     * Get the primary stage property of the view manager.
     *
     * @return Returns the primary stage property.
     */
    ReadOnlyProperty<Stage> primaryStageProperty();

    /**
     * Get the total amount of windows which are currently being shown.
     *
     * @return Returns the total amount of shown windows.
     */
    int getTotalWindows();

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
