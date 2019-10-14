package io.broker.api.client.domain.contract.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BatchCancelOrderRequest {

    /**
     * The symbol ids of the cancel orders
     */
    private List<String> symbolList;
}
