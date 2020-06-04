package io.broker.api.client.domain.option.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetOrderRequest {

    private Long orderId;

    private Long clientOrderId;

    private Long recvWindow;

    private Long timestamp;
}
