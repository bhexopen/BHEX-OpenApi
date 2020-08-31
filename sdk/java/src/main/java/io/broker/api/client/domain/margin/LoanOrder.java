package io.broker.api.client.domain.margin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanOrder {
    private Long loanOrderId;
    private String clientId;
    private String tokenId;
    private String loanAmount;
    private String repaidAmount;
    private String unpaidAmount;
    private String interestRate;
    private Long interestStart;
    private Integer status;
    private String interestPaid;
    private String interestUnpaid;
    private Long createAt;
    private Long updateAt;
    private Long accountId;
}
