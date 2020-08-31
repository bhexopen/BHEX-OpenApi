package io.broker.api.client.domain.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * [{"e":"executionReport","E":"1596180140878","s":"HBCUSDT","c":"159618014059858","S":"BUY","o":"LIMIT","f":"GTC","q":"10","p":"1","X":"CANCELED","i":"683202969400788736","M":"0","l":"0","z":"0","L":"0","n":"0","N":"","u":true,"w":true,"m":false,"O":"1596180140602","Z":"0","A":"0","C":false,"v":"0"}]
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SocketOrder {

    @JsonProperty("e")
    private String eventType;

    @JsonProperty("E")
    private Long eventTime;

    @JsonProperty("s")
    private String symbol;

    @JsonProperty("c")
    private String clientOrderId;

    @JsonProperty("S")
    private String orderSide;

    @JsonProperty("o")
    private String orderType;

    @JsonProperty("f")
    private String timeInForce;

    @JsonProperty("q")
    private String quantity;

    @JsonProperty("p")
    private String price;

    @JsonProperty("X")
    private String status;

    @JsonProperty("i")
    private Long orderId;

    @JsonProperty("M")
    private Long matchOrderId;

    @JsonProperty("z")
    private String executedQuantity;


    @JsonProperty("w")
    private Boolean isWorking;

    @JsonProperty("m")
    private Boolean isMaker;

    @JsonProperty("O")
    private Long createTime;

    @JsonProperty("Z")
    private String executedAmount;


}
