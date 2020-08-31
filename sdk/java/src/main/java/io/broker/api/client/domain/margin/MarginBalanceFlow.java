package io.broker.api.client.domain.margin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarginBalanceFlow {
    private Long id;
    private Long accountId;
    private String token;
    private String tokenId;
    private String tokenName;
    private Integer flowTypeValue;
    private String flowType;
    private String flowName;
    private String change;
    private String total;
    private Long created;
}
