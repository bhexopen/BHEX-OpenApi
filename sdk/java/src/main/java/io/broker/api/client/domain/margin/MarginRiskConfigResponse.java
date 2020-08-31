package io.broker.api.client.domain.margin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarginRiskConfigResponse {
    private String withdrawLine;
    private String warnLine;
    private String appendLine;
    private String stopLine;
}
