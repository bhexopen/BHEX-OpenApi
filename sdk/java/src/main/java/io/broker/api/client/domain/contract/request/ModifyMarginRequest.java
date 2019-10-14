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
public class ModifyMarginRequest {

    /**
     * The symbol's margin to be modified.
     */
    private String symbol;

    /**
     * Direction of the position.
     */
    private PositionSide side;

    /**
     * Amount of margin to be added (Positive Value) or removed (Negative Value).
     * Please note that this amount refers to the underlying quote asset of the asset.
     */
    private String amount;
}
