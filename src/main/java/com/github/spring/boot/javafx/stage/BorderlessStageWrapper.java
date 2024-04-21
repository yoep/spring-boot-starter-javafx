package com.github.spring.boot.javafx.stage;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import org.springframework.lang.Nullable;

import java.util.Objects;

/**
 * The {@link BorderlessStageWrapper} wraps around an existing stage and converts it into a borderless stage.
 * The precondition of the stage is that {@link Stage#isShowing()} needs to be false so the style can be initialized for the stage.
 * If the stage is however already being show, a {@link InvalidStageException} will be thrown.
 */
public class BorderlessStageWrapper {
    public static final String HEADER_PROPERTY = "header";
    public static final String RESIZE_BORDER_PROPERTY = "resizeBorder";

    private final EventHandler<MouseEvent> mouseMovedEventHandler = this::onMouseMoved;
    private final EventHandler<MouseEvent> mousePressedEventHandler = this::onMousePressed;
    private final EventHandler<MouseEvent> mouseDraggedEventHandler = this::onMouseDragged;

    private final DoubleProperty header = new SimpleDoubleProperty(this, HEADER_PROPERTY, 0);
    private final DoubleProperty resizeBorder = new SimpleDoubleProperty(this, RESIZE_BORDER_PROPERTY, 4);
    @Getter
    private final Stage stage;

    private double xOffset;
    private double yOffset;
    private double xStart;
    private double yStart;
    private boolean windowDrag;
    private boolean windowResize;
    private Cursor cursor = Cursor.DEFAULT;

    //region Constructors

    public BorderlessStageWrapper(Stage stage) {
        Objects.requireNonNull(stage, "stage cannot be null");
        this.stage = stage;
        init();
    }

    //endregion

    //region Properties

    /**
     * Get the header height of the pane which allows the stage to be dragged around.
     *
     * @return Returns the header height of this pane.
     */
    public double getHeader() {
        return header.get();
    }

    /**
     * Get the header height property.
     *
     * @return Return the property of the header height.
     */
    public DoubleProperty headerProperty() {
        return header;
    }

    /**
     * Set the header height of the pane which allows the stage to be dragged around.
     *
     * @param header The height of the header.
     */
    public void setHeader(double header) {
        this.header.set(header);
    }

    /**
     * Get the width of the resize border.
     *
     * @return Returns the width of the resize border.
     */
    public double getResizeBorder() {
        return resizeBorder.get();
    }

    /**
     * Get the resize border property.
     *
     * @return Returns the resize border property.
     */
    public DoubleProperty resizeBorderProperty() {
        return resizeBorder;
    }

    /**
     * Set the new width of the resize border.
     *
     * @param resizeBorder The new border resize width.
     */
    public void setResizeBorder(double resizeBorder) {
        this.resizeBorder.set(resizeBorder);
    }

    //endregion

    //region Functions

    /**
     * Invoked when the scene of this {@link BorderlessStage} is being changed.
     *
     * @param oldValue The old scene of the {@link BorderlessStage}.
     * @param newValue The new scene of the {@link BorderlessStage}.
     */
    protected void onSceneChanged(@Nullable Scene oldValue, @Nullable Scene newValue) {
        if (oldValue != null)
            removeSceneListeners(oldValue);

        if (newValue != null)
            addSceneListeners(newValue);
    }

    private void init() {
        initializeStage();
        initializeListeners();
    }

    private void initializeStage() {
        if (stage.isShowing()) {
            throw new InvalidStageException(stage, "Stage is in an invalid state, stage is already being showed");
        }

        stage.initStyle(StageStyle.UNDECORATED);
    }

    private void initializeListeners() {
        stage.sceneProperty().addListener((observable, oldValue, newValue) -> onSceneChanged(oldValue, newValue));
    }

