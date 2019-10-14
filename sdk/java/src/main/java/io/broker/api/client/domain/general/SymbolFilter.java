package io.broker.api.client.domain.general;


import java.io.Serializable;

/**
 * Filters define trading rules on a symbol or an exchange. Filters come in two forms: symbol filters and exchange filters.
 * <p>
 * The PRICE_FILTER defines the price rules for a symbol.
 * <p>
 * The LOT_SIZE filter defines the quantity (aka "lots" in auction terms) rules for a symbol.
 * <p>
 * The MIN_NOTIONAL filter defines the minimum notional value allowed for an order on a symbol. An order's notional value is the price * quantity.
 */
public class SymbolFilter implements Serializable {

    // PRICE_FILTER

    private FilterType filterType;

    /**
     * Defines the minimum price/stopPrice allowed.
     */
    private String minPrice;

    /**
     * Defines the maximum price/stopPrice allowed.
     */
    private String maxPrice;

    /**
     * Defines the intervals that a price/stopPrice can be increased/decreased by.
     */
    private String tickSize;


    // LOT_SIZE

    /**
     * Defines the minimum quantity/icebergQty allowed.
     */
    private String minQty;

    /**
     * Defines the maximum quantity/icebergQty allowed.
     */
    private String maxQty;

    /**
     * Defines the intervals that a quantity/icebergQty can be increased/decreased by.
     */
    private String stepSize;

    // MIN_NOTIONAL

    /**
     * Defines the minimum notional value allowed for an order on a symbol. An order's notional value is the price * quantity.
     */
    private String minNotional;

    private String limit;

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getTickSize() {
        return tickSize;
    }

    public void setTickSize(String tickSize) {
        this.tickSize = tickSize;
    }

    public String getMinQty() {
        return minQty;
    }

    public void setMinQty(String minQty) {
        this.minQty = minQty;
    }

    public String getMaxQty() {
        return maxQty;
    }

    public void setMaxQty(String maxQty) {
        this.maxQty = maxQty;
    }

    public String getStepSize() {
        return stepSize;
    }

    public void setStepSize(String stepSize) {
        this.stepSize = stepSize;
    }

    public String getMinNotional() {
        return minNotional;
    }

    public void setMinNotional(String minNotional) {
        this.minNotional = minNotional;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }
}
