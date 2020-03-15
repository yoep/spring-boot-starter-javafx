package com.github.spring.boot.javafx.font.config;

import com.github.spring.boot.javafx.font.FontRegistry;
import com.github.spring.boot.javafx.font.FontRegistryImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FontConfiguration {
    @Bean
    @ConditionalOnMissingBean(FontRegistry.class)
    public FontRegistry fontRegistry() {
        return FontRegistryImpl.getInstance();
    }
}
