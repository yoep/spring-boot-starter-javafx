# Spring Boot JavaFX starter

Spring Boot starter for easy integration with JavaFX.
This library provides easy integration with JavaJX as well as additional helpers for 
loading & managing JavaFX views in Spring.

## Maven

The library is available in the maven central repository and can be used by adding:

    <dependency>
      <groupId>com.github.yoep</groupId>
      <artifactId>spring-boot-starter-javafx</artifactId>
      <version>1.0.0</version>
    </dependency>

## Usage

Create a class that extends `SpringJavaFXApplication` and launch the JavaFX application from this class.

Example:

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
    
## IntelliJ IDEA

IntelliJ adds by default the `javafx.base` and `javafx.graphics` to the modules of Java 9+.
This might be causing issues in Java 9 and above, as the `javafx.controls` and `javafx.fxml` are 
missing from the modules causing an `IllegalAccessException` when trying to run the application.

Add the following options to the `VM Options` in the run configuration of IntelliJ to fix this issue. 

    -p "<PATH TO JAVAFX SDK>\lib" --add-modules javafx.controls,javafx.fxml,javafx.swing