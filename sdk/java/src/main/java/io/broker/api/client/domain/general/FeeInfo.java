package io.broker.api.client.domain.general;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FeeInfo {
    private String feeTokenId;
    private String feeTokenName;
    private String fee;

    public String getFeeTokenId() {
        return feeTokenId;
    }

    public void setFeeTokenId(String feeTokenId) {
        this.feeTokenId = feeTokenId;
    }

    public String getFeeTokenName() {
        return feeTokenName;
    }

    public void setFeeTokenName(String feeTokenName) {
        this.feeTokenName = feeTokenName;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
}
