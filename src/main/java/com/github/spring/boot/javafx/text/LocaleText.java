package com.github.spring.boot.javafx.text;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.MessageSourceResourceBundle;

/**
 * Defines a easy way of loading localized text messages.
 * This interface uses {@link Message} for easy localized text definitions.
 */
public interface LocaleText {
    /**
     * Get the message source that is used by this {@link LocaleText}.
     *
     * @return Returns the message source.
     */
    MessageSourceAccessor getMessageSource();

    /**
     * Get the resource bundle that is used by this {@link LocaleText}.
     *
     * @return Returns the resource bundle.
     */
    MessageSourceResourceBundle getResourceBundle();

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
}
