package io.broker.api.client.domain.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.broker.api.client.constant.BrokerConstants;
import io.broker.api.client.domain.market.OrderBookEntry;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Depth delta event for a symbol.
 */
@JsonDeserialize(using = DepthEventDeserializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepthEvent {

    @JsonProperty("t")
    private long eventTime;

    @JsonProperty("s")
    private String symbol;

    /**
     * Bid depth delta.
     */
    @JsonProperty("b")
    private List<OrderBookEntry> bids;

    /**
     * Ask depth delta.
     */
    @JsonProperty("a")
    private List<OrderBookEntry> asks;


    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }


    public List<OrderBookEntry> getBids() {
        return bids;
    }

    public void setBids(List<OrderBookEntry> bids) {
        this.bids = bids;
    }

    public List<OrderBookEntry> getAsks() {
        return asks;
    }

    public void setAsks(List<OrderBookEntry> asks) {
        this.asks = asks;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BrokerConstants.TO_STRING_BUILDER_STYLE)
                .append("eventTime", eventTime)
                .append("symbol", symbol)
                .append("bids", bids)
                .append("asks", asks)
                .toString();
    }
}
