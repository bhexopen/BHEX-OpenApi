
package io.broker.api.client.domain.option;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenOptionResult {

    /**
     * Symbol.
     */
    private String symbol;

    /**
     * Strike.
     */
    private String strike;

    /**
     * Created time.
     */
    private Long created;

    /**
     * Expiration.
     */
    private Long expiration;

    /**
     * Option type.
     */
    private int optionType;

    /**
     * Max pay off.
     */
    private String maxPayOff;

    /**
     * Index token.
     */
    private String indexToken;

    /**
     * Settlement.
     */
    private String settlement;
}