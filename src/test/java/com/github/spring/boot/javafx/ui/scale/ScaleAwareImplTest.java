package com.github.spring.boot.javafx.ui.scale;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.stage.Window;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScaleAwareImplTest {
    @Mock
    private Scene scene;
    @Mock
    private Region root;
    @Mock
    private Window window;
    @Mock
    private ObservableList<Transform> transforms;
    private MyController controller;

    @BeforeEach
    void setUp() {
        lenient().when(root.getTransforms()).thenReturn(transforms);
        lenient().when(scene.getWindow()).thenReturn(window);

        controller = new MyController();
    }

    @Test
    void testScale_whenSceneIsMissing_shouldThrowMissingScaleAwarePropertyException() {
        assertThrows(MissingScaleAwarePropertyException.class, () -> controller.scale(null, root, 1.0f), "Missing scene to execute scale aware logic");
    }

    @Test
    void testScale_whenInitialScaleIsGiven_shouldInitializeSceneWithTheGivenScale() {
        float scale = 1.5f;
        AtomicReference<Scale> scaleHolder = new AtomicReference<>();
        when(transforms.setAll(isA(Scale.class))).thenAnswer(invocation -> {
            scaleHolder.set(invocation.getArgument(0, Scale.class));
            return true;
        });
        when(scene.widthProperty()).thenReturn(new SimpleDoubleProperty());
        when(scene.heightProperty()).thenReturn(new SimpleDoubleProperty());

        controller.scale(scene, root, scale);
        Scale result = scaleHolder.get();

        assertNotNull(result, "Expected a scale to have been set on the region");
        assertEquals(scale, result.getX());
        assertEquals(scale, result.getY());
        assertEquals(0, result.getPivotX());
        assertEquals(0, result.getPivotY());
    }

    @Test
    void testOnScaleChanged_whenNewValueIsGiven_shouldUpdateTheScaleWithTheNewValue() {
        float initialScale = 1.0f;
        float expectedScale = 1.8f;
        AtomicReference<Scale> scaleHolder = new AtomicReference<>();
        when(transforms.setAll(isA(Scale.class))).thenAnswer(invocation -> {
            scaleHolder.set(invocation.getArgument(0, Scale.class));
            return true;
        });
        when(scene.widthProperty()).thenReturn(new SimpleDoubleProperty());
        when(scene.heightProperty()).thenReturn(new SimpleDoubleProperty());

        controller.scale(scene, root, initialScale);
        controller.onScaleChanged(expectedScale);
        Scale scale = scaleHolder.get();

        assertNotNull(scale, "Expected the scale to have been set on the region");
        assertEquals(expectedScale, scale.getX());
        assertEquals(expectedScale, scale.getY());
    }

    static class MyController extends ScaleAwareImpl {
    }
}