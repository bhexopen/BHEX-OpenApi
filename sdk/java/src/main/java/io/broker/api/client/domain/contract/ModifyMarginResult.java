package io.broker.api.client.domain.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModifyMarginResult {

    /**
     * The timestamp when the response is returned.
     */
    private Long timestamp;

    /**
     * The name of the contract.
     */
    private String symbol;

    /**
     * Updated margin for the symbol.
     */
    private String margin;
}
