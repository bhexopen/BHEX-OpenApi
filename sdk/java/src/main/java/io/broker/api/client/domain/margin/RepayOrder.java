package io.broker.api.client.domain.margin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepayOrder {
    private Long repayOrderId;
    private Long accountId;
    private String clientId;
    private String tokenId;
    private Long loanOrderId;
    private String amount;
    private String interest;
    private Long createAt;
}
