package com.github.spring.boot.javafx.view;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public interface ViewLoader {
    /**
     * The directory containing the FXML files.
     */
    String VIEW_DIRECTORY = "views";
    /**
     * The directory containing the component FXML files.
     */
    String COMPONENT_DIRECTORY = "components";
    /**
     * The directory contain
     */
    String IMAGE_DIRECTORY = "images";

    /**
     * Set the UI scale of the views.
     *
     * @param scale The scale value of the ui.
     */
    void setScale(float scale);

    /**
     * Load and show the given view.
     *
     * @param view Set the view to load and show.
     * @return Returns the controller of the loaded view.
     */
    Object show(String view, ViewProperties properties);

    /**
     * Show the primary scene on the given primary window.
     *
     * @param window     Set the window.
     * @param view       Set the view scene to load.
     * @param properties Set the view properties.
     * @return Returns the controller of the loaded view.
     */
    Object show(Stage window, String view, ViewProperties properties);

    /**
     * Show the given view in a new window.
     *
     * @param view       Set the view to load and show.
     * @param properties Set the properties of the window.
     * @return Returns the controller of the loaded view.
     */
    Object showWindow(String view, ViewProperties properties);

    /**
     * Load the given FXML view.
     * Spring will inject the correct controller based on the {@code fx:controller} attribute in the view.
     *
     * @param view The FXML file to load.
     * @return Returns the root pane of the loaded FXML view if successfully loaded, else null.
     */
    Pane load(String view);

    /**
     * Load the given FXML view and use the given controller in this view. (discouraged)
     * Use Spring's {@link org.springframework.context.annotation.Scope} annotation on the {@link org.springframework.stereotype.Controller} instead.
     * This will create a new controller instance each time the view is loaded (and use {@link #load(String)} instead).
     *
     * @param view       The FXML file to load.
     * @param controller The controller to wire into the view.
     * @return Returns the root pane of the loaded FXML view if successfully loaded, else null.
     */
    Pane load(String view, Object controller);

    /**
     * Load the given FXML view.
     * Spring will inject the correct controller based on the {@code fx:controller} attribute in the view.
     *
     * @param componentView The FXML file to load.
     * @return Returns the root pane of the loaded FXML view if successfully loaded, else null.
     * @deprecated Use {@link #load(String)} instead.
     */
    @Deprecated
    Pane loadComponent(String componentView);

    /**
     * Load the given FXML view and use the given controller in this view. (discouraged)
     * Use Spring's {@link org.springframework.context.annotation.Scope} annotation on the {@link org.springframework.stereotype.Controller} instead.
     * This will create a new controller instance each time the component view is loaded (and use {@link #loadComponent(String)} instead).
     *
     * @param componentView The FXML file to load.
     * @param controller    The controller to wire into the view.
     * @return Returns the root pane of the loaded FXML view if successfully loaded, else null.
     * @deprecated Use {@link #load(String, Object)} instead.
     */
    @Deprecated
    Pane loadComponent(String componentView, Object controller);
}
