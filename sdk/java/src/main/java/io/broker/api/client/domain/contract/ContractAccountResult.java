package io.broker.api.client.domain.contract;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractAccountResult {

    /**
     * Total balance
     */
    private String total;

    /**
     * Available margin for use.
     */
    private String availableMargin;

    /**
     * Margin for positions.
     */
    private String positionMargin;

    /**
     * Margin locked for open orders.
     */
    private String orderMargin;

    /**
     * Token id.
     */
    private String tokenId;
}
