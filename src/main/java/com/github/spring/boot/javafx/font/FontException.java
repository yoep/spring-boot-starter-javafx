package com.github.spring.boot.javafx.font;

import lombok.Getter;

@Getter
public class FontException extends RuntimeException {
    private String filename;

    public FontException(String filename) {
        super("An error occurred while loading font file " + filename);
        this.filename = filename;
    }
}
