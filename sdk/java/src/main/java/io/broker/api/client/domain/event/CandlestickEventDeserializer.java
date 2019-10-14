package io.broker.api.client.domain.event;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class CandlestickEventDeserializer extends JsonDeserializer<CandlestickEvent> {

    @Override
    public CandlestickEvent deserialize(JsonParser jp, DeserializationContext ctx) throws IOException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);


        JsonNode kline = node.get("data").get(0);

        CandlestickEvent event = new CandlestickEvent();

        event.setTime(kline.get("t").asLong());
        event.setHigh(kline.get("h").asText());
        event.setOpen(kline.get("o").asText());
        event.setClose(kline.get("c").asText());
        event.setLow(kline.get("l").asText());
        event.setVolume(kline.get("v").asText());
        event.setSymbol(kline.get("s").asText());
        return event;
    }
}
