package com.github.spring.boot.javafx.font.config;

import com.github.spring.boot.javafx.font.FontRegistry;
import com.github.spring.boot.javafx.font.FontRegistryImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for configuring fonts in the application.
 * This class provides a bean definition for FontRegistry if one is not already present.
 */
@Configuration
public class FontConfiguration {

    /**
     * Defines a bean for FontRegistry if no other bean of type FontRegistry is present.
     *
     * @return the FontRegistry bean instance
     */
    @Bean
    @ConditionalOnMissingBean(FontRegistry.class)
    public FontRegistry fontRegistry() {
        return FontRegistryImpl.getInstance();
    }
}
