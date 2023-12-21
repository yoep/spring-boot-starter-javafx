package com.github.spring.boot.javafx.font;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class FontRegistryImplTest {
    @Test
    void testGetInstance() {
        var result = FontRegistryImpl.getInstance();

        assertNotNull(result, "expected a font registry instance to have been returned");
    }

    @Test
    void testLoadFont() {
        var registry = FontRegistryImpl.getInstance();

        var font = registry.loadFont("fontawesome-regular.ttf");

        assertNotNull(font, "expected a font to have been returned");
    }
}