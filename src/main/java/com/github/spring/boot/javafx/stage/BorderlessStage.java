package com.github.spring.boot.javafx.stage;

import javafx.beans.property.DoubleProperty;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The {@link BorderlessStage} uses the {@link StageStyle#UNDECORATED} style but allows the functionality of
 * moving- and resizing the stage by the user.
 */
public class BorderlessStage extends Stage {
    private final BorderlessStageWrapper stageWrapper;

    //region Constructors

    /**
     * Initialize a new instance of the borderless stage.
     */
    public BorderlessStage() {
        this.stageWrapper = new BorderlessStageWrapper(new Stage());
    }

    //endregion

    //region Properties

    /**
     * Get the header height of the pane which allows the stage to be dragged around.
     *
     * @return Returns the header height of this pane.
     */
    public double getHeader() {
        return stageWrapper.getHeader();
    }

    /**
     * Get the header height property.
     *
     * @return Return the property of the header height.
     */
    public DoubleProperty headerProperty() {
        return stageWrapper.headerProperty();
    }

    /**
     * Set the header height of the pane which allows the stage to be dragged around.
     *
     * @param header The height of the header.
     */
    public void setHeader(double header) {
        stageWrapper.setHeader(header);
    }

    /**
     * Get the width of the resize border.
     *
     * @return Returns the width of the resize border.
     */
    public double getResizeBorder() {
        return stageWrapper.getResizeBorder();
    }

    /**
     * Get the resize border property.
     *
     * @return Returns the resize border property.
     */
    public DoubleProperty resizeBorderProperty() {
        return stageWrapper.resizeBorderProperty();
    }

    /**
     * Set the new width of the resize border.
     *
     * @param resizeBorder The new border resize width.
     */
    public void setResizeBorder(double resizeBorder) {
        stageWrapper.setResizeBorder(resizeBorder);
    }

    //endregion
}
