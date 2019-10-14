package io.broker.api.client.domain.option;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SettlementResult {

    /**
     * Settlement id.
     */
    private Long settlementId;

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
     * Margin.
     */
    private String margin;

    /**
     * Timestamp.
     */
    private Long timestamp;

    /**
     * Strike price.
     */
    private String strikePrice;

    /**
     * Settlement price.
     */
    private String settlementPrice;

    /**
     * Max pay off.
     */
    private String maxPayOff;

    /**
     * Avg price.
     */
    private String averagePrice;

    /**
     * Position.
     */
    private String position;

    /**
     * Cost price.
     */
    private String costPrice;

    /**
     * Changed.
     */
    private String changed;

    /**
     * Changed rate.
     */
    private String changedRate;

    /**
     * Quote token name.
     */
    private String quoteTokenName;

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
