package org.github.spring.boot.javafx.exceptions;

import lombok.Getter;

@Getter
public class WindowNotFoundException extends RuntimeException {
    private final String name;

    public WindowNotFoundException(String name) {
        super("Window '" + name + "' couldn't be found");
        this.name = name;
    }
}
