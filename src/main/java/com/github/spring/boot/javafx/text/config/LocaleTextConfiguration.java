package com.github.spring.boot.javafx.text.config;

import com.github.spring.boot.javafx.text.LocaleText;
import com.github.spring.boot.javafx.text.LocaleTextImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class LocaleTextConfiguration {
    @Bean
    @ConditionalOnMissingBean(LocaleText.class)
    public LocaleText localeText(ResourceBundleMessageSource messageSource) {
        return new LocaleTextImpl(messageSource);
    }
}
