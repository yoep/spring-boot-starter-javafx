package com.github.spring.boot.javafx.font;

import javafx.scene.text.Font;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Registry for loading additional fonts into JavaFX.
 */
public class FontRegistryImpl implements FontRegistry {
    private static final FontRegistryImpl INSTANCE = new FontRegistryImpl();
    private final Map<String, Font> loadedFonts = new HashMap<>();

    private FontRegistryImpl() {
    }

    /**
     * Get the instance of the {@link FontRegistryImpl}.
     *
     * @return Returns the instance.
     */
    public static FontRegistryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public Font loadFont(String filename) {
        Objects.requireNonNull(filename, "filename cannot be null");
        var defaultFont = Font.getDefault();

        return loadFont(filename, defaultFont.getSize());
    }

    @Override
    public Font loadFont(String filename, double size) {
        Objects.requireNonNull(filename, "filename cannot be null");

        if (loadedFonts.containsKey(filename))
            return createFontFromAlreadyLoadedFont(loadedFonts.get(filename), size);

        return loadFontResource(filename, size);
    }

    private Font loadFontResource(String filename, double size) {
        var resource = getClass().getResource(FONT_DIRECTORY + filename);
        var font = Optional.ofNullable(Font.loadFont(resource.toExternalForm(), size))
                .orElseThrow(() -> new FontException(filename));

        loadedFonts.put(filename, font);

        return font;
    }

    private Font createFontFromAlreadyLoadedFont(Font font, double size) {
        return Font.font(font.getFamily(), size);
    }
}
