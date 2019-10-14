package io.broker.api.client.domain.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.broker.api.client.domain.account.OrderStatus;
import io.broker.api.client.domain.option.OrderMatchFeeInfo;
import lombok.Data;
import lombok.Singular;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractOrderResult {

    /**
     * Timestamp when the order is created.
     */
    private Long time;

    /**
     * Last time this order was updated
     */
    private Long updateTime;

    /**
     * ID of the order.
     */
    private Long orderId;

    /**
     * A unique ID of the order.
     */
    private String clientOrderId;

    /**
     * Name of the contract.
     */
    private String contract;

    /**
     * Price of the order.
     */
    private String price;

    /**
     * Leverage of the order.
     */
    private String leverage;

    /**
     * Quantity ordered.
     */
    private String origQty;

    /**
     * Quantity of orders that has been executed
     */
    private String executeQty;

    /**
     * Average price of filled orders.
     */
    private String avgPrice;

    /**
     * Amount of margin locked for this order.
     * This includes the actually margin needed plus fees to open and close the position.
     */
    private String marginLocked;

    /**
     * The order type.
     */
    private OrderType orderType;

    /**
     * The price type.
     */
    private PriceType priceType;

    /**
     * Direction of the order.
     */
    private OrderSide side;

    /**
     * The state of the order.
     */
    private OrderStatus status;

    /**
     * Time in force.
     */
    private TimeInForce timeInForce;

    /**
     * Fees incurred for this order.
     */
    @Singular
    private List<OrderMatchFeeInfo> fees;
}
