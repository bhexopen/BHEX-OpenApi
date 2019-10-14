package io.broker.api.client.domain.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractPositionResult {

    /**
     * Name of the contract.
     */
    private String symbol;

    /**
     * Position side.
     */
    private PositionSide side;

    /**
     * Average price for opening the position.
     */
    private String avgPrice;

    /**
     * Amount of contracts opened.
     */
    private String position;

    /**
     * Amount of contracts available to close.
     */
    private String available;

    /**
     * Leverage of the position.
     */
    private String leverage;

    /**
     * Last trade price of the symbol.
     */
    private String lastPrice;

    /**
     * Current position value.
     */
    private String positionValue;

    /**
     * Forced liquidation price.
     */
    private String flp;

    /**
     * Margin for this position.
     */
    private String margin;

    /**
     * Margin rate for current position.
     */
    private String marginRate;

    /**
     * Unrealized profit and loss for current position held.
     */
    private String unrealizedPnL;

    /**
     * Rate of return for the position.
     */
    private String profitRate;

    /**
     * Cumulative realized profit and loss for this symbol.
     */
    private String realizedPnL;
}
