package io.broker.api.client.domain.contract.request;

import io.broker.api.client.domain.contract.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CancelContractOrderRequest {

    /**
     * The order ID of the order to be canceled.
     */
    private Long orderId;

    /**
     * Unique client customized ID of the order.
     */
    private String clientOrderId;

    /**
     * The order type, possible types: LIMIT and STOP.
     */
    private OrderType orderType;
}