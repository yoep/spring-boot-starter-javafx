package com.github.spring.boot.javafx.font.controls;

import com.github.spring.boot.javafx.font.FontRegistry;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Abstract implementation of a Font Awesome icon that extends the {@link Label}.
 * This class loads the font file and prevents any changes to the initial loaded font family.
 */
@Slf4j
@SuppressWarnings("unused")
abstract class AbstractIcon extends Label {
    private final DoubleProperty sizeFactorProperty = new SimpleDoubleProperty();
    private String fontFamily;
    private boolean updating;

    AbstractIcon(String filename) {
        Assert.hasText(filename, "filename cannot be empty");
        init(filename);
    }

    AbstractIcon(String filename, String text) {
        super(text);
        Assert.hasText(filename, "filename cannot be empty");
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

    @Override
    public String toString() {
        return getText();
    }

    private void init(String filename) {
        initializeFont(filename);
        initializeSizeFactor();
        initializeFontFamilyListener();
    }

    private void initializeFont(String filename) {
        Font font = FontRegistry.getInstance().loadFont(filename);

        fontFamily = font.getFamily();
        setFont(font);
    }

    private void initializeSizeFactor() {
        sizeFactorProperty.addListener((observable, oldValue, newValue) -> {
            updating = true;
            double fontSize = getActualSize(newValue.doubleValue(), getFont().getSize());

            setFont(new Font(fontFamily, fontSize));
            updating = false;
        });
    }

    private void initializeFontFamilyListener() {
        // this listener prevents any changes to the font family
        fontProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.getFamily().equals(fontFamily) || updating)
                return;

            updating = true;
            double fontSize = getActualSize(sizeFactorProperty.get(), newValue.getSize());

            setFont(Font.font(fontFamily, FontWeight.findByName(newValue.getStyle()), fontSize));
            updating = false;
        });
    }

    private double getActualSize(double sizeFactor, double fontSize) {
        return sizeFactor <= 0 ? fontSize : sizeFactor * fontSize;
    }
}
