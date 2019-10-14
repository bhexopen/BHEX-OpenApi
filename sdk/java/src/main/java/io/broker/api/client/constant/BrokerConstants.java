package io.broker.api.client.constant;

import org.apache.commons.lang3.builder.ToStringStyle;

public class BrokerConstants {

    public static final long DEFAULT_RECEIVING_WINDOW = 5_000L;

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
     * - Example ToStringStyle.JSON_STYLE
     */
    public static final ToStringStyle TO_STRING_BUILDER_STYLE = ToStringStyle.SHORT_PREFIX_STYLE;


    public static final String PING_MSG_KEY = "ping";

    public static final String PONG_MSG_KEY = "pong";

    /**
     * 心跳间隔 1分钟一次
     */
    public static final long HEART_BEAT_INTERVAL = 60 * 1000;

}
