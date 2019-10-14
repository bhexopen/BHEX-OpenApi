package io.broker.api.client.domain.market;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Index of a symbol in Broker.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Index {

    /**
     * The pair of symbol and index.
     */
    private Map<String, String> index;

    /**
     * EDP: Estimated delivery price for those trading pairs.
     * The pair of symbol and edp.
     */
    private Map<String, String> edp;
}
