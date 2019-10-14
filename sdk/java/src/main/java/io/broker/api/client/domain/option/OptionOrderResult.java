
package io.broker.api.client.domain.option;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

import io.broker.api.client.domain.account.OrderSide;
import io.broker.api.client.domain.account.OrderStatus;
import lombok.Data;
import lombok.Singular;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OptionOrderResult {

    /**
     * Order timestamp.
     */
    private Long time;

    /**
     * Order update time.
     */
    private Long updateTime;
    /**
     * Order id.
     */
    private Long orderId;

    /**
     * Account id.
     */
    private Long accountId;
    /**
     * Client order id.
     */
    private String clientOrderId;
    /**
     * Symbol that the order was put on.
     */
    private String symbol;
    /**
     * Price.
     */
    private String price;
    /**
     * Original quantity.
     */
    private String origQty;
    /**
     * Original quantity.
     */
    private String executedQty;

    /**
     * Order executed amount.
     */
    private String executedAmount;

    /**
     * Order avg price.
     */
    private String avgPrice;
    /**
     * Type of order.
     */
    private String type;
    /**
     * Buy/Sell order side.
     */
    private OrderSide side;
    /**
     * Order status.
     */
    private OrderStatus status;

    /**
     * Option.
     */
    private String option;
    /**
     * Time in force to indicate how long will the order remain active.
     */
    private String timeInForce;

    /**
     * fee.
     */
    private @Singular("fee")
    List<OrderMatchFeeInfo> fees;
}
