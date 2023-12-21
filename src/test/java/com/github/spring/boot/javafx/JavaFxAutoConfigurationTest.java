package com.github.spring.boot.javafx;

import com.github.spring.boot.javafx.view.ViewLoader;
import com.github.spring.boot.javafx.view.ViewManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(
        classes = {
                JavaFxAutoConfiguration.class,
                TestConfiguration.class
        }
)
class JavaFxAutoConfigurationTest {
    @Autowired
    private ViewManager viewManager;
    @Autowired
    private ViewLoader viewLoader;

    @Test
    void testAutoConfiguration() {
        assertNotNull(viewManager, "expected a view manager to have been created");
        assertNotNull(viewLoader, "expected a view loader to have been created");
    }
}