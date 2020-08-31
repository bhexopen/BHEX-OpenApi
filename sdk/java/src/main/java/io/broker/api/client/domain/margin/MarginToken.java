package io.broker.api.client.domain.margin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarginToken {
    private Long exchangeId;
    private String tokenId;
    private String convertRate;
    private Integer leverage;
    private boolean canBorrow;
    private String maxQuantity;
    private String minQuantity;
    private String quantityPrecision;
    private String repayMinQuantity;
    private String interest;
    private Integer isOpen;
}
