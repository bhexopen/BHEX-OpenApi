package io.broker.api.client.domain.margin.request;

public class MarginLoanableRequest extends MarginBaseRequest {
    private String tokenId;

    public MarginLoanableRequest(String tokenId) {
        super();
        this.tokenId = tokenId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public MarginLoanableRequest tokenId(String tokenId) {
        this.tokenId = tokenId;
        return this;
    }
}
