package com.github.spring.boot.javafx.text;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.MessageSourceResourceBundle;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Get the localized text for the given message key.
 * This class uses Spring's {@link MessageSourceResourceBundle} for retrieving the locale strings.
 */
@Slf4j
public class LocaleTextImpl implements LocaleText {
    private final ResourceBundleMessageSource messageSource;
    private final ObjectProperty<MessageSourceAccessor> messageSourceAccessor = new SimpleObjectProperty<>(this, MESSAGE_SOURCE_ACCESSOR_PROPERTY);
    private final ObjectProperty<ResourceBundle> resourceBundle = new SimpleObjectProperty<>(this, RESOURCE_BUNDLE_PROPERTY);

    /**
     * Initialize a new instance of {@link LocaleTextImpl}.
     *
     * @param messageSource set the message source to use.
     */
    public LocaleTextImpl(ResourceBundleMessageSource messageSource) {
        Objects.requireNonNull(messageSource, "messageSource cannot be null");
        this.messageSource = messageSource;

        init();
    }

    //region Properties

    @Override
    public MessageSourceAccessor getMessageSource() {
        return messageSourceAccessor.get();
    }

    @Override
    public ObjectProperty<MessageSourceAccessor> messageSourceProperty() {
        return messageSourceAccessor;
    }

    private void setMessageSource(MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor.set(messageSourceAccessor);
    }

    @Override
    public ResourceBundle getResourceBundle() {
        return resourceBundle.get();
    }

    @Override
    public ReadOnlyObjectProperty<ResourceBundle> resourceBundleProperty() {
        return resourceBundle;
    }

    private void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle.set(resourceBundle);
    }

    //endregion

    //region Getters

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
            return getMessageSource().getMessage(message, args);
        } catch (NoSuchMessageException ex) {
            log.warn("Message key '" + message + "' not found", ex);
            return message;
        }
    }

    //endregion

    //region Methods

    @Override
    public void updateLocale(Locale locale) {
        setMessageSource(createMessageSourceAccessor(locale));
        setResourceBundle(createResourceBundle(locale));
    }

    //endregion

    //region Functions

    private void init() {
        setMessageSource(createMessageSourceAccessor(Locale.getDefault()));
        setResourceBundle(createResourceBundle(Locale.getDefault()));
    }

    private MessageSourceAccessor createMessageSourceAccessor(Locale locale) {
        return new MessageSourceAccessor(messageSource, locale);
    }

    private MessageSourceResourceBundle createResourceBundle(Locale locale) {
        return new MessageSourceResourceBundle(messageSource, locale);
    }

    //endregion
}
