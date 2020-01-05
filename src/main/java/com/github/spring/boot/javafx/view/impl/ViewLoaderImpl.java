package com.github.spring.boot.javafx.view.impl;

import com.github.spring.boot.javafx.text.LocaleText;
import com.github.spring.boot.javafx.ui.scale.ScaleAware;
import com.github.spring.boot.javafx.ui.size.SizeAware;
import com.github.spring.boot.javafx.view.*;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;

@Slf4j
@Component
public class ViewLoaderImpl implements ViewLoader {
    private final ApplicationContext applicationContext;
    private final ViewManager viewManager;
    private final LocaleText localeText;

    private float scale = 1f;

    /**
     * Initialize a new instance of {@link ViewLoaderImpl}.
     *
     * @param applicationContext Set the current application context.
     * @param viewManager        Set the view manager to store the views in.
     * @param localeText         Set the UI text manager.
     */
    public ViewLoaderImpl(ApplicationContext applicationContext, ViewManager viewManager, LocaleText localeText) {
        this.applicationContext = applicationContext;
        this.viewManager = viewManager;
        this.localeText = localeText;
    }

    @Override
    public void setScale(float scale) {
        if (this.scale == scale)
            return;

        this.scale = scale;
        onScaleChanged(scale);
    }

    @Override
    public Object show(String view, ViewProperties properties) {
        Assert.hasText(view, "view cannot be empty");
        Assert.notNull(properties, "properties cannot be null");
        Stage stage = viewManager.getPrimaryStage()
                .orElseThrow(StageNotFoundException::new);
        SceneInfo sceneInfo = loadView(view);

        showScene(stage, sceneInfo, properties);
        return sceneInfo.getController();
    }

    @Override
    public Object show(Stage window, String view, ViewProperties properties) {
        Assert.notNull(window, "window cannot be empty");
        Assert.hasText(view, "view cannot be empty");
        Assert.notNull(properties, "properties cannot be null");
        SceneInfo sceneInfo = loadView(view);

        showScene(window, sceneInfo, properties);
        return sceneInfo.getController();
    }

    @Override
    public Object showWindow(String view, ViewProperties properties) {
        Assert.hasText(view, "view cannot be empty");
        Assert.notNull(properties, "properties cannot be null");
        SceneInfo sceneInfo = loadView(view);

        showScene(new Stage(), sceneInfo, properties);
        return sceneInfo.getController();
    }

    @Override
    public Pane load(String view) {
        Assert.hasText(view, "view cannot be empty");
        FXMLLoader loader = loadResource(view);

        loader.setControllerFactory(applicationContext::getBean);
        return loadComponent(loader);
    }

    @Override
    public Pane load(String view, Object controller) {
        Assert.hasText(view, "view cannot be empty");
        Assert.notNull(controller, "controller cannot be null");
        FXMLLoader loader = loadResource(view);

        loader.setController(controller);
        return loadComponent(loader);
    }

    @Override
    public Pane loadComponent(String componentView, Object controller) {
        Assert.hasText(componentView, "componentView cannot be empty");
        return load(ViewLoader.COMPONENT_DIRECTORY + File.separator + componentView, controller);
    }

    @Override
    public Pane loadComponent(String componentView) {
        Assert.hasText(componentView, "componentView cannot be empty");
        return load(ViewLoader.COMPONENT_DIRECTORY + File.separator + componentView);
    }

    /**
     * Load the given view.
     *
     * @param view Set the view name to load.
     * @return Returns the loaded view.
     * @throws ViewNotFoundException Is thrown when the given view file couldn't be found.
     */
    private SceneInfo loadView(String view) throws ViewNotFoundException {
        Assert.hasText(view, "view cannot be empty");
        ClassPathResource fxmlResourceFile = new ClassPathResource(ViewLoader.VIEW_DIRECTORY + File.separator + view);

        if (fxmlResourceFile.exists()) {
            FXMLLoader loader;

            try {
                loader = new FXMLLoader(fxmlResourceFile.getURL(), localeText.getResourceBundle());
            } catch (IOException ex) {
                throw new ViewException(view, ex.getMessage(), ex);
            }

            loader.setControllerFactory(applicationContext::getBean);

            try {
                Region root = loader.load();
                Object controller = loader.getController();
                Scene scene;

                if (controller instanceof ScaleAware) {
                    scene = new Scene(new Group(root));
                } else {
                    scene = new Scene(root);
                }

                return new SceneInfo(scene, root, controller);
            } catch (IllegalStateException ex) {
                throw new ViewNotFoundException(view, ex);
            } catch (IOException ex) {
                log.error("View '" + view + "' is invalid", ex);
                throw new ViewException(view, ex.getMessage(), ex);
            }
        }

        throw new ViewNotFoundException(view);
    }

