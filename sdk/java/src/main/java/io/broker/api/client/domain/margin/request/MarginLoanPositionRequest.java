package io.broker.api.client.domain.margin.request;

public class MarginLoanPositionRequest extends MarginBaseRequest {
    private String tokenId;

    public MarginLoanPositionRequest() {
        super();
    }

    public MarginLoanPositionRequest(String tokenId) {
        super();
        this.tokenId = tokenId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public MarginLoanPositionRequest tokenId(String tokenId) {
        this.tokenId = tokenId;
        return this;
    }
}
