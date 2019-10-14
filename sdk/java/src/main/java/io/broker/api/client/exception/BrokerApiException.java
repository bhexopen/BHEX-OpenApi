package io.broker.api.client.exception;

import io.broker.api.client.BrokerApiError;

/**
 * An exception which can occur while invoking methods of the Broker API.
 */
public class BrokerApiException extends RuntimeException {

    private static final long serialVersionUID = 3788669840036201041L;
    /**
     * Error response object returned by Broker API.
     */
    private BrokerApiError error;

    /**
     * Instantiates a new Broker api exception.
     *
     * @param error an error response object
     */
    public BrokerApiException(BrokerApiError error) {
        this.error = error;
    }

    /**
     * Instantiates a new Broker api exception.
     */
    public BrokerApiException() {
        super();
    }

    /**
     * Instantiates a new Broker api exception.
     *
     * @param message the message
     */
    public BrokerApiException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Broker api exception.
     *
     * @param cause the cause
     */
    public BrokerApiException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Broker api exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public BrokerApiException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @return the response error object from Broker API, or null if no response object was returned (e.g. server returned 500).
     */
    public BrokerApiError getError() {
        return error;
    }

    @Override
    public String getMessage() {
        if (error != null) {
            return error.getMsg();
        }
        return super.getMessage();
    }
}
