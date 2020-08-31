package io.broker.api.client.domain.margin.request;

public class MarginTransferRequest extends MarginBaseRequest {
    private int fromAccountType;
    private int toAccountType;
    private String tokenId;
    private String amount;

    public MarginTransferRequest(int fromAccountType, int toAccountType, String tokenId, String amount) {
        super();
        this.fromAccountType = fromAccountType;
        this.toAccountType = toAccountType;
        this.tokenId = tokenId;
        this.amount = amount;
    }

    public int getFromAccountType() {
        return fromAccountType;
    }

    public MarginTransferRequest fromAccountType(int fromAccountType) {
        this.fromAccountType = fromAccountType;
        return this;
    }

    public int getToAccountType() {
        return toAccountType;
    }

    public MarginTransferRequest toAccountType(int toAccountType) {
        this.toAccountType = toAccountType;
        return this;
    }

    public String getTokenId() {
        return tokenId;
    }

    public MarginTransferRequest tokenId(String tokenId) {
        this.tokenId = tokenId;
        return this;
    }

    public String getAmount() {
        return amount;
    }

    public MarginTransferRequest amount(String amount) {
        this.amount = amount;
        return this;
    }
}
