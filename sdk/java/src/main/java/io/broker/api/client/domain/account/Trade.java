package io.broker.api.client.domain.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.broker.api.client.constant.BrokerConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents an executed trade.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Trade {

    /**
     * Trade id.
     */
    private Long id;

    /**
     * Price.
     */
    private String price;

    /**
     * Quantity.
     */
    private String qty;

    /**
     * Commission.
     */
    private String commission;

    /**
     * Asset on which commission is taken
     */
    private String commissionAsset;

    /**
     * Trade execution time.
     */
    private long time;

    /**
     * The symbol of the trade.
     */
    private String symbol;

    private boolean buyer;

    private boolean maker;

    private String orderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getCommissionAsset() {
        return commissionAsset;
    }

    public void setCommissionAsset(String commissionAsset) {
        this.commissionAsset = commissionAsset;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public boolean isBuyer() {
        return buyer;
    }

    @JsonProperty("isBuyer")
    public void setBuyer(boolean buyer) {
        this.buyer = buyer;
    }

    public boolean isMaker() {
        return maker;
    }

    @JsonProperty("isMaker")
    public void setMaker(boolean maker) {
        this.maker = maker;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BrokerConstants.TO_STRING_BUILDER_STYLE)
                .append("id", id)
                .append("symbol", symbol)
                .append("price", price)
                .append("qty", qty)
                .append("commission", commission)
                .append("commissionAsset", commissionAsset)
                .append("time", time)
                .append("buyer", buyer)
                .append("maker", maker)
                .append("orderId", orderId)
                .toString();
    }
}
