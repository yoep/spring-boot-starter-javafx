package org.github.spring.boot.javafx.view;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.github.spring.boot.javafx.text.Text;
import org.github.spring.boot.javafx.ui.scale.ScaleAware;
import org.github.spring.boot.javafx.ui.size.SizeAware;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;

@Slf4j
@Component
public class ViewLoader {
    public static final String VIEW_DIRECTORY = "/views/";
    private static final String COMPONENT_DIRECTORY = VIEW_DIRECTORY + "components/";
    private static final String IMAGE_DIRECTORY = "/images/";

    private final ApplicationContext applicationContext;
    private final ViewManager viewManager;
    private final Text text;

    /**
     * Intialize a new instance of {@link ViewLoader}.
     *
     * @param applicationContext Set the current application context.
     * @param viewManager        Set the view manager to store the views in.
     * @param text             Set the UI text manager.
     */
    public ViewLoader(ApplicationContext applicationContext, ViewManager viewManager, Text text) {
        this.applicationContext = applicationContext;
        this.viewManager = viewManager;
        this.text = text;
    }

    /**
     * Load and show the given view.
     *
     * @param view Set the view to load and show.
     */
    public void show(String view, ViewProperties properties) {
        Assert.hasText(view, "view cannot be empty");
        Assert.notNull(properties, "properties cannot be null");

        try {
            showScene(viewManager.getPrimaryWindow(), view, properties);
        } catch (PrimaryWindowNotAvailableException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    /**
     * Show the primary scene on the given primary window.
     *
     * @param window     Set the window.
     * @param view       Set the view scene to load.
     * @param properties Set the view properties.
     */
    public void show(Stage window, String view, ViewProperties properties) {
        Assert.notNull(window, "window cannot be empty");
        Assert.hasText(view, "view cannot be empty");
        Assert.notNull(properties, "properties cannot be null");
        showScene(window, view, properties);
    }

    /**
     * Show the given view in a new window.
     *
     * @param view       Set the view to load and show.
     * @param properties Set the properties of the window.
     */
    public void showWindow(String view, ViewProperties properties) {
        Assert.hasText(view, "view cannot be empty");
        Assert.notNull(properties, "properties cannot be null");
        Platform.runLater(() -> showScene(new Stage(), view, properties));
    }

    /**
     * Load the given FXML view and set the given controller.
     *
     * @param componentView The FXML file to load.
     * @param controller    The controller to wire into the view.
     * @return Returns the root pane of the loaded FXML view if successfully loaded, else null.
     */
    public Pane loadComponent(String componentView, Object controller) {
        Assert.hasText(componentView, "componentView cannot be empty");
        Assert.notNull(controller, "controller cannot be null");
        FXMLLoader loader = new FXMLLoader(getClass().getResource(COMPONENT_DIRECTORY + componentView));

        loader.setController(controller);
        return loadComponent(loader);
    }

    /**
     * Load the given FXML view.
     *
     * @param componentView The FXML file to load.
     * @return Returns the root pane of the loaded FXML view if successfully loaded, else null.
     */
    public Pane loadComponent(String componentView) {
        Assert.hasText(componentView, "componentView cannot be empty");
        FXMLLoader loader = new FXMLLoader(getClass().getResource(COMPONENT_DIRECTORY + componentView));

        loader.setControllerFactory(applicationContext::getBean);
        return loadComponent(loader);
    }

    /**
     * Load the given view.
     *
     * @param view Set the view name to load.
     * @return Returns the loaded view.
     * @throws ViewNotFoundException Is thrown when the given view file couldn't be found.
     */
    private SceneInfo load(String view) throws ViewNotFoundException {
        Assert.hasText(view, "view cannot be empty");
        FXMLLoader loader = new FXMLLoader(getClass().getResource(VIEW_DIRECTORY + view), text.getResourceBundle());

        loader.setControllerFactory(applicationContext::getBean);

        try {
            Scene scene = new Scene(loader.load());
            Object controller = loader.getController();

            return new SceneInfo(scene, controller);
        } catch (IllegalStateException ex) {
            throw new ViewNotFoundException(view, ex);
        } catch (IOException ex) {
            log.error("View '" + view + "' is invalid", ex);
        }

        return null;
    }

    private Pane loadComponent(FXMLLoader loader) {
        loader.setResources(text.getResourceBundle());

        try {
            return loader.load();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Show the given scene filename in the given window with the given properties.
     *
     * @param window     Set the window to show the view in.
     * @param view       Set the view to load and render.
     * @param properties Set the view properties.
     */
    private void showScene(Stage window, String view, ViewProperties properties) {
        SceneInfo sceneInfo = load(view);

        if (sceneInfo != null) {
            Scene scene = sceneInfo.getScene();
            Object controller = sceneInfo.getController();

            window.setScene(scene);
            viewManager.addWindowView(window, scene);

            if (controller instanceof ScaleAware) {
                initWindowScale(scene, (ScaleAware) controller);
            }
            if (controller instanceof SizeAware) {
                initWindowSize(scene, (SizeAware) controller);
            }

            setWindowViewProperties(window, properties);

            if (properties.isDialog()) {
                window.initModality(Modality.APPLICATION_MODAL);
                window.showAndWait();
            } else {
                window.show();
            }
        } else {
            log.warn("Unable to show view " + view + " in window " + window);
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
        return new Image(getClass().getResourceAsStream(IMAGE_DIRECTORY + iconName));
    }

    private void initWindowScale(Scene scene, ScaleAware controller) {
        controller.scale(scene, 1f);
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

    @Getter
    @AllArgsConstructor
    private static class SceneInfo {
        private Scene scene;
        private Object controller;
    }
}
