package com.github.spring.boot.javafx.font.controls;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lombok.extern.slf4j.Slf4j;
import com.github.spring.boot.javafx.font.FontRegistry;

import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
abstract class AbstractIcon extends Label {
    private final DoubleProperty sizeFactorProperty = new SimpleDoubleProperty();
    private double defaultSize;

    AbstractIcon(String filename) {
        init(filename);
    }

    AbstractIcon(String filename, String text) {
        super(text);
        init(filename);
    }

    public double getSizeFactor() {
        return sizeFactorProperty.get();
    }

    public void setSizeFactor(double factor) {
        sizeFactorProperty.set(factor);
    }

    public void setColor(Color color) {
        setTextFill(color);
    }

    <T> void setProperty(T property, Consumer<T> mapping) {
        Optional.ofNullable(property)
                .ifPresent(mapping);
    }

    private void init(String filename) {
        initializeFont(filename);
        initializeSizeFactor();
    }

    private void initializeFont(String filename) {
        Font font = FontRegistry.getInstance().loadFont(filename);
        this.defaultSize = font.getSize();

        setFont(font);
    }

    private void initializeSizeFactor() {
        sizeFactorProperty.addListener((observable, oldValue, newValue) -> setFont(new Font(getFont().getFamily(), getActualSizeFactor(newValue.doubleValue()))));
    }

    private double getActualSizeFactor(double sizeFactor) {
        return sizeFactor < 1 ? 1 : sizeFactor * defaultSize;
    }
}
