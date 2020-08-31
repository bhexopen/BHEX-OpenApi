package io.broker.api.client.domain.margin.request;

public class QueryRepayOrdersRequest extends MarginBaseRequest {
    private String tokenId;
    private Long fromRepayId;
    private Long endRepayId;
    private int limit;
    private Long loanOrderId;

    public QueryRepayOrdersRequest(String tokenId, Long fromRepayId, Long endRepayId, int limit) {
        this.tokenId = tokenId;
        this.fromRepayId = fromRepayId;
        this.endRepayId = endRepayId;
        this.limit = limit;
    }

    public QueryRepayOrdersRequest(Long loanOrderId) {
        this.loanOrderId = loanOrderId;
    }

    public QueryRepayOrdersRequest() {
    }

    public QueryRepayOrdersRequest(Long fromRepayId, Long endRepayId) {
        this.fromRepayId = fromRepayId;
        this.endRepayId = endRepayId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public QueryRepayOrdersRequest tokenId(String tokenId) {
        this.tokenId = tokenId;
        return this;
    }

    public Long getFromRepayId() {
        return fromRepayId;
    }

    public QueryRepayOrdersRequest fromRepayId(Long fromRepayId) {
        this.fromRepayId = fromRepayId;
        return this;
    }

    public Long getEndRepayId() {
        return endRepayId;
    }

    public QueryRepayOrdersRequest endRepayId(Long endRepayId) {
        this.endRepayId = endRepayId;
        return this;
    }

    public int getLimit() {
        return limit;
    }

    public QueryRepayOrdersRequest limit(int limit) {
        this.limit = limit;
        return this;
    }

    public Long getLoanOrderId() {
        return loanOrderId;
    }

    public QueryRepayOrdersRequest loanOrderId(Long loanOrderId) {
        this.loanOrderId = loanOrderId;
        return this;
    }
}
