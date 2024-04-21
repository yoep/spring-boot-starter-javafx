package com.github.spring.boot.javafx;

import com.github.spring.boot.javafx.font.config.FontConfiguration;
import com.github.spring.boot.javafx.text.config.LocaleTextConfiguration;
import com.github.spring.boot.javafx.view.config.ViewConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Configuration class for auto-configuring JavaFX related beans.
 * This class imports other configuration classes responsible for configuring
 * fonts, locale text, and views in a JavaFX application.
 */
@Configuration
@Import({
        FontConfiguration.class,
        LocaleTextConfiguration.class,
        ViewConfiguration.class
})
public class JavaFxAutoConfiguration {
}
