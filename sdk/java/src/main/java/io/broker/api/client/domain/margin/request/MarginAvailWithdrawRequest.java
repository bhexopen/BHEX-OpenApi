package io.broker.api.client.domain.margin.request;

public class MarginAvailWithdrawRequest extends MarginBaseRequest {
    private String tokenId;

    public MarginAvailWithdrawRequest(String tokenId) {
        super();
        this.tokenId = tokenId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public MarginAvailWithdrawRequest tokenId(String tokenId) {
        this.tokenId = tokenId;
        return this;
    }
}
