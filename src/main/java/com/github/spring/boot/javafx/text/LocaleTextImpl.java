package com.github.spring.boot.javafx.text;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.MessageSourceResourceBundle;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

/**
 * Get the localized text for the given message key.
 * This class uses Spring's {@link MessageSourceResourceBundle} for retrieving the locale strings.
 */
@Slf4j
public class LocaleTextImpl implements LocaleText {
    private final MessageSourceAccessor messageSource;
    private final MessageSourceResourceBundle resourceBundle;

    /**
     * Initialize a new instance of {@link LocaleTextImpl}.
     *
     * @param messageSource set the message source to use.
     */
    public LocaleTextImpl(ResourceBundleMessageSource messageSource) {
        this.messageSource = new MessageSourceAccessor(messageSource);
        this.resourceBundle = new MessageSourceResourceBundle(messageSource, Locale.getDefault());
    }

    @Override
    public MessageSourceAccessor getMessageSource() {
        return messageSource;
    }

    @Override
    public MessageSourceResourceBundle getResourceBundle() {
        return resourceBundle;
    }

    @Override
    public String get(Message message) {
        return get(message, ArrayUtils.EMPTY_OBJECT_ARRAY);
    }

    @Override
    public String get(Message message, Object... args) {
        return get(message.getKey(), args);
    }

    @Override
    public String get(String message, Object... args) {
        try {
            return messageSource.getMessage(message, args);
        } catch (NoSuchMessageException ex) {
            log.warn("Message key '" + message + "' not found", ex);
            return message;
        }
    }
}
