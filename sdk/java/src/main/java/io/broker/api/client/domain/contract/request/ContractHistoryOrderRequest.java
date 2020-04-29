package io.broker.api.client.domain.contract.request;

import io.broker.api.client.domain.contract.OrderSide;
import io.broker.api.client.domain.contract.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContractHistoryOrderRequest {

    /**
     * Symbol to return open orders for.
     * If not sent, orders of all contracts will be returned.
     */
    private String symbol;

    /**
     * Order ID
     */
    private Long orderId;

    /**
     * Direction of the order.
     * <strong>This parameter is no longer supported</strong>
     */
    @Deprecated
    private OrderSide side;

    /**
     * The order type.
     */
    private OrderType orderType;

    /**
     * Number of entries to return.
     */
    private Integer limit;
}
