package org.github.spring.boot.javafx.text;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.MessageSourceResourceBundle;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Getter
@Slf4j
@Component
public class Text {
    private final MessageSourceAccessor messageSource;
    private final MessageSourceResourceBundle resourceBundle;

    /**
     * Initialize a new instance of {@link Text}.
     *
     * @param messageSource set the message source to use.
     */
    public Text(ResourceBundleMessageSource messageSource) {
        this.messageSource = new MessageSourceAccessor(messageSource);
        this.resourceBundle = new MessageSourceResourceBundle(messageSource, Locale.getDefault());
    }

    /**
     * Get the text for the given message key.
     *
     * @param message Set the message key.
     * @return Returns the formatted text.
     */
    public String get(Message message) {
        return get(message, ArrayUtils.EMPTY_OBJECT_ARRAY);
    }

    /**
     * Get the text for the given message key.
     *
     * @param message Set the message key.
     * @param args    Set the arguments to pass to the message.
     * @return Returns the formatted text.
     */
    public String get(Message message, Object... args) {
        return get(message.getKey(), args);
    }

    /**
     * Get the text for the given message.
     *
     * @param message Set the message.
     * @param args    Set the arguments to pass to the message.
     * @return Returns the formatted text.
     */
    public String get(String message, Object... args) {
        try {
            return messageSource.getMessage(message, args);
        } catch (NoSuchMessageException ex) {
            log.error("Message key '" + message + "' not found", ex);
            return message;
        }
    }
}
