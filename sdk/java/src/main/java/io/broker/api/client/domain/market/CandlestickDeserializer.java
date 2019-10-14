package io.broker.api.client.domain.market;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class CandlestickDeserializer extends JsonDeserializer<Candlestick> {

    @Override
    public Candlestick deserialize(JsonParser jp, DeserializationContext ctx) throws IOException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        Candlestick candlestick = new Candlestick();
        candlestick.setOpenTime(node.get(0).asLong());
        candlestick.setOpen(node.get(1).asText());
        candlestick.setHigh(node.get(2).asText());
        candlestick.setLow(node.get(3).asText());
        candlestick.setClose(node.get(4).asText());
        candlestick.setVolume(node.get(5).asText());
        candlestick.setCloseTime(node.get(6).asLong());
        candlestick.setQuoteAssetVolume(node.get(7).asText());
        candlestick.setNumberOfTrades(node.get(8).asLong());
        candlestick.setTakerBuyBaseAssetVolume(node.get(9).asText());
        candlestick.setTakerBuyQuoteAssetVolume(node.get(10).asText());
        return candlestick;
    }
}
