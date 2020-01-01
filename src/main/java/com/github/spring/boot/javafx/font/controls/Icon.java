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

    public static final String CARET_DOWN_UNICODE = "\uf0d7";
    public static final String CARET_LEFT_UNICODE = "\uf0d9";
    public static final String CARET_RIGHT_UNICODE = "\uf0da";
    public static final String CARET_UP_UNICODE = "\uf0d8";
    public static final String CHECK_UNICODE = "\uf00c";
    public static final String CIRCLE_UNICODE = "\uf111";
    public static final String COGS_UNICODE = "\uf085";
    public static final String COG_UNICODE = "\uf013";
    public static final String COLLAPSE_UNICODE = "\uf066";
    public static final String COMMENT_UNICODE = "\uf075";
    public static final String CROSS_UNICODE = "\uf00d";
    public static final String EXPAND_UNICODE = "\uf065";
    public static final String EYE_SLASH_UNICODE = "\uf070";
    public static final String EYE_UNICODE = "\uf06e";
    public static final String FOLDER_OPEN_UNICODE = "\uf07c";
    public static final String FOLDER_OPEN_O_UNICODE = "\uf115";
    public static final String FONT_UNICODE = "\uf031";
    public static final String HEART_UNICODE = "\uf004";
    public static final String INBOX_UNICODE = "\uf01c";
    public static final String INFO_CIRCLE_UNICODE = "\uf05a";
    public static final String MAGNET_UNICODE = "\uf076";
    public static final String PAUSE_UNICODE = "\uf04c";
    public static final String PLAY_UNICODE = "\uf04b";
    public static final String RANDOM_UNICODE = "\uf074";
    public static final String SEARCH_UNICODE = "\uf002";
    public static final String STAR_HALF_EMPTY_UNICODE = "\uf123";
    public static final String STAR_HALF_UNICODE = "\uf089";
    public static final String STAR_UNICODE = "\uf005";

    //endregion

    private static final String FILENAME = "fontawesome-webfont.woff";

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
