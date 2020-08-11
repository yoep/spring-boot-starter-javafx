package com.github.spring.boot.javafx.stage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class BorderlessStageWrapperTest {
    @Test
    void testConstructor_whenStageArgumentIsNull_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new BorderlessStageWrapper(null), "stage cannot be null");
    }
}
