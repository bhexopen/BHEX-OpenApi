package io.bhex.api.client;

/**
 * BHexApiCallback is a functional interface used together with the BHexApiAsyncClient to provide a non-blocking REST client.
 *
 * @param <T> the return type from the callback
 */
@FunctionalInterface
public interface BHexApiCallback<T> {

    /**
     * Called whenever a response comes back from the BHex API.
     *
     * @param response the expected response object
     */
    void onResponse(T response);

    /**
     * Called whenever an error occurs.
     *
     * @param cause the cause of the failure
     */
    default void onFailure(Throwable cause) {
    }
}
