package io.broker.api.client.domain.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RiskLimit {

    private String riskLimitId;
    private String quantity;
    private String initialMargin;
    private String maintMargin;
}
