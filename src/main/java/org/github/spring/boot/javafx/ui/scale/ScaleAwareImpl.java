package org.github.spring.boot.javafx.ui.scale;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.transform.Scale;
import javafx.stage.Window;
import org.github.spring.boot.javafx.exceptions.MissingScaleAwarePropertyException;

/**
 * Abstract implementation of {@link ScaleAware} for scaling the scene during initialization.
 */
public abstract class ScaleAwareImpl implements ScaleAware {
    protected float scaleFactor;

    private Scene scene;
    private Region root;

    @Override
    public void scale(Scene scene, float scale) {
        if (scene == null) {
            throw new MissingScaleAwarePropertyException();
        }

        this.scene = scene;
        this.root = (Region) scene.getRoot();
        this.scaleFactor = scale;

        scale();
    }

    private void scale() {
        Window window = scene.getWindow();

        //set initial window size
        window.setWidth(root.getPrefWidth() * scaleFactor);
        window.setHeight(root.getPrefHeight() * scaleFactor);

        //scale the scene by the given scale factor
        scene.setRoot(new Group(root));
        scene.widthProperty().addListener((observable, oldValue, newValue) -> root.setPrefWidth(newValue.doubleValue() * 1 / scaleFactor));
        scene.heightProperty().addListener((observable, oldValue, newValue) -> root.setPrefHeight(newValue.doubleValue() * 1 / scaleFactor));

        Scale scale = new Scale(scaleFactor, scaleFactor);
        scale.setPivotX(0);
        scale.setPivotY(0);
        root.getTransforms().setAll(scale);
    }
}
