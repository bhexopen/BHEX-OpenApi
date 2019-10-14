package io.broker.api.client.domain.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.broker.api.client.constant.BrokerConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 24 hour price change statistics for a ticker.
 */
@JsonDeserialize(using = TickerEventDeserializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TickerEvent {

    private Long exchangeId;
    /**
     * Ticker symbol.
     */
    private String symbol;

    /**
     * Open price 24 hours ago.
     */
    private String openPrice;

    /**
     * Highest price during the past 24 hours.
     */
    private String highPrice;

    /**
     * Lowest price during the past 24 hours.
     */
    private String lowPrice;

    /**
     * Close Price during the past 24 hours.
     */
    private String closePrice;

    /**
     * Total volume during the past 24 hours.
     */
    private String volume;

    /**
     * Open time.
     */
    private long time;

    public Long getExchangeId() {
        return exchangeId;
    }

    public void setExchangeId(Long exchangeId) {
        this.exchangeId = exchangeId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(String closePrice) {
        this.closePrice = closePrice;
    }

    public String getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(String openPrice) {
        this.openPrice = openPrice;
    }

    public String getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(String highPrice) {
        this.highPrice = highPrice;
    }

    public String getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BrokerConstants.TO_STRING_BUILDER_STYLE)
                .append("exchangeId", exchangeId)
                .append("symbol", symbol)
                .append("openPrice", openPrice)
                .append("highPrice", highPrice)
                .append("lowPrice", lowPrice)
                .append("closePrice", closePrice)
                .append("volume", volume)
                .append("time", time)
                .toString();
    }
}
