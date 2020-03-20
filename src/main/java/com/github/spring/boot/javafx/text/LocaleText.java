package com.github.spring.boot.javafx.text;

import javafx.beans.property.ReadOnlyObjectProperty;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Defines a easy way of loading localized text messages.
 * This interface uses {@link Message} for easy localized text definitions.
 */
public interface LocaleText {
    /**
     * The property name of the locale text message source accessor.
     */
    String MESSAGE_SOURCE_ACCESSOR_PROPERTY = "messageSourceAccessor";

    /**
     * The property name of the locale text resource bundle.
     */
    String RESOURCE_BUNDLE_PROPERTY = "resourceBundle";

    /**
     * Get the message source that is used by this {@link LocaleText}.
     *
     * @return Returns the message source.
     */
    MessageSourceAccessor getMessageSource();

    /**
     * Get the message source accessor property of this locale text.
     *
     * @return Returns the read-only message source accessor property.
     */
    ReadOnlyObjectProperty<MessageSourceAccessor> messageSourceProperty();

    /**
     * Get the resource bundle that is used by this {@link LocaleText}.
     *
     * @return Returns the resource bundle.
     */
    ResourceBundle getResourceBundle();

    /**
     * Get the resource bundle property of this locale text.
     *
     * @return Returns the read-only instance of the resource bundle property.
     */
    ReadOnlyObjectProperty<ResourceBundle> resourceBundleProperty();

    /**
     * Get the text for the given message key.
     *
     * @param message Set the message key.
     * @return Returns the formatted text.
     */
    String get(Message message);

    /**
     * Get the text for the given message key.
     *
     * @param message Set the message key.
     * @param args    Set the arguments to pass to the message.
     * @return Returns the formatted text.
     */
    String get(Message message, Object... args);

    /**
     * Get the text for the given message.
     *
     * @param message Set the message.
     * @param args    Set the arguments to pass to the message.
     * @return Returns the formatted text.
     */
    String get(String message, Object... args);

    /**
     * Update the locale that needs to be used for the localized texts.
     *
     * @param locale The new locale to use.
     */
    void updateLocale(Locale locale);
}
