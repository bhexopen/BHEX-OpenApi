package io.bhex.api.client.domain.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Buy/Sell order side.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public enum OrderSide {
    BUY,
    SELL
}
