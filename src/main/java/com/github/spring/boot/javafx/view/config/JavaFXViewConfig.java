package com.github.spring.boot.javafx.view.config;

import com.github.spring.boot.javafx.text.LocaleText;
import com.github.spring.boot.javafx.view.ViewLoader;
import com.github.spring.boot.javafx.view.ViewManager;
import com.github.spring.boot.javafx.view.impl.ViewLoaderImpl;
import com.github.spring.boot.javafx.view.impl.ViewManagerImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaFXViewConfig {
    @Bean
    @ConditionalOnMissingBean
    public ViewLoader viewLoader(ApplicationContext applicationContext, ViewManager viewManager, LocaleText localeText) {
        return new ViewLoaderImpl(applicationContext, viewManager, localeText);
    }

    @Bean
    @ConditionalOnMissingBean
    public ViewManager viewManager(ConfigurableApplicationContext applicationContext) {
        return new ViewManagerImpl(applicationContext);
    }
}
