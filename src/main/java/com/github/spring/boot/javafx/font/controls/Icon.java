package com.github.spring.boot.javafx.font.controls;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import lombok.Builder;

import java.util.List;

/**
 * Fontawesome v4.3 icon.
 */
@SuppressWarnings("unused")
public class Icon extends AbstractIcon {
    //region Unicodes

    public static final String CIRCLE_UNICODE = "\uf111";
    public static final String EYE_UNICODE = "\uf06e";
    public static final String HEART_UNICODE = "\uf004";
    public static final String MAGNET_UNICODE = "\uf076";
    public static final String STAR_HALF_EMPTY_UNICODE = "\uf123";
    public static final String STAR_HALF_UNICODE = "\uf089";
    public static final String STAR_UNICODE = "\uf005";

    //endregion

    //region Icons

    public static final Icon CIRCLE = new Icon(CIRCLE_UNICODE);
    public static final Icon EYE = new Icon(EYE_UNICODE);
    public static final Icon HEART = new Icon(HEART_UNICODE);
    public static final Icon MAGNET = new Icon(MAGNET_UNICODE);
    public static final Icon STAR = new Icon(STAR_UNICODE);
    public static final Icon STAR_HALF = new Icon(STAR_HALF_UNICODE);
    public static final Icon STAR_HALF_EMPTY = new Icon(STAR_HALF_EMPTY_UNICODE);

    //endregion

    private static final String FILENAME = "fontawesome-webfont.ttf";

    //region Constructors

    /**
     * Instantiate a new Font Awesome icon.
     */
    public Icon() {
        super(FILENAME);
    }

    /**
     * Instantiate a new Font Awesome icon.
     *
     * @param unicode The unicode of the Font Awesome icon.
     */
    public Icon(String unicode) {
        super(FILENAME, unicode);
    }

    //endregion

    @Builder
    public Icon(String unicode, Insets padding, Boolean visible, EventHandler<? super MouseEvent> onMouseClicked, List<String> styleClasses) {
        super(FILENAME);
        setProperty(unicode, this::setText);
        setProperty(padding, this::setPadding);
        setProperty(visible, this::setVisible);
        setProperty(onMouseClicked, this::setOnMouseClicked);
        setProperty(styleClasses, e -> this.getStyleClass().addAll(e));
    }
}
