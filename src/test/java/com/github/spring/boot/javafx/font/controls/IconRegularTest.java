package com.github.spring.boot.javafx.font.controls;

import javafx.geometry.Insets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class IconRegularTest {

    @Test
    void testNewInstance() {
        var result = new IconRegular();

        assertTrue(result.getStyleClass().contains(AbstractIcon.STYLE_CLASS));
    }

    @Test
    void testNewInstanceWithUnicode() {
        var unicode = IconRegular.ANGRY_UNICODE;

        var result = new IconRegular(unicode);

        assertTrue(result.getStyleClass().contains(AbstractIcon.STYLE_CLASS));
        assertEquals(unicode, result.getText());
    }

    @Test
    void testNewInstanceWithBuilder() {
        var unicode = IconRegular.ANGRY_UNICODE;
        var padding = new Insets(12, 20, 30, 40);

        var result = IconRegular.builder()
                .unicode(unicode)
                .padding(padding)
                .visible(false)
                .styleClasses(asList("foo", "bar"))
                .build();

        assertTrue(result.getStyleClass().contains(AbstractIcon.STYLE_CLASS));
        assertEquals(unicode, result.getText());
        assertEquals(padding, result.getPadding());
        assertFalse(result.isVisible());
        assertTrue(result.getStyleClass().contains("foo"));
        assertTrue(result.getStyleClass().contains("bar"));
    }
}