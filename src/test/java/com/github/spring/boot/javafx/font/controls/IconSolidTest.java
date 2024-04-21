package com.github.spring.boot.javafx.font.controls;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
class IconSolidTest {

    @Test
    void testNewInstance() {
        var result = new IconSolid();

        assertTrue(result.getStyleClass().contains(AbstractIcon.STYLE_CLASS));
    }

    @Test
    void testNewInstanceWithUnicode() {
        var unicode = IconSolid.AD_UNICODE;

        var result = new IconSolid(unicode);

        assertTrue(result.getStyleClass().contains(AbstractIcon.STYLE_CLASS));
        assertEquals(unicode, result.getText());
    }
}