package org.github.spring.boot.javafx.view;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.github.spring.boot.javafx.exceptions.PrimaryWindowNotAvailableException;
import org.github.spring.boot.javafx.exceptions.WindowNotFoundException;

public interface ViewManager {
    /**
     * Get the current policy for the view manager.
     *
     * @return Returns the current policy.
     */
    ViewManagerPolicy getPolicy();

    /**
     * Set the policy for the view manager which it follows when all windows are closed.
     *
     * @param policy Set the policy that needs to be applied.
     */
    void setPolicy(ViewManagerPolicy policy);

    /**
     * Get the total amount of windows which are currently being shown.
     *
     * @return Returns the total amount of shown windows.
     */
    int getTotalWindows();

    /**
     * Get the primary window of the application.
     *
     * @return Returns the primary window.
     * @throws PrimaryWindowNotAvailableException Is thrown when the primary window is not available yet.
     */
    Stage getPrimaryWindow() throws PrimaryWindowNotAvailableException;

    /**
     * Get the window by the given name.
     *
     * @param name Set the name of the window.
     * @return Returns the found window.
     * @throws WindowNotFoundException Is thrown when the window with the given name couldn't be found.
     */
    Stage getWindow(String name) throws WindowNotFoundException;

    /**
     * Add a new opened window to the manager.
     *
     * @param window Set the new window.
     * @param view   Set the corresponding loaded view of the window.
     */
    void addWindowView(Stage window, Scene view);
}
