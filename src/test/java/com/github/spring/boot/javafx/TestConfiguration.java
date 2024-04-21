package com.github.spring.boot.javafx;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@ComponentScan({
        "com.github.spring.boot.javafx.controllers"
})
public class TestConfiguration {
    @Bean
    public ResourceBundleMessageSource resourceBundleMessageSource() {
        return new ResourceBundleMessageSource();
    }
}
