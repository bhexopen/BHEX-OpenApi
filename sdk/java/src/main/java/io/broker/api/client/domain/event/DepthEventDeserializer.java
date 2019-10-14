package io.broker.api.client.domain.event;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.broker.api.client.domain.market.OrderBookEntry;

import java.io.IOException;
import java.util.List;

public class DepthEventDeserializer extends JsonDeserializer<DepthEvent> {
    @Override
    public DepthEvent deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        try {
            ObjectCodec oc = jsonParser.getCodec();
            JsonNode node = oc.readTree(jsonParser);


            JsonNode data = node.get("data");
            JsonNode eventNode = data.get(0);

            ObjectMapper objectMapper = new ObjectMapper();
            List<OrderBookEntry> bidList = objectMapper.readValue(eventNode.get("b").toString(), new TypeReference<List<OrderBookEntry>>() {
            });
            List<OrderBookEntry> askList = objectMapper.readValue(eventNode.get("a").toString(), new TypeReference<List<OrderBookEntry>>() {
            });

            DepthEvent event = new DepthEvent();
            event.setSymbol(node.get("symbol").asText());
            event.setBids(bidList);
            event.setAsks(askList);
            event.setEventTime(eventNode.get("t").asLong());

            return event;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
