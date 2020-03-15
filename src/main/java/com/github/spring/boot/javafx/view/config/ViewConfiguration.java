package com.github.spring.boot.javafx.view.config;

import com.github.spring.boot.javafx.text.LocaleTextImpl;
import com.github.spring.boot.javafx.view.ViewLoader;
import com.github.spring.boot.javafx.view.ViewManager;
import com.github.spring.boot.javafx.view.ViewLoaderImpl;
import com.github.spring.boot.javafx.view.ViewManagerImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ViewConfiguration {
    @Bean
    @ConditionalOnMissingBean(ViewLoader.class)
    public ViewLoader viewLoader(ApplicationContext applicationContext, ViewManager viewManager, LocaleTextImpl localeText) {
        return new ViewLoaderImpl(applicationContext, viewManager, localeText);
    }

    @Bean
    @ConditionalOnMissingBean(ViewManager.class)
    public ViewManager viewManager(ConfigurableApplicationContext applicationContext) {
        return new ViewManagerImpl(applicationContext);
    }
}
