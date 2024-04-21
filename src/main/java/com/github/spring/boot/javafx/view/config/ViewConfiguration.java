package com.github.spring.boot.javafx.view.config;

import com.github.spring.boot.javafx.text.LocaleText;
import com.github.spring.boot.javafx.view.ViewLoader;
import com.github.spring.boot.javafx.view.ViewLoaderImpl;
import com.github.spring.boot.javafx.view.ViewManager;
import com.github.spring.boot.javafx.view.ViewManagerImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for managing views in the application.
 * This class provides bean definitions for ViewLoader and ViewManager
 * if no beans of these types are already present.
 */
@Configuration
public class ViewConfiguration {

    /**
     * Defines a bean for ViewLoader if no other bean of type ViewLoader is present.
     *
     * @param applicationContext the application context
     * @param viewManager the ViewManager instance
     * @param localeText the LocaleText instance
     * @return the ViewLoader bean instance
     */
    @Bean
    @ConditionalOnMissingBean(ViewLoader.class)
    public ViewLoader viewLoader(ApplicationContext applicationContext, ViewManager viewManager, LocaleText localeText) {
        return new ViewLoaderImpl(applicationContext, viewManager, localeText);
    }

    /**
     * Defines a bean for ViewManager if no other bean of type ViewManager is present.
     *
     * @param applicationContext the configurable application context
     * @return the ViewManager bean instance
     */
    @Bean
    @ConditionalOnMissingBean(ViewManager.class)
    public ViewManager viewManager(ConfigurableApplicationContext applicationContext) {
        return new ViewManagerImpl(applicationContext);
    }
}
