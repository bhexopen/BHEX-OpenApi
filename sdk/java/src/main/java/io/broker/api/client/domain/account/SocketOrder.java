package io.broker.api.client.domain.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @JsonProperty("z")
    private String executedQuantity;


    @JsonProperty("w")
    private Boolean isWorking;

    @JsonProperty("m")
    private Boolean isMaker;
//
//    private Boolean M;

    @JsonProperty("O")
    private Long createTime;

    @JsonProperty("Z")
    private String executedAmount;


}
