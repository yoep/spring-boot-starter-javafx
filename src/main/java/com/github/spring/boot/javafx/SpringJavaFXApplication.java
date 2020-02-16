package com.github.spring.boot.javafx;

import com.github.spring.boot.javafx.view.ViewManager;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.stage.Stage;
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
     * @param args     The program arguments.
     */
    @SuppressWarnings("unused")
    public static void launch(Class<? extends Application> appClass, String... args) {
        ARGUMENTS = args;
        Application.launch(appClass, args);
    }

    /**
     * Launch a JavaFX application with for the given class and program arguments.
     *
     * @param appClass       The class to launch the JavaFX application for.
     * @param preloaderClass The class to use as the preloader of the JavaFX application.
     * @param args           The program arguments.
     */
    @SuppressWarnings("unused")
    public static void launch(Class<? extends Application> appClass, Class<? extends Preloader> preloaderClass, String... args) {
        ARGUMENTS = args;
        System.setProperty("javafx.preloader", preloaderClass.getName());
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
        application.setHeadless(false);
        applicationContext = application.run(ARGUMENTS);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ViewManager viewManager = applicationContext.getBean(ViewManager.class);
        viewManager.registerPrimaryStage(primaryStage);
    }
}
