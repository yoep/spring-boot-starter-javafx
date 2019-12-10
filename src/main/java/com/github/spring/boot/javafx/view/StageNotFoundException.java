package com.github.spring.boot.javafx.view;

import lombok.Getter;

@Getter
public class StageNotFoundException extends RuntimeException {
    private final String stageName;
    private final boolean primaryStage;

    public StageNotFoundException() {
        super("Primary stage couldn't be found");
        this.stageName = "primary";
        this.primaryStage = false;
    }

    public StageNotFoundException(String stageName) {
        super("Window '" + stageName + "' couldn't be found");
        this.stageName = stageName;
        this.primaryStage = false;
    }
}
