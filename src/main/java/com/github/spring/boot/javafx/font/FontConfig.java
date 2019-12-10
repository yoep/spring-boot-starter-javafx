package com.github.spring.boot.javafx.font;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FontConfig {
    @Bean
    public FontRegistry fontRegistry() {
        return FontRegistry.getInstance();
    }
}
