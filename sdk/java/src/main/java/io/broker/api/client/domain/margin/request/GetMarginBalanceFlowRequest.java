package io.broker.api.client.domain.margin.request;

public class GetMarginBalanceFlowRequest extends MarginBaseRequest {
    private String tokenId;
    private Long fromFlowId;
    private Long endFlowId;
    private Long startTime;
    private Long endTime;
    private int limit;

    public GetMarginBalanceFlowRequest() {
    }

    public String getTokenId() {
        return tokenId;
    }

    public GetMarginBalanceFlowRequest tokenId(String tokenId) {
        this.tokenId = tokenId;
        return this;
    }

    public Long getFromFlowId() {
        return fromFlowId;
    }

    public GetMarginBalanceFlowRequest fromFlowId(Long fromFlowId) {
        this.fromFlowId = fromFlowId;
        return this;
    }

    public Long getEndFlowId() {
        return endFlowId;
    }

    public GetMarginBalanceFlowRequest endFlowId(Long endFlowId) {
        this.endFlowId = endFlowId;
        return this;
    }

    public Long getStartTime() {
        return startTime;
    }

    public GetMarginBalanceFlowRequest startTime(Long startTime) {
        this.startTime = startTime;
        return this;
    }

    public Long getEndTime() {
        return endTime;
    }

    public GetMarginBalanceFlowRequest endTime(Long endTime) {
        this.endTime = endTime;
        return this;
    }

    public int getLimit() {
        return limit;
    }

    public GetMarginBalanceFlowRequest limit(int limit) {
        this.limit = limit;
        return this;
    }
}
