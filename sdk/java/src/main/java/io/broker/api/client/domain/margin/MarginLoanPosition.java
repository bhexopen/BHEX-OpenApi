package io.broker.api.client.domain.margin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarginLoanPosition {
    private String tokenId;
    private String loanTotal;
    private String loanBtcValue;
    private String interestPaid;
    private String interestUnpaid;
    private String unpaidBtcValue;
}
