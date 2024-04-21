package com.github.spring.boot.javafx.font.controls;

import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class IconTest {
    public static final String FONT_FAMILY = "FontAwesome";

    @Test
    void testNewInstance() {
        var result = new Icon();

        assertTrue(result.getStyleClass().contains(AbstractIcon.STYLE_CLASS));
        assertEquals(FONT_FAMILY, result.getFont().getFamily());
    }

    @Test
    void testNewInstanceWithUnicode() {
        var unicode = Icon.ADJUST_UNICODE;

        var result = new Icon(unicode);

        assertTrue(result.getStyleClass().contains(AbstractIcon.STYLE_CLASS));
        assertEquals(unicode, result.getText());
    }

    @Test
    void testNewInstanceWithBuilder() {
        var padding = new Insets(10, 20, 30, 40);

        var result = Icon.builder()
                .unicode(Icon.AMAZON_UNICODE)
                .padding(padding)
                .visible(true)
                .build();

        assertTrue(result.getStyleClass().contains(AbstractIcon.STYLE_CLASS));
        assertEquals(Icon.AMAZON_UNICODE, result.getText());
        assertEquals(padding, result.getPadding());
        assertTrue(result.isVisible());
        assertEquals(FONT_FAMILY, result.getFont().getFamily(), "expected the font family to not have been changed");
    }

    @Test
    void testSizeFactor() {
        var icon = new Icon();
        assertEquals(0.0, icon.getSizeFactor(), "expected the initial size factor to be 0.0");

        var newSizeFactor = 2.3;
        icon.setSizeFactor(newSizeFactor);
        assertEquals(newSizeFactor, icon.getSizeFactor(), "expected the size factor to be 2.3");
        assertEquals(FONT_FAMILY, icon.getFont().getFamily(), "expected the font family to not have been changed");
    }

    @Test
    void testTextColor() {
        var icon = new Icon();
        assertEquals(Color.BLACK, icon.getTextFill(), "expected the initial text color to be black");

        icon.setColor(Color.RED);
        assertEquals(Color.RED, icon.getTextFill(), "expected the text color to be red");
        assertEquals(FONT_FAMILY, icon.getFont().getFamily(), "expected the font family to not have been changed");
    }
}