package com.github.spring.boot.javafx.font;

import javafx.scene.text.Font;

public interface FontRegistry {
    /**
     * The font directory location.
     */
    String FONT_DIRECTORY = "/fonts/";

    /**
     * Load the given font file and use the size of the system default font.
     *
     * @param filename The font filename to load from the {@link #FONT_DIRECTORY}.
     * @return Returns the loaded font.
     */
    Font loadFont(String filename);

    /**
     * Load the given font file.
     *
     * @param filename The font filename to load from the {@link #FONT_DIRECTORY}.
     * @param size     The size of the font.
     * @return Returns the loaded font.
     */
    Font loadFont(String filename, double size);
}
