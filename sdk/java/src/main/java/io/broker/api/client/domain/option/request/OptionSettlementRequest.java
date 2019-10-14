package io.broker.api.client.domain.option.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OptionSettlementRequest {

    private String symbol;

    private Long recvWindow;

    private Long timestamp;
}
