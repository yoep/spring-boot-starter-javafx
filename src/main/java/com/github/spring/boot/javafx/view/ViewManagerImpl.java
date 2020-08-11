package com.github.spring.boot.javafx.view;

import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@EqualsAndHashCode
public class ViewManagerImpl implements ViewManager {
    public static final String PRIMARY_STAGE_PROPERTY = "primaryStage";
    public static final String POLICY_PROPERTY = "policy";

    private final ConfigurableApplicationContext applicationContext;
    private final List<Window> windows = new ArrayList<>();
    private final Property<Stage> primaryStage = new SimpleObjectProperty<>(this, PRIMARY_STAGE_PROPERTY);
    private final Property<ViewManagerPolicy> policy = new SimpleObjectProperty<>(this, POLICY_PROPERTY, ViewManagerPolicy.CLOSEABLE);

    //region Constructors

    public ViewManagerImpl(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    //endregion

    //region Properties

    @Override
    public Optional<Stage> getPrimaryStage() {
        return Optional.ofNullable(primaryStage.getValue());
    }

    @Override
    public ReadOnlyProperty<Stage> primaryStageProperty() {
        return primaryStage;
    }

    @Override
    public Optional<Stage> getStage(String name) {
        return windows.stream()
                .filter(e -> e.getStage().getTitle().equalsIgnoreCase(name))
                .findFirst()
                .map(Window::getStage);
    }

    @Override
    public ViewManagerPolicy getPolicy() {
        return policy.getValue();
    }

    @Override
    public Property<ViewManagerPolicy> policyProperty() {
        return policy;
    }

    @Override
    public void setPolicy(ViewManagerPolicy policy) {
        this.policy.setValue(policy);
    }

    @Override
    public int getTotalWindows() {
        return windows.size();
    }

    //endregion

    //region Methods

    @Override
    public void registerPrimaryStage(Stage primaryStage) {
        Assert.notNull(primaryStage, "primaryStage cannot be null");
        if (getPrimaryStage().isPresent()) {
            log.warn("Ignoring primary stage register as one has already been registered");
            return;
        }

        this.primaryStage.setValue(primaryStage);
        addWindowView(primaryStage, primaryStage.getScene(), true);
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

            if (policy.getValue() == ViewManagerPolicy.CLOSEABLE) {
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

