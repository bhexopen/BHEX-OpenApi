package io.broker.api.client.domain.account.request;

import io.broker.api.client.constant.BrokerConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class OpenOrderRequest extends OrderRequest {

    public OpenOrderRequest() {
    }

    public OpenOrderRequest(Integer limit) {
        super();
        this.limit = limit;
    }

    public OpenOrderRequest(String symbol, Integer limit) {
        super();
        this.symbol = symbol;
        this.limit = limit;
    }

    private String symbol;

    private Integer limit;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BrokerConstants.TO_STRING_BUILDER_STYLE)
                .append("limit", limit)
                .toString();
    }
}
