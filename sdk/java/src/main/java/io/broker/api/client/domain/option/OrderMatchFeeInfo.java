package io.broker.api.client.domain.option;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderClassName = "Builder", toBuilder = true)
public class OrderMatchFeeInfo {

    private String feeToken;
    private String feeTokenId;
    private String feeTokenName;
    private String fee;

}
