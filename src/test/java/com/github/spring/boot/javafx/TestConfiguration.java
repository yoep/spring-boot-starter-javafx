package com.github.spring.boot.javafx;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class TestConfiguration {
    @Bean
    public ResourceBundleMessageSource resourceBundleMessageSource() {
        return new ResourceBundleMessageSource();
    }
}
