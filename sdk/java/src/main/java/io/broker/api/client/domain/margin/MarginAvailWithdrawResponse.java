package io.broker.api.client.domain.margin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarginAvailWithdrawResponse {
    private String tokenId;
    private String availWithdrawAmount;
}