    private void removeSceneListeners(Scene scene) {
        Objects.requireNonNull(scene, "scene cannot be null");
        scene.removeEventHandler(MouseEvent.MOUSE_MOVED, mouseMovedEventHandler);
        scene.removeEventHandler(MouseEvent.MOUSE_PRESSED, mousePressedEventHandler);
        scene.removeEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDraggedEventHandler);
    }

    private void addSceneListeners(Scene scene) {
        Objects.requireNonNull(scene, "scene cannot be null");
        scene.addEventHandler(MouseEvent.MOUSE_MOVED, mouseMovedEventHandler);
        scene.addEventHandler(MouseEvent.MOUSE_PRESSED, mousePressedEventHandler);
        scene.addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDraggedEventHandler);
    }

    private void onMouseMoved(MouseEvent event) {
        // check if the stage is allowed to be resized
        // if not, ignore this event
        if (!isResizeAllowed())
            return;

        double x = event.getSceneX();
        double y = event.getSceneY();
        double width = stage.getWidth();
        double height = stage.getHeight();
        double border = getResizeBorder();

        if (x < border && y < border) {
            cursor = Cursor.NW_RESIZE;
        } else if (x > width - border && y < border) {
            cursor = Cursor.NE_RESIZE;
        } else if (x > width - border && y > height - border) {
            cursor = Cursor.SE_RESIZE;
        } else if (x < border && y > height - border) {
            cursor = Cursor.SW_RESIZE;
        } else if (y < border) {
            cursor = Cursor.N_RESIZE;
        } else if (x > width - border) {
            cursor = Cursor.E_RESIZE;
        } else if (y > height - border) {
            cursor = Cursor.S_RESIZE;
        } else if (x < border) {
            cursor = Cursor.W_RESIZE;
        } else {
            cursor = Cursor.DEFAULT;
        }

        windowResize = cursor != Cursor.DEFAULT;
        stage.getScene().setCursor(cursor);
    }

    private void onMousePressed(MouseEvent event) {
        // check if the stage is in fullscreen
        // if so, ignore this event
        if (stage.isFullScreen()) {
            return;
        }

        xOffset = stage.getX() - event.getScreenX();
        yOffset = stage.getY() - event.getScreenY();
        xStart = stage.getWidth() - event.getSceneX();
        yStart = stage.getHeight() - event.getSceneY();
        windowDrag = isValidWindowDragEvent(event);
    }

    private void onMouseDragged(MouseEvent event) {
        if (windowDrag)
            onWindowDrag(event);
        if (windowResize && isResizeAllowed())
            onWindowResize(event);
    }

    private void onWindowDrag(MouseEvent event) {
        event.consume();

        stage.setX(event.getScreenX() + xOffset);
        stage.setY(event.getScreenY() + yOffset);
    }

    private void onWindowResize(MouseEvent event) {
        event.consume();

        if (cursor == Cursor.E_RESIZE || cursor == Cursor.NE_RESIZE || cursor == Cursor.SE_RESIZE) {
            double newWidth = event.getSceneX() + xStart;

            if (newWidth >= stage.getMinWidth())
                stage.setWidth(newWidth);
        }

        if (cursor == Cursor.S_RESIZE || cursor == Cursor.SE_RESIZE || cursor == Cursor.SW_RESIZE) {
            double newHeight = event.getSceneY() + yStart;

            if (newHeight >= stage.getMinHeight())
                stage.setHeight(newHeight);
        }

        if (cursor == Cursor.W_RESIZE || cursor == Cursor.SW_RESIZE || cursor == Cursor.NW_RESIZE) {
            double width = stage.getX() - event.getScreenX() + stage.getWidth();

            if (width >= stage.getMinWidth()) {
                stage.setX(event.getScreenX());
                stage.setWidth(width);
            }
        }

        if (cursor == Cursor.N_RESIZE || cursor == Cursor.NW_RESIZE || cursor == Cursor.NE_RESIZE) {
            double height = stage.getY() - event.getScreenY() + stage.getHeight();

            if (height >= stage.getMinHeight()) {
                stage.setY(event.getScreenY());
                stage.setHeight(height);
            }
        }
    }

    private boolean isResizeAllowed() {
        return stage.isResizable() && !stage.isFullScreen();
    }

    private boolean isValidWindowDragEvent(MouseEvent event) {
        // check if the mouse event is a valid window drag event
        // the event should be within the header height and not a window resize event
        return !windowResize && event.getSceneY() <= getHeader();
    }

    //endregion
}
