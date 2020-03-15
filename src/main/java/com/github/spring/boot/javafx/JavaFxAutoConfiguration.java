package com.github.spring.boot.javafx;

import com.github.spring.boot.javafx.font.config.FontConfiguration;
import com.github.spring.boot.javafx.text.config.LocaleTextConfiguration;
import com.github.spring.boot.javafx.view.config.ViewConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * The auto configuration for JavaFX.
 */
@Configuration
@Import({
        FontConfiguration.class,
        LocaleTextConfiguration.class,
        ViewConfiguration.class
})
public class JavaFxAutoConfiguration {
}
