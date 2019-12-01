# Spring Boot JavaFX starter

Spring Boot starter for easy integration with JavaFX.
This library provides easy integration with JavaJX as well as additional helpers for 
loading & managing JavaFX views in Spring.

## Usage

Create a class that extends `SpringJavaFXApplication` and launch the JavaFX application from this class.

Example:

    @SpringBootApplication
    public class MySpringApplication extends SpringJavaFXApplication {
    
        public static void main(String[] args) {                
            launch(MySpringApplication.class, args);
        }
        
    }