package io.broker.api.client.domain.account.request;

import io.broker.api.client.constant.BrokerConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Request object for canceling an order.
 */
public class CancelOrderRequest extends OrderRequest {

    private Long orderId;

    private String clientOrderId;

    public CancelOrderRequest(Long orderId) {
        super();
        this.orderId = orderId;
    }

    public CancelOrderRequest(String clientOrderId) {
        super();
        this.clientOrderId = clientOrderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public CancelOrderRequest orderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getClientOrderId() {
        return clientOrderId;
    }

    public CancelOrderRequest clientOrderId(String clientOrderId) {
        this.clientOrderId = clientOrderId;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BrokerConstants.TO_STRING_BUILDER_STYLE)
                .append("orderId", orderId)
                .append("clientOrderId", clientOrderId)
                .toString();
    }
}
