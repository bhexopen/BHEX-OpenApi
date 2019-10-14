package io.broker.api.client.domain.event;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class TickerEventDeserializer extends JsonDeserializer<TickerEvent> {
    @Override
    public TickerEvent deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        JsonNode tickerNode = node.get("data").get(0);

        TickerEvent event = new TickerEvent();
        event.setExchangeId(tickerNode.get("e").asLong());
        event.setTime(tickerNode.get("t").asLong());
        event.setSymbol(tickerNode.get("s").asText());
        event.setOpenPrice(tickerNode.get("o").asText());
        event.setHighPrice(tickerNode.get("h").asText());
        event.setLowPrice(tickerNode.get("l").asText());
        event.setClosePrice(tickerNode.get("c").asText());
        event.setVolume(tickerNode.get("v").asText());
        return event;
    }
}
