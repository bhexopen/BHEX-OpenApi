package io.broker.api.client.domain.market;

import io.broker.api.client.constant.BrokerConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Wraps a symbol and its corresponding latest price.
 */
public class TickerPrice {

    private Long exchangeId;

    /**
     * Ticker symbol.
     */
    private String symbol;

    /**
     * Latest price.
     */
    private String price;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BrokerConstants.TO_STRING_BUILDER_STYLE)
                .append("exchangeId", exchangeId)
                .append("symbol", symbol)
                .append("price", price)
                .toString();
    }
}
