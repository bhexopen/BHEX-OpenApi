package io.broker.api.client.domain.margin.request;

public class QueryLoanOdersRequest extends MarginBaseRequest {
    private String tokenId;
    private int status;
    private Long fromLoanId;
    private Long endLoanId;
    private int limit;

    public QueryLoanOdersRequest() {
    }

    public QueryLoanOdersRequest(String tokenId, int status, int limit) {
        this.tokenId = tokenId;
        this.status = status;
        this.limit = limit;
    }

    public QueryLoanOdersRequest(String tokenId, int status, Long fromLoanId, Long endLoanId, int limit) {
        this.tokenId = tokenId;
        this.status = status;
        this.fromLoanId = fromLoanId;
        this.endLoanId = endLoanId;
        this.limit = limit;
    }

    public QueryLoanOdersRequest(int status, int limit) {
        this.status = status;
        this.limit = limit;
    }

    public String getTokenId() {
        return tokenId;
    }

    public QueryLoanOdersRequest tokenId(String tokenId) {
        this.tokenId = tokenId;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public QueryLoanOdersRequest status(int status) {
        this.status = status;
        return this;
    }

    public Long getFromLoanId() {
        return fromLoanId;
    }

    public QueryLoanOdersRequest fromLoanId(Long fromLoanId) {
        this.fromLoanId = fromLoanId;
        return this;
    }

    public Long getEndLoanId() {
        return endLoanId;
    }

    public QueryLoanOdersRequest endLoanId(Long endLoanId) {
        this.endLoanId = endLoanId;
        return this;
    }

    public int getLimit() {
        return limit;
    }

    public QueryLoanOdersRequest limit(int limit) {
        this.limit = limit;
        return this;
    }
}
