package com.github.spring.boot.javafx.stage;

import javafx.application.Platform;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ApplicationExtension.class)
class BorderlessStageTest {
    @Test
    void testSetHeader() {
        var expectedResult = 10.0;
        var stage = new AtomicReference<BorderlessStage>();
        Platform.runLater(() -> stage.set(new BorderlessStage()));
        WaitForAsyncUtils.waitForFxEvents();

        stage.get().setHeader(expectedResult);

        assertEquals(expectedResult, stage.get().getHeader());
    }

    @Test
    void testHeaderProperty() {
        var expectedResult = 15.0;
        var stage = new AtomicReference<BorderlessStage>();
        var headerHolder = new AtomicReference<Double>();
        Platform.runLater(() -> stage.set(new BorderlessStage()));
        WaitForAsyncUtils.waitForFxEvents();

        stage.get().headerProperty().addListener((observable, oldValue, newValue) -> {
            headerHolder.set(newValue.doubleValue());
        });
        stage.get().setHeader(expectedResult);
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(expectedResult, headerHolder.get());
    }

    @Test
    void testSetResizeBorder() {
        var expectedResult = 5.0;
        var stage = new AtomicReference<BorderlessStage>();
        Platform.runLater(() -> stage.set(new BorderlessStage()));
        WaitForAsyncUtils.waitForFxEvents();

        stage.get().setResizeBorder(expectedResult);

        assertEquals(expectedResult, stage.get().getResizeBorder());
    }
}