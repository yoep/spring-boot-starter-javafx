package com.github.spring.boot.javafx;

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

    /**
     * Launch a JavaFX application with for the given class and program arguments.
     *
     * @param appClass The class to launch the JavaFX application for.
     * @param args The program arguments.
     */
    @SuppressWarnings("unused")
    public static void launch(Class<? extends Application> appClass, String... args) {
        ARGUMENTS = args;
        Application.launch(appClass, args);
    }

    /**
     * Launch a JavaFX application with the given program arguments.
     *
     * @param args The program arguments.
     */
    @SuppressWarnings("unused")
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
