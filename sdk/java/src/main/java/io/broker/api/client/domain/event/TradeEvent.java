package io.broker.api.client.domain.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.broker.api.client.constant.BrokerConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@JsonDeserialize(using = TradeEventDeserializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeEvent {

    private String symbol;

    private List<TradeItem> itemList;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public List<TradeItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<TradeItem> itemList) {
        this.itemList = itemList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BrokerConstants.TO_STRING_BUILDER_STYLE)
                .append("symbol", symbol)
                .append("itemList", itemList)
                .toString();

    }
}
