package com.github.spring.boot.javafx.ui.scale;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.transform.Scale;
import javafx.stage.Window;

/**
 * Abstract implementation of {@link ScaleAware} for scaling the scene during initialization.
 */
public abstract class ScaleAwareImpl implements ScaleAware {
    protected float scaleFactor;

    private Scene scene;
    private Region root;
    private Scale scale;

    @Override
    public void scale(Scene scene, Region root, float scale) {
        if (scene == null)
            throw new MissingScaleAwarePropertyException();

        this.scene = scene;
        this.root = root;
        this.scaleFactor = scale;

        initializeScaling();
    }

    @Override
    public void onScaleChanged(float newValue) {
        // check if the initial scaling has already been applied before trying to rescale the scene
        if (scene == null)
            return;

        this.scaleFactor = newValue;
        scale();
    }

    private void initializeScaling() {
        Window window = scene.getWindow();

        // set initial window size
        window.setWidth(root.getPrefWidth() * scaleFactor);
        window.setHeight(root.getPrefHeight() * scaleFactor);

        // scale the scene by the given scale factor
        scene.widthProperty().addListener((observable, oldValue, newValue) -> scaleWidth(newValue.doubleValue()));
        scene.heightProperty().addListener((observable, oldValue, newValue) -> scaleHeight(newValue.doubleValue()));

        // apply a scale transformer to the root region
        scale = new Scale(scaleFactor, scaleFactor);
        scale.setPivotX(0);
        scale.setPivotY(0);
        root.getTransforms().setAll(scale);
    }

    private void scale() {
        scale.setX(this.scaleFactor);
        scale.setY(this.scaleFactor);

        scaleWidth(scene.getWidth());
        scaleHeight(scene.getHeight());
    }

    private void scaleWidth(Double newValue) {
        root.setPrefWidth(newValue * 1 / scaleFactor);
    }

    private void scaleHeight(Double newValue) {
        root.setPrefHeight(newValue * 1 / scaleFactor);
    }
}
