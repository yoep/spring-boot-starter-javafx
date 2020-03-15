package com.github.spring.boot.javafx.view;

import com.github.spring.boot.javafx.text.LocaleText;
import com.github.spring.boot.javafx.ui.scale.ScaleAware;
import com.github.spring.boot.javafx.ui.size.SizeAware;
import com.github.spring.boot.javafx.ui.stage.StageAware;
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
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Slf4j
public class ViewLoaderImpl implements ViewLoader {
    protected final ApplicationContext applicationContext;
    protected final ViewManager viewManager;
    protected final LocaleText localeText;

    protected float scale = 1f;

    //region Constructors

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

    //endregion

    //region ViewLoader

    @Override
    public void setScale(float scale) {
        if (this.scale == scale)
            return;

        this.scale = scale;
        onScaleChanged(scale);
    }

    @Override
    public void show(String view, ViewProperties properties) {
        Assert.hasText(view, "view cannot be empty");
        Assert.notNull(properties, "properties cannot be null");

        Stage stage = viewManager.getPrimaryStage()
                .orElseThrow(StageNotFoundException::new);
        showScene(stage, view, properties);
    }

    @Override
    public void show(Stage window, String view, ViewProperties properties) {
        Assert.notNull(window, "window cannot be empty");
        Assert.hasText(view, "view cannot be empty");
        Assert.notNull(properties, "properties cannot be null");
        showScene(window, view, properties);
    }

    @Override
    public void showWindow(String view, ViewProperties properties) {
        Assert.hasText(view, "view cannot be empty");
        Assert.notNull(properties, "properties cannot be null");
        Platform.runLater(() -> showScene(new Stage(), view, properties));
    }

    @Override
    public void showWindow(Pane pane, Object controller, ViewProperties properties) {
        Assert.notNull(pane, "pane cannot be null");
        Assert.notNull(controller, "controller cannot be null");
        Assert.notNull(properties, "properties cannot be null");

        Platform.runLater(() -> showScene(new Stage(), new SceneInfo(new Scene(pane), pane, controller), properties));
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

    //endregion

    //region Functions

    /**
     * Load the view component from the {@link FXMLLoader}.
     * This method attaches the available resources of the application to the loader before loading the actual component.
     *
     * @param loader The loader to load the component from.
     * @return Returns the loaded view component on success, else null when the loading failed.
     */
    protected Pane loadComponent(FXMLLoader loader) {
        Assert.notNull(loader, "loader cannot be null");
        loader.setResources(localeText.getResourceBundle());

        try {
            return loader.load();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Prepare the resource view to be loaded.
     * This method will load the resource file from the classpath and prepare the {@link FXMLLoader}.
     *
     * @param view The view file that needs to be loaded.
     * @return Returns the loader for the given view file.
     */
    protected FXMLLoader loadResource(String view) {
        Assert.notNull(view, "view cannot be null");
        ClassPathResource componentResource = new ClassPathResource(ViewLoader.VIEW_DIRECTORY + File.separator + view);

        if (!componentResource.exists())
            throw new ViewNotFoundException(view);

        try {
            return new FXMLLoader(componentResource.getURL());
        } catch (IOException ex) {
            throw new ViewException(ex.getMessage(), ex);
        }
    }

    private SceneInfo loadView(String view, ViewProperties properties) throws ViewNotFoundException {
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

                // check if a background fill color has been defined
                // if so, set the fill color for the scene
                if (properties != null && properties.getBackground() != null)
                    scene.setFill(properties.getBackground());

                return new SceneInfo(scene, root, controller);
            } catch (IllegalStateException ex) {
                throw new ViewNotFoundException(view, ex);
            } catch (IOException ex) {
                log.error("View '" + view + "' is invalid", ex);
                throw new ViewException(view, ex.getMessage(), ex);
            }
        }

        return null;
    }

    private void showScene(Stage window, String view, ViewProperties properties) {
        SceneInfo sceneInfo = loadView(view, properties);

        if (sceneInfo != null) {
            showScene(window, sceneInfo, properties);
        } else {
            log.warn("Unable to show view " + view + " in window " + window);
        }
    }

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
        if (controller instanceof StageAware) {
            initWindowEvents(scene, (StageAware) controller);
        }

        setWindowViewProperties(window, properties);

        if (properties.isDialog()) {
            window.initModality(Modality.APPLICATION_MODAL);
            window.showAndWait();
        } else {
            window.show();
        }
    }

    private void setWindowViewProperties(Stage window, ViewProperties properties) {
        window.setTitle(properties.getTitle());
        window.setMaximized(properties.isMaximized());
        window.setResizable(properties.isResizable());

        Optional.ofNullable(properties.getIcon())
                .filter(StringUtils::isNotBlank)
                .ifPresent(icon -> window.getIcons().add(loadWindowIcon(icon)));

        if (properties.isCenterOnScreen()) {
            centerOnScreen(window);
        }
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

    private void initWindowEvents(Scene scene, StageAware controller) {
        final Stage window = (Stage) scene.getWindow();

        window.setOnShown(event -> controller.onShown(window));
        window.setOnCloseRequest(event -> controller.onClosed(window));
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

    //endregion

    @Getter
    @AllArgsConstructor
    static class SceneInfo {
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
