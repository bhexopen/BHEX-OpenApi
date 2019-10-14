package io.broker.api.client.domain.contract.request;

import io.broker.api.client.domain.contract.PositionSide;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContractPositionRequest {

    /**
     * Name of the contract.
     * If not sent, positions for all contracts will be returned.
     */
    private String symbol;

    /**
     * Direction of the position.
     * If not sent, positions for both sides will be returned.
     */
    private PositionSide side;
}
