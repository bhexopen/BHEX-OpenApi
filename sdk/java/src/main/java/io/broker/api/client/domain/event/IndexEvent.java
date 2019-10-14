package io.broker.api.client.domain.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonDeserialize(using = IndexEventDeserializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndexEvent {
    /**
     * The symbol of index.
     */
    private String symbol;

    /**
     * The index value.
     */
    private String index;

    /**
     * EDP: Estimated delivery price for those trading pairs.
     */
    private String edp;
}
