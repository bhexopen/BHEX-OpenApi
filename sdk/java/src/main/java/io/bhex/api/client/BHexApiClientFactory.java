package io.bhex.api.client;

import io.bhex.api.client.constant.BHexConstants;
import io.bhex.api.client.impl.BHexApiRestClientImpl;
import io.bhex.api.client.impl.BHexApiWebSocketClientImpl;
import io.bhex.api.client.impl.BHexOptionApiRestClientImpl;

import static io.bhex.api.client.impl.BHexApiServiceGenerator.getSharedClient;

/**
 * A factory for creating BHexApi client objects.
 */
public final class BHexApiClientFactory {

    /**
     * API Key
     */
    private String apiKey;

    /**
     * Secret.
     */
    private String secret;

    private String baseUrl = BHexConstants.API_BASE_URL;

    /**
     * Instantiates a new BHex api client factory.
     *
     * @param apiKey the API key
     * @param secret the Secret
     */
    private BHexApiClientFactory(String apiKey, String secret) {
        this.apiKey = apiKey;
        this.secret = secret;
    }

    private BHexApiClientFactory(String baseUrl, String apiKey, String secret) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.secret = secret;
    }

    /**
     * New instance.
     *
     * @param apiKey the API key
     * @param secret the Secret
     * @return the BHex api client factory
     */
    public static BHexApiClientFactory newInstance(String apiKey, String secret) {
        return new BHexApiClientFactory(apiKey, secret);
    }

    /**
     * for bhop.cloud client and inner test only
     *
     * @param baseUrl
     * @param apiKey
     * @param secret
     * @return
     */
    public static BHexApiClientFactory newInstance(String baseUrl, String apiKey, String secret) {
        return new BHexApiClientFactory(baseUrl, apiKey, secret);
    }

    /**
     * New instance without authentication.
     *
     * @return the BHex api client factory
     */
    public static BHexApiClientFactory newInstance() {
        return new BHexApiClientFactory(null, null);
    }

    /**
     * Creates a new synchronous/blocking REST client.
     */
    public BHexApiRestClient newRestClient() {
        return new BHexApiRestClientImpl(baseUrl, apiKey, secret);
    }


    public BHexApiWebSocketClient newWebSocketClient() {
        return new BHexApiWebSocketClientImpl(getSharedClient(), BHexConstants.WS_API_BASE_URL, BHexConstants.WS_API_USER_URL);
    }

    /**
     * for bhop.cloud client and inner test only
     *
     * @param wsApiBaseUrl
     * @param wsApiUserUrl
     * @return
     */
    public BHexApiWebSocketClient newWebSocketClient(String wsApiBaseUrl, String wsApiUserUrl) {
        return new BHexApiWebSocketClientImpl(getSharedClient(), wsApiBaseUrl, wsApiUserUrl);
    }

    /**
     * Creates a new synchronous/blocking Option REST client.
     */
    public BHexOptionApiRestClient newOptionRestClient() {
        return new BHexOptionApiRestClientImpl(baseUrl, apiKey, secret);
    }

}
