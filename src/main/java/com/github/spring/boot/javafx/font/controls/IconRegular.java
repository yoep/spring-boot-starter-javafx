package com.github.spring.boot.javafx.font.controls;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import lombok.Builder;

import java.util.List;

/**
 * Fontawesome v5 regular icon.
 */
public class IconRegular extends AbstractIcon {
    //region Unicode

    public static final String ADDRESS_BOOK_UNICODE = "\uf2b9";
    public static final String ADDRESS_CARD_UNICODE = "\uf2bb";
    public static final String ANGRY_UNICODE = "\uf556";
    public static final String ARROW_ALT_CIRCLE_DOWN_UNICODE = "\uf358";
    public static final String ARROW_ALT_CIRCLE_LEFT_UNICODE = "\uf359";
    public static final String ARROW_ALT_CIRCLE_RIGHT_UNICODE = "\uf35a";
    public static final String ARROW_ALT_CIRCLE_UP_UNICODE = "\uf35b";
    public static final String BELL_UNICODE = "\uf0f3";
    public static final String BELL_SLASH_UNICODE = "\uf1f6";
    public static final String BOOKMARK_UNICODE = "\uf02e";
    public static final String BUILDING_UNICODE = "\uf1ad";
    public static final String CALENDAR_UNICODE = "\uf133";
    public static final String CALENDAR_ALT_UNICODE = "\uf073";
    public static final String CALENDAR_CHECK_UNICODE = "\uf274";
    public static final String CALENDAR_MINUS_UNICODE = "\uf272";
    public static final String CALENDAR_PLUS_UNICODE = "\uf271";
    public static final String CALENDAR_TIMES_UNICODE = "\uf273";
    public static final String CARET_SQUARE_DOWN_UNICODE = "\uf150";
    public static final String CARET_SQUARE_LEFT_UNICODE = "\uf191";
    public static final String CARET_SQUARE_RIGHT_UNICODE = "\uf152";
    public static final String CARET_SQUARE_UP_UNICODE = "\uf151";
    public static final String CHART_BAR_UNICODE = "\uf080";
    public static final String CHECK_CIRCLE_UNICODE = "\uf058";
    public static final String CHECK_SQUARE_UNICODE = "\uf14a";
    public static final String CIRCLE_UNICODE = "\uf111";
    public static final String CLIPBOARD_UNICODE = "\uf328";
    public static final String CLOCK_UNICODE = "\uf017";
    public static final String CLONE_UNICODE = "\uf24d";
    public static final String CLOSED_CAPTIONING_UNICODE = "\uf20a";
    public static final String COMMENT_UNICODE = "\uf075";
    public static final String COMMENT_ALT_UNICODE = "\uf27a";
    public static final String COMMENT_DOTS_UNICODE = "\uf4ad";
    public static final String COMMENTS_UNICODE = "\uf086";
    public static final String COMPASS_UNICODE = "\uf14e";
    public static final String COPY_UNICODE = "\uf0c5";
    public static final String COPYRIGHT_UNICODE = "\uf1f9";
    public static final String CREDIT_CARD_UNICODE = "\uf09d";
    public static final String DIZZY_UNICODE = "\uf567";
    public static final String DOT_CIRCLE_UNICODE = "\uf192";
    public static final String EDIT_UNICODE = "\uf044";
    public static final String ENVELOPE_UNICODE = "\uf0e0";
    public static final String ENVELOPE_OPEN_UNICODE = "\uf2b6";
    public static final String EYE_UNICODE = "\uf06e";
    public static final String EYE_SLASH_UNICODE = "\uf070";
    public static final String FILE_UNICODE = "\uf15b";
    public static final String FILE_ALT_UNICODE = "\uf15c";
    public static final String FILE_ARCHIVE_UNICODE = "\uf1c6";
    public static final String FILE_AUDIO_UNICODE = "\uf1c7";
    public static final String FILE_CODE_UNICODE = "\uf1c9";
    public static final String FILE_EXCEL_UNICODE = "\uf1c3";
    public static final String FILE_IMAGE_UNICODE = "\uf1c5";
    public static final String FILE_PDF_UNICODE = "\uf1c1";
    public static final String FILE_POWERPOINT_UNICODE = "\uf1c4";
    public static final String FILE_VIDEO_UNICODE = "\uf1c8";
    public static final String FILE_WORD_UNICODE = "\uf1c2";
    public static final String FLAG_UNICODE = "\uf024";
    public static final String FLUSHED_UNICODE = "\uf579";
    public static final String FOLDER_UNICODE = "\uf07b";
    public static final String FOLDER_OPEN_UNICODE = "\uf07c";
    public static final String FROWN_UNICODE = "\uf119";
    public static final String FROWN_OPEN_UNICODE = "\uf57a";
    public static final String FUTBOL_UNICODE = "\uf1e3";
    public static final String GEM_UNICODE = "\uf3a5";
    public static final String GRIMACE_UNICODE = "\uf57f";
    public static final String GRIN_UNICODE = "\uf580";
    public static final String GRIN_ALT_UNICODE = "\uf581";
    public static final String GRIN_BEAM_UNICODE = "\uf582";
    public static final String GRIN_BEAM_SWEAT_UNICODE = "\uf583";
    public static final String GRIN_HEARTS_UNICODE = "\uf584";
    public static final String GRIN_SQUINT_UNICODE = "\uf585";
    public static final String GRIN_SQUINT_TEARS_UNICODE = "\uf586";
    public static final String GRIN_STARS_UNICODE = "\uf587";
    public static final String GRIN_TEARS_UNICODE = "\uf588";
    public static final String GRIN_TONGUE_UNICODE = "\uf589";
    public static final String GRIN_TONGUE_SQUINT_UNICODE = "\uf58a";
    public static final String GRIN_TONGUE_WINK_UNICODE = "\uf58b";
    public static final String GRIN_WINK_UNICODE = "\uf58c";
    public static final String HAND_LIZARD_UNICODE = "\uf258";
    public static final String HAND_PAPER_UNICODE = "\uf256";
    public static final String HAND_PEACE_UNICODE = "\uf25b";
    public static final String HAND_POINT_DOWN_UNICODE = "\uf0a7";
    public static final String HAND_POINT_LEFT_UNICODE = "\uf0a5";
    public static final String HAND_POINT_RIGHT_UNICODE = "\uf0a4";
    public static final String HAND_POINT_UP_UNICODE = "\uf0a6";
    public static final String HAND_POINTER_UNICODE = "\uf25a";
    public static final String HAND_ROCK_UNICODE = "\uf255";
    public static final String HAND_SCISSORS_UNICODE = "\uf257";
    public static final String HAND_SPOCK_UNICODE = "\uf259";
    public static final String HANDSHAKE_UNICODE = "\uf2b5";
    public static final String HDD_UNICODE = "\uf0a0";
    public static final String HEART_UNICODE = "\uf004";
    public static final String HOSPITAL_UNICODE = "\uf0f8";
    public static final String HOURGLASS_UNICODE = "\uf254";
    public static final String ID_BADGE_UNICODE = "\uf2c1";
    public static final String ID_CARD_UNICODE = "\uf2c2";
    public static final String IMAGE_UNICODE = "\uf03e";
    public static final String IMAGES_UNICODE = "\uf302";
    public static final String KEYBOARD_UNICODE = "\uf11c";
    public static final String KISS_UNICODE = "\uf596";
    public static final String KISS_BEAM_UNICODE = "\uf597";
    public static final String KISS_WINK_HEART_UNICODE = "\uf598";
    public static final String LAUGH_UNICODE = "\uf599";
    public static final String LAUGH_BEAM_UNICODE = "\uf59a";
    public static final String LAUGH_SQUINT_UNICODE = "\uf59b";
    public static final String LAUGH_WINK_UNICODE = "\uf59c";
    public static final String LEMON_UNICODE = "\uf094";
    public static final String LIFE_RING_UNICODE = "\uf1cd";
    public static final String LIGHTBULB_UNICODE = "\uf0eb";
    public static final String LIST_ALT_UNICODE = "\uf022";
    public static final String MAP_UNICODE = "\uf279";
    public static final String MEH_UNICODE = "\uf11a";
    public static final String MEH_BLANK_UNICODE = "\uf5a4";
    public static final String MEH_ROLLING_EYES_UNICODE = "\uf5a5";
    public static final String MINUS_SQUARE_UNICODE = "\uf146";
    public static final String MONEY_BILL_ALT_UNICODE = "\uf3d1";
    public static final String MOON_UNICODE = "\uf186";
    public static final String NEWSPAPER_UNICODE = "\uf1ea";
    public static final String OBJECT_GROUP_UNICODE = "\uf247";
    public static final String OBJECT_UNGROUP_UNICODE = "\uf248";
    public static final String PAPER_PLANE_UNICODE = "\uf1d8";
    public static final String PAUSE_CIRCLE_UNICODE = "\uf28b";
    public static final String PLAY_CIRCLE_UNICODE = "\uf144";
    public static final String PLUS_SQUARE_UNICODE = "\uf0fe";
    public static final String QUESTION_CIRCLE_UNICODE = "\uf059";
    public static final String REGISTERED_UNICODE = "\uf25d";
    public static final String SAD_CRY_UNICODE = "\uf5b3";
    public static final String SAD_TEAR_UNICODE = "\uf5b4";
    public static final String SAVE_UNICODE = "\uf0c7";
    public static final String SHARE_SQUARE_UNICODE = "\uf14d";
    public static final String SMILE_UNICODE = "\uf118";
    public static final String SMILE_BEAM_UNICODE = "\uf5b8";
    public static final String SMILE_WINK_UNICODE = "\uf4da";
    public static final String SNOWFLAKE_UNICODE = "\uf2dc";
    public static final String SQUARE_UNICODE = "\uf0c8";
    public static final String STAR_UNICODE = "\uf005";
    public static final String STAR_HALF_UNICODE = "\uf089";
    public static final String STICKY_NOTE_UNICODE = "\uf249";
    public static final String STOP_CIRCLE_UNICODE = "\uf28d";
    public static final String SUN_UNICODE = "\uf185";
    public static final String SURPRISE_UNICODE = "\uf5c2";
    public static final String THUMBS_DOWN_UNICODE = "\uf165";
    public static final String THUMBS_UP_UNICODE = "\uf164";
    public static final String TIMES_CIRCLE_UNICODE = "\uf057";
    public static final String TIRED_UNICODE = "\uf5c8";
    public static final String TRASH_ALT_UNICODE = "\uf2ed";
    public static final String USER_UNICODE = "\uf007";
    public static final String USER_CIRCLE_UNICODE = "\uf2bd";
    public static final String WINDOW_CLOSE_UNICODE = "\uf410";
    public static final String WINDOW_MAXIMIZE_UNICODE = "\uf2d0";
    public static final String WINDOW_MINIMIZE_UNICODE = "\uf2d1";
    public static final String WINDOW_RESTORE_UNICODE = "\uf2d2";

    //endregion

    private static final String FILENAME = "fontawesome-regular.ttf";

    public IconRegular() {
        super(FILENAME);
    }

    public IconRegular(String unicode) {
        super(FILENAME, unicode);
    }

    @Builder
    public IconRegular(String unicode, Insets padding, Boolean visible, EventHandler<? super MouseEvent> onMouseClicked, List<String> styleClasses) {
        super(FILENAME);
        setProperty(unicode, this::setText);
        setProperty(padding, this::setPadding);
        setProperty(visible, this::setVisible);
        setProperty(onMouseClicked, this::setOnMouseClicked);
        setProperty(styleClasses, e -> this.getStyleClass().addAll(e));
    }
}
