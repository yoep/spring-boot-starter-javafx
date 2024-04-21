package com.github.spring.boot.javafx.controllers;

import com.github.spring.boot.javafx.stereotype.ViewController;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@ViewController
public class MyTestController {
    @FXML
    Pane root;
}
