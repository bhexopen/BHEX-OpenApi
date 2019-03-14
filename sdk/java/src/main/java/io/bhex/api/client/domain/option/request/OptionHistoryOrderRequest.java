package io.bhex.api.client.domain.option.request;


import io.bhex.api.client.domain.account.OrderSide;
import io.bhex.api.client.domain.account.OrderStatus;
import io.bhex.api.client.domain.account.OrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OptionHistoryOrderRequest {

    private String symbol;

    private OrderSide orderSide;

    private OrderType orderType;

    private Integer limit;

    private OrderStatus orderStatus;

    private Long recvWindow;

    private Long timestamp;
}