    private Pane loadComponent(FXMLLoader loader) {
        loader.setResources(localeText.getResourceBundle());

        try {
            return loader.load();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    private FXMLLoader loadResource(String view) {
        ClassPathResource componentResource = new ClassPathResource(ViewLoader.VIEW_DIRECTORY + File.separator + view);

        if (!componentResource.exists())
            throw new ViewNotFoundException(view);

        try {
            return new FXMLLoader(componentResource.getURL());
        } catch (IOException ex) {
            throw new ViewException(ex.getMessage(), ex);
        }
    }

    /**
     * Show the given scene filename in the given window with the given properties.
     *
     * @param window     Set the window to show the view in.
     * @param sceneInfo  The scene info to render in the given view.
     * @param properties Set the view properties.
     */
    private void showScene(Stage window, SceneInfo sceneInfo, ViewProperties properties) {
        Scene scene = sceneInfo.getScene();
        Object controller = sceneInfo.getController();

        window.setScene(scene);
        viewManager.addWindowView(window, scene);

        if (controller instanceof ScaleAware) {
            initWindowScale(sceneInfo);
        }
        if (controller instanceof SizeAware) {
            initWindowSize(scene, (SizeAware) controller);
        }

        setWindowViewProperties(window, properties);

        if (properties.isDialog()) {
            window.initModality(Modality.APPLICATION_MODAL);

            Platform.runLater(window::showAndWait);
        } else {
            Platform.runLater(window::show);
        }
    }

    private void setWindowViewProperties(Stage window, ViewProperties properties) {
        if (!properties.isMaximizable()) {
            window.setResizable(false);
        }
        if (StringUtils.isNoneEmpty(properties.getIcon())) {
            window.getIcons().add(loadWindowIcon(properties.getIcon()));
        }
        if (properties.isCenterOnScreen()) {
            centerOnScreen(window);
        }

        window.setTitle(properties.getTitle());
    }

    /**
     * Center the given window on the screen.
     *
     * @param window Set the window to center.
     */
    private void centerOnScreen(Stage window) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        window.setX((screenBounds.getWidth() - window.getWidth()) / 2);
        window.setY((screenBounds.getHeight() - window.getHeight()) / 2);
    }

    private Image loadWindowIcon(String iconName) {
        try {
            ClassPathResource iconResource = new ClassPathResource(ViewLoader.IMAGE_DIRECTORY + File.separator + iconName);

            if (iconResource.exists()) {
                return new Image(iconResource.getInputStream());
            } else {
                throw new ViewException("Icon '" + iconName + "' not found");
            }
        } catch (IOException ex) {
            throw new ViewException(ex.getMessage(), ex);
        }
    }

    private void initWindowScale(SceneInfo sceneInfo) {
        ScaleAware controller = (ScaleAware) sceneInfo.getController();

        controller.scale(sceneInfo.getScene(), sceneInfo.getRoot(), scale);
    }

    private void initWindowSize(Scene scene, SizeAware controller) {
        Stage window = (Stage) scene.getWindow();
        controller.setInitialSize(window);
        window.widthProperty().addListener((observable, oldValue, newValue) -> {
            if (window.isShowing()) {
                controller.onSizeChange(newValue, window.getHeight(), window.isMaximized());
            }
        });
        window.heightProperty().addListener((observable, oldValue, newValue) -> {
            if (window.isShowing()) {
                controller.onSizeChange(window.getWidth(), newValue, window.isMaximized());
            }
        });
        window.maximizedProperty().addListener(((observable, oldValue, newValue) -> {
            if (window.isShowing()) {
                controller.onSizeChange(window.getWidth(), window.getHeight(), newValue);
            }
        }));
    }

    private void onScaleChanged(final float newValue) {
        for (ScaleAware scaleAware : applicationContext.getBeansOfType(ScaleAware.class).values()) {
            try {
                scaleAware.onScaleChanged(newValue);
            } catch (Exception ex) {
                log.error("Failed to invoke scale awareness with error " + ex.getMessage(), ex);
            }
        }
    }

    @Getter
    @AllArgsConstructor
    private static class SceneInfo {
        /**
         * The scene for the loaded FXML file.
         */
        private Scene scene;
        /**
         * The root region of the loaded FXML file.
         */
        private Region root;
        /**
         * The FXML controller that has been created.
         */
        private Object controller;
    }
}
