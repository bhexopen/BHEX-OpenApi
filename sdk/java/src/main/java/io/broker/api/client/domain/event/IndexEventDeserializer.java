package io.broker.api.client.domain.event;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class IndexEventDeserializer extends JsonDeserializer<IndexEvent> {
    @Override
    public IndexEvent deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        JsonNode indexNode = node.get("data").get(0);

        return IndexEvent.builder()
            .symbol(indexNode.get("symbol").asText())
            .index(indexNode.get("index").asText())
            .edp(indexNode.get("edp").asText())
            .build();
    }
}
