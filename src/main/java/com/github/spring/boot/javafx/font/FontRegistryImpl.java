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
        Assert.notNull(filename, "filename cannot be null");
        Font defaultFont = Font.getDefault();

        return loadFont(filename, defaultFont.getSize());
    }

    @Override
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
