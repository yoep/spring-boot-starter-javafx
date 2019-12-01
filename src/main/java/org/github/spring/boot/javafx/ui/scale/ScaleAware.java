package org.github.spring.boot.javafx.ui.scale;

import javafx.scene.Scene;

/**
 * Defines that a view controller is aware of the scale factor and the view is able to be scaled accordingly.
 */
public interface ScaleAware {
    /**
     * Scale the given scene according to the scale factor.
     *
     * @param scene The current scene to use for scaling.
     * @param scale Set the scale of the UI.
     */
    void scale(Scene scene, float scale);
}
