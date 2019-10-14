package io.broker.api.client.domain.contract.request;

import io.broker.api.client.domain.contract.OrderSide;
import io.broker.api.client.domain.contract.OrderType;
import io.broker.api.client.domain.contract.PriceType;
import io.broker.api.client.domain.contract.TimeInForce;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContractOrderRequest {

    /**
     * Name of the contract.
     */
    private String symbol;

    /**
     * Direction of the order.
     */
    private OrderSide side;

    /**
     * The order type,
     */
    private OrderType orderType;

    /**
     * The number of contracts to buy.
     */
    private String quantity;

    /**
     * Leverage of the order.
     */
    private String leverage;

    /**
     * Price of the order
     */
    private String price;

    /**
     * The price type,
     */
    private PriceType priceType;

    /**
     * The price at which the trigger order will be executed.
     * Required for STOP orders.
     */
    private String triggerPrice;

    /**
     * Time in force for LIMIT orders.
     */
    private TimeInForce timeInForce;

    /**
     * A unique ID of the order. Automatically generated if not sent.
     */
    private String clientOrderId;
}
