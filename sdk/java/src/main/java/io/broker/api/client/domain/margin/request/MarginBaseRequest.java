package io.broker.api.client.domain.margin.request;

import io.broker.api.client.constant.BrokerConstants;
import io.broker.api.client.domain.account.request.OrderRequest;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class MarginBaseRequest  {
    private Long recvWindow;

    private Long timestamp;

    public MarginBaseRequest() {
        this.timestamp = System.currentTimeMillis();
        this.recvWindow = BrokerConstants.DEFAULT_RECEIVING_WINDOW;
    }

    public Long getRecvWindow() {
        return recvWindow;
    }

    public MarginBaseRequest recvWindow(Long recvWindow) {
        this.recvWindow = recvWindow;
        return this;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public MarginBaseRequest timestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BrokerConstants.TO_STRING_BUILDER_STYLE)
                .append("recvWindow", recvWindow)
                .append("timestamp", timestamp)
                .toString();
    }
}
