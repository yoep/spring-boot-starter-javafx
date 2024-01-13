# Spring Boot JavaFX starter

Spring Boot starter for easy integration with JavaFX.
This library provides easy integration with JavaFX as well as additional helpers for 
loading & managing JavaFX views in Spring.

## Maven

The library is available in the maven central repository and can be used by adding:

_Spring Boot 3.X_
```xml
<dependency>
  <groupId>com.github.yoep</groupId>
  <artifactId>spring-boot-starter-javafx</artifactId>
  <version>2.0.0</version>
</dependency>
```

_Spring Boot 2.X_
```xml
<dependency>
  <groupId>com.github.yoep</groupId>
  <artifactId>spring-boot-starter-javafx</artifactId>
  <version>1.0.12</version>
</dependency>
```

## Requirements

### Version 1.0.0+

This is the legacy version of the library which requires Spring Boot 2+ and Java 8+.

- Spring Boot 2+
- Java 8+

### Version 2.0.0+

This is the new major version of the library which requires Spring Boot 3+ and Java 17+.

- Spring Boot 3+
- Java 17+

## Usage

Create a class which extends `SpringJavaFXApplication` and launch the JavaFX application from this class.

_main entry example_
```java
@SpringBootApplication
public class MySpringApplication extends SpringJavaFXApplication {

    public static void main(String[] args) {                
        launch(MySpringApplication.class, args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        super.start(primaryStage);
        
        // YOUR CODE ON STARTUP HERE
    }
}
```

Create a `ResourceBundle` bean which can be used by JavaFX within `.fxml` files.

_resource bundle example_
```java
@Configuration
public class LanguageConfig {
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("lang/example");
        return messageSource;
    }
}
```

### Borderless stage

You can create a borderless (undecorated) stage which is still resizable 
and draggable by using the `BorderlessStage` class.

This functionality can also be achieved on the primary stage by using
the `BorderlessStageWrapper` as follows:

```java

@SpringBootApplication
public class MySpringApplication extends SpringJavaFXApplication {    
    @Override
    public void start(Stage stage) throws Exception {
        var myBorderlessStage = new BorderlessStageWrapper(stage);
        super.start(stage);
        
        // set the height of the header
        // this height is used within the BorderlessStage
        // to drag the window around
        myBorderlessStage.setHeader(20);
        // set the virtual border of the BorderlessStage
        // this width is used to determine when the user can grab 
        // the non-existing border to resize the borderless stage
        myBorderlessStage.setResizeBorder(2);
    }
}
```

### View controllers

Use the following stereotype to define a view controller.
This is not required as the library will use any bean that is known within the 
`ApplicationContext` to bind them to views.

```java
@ViewController
public class MyViewController {
}
```

### Loading views

There are 2 options when loading views, automatic controller selection through beans
or manually defining the controller that needs to be used by JavaFX.

- Automatically use a controller from the `ApplicationContext`.

```java
private class Example {
    private ViewLoader viewLoader;
    
    private void loadView() {
        viewLoader.load("my-view-file.fxml");
    }
}
```

- Define a controller which needs to be used in the view.

```java
private class Example {
    private ViewLoader viewLoader;
    
    private void loadView() {
        ViewController controller = new ViewController();
        
        viewLoader.load("my-view-file.fxml", controller);
    }
}
```

## FAT jar packaging

It is **recommended to not use** the `spring-boot-maven-plugin` if you want to package the JavaFX application into a fat jar.
The reason behind this, is that the plugin will break JavaFX due to the JAR layout that is used by the plugin.

For creating fat jar packages, use the `maven-shade-plugin` instead.

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <configuration>
        <keepDependenciesWithProvidedScope>false</keepDependenciesWithProvidedScope>
    </configuration>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
            <configuration>
                <transformers>
                    <transformer implementation="org.apache.maven.plugins.shade.resource.AppendingTransformer">
                        <resource>META-INF/spring.handlers</resource>
                    </transformer>
                    <transformer implementation="org.springframework.boot.maven.PropertiesMergingResourceTransformer">
                        <resource>META-INF/spring.factories</resource>
                    </transformer>
                    <transformer implementation="org.apache.maven.plugins.shade.resource.AppendingTransformer">
                        <resource>META-INF/spring.schemas</resource>
                    </transformer>
                    <transformer implementation="org.apache.maven.plugins.shade.resource.ServicesResourceTransformer"/>
                    <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                        <mainClass>${start-class}</mainClass>
                    </transformer>
                </transformers>
            </configuration>
        </execution>
    </executions>
</plugin>
```
    
## IntelliJ IDEA

IntelliJ adds by default the `javafx.base` and `javafx.graphics` to the modules of Java 9+.
This might be causing issues in Java 9 and above, as the `javafx.controls` and `javafx.fxml` are 
missing from the modules causing an `IllegalAccessException` when trying to run the application.

Add the following options to the `VM Options` in the run configuration of IntelliJ to fix this issue. 

    -p "<PATH TO JAVAFX SDK>\lib" --add-modules javafx.controls,javafx.fxml,javafx.swing
