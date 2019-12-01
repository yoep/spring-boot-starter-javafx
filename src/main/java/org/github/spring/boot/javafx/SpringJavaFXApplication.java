package org.github.spring.boot.javafx;

import javafx.application.Application;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

/**
 * Abstract Spring Boot extension of {@link Application}.
 */
public abstract class SpringJavaFXApplication extends Application {
    private static String[] ARGUMENTS;

    protected ApplicationContext applicationContext;

    public static void launch(Class<? extends Application> appClass, String... args) {
        ARGUMENTS = args;
        Application.launch(appClass, args);
    }

    public static void launch(String... args) {
        ARGUMENTS = args;
        Application.launch(args);
    }

    @Override
    public void init() {
        SpringApplication application = new SpringApplication(this.getClass());
        application.setBannerMode(Banner.Mode.OFF);
        applicationContext = application.run(ARGUMENTS);
    }
}
