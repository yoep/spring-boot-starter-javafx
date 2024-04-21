package com.github.spring.boot.javafx.text.config;

import com.github.spring.boot.javafx.text.LocaleText;
import com.github.spring.boot.javafx.text.LocaleTextImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Configuration class for managing locale-specific text resources.
 * This class provides a bean definition for LocaleText if one is not already present,
 * using a ResourceBundleMessageSource as the underlying message source.
 */
@Configuration
public class LocaleTextConfiguration {

    /**
     * Defines a bean for LocaleText if no other bean of type LocaleText is present,
     * using the provided ResourceBundleMessageSource as the message source.
     *
     * @param messageSource the ResourceBundleMessageSource instance to use for managing message sources
     * @return the LocaleText bean instance
     */
    @Bean
    @ConditionalOnMissingBean(LocaleText.class)
    public LocaleText localeText(ResourceBundleMessageSource messageSource) {
        return new LocaleTextImpl(messageSource);
    }
}
