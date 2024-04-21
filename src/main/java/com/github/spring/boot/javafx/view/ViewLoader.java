package com.github.spring.boot.javafx.view;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public interface ViewLoader {
    /**
     * The directory containing the FXML files.
     */
    String VIEW_DIRECTORY = "/views";
    /**
     * The directory contain
     */
    String IMAGE_DIRECTORY = "/images";

    /**
     * Set the UI scale of the views.
     *
     * @param scale The scale value of the ui.
     */
    void setScale(float scale);

    /**
     * Load and show the given view.
     *
     * @param view       Set the view to load and show.
     * @param properties The view properties.
     */
    void show(String view, ViewProperties properties);

    /**
     * Show the primary scene on the given primary window.
     *
     * @param window     The window.
     * @param view       The view scene to load.
     * @param properties The view properties.
     */
    void show(Stage window, String view, ViewProperties properties);

    /**
     * Show the given view in a new window.
     *
     * @param view       The FXML file to load and show.
     * @param properties The properties of the window.
     */
    void showWindow(String view, ViewProperties properties);

    /**
     * Show the given pane in a new window.
     *
     * @param pane       The root pane to show in the window.
     * @param controller The controller of the root pane.µ
     * @param properties The properties of the window.
     */
    void showWindow(Pane pane, Object controller, ViewProperties properties);

    /**
     * Load the given FXML view file from the classpath.
     * Spring will inject the correct controller based on the {@code fx:controller} attribute in the view.
     *
     * @param view  The FXML file to load.
     * @param <T>   The root node of the view file.
     * @return Returns the root node of the loaded FXML view if successfully loaded, else null.
     */
    <T extends Node> T load(String view);

    /**
     * Load the given FXML view from the classpath and use the given controller within the loaded view. (discouraged)
     * Use Spring's {@link org.springframework.context.annotation.Scope} annotation on the {@link com.github.spring.boot.javafx.stereotype.ViewController}
     * instead.
     * This will create a new controller instance each time the view is loaded (and use {@link #load(String)} instead).
     *
     * @param view       The FXML file to load.
     * @param controller The controller to wire into the view.
     * @param <T>        The root node of the view file.
     * @return Returns the root node of the loaded FXML view if successfully loaded, else null.
     */
    <T extends Node> T load(String view, Object controller);
}
