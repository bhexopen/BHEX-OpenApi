
package io.broker.api.client.domain.option;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.broker.api.client.domain.account.OrderSide;
import io.broker.api.client.domain.account.OrderType;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OptionMatchResult {

    /**
     * Order timestamp.
     */
    private Long time;
    /**
     * Order trade id.
     */
    private Long tradeId;

    /**
     * Order id.
     */
    private Long orderId;
    /**
     * Account id.
     */
    private Long accountId;
    /**
     * Symbol id.
     */
    private String symbolId;
    /**
     * Symbol name.
     */
    private String symbolName;

    /**
     * Base token id.
     */
    private String baseTokenId;
    /**
     * Base token id.
     */
    private String baseTokenName;
    /**
     * Quote token id.
     */
    private String quoteTokenId;
    /**
     * Quote token name.
     */
    private String quoteTokenName;
    /**
     * Price.
     */
    private String price;
    /**
     * Original quantity.
     */
    private String quantity;
    /**
     * Fee token id.
     */
    private String feeTokenId;
    /**
     * Fee token name.
     */
    private String feeTokenName;
    /**
     * fee.
     */
    private String fee;
    /**
     * Order type.
     */
    private OrderType type;
    /**
     * Order side.
     */
    private OrderSide side;
    /**
     * Symbol.
     */
    private String symbol;
    /**
     * Time in force to indicate how long will the order remain active.
     */
    private String timeInForce;
}
