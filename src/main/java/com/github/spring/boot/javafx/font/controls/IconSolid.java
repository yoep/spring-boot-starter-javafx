package com.github.spring.boot.javafx.font.controls;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import lombok.Builder;

import java.util.List;

/**
 * Fontawesome v5 solid icon.
 */
public class IconSolid extends AbstractIcon {
    private static final String FILENAME = "fontawesome-solid.ttf";

    public IconSolid() {
        super(FILENAME);
    }

    public IconSolid(String unicode) {
        super(FILENAME, unicode);
    }

    @Builder
    public IconSolid(String unicode, Insets padding, Boolean visible, EventHandler<? super MouseEvent> onMouseClicked, List<String> styleClasses) {
        super(FILENAME);
        setProperty(unicode, this::setText);
        setProperty(padding, this::setPadding);
        setProperty(visible, this::setVisible);
        setProperty(onMouseClicked, this::setOnMouseClicked);
        setProperty(styleClasses, e -> this.getStyleClass().addAll(e));
    }
}
