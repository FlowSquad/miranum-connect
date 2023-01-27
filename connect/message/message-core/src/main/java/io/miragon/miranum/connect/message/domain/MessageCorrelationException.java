package io.miragon.miranum.connect.binder.message.domain;

public class MessageCorrelationException extends RuntimeException {

    public MessageCorrelationException(final String message, final Exception e) {
        super(message, e);
    }

    public MessageCorrelationException(final String message) {
        super(message);
    }
}
