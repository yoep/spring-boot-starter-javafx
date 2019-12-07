package com.github.spring.boot.javafx.font;

import javafx.scene.text.Font;
import org.springframework.util.Assert;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Registry for loading additional fonts into JavaFX.
 */
public class FontRegistry {
    private static final String FONT_DIRECTORY = "/fonts/";
    private static final FontRegistry INSTANCE = new FontRegistry();
    private final Map<String, Font> loadedFonts = new HashMap<>();

    private FontRegistry() {
    }

    /**
     * Get the instance of the {@link FontRegistry}.
     *
     * @return Returns the instance.
     */
    public static FontRegistry getInstance() {
        return INSTANCE;
    }

    /**
     * Load the given font file and use the size of the system default font.
     *
     * @param filename The font filename to load from the {@link #FONT_DIRECTORY}.
     * @return Returns the loaded font.
     */
    public Font loadFont(String filename) {
        Assert.notNull(filename, "filename cannot be null");
        Font defaultFont = Font.getDefault();

        return loadFont(filename, defaultFont.getSize());
    }

    /**
     * Load the given font file.
     *
     * @param filename The font filename to load from the {@link #FONT_DIRECTORY}.
     * @param size     The size of the font.
     */
    public Font loadFont(String filename, double size) {
        Assert.notNull(filename, "filename cannot be null");

        if (loadedFonts.containsKey(filename))
            return createFontFromAlreadyLoadedFont(loadedFonts.get(filename), size);

        return loadFontResource(filename, size);
    }

    private Font loadFontResource(String filename, double size) {
        URL resource = getClass().getResource(FONT_DIRECTORY + filename);
        Font font = Optional.ofNullable(Font.loadFont(resource.toExternalForm(), size))
                .orElseThrow(() -> new FontException(filename));

        loadedFonts.put(filename, font);

        return font;
    }

    private Font createFontFromAlreadyLoadedFont(Font font, double size) {
        return Font.font(font.getFamily(), size);
    }
}
