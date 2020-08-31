package io.broker.api.client.domain.margin.request;

public class LoanRequest extends MarginBaseRequest {
    private String clientOrderId;
    private String loanAmount;
    private String tokenId;

    public LoanRequest(String loanAmount, String tokenId) {
        this.loanAmount = loanAmount;
        this.tokenId = tokenId;
    }

    public String getClientOrderId() {
        return clientOrderId;
    }

    public LoanRequest clientOrderId(String clientOrderId) {
        this.clientOrderId = clientOrderId;
        return this;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public LoanRequest loanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
        return this;
    }

    public String getTokenId() {
        return tokenId;
    }

    public LoanRequest tokenId(String tokenId) {
        this.tokenId = tokenId;
        return this;
    }
}
