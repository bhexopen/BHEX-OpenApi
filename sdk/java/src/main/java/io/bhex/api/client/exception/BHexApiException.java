package io.bhex.api.client.exception;

import io.bhex.api.client.BHexApiError;

/**
 * An exception which can occur while invoking methods of the BHex API.
 */
public class BHexApiException extends RuntimeException {

    private static final long serialVersionUID = 3788669840036201041L;
    /**
     * Error response object returned by BHex API.
     */
    private BHexApiError error;

    /**
     * Instantiates a new BHex api exception.
     *
     * @param error an error response object
     */
    public BHexApiException(BHexApiError error) {
        this.error = error;
    }

    /**
     * Instantiates a new BHex api exception.
     */
    public BHexApiException() {
        super();
    }

    /**
     * Instantiates a new BHex api exception.
     *
     * @param message the message
     */
    public BHexApiException(String message) {
        super(message);
    }

    /**
     * Instantiates a new BHex api exception.
     *
     * @param cause the cause
     */
    public BHexApiException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new BHex api exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public BHexApiException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @return the response error object from BHex API, or null if no response object was returned (e.g. server returned 500).
     */
    public BHexApiError getError() {
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
