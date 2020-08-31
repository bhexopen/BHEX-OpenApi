package io.broker.api.client.domain.margin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarginAllPositionResponse {
    private String total;
    private String loanAmount;
    private String availMargin;
    private String occupyMargin;
}
