package io.broker.api.client.domain.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.broker.api.client.constant.BrokerConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeItem {

    @JsonProperty("t")
    private Long time;

    @JsonProperty("p")
    private String price;

    @JsonProperty("q")
    private String quantity;

    @JsonProperty("m")
    private Boolean isBuy;

    @JsonProperty("makerBuy")
    private Boolean makerBuy;

    @JsonProperty("v")
    private String version;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Boolean getBuy() {
        return isBuy;
    }

    public void setBuy(Boolean buy) {
        isBuy = buy;
    }

    public Boolean getMakerBuy() {
        return makerBuy;
    }

    public void setMakerBuy(Boolean makerBuy) {
        this.makerBuy = makerBuy;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String toString() {
        return new ToStringBuilder(this, BrokerConstants.TO_STRING_BUILDER_STYLE)
                .append("price", price)
                .append("quantity", quantity)
                .append("isBuy", isBuy)
                .append("makerBuy", makerBuy)
                .append("time", time)
                .append("version", version)
                .toString();

    }
}
