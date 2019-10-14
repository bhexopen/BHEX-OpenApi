package io.broker.api.client.domain.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BatchCancelOrderResult {

    /**
     * The message response of the cancel request.
     */
    private String message;

    /**
     * The timestamp when the response is returned.
     */
    private Long timestamp;
}
