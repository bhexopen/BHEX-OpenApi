package io.bhex.api.client.domain.account.request;

import io.bhex.api.client.constant.BHexConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class OpenOrderRequest extends OrderRequest {

    public OpenOrderRequest() {
    }


    public OpenOrderRequest(Integer limit) {
        super();
        this.limit = limit;
    }

    private Integer limit;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String toString() {
        return new ToStringBuilder(this, BHexConstants.TO_STRING_BUILDER_STYLE)
                .append("limit", limit)
                .toString();
    }
}
