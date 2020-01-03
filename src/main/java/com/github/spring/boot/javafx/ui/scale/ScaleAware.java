package com.github.spring.boot.javafx.ui.scale;

import javafx.scene.Scene;
import javafx.scene.layout.Region;

/**
 * Defines that a view controller is aware of the scale factor and the view is able to be scaled accordingly.
 */
public interface ScaleAware {
    /**
     * Scale the given scene according to the scale factor.
     * This method is automatically invoked by the {@link com.github.spring.boot.javafx.view.ViewLoader} when the view is being loaded.
     *
     * @param scene The current scene to use for scaling.
     * @param root  The root region of the FXML file.
     * @param scale Set the scale of the UI.
     */
    void scale(Scene scene, Region root, float scale);

    /**
     * Invoked when the scene scale is being changed to a new scale factor.
     *
     * @param newValue The new scale factor.
     */
    void onScaleChanged(float newValue);
}
