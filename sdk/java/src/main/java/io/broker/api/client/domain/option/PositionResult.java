package io.broker.api.client.domain.option;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PositionResult {

    /**
     * Balance id.
     */
    private Long balanceId;

    /**
     * Token id.
     */
    private String tokenId;

    /**
     * Account id.
     */
    private Long accountId;

    /**
     * Symbol.
     */
    private String symbol;

    /**
     * Symbol name.
     */
    private String symbolName;

    /**
     * Option type.
     */
    private String optionType;

    /**
     * Position.
     */
    private String position;

    /**
     * Total.
     */
    private String total;

    /**
     * Margin.
     */
    private String margin;

    /**
     * Settlement time.
     */
    private Long settlementTime;

    /**
     * Strike price.
     */
    private String strikePrice;

    /**
     * Price.
     */
    private String price;

    /**
     * Cost price.
     */
    private String costPrice;

    /**
     * Available position.
     */
    private String availablePosition;

    /**
     * Average price.
     */
    private String averagePrice;

    /**
     * Changed rate.
     */
    private String changedRate;

    /**
     * Changed.
     */
    private String changed;

    /**
     * Quote token name.
     */
    private String quoteTokenName;

    /**
     * Index.
     */
    private String index;

    /**
     * Base token id.
     */
    private String baseTokenId;

    /**
     * Base token name.
     */
    private String baseTokenName;

    /**
     * Quote token id.
     */
    private String quoteTokenId;
}
