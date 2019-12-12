package io.broker.api.client.domain.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.broker.api.client.domain.account.OrderType;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractMatchResult {

    /**
     * Timestamp when the order is created.
     */
    private Long time;

    /**
     * The ID for the trade.
     */
    private Long tradeId;

    /**
     * ID of the order.
     */
    private Long orderId;

    /**
     * ID of the match order.
     */
    private Long matchOrderId;

    /**
     * Name of the contract.
     */
    private String symbolId;

    /**
     * Price of the trade.
     */
    private String price;

    /**
     * Quantity of the trade.
     */
    private String quantity;

    /**
     * Fee token name/id.
     */
    private String feeTokenId;

    /**
     * Direction of the order. BUY or SELL.
     */
    private OrderSide side;

    /**
     * The order type.
     */
    private OrderType orderType;
}
