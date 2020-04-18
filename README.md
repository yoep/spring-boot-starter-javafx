# Spring Boot JavaFX starter

Spring Boot starter for easy integration with JavaFX.
This library provides easy integration with JavaJX as well as additional helpers for 
loading & managing JavaFX views in Spring.

## Maven

The library is available in the maven central repository and can be used by adding:

    <dependency>
      <groupId>com.github.yoep</groupId>
      <artifactId>spring-boot-starter-javafx</artifactId>
      <version>1.0.5</version>
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

## FAT jar packaging

It is **recommended to not use** the `spring-boot-maven-plugin` if you want to package the JavaFX application into a fat jar.
The reason behind this, is that the plugin will break JavaFX due to the JAR layout that is used by the plugin.

For creating fat jar packages, use the `maven-shade-plugin` instead.

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
    
## IntelliJ IDEA

IntelliJ adds by default the `javafx.base` and `javafx.graphics` to the modules of Java 9+.
This might be causing issues in Java 9 and above, as the `javafx.controls` and `javafx.fxml` are 
missing from the modules causing an `IllegalAccessException` when trying to run the application.

Add the following options to the `VM Options` in the run configuration of IntelliJ to fix this issue. 

    -p "<PATH TO JAVAFX SDK>\lib" --add-modules javafx.controls,javafx.fxml,javafx.swing
