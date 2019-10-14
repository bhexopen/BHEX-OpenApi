package io.broker.api.client.domain.event;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class TradeEventDeserializer extends JsonDeserializer<TradeEvent> {
    @Override
    public TradeEvent deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        try {
            ObjectCodec oc = jsonParser.getCodec();
            JsonNode node = oc.readTree(jsonParser);

            JsonNode trade = node.get("data");

            ObjectMapper objectMapper = new ObjectMapper();
            List<TradeItem> itemList = objectMapper.readValue(trade.toString(), new TypeReference<List<TradeItem>>() {
            });

            TradeEvent event = new TradeEvent();
            event.setSymbol(node.get("symbol").asText());
            event.setItemList(itemList);
            return event;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
