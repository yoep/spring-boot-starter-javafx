package com.github.spring.boot.javafx.view.impl;

import com.github.spring.boot.javafx.view.StageNotFoundException;
import com.github.spring.boot.javafx.view.ViewManager;
import com.github.spring.boot.javafx.view.ViewManagerPolicy;
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
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Optional<Stage> getPrimaryStage() {
        return windows.stream()
                .filter(Window::isPrimaryWindow)
                .map(Window::getStage)
                .findFirst();
    }

    @Override
    public Optional<Stage> getStage(String name) {
        return windows.stream()
                .filter(e -> e.getStage().getTitle().equalsIgnoreCase(name))
                .findFirst()
                .map(Window::getStage);
    }

    @Override
    public void initialize(Stage primaryStage, Scene scene) {
        Assert.notNull(primaryStage, "primaryStage cannot be null");
        Assert.notNull(scene, "scene cannot be null");
        addWindowView(primaryStage, scene, true);
    }

    @Override
    public void registerPrimaryStage(Stage primaryStage) {
        Assert.notNull(primaryStage, "primaryStage cannot be null");
        if (getPrimaryStage().isPresent()) {
            log.warn("Ignoring primary stage register as one has already been registered");
            return;
        }

        this.windows.add(new Window(primaryStage, null, true));
    }

    @Override
    public void addWindowView(Stage window, Scene view) {
        Assert.notNull(window, "window cannot be null");
        Assert.notNull(view, "view cannot be null");
        addWindowView(window, view, false);
    }

    //endregion

    //region Functions

    private void addWindowView(Stage window, Scene view, boolean isPrimaryStage) {
        window.setOnHiding(onWindowClosingEventHandler());
        windows.add(new Window(window, view, isPrimaryStage));
        log.debug("Currently showing " + getTotalWindows() + " window(s)");
    }

    private EventHandler<WindowEvent> onWindowClosingEventHandler() {
        return event -> {
            Stage stage = (Stage) event.getSource();
            Window window = this.windows.stream()
                    .filter(e -> e.getStage() == stage)
                    .findFirst()
                    .orElseThrow(() -> new StageNotFoundException(stage.getTitle()));

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
    private static class Window {
        private final Stage stage;
        private Scene scene;
        private final boolean primaryWindow;
    }
}

