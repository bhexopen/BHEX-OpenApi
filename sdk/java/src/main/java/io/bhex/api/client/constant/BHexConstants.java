package io.bhex.api.client.constant;

import org.apache.commons.lang3.builder.ToStringStyle;

public class BHexConstants {

    public static final long DEFAULT_RECEIVING_WINDOW = 5_000L;

//    public static final String API_BASE_URL = "http://local.bhex.cn:7128";
    public static final String API_BASE_URL = "https://www.bhex.us/";
//    public static final String API_BASE_URL = "http://www.bhex.cn";

    /**
     * Streaming API base URL.
     */
    public static final String WS_API_BASE_URL = "ws://www.bhex.us/openapi/quote/v1";

    public static final String WS_API_USER_URL = "ws://www.bhex.us/openapi/ws/";
//    public static final String WS_API_USER_URL = "http://local.bhex.cn:7128/openapi/ws/";


    public static final String API_KEY_HEADER = "X-BH-APIKEY";

    /**
     * Decorator to indicate that an endpoint requires an API key.
     */
    public static final String ENDPOINT_SECURITY_TYPE_APIKEY = "APIKEY";
    public static final String ENDPOINT_SECURITY_TYPE_APIKEY_HEADER = ENDPOINT_SECURITY_TYPE_APIKEY + ": #";

    /**
     * Decorator to indicate that an endpoint requires a signature.
     */
    public static final String ENDPOINT_SECURITY_TYPE_SIGNED = "SIGNED";
    public static final String ENDPOINT_SECURITY_TYPE_SIGNED_HEADER = ENDPOINT_SECURITY_TYPE_SIGNED + ": #";
    /**
     * Default ToStringStyle used by toString methods.
     * Override this to change the output format of the overridden toString methods.
     *  - Example ToStringStyle.JSON_STYLE
     */
    public static ToStringStyle TO_STRING_BUILDER_STYLE = ToStringStyle.SHORT_PREFIX_STYLE;

}
