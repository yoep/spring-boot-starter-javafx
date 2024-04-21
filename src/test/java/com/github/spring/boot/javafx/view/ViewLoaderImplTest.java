package com.github.spring.boot.javafx.view;

import com.github.spring.boot.javafx.JavaFxAutoConfiguration;
import com.github.spring.boot.javafx.TestConfiguration;
import com.github.spring.boot.javafx.controllers.MyTestController;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testfx.framework.junit5.ApplicationExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(
        classes = {
                JavaFxAutoConfiguration.class,
                TestConfiguration.class
        },
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@ExtendWith({MockitoExtension.class, ApplicationExtension.class})
public class ViewLoaderImplTest {
    @Autowired
    ViewLoader viewLoader;
    @Autowired
    MyTestController controller;

    @AfterEach
    void tearDown() {
        controller.setRoot(null);
    }

    @Test
    void testLoadWithoutController() {
        var result = viewLoader.load("load_view_with_controller.fxml");

        assertNotNull(result);
        assertNotNull(controller.getRoot(), "expected a root node to have been wired");
    }

    @Test
    void testLoadWithController() {
        var controller = new MyController();

        var result = viewLoader.load("load_view_without_controller.fxml", controller);

        assertNotNull(result);
        assertNotNull(controller.root, "expected a root node to have been wired");
    }

    public static class MyController {
        @FXML
        Pane root;
    }
}