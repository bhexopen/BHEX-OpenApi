package io.broker.api.client.domain.option.request;


import io.broker.api.client.domain.account.OrderSide;
import io.broker.api.client.domain.account.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OptionOpenOrderRequest {

    private String symbol;

    private Long orderId;

    private Integer limit;

    private OrderSide orderSide;

    private OrderType orderType;

    private Long recvWindow;

    private Long timestamp;
}
