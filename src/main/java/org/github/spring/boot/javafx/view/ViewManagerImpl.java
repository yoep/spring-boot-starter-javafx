package org.github.spring.boot.javafx.view;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.github.spring.boot.javafx.exceptions.PrimaryWindowAlreadyPresentException;
import org.github.spring.boot.javafx.exceptions.PrimaryWindowNotAvailableException;
import org.github.spring.boot.javafx.exceptions.WindowNotFoundException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@EqualsAndHashCode
@Component
@RequiredArgsConstructor
public class ViewManagerImpl implements ViewManager {
    private final List<Window> windows = new ArrayList<>();
    private final ConfigurableApplicationContext applicationContext;

    private ViewManagerPolicy policy = ViewManagerPolicy.CLOSEABLE;


    //region Getters & Setters

    @Override
    public ViewManagerPolicy getPolicy() {
        return policy;
    }

    @Override
    public void setPolicy(ViewManagerPolicy policy) {
        this.policy = policy;
    }

    @Override
    public int getTotalWindows() {
        return windows.size();
    }

    //endregion

    //region Methods

    @Override
    public Stage getPrimaryWindow() throws PrimaryWindowNotAvailableException {
        return windows.stream()
                .filter(Window::isPrimaryWindow)
                .map(Window::getStage)
                .findFirst()
                .orElseThrow(PrimaryWindowNotAvailableException::new);
    }

    @Override
    public Stage getWindow(String name) throws WindowNotFoundException {
        for (Window window : windows) {
            if (window.getStage().getTitle().equals(name)) {
                return window.getStage();
            }
        }

        throw new WindowNotFoundException(name);
    }

    @Override
    public void addWindowView(Stage window, Scene view) {
        Assert.notNull(window, "window cannot be null");
        Assert.notNull(view, "view cannot be null");

        try {
            Field primaryField = Stage.class.getDeclaredField("primary");
            primaryField.setAccessible(true);
            Boolean isPrimaryStage = (Boolean) primaryField.get(window);

            if (isPrimaryStage && isPrimaryWindowAvailable()) {
                throw new PrimaryWindowAlreadyPresentException();
            }

            window.setOnHiding(onWindowClosingEventHandler());
            windows.add(new Window(window, view, isPrimaryStage));
            log.debug("Currently showing " + getTotalWindows() + " window(s)");
        } catch (IllegalAccessException | NoSuchFieldException e) {
            log.error(e.getMessage(), e);
        }
    }

    //endregion

    //region Functions

    private boolean isPrimaryWindowAvailable() {
        return windows.stream().anyMatch(Window::isPrimaryWindow);
    }

    private EventHandler<WindowEvent> onWindowClosingEventHandler() {
        return event -> {
            Stage stage = (Stage) event.getSource();
            Window window = this.windows.stream()
                    .filter(e -> e.getStage() == stage)
                    .findFirst()
                    .orElseThrow(() -> new WindowNotFoundException(stage.getTitle()));

            this.windows.remove(window);
            log.debug("Currently showing " + getTotalWindows() + " window(s)");

            if (policy == ViewManagerPolicy.CLOSEABLE) {
                if (window.isPrimaryWindow()) {
                    log.debug("Application closing, primary window is closed");
                    exitApplication();
                } else if (this.windows.size() == 0) {
                    log.debug("All windows closed, exiting application");
                    exitApplication();
                }
            }
        };
    }

    private void exitApplication() {
        Platform.exit();
        applicationContext.close();
        System.exit(0);
    }

    //endregion

    @Value
    @AllArgsConstructor
    private class Window {
        private Stage stage;
        private Scene scene;
        private boolean primaryWindow;
    }
}

